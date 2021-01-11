package com.example.ussdapplication.service;

import com.example.ussdapplication.model.*;
import com.example.ussdapplication.utility.ApplicationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Optional;

@Service
public class ServiceResponse {

    @Autowired
    ApplicationProperties applicationProperties;

    public  UssdResponse initResponse(UssdRequest ussdRequest) {
        UssdResponse ussdResponse = new UssdResponse(ussdRequest.getMsisdn(), applicationProperties.getAccountNumber(), RequestType.existing, ussdRequest.getSessionId(), ClientState.root);
        return ussdResponse;
    }



    public UssdResponse userNotFoundResponse(UssdRequest ussdRequest) {
        UssdResponse userNotFound = new UssdResponse(ussdRequest.getMsisdn(), applicationProperties.getInvalidAccountNumber() + applicationProperties.getAccountNumber(), RequestType.existing, ussdRequest.getSessionId(), ClientState.userNotFound);
        return userNotFound;
    }



    public UssdResponse confirmUser(Optional<User> acc, UssdRequest ussdRequest) {
        UssdResponse confirmUser = new UssdResponse(ussdRequest.getMsisdn(), MessageFormat.format(applicationProperties.getConfirmUser(),acc.get().getAccNumber(),acc.get().getName()), RequestType.existing, ussdRequest.getSessionId(), ClientState.confirm);
        return confirmUser;
    }

    public UssdResponse userConfirmation(UssdRequest ussdRequest){
        UssdResponse userConfirmation = new UssdResponse(ussdRequest.getMsisdn(),MessageFormat.format(applicationProperties.getAmount(),10, 500),RequestType.existing,ussdRequest.getSessionId(),ClientState.amount);
        return userConfirmation;

    }

    public UssdResponse invalidAmountResponse(UssdRequest ussdRequest){
        UssdResponse invalid = new UssdResponse(ussdRequest.getMsisdn(),MessageFormat.format(applicationProperties.getInvalidAmount(),10, 500),RequestType.existing,ussdRequest.getSessionId(),ClientState.amount);
        return invalid;

    }
    public UssdResponse validAmountResponse(UssdRequest ussdRequest, String amount){
        UssdResponse valid = new UssdResponse(ussdRequest.getMsisdn(),MessageFormat.format(applicationProperties.getConfirmPayOrCancel(),amount), RequestType.existing,ussdRequest.getSessionId(),ClientState.pay);
        return valid;

    }

    public  UssdResponse cancelResponse(UssdRequest ussdRequest) {
        UssdResponse ussdResponse = new UssdResponse(ussdRequest.getMsisdn(), applicationProperties.getCancel(), RequestType.cleanup, ussdRequest.getSessionId(), ClientState.root);
        return ussdResponse;
    }

    public  UssdResponse finalResponse(UssdRequest ussdRequest) {
        UssdResponse ussdResponse = new UssdResponse(ussdRequest.getMsisdn(), applicationProperties.getEnd(), RequestType.cleanup, ussdRequest.getSessionId(), ClientState.root);
        return ussdResponse;
    }
}
