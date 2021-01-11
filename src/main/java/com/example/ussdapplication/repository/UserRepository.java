package com.example.ussdapplication.repository;

import com.example.ussdapplication.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String> {
    User findByAccountNumber(String accountNumber);
}
