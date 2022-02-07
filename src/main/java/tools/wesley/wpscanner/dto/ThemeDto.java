package tools.wesley.wpscanner.dto;

import javax.validation.constraints.NotNull;

public record ThemeDto(@NotNull String scanGuid, @NotNull String themeName) {
}
