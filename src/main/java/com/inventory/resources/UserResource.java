package com.inventory.resources;


import com.inventory.db.UserDAO;
import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Optional;

import com.inventory.core.User;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {
    private final UserDAO userDao;
    public UserResource(UserDAO userDao){
        this.userDao = userDao;
    }

    @GET
    @UnitOfWork
    @PermitAll
    public List<User> getAllUsers(@Auth User user){
        return userDao.getAll();
    }

    @POST
    @UnitOfWork
    @DenyAll
    public User create(@NotNull User c){
        return userDao.create(c);
    }

    @GET
    @UnitOfWork
    @Path("/{username}")
    @RolesAllowed("ADMIN")
    public Optional<User> findByUsername(@PathParam("username") String username, @Auth User user){
        return userDao.findUserByUsername(username);
    }

}
