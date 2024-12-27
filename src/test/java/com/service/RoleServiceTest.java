package com.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
 
import com.dao.RoleDAO;
import com.model.Role;
import com.service.RoleService;
 
class RoleServiceTest {
 
    @InjectMocks
    private RoleService roleService;
 
    @Mock
    private RoleDAO roleRepository;
 
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
 
    @Test
    void testFindByRoleName() {
        Role role = new Role();
        role.setRole_name("ADMIN");
 
        when(roleRepository.findByRoleName("ADMIN")).thenReturn(role);
 
        Role result = roleService.findByRoleName("ADMIN");
 
        assertNotNull(result);
        assertEquals("ADMIN", result.getRole_name());
        verify(roleRepository, times(1)).findByRoleName("ADMIN");
    }
 
    @Test
    void testSaveRole() {
        Role role = new Role();
        role.setRole_name("USER");
 
        roleService.saveRole(role);
 
        verify(roleRepository, times(1)).save(role);
    }
 
    @Test
    void testExistsByRoleName() {
        when(roleRepository.findByRoleName("MANAGER")).thenReturn(new Role());
 
        boolean result = roleService.existsByRoleName("MANAGER");
 
        assertTrue(result);
        verify(roleRepository, times(1)).findByRoleName("MANAGER");
    }
 
    @Test
    void testExistsByRoleNameWhenRoleNotFound() {
        when(roleRepository.findByRoleName("GUEST")).thenReturn(null);
 
        boolean result = roleService.existsByRoleName("GUEST");
 
        assertFalse(result);
        verify(roleRepository, times(1)).findByRoleName("GUEST");
    }
}