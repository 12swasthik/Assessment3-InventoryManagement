package com.inventory;

import com.inventory.auth.InventoryAuthenticator;
import com.inventory.auth.InventoryAuthorizer;
import com.inventory.core.*;
import com.inventory.db.*;
import com.inventory.resources.*;
import io.dropwizard.Application;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.db.PooledDataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.hibernate.UnitOfWorkAwareProxyFactory;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

public class InventoryApplication extends Application<InventoryConfiguration> {

    public static void main(String[] args) throws Exception {
        new InventoryApplication().run(args);
    }

    private final HibernateBundle<InventoryConfiguration> hibernateBundle
            = new HibernateBundle<InventoryConfiguration>(User.class, Category.class, Sale.class, Item.class, Purchase.class
    ) {
        @Override
        public PooledDataSourceFactory getDataSourceFactory(InventoryConfiguration inventoryConfiguration) {
            return inventoryConfiguration.getDataFactory();
        }
    };

    private final MigrationsBundle<InventoryConfiguration> migrationsBundle = new MigrationsBundle<InventoryConfiguration>() {
        @Override
        public DataSourceFactory getDataSourceFactory(InventoryConfiguration inventoryConfiguration) {
            return inventoryConfiguration.getDataFactory();
        }
    };


    @Override
    public void initialize(Bootstrap<InventoryConfiguration> bootstrap) {
        bootstrap.addBundle(hibernateBundle);
        bootstrap.addBundle(migrationsBundle);
    }

    @Override
    public void run(InventoryConfiguration configuration, Environment environment) {
        final CategoryDAO categoryDao = new CategoryDAO(hibernateBundle.getSessionFactory());
        final ItemDAO itemDao = new ItemDAO(hibernateBundle.getSessionFactory());
        final UserDAO userDao = new UserDAO(hibernateBundle.getSessionFactory());
        final SaleDAO saleDao = new SaleDAO(hibernateBundle.getSessionFactory());
        final PurchaseDAO purchaseDao = new PurchaseDAO(hibernateBundle.getSessionFactory());


        environment.jersey().register(itemDao);
        environment.jersey().register(categoryDao);
        environment.jersey().register(userDao);
        environment.jersey().register(saleDao);
        environment.jersey().register(purchaseDao);

        UnitOfWorkAwareProxyFactory factory = new UnitOfWorkAwareProxyFactory(hibernateBundle);
        InventoryAuthenticator proxy = factory.create(InventoryAuthenticator.class, UserDAO.class, userDao);
        environment.jersey().register(new AuthDynamicFeature(new BasicCredentialAuthFilter.Builder<User>()
                .setAuthenticator(proxy)
                .setAuthorizer(new InventoryAuthorizer())
                .setRealm("BASIC-AUTH")
                .buildAuthFilter()));
        environment.jersey().register(new AuthValueFactoryProvider.Binder<>(User.class));
        environment.jersey().register(RolesAllowedDynamicFeature.class);


        environment.jersey().register(new UserResource(userDao));
        environment.jersey().register(new ItemResource(itemDao));
        environment.jersey().register(new CategoryResource(categoryDao));
        environment.jersey().register(new SalesResource(saleDao, itemDao, userDao));
        environment.jersey().register(new PurchaseResource(purchaseDao, itemDao, userDao));
    }
}
