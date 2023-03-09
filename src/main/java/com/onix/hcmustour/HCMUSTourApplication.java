package com.onix.hcmustour;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableCaching
@RestController
@RequestMapping("/")
public class HCMUSTourApplication {

	public static void main(String[] args) {
		SpringApplication.run(HCMUSTourApplication.class, args);
	}

	@GetMapping
	public ResponseEntity<String> greeting() {
		return ResponseEntity.ok("Welcome to HCMUS Tour");
	}
}
