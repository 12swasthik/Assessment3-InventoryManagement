package com.inventory.db;

import com.inventory.core.Category;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

public class CategoryDAO extends AbstractDAO<Category> {

    public CategoryDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Optional<Category> findById(Long id) {
        return Optional.ofNullable(get(id));
    }


    public List<Category> findByCategoryName(String categoryName) {
        return list(namedTypedQuery("com.inventory.core.Category.findByName").setParameter("name", categoryName));
    }

    public Category create(Category category) {
        return persist(category);
    }

    public List<Category> getAll() {
        return list(namedTypedQuery("com.inventory.core.Category.findAll"));
    }
}
