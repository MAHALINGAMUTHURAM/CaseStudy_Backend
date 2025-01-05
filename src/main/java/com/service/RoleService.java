package com.service;
 
import com.dao.RoleDAO;
import com.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
import java.util.List;
 
@Service
public class RoleService {
 
    @Autowired
    private RoleDAO roleRepository;
 
    // Find a single role by name
    public List<Role> findByRoleName(String roleName) {
        return roleRepository.findByRoleName(roleName);
    }
 
    // Find a list of roles by a specific role name or partial match
    public List<Role> findRolesByRoleName(String roleName) {
        return roleRepository.findByRoleName(roleName);
    }
 
    // Save a new role
    public void saveRole(Role role) {
        roleRepository.save(role);
    }
 
    // Check if a role already exists by name
    public boolean existsByRoleName(String roleName) {
        return roleRepository.findByRoleName(roleName) != null;
    }
}