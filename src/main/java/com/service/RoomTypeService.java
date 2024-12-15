package com.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.RoomTypeDAO;
import com.model.RoomType;

@Service
public class RoomTypeService {
	@Autowired
	RoomTypeDAO roomtypeDAO;
	public RoomTypeService()
	{
		
	}
	public void add(RoomType roomtype)
	{
		roomtypeDAO.save(roomtype);
	}
	public List<RoomType> getAll()
	{
		return roomtypeDAO.findAll();
		
	}
	public void update(RoomType roomtype)
	{
		roomtypeDAO.save(roomtype);
	}
	public void delete(Long id)
	{
		RoomType roomtype=roomtypeDAO.findById(id).get();
		roomtypeDAO.delete(roomtype);
	}
	public void save(RoomType roomtype) {
	    roomtypeDAO.save(roomtype);
	}
	

}