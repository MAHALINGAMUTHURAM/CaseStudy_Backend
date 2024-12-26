package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.AreaDAO;
import com.model.Area;
import com.model.RoomType;

@Service
public class AreaService {
	
	@Autowired
	AreaDAO areaDAO;
	
	public List<Area> getAllArea()
	{
		return areaDAO.findAll();
	}
}

