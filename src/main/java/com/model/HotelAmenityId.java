package com.model;

import java.io.Serializable;
import java.util.Objects;

public class HotelAmenityId implements Serializable {

    private long hotel;
    private long amenity;

    public HotelAmenityId() {}

    public HotelAmenityId(long hotel, long amenity) {
        this.hotel = hotel;
        this.amenity = amenity;
    }

    public long getHotel() {
		return hotel;
	}

	public void setHotel(long hotel) {
		this.hotel = hotel;
	}

	public long getAmenity() {
		return amenity;
	}

	public void setAmenity(long amenity) {
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

