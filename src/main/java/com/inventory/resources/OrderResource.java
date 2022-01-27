package com.inventory.resources;


import com.inventory.core.Issue;
import com.inventory.core.Order;
import com.inventory.db.IssueDAO;
import com.inventory.db.ItemDAO;
import com.inventory.db.OrderDAO;
import io.dropwizard.hibernate.UnitOfWork;

import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Optional;

@Path("/order")
@Produces(MediaType.APPLICATION_JSON)
public class OrderResource {
    private final OrderDAO orderDao;
    private final ItemDAO itemDao;
    public OrderResource(OrderDAO orderDao,ItemDAO itemDao){
        this.orderDao = orderDao;
        this.itemDao = itemDao;
    }

    @GET
    @UnitOfWork
    public List<Order> getAllIssues(){
        return orderDao.getAll();
    }

    @POST
    @UnitOfWork
    public Order create(@NotNull Order i){
        itemDao.addStock(i.getQuantity(),i.getItemId());
        return orderDao.create(i);
    }

    @GET
    @UnitOfWork
    @Path("/{id}")
    public Optional<Order> getIssueById(@PathParam("id") Long id){
        return orderDao.findById(id);
    }
}
