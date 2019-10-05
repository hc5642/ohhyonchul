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
		
		StringBuffer sql = new StringBuffer("/*1. 2018년, 2019년 각 연도별 합계 금액이 가장 많은 고객을 추출하는 API 개발 */\n");
		sql.append("SELECT YEAR, ACNM, ACNO, MAX(SUM_AMOUNT) AS MAX_AMOUNT \r\n" +  
				 	"FROM (\r\n" +  
				 	"	SELECT 	SUBSTR(TRXDAT, 1, 4) AS YEAR, ACNO, 	\r\n" +  
				    "	(SELECT 	ACNM FROM DATA_AC_INFO SUB_T WHERE T1.ACNO=SUB_T.ACNO) AS ACNM, 	\r\n" +  
				 	"		SUM(D_AMOUNT-FEE) AS SUM_AMOUNT \r\n" +  
				 	"	FROM 	(SELECT 	TRXDAT, ACNO, \r\n" +  
				 	"			CASE WHEN CANCELYN='Y' THEN (AMOUNT*-1) \r\n" +  
				 	"			ELSE AMOUNT END AS D_AMOUNT, FEE 	\r\n" +  
					"		FROM DATA_TR_HISTORY ) T1 \r\n" +  
					"	GROUP BY SUBSTR(TRXDAT, 1, 4), ACNO \r\n" +  
					") GROUP BY YEAR ORDER BY 1" 
				+ ""); 

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
