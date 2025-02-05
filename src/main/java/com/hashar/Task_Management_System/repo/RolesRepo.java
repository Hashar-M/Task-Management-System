package com.hashar.Task_Management_System.repo;

import com.hashar.Task_Management_System.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolesRepo extends JpaRepository<Roles, Integer> {
    Optional<Roles> findByRoleName(String roleName);
    Boolean existsByRoleName(String roleName);
}
