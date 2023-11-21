package com.passwordmanager.client.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.passwordmanager.client.dto.ErrorDto;
import com.passwordmanager.client.model.CreditCard;
import com.passwordmanager.client.model.CreditCard;
import com.passwordmanager.client.model.Password;
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
public class CreditCardController {

    private final Logger logger = LoggerFactory.getLogger(CreditCardController.class);

    @Value("${creditCard.baseurl}")
    private String creditCardUrl;
    private URI uri;
    private RestClient<CreditCard> restClient;
    private ObjectMapper objectMapper;
    
    private ModelAndView mav = new ModelAndView();

    private CreditCard userCreditCard;
    
    private ErrorDto errorDto;

    @Autowired
    public void setRestClient(RestClient<CreditCard> restClient) {
        this.restClient = restClient;
    }
    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
    @Autowired
    public void setUserCreditCard(CreditCard userCreditCard) {
        this.userCreditCard = userCreditCard;
    }

    @Autowired
    public void setErrorDto(ErrorDto errorDto) {
        this.errorDto = errorDto;
    }
    @ModelAttribute("creditCards")
    public List<?> getCreditCards(HttpServletRequest request, CreditCard creditCard) throws JsonProcessingException {
        logger.info("Sending request to remote to get CreditCards" + objectMapper.writeValueAsString(request.getRemoteUser()));
        uri = UriComponentsBuilder.fromUriString(creditCardUrl).path("/getUserCreditCard/{username}")
                .build(request.getUserPrincipal().getName());
        List<?> creditCards = restClient.getAll(uri, CreditCard[].class);
        logger.info("Received response from remote:" + objectMapper.writeValueAsString(creditCards));
        return creditCards;
    }

    @PostMapping("/saveCreditCard")
    public ModelAndView saveCreditCard(HttpServletRequest request, CreditCard CreditCard, BindingResult bindingResult) throws JsonProcessingException {
        logger.info("Request to add a new CreditCard");
        deleteCreditCard(CreditCard);
        uri = UriComponentsBuilder.fromUriString(creditCardUrl).path("/addCreditCard/{username}")
                .build(request.getUserPrincipal().getName());
        logger.info("Sending request to remote to save CreditCard" + objectMapper.writeValueAsString(CreditCard.toString()));
        CreditCard savedCreditCard = restClient.post(uri, userCreditCard, CreditCard.class);
        if (savedCreditCard ==null){
            errorDto.setErrorMessage("Failed to add CreditCard. Please contact support if this persists");
            mav.addObject(errorDto);
            mav.setViewName("redirect:/viewcreditcard");
        }
        logger.info("Received response from remote, CreditCard saved successfully" + objectMapper.writeValueAsString(savedCreditCard));
        errorDto.setErrorMessage("Successfully added CreditCard");
        mav.addObject(errorDto);
        mav.setViewName("redirect:/viewcreditcard");
        return mav;

    }

    @GetMapping("/viewcreditcard")
    public ModelAndView viewCreditCards(HttpServletRequest request) {
        mav.setViewName("password/viewcreditcards");
        return mav;
    }

    @ModelAttribute("creditcard")
    public CreditCard addCreditCard() {
        return new CreditCard();
    }

    @GetMapping("/showeditCreditCard")
    public ModelAndView getCreditCard(CreditCard userCreditCard) {
        uri = UriComponentsBuilder.fromUriString(creditCardUrl).path("/getCreditCard/{id}").build(4);
        CreditCard creditCard = restClient.get(uri, CreditCard.class);
        mav.addObject(creditCard);
        mav.setViewName("password/editcreditcard");
        return mav;
    }

    @GetMapping("/removecreditcard/{id}")
    public ModelAndView removeCreditCard(@PathVariable("id") Long id, HttpServletRequest request, CreditCard creditCard) throws JsonProcessingException {
        logger.info("Sending request to remote to delete CreditCard" + objectMapper.writeValueAsString(request.getUserPrincipal()));
        uri = UriComponentsBuilder.fromUriString(creditCardUrl).path("/removeCreditCard/{id}/{username}").build(id,request.getUserPrincipal().getName());
        CreditCard creditCard1 = restClient.get(uri, CreditCard.class);
        if (creditCard1 != null) {
            mav.addObject("message", "CreditCard was deleted successfully");
            logger.info("Received response from remote, CreditCard deleted successfully:" + objectMapper.writeValueAsString(creditCard1));
            mav.setViewName("redirect:/viewCreditCard");
        }
        mav.setViewName("redirect:/viewCreditCard");
        return mav;
    }

    @PostMapping("/editcreditcard")
    public ModelAndView editCreditCard(CreditCard creditCard) throws JsonProcessingException {
        logger.info("Sending request to remote to edit CreditCard" + objectMapper.writeValueAsString(creditCard));
        uri = UriComponentsBuilder.fromUriString(creditCardUrl).path("/editCreditCard")
                .build().toUri();
        CreditCard editedCreditCard = restClient.post(uri, creditCard, CreditCard.class);
        mav.setViewName("redirect:/viewCreditCard");
        logger.info("Received response from remote, CreditCard edited successfully:" + objectMapper.writeValueAsString(editedCreditCard));
        return mav;
    }
    private void deleteCreditCard(CreditCard creditCard) {
        userCreditCard.setCardNumber(creditCard.getCardNumber());
        userCreditCard.setCreated(ZonedDateTime.now());
        userCreditCard.setDeleted(ZonedDateTime.now());
        userCreditCard.setName(creditCard.getName());
        userCreditCard.setType(creditCard.getType());
        userCreditCard.setPin(creditCard.getPin());
    }
}
