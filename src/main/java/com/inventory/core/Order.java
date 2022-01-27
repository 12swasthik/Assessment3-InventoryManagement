package com.inventory.core;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.ZonedDateTime;

@Entity
@Table(name="order")
@NamedQuery(name="com.inventory.core.Order.findAll",query="SELECT c from Order c")
public class Order {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    @Column(name="item_id",nullable = false)
    private long itemId;

    @Column(name="quantity",nullable = false)
    private int quantity;

    @GeneratedValue
    @Column(name="created_at")
    private Timestamp createdAt;


    @Column(name="ordered_by",nullable = false)
    private long orderedBy;

    public Order(){}

    @JsonCreator
    public Order(@JsonProperty("itemId") long itemId, @JsonProperty("quantity") int quantity){
        this.itemId = itemId;
        this.quantity = quantity;
        this.createdAt = Timestamp.from(ZonedDateTime.now().toInstant());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getCreatedAt() {
        return createdAt.toString();
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public long getOrderedBy() {
        return orderedBy;
    }

    public void setOrderedBy(long orderedBy) {
        this.orderedBy = orderedBy;
    }
}
