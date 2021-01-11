package com.example.ussdapplication.repository;

import com.example.ussdapplication.model.UssdRequest;
import org.springframework.data.repository.CrudRepository;

public interface UssdRequestRepository extends CrudRepository<UssdRequest, String> {
}
