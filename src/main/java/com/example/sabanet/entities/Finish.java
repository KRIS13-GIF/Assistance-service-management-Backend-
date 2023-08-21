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
public class Finish {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    private Customer customer;

    @ManyToOne
    private Personel personel;

    @OneToOne
    private Ordering ordering;

    private boolean collect; // only acceptance can call
    private double money;
    private String description;

    private int nrFile;
    private boolean repaired;
    private boolean ready;

    private Date finishDate; // take when you click finish




}
