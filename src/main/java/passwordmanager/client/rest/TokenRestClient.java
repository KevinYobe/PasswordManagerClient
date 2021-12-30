package passwordmanager.client.rest;

import java.net.URI;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import com.passwordmanager.client.model.Notification;


public class TokenRestClient extends AbstractRestClient {
	@Override
	public Notification get(URI url) {
		ResponseEntity<Notification> notification;
		try {
			notification = restTemplate.getForEntity(url, Notification.class);
			return notification.getBody();
		} catch (HttpClientErrorException | HttpServerErrorException e) {
			
		}
		return null;
	}

	@Override
	public Notification post(URI url, Object obj) {
		Notification response;
		try {
			response = restTemplate.postForObject(url, new HttpEntity<Object>(obj), Notification.class);
			return response;
		} catch (HttpClientErrorException | HttpServerErrorException e) {
			
		}
		return null;
	}


}
