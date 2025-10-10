package com.OrderTest.EmpOrderService.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.OrderTest.EmpOrderService.model.Order;


@RestController
@RequestMapping("/order")
public class OrderController {
	
	@GetMapping("/{id}")
	public Order getOrderById(@PathVariable int id) {
		return new Order(id,"Laptop",2000.0);
	}
	
	

}
