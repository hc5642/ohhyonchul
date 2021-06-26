package com.ohc.kakaopay.svc.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ohc.kakaopay.dao.WorkNumber3Dao;
import com.ohc.kakaopay.dao.vo.WorkNumber3Vo;
import com.ohc.kakaopay.svc.WorkNumber3Svc;

@Service
public class WorkNumber3SvcImpl implements WorkNumber3Svc{
	
	@Autowired
	private WorkNumber3Dao dao;
	
	/**
	 * NUmber 3
	 */
	public List<WorkNumber3Vo> doWork() {
		return dao.doWork() ;
	}

}
