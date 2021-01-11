package com.example.ussdapplication.controller;

import com.example.ussdapplication.model.*;
import com.example.ussdapplication.repository.RedisUssdSessionRepository;
import com.example.ussdapplication.service.UserService;
import com.example.ussdapplication.utility.ApplicationProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping(path="/ussd")
public class UssdController {
    // Variable holding the ussd response

    @Autowired
    private UssdController requestService;
    @Autowired
    private UserService userService;

    @Autowired
    ApplicationProperties applicationProperties;

    @Autowired
    RedisUssdSessionRepository redisUssdSessionRepository;

    @Autowired
    userInputProcessor userInputProcessor;

    UssdResponse ussdResponse = new UssdResponse();

    @PostMapping(path = "/add")
    public @ResponseBody
    String addNewUser(@RequestParam String name, @RequestParam String accNum) {
        User newUser = new User(accNum, name);
        userService.addAccount(newUser);
        return "Added new customer successfully";
    }


//    @GetMapping(path="/find")
//    public Optional<User> findUser(@RequestBody UssdRequest ussdRequest) {
//        Optional<User> userAccount = userService.getAccount(ussdRequest.getUserInput());
//        return userAccount;
//    }


    @PostMapping(path = "/request")
    public UssdResponse request(@RequestBody UssdRequest ussdRequest) {

        if (ussdRequest != null) {

            UssdSession tmpUssdSession = new UssdSession();
            tmpUssdSession.setSessionId(ussdRequest.getSessionId());
            tmpUssdSession.setMsisdn(ussdRequest.getMsisdn());
            UssdSession existingSession = redisUssdSessionRepository.get(tmpUssdSession);

            UssdSession udUssdSession = null;
            if (existingSession != null) {
                udUssdSession = redisUssdSessionRepository.getShow("USSD_APP" + ussdRequest.getMsisdn());
            } else {
                udUssdSession = new UssdSession();
                udUssdSession.setSessionId(ussdRequest.getSessionId());
                udUssdSession.setMsisdn(ussdRequest.getMsisdn());
            }

            if (ussdRequest.getRequestType().equalsIgnoreCase(RequestType.initiation)) {
                ussdResponse = userInputProcessor.displayRoot(ussdRequest,udUssdSession);
            } else if (ussdRequest.getRequestType().equalsIgnoreCase(RequestType.existing)) {
                log.info("Client state {}", udUssdSession.getClientState());
                ussdResponse = userInputProcessor.processRoot(udUssdSession, ussdRequest);

            }

        }
        return ussdResponse;
    }
}