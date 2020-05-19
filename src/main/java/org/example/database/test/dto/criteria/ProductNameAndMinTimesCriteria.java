package org.example.database.test.dto.criteria;

import lombok.Data;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class ProductNameAndMinTimesCriteria implements Criteria {
    @NotNull
    private String productName;
    @Min(0)
    private long minTimes;
}
