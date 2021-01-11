package com.example.ussdapplication.repository;

import com.example.ussdapplication.model.DomainObject;

import java.util.List;
public interface RedisRepository <V extends DomainObject>{
    void put(V obj);
    V get(V key);
    void delete(V key);
    List<V> getObjects();
}
