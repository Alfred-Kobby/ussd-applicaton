package com.example.ussdapplication.service;
import com.example.ussdapplication.model.User;
import com.example.ussdapplication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public Optional<User> getAccount(String accNum){

        return userRepository.findById(accNum);
    }


    public void addAccount(User user){
        userRepository.save(user);
    }

}
