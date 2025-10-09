package com.order.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.order.service.Inventory;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private Inventory inv;

    @GetMapping("/all")
    public List<String> getReceivedOrders() {
        return inv.getMessage();
    }
}
