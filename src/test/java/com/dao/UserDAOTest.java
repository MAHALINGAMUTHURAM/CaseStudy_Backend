package com.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import com.model.UserEntity;
import com.service.UserService; // Assuming you have a service that uses the DAO

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

public class UserDAOTest {

    @Mock
    private UserDAO userDAO;

    @InjectMocks
    private UserService userService; // Assuming you have a service that uses the DAO

    private UserEntity userEntity;
    private final String username = "testuser"; // Dynamic value for username

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        
        // Initialize UserEntity object
        userEntity = new UserEntity();
        userEntity.setUser_id(1L); // Assuming UserEntity has an ID field
        userEntity.setUsername(username); // Set username dynamically
        // Set other properties as needed
    }

    @Test
    public void testFindByUsername() {
        // Given
        when(userDAO.findByUsername(username)).thenReturn(Optional.of(userEntity));

        // When
        Optional<UserEntity> foundUser = userDAO.findByUsername(username);

        // Then
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getUsername()).isEqualTo(username);

        // Verify that the method was called once
        verify(userDAO, times(1)).findByUsername(username);
    }

    @Test
    public void testFindByUsername_NoResults() {
        // Given
        when(userDAO.findByUsername(username)).thenReturn(Optional.empty());

        // When
        Optional<UserEntity> foundUser = userDAO.findByUsername(username);

        // Then
        assertThat(foundUser).isNotPresent();

        // Verify that the method was called once
        verify(userDAO, times(1)).findByUsername(username);
    }
}
