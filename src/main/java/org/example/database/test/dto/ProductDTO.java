package org.example.database.test.dto;

import lombok.Data;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class ProductDTO {
    @NotNull
    private String name;
    @Min(value = 0)
    private int cost;
}
