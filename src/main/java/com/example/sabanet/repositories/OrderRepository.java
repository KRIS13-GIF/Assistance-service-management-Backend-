package com.example.sabanet.repositories;


import com.example.sabanet.entities.Ordering;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Ordering, String> {

    List<Ordering>findAllByCustomerId(String id);



}
