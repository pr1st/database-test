package org.example.database.test.dto;

import lombok.Data;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class PurchaseDTO {
    @NotNull
    private CustomerDTO customer;
    @NotNull
    private String productName;
    @NotNull
    private LocalDate purchaseDate;
}
