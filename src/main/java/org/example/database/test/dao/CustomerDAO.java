package org.example.database.test.dao;

import org.example.database.test.dto.command.output.StatCommandOutput;
import org.example.database.test.dto.criteria.BadCustomersCriteria;
import org.example.database.test.dto.criteria.LastNameCriteria;
import org.example.database.test.dto.criteria.MinAndMaxExpensesCriteria;
import org.example.database.test.dto.criteria.ProductNameAndMinTimesCriteria;
import org.example.database.test.model.Customer;

import javax.persistence.Query;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public List<StatCommandOutput.CustomerResult> getStatisticsForCustomers(List<LocalDate> workingDays) {
        // as in JPQL subqueries in from or join clause are not supported decided to use native query
        // this usage of nativeQuery cannot be casted to generic type
        // in result intellij idea is warning about using raw type
        @SuppressWarnings("unchecked")
        List<Object[]> resultList = entityManager.createNativeQuery(
                "select c.firstname, c.lastname, prod_cost.pro_name, prod_cost.cost from customer c " +
                        "inner join " +
                        "( " +
                        "   select pro.id as pro_id, cus.id as cus_id, pro.name as pro_name, count(pur.id)*pro.cost as cost " +
                        "   from purchase pur " +
                        "   inner join product pro on pro.id = pur.product_id " +
                        "   inner join customer cus on cus.id = pur.customer_id " +
                        "   where pur.purchasedate in :workingDays " +
                        "   group by cus.id, pro.id, pro.name " +
                        "   order by cost desc " +
                        ") prod_cost on c.id = prod_cost.cus_id")
                .setParameter("workingDays", workingDays)
                .getResultList();

        Map<String, StatCommandOutput.CustomerResult> customerMap = new HashMap<>();

        for (Object[] row : resultList) {
            String name = row[1] + " " + row[0];
            String productName = (String) row[2];
            BigInteger cost = (BigInteger) row[3];

            StatCommandOutput.CustomerResult customerResult = customerMap.get(name);
            if (customerResult == null) {
                StatCommandOutput.CustomerResult newCustomer = new StatCommandOutput.CustomerResult();
                newCustomer.setName(name);
                newCustomer.setPurchases(new LinkedList<>());
                customerMap.put(name, newCustomer);
                customerResult = newCustomer;
            }
            customerResult.getPurchases().add(0, new StatCommandOutput.PurchaseResult(productName, cost.intValue()));
        }

        for (StatCommandOutput.CustomerResult result: customerMap.values()) {
            Integer totalExpenses = result.getPurchases()
                    .stream()
                    .map(StatCommandOutput.PurchaseResult::getExpenses)
                    .reduce(Integer::sum)
                    .orElse(0);
            result.setTotalExpense(totalExpenses);
        }

        return new ArrayList<>(customerMap.values());
    }
}
