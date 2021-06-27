package com.ohhyonchul.fepmanager;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ohhyonchul.fepmanager.thread.FepSimulatorThread;

@Controller
@RequestMapping("/sim")
public class FepSimulatorController {
	
	@Autowired 
	private FepSimulatorService simSvc;
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	@RequestMapping("/test/{app}/{msg}")
	@ResponseBody
	public byte [] test(
			@PathVariable String app,
			@PathVariable String msg,
			@RequestBody byte[] input) {
		logger.info("--- RECV MSG : " + new String(input));
		String rule = simSvc.getRule(app, msg);
		logger.info("--- RULE NAME : " + rule);
		return simSvc.agenyResponse(input, rule);
	}
	
	@GetMapping("/list")
	public String list(Model model) {
		model.addAttribute("applist", simSvc.getAppList());
		model.addAttribute("threadlist", simSvc.getThreadList());
		return "sim";
	}
	
	@GetMapping("/list/{app}")
	public String list(
			@PathVariable("app") String app,
			Model model) {
		model.addAttribute("applist", simSvc.getAppList());
		model.addAttribute("msglist", simSvc.getMsgList(app));
		model.addAttribute("threadlist", simSvc.getThreadList());
		return "sim";
	}
	
	@GetMapping("/list/{app}/{msg}")
	public String listAppMsg(
			@PathVariable("app") String app,
			@PathVariable("msg") String msg,
			Model model) {
		model.addAttribute("app", app);
		model.addAttribute("msg", msg);
		model.addAttribute("loadRule", simSvc.getRule(app, msg));
		model.addAttribute("applist", simSvc.getAppList());
		model.addAttribute("msglist", simSvc.getMsgList(app));
		model.addAttribute("msgname", simSvc.getMsgName(app, msg));
		model.addAttribute("reqdata", simSvc.getReqColumn(app, msg));
		model.addAttribute("resdata", simSvc.getResColumn(app, msg));
		model.addAttribute("threadlist", simSvc.getThreadList());
		return "sim";
	}
	
	@PostMapping("/save/{app}/{msg}")
	public String save(
			@PathVariable("app") String app,
			@PathVariable("msg") String msg,
			@RequestParam(value="iswork", required=false) String iswork,
			@RequestParam(value="isdown", required=false) String isdown,
			Model model) {
		if ( "on".equals(iswork) ) {
			logger.info("--- 시작을 시도합니다.");
			simSvc.startThread(app, msg);
		}
		if ( "on".equals(isdown)) {
			logger.info("--- 중지를 시도합니다.");
			simSvc.stopThread(app, msg);
		}
		return "redirect:/sim/list/" + app + "/" + msg;
	}

}
