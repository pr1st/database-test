package org.example.database.test.dto.command.input;

import lombok.Data;
import org.example.database.test.dto.CustomerDTO;
import org.example.database.test.dto.ProductDTO;
import org.example.database.test.dto.PurchaseDTO;
import javax.validation.Valid;
import java.util.List;

@Data
public class AddCommandInput {
    @Valid
    private List<CustomerDTO> customers;
    @Valid
    private List<ProductDTO> products;
    @Valid
    private List<PurchaseDTO> purchases;
}
