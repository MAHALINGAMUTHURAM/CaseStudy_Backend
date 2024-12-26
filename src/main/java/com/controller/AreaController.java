package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exception.CustomException;
import com.model.Area;
import com.service.AreaService;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/area")
public class AreaController {
	
	@Autowired
	AreaService areaService;
	
    @GetMapping("/all")
    public ResponseEntity<Object> getAllRoomTypes() throws CustomException{

            List<Area> areaList = areaService.getAllArea();
            if (areaList.isEmpty()) {
                throw new CustomException("GETFAILS", "Area list is empty");
            }
            return ResponseEntity.ok(areaList);

    }

}
