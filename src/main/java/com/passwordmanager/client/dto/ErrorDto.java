package com.passwordmanager.client.dto;

import org.springframework.stereotype.Component;

@Component
public class ErrorDto {
    private String errorMessage;

    public ErrorDto() {
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
