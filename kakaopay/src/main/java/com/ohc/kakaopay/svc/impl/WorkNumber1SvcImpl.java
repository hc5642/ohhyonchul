package com.ohc.kakaopay.svc.impl;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ohc.kakaopay.dao.WorkNumber1Dao;
import com.ohc.kakaopay.dao.vo.WorkNumber1Vo;
import com.ohc.kakaopay.svc.WorkNumber1Svc;

@SuppressWarnings("unchecked")
@Service
public class WorkNumber1SvcImpl implements WorkNumber1Svc{
	
	@Autowired
	private WorkNumber1Dao dao;
	
	/**
	 * Number 1 
	 */
	public String doWork() {
		
		JSONObject obj = null;
		JSONArray list = new JSONArray();

		try {
			List<WorkNumber1Vo> result = this.dao.doWork();
			for ( WorkNumber1Vo vo : result ) {
				obj = new JSONObject();
				obj.put("year", vo.getYear());
				obj.put("name", vo.getName());
				obj.put("acctNo", vo.getAcctNo());
				obj.put("sumAmt", vo.getSumAmt());
				list.add(obj);
			}
			
		} catch ( Exception e ) {
			e.printStackTrace();
			obj = new JSONObject();
			obj.put("code", "404");
			obj.put("메시지", e.toString());
			list.add(obj);
		}
		return list.toJSONString() ;
	}

}
