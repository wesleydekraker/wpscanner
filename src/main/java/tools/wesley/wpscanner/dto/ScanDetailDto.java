package tools.wesley.wpscanner.dto;

import tools.wesley.wpscanner.domain.Scan;
import tools.wesley.wpscanner.domain.ThemeVulnerability;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public class ScanDetailDto {
    @NotNull
    private boolean finished;
    private String message;
    private List<ProblemDto> problems;
    private List<InstalledPluginDto> installedPlugins;
    private List<InstalledThemeDto> installedThemes;

    public ScanDetailDto() {

    }

    public ScanDetailDto(Scan scan) {
        this.setFinished(scan.getEndTime() != null);

        if (scan.getIpAddress() == null || !scan.getValidUrl()) {
            this.setMessage("Invalid URL.");
        } else if (!scan.getWP()) {
            this.setMessage("This is not a WordPress website.");
        } else {
            this.setMessage("The analysis of " + scan.getUrl() + " is completed.");
            List<ProblemDto> problems = new ArrayList<>();
            List<InstalledThemeDto> installedThemes = new ArrayList<>();
            List<InstalledPluginDto> installedPlugins = new ArrayList<>();

            for (var installedTheme : scan.getInstalledThemes()) {
                var theme = installedTheme.getTheme();
                installedThemes.add(new InstalledThemeDto(theme.getDirectoryName(), theme.getDisplayName(),
                        installedTheme.getVersion()));

                for (ThemeVulnerability vulnerability : theme.getVulnerabilities(installedTheme.getVersion())) {
                    problems.add(new ProblemDto("High", "Vulnerable theme",
                            "Your theme " + theme.getDisplayName() + " is vulnerable to " +
                                    vulnerability.getVulnerabilityType() +
                                    ". You should update this theme immediately."));
                }
            }

            for (var installedPlugin : scan.getInstalledPlugins()) {
                var plugin = installedPlugin.getPlugin();

                installedPlugins.add(new InstalledPluginDto(plugin.getDirectoryName(), plugin.getDisplayName(),
                        installedPlugin.getVersion()));

                for (var pluginVulnerability : plugin.getVulnerabilities(installedPlugin.getVersion())) {
                    problems.add(new ProblemDto("High", "Vulnerable plugin",
                            "Your plugin " + plugin.getDisplayName() + " is vulnerable to " +
                                    pluginVulnerability.getVulnerabilityType() +
                                    ". You should update this plugin immediately."));
                }
            }

            if (scan.getBruteForceProtection() != null && !scan.getBruteForceProtection()) {
                problems.add(new ProblemDto("Medium", "Unlimited login attempts",
                        "Install the \"All In One WP Security\" plugin."));
            }

            if (scan.getReadmeAccessible() != null && scan.getReadmeAccessible()) {
                problems.add(new ProblemDto("Low", "readme.html is accessible",
                        "Remove the readme.html file."));
            }

            this.setProblems(problems);
            this.setInstalledThemes(installedThemes);
            this.setInstalledPlugins(installedPlugins);
        }
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ProblemDto> getProblems() {
        return problems;
    }

    public void setProblems(List<ProblemDto> problems) {
        this.problems = problems;
    }

    public List<InstalledPluginDto> getInstalledPlugins() {
        return installedPlugins;
    }

    public void setInstalledPlugins(List<InstalledPluginDto> installedPlugins) {
        this.installedPlugins = installedPlugins;
    }

    public List<InstalledThemeDto> getInstalledThemes() {
        return installedThemes;
    }

    public void setInstalledThemes(List<InstalledThemeDto> installedThemes) {
        this.installedThemes = installedThemes;
    }
}
