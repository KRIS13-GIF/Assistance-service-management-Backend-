package com.example.sabanet.models;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FinishResponse {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
}
