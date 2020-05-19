package org.example.database.test.dao;

import org.example.database.test.model.Product;
import org.example.database.test.model.Purchase;

import java.util.List;

public class PurchaseDAO extends AbstractDAO<Purchase> {
    @Override
    public Purchase add(Purchase entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public List<Purchase> findAll() {
        return entityManager.createQuery("from Purchase", Purchase.class).getResultList();
    }
}
