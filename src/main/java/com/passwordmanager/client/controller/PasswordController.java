package com.passwordmanager.client.controller;

import java.net.URI;
import java.time.ZonedDateTime;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.passwordmanager.client.dto.ErrorDto;
import com.passwordmanager.client.rest.RestClient;
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

@Controller
public class PasswordController extends AbstractWebController {

	@Value("${password.baseurl}")
	private String passwordUrl;

	@Autowired
	RestClient<Password> restClient;

	@Autowired
	private Password userPassword;

	private ModelAndView mav = new ModelAndView();

	private URI uri;
	
	private final Logger logger = LoggerFactory.getLogger(PasswordController.class);

	@Autowired
	ErrorDto errorDto;

	@Autowired 
	private ObjectMapper objectMapper;

	@GetMapping("/addpassword")
	public ModelAndView addPassoword() {
		mav.setViewName("password/addpassword");
		return mav;
	}

	@PostMapping("/savePassword")
	public ModelAndView savePassword(HttpServletRequest request, Password password, BindingResult bindingResult) throws JsonProcessingException {
		
		logger.info("Request to add a new password");
		deletePassword(password);
		uri = UriComponentsBuilder.fromUriString(passwordUrl).path("/addPassword/{username}")
				.build(request.getUserPrincipal().getName());
		logger.info("Sending request to remote to save password" + objectMapper.writeValueAsString(password.toString()));
		Password savedPassword = restClient.post(uri, userPassword, Password.class);
		if (savedPassword ==null){
			errorDto.setErrorMessage("Failed to add password. Please contact support if this persists");
			mav.addObject(errorDto);
			mav.setViewName("redirect:/viewpassword");
		}
		logger.info("Received response from remote, password saved successfully" + objectMapper.writeValueAsString(savedPassword));
		errorDto.setErrorMessage("Successfully added password");
		mav.addObject(errorDto);
		mav.setViewName("redirect:/viewpassword");
		return mav;

	}

	@GetMapping("/viewpassword")
	public ModelAndView viewPasswords(HttpServletRequest request) {
		mav.setViewName("password/viewpassword");
		return mav;
	}

	@ModelAttribute("passwords")
	public List<?> getPasswords(HttpServletRequest request, Password password) throws JsonProcessingException {
		logger.info("Sending request to remote to get passwords" + objectMapper.writeValueAsString(request.getRemoteUser()));
		uri = UriComponentsBuilder.fromUriString(passwordUrl).path("/getUserPassword/{username}")
				.build(request.getUserPrincipal().getName());
		List<?> passwords = restClient.getAll(uri, Password[].class);
		logger.info("Received response from remote:" + objectMapper.writeValueAsString(passwords));
		return passwords;
	}
	
	@ModelAttribute("password")
	public Password addPassword() {
		return new Password();
	}

	@GetMapping("/showeditpassword/{id}")
	public ModelAndView getPassword(@PathVariable("id") Long id, Password userPassword) {
		uri = UriComponentsBuilder.fromUriString(passwordUrl).path("/getPassword/{id}").build(id);
		Password password = restClient.get(uri, Password.class);
		mav.addObject(password);
		mav.setViewName("password/editpassword");
		return mav;
	}
	
	@GetMapping("/removepassword/{id}")
	public ModelAndView removePassword(@PathVariable("id") Long id, HttpServletRequest request, Password p) throws JsonProcessingException {
		logger.info("Sending request to remote to delete password" + objectMapper.writeValueAsString(request.getUserPrincipal()));
		uri = UriComponentsBuilder.fromUriString(passwordUrl).path("/removePassword/{id}/{username}").build(id,request.getUserPrincipal().getName());
		Password password = restClient.get(uri, Password.class);
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
		Password editedPassword = restClient.post(uri, password, Password.class);
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
