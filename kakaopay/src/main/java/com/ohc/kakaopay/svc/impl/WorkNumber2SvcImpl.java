package com.ohc.kakaopay.svc.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ohc.kakaopay.dao.WorkNumber2Dao;
import com.ohc.kakaopay.dao.vo.WorkNumber2Vo;
import com.ohc.kakaopay.svc.WorkNumber2Svc;

@Service
public class WorkNumber2SvcImpl implements WorkNumber2Svc{
	
	@Autowired
	private WorkNumber2Dao dao;
	
	/**
	 * Number 2
	 */
	public List<WorkNumber2Vo> doWork() {
		return dao.doWork() ;
	}
	
}
