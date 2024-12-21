package com.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.model.Amenity;

@Repository
public interface AmenityDAO extends JpaRepository<Amenity, Long> {
	
	public List<Amenity> findByname(String name);

}
