package com.passwordmanager.client.controller;

import java.net.URI;

import javax.servlet.http.HttpSession;

import com.passwordmanager.client.model.OTP;
import com.passwordmanager.client.rest.RestClientImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.passwordmanager.client.dto.ResetPasswordDto;

@Controller
public class TokenController {
	private RestClientImpl<OTP> restClient;
	@Value("${token.baseurl}")
	private String tokenUrl;
	private ModelAndView mav = new ModelAndView();
	private URI uri;
	private final Logger logger = LoggerFactory.getLogger(TokenController.class);
	String message = "";
	private ObjectMapper objectMapper;
	@Autowired
	public void setObjectMapper(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}
	@Autowired
	public void setRestClient(RestClientImpl<OTP> restClient) {
		this.restClient = restClient;
	}

	@GetMapping("/confirmtoken/{token}")
	public ModelAndView confirmToken(@PathVariable("otp") OTP otp, HttpSession session)
			throws JsonProcessingException {

		uri = UriComponentsBuilder.fromUriString(tokenUrl).path("/findbyOtp/{userId}/{otp}").build(otp.getUserId(),otp.getOTP());
		logger.info("Sending request to confirm token" + otp);
		OTP otpFromRemote = restClient.get(uri, OTP.class);
		if (otpFromRemote != null) {
			message = "Email verified, please proceed to login";
			logger.info("Received token from remote" + objectMapper.writeValueAsString(otpFromRemote));
			session.setAttribute("token", otpFromRemote);
			mav.addObject("message", message);
			mav.setViewName("/user/setpassword");
		}
		else {
			message = "Token has expired, may please reset your password";
			mav.addObject("message", message);
			mav.setViewName("redirect:/login");
		}
		return mav;
	}

	@ModelAttribute("passwordDto")
	public ResetPasswordDto resetPasswordDto() {
		return new ResetPasswordDto();
	}

}
