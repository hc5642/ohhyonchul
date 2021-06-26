package com.ohc.kakaopay.svc;


import java.util.List;

import org.springframework.stereotype.Service;

import com.ohc.kakaopay.dao.vo.WorkNumber1Vo;

@Service
public interface WorkNumber1Svc {
	
	public List<WorkNumber1Vo> doWork();
	
}
