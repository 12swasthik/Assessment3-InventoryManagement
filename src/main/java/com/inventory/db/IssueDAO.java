package com.inventory.db;

import com.inventory.core.Issue;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

public class IssueDAO extends AbstractDAO<Issue> {
    public IssueDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Optional<Issue> findById(Long id){
        return Optional.ofNullable(get(id));
    }

    public Issue create(Issue c){
        return persist(c);
    }

    public List<Issue> getAll(){
        return list(namedTypedQuery("com.inventory.core.Issue.findAll"));
    }
}

