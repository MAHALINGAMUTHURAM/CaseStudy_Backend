package com.model;

import java.io.Serializable;
import java.util.Objects;

public class RoomAmenityId implements Serializable {

    private int room;
    private int amenity;

    public RoomAmenityId() {}

    public RoomAmenityId(int room, int amenity) {
        this.room = room;
        this.amenity = amenity;
    }


    public int getRoom() {
		return room;
	}

	public void setRoom(int room) {
		this.room = room;
	}

	public int getAmenity() {
		return amenity;
	}

	public void setAmenity(int amenity) {
		this.amenity = amenity;
	}

	@Override
    public int hashCode() {
        return Objects.hash(room, amenity);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) 
        	return true;
        if (o == null || getClass() != o.getClass()) 
        	return false;
        RoomAmenityId that = (RoomAmenityId) o;
        return Objects.equals(room, that.room) && Objects.equals(amenity, that.amenity);
    }
}

