package org.example.database.test.dao;

import org.example.database.test.dto.criteria.BadCustomersCriteria;
import org.example.database.test.dto.criteria.LastNameCriteria;
import org.example.database.test.dto.criteria.MinAndMaxExpensesCriteria;
import org.example.database.test.dto.criteria.ProductNameAndMinTimesCriteria;
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

    public List<Customer> getByLastNameCriteria(LastNameCriteria criteria) {
        return entityManager.createQuery("from Customer c where c.lastName = :lastName", Customer.class)
                .setParameter("lastName", criteria.getLastName())
                .getResultList();
    }

    public List<Customer> getByProductNameAndMinTimesCriteria(ProductNameAndMinTimesCriteria criteria) {
        return entityManager.createQuery(
                "select c from Customer c where :minTimes <= " +
                        "( " +
                        "   select count(*) from Purchase pur " +
                        "   where pur.customer = c and pur.product in " +
                        "   ( " +
                        "       select pro from Product pro where pro.name = :productName " +
                        "   ) " +
                        ")"
                , Customer.class)
                .setParameter("minTimes", criteria.getMinTimes())
                .setParameter("productName", criteria.getProductName())
                .getResultList();
    }

    public List<Customer> getByMinAndMaxExpensesCriteria(MinAndMaxExpensesCriteria criteria) {
        return entityManager.createQuery(
                "select c from Customer c where " +
                        "( " +
                        "   select sum(pro.cost) from Purchase pur " +
                        "   join pur.product pro " +
                        "   where pur.customer = c " +
                        ") between :minExpenses and :maxExpenses"
                , Customer.class)
                .setParameter("minExpenses", (long) criteria.getMinExpenses())
                .setParameter("maxExpenses", (long) criteria.getMaxExpenses())
                .getResultList();
    }

    public List<Customer> getByBadCustomersCriteria(BadCustomersCriteria criteria) {
        return entityManager.createQuery(
                "select c from Purchase pur " +
                        "right join pur.customer c " +
                        "group by c " +
                        "order by count(pur)"
                , Customer.class)
                .setMaxResults(criteria.getBadCustomers())
                .getResultList();
    }
}
