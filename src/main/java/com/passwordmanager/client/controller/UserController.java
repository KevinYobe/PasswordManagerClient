package com.passwordmanager.client.controller;

import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

import com.passwordmanager.client.model.User;

import passwordmanager.client.rest.UserRestClient;

@Controller
public class UserController {

	@Value("${user.baseurl}")
	private String userUrl;

	private ModelAndView mav = new ModelAndView();

	private URI uri;

	@Autowired
	private UserRestClient userRestClient;

	public UserController() {
	}

	@ModelAttribute("users")
	public List<User> getAllUsers() {
		uri =UriComponentsBuilder.fromUriString(userUrl).path("/getAllUsers").build().toUri();
		List<User> users = userRestClient.getAll(uri);
		return users;

	}

	@GetMapping("/showuser")
	public ModelAndView showUsers() {
		mav.setViewName("user/viewusers");
		return mav;
	}
	
	@GetMapping("/account")
	public ModelAndView showAccount(HttpServletRequest request) {
		uri = UriComponentsBuilder.fromUriString(userUrl).path("/finduserbyusername/{username}").build(request.getUserPrincipal().getName());
		User user = userRestClient.get(uri);
		System.out.println(user.getFirstName());
		mav.addObject("user",user);
		mav.setViewName("user/profile");
		return mav;
	}
	
	@PostMapping("/account")
	public ModelAndView updateUser(User user) {
		uri = UriComponentsBuilder.fromUriString(userUrl).path("/save").build().toUri();
		User updatedUser = userRestClient.post(uri, user);
		mav.addObject("user",updatedUser);
		mav.setViewName("user/profile");
		return mav;
	}
	
	
	@GetMapping("/adduser")
	public ModelAndView addUser(){
		mav.setViewName("/user/createuser");
		return mav;
	}
	

	@PostMapping("/adduser")
	public ModelAndView addUser(User user) {
		uri =UriComponentsBuilder.fromUriString(userUrl).path("/save").build().toUri();	
		userRestClient.post(uri, user);
		mav.setViewName("redirect:/showuser");
		return mav;
	}

	@GetMapping("/removeuser/{id}")
	public ModelAndView removeUser(@PathVariable Long id) {
		uri = UriComponentsBuilder.fromUriString(userUrl).path("/removeuser/{id}").build(id);
		userRestClient.get(uri);
		mav.setViewName("redirect:/showuser");
		return mav;
	}
	
	@GetMapping("/createaccount")
	public ModelAndView createAccount() {
		mav.setViewName("/user/createaccount");
		return mav;
	}
	
	@GetMapping("/confirmtoken/({token}")
	public ModelAndView confirmToken(@PathVariable Long id) {
		mav.setViewName("/user/createaccount");
		return mav;
	}
	
	@PostMapping("/createaccount")
	public ModelAndView createAccount(User user) {
		uri =UriComponentsBuilder.fromUriString(userUrl).path("/save").build().toUri();	
		userRestClient.post(uri, user);
		mav.setViewName("redirect:/login");
		return mav;
	}
	
	@ModelAttribute("user")
	public User getUser() {
		return new User();
	}
	

}
