package com.example.sabanet.models;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateRequestProduct {
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

    private String fileName;

    private int nr;
}
