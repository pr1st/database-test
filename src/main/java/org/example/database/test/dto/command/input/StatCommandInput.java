package org.example.database.test.dto.command.input;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class StatCommandInput {
    @NotNull
    private LocalDate startDate;
    @NotNull
    private LocalDate endDate;
}
