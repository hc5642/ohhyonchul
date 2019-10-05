package com.ohc.kakaopay.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.ohc.kakaopay.dao.WorkNumber2Dao;
import com.ohc.kakaopay.dao.vo.WorkNumber2Vo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class WorkNumber2DaoImpl implements WorkNumber2Dao{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public List<WorkNumber2Vo> doWork() {
		
		StringBuffer sql = new StringBuffer("\n/*2. 2018년 또는 2019년에 거래가 없는 고객을 추출하는 API 개발. */\n");
		sql.append("SELECT SUBSTR(Z.MOM_DATA,1,4), SUBSTR(Z.MOM_DATA,6),\n"); 
		sql.append("	(SELECT ACNM FROM DATA_AC_INFO T WHERE T.ACNO=SUBSTR(Z.MOM_DATA,6))\n"); 
		sql.append("FROM (\n"); 
		sql.append("	SELECT DISTINCT SUBSTR(A.TRXDAT, 1, 4) || '_' || B.ACNO AS MOM_DATA\n"); 
		sql.append("	FROM DATA_TR_HISTORY A, DATA_AC_INFO B\n"); 
		sql.append(") Z LEFT OUTER JOIN (\n"); 
		sql.append("	SELECT	SUBSTR(TRXDAT, 1, 4) || '_' || ACNO AS MOM_DATA\n"); 
		sql.append("	FROM 	DATA_TR_HISTORY\n"); 
		sql.append("	WHERE 	CANCELYN='N'\n"); 
		sql.append(") Y\n"); 
		sql.append("ON Z.MOM_DATA=Y.MOM_DATA\n"); 
		sql.append("WHERE Y.MOM_DATA IS NULL");
		
		log.info(sql.toString());
		
		return jdbcTemplate.query(sql.toString(), new RowMapper<WorkNumber2Vo>() {
			@Override
			public WorkNumber2Vo mapRow(ResultSet rs, int rowNum) throws SQLException {
				WorkNumber2Vo vo = new WorkNumber2Vo();
				vo.setYear(rs.getString(1));
				vo.setAcctNo(rs.getString(2));
				vo.setName(rs.getString(3));
				return vo;
			}
		});
		
	}
}