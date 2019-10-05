package com.ohc.kakaopay.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.ohc.kakaopay.dao.WorkNumber4Dao;
import com.ohc.kakaopay.dao.vo.WorkNumber4Vo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class WorkNumber4DaoImpl implements WorkNumber4Dao{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public WorkNumber4Vo doWork(String brName) {
		
		StringBuffer sql = new StringBuffer("\n\n/*4. 분당점과 판교점을 통폐합하여 판교점으로 관리점 이관을 하였습니다. 지점명을 입력하면 해당지점의 거래금액 합계를 출력하는 API 개발*/\n");
		sql.append("SELECT 	T_BRCD,\n");
		sql.append("	(SELECT BRNM FROM DATA_BR_INFO Y WHERE Y.BRCD=T_BRCD) AS T_BRNM,\n"); 
		sql.append("	SUM(D_AMOUNT)\n"); 
		sql.append("FROM (\n");
		sql.append("	SELECT 	TRXDAT, ACNO, \n"); 
		sql.append("		(SELECT CASE WHEN BRCD='B' THEN 'A' ELSE BRCD END\n"); 
		sql.append("		FROM DATA_AC_INFO A WHERE A.ACNO=Z.ACNO) AS T_BRCD,\n"); 
		sql.append("		CASE WHEN CANCELYN='Y' THEN (AMOUNT*-1) ELSE AMOUNT END AS D_AMOUNT \n"); 
		sql.append("	FROM DATA_TR_HISTORY Z \n"); 
		sql.append(") B \n"); 
		sql.append("WHERE T_BRNM='"+brName+"' \n"); 
		sql.append("GROUP BY T_BRCD\n"); 
		
		log.info(sql.toString());
		log.info("Input String [brName]=["+brName+"]");
		
		return jdbcTemplate.query(sql.toString(), new ResultSetExtractor<WorkNumber4Vo>() {
			@Override
			public WorkNumber4Vo extractData(ResultSet rs) throws SQLException, DataAccessException {
				if ( rs.next() ) {
					WorkNumber4Vo vo = new WorkNumber4Vo();
					int index=1;
					vo.setBrCode(rs.getString(index++));
					vo.setBrName(rs.getString(index++));
					vo.setSumAmt(rs.getString(index++));
					return vo;
				} else {
					return null;
				}
			}
		});
		
	}
}