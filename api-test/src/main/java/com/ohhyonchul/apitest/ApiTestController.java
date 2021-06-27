package com.ohhyonchul.apitest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ApiTestController {
	
	@RequestMapping("/hello")
	public String hello() {
		return "hello!!";
	}
}
