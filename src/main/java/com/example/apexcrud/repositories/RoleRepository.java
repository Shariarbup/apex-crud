package com.example.apexcrud.repositories;

import com.example.apexcrud.enums.RoleType;
import com.example.apexcrud.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {
    List<Role> findAllByRoleIn(List<RoleType> roleType);
}