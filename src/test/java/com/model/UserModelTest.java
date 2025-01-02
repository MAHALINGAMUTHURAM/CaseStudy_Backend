package com.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserModelTest {

    private UserEntity userEntity;

    @BeforeEach
    void setUp() {
        userEntity = new UserEntity();
    }

    @Test
    void testSetAndGetUserId() {
        long userId = 1L;
        userEntity.setUser_id(userId);
        assertEquals(userId, userEntity.getUser_id(), "User ID should be set and retrieved correctly");
    }

    @Test
    void testSetAndGetUsername() {
        String username = "testUser";
        userEntity.setUsername(username);
        assertEquals(username, userEntity.getUsername(), "Username should be set and retrieved correctly");
    }

    @Test
    void testSetAndGetPassword() {
        String password = "testPassword";
        userEntity.setPassword(password);
        assertEquals(password, userEntity.getPassword(), "Password should be set and retrieved correctly");
    }

    @Test
    void testSetAndGetRoles() {
        Role role1 = new Role();
        role1.setRole_name("ROLE_ADMIN");
        Role role2 = new Role();
        role2.setRole_name("ROLE_USER");
        List<Role> roles = Arrays.asList(role1, role2);

        userEntity.setRoles(roles);
        assertEquals(roles, userEntity.getRoles(), "Roles should be set and retrieved correctly");
    }

    @Test
    void testConstructorWithArguments() {
        Role role = new Role();
        role.setRole_name("ROLE_ADMIN");
        List<Role> roles = Arrays.asList(role);
        UserEntity user = new UserEntity("testUser", "testPassword", roles);

        assertEquals("testUser", user.getUsername(), "Constructor should set username correctly");
        assertEquals("testPassword", user.getPassword(), "Constructor should set password correctly");
        assertEquals(roles, user.getRoles(), "Constructor should set roles correctly");
    }

    @Test
    void testDefaultConstructor() {
        UserEntity user = new UserEntity();
        assertNull(user.getUsername(), "Default constructor should initialize username as null");
        assertNull(user.getPassword(), "Default constructor should initialize password as null");
        assertNull(user.getRoles(), "Default constructor should initialize roles as null");
    }
}
