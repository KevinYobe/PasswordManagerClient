package com.passwordmanager.client.authentication;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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


import com.passwordmanager.client.model.Principal;
import com.passwordmanager.client.model.Roles;

import passwordmanager.client.rest.LoginRestClient;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private LoginRestClient loginRestClient;
	
	private URI uri;
	
	@Value("${login.baseurl}")
	private String loginUrl;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Principal principal = getUserPrincipal(username);
		System.out.println(principal.getRoles().size());
		return new User(principal.getUsername(), principal.getPassword(), getAuthorities(principal.getRoles()));
	}
	
	private Collection<? extends GrantedAuthority> getAuthorities(Collection<Roles> roles) {
    	List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
    	for (Roles role: roles) {
    		authorities.add(new SimpleGrantedAuthority(role.getRole()));
    	}
        return authorities;
    }
	
	private Principal getUserPrincipal(String username) {
		uri =UriComponentsBuilder.fromUriString(loginUrl).path("/getuser/" +username).build().toUri();
		Principal principal = loginRestClient.getPrincipal(uri);
		return principal;
		
	}
}
