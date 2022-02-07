package tools.wesley.wpscanner.dto;

import javax.validation.constraints.NotNull;

public record ChangePasswordDto(@NotNull String username, @NotNull String newPassword) {
}
