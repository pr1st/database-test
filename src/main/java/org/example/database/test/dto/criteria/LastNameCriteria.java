package org.example.database.test.dto.criteria;

import lombok.Data;
import javax.validation.constraints.NotNull;

@Data
public class LastNameCriteria implements Criteria {
    @NotNull
    private String lastName;
}
