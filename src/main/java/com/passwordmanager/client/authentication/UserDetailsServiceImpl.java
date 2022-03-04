package com.passwordmanager.client.authentication;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.passwordmanager.client.rest.RestClientImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.passwordmanager.client.model.Principal;
import com.passwordmanager.client.model.Roles;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private RestClientImpl<Principal> 	restClient;
	
	@Autowired
	ObjectMapper objectMapper;
	
	private URI uri;
	
	@Value("${login.baseurl}")
	private String loginUrl;
	
	Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Principal principal;
		try {
			principal = getUserPrincipal(username);
			return new User(principal.getUsername(), principal.getPassword(), getAuthorities(principal.getRoles()));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	
		
	}
	
	private Collection<? extends GrantedAuthority> getAuthorities(Collection<Roles> roles) {
    	List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
    	for (Roles role: roles) {
    		authorities.add(new SimpleGrantedAuthority(role.getRole()));
    	}
        return authorities;
    }
	
	private Principal getUserPrincipal(String username) throws JsonProcessingException {
		uri =UriComponentsBuilder.fromUriString(loginUrl).path("/getuser/" +username).build().toUri();
		logger.debug("Sending request for authentication for " + username);
		Principal principal = restClient.get(uri, Principal.class);
		logger.debug("Received principal from remote" + objectMapper.writeValueAsString(principal));
		return principal;
		
	}
}
