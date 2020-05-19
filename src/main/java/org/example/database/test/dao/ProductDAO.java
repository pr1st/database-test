package org.example.database.test.dao;

import org.example.database.test.model.Customer;
import org.example.database.test.model.Product;

import java.util.List;

public class ProductDAO extends AbstractDAO<Product> {
    @Override
    public Product add(Product entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public List<Product> findAll() {
        return entityManager.createQuery("from Product", Product.class).getResultList();
    }
}
