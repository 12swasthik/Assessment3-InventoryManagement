package com.inventory.resources;


import com.inventory.core.Item;
import com.inventory.core.Purchase;
import com.inventory.core.User;
import com.inventory.db.ItemDAO;
import com.inventory.db.PurchaseDAO;
import com.inventory.db.UserDAO;
import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;

import javax.annotation.security.RolesAllowed;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Optional;

@Path("/orders")
@Produces(MediaType.APPLICATION_JSON)
public class PurchaseResource {
    private final PurchaseDAO purchaseDao;
    private final ItemDAO itemDao;
    private final UserDAO userDao;

    public PurchaseResource(PurchaseDAO purchaseDao, ItemDAO itemDao, UserDAO userDao) {
        this.purchaseDao = purchaseDao;
        this.itemDao = itemDao;
        this.userDao = userDao;
    }

    @GET
    @UnitOfWork
    @RolesAllowed("ADMIN")
    public List<Purchase> getAllPurchases(@Auth User user) {
        return purchaseDao.getAll();
    }

    //logic to move to service
    @POST
    @UnitOfWork
    @RolesAllowed("ADMIN")
    public Optional<Purchase> purchase(@NotNull Purchase purchase, @Auth User user) {
        Optional<User> dbUser = userDao.findUserByUsername(user.getUsername());
        Optional<Item> item = itemDao.findById(purchase.getItemId());
        if (item.isPresent()) {
            itemDao.addStock(purchase.getQuantity(), purchase.getItemId());
            dbUser.ifPresent(value -> purchase.setPurchasedBy(value.getId()));
            return Optional.ofNullable(purchaseDao.create(purchase));
        } else {
            return Optional.empty();
        }
    }

    @GET
    @UnitOfWork
    @RolesAllowed("ADMIN")
    @Path("/{id}")
    public Optional<Purchase> getPurchaseById(@PathParam("id") Long id, @Auth User user) {
        return purchaseDao.findById(id);
    }
}
