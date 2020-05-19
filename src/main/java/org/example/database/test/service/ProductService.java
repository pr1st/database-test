package org.example.database.test.service;

import org.example.database.test.dao.ProductDAO;
import org.example.database.test.model.Product;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ProductService implements AbstractService<Product> {
    private ProductDAO productDAO = new ProductDAO();

    @Override
    public Product add(Product entity) {
        productDAO.startTransaction();
        Product added = productDAO.add(entity);
        productDAO.endTransaction();
        return added;
    }

    @Override
    public List<Product> addAll(Stream<Product> entityStream) {
        productDAO.startTransaction();
        List<Product> added = entityStream.map(p -> productDAO.add(p)).collect(Collectors.toList());
        productDAO.endTransaction();
        return added;
    }

    @Override
    public List<Product> findAll() {
        productDAO.startTransaction();
        List<Product> found = productDAO.findAll();
        productDAO.endTransaction();
        return found;
    }
}
