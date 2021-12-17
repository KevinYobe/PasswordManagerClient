package com.passwordmanager.client.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class AbstractWebController implements WebController{

	@Override
	public HttpSession getSession(HttpServletRequest request) {
		return request.getSession();
	}

	@Override
	public Object getFromSession(HttpServletRequest request, String objectName) {
		return getSession(request).getAttribute(objectName);
	}

}
