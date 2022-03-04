package com.passwordmanager.client.rest;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class RestClientImpl<T> implements RestClient<T>{

	@Autowired
	protected RestTemplate restTemplate;

	private final Logger logger = LoggerFactory.getLogger(RestClientImpl.class);

	private ObjectMapper objectMapper;

	public RestClientImpl() {
	}

	@Override
	public List<T> getAll(URI uri, Class<T[]> type) {
		List <T> result = new ArrayList<>();
		try {
			logger.info("Sending request to remote:" + uri);
			ResponseEntity<T[]> response = restTemplate.getForEntity(uri, type);
			logger.info("Received response from remote:" + getObjectMapper().writeValueAsString(response));
			result = Arrays.asList(response.getBody());
			return result;
		}catch (HttpClientErrorException | HttpServerErrorException | JsonProcessingException e){
			logger.error(e.getLocalizedMessage());
			return null;
		}
	}

	@Override
	public  T get(URI uri, Class<T> type) {

		try {
			logger.info("Sending request to remote:" + uri);
			ResponseEntity<T> response = restTemplate.getForEntity(uri, type);
			logger.info("Received response from remote:" + getObjectMapper().writeValueAsString(response));
			return response.getBody();
		}catch (HttpClientErrorException | HttpServerErrorException | JsonProcessingException e){
			logger.error(e.getLocalizedMessage());
			return null;
		}
	}

	@Override
	public <T extends Object> T post(URI uri, T obj, Class<T> type) {
		try {
			logger.info("Sending request to remote:" + uri);
			ResponseEntity<?> response = restTemplate.postForEntity(uri,obj,type);
			logger.info("Received response from remote:" + getObjectMapper().writeValueAsString(response));
			return (T) response.getBody();
		}catch (HttpClientErrorException | HttpServerErrorException | JsonProcessingException e){
			logger.error(e.getLocalizedMessage());
			return null;
		}
	}

	public ObjectMapper getObjectMapper() {
		this.objectMapper = new ObjectMapper();
		objectMapper.findAndRegisterModules();
		return objectMapper;
	}
}
