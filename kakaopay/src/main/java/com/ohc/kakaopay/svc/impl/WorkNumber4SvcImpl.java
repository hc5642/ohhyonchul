package com.ohc.kakaopay.svc.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ohc.kakaopay.dao.WorkNumber4Dao;
import com.ohc.kakaopay.dao.vo.WorkNumber4Vo;
import com.ohc.kakaopay.svc.WorkNumber4Svc;

@Service
public class WorkNumber4SvcImpl implements WorkNumber4Svc{
	
	@Autowired
	private WorkNumber4Dao dao;
	
	/**
	 * Number 4
	 */
	public WorkNumber4Vo doWork(String input) {
		return dao.doWork(input);
	}

}
