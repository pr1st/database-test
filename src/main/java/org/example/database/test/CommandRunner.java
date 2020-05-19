package org.example.database.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import lombok.SneakyThrows;
import org.example.database.test.dto.CustomerDTO;
import org.example.database.test.dto.command.input.AddCommandInput;
import org.example.database.test.dto.command.input.SearchCommandInput;
import org.example.database.test.dto.command.input.StatCommandInput;
import org.example.database.test.dto.command.output.SearchCommandOutput;
import org.example.database.test.dto.command.output.StatCommandOutput;
import org.example.database.test.dto.criteria.Criteria;
import org.example.database.test.model.Customer;
import org.example.database.test.model.Product;
import org.example.database.test.model.Purchase;
import org.example.database.test.service.CustomerService;
import org.example.database.test.service.ProductService;
import org.example.database.test.service.PurchaseService;
import org.example.database.test.util.JsonCriteriaDeserializer;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.io.File;
import java.util.List;
import java.util.Set;

public class CommandRunner {
    // for working with input/output data
    private final File inputFile;
    private final File outputFile;
    private final ObjectMapper objectMapper;
    private final Validator validator;

    // for working with database data
    private final CustomerService customerService;
    private final ProductService productService;
    private final PurchaseService purchaseService;

    public CommandRunner(File inputFile, File outputFile) {
        this.inputFile = inputFile;
        this.outputFile = outputFile;
        this.objectMapper = new ObjectMapper();
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
        this.customerService = new CustomerService();
        this.productService = new ProductService();
        this.purchaseService = new PurchaseService();

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

        if (input.getCustomers() != null) {
            customerService.addAll(input.getCustomers()
                    .stream()
                    .map(c -> new Customer(c.getFirstName(), c.getLastName())));
        }
        if (input.getProducts() != null) {
            productService.addAll(input.getProducts()
                    .stream()
                    .map(p -> new Product(p.getName(), p.getCost())));
        }

        if (input.getPurchases() != null) {
            List<Customer> customers = customerService.findAll();
            List<Product> products = productService.findAll();
            purchaseService.addAll(input.getPurchases()
                    .stream()
                    .map(purchase -> {
                        CustomerDTO customerDTO = purchase.getCustomer();
                        Customer customer = customers.stream()
                                .filter(c -> c.getFirstName().equals(customerDTO.getFirstName()))
                                .filter(c -> c.getLastName().equals(customerDTO.getLastName()))
                                .findFirst()
                                .orElseThrow(() -> new IllegalArgumentException("Foreign relation for: " + purchase + " not found"));
                        Product product = products.stream()
                                .filter(pr -> pr.getName().equals(purchase.getProductName()))
                                .findFirst()
                                .orElseThrow(() -> new IllegalArgumentException("Foreign relation for: " + purchase + " not found"));
                        return new Purchase(customer, product, purchase.getPurchaseDate());
                    }));
        }
    }

    private void validationCheck(Object o) {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(o);
        if (constraintViolations.size() > 0)
            throw new IllegalArgumentException("Incorrect input json format");
    }
}
