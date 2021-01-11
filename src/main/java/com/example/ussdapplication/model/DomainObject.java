package com.example.ussdapplication.model;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown = true)
public interface DomainObject extends Serializable{
    String getKey();
    String getObjectKey();
}
