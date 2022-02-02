package com.passwordmanager.client.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

	private final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@GetMapping("/login")
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView();
		logger.info("Login request - rendering view");
		mav.setViewName("auth/login");
		return mav;
	}

	@GetMapping("/logout")
	public ModelAndView logout() {
		ModelAndView mav = new ModelAndView();
		logger.info("Logout request - rendering view");
		mav.setViewName("auth/login");
		return mav;
	}

}
