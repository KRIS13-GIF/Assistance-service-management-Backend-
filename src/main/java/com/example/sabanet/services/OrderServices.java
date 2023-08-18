package com.example.sabanet.services;

import com.example.sabanet.repositories.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderServices {

    private final OrderRepository orderRepository;

    public OrderServices(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }


}
