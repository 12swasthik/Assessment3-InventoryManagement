package com.inventory.db;

import com.inventory.core.Order;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

public class OrderDAO extends AbstractDAO<Order> {
    public OrderDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Optional<Order> findById(Long id){
        return Optional.ofNullable(get(id));
    }

    public Order create(Order c){
        return persist(c);
    }

    public List<Order> getAll(){
        return list(namedTypedQuery("com.inventory.core.Order.findAll"));
    }
}

