package org.example.database.test.dto.command.output;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.example.database.test.dto.CustomerDTO;
import org.example.database.test.dto.criteria.Criteria;
import java.util.List;

@Data
public class SearchCommandOutput {
    @Data
    public static class Result {
        private Criteria criteria;
        private List<CustomerDTO> results;
    }

    @Setter(AccessLevel.NONE)
    private String type;
    private List<Result> results;

    public SearchCommandOutput() {
        type = "search";
    }
}
