package com.filter;
 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
 
import static org.junit.jupiter.api.Assertions.*;
 
public class JwtTokenTest {
 
    private JwtToken jwtToken;
 
    @BeforeEach
    public void setUp() {
        // Initialize the JwtToken object before each test
        jwtToken = new JwtToken();
    }
 
    @Test
    public void testGenerateToken() {
        // Arrange
        String userName = "testUser";
        String password = "testPass";
        String role = "admin";
 
        // Act
        jwtToken.generateToken(userName, password, role);
        String generatedToken = jwtToken.getToken();
 
        // Assert
        assertNotNull(generatedToken, "Token should not be null after generation.");
        assertTrue(generatedToken.length() > 0, "Token should have a non-zero length.");
    }
 
    @Test
    public void testValidateWithValidToken() {
        // Arrange
        String userName = "testUser";
        String password = "testPass";
        String role = "admin";
        jwtToken.generateToken(userName, password, role);
        String validToken = jwtToken.getToken();
 
        // Act
        boolean isValid = jwtToken.validate(validToken);
 
        // Assert
        assertTrue(isValid, "The token should be valid.");
    }
 
    @Test
    public void testValidateWithInvalidToken() {
        // Arrange
        String invalidToken = "invalidToken";
 
        // Act
        boolean isValid = jwtToken.validate(invalidToken);
 
        // Assert
        assertFalse(isValid, "The token should be invalid.");
    }
 
    @Test
    public void testGetSecretKey() {
        // Act
        String secretKey = jwtToken.getSecretKey();
 
        // Assert
        assertEquals("##XXXAACCCRR#123###AAA", secretKey, "The secret key should match the predefined value.");
    }
}
