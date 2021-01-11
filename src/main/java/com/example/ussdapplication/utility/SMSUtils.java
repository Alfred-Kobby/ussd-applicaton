package com.example.ussdapplication.utility;
import com.example.ussdapplication.utility.ApplicationProperties;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
public class SMSUtils {

    @Autowired
    ApplicationProperties applicationProperties;

    public void sendSMS(String to, String message){
        OkHttpClient client = new OkHttpClient();
        log.info("Processing SMS");
        Request request = new Request.Builder()
                .url(applicationProperties.getSmsEndpoint()+"send?"
                        + "api_key="+applicationProperties.getGabbageCollectionSMSApikey()+"&"
                        + "merchant_id="+applicationProperties.getGabbageCollectionSMSMerchantId()+"&"
                        + "async="+applicationProperties.getAsync()+"&"
                        + "message="+message+"&"
                        + "recipients="+to+"&"
                        + "source="+applicationProperties.getSource())
                .get()

                .addHeader("cache-control", "no-cache")
                .addHeader("postman-token", "ab080853-0ea6-c641-ab48-a20aa4ab0434")
                .build();
        log.info("{}",request.httpUrl());
        log.info("Completed processing SMS");
        try {
            Response response = client.newCall(request).execute();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            log.error("exception thrown {}", e.getMessage());
        }
        return;
    }
}
