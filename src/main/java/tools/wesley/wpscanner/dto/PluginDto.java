package tools.wesley.wpscanner.dto;

import javax.validation.constraints.NotNull;

public record PluginDto(@NotNull String scanGuid, @NotNull String pluginName) {
}
