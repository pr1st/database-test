package org.example.database.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.database.test.dto.ErrorOutput;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Consumer;

public class Main {
    private static Consumer<CommandRunner> command;
    private static File inputFile;
    private static File outputFile;

    public static void main(String[] args) throws IOException {
        if (args.length > 0 && args[0].equals("help")) {
            System.out.println(getUsage());
            return;
        }

        try {
            parseArgs(args);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.err.println(getUsage());
            return;
        }

        try {
            command.accept(new CommandRunner(inputFile, outputFile));
        } catch (Exception e) {
            ErrorOutput errorOutput = new ErrorOutput();
            errorOutput.setMessage(e.getMessage());
            new ObjectMapper().writerWithDefaultPrettyPrinter().writeValue(outputFile, errorOutput);
            e.printStackTrace();
        }
    }

    private static void parseArgs(String[] args) {
        if (args.length < 3) {
            throw new IllegalArgumentException("Not enough args");
        }

        switch (args[0]) {
            case "search":
                command = CommandRunner::search;
                break;
            case "stat":
                command = CommandRunner::stat;
                break;
            case "add":
                command = CommandRunner::add;
                break;
            default:
                throw new IllegalArgumentException("Command not found");
        }

        Path input = Paths.get(args[1]);
        if (!Files.isRegularFile(input)) {
            throw new IllegalArgumentException("Input file does not exist");
        }
        inputFile = input.toFile();

        Path output = Paths.get(args[2]);
        if (!Files.isRegularFile(output)) {
            throw new IllegalArgumentException("Output file does not exist");
        }
        outputFile = output.toFile();
    }

    private static String getUsage() {
        return "Usage: java -jar program.jar command inputFile outputFile\n" +
                "available commands: help, add, search, stat";
    }
}
