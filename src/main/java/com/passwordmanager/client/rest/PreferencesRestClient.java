package com.passwordmanager.client.rest;

import java.net.URI;
import java.util.List;

import org.springframework.stereotype.Component;

import com.passwordmanager.client.model.Preferences;

@Component
public class PreferencesRestClient extends AbstractRestClient  {
	
	public PreferencesRestClient() {
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Preferences> getAll(URI url) {
		return (List<Preferences>) super.getAll(url);
	}

	@Override
	public Preferences get(URI url) {
		return (Preferences) super.get(url);
	}

	@Override
	public Preferences post(URI url, Object obj) {
		// TODO Auto-generated method stub
		return (Preferences) super.post(url, obj);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Preferences> postAll(URI url, List<?> items) {
		// TODO Auto-generated method stub
		return (List<Preferences>) super.postAll(url, items);
	}
	
	

}
