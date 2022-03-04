package com.passwordmanager.client.util;

import java.time.ZonedDateTime;

import java.util.UUID;

import java.net.URI;

import com.passwordmanager.client.rest.RestClientImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import com.passwordmanager.client.model.Token;

@Component
public class TokenUtil {
	
	@Value("${token.baseurl}")
	private String tokenUrl;
	
	@Autowired
	private RestClientImpl<Token> tokenRestClient;
	
	private URI uri;
	
	public Token createToken(String type, Long userId) {
		uri = UriComponentsBuilder.fromUriString(tokenUrl).path("/save").build().toUri();
		Token token = new Token();
		token.setCreated(ZonedDateTime.now());
		token.setDeleted(null);
		token.setUpdated(ZonedDateTime.now());
		token.setExpired(false);
		token.setStatus("VALID");
		token.setToken(generateToken());
		token.setType(type);
		token.setUserId(userId);
		tokenRestClient.post(uri, token, Token.class);
		return token;
	}
	
	private String generateToken() {
		return UUID.randomUUID().toString();
	}

}
