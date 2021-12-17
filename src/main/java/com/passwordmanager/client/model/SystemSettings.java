package com.passwordmanager.client.model;

import java.time.ZonedDateTime;

import org.springframework.stereotype.Component;

@Component
public class SystemSettings {

	private Long id;
	
	private String key;
	
	private String value; 
	
	private ZonedDateTime created;
	
	private ZonedDateTime updated;
	
	private ZonedDateTime lastLogin;
	
	private ZonedDateTime deleted;
	
public SystemSettings() {
	// TODO Auto-generated constructor stub
}

public Long getId() {
	return id;
}

public void setId(Long id) {
	this.id = id;
}

public String getKey() {
	return key;
}

public void setKey(String key) {
	this.key = key;
}

public String getValue() {
	return value;
}

public void setValue(String value) {
	this.value = value;
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



}
