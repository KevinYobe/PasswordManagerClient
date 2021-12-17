package com.passwordmanager.client.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;

@Controller
public interface WebController {
	
	public HttpSession getSession(HttpServletRequest request);
	public Object getFromSession(HttpServletRequest request, String objectName);

}
