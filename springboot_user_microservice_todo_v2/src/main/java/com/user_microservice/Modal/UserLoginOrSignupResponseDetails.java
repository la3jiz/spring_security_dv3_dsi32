package com.user_microservice.Modal;

public class UserLoginOrSignupResponseDetails {

	private String token;
	private String userId;
	private String username;
	public UserLoginOrSignupResponseDetails(String token,String userId,String username) {
		super();
		this.token = token;
		this.userId=userId;
		this.username=username;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return token + " " + userId + " "+username;
	}


	
}
