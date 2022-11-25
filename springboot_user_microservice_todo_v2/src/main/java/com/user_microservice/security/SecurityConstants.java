package com.user_microservice.security;

public interface SecurityConstants {
public static final long EXPERATION_TIME=10*24*60*60*1000;
public static final String TOKEN_PREFIX="Bearer ";
public static final String HEADER_STRING="Authorization";
public static final String SIGNUP_URL="/users";
public static final String TOKEN_SECRET="tokensecretkey";

}
