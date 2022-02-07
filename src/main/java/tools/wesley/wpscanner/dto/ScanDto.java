package tools.wesley.wpscanner.dto;

import javax.validation.constraints.NotNull;

public record ScanDto(@NotNull String userIp, @NotNull String url, @NotNull String startTime, String endTime) {
}
