package org.example.database.test.dto.criteria;

import lombok.Data;
import javax.validation.constraints.Min;

@Data
public class MinAndMaxExpensesCriteria implements Criteria {
    @Min(0)
    private int minExpenses;
    @Min(0)
    private int maxExpenses;
}
