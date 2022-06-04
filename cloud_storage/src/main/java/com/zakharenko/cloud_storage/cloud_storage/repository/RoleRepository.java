package com.zakharenko.cloud_storage.cloud_storage.repository;

import com.zakharenko.cloud_storage.cloud_storage.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
