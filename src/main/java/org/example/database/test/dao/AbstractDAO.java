package org.example.database.test.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.stream.Stream;

public abstract class AbstractDAO<T> {
    private static EntityManagerFactory entityManagerFactory;

    static {
        entityManagerFactory = Persistence.createEntityManagerFactory("Store");
    }

    protected EntityManager entityManager;

    public final void startTransaction() {
        entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
    }

    public final void endTransaction() {
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public abstract T add(T entity);

    public abstract List<T> findAll();
}
