package com.example.ussdapplication.model;

import com.example.ussdapplication.repository.RedisUssdSessionRepository;
import com.example.ussdapplication.repository.TransactionRepository;
import com.example.ussdapplication.service.RabbitMqSender;
import com.example.ussdapplication.service.ServiceResponse;
import com.example.ussdapplication.service.UserService;
import com.example.ussdapplication.utility.ApplicationProperties;
import com.example.ussdapplication.utility.SMSUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.Date;

import java.text.MessageFormat;
import java.util.Optional;

@Service
public class userInputProcessor {
    @Autowired
    RedisUssdSessionRepository redisUssdSessionRepository;
    @Autowired
    private ServiceResponse serviceResponse;
    @Autowired
    ApplicationProperties applicationProperties;
    @Autowired
    private UserService userService;
    @Autowired
    RabbitMqSender rabbitMqSender;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private SMSUtils smsUtils;

    public void populateSession(UssdRequest ussdRequest, UssdResponse ussdResponse, UssdSession udUssdSession){
        udUssdSession.setClientState(ussdResponse.getCurrentMenu());
        udUssdSession.setMsisdn(ussdRequest.getMsisdn());
        udUssdSession.setOperator(ussdRequest.getOperator());
        udUssdSession.setSessionId(ussdRequest.getSessionId());
        udUssdSession.setUpdated(new Date());
        redisUssdSessionRepository.put(udUssdSession);
    }


    public UssdResponse displayRoot(UssdRequest ussdRequest, UssdSession udUssdSession)
    {
        UssdResponse ussdResponse;

            ussdRequest.setSessionId(udUssdSession.getKey());
            ussdResponse = serviceResponse.initResponse(ussdRequest);
            udUssdSession.setStatus(SessionStatus.ACTIVE);
            udUssdSession.setPrompt(PromptAction.input);
            populateSession(ussdRequest, ussdResponse, udUssdSession);
            return ussdResponse;

    }

    public Optional<User> findUser(@RequestBody UssdRequest ussdRequest) {
        Optional<User> userAccount = userService.getAccount(ussdRequest.getUserInput());
        return userAccount;
    }

    public UssdResponse processRoot( UssdSession udUssdSession, UssdRequest ussdRequest){
        UssdResponse ussdResponse = new UssdResponse();
        ussdRequest.setSessionId(udUssdSession.getKey());


        switch (ussdRequest.getRequestType()) {
            case RequestType.initiation: {
                System.out.println(ussdRequest.toString());
                System.out.println(ussdResponse.getMenuContent());
                populateSession(ussdRequest, ussdResponse, udUssdSession);
                break;
            }
            case RequestType.cleanup: {
                ussdResponse = serviceResponse.cancelResponse(ussdRequest);
                udUssdSession.setStatus(SessionStatus.INACTIVE);
                populateSession(ussdRequest, ussdResponse, udUssdSession);
                System.out.println(ussdResponse.getMenuContent());
                return ussdResponse;
            }
            case RequestType.existing: {
                System.out.println("Ussd Request " + ussdRequest.toString());
                switch (udUssdSession.getClientState()) {
                    case ClientState.root: {
                        if (!ussdRequest.getUserInput().isEmpty()) {
                            udUssdSession.setStatus(SessionStatus.ACTIVE);
                            udUssdSession.setAccountNumber(ussdRequest.getUserInput());
                            Optional<User> acc = findUser(ussdRequest);
                            while (true) {
                                ussdResponse.setMsisdn(ussdRequest.getMsisdn());
                                if (!acc.isPresent()) {
                                    ussdResponse = serviceResponse.userNotFoundResponse(ussdRequest);
                                    udUssdSession.setPrompt(PromptAction.input);
                                    populateSession(ussdRequest, ussdResponse, udUssdSession);
                                    System.out.println(ussdResponse.getMenuContent());
                                    return ussdResponse;
                                } else {
                                    ussdResponse = serviceResponse.confirmUser(acc, ussdRequest);
                                    udUssdSession.setPrompt(PromptAction.showMenu);
                                    udUssdSession.setSelectOption(applicationProperties.getSelectOption());
                                    populateSession(ussdRequest, ussdResponse, udUssdSession);
                                    System.out.println(ussdResponse.getMenuContent());
                                    break;
                                }
                            }
                        }
                        break;
                    }

                    case ClientState.confirm: {
                        if (!ussdRequest.getUserInput().isEmpty()) {
                            switch (ussdRequest.getUserInput()) {
                                case "0": {
                                    ussdResponse = serviceResponse.cancelResponse(ussdRequest);
                                    udUssdSession.setStatus(SessionStatus.INACTIVE);
                                    udUssdSession.setPrompt(PromptAction.prompt);
                                    populateSession(ussdRequest, ussdResponse, udUssdSession);
                                    System.out.println(ussdResponse.getMenuContent());
                                    return ussdResponse;
                                }
                                case "00": {
                                    ussdResponse = serviceResponse.initResponse(ussdRequest);
                                    udUssdSession.setStatus(SessionStatus.ACTIVE);
                                    udUssdSession.setPrompt(PromptAction.input);
                                    populateSession(ussdRequest, ussdResponse, udUssdSession);
                                    System.out.println(ussdResponse.getMenuContent());
                                    return ussdResponse;

                                }
                                case "1": {
                                    ussdResponse = serviceResponse.userConfirmation(ussdRequest);
                                    udUssdSession.setStatus(SessionStatus.ACTIVE);
                                    udUssdSession.setPrompt(PromptAction.input);
                                    populateSession(ussdRequest, ussdResponse, udUssdSession);
                                    System.out.println(ussdResponse.getMenuContent());
                                    break;
                                }
                                default: {
                                    System.out.println("You entered a wrong input");
                                }
                            }
                        }
                        break;
                    }

                    case ClientState.amount: {
                        if (!ussdRequest.getUserInput().isEmpty()) {
                            udUssdSession.setAmount(ussdRequest.getUserInput());
                            udUssdSession.setStatus(SessionStatus.ACTIVE);
                            Integer amount = Integer.parseInt(ussdRequest.getUserInput());
                            if (amount < 10 || amount > 500) {
                                ussdResponse = serviceResponse.invalidAmountResponse(ussdRequest);
                                udUssdSession.setPrompt(PromptAction.input);
                                populateSession(ussdRequest, ussdResponse, udUssdSession);
                                System.out.println(ussdResponse.getMenuContent());
                                return ussdResponse;
                            } else {
                                ussdResponse = serviceResponse.validAmountResponse(ussdRequest, ussdRequest.getUserInput());
                                udUssdSession.setPrompt(PromptAction.showMenu);
                                udUssdSession.setSelectOption(applicationProperties.getSelectOption());
                                populateSession(ussdRequest, ussdResponse, udUssdSession);
                                System.out.println(ussdResponse.getMenuContent());
                            }
                        }
                        break;
                    }

                    case ClientState.pay: {
                        if (!ussdRequest.getUserInput().isEmpty()) {
                            switch (ussdRequest.getUserInput()) {
                                case "0": {
                                    ussdResponse = serviceResponse.cancelResponse(ussdRequest);
                                    udUssdSession.setStatus(SessionStatus.INACTIVE);
                                    udUssdSession.setPrompt(PromptAction.prompt);
                                    populateSession(ussdRequest, ussdResponse, udUssdSession);
                                    System.out.println(ussdResponse.getMenuContent());
                                    return ussdResponse;
                                }
                                case "1": {
                                    ussdResponse = serviceResponse.finalResponse(ussdRequest);
                                    udUssdSession.setStatus(SessionStatus.INACTIVE);
                                    udUssdSession.setPrompt(PromptAction.prompt);
                                    populateSession(ussdRequest, ussdResponse, udUssdSession);
                                    rabbitMqSender.send(udUssdSession);
                                    System.out.println("Thank you for using our service");
                                    smsUtils.sendSMS("233552342378", MessageFormat.format(applicationProperties.getSmsMessage(), udUssdSession.getAmount(), udUssdSession.getAccountNumber()));
                                    return ussdResponse;
                                }
                            }
                        }
                        break;
                    }

                    default: {
                        System.out.println("Current menu wrong");
                        break;
                    }

                }

            }
            default: {
                System.out.println("Request type wrong");
                break;
            }

        }
        return ussdResponse;
    }
}
