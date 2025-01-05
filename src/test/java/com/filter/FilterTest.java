package com.filter;
 
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
 
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
 
import java.io.IOException;
 
public class FilterTest {
 
    private JwtFilter jwtFilter;
 
    @Mock
    private FilterChain filterChain;
 
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private final String secretKey = "my-secret-key";
 
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        jwtFilter = new JwtFilter();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        SecurityContextHolder.clearContext();
    }
 
    private String generateToken(String role) {
        return Jwts.builder()
                .setSubject("Test User")
                .claim("role", role)
                .signWith(SignatureAlgorithm.HS256, secretKey.getBytes()) // Match secretKey.getBytes()
                .compact();
    }
 
    
 
    @Test
    public void testFilterWithInvalidToken() throws ServletException, IOException {
        request.addHeader("Authorization", "Bearer invalid_token");
 
        jwtFilter.doFilterInternal(request, response, filterChain);
 
        assertEquals(401, response.getStatus());
        assertEquals("{\"error\": \"Invalid token\"}", response.getContentAsString());
    }
 
    @Test
    public void testFilterWithoutToken() throws ServletException, IOException {
        jwtFilter.doFilterInternal(request, response, filterChain);
 
        assertEquals(401, response.getStatus());
        assertEquals("{\"error\": \"Invalid token\"}", response.getContentAsString());
    }
 
    @Test
    public void testFilterForAuthEndpoint() throws ServletException, IOException {
        request.setRequestURI("/api/auth");
        request.setMethod("POST");
 
        jwtFilter.doFilterInternal(request, response, filterChain);
 
        verify(filterChain, times(1)).doFilter(request, response);
    }
}