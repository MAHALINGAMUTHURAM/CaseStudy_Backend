package com.service;

import com.dao.UserDAO;
import com.model.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserDAO userDAO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveUser() {
        UserEntity user = new UserEntity();
        user.setUser_id(1L);
        userService.saveUser(user);
        verify(userDAO, times(1)).save(user); 
    }

    @Test
    void testFindById_Exists() {
        long id = 1L;
        UserEntity user = new UserEntity();
        user.setUser_id(id); 
        when(userDAO.findById(id)).thenReturn(Optional.of(user));
        boolean exists = userService.findById(id);
        assertTrue(exists);
    }

    @Test
    void testFindById_NotExists() {
        long id = 999L; 
        when(userDAO.findById(id)).thenReturn(Optional.empty());
        boolean exists = userService.findById(id);
        assertFalse(exists);
    }

    @Test
    void testDeleteUser_Success() {
        long id = 1L;
        userService.deleteUser(id);
        verify(userDAO, times(1)).deleteById(id); 
    }

    @Test
    void testGetUserById_Success() {
        // Arrange
        long id = 1L;
        UserEntity user = new UserEntity();
        user.setUser_id(id);
        when(userDAO.findById(id)).thenReturn(Optional.of(user));
        UserEntity result = userService.getUserById(id);
        assertNotNull(result);
        assertEquals(id, result.getUser_id()); 
    }

    @Test
    void testGetUserById_NotFound() {
        long id = 999L; 
        when(userDAO.findById(id)).thenReturn(Optional.empty());
       Exception exception = assertThrows(NoSuchElementException.class, () -> {
           userService.getUserById(id);
       });

       assertEquals("No value present", exception.getMessage()); 
   }
}
