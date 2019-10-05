package com.ohc.kakaopay.svc.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ohc.kakaopay.dao.WorkNumber1Dao;
import com.ohc.kakaopay.dao.vo.WorkNumber1Vo;
import com.ohc.kakaopay.svc.WorkNumber1Svc;

@Service
public class WorkNumber1SvcImpl implements WorkNumber1Svc{
	
	@Autowired
	private WorkNumber1Dao dao;
	
	/**
	 * Number 1 
	 */
	public List<WorkNumber1Vo> doWork() {
		return dao.doWork();
	}

}
