package com.inventory.resources;


import com.inventory.core.Category;
import com.inventory.db.CategoryDAO;
import io.dropwizard.hibernate.UnitOfWork;

import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Optional;


@Path("/category")
@Produces(MediaType.APPLICATION_JSON)
public class CategoryResource {
    private final CategoryDAO categoryDao;
    public CategoryResource(CategoryDAO categoryDao){
        this.categoryDao = categoryDao;
    }

    @GET
    @UnitOfWork
    public List<Category> getAllCategory(){
        return categoryDao.getAll();
    }

    @POST
    @UnitOfWork
    public Category create(@NotNull Category c){
        return categoryDao.create(c);
    }

    @GET
    @UnitOfWork
    @Path("/{id}")
    public Optional<Category> getCategoryById(@PathParam("id") Long id){
        return categoryDao.findById(id);
    }

    @GET
    @UnitOfWork
    @Path("/search")
    public Optional<Category> getCategoryByName(@QueryParam("query") String name){
        return categoryDao.findByCategoryName(name);
    }

}
