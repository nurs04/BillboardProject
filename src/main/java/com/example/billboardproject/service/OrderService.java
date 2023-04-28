package com.example.billboardproject.service;

import com.example.billboardproject.model.Order;

import java.util.List;

public interface OrderService {

    boolean addOrder(Order order);

    List<Order> getAllOrdersByUserId(Long id);
}
