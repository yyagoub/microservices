package com.service.oauth2.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

	/*
	 * to test user login use:
	 * curl first-client:noonewilleverguess@localhost:8080/oauth/token -dgrant_type=password -dscope=any -dusername='user' -dpassword='user'
	 * or
	 * curl first-client:noonewilleverguess@localhost:8080/oauth/token -dgrant_type=password -dscope=any -dusername='admin' -dpassword='admin'
	 * 
	 * then use the given token to test the authorities:
	 * replace your token in the XXX-XXX-XXX field
	 * curl first-client:noonewilleverguess@localhost:8080/user -H 'authorization: bearer XXX-XXX-XXX'
	 * or
	 * curl first-client:noonewilleverguess@localhost:8080/admin -H 'authorization: bearer XXX-XXX-XXX'
	 */

	@GetMapping("/user")
	@PreAuthorize("hasAuthority('USER')")
	public String getUser() {
		return "this is user controller";
	}
	
	@GetMapping("/admin")
	@PreAuthorize("hasAuthority('ADMIN')")
	public String getAdmin() {
		return "this is admin controller";
	}
}
