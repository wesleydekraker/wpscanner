package tools.wesley.wpscanner.dto;

import javax.validation.constraints.NotNull;

public record ProblemDto(@NotNull String priority, @NotNull String problem, @NotNull String solution) {
}
