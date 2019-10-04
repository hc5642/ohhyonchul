package com.ohc.kakaopay.ctl;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ohc.kakaopay.svc.WorkNumber1Svc;
import com.ohc.kakaopay.svc.WorkNumber2Svc;
import com.ohc.kakaopay.svc.WorkNumber3Svc;
import com.ohc.kakaopay.svc.WorkNumber4Svc;

@Controller
public class WorkController {
	
	@Autowired
	private WorkNumber1Svc svc1;
	@Autowired
	private WorkNumber2Svc svc2;
	@Autowired
	private WorkNumber3Svc svc3;
	@Autowired
	private WorkNumber4Svc svc4;
	
	
	@RequestMapping("/work/1")
	@ResponseBody
	public String work1() throws IOException {
		return svc1.doWork();
	}
	
	@RequestMapping("/work/2")
	@ResponseBody
	public String work2() throws IOException {
		return svc2.doWork();
	}
	
	@RequestMapping("/work/3")
	@ResponseBody
	public String work3() throws IOException {
		return svc3.doWork();
	}
	
	@RequestMapping("/work/4")
	@ResponseBody
	public String work4(@RequestParam("input") String input) throws IOException {
		return svc4.doWork(input);
	}

}
