package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@GetMapping("/public")
    public String publicEndpoint() {
        return "Public content!";
    }

	@GetMapping("/user")
    public String userEndpoint() {
        return "User content!";
    }

    @GetMapping("/admin")
    public String adminEndpoint() {
        return "Admin content!";
    }
    
    @GetMapping("/developer")
    public String developerEndpoint() {
        return "Developer content!";
    }
	

}
