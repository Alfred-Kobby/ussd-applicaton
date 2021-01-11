package com.example.ussdapplication.utility;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import lombok.Data;

@Component
@Data
@ConfigurationProperties
public class ApplicationProperties {
    private String delaybeforepush;
    private int ussdsessionTimeout;


//    SMS
    private String smsEndpoint;
    private String gabbageCollectionSMSApikey;
    private String gabbageCollectionSMSMerchantId;
    private String async;
    private String source;

//    Messages
    private String welcome;
    private String accountNumber;
    private String invalidAccountNumber;
    private String confirmUser;
    private String amount;
    private String invalidAmount;
    private String confirmPayOrCancel;
    private String smsMessage;
    private String cancel;
    private String end;
    private String selectOption;

//    redis
    private String redisHost;
    private String redisPort;
    private String redisPassword;


}
