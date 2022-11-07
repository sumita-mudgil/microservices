package com.intuit.snip;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
//@EnableDiscoveryClient
@EnableCaching
public class SnipServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SnipServiceApplication.class, args);
	}
	
}
