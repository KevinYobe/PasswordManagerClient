package com.passwordmanager.client.model;

import java.time.ZonedDateTime;

public class Token {
	
	
	private Long id;
	

	private Long userId;
	
	private String token;
	
	private String type;
	
	private String status;
	
	private boolean expired;
	
	private ZonedDateTime created;
	
	private ZonedDateTime updated;
	
	private ZonedDateTime deleted;
	
	public Token() {
		
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

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean isExpired() {
		return expired;
	}

	public void setExpired(boolean expired) {
		this.expired = expired;
	}

	public ZonedDateTime getCreated() {
		return created;
	}

	public void setCreated(ZonedDateTime created) {
		this.created = created;
	}

	public ZonedDateTime getUpdated() {
		return updated;
	}

	public void setUpdated(ZonedDateTime updated) {
		this.updated = updated;
	}

	public ZonedDateTime getDeleted() {
		return deleted;
	}

	public void setDeleted(ZonedDateTime deleted) {
		this.deleted = deleted;
	}
	
	

}
