package com.inventory.resources;


import com.inventory.core.Item;
import com.inventory.core.Sale;
import com.inventory.core.User;
import com.inventory.db.ItemDAO;
import com.inventory.db.SaleDAO;
import com.inventory.db.UserDAO;
import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;

import javax.annotation.security.RolesAllowed;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Optional;

@Path("/sale")
@Produces(MediaType.APPLICATION_JSON)
public class SalesResource {
    private final SaleDAO saleDao;
    private final ItemDAO itemDao;
    private final UserDAO userDao;

    public SalesResource(SaleDAO saleDao, ItemDAO itemDao, UserDAO userDao) {
        this.saleDao = saleDao;
        this.itemDao = itemDao;
        this.userDao = userDao;
    }

    @GET
    @UnitOfWork
    @RolesAllowed("ADMIN")
    public List<Sale> getAllSales(@Auth User user) {
        return saleDao.getAll();
    }

    //logic to move to service
    @POST
    @UnitOfWork
    @RolesAllowed("ADMIN")
    public Optional<Sale> sell(@NotNull Sale sale, @Auth User user) {
        Optional<User> dbUser = userDao.findUserByUsername(user.getUsername());
        Optional<Item> item = itemDao.findById(sale.getItemId());
        if (item.isPresent() && item.get().getStock() >= sale.getQuantity()) {
            itemDao.reduceStock(sale.getQuantity(), sale.getItemId());
            dbUser.ifPresent(admin -> sale.setSoldBy(admin.getId()));
            return Optional.ofNullable(saleDao.create(sale));
        } else {
            return Optional.empty();
        }
    }

    @GET
    @UnitOfWork
    @RolesAllowed("ADMIN")
    @Path("/{id}")
    public Optional<Sale> getSalesById(@PathParam("id") Long id, @Auth User user) {
        return saleDao.findById(id);
    }
}
