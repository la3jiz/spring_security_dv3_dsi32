package com.user_microservice.utils;

import java.security.SecureRandom;
import java.util.Random;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class Utils {
private final Random RANDOM=new SecureRandom();
private final String ALPHABET="123456789AZERTYUIOPQSDFGHJKLMWXCVBNazertyuiopqsdfghjklmwxcvbn";
 

public String generateUserId(int length) {
	return generateRandomString(length);
}

public String generateRandomString(int length) {
	StringBuilder returnValue=new StringBuilder(length);
	for (int i=0;i<length;i++) {
		returnValue.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
	}
	return returnValue.toString();
}

/*
	  public static void main(String[] args) {
	    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	    String encoded = encoder.encode("123");
	    System.out.println(encoded);
	    encoded = encoder.encode("password2");
	    System.out.println(encoded);
	  
	}*/

}
