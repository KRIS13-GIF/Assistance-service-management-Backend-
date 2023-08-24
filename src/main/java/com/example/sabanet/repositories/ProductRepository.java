package com.example.sabanet.repositories;

import com.example.sabanet.entities.Customer;
import com.example.sabanet.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {


    Product findProductByFileNumAndCustomerId(int nr, String id);

    @Query(value = "SELECT p.file_num FROM Product p WHERE p.customer_id = ?", nativeQuery = true)
    List<Integer> findFileNumByCustomerId(String customerId);






}
