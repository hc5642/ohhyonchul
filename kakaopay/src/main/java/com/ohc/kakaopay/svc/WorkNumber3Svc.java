package com.ohc.kakaopay.svc;


import java.util.List;

import org.springframework.stereotype.Service;

import com.ohc.kakaopay.dao.vo.WorkNumber3Vo;

@Service
public interface WorkNumber3Svc {
	
	public List<WorkNumber3Vo> doWork();
	
}
