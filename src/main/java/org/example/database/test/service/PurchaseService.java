package org.example.database.test.service;

import org.example.database.test.dao.PurchaseDAO;
import org.example.database.test.model.Purchase;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PurchaseService implements AbstractService<Purchase> {
    private PurchaseDAO purchaseDAO = new PurchaseDAO();

    @Override
    public Purchase add(Purchase entity) {
        purchaseDAO.startTransaction();
        Purchase added = purchaseDAO.add(entity);
        purchaseDAO.endTransaction();
        return added;
    }

    @Override
    public List<Purchase> addAll(Stream<Purchase> entityStream) {
        purchaseDAO.startTransaction();
        List<Purchase> added = entityStream.map(p -> purchaseDAO.add(p)).collect(Collectors.toList());
        purchaseDAO.endTransaction();
        return added;
    }

    @Override
    public List<Purchase> findAll() {
        purchaseDAO.startTransaction();
        List<Purchase> found = purchaseDAO.findAll();
        purchaseDAO.endTransaction();
        return found;
    }
}
