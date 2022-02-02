package com.passwordmanager.client.model;

import java.time.ZonedDateTime;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@Component
public class Roles {

	private int id;

	private String role;

	private String description;

	private ZonedDateTime created;

	private ZonedDateTime updated;

	private ZonedDateTime deleted;

	public Roles() {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
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
