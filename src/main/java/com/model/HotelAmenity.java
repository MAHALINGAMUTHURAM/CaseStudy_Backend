package com.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
@IdClass(HotelAmenityId.class) 
public class HotelAmenity {

    @Id
    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    @Id
    @ManyToOne
    @JoinColumn(name = "amenity_id")
    
    private Amenity amenity;

	public Hotel getHotel() {
		return hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}

	public Amenity getAmenity() {
		return amenity;
	}

	public void setAmenity(Amenity amenity) {
		this.amenity = amenity;
	}
    
    
}
