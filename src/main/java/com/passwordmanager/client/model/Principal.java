package com.passwordmanager.client.model;



import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Component
@JsonIgnoreProperties(ignoreUnknown = true)
public class Principal {
	
	private String username;
	private String password;
	private ZonedDateTime lastLogin;
	private ZonedDateTime updated;
	private Long userId;
	private List<Roles> roles;
	
	public Principal() {
		
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public ZonedDateTime getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(ZonedDateTime lastLogin) {
		this.lastLogin = lastLogin;
	}

	public ZonedDateTime getUpddated() {
		return updated;
	}

	public void setUpddated(ZonedDateTime updated) {
		this.updated = updated;
	}

	public List<Roles> getRoles() {
		return roles;
	}

	public void setRoles(List<Roles> roles) {
		this.roles = roles;
	}

	
	public Long getUserId() {
		return userId;
	}

	
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public ZonedDateTime getUpdated() {
		return updated;
	}

	public void setUpdated(ZonedDateTime updated) {
		this.updated = updated;
	}
}
