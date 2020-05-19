package org.example.database.test.dto.command.output;

import lombok.*;

import java.util.List;

@Data
public class StatCommandOutput {
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PurchaseResult {
        private String name;
        private int expenses;
    }

    @Data
    public static class CustomerResult {
        private String name;
        private List<PurchaseResult> purchases;
        private int totalExpense;
    }

    @Setter(AccessLevel.NONE)
    private String type;
    private int totalDays;
    private List<CustomerResult> customers;
    private int totalExpenses;
    private double avgExpenses;

    public StatCommandOutput() {
        type = "stat";
    }
}
