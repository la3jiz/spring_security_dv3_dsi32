package com.user_microservice.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.user_microservice.Modal.UserLoginOrSignupResponseDetails;
import com.user_microservice.Modal.UserSignupRequestDetails;
import com.user_microservice.entities.User;
import com.user_microservice.services.UserService;

@RestController
@CrossOrigin(origins = "*")
public class UserRestController {

	@Autowired
	UserService userService;

	@RequestMapping(path = "all", method = RequestMethod.GET)
	public List<User> getAllUsers() {
		return userService.findAllUsers();
	}
	
	@RequestMapping(path = "signup", method = RequestMethod.POST)
	public UserLoginOrSignupResponseDetails signupUser(@RequestBody UserSignupRequestDetails user,@RequestParam String rolename ) {
		UserLoginOrSignupResponseDetails userCredentials=userService.saveUser(user, rolename);
		return userCredentials;
	}
	
	@RequestMapping(path="admin/{userId}",method = RequestMethod.GET)
	public User getUserByUserId(@PathVariable String userId) {
		return userService.findUserByUserId(userId);
	}
	@RequestMapping(path="admin/{id}",method = RequestMethod.DELETE)
	public void deleteUserByUserId(@PathVariable Long id) {
		userService.deleteUser(id);
	}
	@RequestMapping(path="admin/{id}",method = RequestMethod.PUT)
	public User updateUserByUserId(@RequestBody User user) {
		return userService.updateUser(user);
		
	}
}
