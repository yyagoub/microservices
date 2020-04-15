package com.microservice.cart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CartController {

	@Autowired
	private RestTemplate restTemplate;
	
	private final String ItemService = "http://localhost:8081/item";
	
	@GetMapping("/cart")
	public String getCartItems() {
		String itemServiceResponse = restTemplate.getForObject(ItemService, String.class);
		return "this is your cart items: "+itemServiceResponse;
	}
}
