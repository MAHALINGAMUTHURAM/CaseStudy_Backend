package com.dao;



import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.model.UserEntity;

@Repository
public interface UserDAO extends JpaRepository<UserEntity,Integer> {

	Optional<UserEntity> findByUsername(String username);
}