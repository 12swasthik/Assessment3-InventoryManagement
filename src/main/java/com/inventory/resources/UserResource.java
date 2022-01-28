package com.inventory.resources;


import com.inventory.core.User;
import com.inventory.db.UserDAO;
import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;

import javax.annotation.security.RolesAllowed;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Optional;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {
    private final UserDAO userDao;

    public UserResource(UserDAO userDao) {
        this.userDao = userDao;
    }

    @GET
    @UnitOfWork
    @RolesAllowed("ADMIN")
    public List<User> getAllUsers(@Auth User user) {
        return userDao.getAll();
    }

    @POST
    @UnitOfWork
    public User createUser(@NotNull User user) {
        return userDao.create(user);
    }

    @GET
    @UnitOfWork
    @Path("/{username}")
    @RolesAllowed("ADMIN")
    public Optional<User> findByUsername(@PathParam("username") String username, @Auth User user) {
        return userDao.findUserByUsername(username);
    }

}
