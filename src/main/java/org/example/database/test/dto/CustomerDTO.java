package org.example.database.test.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class CustomerDTO {
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
}
