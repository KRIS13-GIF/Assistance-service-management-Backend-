package com.example.sabanet.services;


import com.example.sabanet.entities.Customer;
import com.example.sabanet.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerServices {

    private final CustomerRepository customerRepository;


    public CustomerServices(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void intervent(String id) throws Exception {
        Optional<Customer> customers=customerRepository.findById(id);

        if (customers.isEmpty()){
            throw new Exception("This user does not exist");
        }

        Customer customer=customers.get();
        customer.setIntervent(true);
        customerRepository.save(customer);
        System.out.println("Done");

    }

    public Customer findCustomer(String id){
        return customerRepository.findById(id).get();
    }






}
