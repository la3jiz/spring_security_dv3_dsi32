package com.user_microservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.user_microservice.entities.Role;


public interface RoleRepository extends JpaRepository<Role,Long>{
Role findByRole(String roleName);
}
