package org.example.database.test.service;

import org.example.database.test.dao.CustomerDAO;
import org.example.database.test.dto.CustomerDTO;
import org.example.database.test.dto.command.output.SearchCommandOutput;
import org.example.database.test.dto.criteria.*;
import org.example.database.test.model.Customer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CustomerService implements AbstractService<Customer> {
    private CustomerDAO customerDAO = new CustomerDAO();

    @Override
    public Customer add(Customer entity) {
        customerDAO.startTransaction();
        Customer added = customerDAO.add(entity);
        customerDAO.endTransaction();
        return added;
    }

    @Override
    public List<Customer> addAll(Stream<Customer> entityStream) {
        customerDAO.startTransaction();
        List<Customer> added = entityStream.map(c -> customerDAO.add(c)).collect(Collectors.toList());
        customerDAO.endTransaction();
        return added;
    }

    @Override
    public List<Customer> findAll() {
        customerDAO.startTransaction();
        List<Customer> found = customerDAO.findAll();
        customerDAO.endTransaction();
        return found;
    }

    public List<SearchCommandOutput.Result> getCustomersByCriteria(List<Criteria> criterias) {
        customerDAO.startTransaction();
        List<SearchCommandOutput.Result> results = new ArrayList<>();
        for (Criteria criteria: criterias) {
            SearchCommandOutput.Result result = new SearchCommandOutput.Result();
            result.setCriteria(criteria);
            List<Customer> customerResults;
            if (criteria instanceof LastNameCriteria) {
                customerResults = customerDAO.getByLastNameCriteria((LastNameCriteria) criteria);
            } else if (criteria instanceof ProductNameAndMinTimesCriteria) {
                customerResults = customerDAO.getByProductNameAndMinTimesCriteria((ProductNameAndMinTimesCriteria) criteria);
            } else if (criteria instanceof MinAndMaxExpensesCriteria) {
                customerResults = customerDAO.getByMinAndMaxExpensesCriteria((MinAndMaxExpensesCriteria) criteria);
            } else if (criteria instanceof BadCustomersCriteria) {
                customerResults = customerDAO.getByBadCustomersCriteria((BadCustomersCriteria) criteria);
            } else {
                throw new IllegalStateException("Criteria not found");
            }
            result.setResults(customerResults.stream()
                    .map(c -> new CustomerDTO(c.getFirstName(), c.getLastName()))
                    .collect(Collectors.toList()));
            results.add(result);
        }

        customerDAO.endTransaction();
        return results;
    }
}
