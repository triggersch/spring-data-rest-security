package com.apress.hellorest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class HelloRestApplication {

	public static void main(String[] args) {
		SpringApplication.run(HelloRestApplication.class, args);
	}
	
	@RequestMapping("/greet")
	public String helloGreeting(){
		return "HELLO REST";
	}
}
