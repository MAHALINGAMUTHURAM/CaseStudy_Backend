package com.model;
 
 
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
 
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
 
@Entity
public class UserEntity {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;
    
    @Column(unique=true,nullable=false)
    private String username;
    @Column(nullable = false)
    private String password;
    
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY,cascade=CascadeType.ALL)
    @JsonIgnore
    private List<Role> roles;
    
	public UserEntity(String username, String password,List<Role> role) {
		super();
		this.username = username;
		this.password = password;
		this.roles = role;
	}
	
	public List<Role> getRoles() {
		return roles;
	}
    
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
    
    
	public long getUser_id() {
		return userId;
	}
 
	public void setUser_id(long user_id) {
		this.userId = user_id;
	}
 
	public String getUsername() {
		return username;
	}
    
	public void setUsername(String username) {
		this.username = username;
	}
    
	public String getPassword() {
		return password;
	}
 
	public void setPassword(String password) {
		this.password = password;
	}
 
	
   public UserEntity() {}
   
}