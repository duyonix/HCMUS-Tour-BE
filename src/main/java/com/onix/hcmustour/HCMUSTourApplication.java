package com.onix.hcmustour;

import com.onix.hcmustour.dto.response.Response;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
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
	public Response<Object> greeting() {
		return Response.ok().setPayload("Welcome to HCMUS Tour!");
	}
}
