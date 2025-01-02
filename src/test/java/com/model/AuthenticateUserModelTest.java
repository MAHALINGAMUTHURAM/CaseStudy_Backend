package com.model;



import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AuthenticateUserModelTest {

    private AuthenticateUser authenticateUser;

    @BeforeEach
    void setUp() {
        // Initialize a new AuthenticateUser object before each test
        authenticateUser = new AuthenticateUser();
    }

    @Test
    void testUserNameGetterAndSetter() {
        String userName = "testUser";
        authenticateUser.setUserName(userName);
        assertEquals(userName, authenticateUser.getUserName(), "UserName should match the value set.");
    }

    @Test
    void testPasswordGetterAndSetter() {
        String password = "password123";
        authenticateUser.setPassword(password);
        assertEquals(password, authenticateUser.getPassword(), "Password should match the value set.");
    }

    @Test
    void testRoleGetterAndSetter() {
        String role = "ADMIN";
        authenticateUser.setRole(role);
        assertEquals(role, authenticateUser.getRole(), "Role should match the value set.");
    }
}
