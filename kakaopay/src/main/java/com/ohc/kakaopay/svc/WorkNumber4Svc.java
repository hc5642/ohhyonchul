package com.ohc.kakaopay.svc;


import org.springframework.stereotype.Service;

import com.ohc.kakaopay.dao.vo.WorkNumber4Vo;

@Service
public interface WorkNumber4Svc {
	
	public WorkNumber4Vo doWork(String brName);
	
}
