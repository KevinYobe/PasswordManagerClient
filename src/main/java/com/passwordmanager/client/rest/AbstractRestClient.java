package com.passwordmanager.client.rest;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class AbstractRestClient implements RestClient{
	
	@Autowired
	protected RestTemplate restTemplate;
	
	
	@Override
	public List<?> getAll(URI url) {
		ResponseEntity<Object[]> response; 
		try {
			response = restTemplate.getForEntity(url, Object[].class);
			return Arrays.asList(response.getBody());
		} catch (HttpClientErrorException | HttpServerErrorException e) {
			
		}
		return null;
	}

	@Override
	public Object get(URI url) {
		ResponseEntity<Object> response;
		try {
			response = restTemplate.getForEntity(url, Object.class);
			return response.getBody();
		} catch (HttpClientErrorException | HttpServerErrorException e) {
			
		}
		return null;
	}

	
	@Override
	public Object post(URI url, Object obj) {
		Object response;
		try {
			response = restTemplate.postForObject(url, new HttpEntity<Object>(obj), Object.class);
			return response;
		} catch (HttpClientErrorException | HttpServerErrorException e) {
			
		}
		return null;
	}

	@Override
	public List<?> postAll(URI url, List<?> items) {
		Object[]  response;
		try {
			response = restTemplate.postForObject(url, new HttpEntity<Object>(items), Object[].class);
			return Arrays.asList(response);
		} catch (Exception e) {
			
		}
		return null;
		
	}

}
