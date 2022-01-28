package com.inventory.auth;

import com.inventory.core.User;
import io.dropwizard.auth.Authorizer;

public class InventoryAuthorizer implements Authorizer<User> {
    @Override
    public boolean authorize(User user, String role) {
        return user.getRole() != null && user.getRole().contains(role);
    }
}