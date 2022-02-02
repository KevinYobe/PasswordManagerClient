package com.passwordmanager.client.rest;

import java.net.URI;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public interface RestClient {
	
	public List<?> getAll(URI url);
	
	public Object get(URI url);
	
	public Object post(URI url,Object obj);
	
	public List<?>  postAll(URI url, List<?> items);

}
