package com.model;

import java.util.List;

import jakarta.persistence.*;

@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long role_id;

    private String name;
   
    @OneToMany(mappedBy = "role", fetch = FetchType.EAGER,cascade=CascadeType.ALL)
    private List<UserEntity> userEntity;
   

	public List<UserEntity> getUserEntity() {
		return userEntity;
	}

	public void setUserEntity(List<UserEntity> userEntity) {
		this.userEntity = userEntity;
	}

	public Long getId() {
		return role_id;
	}

	public void setId(Long role_id) {
		this.role_id = role_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Role(String name) {
		super();
		this.name = name;
	}
	
	

 public Role() {}
    
}