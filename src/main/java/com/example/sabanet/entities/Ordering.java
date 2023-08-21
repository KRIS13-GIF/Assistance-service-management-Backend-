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
public class Ordering {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @OneToOne
    private Product product;

    @ManyToOne
    private Customer customer;

    @ManyToOne
    private Personel personel;

    private int fileNumber;
    private boolean repaired;
    private boolean completed;

    private Date repNonRepDate;



}
