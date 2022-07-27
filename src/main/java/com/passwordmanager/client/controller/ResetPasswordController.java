package com.passwordmanager.client.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.passwordmanager.client.dto.ResetPasswordDto;
import com.passwordmanager.client.model.Login;
import com.passwordmanager.client.model.OTP;
import com.passwordmanager.client.model.Token;
import com.passwordmanager.client.model.User;
import com.passwordmanager.client.rest.RestClientImpl;
import com.passwordmanager.client.util.OTPUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpSession;
import java.net.URI;


@Controller
public class ResetPasswordController {

    private RestClientImpl<Login> restClient;

    @Value("${login.baseurl}")
    private String tokenUrl;

    @Value("${login.baseurl}")
    private String loginUrl;

    private ModelAndView mav = new ModelAndView();

    private URI uri;

    private String message;

    private ObjectMapper objectMapper;

    private final Logger logger = LoggerFactory.getLogger(TokenController.class);

    private OTPUtil otpUtil;

    @Autowired
    public void setOtpUtil(OTPUtil otpUtil) {
        this.otpUtil = otpUtil;
    }

    @Autowired
    public void setRestClient(RestClientImpl<Login> restClient) {
        this.restClient = restClient;
    }
    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @PostMapping("/setpassword")
    public ModelAndView setPassword(ResetPasswordDto passwordDto, HttpSession session) throws JsonProcessingException {
        OTP otp = (OTP) session.getAttribute("otp");
        passwordDto.setUserId(otp.getUserId());
        logger.info("Sending request to remote to reset password" + objectMapper.writeValueAsString(passwordDto));
        uri = UriComponentsBuilder.fromUriString(tokenUrl).path("/resetpassword/{userId}/{username}").build(passwordDto.getUserId(), passwordDto.getPassword());
        logger.info(uri.toString());
        Login login = restClient.get(uri, Login.class);

        if (login != null) {
            message = "Account Created, please proceed to login";
            mav.addObject(message);
            mav.setViewName("/auth/login");
            logger.info("Password reset was successfull" + objectMapper.writeValueAsString(login));
        } else {
            message = "Failed to set password, please contact support";
            logger.error(message);
            mav.addObject(message);
            mav.setViewName("/auth/login");
        }

        return mav;
    }
    @GetMapping("/resetpassword")
    public ModelAndView resetPassword(){
       mav.setViewName("auth/resetpassword");
       return mav;
    }


    @ModelAttribute("passwordDto")
    public ResetPasswordDto resetPasswordDto() {
        return new ResetPasswordDto();
    }

}
