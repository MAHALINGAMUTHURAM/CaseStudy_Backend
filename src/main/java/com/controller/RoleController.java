package com.controller;
 
import com.model.Role;
import com.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
 
import java.util.List;
 
@RestController
@RequestMapping("/roles")
public class RoleController {
 
    @Autowired
    private RoleService roleService;
 
    // Get a single role by name
//    @GetMapping("/{roleName}")
//    public Role getRoleByName(@PathVariable String roleName) {
//        return roleService.findByRoleName(roleName);
//    }
 
    // Get a list of roles by name or partial match
    @GetMapping("/search/{roleName}")
    public ResponseEntity<?> getRolesByRoleName(@PathVariable String roleName) {
        //roleService.findRolesByRoleName(roleName);
//    	List<Role> roles=roleService.findRolesByRoleName(roleName);
        return ResponseEntity.ok(roleService.findRolesByRoleName(roleName));
    }
 
    // Get all roles
    @GetMapping
    public List<Role> getAllRoles() {
        return roleService.findRolesByRoleName(""); // Pass an empty string for all roles
    }
 
    // Save a new role
    @PostMapping
    public String saveRole(@RequestBody Role role) {
        if (roleService.existsByRoleName(role.getRole_name())) {
            return "Role already exists: " + role.getRole_name();
        }
        roleService.saveRole(role);
        return "Role saved successfully: " + role.getRole_name();
    }
 
    // Check if a role exists by name
    @GetMapping("/exists/{roleName}")
    public boolean existsByRoleName(@PathVariable String roleName) {
        return roleService.existsByRoleName(roleName);
    }
}