package com.model;

import java.io.Serializable;
import java.util.Objects;

public class RoomAmenityId implements Serializable {

    private long room;
    private long amenity;

    public RoomAmenityId() {}

    public RoomAmenityId(long room, long amenity) {
        this.room = room;
        this.amenity = amenity;
    }


    public long getRoom() {
		return room;
	}

	public void setRoom(long room) {
		this.room = room;
	}

	public long getAmenity() {
		return amenity;
	}

	public void setAmenity(long amenity) {
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

