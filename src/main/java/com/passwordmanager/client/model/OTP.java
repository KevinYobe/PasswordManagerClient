package com.passwordmanager.client.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.ZonedDateTime;
@JsonIgnoreProperties(ignoreUnknown = true)
public class OTP {
    private Long id;

    private Long userId;

    private boolean expired;

    private String OTP;

    private String otpType;
    private ZonedDateTime created;

    private ZonedDateTime updated;

    private ZonedDateTime deleted;

    public OTP() {
        // TODO Auto-generated constructor stub
    }
    public Long getUserId() {
        return userId;
    }
    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getOTP() {
        return OTP;
    }

    public void setOTP(String OTP) {
        this.OTP = OTP;
    }

    public String getOTPType() {
        return otpType;
    }

    public void setOTPType(String otpType) {
        this.otpType = otpType;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getCreated() {
        return created;
    }

    public void setCreated(ZonedDateTime created) {
        this.created = created;
    }

    public ZonedDateTime getUpdated() {
        return updated;
    }

    public void setUpdated(ZonedDateTime updated) {
        this.updated = updated;
    }

    public ZonedDateTime getDeleted() {
        return deleted;
    }

    public void setDeleted(ZonedDateTime deleted) {
        this.deleted = deleted;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{")
                .append("\n")
                .append(getId() + "\n")
                .append(getOTP() + "\n")
                .append(getOTPType() + "\n")
                .append(getCreated().toString() + "\n")
                .append(getDeleted().toString() + "\n")
                .append(getDeleted().toString() + "\n")
                .append("}");
        return sb.toString();
    }

}
