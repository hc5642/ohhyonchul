package com.ohc.kakaopay.svc.impl;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ohc.kakaopay.dao.WorkNumber2Dao;
import com.ohc.kakaopay.dao.vo.WorkNumber2Vo;
import com.ohc.kakaopay.svc.WorkNumber2Svc;

@SuppressWarnings("unchecked")
@Service
public class WorkNumber2SvcImpl implements WorkNumber2Svc{
	
	@Autowired
	private WorkNumber2Dao dao;
	
	/**
	 * Number 2
	 */
	public String doWork() {
		JSONObject obj = null;
		JSONArray list = new JSONArray();
		
		try {
			List<WorkNumber2Vo> result = this.dao.doWork();
			for ( WorkNumber2Vo vo : result ) {
				obj = new JSONObject();
				obj.put("year", vo.getYear());
				obj.put("name", vo.getName());
				obj.put("acctNo", vo.getAcctNo());
				list.add(obj);
			}
			
		} catch ( Exception e ) {
			e.printStackTrace();
			obj.put("code", "404");
			obj.put("메시지", e.toString());
		}
		return list.toJSONString() ;
	}
	
}
