package com.inventory.core;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
@Table(name = "item")
@NamedQuery(name = "com.inventory.core.Item.findAll", query = "SELECT i from Item i")
@NamedQuery(name = "com.inventory.core.Item.findByName", query = "SELECT i from Item i" + " WHERE i.itemName like :name")
@NamedQuery(name = "com.inventory.core.Item.increaseItemCount", query = "update Item i set i.stock = i.stock + :quantity where i.id = :id")
@NamedQuery(name = "com.inventory.core.Item.decreaseItemCount", query = "update Item i set i.stock = i.stock - :quantity where i.id = :id")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "item_name", nullable = false)
    private String itemName;

    @Column(name = "category_id", nullable = false)
    private long categoryId;

    @Column(name = "brand")
    private String brand;

    @Column(name = "color")
    private String color;

    @Column(name = "stock")
    private long stock;

    public Item() {
    }

    @JsonCreator
    public Item(@JsonProperty("itemName") String itemName, @JsonProperty("categoryId") long categoryId, @JsonProperty("brand") String brand, @JsonProperty("color") String color) {
        this.itemName = itemName;
        this.categoryId = categoryId;
        this.brand = brand;
        this.color = color;
        this.stock = 0;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setStock(long stock) {
        this.stock = stock;
    }

    public long getId() {
        return id;
    }

    public String getItemName() {
        return itemName;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public String getBrand() {
        return brand;
    }

    public String getColor() {
        return color;
    }

    public long getStock() {
        return stock;
    }

    public Item(long id, String itemName, long categoryId, String brand, String color, long stock) {
        this.id = id;
        this.itemName = itemName;
        this.categoryId = categoryId;
        this.brand = brand;
        this.color = color;
        this.stock = stock;
    }
}
