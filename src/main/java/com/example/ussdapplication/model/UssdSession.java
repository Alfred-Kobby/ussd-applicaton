package com.example.ussdapplication.model;
import java.util.Date;

import lombok.Data;



@Data
public class UssdSession implements DomainObject {
    //Redis specific details
    private static final long serialVersionUID = 1L;
    public static final String OBJECT_KEY = "USSD_APP";

    @Override
    public String getKey() {

        return OBJECT_KEY+msisdn;
        //			return senderAddress+dialogId;
    }
    public void setKey(String msisdn) {
        this.sessionId = OBJECT_KEY+msisdn;
    }
    @Override
    public String getObjectKey() {
        return OBJECT_KEY;
    }

    private Date updated;
    private SessionStatus status;
    //END TRANSACTIONAL DATA
    //START USSD SPECIFIC DATA
    private String msisdn;
    private String sessionId;
    private String clientState;
    private String operator;
    private String network;

    private String accountNumber;
    private String amount;
    private PromptAction prompt;
    private String selectOption;

}
