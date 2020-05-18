package org.example.database.test.dto.command.input;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class StatCommandInput {
    @NotNull
    private Date startDate;
    @NotNull
    private Date endDate;
}
