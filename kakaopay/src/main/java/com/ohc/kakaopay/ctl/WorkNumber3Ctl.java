package com.ohc.kakaopay.ctl;

import java.io.IOException;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ohc.kakaopay.dao.vo.WorkNumber3Vo;
import com.ohc.kakaopay.svc.WorkNumber3Svc;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SuppressWarnings("unchecked")
@Controller
public class WorkNumber3Ctl {
	
	@Autowired
	private WorkNumber3Svc svc;
	
	@RequestMapping("/work/3")
	@ResponseBody
	public String work3() throws IOException {
		JSONObject obj = null;
		JSONObject subObj = null;
		JSONArray result = new JSONArray();
		JSONArray subList = new JSONArray();
		
		try {
			List<WorkNumber3Vo> list = svc.doWork();
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
			log.error("###에러발생", e);
			obj = new JSONObject();
			obj.put("code", "404");
			obj.put("메시지", e.toString());
			result.add(obj);
		}
		log.info("###결과" + result.toJSONString());
		return result.toJSONString();
	}
	
}
