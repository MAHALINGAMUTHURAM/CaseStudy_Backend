package com.model;

import jakarta.persistence.*;

@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int role_id;
    
    private String role_name;  // Field name 'role_name'
    
    @ManyToOne
    @JoinColumn(name = "user_id")  // This creates a foreign key in the 'role' table
    private UserEntity user;  // This is the user field you referenced in 'UserEntity'

    // Constructors, Getters and Setters
    public Role() {}

    public Role(String role_name) {
        this.role_name = role_name;
    }

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }

    public String getRole_name() {
        return role_name;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}
