package com.passwordmanager.client.rest;

import java.net.URI;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.passwordmanager.client.model.Password;

@Component
public class PasswordRestClient extends AbstractRestClient{
	
	private final Logger logger = LoggerFactory.getLogger(PasswordRestClient.class);
	
	
	public PasswordRestClient() {
		
	}

	@Override
	public List<?> getAll(URI url) {
		return super.getAll(url);
	}

	@Override
	public Password get(URI url) {
		ResponseEntity<Password> password;
		try {
			logger.info("Sending request to remote:" + url);
			password = restTemplate.getForEntity(url, Password.class);
			logger.info("Received response from remote:" + getObjectMapper().writeValueAsString(password));
			return password.getBody();
		} catch (HttpClientErrorException | HttpServerErrorException e) {
			
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Password post(URI url, Object obj) {
		Password response;
		try {
			logger.info("Sending request to remote:" + url);
			response = restTemplate.postForObject(url, new HttpEntity<Object>(obj), Password.class);
			logger.info("Received response from remote:" + getObjectMapper().writeValueAsString(response));
			return response;
		} catch (HttpClientErrorException | HttpServerErrorException e) {
			
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}


	@Override
	public List<?> postAll(URI url, List<?> items) {
		logger.info("Sending request to remote:" + url);
		return super.postAll(url, items);
	}
	
	
}
