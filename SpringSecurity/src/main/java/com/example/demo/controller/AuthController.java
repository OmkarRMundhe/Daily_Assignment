package com.example.demo.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.utils.JwtService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtService jwtService;
	@Autowired
	private UserRepository userRepository;
	
	
	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@RequestBody User user){
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.save(user);
		 return ResponseEntity.ok(user);
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> loginUser(@RequestBody User user){
		authenticationManager.authenticate(
				 new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
				);
		
		UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(userRepository.findByUsername(user.getUsername()).get().getRole().name())
                .build();
		
		String tokenString=jwtService.generateToken(userDetails);
		
		return ResponseEntity.ok(Map.of(
				"Token", tokenString
				));
	}
}
