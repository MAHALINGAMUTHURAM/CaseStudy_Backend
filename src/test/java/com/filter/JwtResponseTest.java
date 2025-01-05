package com.filter;
 
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
 
public class JwtResponseTest {
 
    @Test
    public void testJwtResponseConstructorAndGetter() {
        // Arrange: Set up the expected token
        String expectedToken = "my_test_token_123";
 
        // Act: Create an instance of JwtResponse
        JwtResponse jwtResponse = new JwtResponse(expectedToken);
 
        // Assert: Verify that the token returned by the getter matches the expected value
        assertEquals(expectedToken, jwtResponse.getToken(), "The token should match the expected value.");
    }
}