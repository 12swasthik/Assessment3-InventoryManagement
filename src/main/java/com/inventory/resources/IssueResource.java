package com.inventory.resources;


import com.inventory.core.Category;
import com.inventory.core.Issue;
import com.inventory.core.Item;
import com.inventory.db.IssueDAO;
import com.inventory.db.ItemDAO;
import io.dropwizard.hibernate.UnitOfWork;

import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Optional;

@Path("/issue")
@Produces(MediaType.APPLICATION_JSON)
public class IssueResource {
    private final IssueDAO issueDao;
    private final ItemDAO itemDao;
    public IssueResource(IssueDAO issueDao,ItemDAO itemDao){
        this.issueDao = issueDao;
        this.itemDao = itemDao;
    }

    @GET
    @UnitOfWork
    public List<Issue> getAllIssues(){
        return issueDao.getAll();
    }

    @POST
    @UnitOfWork
    public Issue create(@NotNull Issue i){
        itemDao.reduceStock(i.getQuantity(),i.getItemId());
        return issueDao.create(i);
    }

    @GET
    @UnitOfWork
    @Path("/{id}")
    public Optional<Issue> getIssueById(@PathParam("id") Long id){
        return issueDao.findById(id);
    }
}
