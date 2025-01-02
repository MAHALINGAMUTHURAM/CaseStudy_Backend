package com.dao;

import com.model.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class RoleDAOTest {

    @Mock
    private RoleDAO roleDAO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        
        // Sample Role objects
        Role adminRole = new Role("ADMIN");
        Role userRole = new Role("USER");

        // Mocking the behavior of RoleDAO methods
        when(roleDAO.findByRoleName("ADMIN")).thenReturn(adminRole);
        when(roleDAO.findByRoleName("USER")).thenReturn(userRole);
        when(roleDAO.findByRoleName("GUEST")).thenReturn(null); // No role for GUEST
    }
    @Test
    public void testFindByRoleName_Admin() {
        Role role = roleDAO.findByRoleName("ADMIN");
        assertThat(role).isNotNull(); // Ensure the role is found
      //  assertThat(role.getRole_id()).isEqualTo("ADMIN"); // Check the role name
    }

    @Test
    public void testFindByRoleName_User() {
        Role role = roleDAO.findByRoleName("USER");
        assertThat(role).isNotNull(); // Ensure the role is found
    //    assertThat(role.getRole_id()).isEqualTo("USER"); // Check the role name
    }

    @Test
    public void testFindByRoleName_Guest() {
        Role role = roleDAO.findByRoleName("GUEST");
      //  assertThat(role).isNull(); // Ensure no role is found for GUEST
    }
}
