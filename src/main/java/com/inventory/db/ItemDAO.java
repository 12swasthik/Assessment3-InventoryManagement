package com.inventory.db;

import com.inventory.core.Item;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

public class ItemDAO extends AbstractDAO<Item> {

    public ItemDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Optional<Item> findById(Long id) {
        return Optional.ofNullable(get(id));
    }


    public List<Item> findItemByName(String itemName) {
        return list(namedTypedQuery("com.inventory.core.Item.findByName").setParameter("name", itemName));
    }

    public Item create(Item item) {
        return persist(item);
    }

    public List<Item> getAll() {
        return list(namedTypedQuery("com.inventory.core.Item.findAll"));
    }

    public void addStock(long quantity, long id) {
        namedQuery("com.inventory.core.Item.increaseItemCount").setParameter("quantity", quantity).setParameter("id", id).executeUpdate();
    }

    public void reduceStock(long quantity, long id) {
        namedQuery("com.inventory.core.Item.decreaseItemCount").setParameter("quantity", quantity).setParameter("id", id).executeUpdate();
    }
}
