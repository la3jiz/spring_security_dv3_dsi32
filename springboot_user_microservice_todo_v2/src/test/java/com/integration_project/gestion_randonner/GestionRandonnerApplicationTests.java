package com.integration_project.gestion_randonner;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.user_microservice.entities.Role;
import com.user_microservice.entities.User;
import com.user_microservice.repository.UserRepository;

@SpringBootTest
class GestionRandonnerApplicationTests {

	@Test
	void contextLoads() {
	}
	@Autowired
	UserRepository userRepository;
@Autowired
BCryptPasswordEncoder bcryptPasswordEncoder;
	
	
	
	
}
