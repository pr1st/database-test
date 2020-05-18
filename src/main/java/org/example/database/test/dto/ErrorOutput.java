package org.example.database.test.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
public class ErrorOutput {
    @Setter(AccessLevel.NONE)
    private String type;
    private String message;

    public ErrorOutput() {
        type = "error";
    }
}
