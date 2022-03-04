package com.passwordmanager.client.rest;

import java.net.URI;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public interface RestClient<T> {
	
	public List<?> getAll(URI uri, Class<T[]> type);

	public T get(URI uri, Class<T> type);
	
	public <T> T post(URI uri, T obj, Class<T> type);


}
