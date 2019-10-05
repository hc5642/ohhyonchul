package com.ohc.kakaopay.ctl;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ohc.kakaopay.dao.vo.WorkNumber4Vo;
import com.ohc.kakaopay.svc.WorkNumber4Svc;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SuppressWarnings("unchecked")
@Controller
public class WorkNumber4Ctl {
	
	@Autowired
	private WorkNumber4Svc svc;
	
	@RequestMapping("/work/4")
	@ResponseBody
	public String work4(@RequestParam("input") String input, HttpServletResponse response) throws IOException, ParseException {
		
		JSONObject retValue = new JSONObject();
		log.info("input 값 테스트 = [" + input + "]");
		
		JSONParser parser = new JSONParser();
		JSONObject jo = (JSONObject) parser.parse(input);
		String brName = (String) jo.get("brName");
		WorkNumber4Vo result = svc.doWork(brName);
		if ( result == null ) {
			log.error("### 결과 없음");
			retValue.put("code", "404");
			retValue.put("msg", "br code not found error");
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return retValue.toJSONString();
		}
		retValue.put("brName", result.getBrName());
		retValue.put("brCode", result.getBrCode());
		retValue.put("sumAmt", result.getSumAmt());
		log.info("###결과" + retValue.toJSONString());
		return retValue.toJSONString();
	}
	
	
}
