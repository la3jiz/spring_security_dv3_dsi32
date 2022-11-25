package com.user_microservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.user_microservice.entities.User;



public interface UserRepository extends JpaRepository<User,Long> {
User findByUsername(String userName);
User findByUserId(String userId);
}
