package com.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Room {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long roomId;
	@Column(unique = true, nullable = false)
	private int roomNumber;
	
	@ManyToOne
	@JoinColumn(name="room_type_id")
	private RoomType roomtype;
	
	 @Column(nullable = false)
	private String location;
	 
	 @Column(nullable = false)
	private boolean isAvailable;
	 
	 @ManyToOne
	 @JoinColumn(name="hotel_id")
	 private Hotel hotel;
	
	public Hotel getHotel() {
		return hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public long getRoomId() {
		return roomId;
	}

	public void setRoomId(long roomId) {
		this.roomId = roomId;
	}

	public int getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(int roomNumber) {
		this.roomNumber = roomNumber;
	}

	public RoomType getRoomtype() {
		return roomtype;
	}

	public void setRoomtype(RoomType roomtype) {
		this.roomtype = roomtype;
	}

	public boolean isAvailable() {
	    return isAvailable;
	}

	public void setAvailable(boolean available) {
	    this.isAvailable = available;
	}
	
	
	

}