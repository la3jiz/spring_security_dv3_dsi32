package com.user_microservice.services;


import java.util.List;

import com.user_microservice.Modal.UserLoginOrSignupResponseDetails;
import com.user_microservice.Modal.UserSignupRequestDetails;
import com.user_microservice.entities.Role;
import com.user_microservice.entities.User;

public interface UserService  {
UserLoginOrSignupResponseDetails saveUser(UserSignupRequestDetails user,String role);
User findUserByUsername(String username);
User findUserByUserId(String userId);
Role addRole (Role role);
User addRoleToUser(String username, String rolename);
List<User> findAllUsers();
void deleteUser(Long id);
User updateUser(User user);
}
