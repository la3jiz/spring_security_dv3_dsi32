package com.user_microservice.security;



import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.user_microservice.entities.User;
import com.user_microservice.repository.UserRepository;

@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	UserRepository userRepository;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user=userRepository.findByUsername(username);
		
		if(user==null)
			throw new UsernameNotFoundException("user not found !");
		
		List<GrantedAuthority> auths=new ArrayList<>(); 
		
		user.getRoles().forEach(role->{
			GrantedAuthority authority=new SimpleGrantedAuthority(role.getRole());
			auths.add(authority);
		});
		
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), auths);
	}

}
