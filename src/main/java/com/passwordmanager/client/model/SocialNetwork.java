package com.passwordmanager.client.model;

import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

@Component
public class SocialNetwork {
    private Long id;
    private String resource;
    private String resourceType;
    private String url;
    private String username;
    private String password;
    private ZonedDateTime created;
    private ZonedDateTime updated;
    private ZonedDateTime deleted;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getResource() {
        return resource;
    }
    public void setResource(String resource) {
        this.resource = resource;
    }
    public String getResourceType() {
        return resourceType;
    }
    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
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
