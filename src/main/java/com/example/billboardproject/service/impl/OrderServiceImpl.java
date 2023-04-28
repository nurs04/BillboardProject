package com.example.billboardproject.service.impl;

import com.example.billboardproject.model.Order;
import com.example.billboardproject.repository.OrderRepository;
import com.example.billboardproject.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl  implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public List<Order> getAllOrdersByUserId(Long id) {
        return orderRepository.findOrdersByUser_Id(id);
    }

    @Override
    public boolean addOrder(Order order) {
        boolean check = false;
        if (order != null) {
            orderRepository.save(order);
            check = true;
        }

        return true;
    }
}
