package passwordmanager.client.rest;

import java.net.URI;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import com.passwordmanager.client.model.Password;

@Component
public class PasswordRestClient extends AbstractRestClient{
	
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
			password = restTemplate.getForEntity(url, Password.class);
			return password.getBody();
		} catch (HttpClientErrorException | HttpServerErrorException e) {
			
		}
		return null;
	}

	@Override
	public Password post(URI url, Object obj) {
		Password response;
		try {
			response = restTemplate.postForObject(url, new HttpEntity<Object>(obj), Password.class);
			return response;
		} catch (HttpClientErrorException | HttpServerErrorException e) {
			
		}
		return null;
	}


	@Override
	public List<?> postAll(URI url, List<?> items) {
		return super.postAll(url, items);
	}
	
	
}
