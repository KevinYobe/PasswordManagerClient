package com.passwordmanager.client.util;

import com.passwordmanager.client.model.OTP;
import com.passwordmanager.client.rest.RestClientImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.xml.parsers.SAXParser;
import java.net.URI;
import java.time.ZonedDateTime;
import java.util.Random;
@Component
public class OTPUtil {

    @Autowired
    private RestClientImpl<OTP> OTPRestClient;

    @Value("${otp.baseurl}")
    private String otpUrl;

    public OTP createOTP(Long userId){
        URI uri = UriComponentsBuilder.fromUriString(otpUrl).path("/save").build().toUri();
        OTP otp = new OTP();
        otp.setOTP(String.format("%06d", new Random().nextInt(999999)));
        otp.setOTPType("CREATE_ACCOUNT");
        otp.setExpired(false);
        otp.setUserId(userId);
        otp.setCreated(ZonedDateTime.now());
        otp.setUpdated(ZonedDateTime.now());
        return OTPRestClient.post(uri,otp,OTP.class);
    }

    public OTP confirmOTP(String otp, Long userId){
        URI uri = UriComponentsBuilder.fromUriString(otpUrl).path("/findbyOtp/{userId}/{otp}").build(userId,otp);
        OTP savedOtp = OTPRestClient.get(uri,OTP.class);
        return savedOtp;
    }
}
