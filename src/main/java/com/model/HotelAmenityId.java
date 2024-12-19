package com.model;

import java.io.Serializable;
import java.util.Objects;

public class HotelAmenityId implements Serializable {

    private int hotel;
    private int amenity;

    public HotelAmenityId() {}

    public HotelAmenityId(int hotel, int amenity) {
        this.hotel = hotel;
        this.amenity = amenity;
    }

    public int getHotel() {
		return hotel;
	}

	public void setHotel(int hotel) {
		this.hotel = hotel;
	}

	public int getAmenity() {
		return amenity;
	}

	public void setAmenity(int amenity) {
		this.amenity = amenity;
	}

	@Override
    public int hashCode() {
        return Objects.hash(hotel, amenity);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) 
        	return false;
        HotelAmenityId that = (HotelAmenityId) o;
        return Objects.equals(hotel, that.hotel) && Objects.equals(amenity, that.amenity);
    }
}

