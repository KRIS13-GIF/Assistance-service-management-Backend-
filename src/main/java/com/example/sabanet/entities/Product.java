package com.example.sabanet.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @ManyToOne
    private Customer customer;



    private String serialNo;
    private String brand;
    private String template;
    private String description;
    private Date datePurchase;
    private Date expiryDate;
    private String notes;
    private String password;
    private String customerName;
    private String fullAddress;
    private String telephoneNumber;
    private String email;
    private String fiscalCode;
    private String vatNumber;
    private String pec;
    private int fileNum;
    private String filename;


    private boolean accept; // acceptance
    private boolean process;// go to order



}
