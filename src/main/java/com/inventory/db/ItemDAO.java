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

    public Optional<Item> findById(Long id){
        return Optional.ofNullable(get(id));
    }


    public Optional<Item> findItemByName(String name){
        return Optional.ofNullable(uniqueResult(namedTypedQuery("com.inventory.core.Item.findByName").setParameter("name",name)));
    }

    public Item create(Item i){
        return persist(i);
    }

    public List<Item> getAll(){
        return list(namedTypedQuery("com.inventory.core.Item.findAll"));
    }

    public void addStock(int quantity, long id){
        namedTypedQuery("com.inventory.core.Item.increaseItemCount").setParameter("quantity", quantity).setParameter("id", id);
    }

    public void reduceStock(int quantity,long id){
         namedTypedQuery("com.inventory.core.Item.decreseItemCount").setParameter("quantity",quantity).setParameter("id",id);
    }
}
