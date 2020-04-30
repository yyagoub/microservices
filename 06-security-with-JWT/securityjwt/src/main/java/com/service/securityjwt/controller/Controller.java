package com.service.securityjwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.service.securityjwt.model.AuthRequest;
import com.service.securityjwt.model.AuthResponse;
import com.service.securityjwt.util.JwtUtil;

@RestController
public class Controller {

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private JwtUtil jwtUtil;
	
	@PostMapping("/authenticate")
	public ResponseEntity<?> postAuthenticate(@RequestBody AuthRequest authRequest){
		
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
		
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
		
		final String jwt = jwtUtil.generateToken(userDetails);
		
		return ResponseEntity.ok(new AuthResponse(jwt));
	}
	
	@GetMapping("/hello")
	public String getHello() {
		return "this is hello method";
	}
}
