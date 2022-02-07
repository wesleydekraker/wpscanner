package tools.wesley.wpscanner.dto;

import javax.validation.constraints.NotNull;

public record ScanRequestDto(@NotNull String url) {
}
