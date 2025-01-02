package com.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoleTest {

    private Role role;

    @BeforeEach
    void setUp() {
        role = new Role();
    }

    @Test
    void testGetAndSetRoleId() {
        long expectedId = 1L;
        role.setRole_id(expectedId);
        assertEquals(expectedId, role.getRole_id());
    }

    @Test
    void testGetAndSetRoleName() {
        String expectedRoleName = "ADMIN";
        role.setRole_name(expectedRoleName);
        assertEquals(expectedRoleName, role.getRole_name());
    }

    @Test
    void testGetAndSetUser() {
        UserEntity user = new UserEntity(); // Assuming you have a UserEntity class
        role.setUser(user);
        assertEquals(user, role.getUser());
    }

    @Test
    void testConstructorWithParameters() {
        String expectedRoleName = "USER";
        Role roleWithParam = new Role(expectedRoleName);
        assertEquals(expectedRoleName, roleWithParam.getRole_name());
    }
}
