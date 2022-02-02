package com.passwordmanager.client.rest;

import java.net.URI;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import com.passwordmanager.client.model.Password;
import com.passwordmanager.client.model.User;

@Component
public class UserRestClient extends AbstractRestClient{
	
	public UserRestClient() {
	}
	
	@Override
	public User get(URI url) {
		ResponseEntity<User> user;
		try {
			user = restTemplate.getForEntity(url, User.class);
			return user.getBody();
		} catch (HttpClientErrorException | HttpServerErrorException e) {
			
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<User> getAll(URI url) {
		return (List<User>) super.getAll(url);
	}
	
	@Override
	public User post(URI url, Object obj) {
		User response;
		try {
			response = (User) restTemplate.postForObject(url, new HttpEntity<Object>(obj), User.class);
			return response;
		} catch (HttpClientErrorException | HttpServerErrorException e) {
			
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<User> postAll(URI url, List<?> items) {
		return (List<User>) super.postAll(url, items);
	}
	
}
