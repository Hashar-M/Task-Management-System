package com.hashar.Task_Management_System.repo;

import com.hashar.Task_Management_System.model.Permissions;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface PermissionRepo extends JpaRepository<Permissions, Integer> {
    Optional<Permissions> findByPermission(String permission);
    Boolean existsByPermission(String permission);
}
