package org.example.database.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import lombok.SneakyThrows;
import org.example.database.test.dto.command.input.AddCommandInput;
import org.example.database.test.dto.command.input.SearchCommandInput;
import org.example.database.test.dto.command.input.StatCommandInput;
import org.example.database.test.dto.command.output.SearchCommandOutput;
import org.example.database.test.dto.command.output.StatCommandOutput;
import org.example.database.test.dto.criteria.Criteria;
import org.example.database.test.util.JsonCriteriaDeserializer;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.io.File;
import java.util.Set;

public class CommandRunner {
    private final File inputFile;
    private final File outputFile;
    private final ObjectMapper objectMapper;
    private final Validator validator;

    public CommandRunner(File inputFile, File outputFile) {
        this.inputFile = inputFile;
        this.outputFile = outputFile;
        this.objectMapper = new ObjectMapper();
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();

        SimpleModule module = new SimpleModule();
        module.addDeserializer(Criteria.class, new JsonCriteriaDeserializer());
        objectMapper.registerModule(module);
    }

    @SneakyThrows
    public void search() {
        SearchCommandInput input = objectMapper.readValue(inputFile, SearchCommandInput.class);
        validationCheck(input);
        SearchCommandOutput output = new SearchCommandOutput();

        // todo construct output

        objectMapper.writeValue(outputFile, output);
    }

    @SneakyThrows
    public void stat() {
        StatCommandInput input = objectMapper.readValue(inputFile, StatCommandInput.class);
        validationCheck(input);
        StatCommandOutput output = new StatCommandOutput();

        // todo construct output

        objectMapper.writeValue(outputFile, output);
    }

    @SneakyThrows
    public void add() {
        AddCommandInput input = objectMapper.readValue(inputFile, AddCommandInput.class);
        validationCheck(input);

        // todo add to database
    }

    private void validationCheck(Object o) {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(o);
        if (constraintViolations.size() > 0)
            throw new IllegalArgumentException("Incorrect input json format");
    }
}
