package com.inventory.db;

import com.inventory.core.Sale;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

public class SaleDAO extends AbstractDAO<Sale> {
    public SaleDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Optional<Sale> findById(Long id) {
        return Optional.ofNullable(get(id));
    }

    public Sale create(Sale sale) {
        return persist(sale);
    }

    public List<Sale> getAll() {
        return list(namedTypedQuery("com.inventory.core.Sale.findAll"));
    }
}

