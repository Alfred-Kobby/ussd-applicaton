package com.example.ussdapplication.service;

import com.example.ussdapplication.model.UssdRequest;
import com.example.ussdapplication.repository.UssdRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UssdRequestService {
    @Autowired
    private UssdRequestRepository requestRepository;


    public List<UssdRequest> getAllUssdRequests(){
        List<UssdRequest> ussdRequests = new ArrayList<>();
        requestRepository.findAll()
                .forEach (ussdRequests::add);
        return ussdRequests;

    }

    public Optional<UssdRequest> getUssdRequest(String id){
        return requestRepository.findById(id);
    }

    public void addUssdRequest(UssdRequest ussdRequest){
        requestRepository.save(ussdRequest);
    }
    public void updateUssdRequest(String id, UssdRequest ussdRequest){

        requestRepository.save(ussdRequest);

    }
    public void deleteUssdRequest(String id){
        requestRepository.deleteById(id);
    }
}
