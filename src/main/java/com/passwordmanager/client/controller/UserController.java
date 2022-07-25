package com.passwordmanager.client.controller;

import java.net.URI;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.passwordmanager.client.dto.ResetPasswordDto;
import com.passwordmanager.client.model.OTP;
import com.passwordmanager.client.rest.RestClientImpl;
import com.passwordmanager.client.util.OTPUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.passwordmanager.client.model.Notification;
import com.passwordmanager.client.model.Token;
import com.passwordmanager.client.model.User;
import com.passwordmanager.client.notification.Messenger;
import com.passwordmanager.client.util.TokenUtil;

@Controller
public class UserController {

	@Value("${user.baseurl}")
	private String userUrl;

	@Autowired
	private Messenger messenger;

	@Autowired
	OTPUtil otpUtil;

	private ModelAndView mav = new ModelAndView();

	private URI uri;

	@Autowired
	TokenUtil tokenUtil;

	@Autowired
	private RestClientImpl<User> restClient;

	@Autowired
	private ObjectMapper objectMapper;

	private final Logger logger = LoggerFactory.getLogger(TokenController.class);

	public UserController() {
	}

	@GetMapping("/showuser")
	public ModelAndView showUsers() {
		mav.setViewName("user/viewusers");
		return mav;
	}

	@GetMapping("/account")
	public ModelAndView showAccount(HttpServletRequest request) throws JsonProcessingException {
		logger.info("Sending request to remote");
		uri = UriComponentsBuilder.fromUriString(userUrl).path("/finduserbyusername/{username}")
				.build(request.getUserPrincipal().getName());
		User user = restClient.get(uri, User.class);
		logger.info("Received response from remote" + objectMapper.writeValueAsString(user));
		mav.addObject("user", user);
		mav.setViewName("user/profile");
		return mav;
	}

	@PostMapping("/account")
	public ModelAndView updateUser(User user) throws JsonProcessingException {
		logger.info("Sending request to update user");
		uri = UriComponentsBuilder.fromUriString(userUrl).path("/save").build().toUri();
		User updatedUser = restClient.post(uri, user, User.class);
		logger.info("Received response from remote" + objectMapper.writeValueAsString(updatedUser));
		mav.addObject("user", updatedUser);
		mav.setViewName("user/profile");
		return mav;
	}

	@GetMapping("/adduser")
	public ModelAndView addUser() {
		mav.setViewName("/user/createuser");
		return mav;
	}

	@PostMapping("/adduser")
	public ModelAndView addUser(User user) throws JsonProcessingException {
		logger.info("Sending request to add user: " + objectMapper.writeValueAsString(user));
		uri = UriComponentsBuilder.fromUriString(userUrl).path("/save").build().toUri();
		restClient.post(uri, user, User.class);
		mav.setViewName("redirect:/showuser");
		return mav;
	}

	@GetMapping("/removeuser/{id}")
	public ModelAndView removeUser(@PathVariable Long id) throws JsonProcessingException {
		logger.info("Sending request to remove user with id: " + id);
		uri = UriComponentsBuilder.fromUriString(userUrl).path("/removeuser/{id}").build(id);
		User user = restClient.get(uri, User.class);
		logger.info("User was removed successfully" + objectMapper.writeValueAsString(user));
		mav.setViewName("redirect:/showuser");
		return mav;
	}

	@PostMapping("/createaccount")
	public ModelAndView createAccount(User user, HttpSession session) throws JsonProcessingException {
		logger.info("Sending request to create account: " + objectMapper.writeValueAsString(user));
		uri = UriComponentsBuilder.fromUriString(userUrl).path("/save").build().toUri();
		User savedUser = restClient.post(uri, user, User.class);
		if (savedUser != null) {
			OTP otp = otpUtil.createOTP(savedUser.getId());
			session.setAttribute("user", savedUser);
			session.setAttribute("otp", otp);
			String message = "Welcome to secure password manager. Please find your otp below:   " + "\n"
					+ otp.getOTP();
			Notification notification = new Notification();
			notification.setCreated(ZonedDateTime.now());
			notification.setDeleted(null);
			notification.setDestination(savedUser.getEmail());
			notification.setMessage(message);
			notification.setUpdated(ZonedDateTime.now());
			notification.setUserId(savedUser.getId());
			sendConfirmationEmail(notification);
			logger.info("Account created succesfully, password link sent");
		}


		mav.setViewName("auth/verifyemail");
		return mav;
	}
	@PostMapping ("/confirm")
	public ModelAndView confirmOTP(@RequestParam("otp") String otp, HttpSession session){
		User user = (User) session.getAttribute("user");
		System.out.println(user.getId());
		OTP userOtp = otpUtil.confirmOTP(otp, user.getId());
		System.out.println(userOtp.getOTP());
		if(userOtp!=null){
			String message = "Email verified, please set your password";
			mav.addObject(message);
			mav.setViewName("/user/setpassword");
		}
		else{
			String message = "Failed to verify token, token may have expired";
			mav.addObject(message);
			mav.setViewName("redirect:/confirm");
		}
		return mav;
	}
	@ModelAttribute("user")
	public User getUser() {
		return new User();
	}

	protected Token createToken(String type, Long userId) {
		return tokenUtil.createToken(type, userId);

	}
	@ModelAttribute("passwordDto")
	public ResetPasswordDto resetPasswordDto() {
		return new ResetPasswordDto();
	}

	protected void sendConfirmationEmail(Notification notification) {
		messenger.sendNotification(notification);
	}

}
