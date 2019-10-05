package com.ohc.kakaopay.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.ohc.kakaopay.dao.WorkNumber1Dao;
import com.ohc.kakaopay.dao.vo.WorkNumber1Vo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class WorkNumber1DaoImpl implements WorkNumber1Dao{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public List<WorkNumber1Vo> doWork() {
		
		StringBuffer sql = new StringBuffer("\n/*1. 2018년, 2019년 각 연도별 합계 금액이 가장 많은 고객을 추출하는 API 개발 */\n");
		sql.append("SELECT YEAR, ACNM, ACNO, MAX(SUM_AMOUNT) AS MAX_AMOUNT \n");  
		sql.append("FROM (\n");  
		sql.append("	SELECT 	SUBSTR(TRXDAT, 1, 4) AS YEAR, ACNO, 	\n");  
		sql.append("	(SELECT 	ACNM FROM DATA_AC_INFO SUB_T WHERE T1.ACNO=SUB_T.ACNO) AS ACNM, 	\n");  
		sql.append("		SUM(D_AMOUNT-FEE) AS SUM_AMOUNT \n");  
		sql.append("	FROM 	(SELECT 	TRXDAT, ACNO, \n");  
		sql.append("			CASE WHEN CANCELYN='Y' THEN (AMOUNT*-1) \n");  
		sql.append("			ELSE AMOUNT END AS D_AMOUNT, FEE 	\n");  
		sql.append("		FROM DATA_TR_HISTORY ) T1 \n");  
		sql.append("	GROUP BY SUBSTR(TRXDAT, 1, 4), ACNO \n");  
		sql.append(") GROUP BY YEAR ORDER BY 1"); 

		log.info(sql.toString());
		
		return jdbcTemplate.query(sql.toString(), new RowMapper<WorkNumber1Vo>() {
			@Override
			public WorkNumber1Vo mapRow(ResultSet rs, int rowNum) throws SQLException {
				WorkNumber1Vo vo = new WorkNumber1Vo();
				vo.setYear(rs.getString("YEAR"));
				vo.setName(rs.getString("ACNM"));
				vo.setAcctNo(rs.getString("ACNO"));
				vo.setSumAmt(rs.getString("MAX_AMOUNT"));
				return vo;
			}
		});
		
	}
	
}
