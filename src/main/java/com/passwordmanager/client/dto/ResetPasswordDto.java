package com.passwordmanager.client.dto;

import org.springframework.stereotype.Component;

@Component
public class ResetPasswordDto {
	
	private String password; 
	private String token;
	private Long userId;
	
	public ResetPasswordDto() {
		
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * @return the userId
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	

}
