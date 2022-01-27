package com.inventory.db;

import com.inventory.core.User;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;


public class UserDAO extends AbstractDAO<User> {

    public UserDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Optional<User> findUserByUsername(String username){
        return Optional.ofNullable(uniqueResult(namedTypedQuery("com.inventory.core.User.findByUsername").setParameter("name",username)));
    }
    public User create(User i){
        return persist(i);
    }

    public List<User> getAll(){
       return list(namedTypedQuery("com.inventory.core.User.findAll"));
    }
}
