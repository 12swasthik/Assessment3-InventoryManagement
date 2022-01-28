package com.inventory.db;

import com.inventory.core.Purchase;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

public class PurchaseDAO extends AbstractDAO<Purchase> {
    public PurchaseDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Optional<Purchase> findById(Long id) {
        return Optional.ofNullable(get(id));
    }

    public Purchase create(Purchase purchase) {
        return persist(purchase);
    }

    public List<Purchase> getAll() {
        return list(namedTypedQuery("com.inventory.core.Orders.findAll"));
    }
}

