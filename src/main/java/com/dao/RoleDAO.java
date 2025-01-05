package com.dao;
 
import com.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
 
import java.util.List;
 
public interface RoleDAO extends JpaRepository<Role, Long> {
 
    // Find a role by exact role name
    @Query("SELECT r FROM Role r WHERE r.role_name = ?1")
    List<Role> findByRoleName(String roleName);
 
    // Find roles by hierarchy (e.g., Admin, Manager, User)
    @Query("SELECT r FROM Role r WHERE r.role_name IN ?1 ORDER BY FIELD(r.role_name, 'Admin', 'Manager', 'User')")
    List<Role> findRolesByHierarchy(List<String> roleNames);
}