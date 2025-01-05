package com.service;
 
import com.dao.RoleDAO;
import com.model.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
 
import java.util.ArrayList;
import java.util.List;
 
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
 
class RoleServiceTest {
 
    @Mock
    private RoleDAO roleRepository;
 
    @InjectMocks
    private RoleService roleService;
 
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
 
    @Test
    void testFindByRoleName() {
        String roleName = "USER";
        Role role = new Role();
        role.setRole_id(1L);
        role.setRole_name(roleName);
 
        when(roleRepository.findByRoleName(roleName)).thenReturn(List.of(role));
 
        List<Role> result = roleService.findByRoleName(roleName);
 
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(roleName, result.get(0).getRole_name());
        verify(roleRepository, times(1)).findByRoleName(roleName);
    }
 
    @Test
    void testFindRolesByRoleName() {
        String roleName = "ADMIN";
        Role role = new Role();
        role.setRole_id(2L);
        role.setRole_name(roleName);
 
        when(roleRepository.findByRoleName(roleName)).thenReturn(List.of(role));
 
        List<Role> result = roleService.findRolesByRoleName(roleName);
 
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(roleName, result.get(0).getRole_name());
        verify(roleRepository, times(1)).findByRoleName(roleName);
    }
 
    @Test
    void testSaveRole() {
        Role newRole = new Role();
        newRole.setRole_id(3L);
        newRole.setRole_name("MANAGER");
 
        // Act
        roleService.saveRole(newRole);
 
        // Verify that save was called on the repository
        verify(roleRepository, times(1)).save(newRole);
    }
 
    @Test
    void testExistsByRoleName_WhenExists() {
        String roleName = "USER";
        Role existingRole = new Role();
        existingRole.setRole_id(1L);
        existingRole.setRole_name(roleName);
 
        when(roleRepository.findByRoleName(roleName)).thenReturn(List.of(existingRole));
 
        boolean exists = roleService.existsByRoleName(roleName);
 
        assertTrue(exists);
        verify(roleRepository, times(1)).findByRoleName(roleName);
    }
 
    @Test
    void testExistsByRoleName_WhenDoesNotExist() {
        String roleName = "GUEST";
 
        when(roleRepository.findByRoleName(roleName)).thenReturn(new ArrayList<>());
 
        boolean exists = roleService.existsByRoleName(roleName);
 
        assertTrue(exists);
        verify(roleRepository, times(1)).findByRoleName(roleName);
    }
}