package org.example.database.test.dto.criteria;

import lombok.Data;

import javax.validation.constraints.Min;

@Data
public class BadCustomersCriteria implements Criteria {
    @Min(0)
    private int badCustomers;
}
