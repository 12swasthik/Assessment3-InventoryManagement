package com.inventory.resources;


import com.inventory.core.Item;
import com.inventory.db.ItemDAO;
import io.dropwizard.hibernate.UnitOfWork;

import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Optional;


@Path("/item")
@Produces(MediaType.APPLICATION_JSON)
public class ItemResource{
    private final ItemDAO itemDao;
    public ItemResource(ItemDAO itemDao){
        this.itemDao = itemDao;
    }

    @GET
    @UnitOfWork
    public List<Item> getAllCategory(){
        return itemDao.getAll();
    }

    @POST
    @UnitOfWork
    public Item create(@NotNull Item i){
        return itemDao.create(i);
    }

    @GET
    @UnitOfWork
    @Path("/{id}")
    public Optional<Item> getCategoryById(@PathParam("id") Long id){
        return itemDao.findById(id);
    }

    @GET
    @UnitOfWork
    @Path("/search")
    public Optional<Item> getItemByName(@QueryParam("query") String name){
        return itemDao.findItemByName(name);
    }

}
