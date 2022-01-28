package com.inventory.core;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.ZonedDateTime;

@Entity
@Table(name="category")
@NamedQuery(name="com.inventory.core.Category.findAll",query="SELECT c from Category c")
@NamedQuery(name="com.inventory.core.Category.findByName",query="SELECT c from Category c"+" WHERE c.categoryName like :name")
public class Category {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    @Column(name="category_name",nullable = false)
    private String categoryName;

    @GeneratedValue
    @Column(name="created_at")
    private Timestamp createdAt;

    public Category(){}

    @JsonCreator
    public Category(@JsonProperty("categoryName") String categoryName){
        this.categoryName = categoryName;
        this.createdAt = Timestamp.from(ZonedDateTime.now().toInstant());
    }

    public String getCreatedAt() {
        return createdAt.toString();
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }


}
