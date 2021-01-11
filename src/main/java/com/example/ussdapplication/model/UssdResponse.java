package com.example.ussdapplication.model;

public class UssdResponse {
    private String msisdn;
    private String menuContent;
    private String requestType;
    private String sessionId;
    private String currentMenu;


    public UssdResponse() {
    }

    public UssdResponse(String msisdn, String menuContent, String requestType, String sessionId, String currentMenu) {
        this.msisdn = msisdn;
        this.menuContent = menuContent;
        this.requestType = requestType;
        this.sessionId = sessionId;
        this.currentMenu = currentMenu;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getMenuContent() {
        return menuContent;
    }

    public void setMenuContent(String menuContent) {
        this.menuContent = menuContent;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getCurrentMenu() {
        return currentMenu;
    }

    public void setCurrentMenu(String currentMenu) {
        this.currentMenu = currentMenu;
    }

    @Override
    public String toString() {
        return "UssdResponse{" +
                "msisdn='" + msisdn + '\'' +
                ", menuContent='" + menuContent + '\'' +
                ", requestType='" + requestType + '\'' +
                ", sessionId='" + sessionId + '\'' +
                ", currentMenu='" + currentMenu + '\'' +
                '}';
    }
}
