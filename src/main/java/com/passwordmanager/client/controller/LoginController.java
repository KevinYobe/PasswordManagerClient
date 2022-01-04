package com.passwordmanager.client.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;





@Controller
public class LoginController {

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
