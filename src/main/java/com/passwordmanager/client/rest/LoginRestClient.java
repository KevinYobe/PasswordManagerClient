package com.passwordmanager.client.rest;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.passwordmanager.client.model.Login;
import com.passwordmanager.client.model.Principal;

@Component
public class LoginRestClient extends AbstractRestClient {
	
	private final Logger logger = LoggerFactory.getLogger(LoginRestClient.class);
	
	public LoginRestClient() {
		
	}
	
	public Principal authenticate(String url, String username, String Password) {
		Principal principal;
		try {
			ResponseEntity<Principal> response = restTemplate.getForEntity(url, Principal.class);
			if (response.getStatusCode()==HttpStatus.OK && response.hasBody()) {
				principal = response.getBody();
				return principal;
			}
				
		} catch (HttpClientErrorException | HttpServerErrorException e) {
			
		}
		return null;
	}
	
	@Override
	public Login post(URI url, Object obj) {
		return (Login) super.post(url, obj);
	}
	
	@Override
	public Login get(URI url) {
		return (Login) super.get(url);
	}
	
	public Principal getPrincipal(URI url) {
		Principal principal;
		try {
			logger.info("Sending request to remote:" + url);
			ResponseEntity<Principal> response = restTemplate.getForEntity(url, Principal.class);
			if (response.getStatusCode()==HttpStatus.OK && response.hasBody()) {
				principal = response.getBody();
				logger.info("Received response from remote:" + getObjectMapper().writeValueAsString(response));
				return principal;
			}
				
		} catch (HttpClientErrorException | HttpServerErrorException e) {
			
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	

}
