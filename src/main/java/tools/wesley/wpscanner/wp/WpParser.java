package tools.wesley.wpscanner.wp;

import org.springframework.stereotype.Service;
import tools.wesley.wpscanner.domain.Version;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class WpParser {
    private final Pattern themeDirectory = Pattern.compile("(http[s]?://.*/)wp-content/themes/([^/]*)");
    private final Pattern themeVersion = Pattern.compile("Version: (.*)");
    private final Pattern themeName = Pattern.compile("Theme Name: (.*)");

    private final Pattern pluginDirectories = Pattern.compile("/wp-content/plugins/([^/]*)");
    private final Pattern pluginVersion = Pattern.compile("Stable tag: (.*)");
    private final Pattern pluginName = Pattern.compile("=== (.*) ===");

    private final Pattern installationDirectory = Pattern.compile("(http[s]?://.*/)wp-content/");
    private final Pattern wpVersion = Pattern.compile("<meta name=\"generator\" content=\"WordPress (.*)\" />");

    public Version getThemeVersion(String stylesheet) {
        var matcher = themeVersion.matcher(stylesheet);
        return matcher.find() ? new Version(matcher.group(1)) : new Version();
    }

    public Optional<String> getThemeName(String stylesheet) {
        var matcher = themeName.matcher(stylesheet);
        return matcher.find() ? Optional.of(matcher.group(1)) : Optional.empty();
    }

    public boolean hasPluginVersion(String readme) {
        return pluginVersion.matcher(readme).find();
    }

    public Version getPluginVersion(String readme) {
        var matcher = pluginVersion.matcher(readme);
        return matcher.find() ? new Version(matcher.group(1)) : new Version();
    }

    public Optional<String> getPluginName(String readme) {
        var matcher = pluginName.matcher(readme);
        return matcher.find() ? Optional.of(matcher.group(1)) : Optional.empty();
    }

    public Optional<String> getInstallationDirectory(String page) {
        var matcher = installationDirectory.matcher(page);
        return matcher.find() ? Optional.of(matcher.group(1)) : Optional.empty();
    }

    public Version getWpVersion(String page) {
        var matcher = wpVersion.matcher(page);
        return matcher.find() ? new Version(matcher.group(1)) : new Version();
    }

    public Optional<String> getThemeDirectory(String page) {
        var matcher = themeDirectory.matcher(page);
        return matcher.find() ? Optional.of(matcher.group(2)) : Optional.empty();
    }

    public List<String> getPluginDirectories(String page) {
        var matcher = pluginDirectories.matcher(page);
        var pluginDirectories = new ArrayList<String>();

        while (matcher.find())
            pluginDirectories.add(matcher.group(1));

        return pluginDirectories;
    }
}
