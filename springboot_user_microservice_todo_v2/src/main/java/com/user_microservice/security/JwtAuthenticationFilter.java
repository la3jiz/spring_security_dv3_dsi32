package com.user_microservice.security;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.user_microservice.SpringApplicationContext;
import com.user_microservice.Modal.UserLoginOrSignupResponseDetails;
import com.user_microservice.entities.User;
import com.user_microservice.services.UserService;


public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	


	private AuthenticationManager authenticationManager;	



	public JwtAuthenticationFilter(AuthenticationManager authenticationManger) {
		super();
		this.authenticationManager = authenticationManger;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		User user=null;
		
		try {
			user=new ObjectMapper().readValue(request.getInputStream(),User.class);
		} catch (StreamReadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DatabindException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		
		org.springframework.security.core.userdetails.User springSecurityUser=(org.springframework.security.core.userdetails.User) authResult.getPrincipal();
	
	List<String> roles=new ArrayList<>();
	springSecurityUser.getAuthorities().forEach(auth->{
		roles.add(auth.getAuthority());
	});
	
	String jwt=JWT.create().
			withSubject(springSecurityUser.getUsername()).
			withArrayClaim("roles", roles.toArray(new String[roles.size()])).
			withExpiresAt(new Date(System.currentTimeMillis() +SecurityConstants.EXPERATION_TIME)). 
			sign(Algorithm.HMAC256(SecurityConstants.TOKEN_SECRET));

	UserService userService=(UserService) SpringApplicationContext.getBean("userServiceImpl");
	
	User findedUserByUsername=userService.findUserByUsername(springSecurityUser.getUsername());
	String findedUserId=findedUserByUsername.getUserId();
	String findedUsername=findedUserByUsername.getUsername();
	
	response.addHeader(SecurityConstants.HEADER_STRING, jwt);
	
	UserLoginOrSignupResponseDetails userLoginOrSignupResponseDetails=new UserLoginOrSignupResponseDetails(jwt,findedUserId,findedUsername);
	
    response.getWriter().write(userLoginOrSignupResponseDetails.toString());
//  response.getWriter().write(findedUserByUsername.getRoles().get(0).getRole().toString());
    response.getWriter().flush();
	}
	

}
