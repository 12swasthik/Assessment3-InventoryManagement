package com.inventory;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class InventoryConfiguration extends Configuration {

    @NotNull
    @Valid
    private DataSourceFactory dataFactory = new DataSourceFactory();

    @JsonProperty("database")
    public void setDataFactory(DataSourceFactory db) {
        this.dataFactory = db;
    }

    @JsonProperty("database")
    public DataSourceFactory getDataFactory() {
        return this.dataFactory;
    }
}
