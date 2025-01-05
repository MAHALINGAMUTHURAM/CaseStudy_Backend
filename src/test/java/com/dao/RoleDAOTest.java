package com.dao;
 
import com.model.Role;
import com.service.RoleService;
 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
 
import java.util.ArrayList;
import java.util.List;
 
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
 
public class RoleDAOTest {
 
    @Mock
    private RoleDAO roleDAO;
 
    @InjectMocks
    private RoleService roleService; // Assuming you have a service that uses the DAO
 
    private Role role;
    private final String roleName = "Admin"; // Dynamic value for role name
 
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        
        // Initialize Role object
        role = new Role();
        role.setRole_id(1L); // Assuming Role has an ID field
        role.setRole_name(roleName); // Set role name dynamically
    }
 
    @Test
    public void testFindByRoleName() {
        // Given
        List<Role> roles = new ArrayList<>();
        roles.add(role);
        
        when(roleDAO.findByRoleName(roleName)).thenReturn(roles);
 
        // When
        List<Role> foundRoles = roleDAO.findByRoleName(roleName);
 
        // Then
        assertThat(foundRoles).isNotEmpty();
        assertThat(foundRoles.get(0).getRole_name()).isEqualTo(roleName);
 
        // Verify that the method was called once
        verify(roleDAO, times(1)).findByRoleName(roleName);
    }
 
    @Test
    public void testFindByRoleName_NoResults() {
        // Given
        when(roleDAO.findByRoleName(roleName)).thenReturn(new ArrayList<>());
 
        // When
        List<Role> foundRoles = roleDAO.findByRoleName(roleName);
 
        // Then
        assertThat(foundRoles).isEmpty();
 
        // Verify that the method was called once
        verify(roleDAO, times(1)).findByRoleName(roleName);
    }
 
    @Test
    public void testFindRolesByHierarchy() {
        // Given
        List<Role> roles = new ArrayList<>();
        roles.add(role);
        
        when(roleDAO.findRolesByHierarchy(anyList())).thenReturn(roles);
 
        // When
        List<Role> foundRoles = roleDAO.findRolesByHierarchy(List.of("Admin", "Manager", "User"));
 
        // Then
        assertThat(foundRoles).isNotEmpty();
        
        // Verify that the method was called once
        verify(roleDAO, times(1)).findRolesByHierarchy(anyList());
    }
 
    @Test
    public void testFindRolesByHierarchy_NoResults() {
        // Given
        when(roleDAO.findRolesByHierarchy(anyList())).thenReturn(new ArrayList<>());
 
        // When
        List<Role> foundRoles = roleDAO.findRolesByHierarchy(List.of("Admin", "Manager", "User"));
 
        // Then
        assertThat(foundRoles).isEmpty();
 
        // Verify that the method was called once
        verify(roleDAO, times(1)).findRolesByHierarchy(anyList());
    }
}