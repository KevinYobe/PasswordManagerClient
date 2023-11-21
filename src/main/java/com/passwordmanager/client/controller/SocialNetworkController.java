package com.passwordmanager.client.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.passwordmanager.client.dto.ErrorDto;
import com.passwordmanager.client.model.SocialNetwork;
import com.passwordmanager.client.rest.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.time.ZonedDateTime;
import java.util.List;

@Controller
public class SocialNetworkController {
    @Value("${socialnetwork.baseurl}")
    private String socialnetworkUrl;

    RestClient<SocialNetwork> restClient;

    private SocialNetwork userSocialNetwork;

    private ModelAndView mav = new ModelAndView();

    private URI uri;

    private final Logger logger = LoggerFactory.getLogger(SocialNetworkController.class);

    ErrorDto errorDto;

    private ObjectMapper objectMapper;

    @Autowired
    public void setRestClient(RestClient<SocialNetwork> restClient) {
        this.restClient = restClient;
    }
    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
    @Autowired
    public void setUserSocialNetwork(SocialNetwork userSocialNetwork) {
        this.userSocialNetwork = userSocialNetwork;
    }
    @Autowired
    public void setErrorDto(ErrorDto errorDto) {
        this.errorDto = errorDto;
    }
    @PostMapping("/savesocialnetwork")
    public ModelAndView savesocialnetwork(HttpServletRequest request, SocialNetwork socialnetwork, BindingResult bindingResult) throws JsonProcessingException {
        logger.info("Request to add a new social network resource");
        deletesocialnetwork(socialnetwork);
        uri = UriComponentsBuilder.fromUriString(socialnetworkUrl).path("/addsocialnetwork/{username}")
                .build(request.getUserPrincipal().getName());
        logger.info("Sending request to remote to save socialnetwork" + objectMapper.writeValueAsString(socialnetwork.toString()));
        SocialNetwork savedSocialNetwork = restClient.post(uri, socialnetwork, SocialNetwork.class);
        if (savedSocialNetwork ==null){
            errorDto.setErrorMessage("Failed to add social network resource. Please contact support if this persists");
            mav.addObject(errorDto);
            mav.setViewName("redirect:/viewsocialnetwork");
        }
        logger.info("Received response from remote, socialnetwork saved successfully" + objectMapper.writeValueAsString(savedSocialNetwork));
        errorDto.setErrorMessage("Successfully added socialnetwork");
        mav.addObject(errorDto);
        mav.setViewName("redirect:/viewsocialnetwork");
        return mav;

    }
    @GetMapping("/viewsocialnetwork")
    public ModelAndView viewsocialnetworks(HttpServletRequest request) {
        mav.setViewName("password/viewsocialnetwork");
        return mav;
    }
    @ModelAttribute("socialNetworks")
    public List<?> getSocialNetworks(HttpServletRequest request, SocialNetwork socialnetwork) throws JsonProcessingException {
        logger.info("Sending request to remote to get social networks for " + objectMapper.writeValueAsString(request.getRemoteUser()));
        uri = UriComponentsBuilder.fromUriString(socialnetworkUrl).path("/getusersocialnetwork/{username}")
                .build(request.getUserPrincipal().getName());
        List<?> socialnetworks = restClient.getAll(uri, SocialNetwork[].class);
        logger.info("Received response from remote:" + objectMapper.writeValueAsString(socialnetworks));
        return socialnetworks;
    }
    @ModelAttribute("socialnetwork")
    public SocialNetwork addSocialNetwork() {
        return new SocialNetwork();
    }
    @GetMapping("/showeditsocialnetwork")
    public ModelAndView getSocialNetwork(SocialNetwork usersocialnetwork) {
        uri = UriComponentsBuilder.fromUriString(socialnetworkUrl).path("/getsocialnetwork/{id}").build(4);
        SocialNetwork socialNetwork = restClient.get(uri, SocialNetwork.class);
        mav.addObject(socialNetwork);
        mav.setViewName("password/editsocialnetwork");
        return mav;
    }

    @GetMapping("/removesocialnetwork/{id}")
    public ModelAndView removeSocialNçetwork(@PathVariable("id") Long id, HttpServletRequest request) throws JsonProcessingException {
        logger.info("Sending request to remote to delete socialnetwork" + objectMapper.writeValueAsString(request.getUserPrincipal()));
        uri = UriComponentsBuilder.fromUriString(socialnetworkUrl).path("/removesocialnetwork/{id}/{username}").build(id,request.getUserPrincipal().getName());
        SocialNetwork socialNetwork = restClient.get(uri, SocialNetwork.class);
        if (socialNetwork != null) {
            mav.addObject("message", "social network was deleted successfully");
            logger.info("Received response from remote, social network deleted successfully:" + objectMapper.writeValueAsString(socialNetwork));
            mav.setViewName("redirect:/viewsocialnetwork");
        }
        mav.setViewName("redirect:/viewsocialnetwork");
        return mav;
    }
    @PostMapping("/editsocialnetwork")
    public ModelAndView editsocialnetwork(SocialNetwork socialNetwork) throws JsonProcessingException {
        logger.info("Sending request to remote to edit socialnetwork" + objectMapper.writeValueAsString(socialNetwork));
        uri = UriComponentsBuilder.fromUriString(socialnetworkUrl).path("/editsocialnetwork")
                .build().toUri();
        SocialNetwork editedsocialnetwork = restClient.post(uri, socialNetwork, SocialNetwork.class);
        mav.setViewName("redirect:/viewsocialnetwork");
        logger.info("Received response from remote, social network edited successfully:" + objectMapper.writeValueAsString(editedsocialnetwork));
        return mav;
    }
    private void deletesocialnetwork(SocialNetwork socialNetwork) {
        userSocialNetwork.setUrl(socialNetwork.getUrl());
        userSocialNetwork.setCreated(ZonedDateTime.now());
        userSocialNetwork.setDeleted(ZonedDateTime.now());
        userSocialNetwork.setResourceType(socialNetwork.getResourceType());
        userSocialNetwork.setResource(socialNetwork.getResource());
        userSocialNetwork.setUsername(socialNetwork.getUsername());
    }
}
