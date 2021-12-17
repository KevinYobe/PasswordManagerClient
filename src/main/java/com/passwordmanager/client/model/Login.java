package com.passwordmanager.client.model;

import java.time.ZonedDateTime;

import org.springframework.stereotype.Component;

@Component
public class Login {
	

	private Long id;
	
	
	private Long userId;
	
	private int attempts;
	
	
	private boolean attemptsExceeded;
	
	private String username; 
	
	private String password;
	
	private ZonedDateTime created;
	
	private ZonedDateTime updated;
	
	private ZonedDateTime lastLogin;
	
	private ZonedDateTime deleted;
	
	private boolean active;
	
	public Login() {
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public ZonedDateTime getCreated() {
		return created;
	}

	public void setCreated(ZonedDateTime created) {
		this.created = created;
	}

	
	public ZonedDateTime getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(ZonedDateTime lastLogin) {
		this.lastLogin = lastLogin;
	}

	public ZonedDateTime getDeleted() {
		return deleted;
	}

	public void setDeleted(ZonedDateTime deleted) {
		this.deleted = deleted;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}


	public int getAttempts() {
		return attempts;
	}

	public void setAttempts(int attempts) {
		this.attempts = attempts;
	}

	public ZonedDateTime getUpdated() {
		return updated;
	}

	public void setUpdated(ZonedDateTime updated) {
		this.updated = updated;
	}

	public boolean isAttemptsExceeded() {
		return attemptsExceeded;
	}

	public void setAttemptsExceeded(boolean attemptsExceeded) {
		this.attemptsExceeded = attemptsExceeded;
	}
	
	

}
