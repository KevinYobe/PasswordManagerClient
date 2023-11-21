package com.passwordmanager.client.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.passwordmanager.client.dto.ErrorDto;
import com.passwordmanager.client.model.WorkResource;
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
public class WorkResourcesController {
    private final Logger logger = LoggerFactory.getLogger(WorkResourcesController.class);

    @Value("${workresource.baseurl}")
    private String workResourceUrl;
    private URI uri;
    private RestClient<WorkResource> restClient;
    private ObjectMapper objectMapper;
    private ModelAndView mav = new ModelAndView();
    private WorkResource userWorkResource;
    private ErrorDto errorDto;

    @Autowired
    public void setRestClient(RestClient<WorkResource> restClient) {
        this.restClient = restClient;
    }
    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
    @Autowired
    public void setUserWorkResource(WorkResource userWorkResource) {
        this.userWorkResource = userWorkResource;
    }

    @Autowired
    public void setErrorDto(ErrorDto errorDto) {
        this.errorDto = errorDto;
    }
    @ModelAttribute("workResources")
    public List<?> getWorkResources(HttpServletRequest request, WorkResource workResource) throws JsonProcessingException {
        logger.info("Sending request to remote to get WorkResources" + objectMapper.writeValueAsString(request.getRemoteUser()));
        uri = UriComponentsBuilder.fromUriString(workResourceUrl).path("/getuserworkresource/{username}")
                .build(request.getUserPrincipal().getName());
        List<?> workResources = restClient.getAll(uri, WorkResource[].class);
        logger.info("Received response from remote:" + objectMapper.writeValueAsString(workResources));
        return workResources;
    }

    @PostMapping("/saveworkresource")
    public ModelAndView saveWorkResource(HttpServletRequest request, WorkResource workResource, BindingResult bindingResult) throws JsonProcessingException {
        logger.info("Request to add a new WorkResource");
        deleteWorkResource(workResource);
        System.out.println(workResource.getUsername());
        uri = UriComponentsBuilder.fromUriString(workResourceUrl).path("/addworkresource/{username}")
                .build(request.getUserPrincipal().getName());
        logger.info("Sending request to remote to save WorkResource" + objectMapper.writeValueAsString(workResource.toString()));
        WorkResource savedWorkResource = restClient.post(uri, workResource, WorkResource.class);
        if (savedWorkResource ==null){
            errorDto.setErrorMessage("Failed to add WorkResource. Please contact support if this persists");
            mav.addObject(errorDto);
            mav.setViewName("redirect:/viewworkresource");
        }
        logger.info("Received response from remote, WorkResource saved successfully" + objectMapper.writeValueAsString(savedWorkResource));
        errorDto.setErrorMessage("Successfully added WorkResource");
        mav.addObject(errorDto);
        mav.setViewName("redirect:/viewworkresource");
        return mav;
    }

    @GetMapping("/viewworkresource")
    public ModelAndView viewWorkResources(HttpServletRequest request) {
        mav.setViewName("password/viewworkresource");
        return mav;
    }

    @ModelAttribute("workResource")
    public WorkResource addWorkResource() {
        return new WorkResource();
    }

    @GetMapping("/showeditworkresource")
    public ModelAndView getWorkResource(WorkResource userWorkResource) {
        uri = UriComponentsBuilder.fromUriString(workResourceUrl).path("/getworkresource/{id}").build(4);
        WorkResource workResource = restClient.get(uri, WorkResource.class);
        mav.addObject(workResource);
        mav.setViewName("password/editworkresource");
        return mav;
    }

    @GetMapping("/removeworkresource/{id}")
    public ModelAndView removeWorkResource(@PathVariable("id") Long id, HttpServletRequest request) throws JsonProcessingException {
        logger.info("Sending request to remote to delete WorkResource" + objectMapper.writeValueAsString(request.getUserPrincipal()));
        uri = UriComponentsBuilder.fromUriString(workResourceUrl).path("/removeworkresource/{id}/{username}").build(id,request.getUserPrincipal().getName());
        WorkResource workResource1 = restClient.get(uri, WorkResource.class);
        if (workResource1 != null) {
            mav.addObject("message", "WorkResource was deleted successfully");
            logger.info("Received response from remote, WorkResource deleted successfully:" + objectMapper.writeValueAsString(workResource1));
            mav.setViewName("redirect:/viewworkresource");
        }
        mav.setViewName("redirect:/viewworkresource");
        return mav;
    }

    @PostMapping("/editworkresource")
    public ModelAndView editWorkResource(WorkResource workResource) throws JsonProcessingException {
        logger.info("Sending request to remote to edit WorkResource" + objectMapper.writeValueAsString(workResource));
        uri = UriComponentsBuilder.fromUriString(workResourceUrl).path("/editworkresource")
                .build().toUri();
        WorkResource editedWorkResource = restClient.post(uri, workResource, WorkResource.class);
        mav.setViewName("redirect:/viewworkresources");
        logger.info("Received response from remote, WorkResource edited successfully:" + objectMapper.writeValueAsString(editedWorkResource));
        return mav;
    }
    private void deleteWorkResource(WorkResource workResource) {
        userWorkResource.setOrganisation(workResource.getOrganisation());
        userWorkResource.setCreated(ZonedDateTime.now());
        userWorkResource.setDeleted(null);
        userWorkResource.setUrl(workResource.getUrl());
        userWorkResource.setResource(workResource.getResource());
        userWorkResource.setPassword(workResource.getPassword());
    }
}
