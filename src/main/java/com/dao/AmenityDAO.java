package com.dao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.model.Amenity;

@Repository
public interface AmenityDAO extends JpaRepository<Amenity, Long> {
}