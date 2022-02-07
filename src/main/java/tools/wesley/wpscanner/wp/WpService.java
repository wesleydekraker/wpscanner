package tools.wesley.wpscanner.wp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tools.wesley.wpscanner.controllers.HttpException;
import tools.wesley.wpscanner.repositories.PluginRepository;
import tools.wesley.wpscanner.repositories.ThemeRepository;
import tools.wesley.wpscanner.domain.*;

import java.net.http.HttpResponse;

@Service
public class WpService {
    @Autowired
    private ThemeRepository themeRepository;
    @Autowired
    private PluginRepository pluginRepository;
    @Autowired
    private CustomHttpClient httpClient;
    @Autowired
    private WpParser wpParser;

    public boolean addPluginToScan(Scan scan, String directoryName, boolean isActive) {
        for (var installedPlugin : scan.getInstalledPlugins()) {
            var plugin = installedPlugin.getPlugin();
            if (plugin.getDirectoryName().equals(directoryName))
                return false;
        }

        var plugin = pluginRepository.findByDirectoryName(directoryName)
                .orElseGet(() -> pluginRepository.save(new Plugin(directoryName)));

        HttpResponse<String> httpResponse;
        try {
            httpResponse = this.httpClient.getPage(scan.getInstallationDirectory() +
                    "wp-content/plugins/" + directoryName + "/readme.txt");
        } catch (HttpException e) {
            return false;
        }

        if (httpResponse.statusCode() != 200)
            return false;

        var readme = httpResponse.body();

        var installedPlugin = new InstalledPlugin();
        installedPlugin.setPlugin(plugin);

        if (!wpParser.hasPluginVersion(readme) && !isActive)
            return false;

        installedPlugin.setVersion(wpParser.getPluginVersion(readme));
        scan.addInstalledPlugin(installedPlugin);

        var pluginName = wpParser.getPluginName(readme);
        if (plugin.getDisplayName() == null && pluginName.isPresent()) {
            plugin.setDisplayName(pluginName.get());
            pluginRepository.save(plugin);
        }

        return true;
    }

    public void addThemeToScan(Scan scan, String directoryName, boolean isActive) {
        for (var installedPlugin : scan.getInstalledThemes()) {
            var theme = installedPlugin.getTheme();
            if (theme.getDirectoryName().equals(directoryName))
                return;
        }

        var theme = themeRepository.findByDirectoryName(directoryName)
                .orElseGet(() -> themeRepository.save(new Theme(directoryName)));

        HttpResponse<String> httpResponse;
        try {
            httpResponse = this.httpClient.getPage(scan.getInstallationDirectory() +
                    "wp-content/themes/" + directoryName + "/style.css");
        } catch (Exception e) {
            return;
        }

        if (httpResponse.statusCode() != 200)
            return;

        var stylesheet = httpResponse.body();

        var installedTheme = new InstalledTheme();
        installedTheme.setTheme(theme);
        installedTheme.setVersion(wpParser.getThemeVersion(stylesheet));
        installedTheme.setActive(isActive);

        scan.addInstalledTheme(installedTheme);

        var themeName = wpParser.getThemeName(stylesheet);
        if (theme.getDisplayName() == null && themeName.isPresent()) {
            theme.setDisplayName(themeName.get());
            themeRepository.save(theme);
        }
    }
}
