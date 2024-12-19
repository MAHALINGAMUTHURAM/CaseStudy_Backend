package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.model.UserEntity;
import com.model.Role;
import com.service.UserService;
import com.service.RoleService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
    public class UserController {

        @Autowired
        private UserService userService;

        @Autowired
        private RoleService roleService;

        @Autowired
        private PasswordEncoder passwordEncoder;

        @PostMapping("/user/register")
        public ResponseEntity<?> registerUser(@RequestBody UserEntity user) {

            user.setPassword(passwordEncoder.encode(user.getPassword()));

            List<Role> assignedRoles = new ArrayList<>();

            for (Role role : user.getRoles()) {
            	
                    Role newRole = new Role();
                    newRole.setRole_name(role.getRole_name()); 
                    assignedRoles.add(newRole); 
                    newRole.setUser(user);
                    
            }

            user.setRoles(assignedRoles);

            try {
                userService.saveUser(user);
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body("{\"code\": \"REGISTERSUCCESS\", \"message\": \"User created successfully with selected roles\"}");
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("{\"code\": \"REGISTERFAIL\", \"message\": \"Error creating user\"}");
            }
        }
        
        @PutMapping("/manager/register/{user_id}")
        public ResponseEntity<?> updateUser(@PathVariable Long user_id) {
        	
        	try
        	{
        		UserEntity existingUser=userService.getUserById(user_id);
        		Role role=new Role("ROLE_MANAGER");
        		existingUser.getRoles().add(role);
        		role.setUser(existingUser);
        		roleService.saveRole(role);
                userService.saveUser(existingUser);
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body("{\"code\": \"REGISTERSUCCESS\", \"message\": \"User updated successfully with selected roles\"}");
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("{\"code\": \"REGISTERFAIL\", \"message\": \"Error creating user\"}");
            }
        }
    }
        
    