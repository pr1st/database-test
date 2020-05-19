package org.example.database.test.service;

import org.example.database.test.dao.CustomerDAO;
import org.example.database.test.model.Customer;

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
}
