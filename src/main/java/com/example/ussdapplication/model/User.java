package com.example.ussdapplication.model;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {
    @Id
    private String accountNumber;

    private String name;


    public User(){}

    public User(String accNum, String name){
    this.accountNumber = accNum;
    this.name = name;
    }

    public String getAccNumber() {
        return accountNumber;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Account name= '" + name + "'";
    }

}
