package com.example.sabanet.models;


import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.Date;

@Data
public class ProductRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String serialNo;
    private String brand;
    private String template;
    private String description;
    private String notes;
    private String password;
    private String fullAddress;




}
