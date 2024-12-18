package com.service;

import java.util.ArrayList;
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
	List<Amenity> amenityList;
	
	public AmenityService() 
	{
		amenityList=new ArrayList<>();
	}
	
	public List<Amenity> getAllAmenities()
	{
		return amenityDAO.findAll();
	}
	
	  public void updateAmenity(Long id,Amenity updatedAmenity) {
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
	public void deleteAmenity(Long id)
	{
		Amenity amenity=amenityDAO.findById(id).get();
		amenityDAO.delete(amenity);
	}
	
	
    public boolean findAmenity(Amenity amenity) {
       if(amenityList.contains(amenity))
       {
    	   return true;
       }
       return false;
    }
    public void saveAmenity(Amenity amenity) {
		amenityList.add(amenity);
	    amenityDAO.save(amenity);
	}
    public Amenity getAmenityById(long id)
    {
    	return amenityDAO.findById(id).get();
    }
    
    public boolean findById(long id) {
        return amenityDAO.findById(id).isPresent();
    }
    public boolean existsById(Long id) {
        return amenityDAO.existsById(id);
    }
}

