package com.ohhyonchul.apitest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ohhyonchul.apitest.svc.ApiTestPoiSvc;

@Controller
public class ApiTestController {
	
	@Autowired 
	private ApiTestPoiSvc svc;
	
	@RequestMapping("/hello")
	public String hello() {
		svc.doService();
		return "hello!!";
	}
}
