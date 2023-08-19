package com.example.sabanet.models;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FinishRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private double money;
    private String description;


}
