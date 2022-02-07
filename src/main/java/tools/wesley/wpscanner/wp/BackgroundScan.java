package tools.wesley.wpscanner.wp;

import tools.wesley.wpscanner.controllers.HttpException;
import tools.wesley.wpscanner.repositories.ScanRepository;
import tools.wesley.wpscanner.domain.*;

import java.net.InetAddress;
import java.net.URL;
import java.net.http.HttpResponse;

public class BackgroundScan implements Runnable {
    private Scan scan;
    private CustomHttpClient httpClient;
    private WpService wpService;
    private ScanRepository scanRepository;
    private WpParser wpParser = new WpParser();

    public BackgroundScan(Scan scan, CustomHttpClient httpClient, WpService wpService, ScanRepository scanRepository) {
        this.scan = scan;
        this.httpClient = httpClient;
        this.wpService = wpService;
        this.scanRepository = scanRepository;
    }

    public void run() {
        try {
            InetAddress ipAddress = InetAddress.getByName(new URL(scan.getUrl()).getHost());
            scan.setIpAddress(ipAddress.getHostAddress());
        } catch (Exception e) {
            scan.setIpAddress(null);
            return;
        }

        HttpResponse<String> response;

        try {
            response = this.httpClient.getPage(scan.getUrl());

            scan.setValidUrl(true);
        } catch (Exception e) {
            scan.setValidUrl(false);
            return;
        }

        var installationDirectory = wpParser.getInstallationDirectory(response.body());
        var themeDirectory = wpParser.getThemeDirectory(response.body());

        if (installationDirectory.isPresent() && themeDirectory.isPresent()) {
            scan.setWP(true);
            scan.setInstallationDirectory(installationDirectory.get());

            wpService.addThemeToScan(scan, themeDirectory.get(), true);
        } else {
            scan.setWP(false);
            return;
        }

        var pluginDirectories = wpParser.getPluginDirectories(response.body());
        for (var pluginDirectory : pluginDirectories)
            wpService.addPluginToScan(scan, pluginDirectory, true);

        scan.setWordPressVersion(wpParser.getWpVersion(response.body()));

        try {
            response = this.httpClient.getPage(scan.getInstallationDirectory() + "readme.html");
            this.scan.setReadmeAccessible(response.statusCode() == 200);
        } catch (HttpException ignored) {
            this.scan.setReadmeAccessible(false);
        }

        var result = wpService.addPluginToScan(scan, "all-in-one-wp-security-and-firewall", false);
        scan.setBruteForceProtection(result);

        scan.setEndTime();
        scanRepository.save(scan);
    }
}
