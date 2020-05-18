package org.example.database.test.dto;

import lombok.Data;
import javax.validation.constraints.NotNull;

@Data
public class CustomerDTO {
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
}
