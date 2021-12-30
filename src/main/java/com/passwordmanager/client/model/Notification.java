package com.passwordmanager.client.model;

import java.time.ZonedDateTime;


public class Notification {
	

	private Long id;
	

	private Long userId;
	
	private String message;
	
	private String from; 
	
	private String destination;
	
	private String type;
	
	private String status;
	
	private ZonedDateTime created;
	
	private ZonedDateTime updated;
	
	private ZonedDateTime deleted;
	
	public Notification() {
		
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

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
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
