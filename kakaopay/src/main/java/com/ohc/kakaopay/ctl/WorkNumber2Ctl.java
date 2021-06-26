package com.ohc.kakaopay.ctl;

import java.io.IOException;
import java.util.List;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ohc.kakaopay.dao.vo.WorkNumber2Vo;
import com.ohc.kakaopay.svc.WorkNumber2Svc;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SuppressWarnings("unchecked")
@Controller
public class WorkNumber2Ctl {
	
	@Autowired
	private WorkNumber2Svc svc;
	
	@RequestMapping("/work/2")
	@ResponseBody
	public String work2() throws IOException {
		JSONObject obj = null;
		JSONArray list = new JSONArray();
		
		try {
			List<WorkNumber2Vo> result = svc.doWork();
			for ( WorkNumber2Vo vo : result ) {
				obj = new JSONObject();
				obj.put("year", vo.getYear());
				obj.put("name", vo.getName());
				obj.put("acctNo", vo.getAcctNo());
				list.add(obj);
			}
			
		} catch ( Exception e ) {
			log.error("###에러발생", e);
			obj.put("code", "404");
			obj.put("메시지", e.toString());
		}
		log.info("###결과" + list.toJSONString());
		return list.toJSONString();
	}
	
}
