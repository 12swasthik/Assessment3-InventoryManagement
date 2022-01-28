package com.inventory.resources;


import com.inventory.core.Item;
import com.inventory.core.User;
import com.inventory.db.ItemDAO;
import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;

import javax.annotation.security.RolesAllowed;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Optional;


@Path("/item")
@Produces(MediaType.APPLICATION_JSON)
public class ItemResource {
    private final ItemDAO itemDao;

    public ItemResource(ItemDAO itemDao) {
        this.itemDao = itemDao;
    }

    @GET
    @UnitOfWork
    public List<Item> getAllItems() {
        return itemDao.getAll();
    }

    @POST
    @UnitOfWork
    @RolesAllowed("ADMIN")
    public Item createItem(@NotNull Item item, @Auth User user) {
        return itemDao.create(item);
    }

    @GET
    @UnitOfWork
    @Path("/{id}")
    public Optional<Item> getItemById(@PathParam("id") Long id) {
        return itemDao.findById(id);
    }

    @GET
    @UnitOfWork
    @Path("/search")
    public List<Item> getItemByName(@QueryParam("query") String itemName) {
        return itemDao.findItemByName(itemName);
    }

}
