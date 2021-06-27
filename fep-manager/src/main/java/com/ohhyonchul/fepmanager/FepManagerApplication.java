package com.ohhyonchul.fepmanager;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FepManagerApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(FepManagerApplication.class, args);
	}
	
	@PostConstruct
	public void init() {
		System.out.println("--- POST CONSTRUCT ---");
	}
	
	@PreDestroy
	public void destroy() {
		System.out.println("--- PRE DESTROY ---");
	}
	
	
}
