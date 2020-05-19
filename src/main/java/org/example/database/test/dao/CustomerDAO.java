package org.example.database.test.dao;

import org.example.database.test.model.Customer;

import java.util.List;

public class CustomerDAO extends AbstractDAO<Customer> {
    @Override
    public Customer add(Customer entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public List<Customer> findAll() {
        return entityManager.createQuery("from Customer", Customer.class).getResultList();
    }
}
