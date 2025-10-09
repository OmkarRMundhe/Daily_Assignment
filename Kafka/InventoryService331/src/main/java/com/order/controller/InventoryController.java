package com.order.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class InventoryController {
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	
	@Value("${topic.Inventory}")
	private String InventoryTopic;
	
	@PostMapping("/create")
	public String createOrder(@RequestBody Map<String, String> orderDetails) {
	    String message = orderDetails.get("text");
	    kafkaTemplate.send(InventoryTopic, message);
	    return "Order placed Successfully";
	}
}
