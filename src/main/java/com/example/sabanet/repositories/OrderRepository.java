package com.example.sabanet.repositories;


import com.example.sabanet.entities.Ordering;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Ordering, String> {

}
