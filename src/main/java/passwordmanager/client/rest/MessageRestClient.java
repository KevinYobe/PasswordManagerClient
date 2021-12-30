package passwordmanager.client.rest;

import java.net.URI;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import com.passwordmanager.client.model.Token;

public class MessageRestClient extends AbstractRestClient{
	
	@Override
	public Token get(URI url) {
		ResponseEntity<Token> token;
		try {
			token = restTemplate.getForEntity(url, Token.class);
			return token.getBody();
		} catch (HttpClientErrorException | HttpServerErrorException e) {
			
		}
		return null;
	}

	@Override
	public Token post(URI url, Object obj) {
		Token response;
		try {
			response = restTemplate.postForObject(url, new HttpEntity<Object>(obj), Token.class);
			return response;
		} catch (HttpClientErrorException | HttpServerErrorException e) {
			
		}
		return null;
	}



}
