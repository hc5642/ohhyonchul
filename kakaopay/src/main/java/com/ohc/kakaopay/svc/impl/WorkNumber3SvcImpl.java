package com.ohc.kakaopay.svc.impl;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ohc.kakaopay.dao.WorkNumber3Dao;
import com.ohc.kakaopay.dao.vo.WorkNumber3Vo;
import com.ohc.kakaopay.svc.WorkNumber3Svc;

@SuppressWarnings("unchecked")
@Service
public class WorkNumber3SvcImpl implements WorkNumber3Svc{
	
	@Autowired
	private WorkNumber3Dao dao;
	
	
	/**
	 * NUmber 3
	 */
	public String doWork() {
		
		JSONObject obj = null;
		JSONObject subObj = null;
		JSONArray result = new JSONArray();
		JSONArray subList = new JSONArray();
		
		try {
			List<WorkNumber3Vo> list = this.dao.doWork();
			String tempYear = "";
			for ( WorkNumber3Vo vo : list ) {
				subObj = new JSONObject();
				subObj.put("brName", vo.getBrName());
				subObj.put("brCode", vo.getBrCode());
				subObj.put("sumAmt", vo.getSumAmt());
				
				if ( "".equals(tempYear) ) {
					tempYear = vo.getYear();
					subList.add(subObj);
				} else if ( !tempYear.equals(vo.getYear()) ) {
					obj = new JSONObject();
					obj.put("year", tempYear);
					obj.put("dataList", subList);
					result.add(obj);
					tempYear = vo.getYear();
					subList = new JSONArray();
					subList.add(subObj);
				} else {
					subList.add(subObj);
				}
 			}
			obj = new JSONObject();
			obj.put("year", tempYear);
			obj.put("dataList", subList);
			result.add(obj);
			
		} catch ( Exception e ) {
			e.printStackTrace();
			obj = new JSONObject();
			obj.put("code", "404");
			obj.put("메시지", e.toString());
			result.add(obj);
		}
		return result.toJSONString() ;
	}

}
