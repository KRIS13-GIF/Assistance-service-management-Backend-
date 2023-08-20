package com.example.sabanet.repositories;

import com.example.sabanet.entities.Customer;
import com.example.sabanet.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {


    List<Product>findProductByCustomerId(String id);



}
