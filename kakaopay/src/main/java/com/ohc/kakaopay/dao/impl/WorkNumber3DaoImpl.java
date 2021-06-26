package com.ohc.kakaopay.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.ohc.kakaopay.dao.WorkNumber3Dao;
import com.ohc.kakaopay.dao.vo.WorkNumber3Vo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class WorkNumber3DaoImpl implements WorkNumber3Dao{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public List<WorkNumber3Vo> doWork() {
		
		StringBuffer sql = new StringBuffer("\n\n/*3. 연도별 관리점별 거래금액 합계를 구하고 합계금액이 큰 순서로 출력하는 API 개발. */\n");
		sql.append("SELECT YEAR, BRCD, ");
		sql.append("		(SELECT BRNM FROM DATA_BR_INFO Z WHERE Z.BRCD=B.BRCD) AS T_BRNM,");
		sql.append("		SUM(SUM_AMOUNT)\n"); 
		sql.append("FROM (\n"); 
		sql.append("	SELECT \n"); 
		sql.append("		SUBSTR(TRXDAT, 1, 4) AS YEAR, \n"); 
		sql.append("		(SELECT BRCD FROM DATA_AC_INFO B WHERE B.ACNO=A.ACNO) AS BRCD, \n"); 
		sql.append("   		SUM(D_AMOUNT) AS SUM_AMOUNT\n"); 
		sql.append("	FROM (\n"); 
		sql.append("		SELECT TRXDAT, ACNO, \n");
		sql.append("			CASE WHEN CANCELYN='Y' THEN (AMOUNT*-1) ELSE AMOUNT END AS D_AMOUNT \n"); 
		sql.append("		FROM DATA_TR_HISTORY \n"); 
		sql.append("	) A GROUP BY SUBSTR(TRXDAT, 1, 4), ACNO\n"); 
		sql.append(") B \n"); 
		sql.append("GROUP BY YEAR, BRCD\n"); 
		sql.append("ORDER BY 1, 4 DESC\n");
		
		log.info(sql.toString());
		
		return jdbcTemplate.query(sql.toString(), new RowMapper<WorkNumber3Vo>() {
			@Override
			public WorkNumber3Vo mapRow(ResultSet rs, int rowNum) throws SQLException {
				WorkNumber3Vo vo = new WorkNumber3Vo();
				int index = 1;
				vo.setYear(rs.getString(index++));
				vo.setBrCode(rs.getString(index++));
				vo.setBrName(rs.getString(index++));
				vo.setSumAmt(rs.getString(index++));
				return vo;
			}
		});
	}
}
	