package com.passwordmanager.client.rest;


import java.net.URI;
import java.util.Arrays;
import java.util.List;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import com.passwordmanager.client.model.Audit;


@Component
public class AuditRestClient extends AbstractRestClient {
	
	@Override
	public List<Audit> getAll(URI url) {
		
		List<Audit> audit;
		try {
		ResponseEntity<Audit[]> auditResponse = restTemplate.getForEntity(url, Audit[].class);
		if(auditResponse.getStatusCode()==HttpStatus.OK){
			audit = Arrays.asList(auditResponse.getBody());
			return audit;
		}
		}
		catch (HttpClientErrorException | HttpServerErrorException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	@Override
	public Audit get(URI url) {
		Audit audit;
		try {
			ResponseEntity<Audit> auditResponse = restTemplate.getForEntity(url, Audit.class);
			if(auditResponse.getStatusCode() == HttpStatus.OK) {
				audit = auditResponse.getBody();
				return audit;
			}
		}
		catch(HttpClientErrorException | HttpServerErrorException e) {
			e.printStackTrace();
		}
		
		return null;
		
	}	

	
}
