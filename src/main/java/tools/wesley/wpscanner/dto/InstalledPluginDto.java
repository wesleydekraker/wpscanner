package tools.wesley.wpscanner.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import tools.wesley.wpscanner.domain.Version;

import javax.validation.constraints.NotNull;

public record InstalledPluginDto(
        @NotNull String directoryName,
        String pluginName,
        @JsonSerialize(using = VersionSerializer.class) Version version) {
}
