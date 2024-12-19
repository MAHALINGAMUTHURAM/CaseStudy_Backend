package com.model;

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
	private int room_number;
	
	@ManyToOne
	@JoinColumn(name="room_type_id")
	private RoomType roomtype;
	 @Column(nullable = false)
	private String location;
	 @Column(nullable = false)
	private boolean isAvailable;
	
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

	public int getRoom_number() {
		return room_number;
	}

	public void setRoom_number(int room_number) {
		this.room_number = room_number;
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
