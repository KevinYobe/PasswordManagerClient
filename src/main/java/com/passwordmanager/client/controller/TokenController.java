package com.passwordmanager.client.controller;

import java.net.URI;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.passwordmanager.client.dto.ResetPasswordDto;
import com.passwordmanager.client.model.Token;
import com.passwordmanager.client.rest.TokenRestClient;

@Controller
public class TokenController {

	@Autowired
	private TokenRestClient tokenRestClient;

	@Value("${token.baseurl}")
	private String tokenUrl;

	private ModelAndView mav = new ModelAndView();

	private URI uri;

	private final Logger logger = LoggerFactory.getLogger(TokenController.class);

	String message = "";

	@Autowired
	private ObjectMapper objectMapper;

	@GetMapping("/confirmtoken/{token}")
	public ModelAndView confirmToken(@PathVariable("token") String token, HttpSession session)
			throws JsonProcessingException {

		uri = UriComponentsBuilder.fromUriString(tokenUrl).path("/findbyToken/{token}").build(token);
		logger.info("Sending request to confirm token" + token);
		Token savedToken = tokenRestClient.get(uri);
		if (savedToken != null) {
			message = "Email verified, please proceed to login";
			logger.info("Received token from remote" + objectMapper.writeValueAsString(savedToken));
			session.setAttribute("token", savedToken);
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

	@PostMapping("/setpassword")
	public ModelAndView setPassword(ResetPasswordDto passwordDto, HttpSession session) throws JsonProcessingException {
		Token token = (Token) session.getAttribute("token");

		passwordDto.setToken(token.getToken());
		passwordDto.setUserId(token.getUserId());
		logger.info("Sending request to remote to reset password" + objectMapper.writeValueAsString(passwordDto));
		uri = UriComponentsBuilder.fromUriString(tokenUrl).path("/createlogin").build().toUri();
		Token createdToken = tokenRestClient.post(uri, passwordDto);

		if (createdToken != null) {
			message = "Account Created, please proceed to login";
			mav.addObject(message);
			mav.setViewName("/auth/login");
			logger.info("Password reset was successfull" + objectMapper.writeValueAsString(createdToken));
		} else {
			message = "Failed to set pasword please contact support";
			logger.error(message);
			mav.addObject(message);
			mav.setViewName("/auth/login");
		}

		return mav;
	}

	@ModelAttribute("passwordDto")
	public ResetPasswordDto resetPasswordDto() {
		return new ResetPasswordDto();
	}

}