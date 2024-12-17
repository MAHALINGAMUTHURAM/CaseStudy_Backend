package com.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dao.UserDAO;
import com.filter.JwtResponse;
import com.filter.JwtToken;
import com.model.AuthenticateUser;
import com.model.Role;

@RestController
@CrossOrigin("*")
public class AuthController {
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private UserDAO userRepository;
    
    @Autowired
    private JwtToken jwtToken;

    // Authentication endpoint
    @PostMapping("/api/auth")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticateUser user) {
        
        // Authenticating the user
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword())
        );

        // If authentication is successful
        if (authentication.isAuthenticated()) {
            // Retrieve user from the database
            var userEntity = userRepository.findByUsername(user.getUserName()).orElse(null);

            if (userEntity != null) {
                // Check if user has the requested role
                List<Role> roleList = userEntity.getRoles();
                for (Role role : roleList) {
                    if (user.getRole().equals(role.getName())) {
                        // Generate JWT token
                        jwtToken.generateToken(user.getUserName(), user.getPassword(), user.getRole());
                        return new ResponseEntity<>(new JwtResponse(jwtToken.getToken()), HttpStatus.OK);
                    }
                }
            }
            
            // If no role is found or authentication fails
            return new ResponseEntity<>("Invalid role or user", HttpStatus.FORBIDDEN);
        }
        
        // If authentication fails
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
}
