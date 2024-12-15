package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.AmenityDAO;
import com.model.Amenity;



@Service
public class AmenityService {
	@Autowired
	AmenityDAO amenityDAO;
	
	public AmenityService() {
		
	}
	public void add(Amenity amenity)
	{
		amenityDAO.save(amenity);
	}
	public List<Amenity> getAll()
		{
			return amenityDAO.findAll();
		}
	public void update(Amenity amenity)
	{
		amenityDAO.save(amenity);
	}
	public void delete(long id)
	{
		Amenity amenity=amenityDAO.findById(id).get();
		amenityDAO.delete(amenity);
	}
	public boolean find(Amenity amenity) {
		 return amenityDAO.equals(amenity);
	}

}
