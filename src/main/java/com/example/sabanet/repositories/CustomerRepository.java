package com.example.sabanet.repositories;

import com.example.sabanet.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, String> {




}
