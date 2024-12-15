package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.model.UserEntity;
import com.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@RequestBody UserEntity user)
	{
		userService.saveUser(user);
		return new ResponseEntity<>("user created",HttpStatus.OK);
	}
	
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<Object> deleteUser(@PathVariable("userId") Long userId) {
        if (!userService.findById(userId)) { 
            return ResponseEntity.status(404).body("{\"code\": \"DLTFAILS\", \"message\": \"User doesn't exist\"}");
        }
        userService.deleteUser(userId);
        return ResponseEntity.ok("{\"code\": \"DELETESUCCESS\", \"message\": \"User deleted successfully\"}");
    }
}
