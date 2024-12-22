package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.model.RoomType;
import com.service.RoomTypeService;
import com.exception.CustomException;
import com.exception.Response;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/RoomType")
public class RoomTypeController {

    @Autowired
    private RoomTypeService roomTypeService;

    @PostMapping("/post")
    public ResponseEntity<Object> createRoomType(@RequestBody RoomType roomType) throws CustomException{

            if (roomTypeService.findRoomType(roomType)) {
                throw new CustomException("ADDFAILS", "RoomType already exists");
            }
            roomTypeService.saveRoomType(roomType);
            return ResponseEntity.status(201).body(new Response("POSTSUCCESS","RoomType added successfully"));

    }

    @GetMapping("/{RoomType_id}")
    public ResponseEntity<Object> getRoomTypeById(@PathVariable("RoomType_id") Long roomTypeId) throws CustomException{

            if (!roomTypeService.existsById(roomTypeId)) {
                throw new CustomException("GETFAILS", "RoomType doesn't exist");
            }
            return ResponseEntity.ok(roomTypeService.getRoomTypeById(roomTypeId));

    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAllRoomTypes() throws CustomException{

            List<RoomType> roomTypes = roomTypeService.getAllRoomType();
            if (roomTypes.isEmpty()) {
                throw new CustomException("GETFAILS", "RoomTypes list is empty");
            }
            return ResponseEntity.ok(roomTypes);

    }

    @DeleteMapping("/delete/{RoomType_id}")
    public ResponseEntity<Object> deleteRoomType(@PathVariable("RoomType_id") Long roomTypeId) throws CustomException{

            if (!roomTypeService.existsById(roomTypeId)) {
                throw new CustomException("DLTFAILS", "RoomType doesn't exist");
            }
            roomTypeService.deleteRoomType(roomTypeId);
            return ResponseEntity.ok(new Response("DELETESUCCESS","RoomType deleted successfully"));


    }

    @PutMapping("/update/{roomTypeId}")
    public ResponseEntity<Object> updateRoomType(@PathVariable("roomTypeId") Long roomTypeId, @RequestBody RoomType roomType) throws CustomException{

            if (!roomTypeService.existsById(roomTypeId)) {
                throw new CustomException("UPDTFAILS", "RoomType doesn't exist");
            }
            roomTypeService.updateRoomType(roomTypeId, roomType);
            return ResponseEntity.ok(new Response("UPDATESUCCESS","RoomType updated successfully"));


}
}
