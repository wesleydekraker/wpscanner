package tools.wesley.wpscanner.dto;

import javax.validation.constraints.NotNull;

public record LoginDto(@NotNull String username, @NotNull String password) {
}
