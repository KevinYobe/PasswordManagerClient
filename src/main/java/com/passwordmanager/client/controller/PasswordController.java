package com.passwordmanager.client.controller;

import java.net.URI;
import java.time.ZonedDateTime;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.passwordmanager.client.model.Password;
import com.passwordmanager.client.rest.PasswordRestClient;

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
	
	private final Logger logger = LoggerFactory.getLogger(PasswordController.class);
	
	@Autowired 
	ObjectMapper objectMapper;

	@GetMapping("/addpassword")
	public ModelAndView addPassoword() {
		mav.setViewName("password/addpassword");
		return mav;
	}

	@PostMapping("/savePassword")
	public ModelAndView savePassword(HttpServletRequest request, Password password, BindingResult bindingResult) throws JsonProcessingException {
		
		if (bindingResult.hasErrors()) {
			//process errors 
		}
		
		logger.info("Request to add a new password");
		deletePassword(password);
		uri = UriComponentsBuilder.fromUriString(passwordUrl).path("/addPassword/{username}")
				.build(request.getUserPrincipal().getName());
		logger.info("Sending request to remote to save password" + objectMapper.writeValueAsString(password));
		Password savedPassword = passwordRestClient.post(uri, userPassword);
		logger.info("Received response from remote, password saved successfully" + objectMapper.writeValueAsString(savedPassword));
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
	public List<?> getPasswords(HttpServletRequest request) throws JsonProcessingException {
		logger.info("Sending request to remote to get passwords" + objectMapper.writeValueAsString(request.getRemoteUser()));
		uri = UriComponentsBuilder.fromUriString(passwordUrl).path("/getUserPassword/{username}")
				.build(request.getUserPrincipal().getName());
		List<?> passwords = passwordRestClient.getAll(uri);
		logger.info("Received response from remote:" + objectMapper.writeValueAsString(passwords));
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
		mav.addObject(password);
		mav.setViewName("password/editpassword");
		return mav;
	}
	
	@GetMapping("/removepassword/{id}")
	public ModelAndView removePassword(@PathVariable("id") Long id, HttpServletRequest request) throws JsonProcessingException {
		logger.info("Sending request to remote to delete password" + objectMapper.writeValueAsString(request.getUserPrincipal()));
		uri = UriComponentsBuilder.fromUriString(passwordUrl).path("/removePassword/{id}/{username}").build(id,request.getUserPrincipal().getName());
		Password password = passwordRestClient.get(uri);
		if (password != null) {
			mav.addObject("message", "Password was deleted successfully");
			logger.info("Received response from remote, password deleted successfully:" + objectMapper.writeValueAsString(password));
			mav.setViewName("redirect:/viewpassword");
		}
		mav.setViewName("redirect:/viewpassword");
		
		
		return mav;
	}
	
	
	@PostMapping("/editpassword")
	public ModelAndView editPassword(Password password) throws JsonProcessingException {
		logger.info("Sending request to remote to edit password" + objectMapper.writeValueAsString(password));
		uri = UriComponentsBuilder.fromUriString(passwordUrl).path("/editPassword")
				.build().toUri();
		Password editedPassword = passwordRestClient.post(uri, password);
		mav.setViewName("redirect:/viewpassword");
		logger.info("Received response from remote, password edited successfully:" + objectMapper.writeValueAsString(editedPassword));
		return mav;
	}
	
	private void deletePassword(Password password) {
		userPassword.setUrl(password.getUrl());
		userPassword.setCreated(ZonedDateTime.now());
		userPassword.setDeleted(null);
		userPassword.setPassword(password.getPassword());
		userPassword.setResource(password.getResource());
		userPassword.setUsername(password.getUsername());
	}

}
