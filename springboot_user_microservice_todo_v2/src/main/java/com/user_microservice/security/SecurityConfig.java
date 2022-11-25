package com.user_microservice.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.user_microservice.security.JwtAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
@Autowired
UserDetailsService userDetailsService;

@Autowired
BCryptPasswordEncoder bcyptPasswordEncoder;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService)
		.passwordEncoder(bcyptPasswordEncoder);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
	http.csrf().disable();
	
	http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	
	http.authorizeRequests().antMatchers("/login").permitAll();
	
	http.authorizeRequests().antMatchers("/signup").permitAll();
	
	http.authorizeRequests().antMatchers("/all").hasAuthority("ADMIN");
	
	http.authorizeHttpRequests().antMatchers("admin/**").hasAuthority("ADMIN");

	//http.authorizeHttpRequests().antMatchers("/users/admin/**").hasAuthority("ADMIN");
	
	
	
	  http.authorizeRequests().anyRequest().authenticated();
	  
	  /*authenticationFilter*/
	  http.addFilter(new JwtAuthenticationFilter(authenticationManager()));

	  http.addFilterBefore(new JwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
	  
	}
	

}
