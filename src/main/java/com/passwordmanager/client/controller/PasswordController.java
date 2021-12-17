package com.passwordmanager.client.controller;

import java.net.URI;
import java.time.ZonedDateTime;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

import com.passwordmanager.client.model.Password;

import passwordmanager.client.rest.PasswordRestClient;

@Controller
public class PasswordController extends AbstractWebController {

	@Value("${password.baseurl}")
	private String passwordUrl;

	@Autowired
	private PasswordRestClient passwordRestClient;

	@Autowired
	private Password userPassword;

	private ModelAndView mav = new ModelAndView();

	private URI uri;

	@GetMapping("/addpassword")
	public ModelAndView addPassoword() {
		mav.setViewName("password/addpassword");
		return mav;
	}

	@PostMapping("/savePassword")
	public ModelAndView savePassword(HttpServletRequest request, Password password, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			//process errors 
		}
		userPassword.setUrl(password.getUrl());
		userPassword.setCreated(ZonedDateTime.now());
		userPassword.setDeleted(null);
		userPassword.setPassword(password.getPassword());
		userPassword.setResource(password.getResource());
		userPassword.setUsername(password.getUsername());

		uri = UriComponentsBuilder.fromUriString(passwordUrl).path("/addPassword/{username}")
				.build(request.getUserPrincipal().getName());
		
		passwordRestClient.post(uri, userPassword);
		mav.setViewName("redirect:/viewpassword");
		return mav;

	}

	@GetMapping("/viewpassword")
	public ModelAndView viewPasswords(HttpServletRequest request) {
		System.out.println(request.getUserPrincipal());
		mav.setViewName("password/viewpassword");
		return mav;
	}

	@ModelAttribute("passwords")
	public List<?> getPasswords(HttpServletRequest request) {
		uri = UriComponentsBuilder.fromUriString(passwordUrl).path("/getUserPassword/{username}")
				.build(request.getUserPrincipal().getName());
		List<?> passwords = passwordRestClient.getAll(uri);
		return passwords;
	}
	
	@ModelAttribute("password")
	public Password addPassword() {
		return new Password();
	}

	@GetMapping("/showeditpassword/{id}")
	public ModelAndView getPassword(@PathVariable("id") Long id) {
		
		uri = UriComponentsBuilder.fromUriString(passwordUrl).path("/getPassword/{id}").build(id);
		Password password = passwordRestClient.get(uri);
		System.out.println(password.getPassword());
		mav.addObject(password);
		mav.setViewName("password/editpassword");
		return mav;
	}
	
	@GetMapping("/removepassword/{id}")
	public ModelAndView removePassword(@PathVariable("id") Long id, HttpServletRequest request) {
		
		uri = UriComponentsBuilder.fromUriString(passwordUrl).path("/removePassword/{id}/{username}").build(id,request.getUserPrincipal().getName());
		Password password = passwordRestClient.get(uri);
		if (password != null) {
			mav.addObject("message", "Password was deleted successfully");
			mav.setViewName("redirect:/viewpassword");
		}
		mav.setViewName("redirect:/viewpassword");
		
		
		return mav;
	}
	
	
	@PostMapping("/editpassword")
	public ModelAndView editPassword(Password password) {
		
		System.out.println("Username" + password.getUsername());
		uri = UriComponentsBuilder.fromUriString(passwordUrl).path("/editPassword")
				.build().toUri();
		passwordRestClient.post(uri, password);
		mav.setViewName("redirect:/viewpassword");
		return mav;
	}

}
