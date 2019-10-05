package com.ohc.kakaopay.ctl;

import java.io.IOException;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ohc.kakaopay.dao.vo.WorkNumber1Vo;
import com.ohc.kakaopay.svc.WorkNumber1Svc;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SuppressWarnings("unchecked")
@Controller
public class WorkNumber1Ctl {
	
	@Autowired
	private WorkNumber1Svc svc;
	
	@RequestMapping("/work/1")
	@ResponseBody
	public String work1() throws IOException {
		
		JSONObject obj = null;
		JSONArray list = new JSONArray();

		try {
			List<WorkNumber1Vo> result = svc.doWork();
			for ( WorkNumber1Vo vo : result ) {
				obj = new JSONObject();
				obj.put("year", vo.getYear());
				obj.put("name", vo.getName());
				obj.put("acctNo", vo.getAcctNo());
				obj.put("sumAmt", vo.getSumAmt());
				list.add(obj);
			}
			
		} catch ( Exception e ) {
			log.error("###에러발생", e);
			obj = new JSONObject();
			obj.put("code", "404");
			obj.put("메시지", e.toString());
			list.add(obj);
		}
		log.info("###결과" + list.toJSONString());
		
		return list.toJSONString();
	}
	
}
