package com.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.AmenityDAO;

import com.model.Amenity;


@Service
public class AmenityService {
	
	@Autowired
	AmenityDAO amenityDAO;
	
	
	public List<Amenity> getAllAmenities()
	{
		return amenityDAO.findAll();
	}
	
	public void updateAmenity(long id,Amenity updatedAmenity) {
	    Optional<Amenity> existingAmenity = amenityDAO.findById(id);
	    if (existingAmenity.isPresent()) {
	         Amenity amenityToUpdate = existingAmenity.get();
	            
	         amenityToUpdate.setName(updatedAmenity.getName());
	         amenityToUpdate.setDescription(updatedAmenity.getDescription());
	         amenityDAO.save(amenityToUpdate);
	         
	        } else {
	            throw new IllegalArgumentException("Amenity not found for ID: " + id);
	        }
	    }
	  
	public void deleteAmenity(long id)
	{
		Amenity amenity=amenityDAO.findById(id).get();
		amenityDAO.delete(amenity);
	}
    
    public void saveAmenity(Amenity amenity) {
	    amenityDAO.save(amenity);
	}
    
    public Amenity getAmenityById(long id)
    {
    	return amenityDAO.findById(id).get();
    }
    
    public boolean existsById(long id) {
        return amenityDAO.existsById(id);
    }
    
    public boolean findByAmenityname(String name)
    {
    	if(amenityDAO.findByname(name).isEmpty())
    	{
    		return false;
    	}
    	return true;
    }
}

