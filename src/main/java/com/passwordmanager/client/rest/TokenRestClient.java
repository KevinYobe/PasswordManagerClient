package com.passwordmanager.client.rest;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.passwordmanager.client.model.Notification;
import com.passwordmanager.client.model.Token;

@Component
public class TokenRestClient extends AbstractRestClient {
	
	private final Logger logger = LoggerFactory.getLogger(MessageRestClient.class);
	
	@Override
	public Token get(URI url) {
		ResponseEntity<Token> token;
		try {
			logger.info("Sending request to remote:" + url);
			token = restTemplate.getForEntity(url, Token.class);
			logger.info("Received response from remote:" + getObjectMapper().writeValueAsString(token));
			return token.getBody();
		} catch (HttpClientErrorException | HttpServerErrorException e) {
			
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Token post(URI url, Object obj) {
		Token response;
		try {
			logger.info("Sending request to remote:" + url);
			response = restTemplate.postForObject(url, new HttpEntity<Object>(obj), Token.class);
			logger.info("Received response from remote:" + getObjectMapper().writeValueAsString(response));
			return response;
		} catch (HttpClientErrorException | HttpServerErrorException e) {
			
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}


}
