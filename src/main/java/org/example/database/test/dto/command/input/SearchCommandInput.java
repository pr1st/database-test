package org.example.database.test.dto.command.input;

import lombok.Data;
import org.example.database.test.dto.criteria.Criteria;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class SearchCommandInput {
    @NotNull
    @Valid
    private List<Criteria> criterias;
}
