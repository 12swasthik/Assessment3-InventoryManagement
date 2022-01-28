package com.inventory.core;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.inventory.utils.Hashing;

import javax.persistence.*;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.Locale;

@Entity
@Table(name = "user")

@NamedQuery(name = "com.inventory.core.User.findAll", query = "SELECT u from User u")
@NamedQuery(name = "com.inventory.core.User.findByUsername", query = "SELECT u from User u" + " WHERE u.username like :name")
public class User implements Principal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "role")
    private String role;

    @GeneratedValue
    @Column(name = "created_at")
    private Timestamp createdAt;

    public User() {
    }

    @JsonCreator
    public User(@JsonProperty("username") String username, @JsonProperty("password") String password, @JsonProperty("role") String role) throws NoSuchAlgorithmException {
        this.username = username.toLowerCase(Locale.ROOT);
        this.password = Hashing.getInstance().getHash(password);
        this.role = role.toUpperCase(Locale.ROOT);
        this.createdAt = Timestamp.from(ZonedDateTime.now().toInstant());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username.toLowerCase(Locale.ROOT);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getCreatedAt() {
        return createdAt.toString();
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String getName() {
        return this.username;
    }
}
