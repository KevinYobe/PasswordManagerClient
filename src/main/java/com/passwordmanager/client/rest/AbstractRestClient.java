package com.passwordmanager.client.rest;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class AbstractRestClient implements RestClient{
	
	@Autowired
	protected RestTemplate restTemplate;
	
	private final Logger logger = LoggerFactory.getLogger(AbstractRestClient.class);
	
	private ObjectMapper objectMapper;
	
	@Override
	public List<?> getAll(URI url) {
		ResponseEntity<Object[]> response; 
		try {
			logger.info("Sending request to remote:" + url);
			response = restTemplate.getForEntity(url, Object[].class);
			logger.info("Received response from remote:" + getObjectMapper().writeValueAsString(response));
			return Arrays.asList(response.getBody());
		} catch (HttpClientErrorException | HttpServerErrorException e) {
			
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Object get(URI url) {
		ResponseEntity<Object> response;
		try {
			logger.info("Sending request to remote:" + url);
			response = restTemplate.getForEntity(url, Object.class);
			logger.info("Received response from remote:" + getObjectMapper().writeValueAsString(response));
			return response.getBody();
		} catch (HttpClientErrorException | HttpServerErrorException e) {
			
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	
	@Override
	public Object post(URI url, Object obj) {
		Object response;
		try {
			logger.info("Sending request to remote:" + url + obj);
			response = restTemplate.postForObject(url, new HttpEntity<Object>(obj), Object.class);
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
		Object[]  response;
		try {
			logger.info("Sending request to remote:" + url + items);
			response = restTemplate.postForObject(url, new HttpEntity<Object>(items), Object[].class);
			logger.info("Received response from remote:" + getObjectMapper().writeValueAsString(response));
			return Arrays.asList(response);
		} catch (Exception e) {
			
		}
		return null;
		
	}
	
	public ObjectMapper getObjectMapper() {
		this.objectMapper = new ObjectMapper();
		objectMapper.findAndRegisterModules();
		return objectMapper;
	}

}
