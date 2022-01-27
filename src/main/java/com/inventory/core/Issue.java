package com.inventory.core;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.ZonedDateTime;

@Entity
@Table(name="issue")
@NamedQuery(name="com.inventory.core.Issue.findAll",query="SELECT c from Issue c")
public class Issue {

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

    @Column(name="customer_name", nullable=false)
    private String customerName;

    @Column(name="customer_details")
    private String customerDetails;

    @Column(name="issued_by",nullable = false)
    private long issuedBy;

    public Issue(){}

    @JsonCreator
    public Issue(@JsonProperty("itemId") long itemId,@JsonProperty("quantity") int quantity,@JsonProperty("customerName") String customerName,@JsonProperty("customerDetails") String customerDetails){
        this.itemId = itemId;
        this.quantity = quantity;
        this.customerName = customerName;
        this.customerDetails = customerDetails;
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

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerDetails() {
        return customerDetails;
    }

    public void setCustomerDetails(String customerDetails) {
        this.customerDetails = customerDetails;
    }

    public long getIssuedBy() {
        return issuedBy;
    }

    public void setIssuedBy(long issuedBy) {
        this.issuedBy = issuedBy;
    }

}