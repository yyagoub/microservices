package com.microservice.items.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ItemsController {

	@GetMapping("/item")
	public String getItem() {
		return "this is items service!";
	}
}
