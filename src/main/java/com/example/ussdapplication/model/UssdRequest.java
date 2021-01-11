package com.example.ussdapplication.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Entity;

@Entity
public class UssdRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String msisdn;
    private String requestType;
    private String sessionId;
    private String message;
    private String currentMenu;
    private String operator;
    private String userInput;
    private String shortCode;



    public UssdRequest() {
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public Long getId() {
        return id;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getCurrentMenu() {
        return currentMenu;
    }

    public void setCurrentMenu(String currentMenu) {
        this.currentMenu = currentMenu;
    }

    public String getUserInput() {
        return userInput;
    }

    public void setUserInput(String userInput) {
        this.userInput = userInput;
    }

    public String getShortCode() {
        return shortCode;
    }

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }

    @Override
    public String toString() {
        return "UssdRequest{" +
                "id=" + id +
                ", msisdn='" + msisdn + '\'' +
                ", sessionId='" + sessionId + '\'' +
                ", requestType='" + requestType + '\'' +
                ", message='" + message + '\'' +
                ", operator='" + operator + '\'' +
                ", currentMenu=" + currentMenu +
                ", userInput='" + userInput + '\'' +
                ", shortCode='" + shortCode + '\'' +
                '}';
    }
}
