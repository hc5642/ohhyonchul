package com.ohc.kakaopay.svc.impl;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ohc.kakaopay.dao.WorkNumber4Dao;
import com.ohc.kakaopay.dao.vo.WorkNumber4Vo;
import com.ohc.kakaopay.svc.WorkNumber4Svc;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SuppressWarnings("unchecked")
@Service
public class WorkNumber4SvcImpl implements WorkNumber4Svc{
	
	@Autowired
	private WorkNumber4Dao dao;
	
	/**
	 * Number 4
	 */
	public String doWork(String input) {
		
		JSONObject obj = new JSONObject();
		log.info("input 값 테스트 = [" + input + "]");
		
		try {
			JSONParser parser = new JSONParser();
			JSONObject jo = (JSONObject) parser.parse(input);
			String brName = (String) jo.get("brName");
			WorkNumber4Vo result = dao.doWork(brName);
			
			if ( result == null ) {
				throw new NullPointerException();
			} 
			
			obj.put("brName", result.getBrName());
			obj.put("brCode", result.getBrCode());
			obj.put("sumAmt", result.getSumAmt());
			
		} catch ( NullPointerException ne ) {
			ne.printStackTrace();
			obj.put("code", "404");
			obj.put("메시지", "br code not found error");
		} catch ( Exception e ) {
			e.printStackTrace();
			obj.put("code", "404");
			obj.put("메시지", e.toString());
		}
		return obj.toJSONString();
	}

}
