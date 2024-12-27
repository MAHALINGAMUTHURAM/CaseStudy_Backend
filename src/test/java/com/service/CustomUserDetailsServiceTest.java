package com.service;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.hibernate.Hibernate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.dao.UserDAO;
import com.model.Role;
import com.model.UserEntity;
import com.service.CustomUserDetailsService;

class CustomUserDetailsServiceTest {

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    @Mock
    private UserDAO userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoadUserByUsername_UserExists() {
        // Arrange
        Role role = new Role();
        role.setRole_name("ROLE_USER");

        UserEntity user = new UserEntity();
        user.setUsername("testUser");
        user.setPassword("password123");
        user.setRoles(Arrays.asList(role));

        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));

        // Act
        UserDetails userDetails = customUserDetailsService.loadUserByUsername("testUser");

        // Assert
        assertNotNull(userDetails);
        assertEquals("testUser", userDetails.getUsername());
        assertEquals("password123", userDetails.getPassword());
        assertEquals(1, userDetails.getAuthorities().size());
        verify(userRepository, times(1)).findByUsername("testUser");
        assertTrue(Hibernate.isInitialized(user.getRoles()));
    }

    @Test
    void testLoadUserByUsername_UserNotFound() {
        // Arrange
        when(userRepository.findByUsername("nonExistentUser")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UsernameNotFoundException.class, () -> {
            customUserDetailsService.loadUserByUsername("nonExistentUser");
        });
        verify(userRepository, times(1)).findByUsername("nonExistentUser");
    }

    @Test
    void testGetCount() {
        // Arrange
        when(userRepository.count()).thenReturn(5L);

        // Act
        Long count = customUserDetailsService.getCount();

        // Assert
        assertEquals(5L, count);
        verify(userRepository, times(1)).count();
    }
}
