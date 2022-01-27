package com.inventory.auth;

import com.inventory.core.User;
import com.inventory.db.UserDAO;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;
import io.dropwizard.hibernate.UnitOfWork;

import java.util.Objects;
import java.util.Optional;

public class InventoryAuthenticator implements Authenticator<BasicCredentials, User>
{
    private final UserDAO userDao;

    public InventoryAuthenticator(UserDAO userDao){
        this.userDao = userDao;
    }

    @Override
    @UnitOfWork
    public Optional<User> authenticate(BasicCredentials credentials) {
        Optional<User> result;
        Optional<User> user = userDao.findUserByUsername(credentials.getUsername());
        if (user.isPresent() && Objects.equals(user.get().getPassword(), credentials.getPassword())) {
            result = user;
        } else {
            result = Optional.empty();
        }
        return result;
    }
}