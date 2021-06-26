package com.ohc.kakaopay.svc;


import java.util.List;

import org.springframework.stereotype.Service;

import com.ohc.kakaopay.dao.vo.WorkNumber2Vo;

@Service
public interface WorkNumber2Svc {
	
	public  List<WorkNumber2Vo> doWork();
	
	
}
