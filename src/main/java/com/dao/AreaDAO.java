package com.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.model.Area;

@Repository
public interface AreaDAO extends JpaRepository<Area,Integer> {

}
