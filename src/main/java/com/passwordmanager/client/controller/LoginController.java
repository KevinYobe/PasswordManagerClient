package com.passwordmanager.client.controller;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriBuilder;

import com.passwordmanager.client.model.Principal;

import passwordmanager.client.rest.LoginRestClient;
import passwordmanager.client.rest.RolesRestClient;

@Controller
public class LoginController {

	@Value("${login.baseurl}")
	String loginURL;
	
	@Value("${role.baseurl}")
	String rolesUrl;

	@Autowired
	LoginRestClient loginRestClient;
	
	@Autowired
	RolesRestClient rolesRestClient;
	
	@Autowired
	Principal principal;
	
	UriBuilder uriBuilder;
	
	@PostConstruct
	public void init() {
	}

	@GetMapping("/login")
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("auth/login");
		return mav;
	}
	
	@GetMapping("/logout")
	public ModelAndView logout() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("auth/login");
		return mav;
	}

}
