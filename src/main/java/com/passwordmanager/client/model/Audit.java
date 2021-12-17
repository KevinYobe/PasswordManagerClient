package com.passwordmanager.client.model;

import java.time.ZonedDateTime;

import org.springframework.stereotype.Component;

@Component
public class Audit {
	
	
	private Long id;
	
	private String event;
	
	private String description;
	
	private ZonedDateTime created;
	
	private Long userId;
	
	public Audit() {
		
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getEvent() {
		return event;
	}
	public void setEvent(String event) {
		this.event = event;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public ZonedDateTime getCreated() {
		return created;
	}
	public void setCreated(ZonedDateTime created) {
		this.created = created;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
}
