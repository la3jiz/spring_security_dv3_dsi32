package com.user_microservice.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.user_microservice.Modal.UserLoginOrSignupResponseDetails;
import com.user_microservice.Modal.UserSignupRequestDetails;
import com.user_microservice.entities.Role;
import com.user_microservice.entities.User;
import com.user_microservice.repository.RoleRepository;
import com.user_microservice.repository.UserRepository;
import com.user_microservice.security.SecurityConstants;
import com.user_microservice.services.UserService;
import com.user_microservice.utils.Utils;


@Transactional
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	BCryptPasswordEncoder bcryptPasswordEncoder;
	
	@Autowired
	private Utils utils;

	@Override
	public UserLoginOrSignupResponseDetails saveUser(UserSignupRequestDetails userSignupResquestDetails,String rolename) {
		
		if(userRepository.findByUsername(userSignupResquestDetails.getUsername())!=null) {
			throw new IllegalArgumentException("user already exist !");
		}
		User user=new User();
		user.setEnabled(true);
		BeanUtils.copyProperties(userSignupResquestDetails, user);
		
		user.setPassword(bcryptPasswordEncoder.encode(user.getPassword()));
		
		String publicUserId=utils.generateUserId(30);
		user.setUserId(publicUserId);
		

		Role role=new Role(null , rolename);
		List<Role> newRoles=new ArrayList<>();
		newRoles.add(role);
		user.setRoles(newRoles);
		List<String> roles=new ArrayList();
				user.getRoles().forEach(r->{
					roles.add(r.getRole());
				});
		
		String jwt=JWT.create().
				withSubject(user.getUsername()).
				withArrayClaim("roles", roles.toArray(new String[roles.size()])).
				withExpiresAt(new Date(System.currentTimeMillis() +SecurityConstants.EXPERATION_TIME)). 
				sign(Algorithm.HMAC256(SecurityConstants.TOKEN_SECRET));
	
		userRepository.save(user);
		return new UserLoginOrSignupResponseDetails(jwt, publicUserId,userSignupResquestDetails.getUsername());
	}

	@Override
	public User findUserByUsername(String username) {
		return userRepository.findByUsername(username); 
	}

	@Override
	public Role addRole(Role role) {

		return roleRepository.save(role);
	}

	@Override
	public User addRoleToUser(String username, String rolename) {
		User user=userRepository.findByUsername(username);
		Role role = roleRepository.findByRole(rolename);
		user.getRoles().add(role);
		return userRepository.save(user);
	}

	@Override
	public List<User> findAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public User findUserByUserId(String userId) {
		return userRepository.findByUserId(userId);
	}

	@Override
	public void deleteUser(Long id) {
		userRepository.deleteById(id);		
	}

	@Override
	public User updateUser(User user) {

		return userRepository.save(user);
		
	}







}
