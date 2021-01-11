package com.example.ussdapplication.repository;
import com.example.ussdapplication.model.Transaction;
import org.springframework.data.repository.CrudRepository;

public interface TransactionRepository extends CrudRepository<Transaction, String>{
}
