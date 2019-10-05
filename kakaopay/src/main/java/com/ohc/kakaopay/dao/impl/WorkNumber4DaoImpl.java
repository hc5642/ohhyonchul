package com.ohc.kakaopay.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.ohc.kakaopay.dao.WorkNumber4Dao;
import com.ohc.kakaopay.dao.vo.WorkNumber3Vo;
import com.ohc.kakaopay.dao.vo.WorkNumber4Vo;
import com.ohc.kakaopay.util.DbConnUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class WorkNumber4DaoImpl implements WorkNumber4Dao{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public WorkNumber4Vo doWork(String brName) {
		
		StringBuffer sql = new StringBuffer("/*4. 분당점과 판교점을 통폐합하여 판교점으로 관리점 이관을 하였습니다. 지점명을 입력하면 해당지점의 거래금액 합계를 출력하는 API 개발*/\n");
		sql.append("SELECT 	T_BRCD,\r\n" + 
				"	(SELECT BRNM FROM DATA_BR_INFO Y WHERE Y.BRCD=T_BRCD) AS T_BRNM,\r\n" + 
				"	SUM(D_AMOUNT)\r\n" + 
				"FROM (\r\n" + 
				"	SELECT 	TRXDAT, ACNO, \r\n" + 
				"		(SELECT CASE WHEN BRCD='B' THEN 'A' ELSE BRCD END\r\n" + 
				"		FROM DATA_AC_INFO A WHERE A.ACNO=Z.ACNO) AS T_BRCD,\r\n" + 
				"		CASE WHEN CANCELYN='Y' THEN (AMOUNT*-1) ELSE AMOUNT END AS D_AMOUNT \r\n" + 
				"	FROM DATA_TR_HISTORY Z \r\n" + 
				") B \r\n" + 
				"WHERE T_BRNM=? \r\n" + 
				"GROUP BY T_BRCD\r\n" + 
				"" + 
				"");
		
		log.info(sql.toString());
		
		return jdbcTemplate.queryForObject(sql.toString(), new RowMapper<WorkNumber4Vo>() {
			@Override
			public WorkNumber4Vo mapRow(ResultSet rs, int rowNum) throws SQLException {
				WorkNumber4Vo vo = new WorkNumber4Vo();
				int index=1;
				vo.setBrCode(rs.getString(index++));
				vo.setBrName(rs.getString(index++));
				vo.setSumAmt(rs.getString(index++));
				return vo;
			}
		});
		
	}
	
	public WorkNumber4Vo doWork2(String brName) {
		Connection conn = null; 
		PreparedStatement prep = null; 
		ResultSet rs = null;
		WorkNumber4Vo retValue = null;
		StringBuffer sql = new StringBuffer("/*4. 분당점과 판교점을 통폐합하여 판교점으로 관리점 이관을 하였습니다. 지점명을 입력하면 해당지점의 거래금액 합계를 출력하는 API 개발*/\n");
		sql.append("SELECT 	T_BRCD,\r\n" + 
				"	(SELECT BRNM FROM DATA_BR_INFO Y WHERE Y.BRCD=T_BRCD) AS T_BRNM,\r\n" + 
				"	SUM(D_AMOUNT)\r\n" + 
				"FROM (\r\n" + 
				"	SELECT 	TRXDAT, ACNO, \r\n" + 
				"		(SELECT CASE WHEN BRCD='B' THEN 'A' ELSE BRCD END\r\n" + 
				"		FROM DATA_AC_INFO A WHERE A.ACNO=Z.ACNO) AS T_BRCD,\r\n" + 
				"		CASE WHEN CANCELYN='Y' THEN (AMOUNT*-1) ELSE AMOUNT END AS D_AMOUNT \r\n" + 
				"	FROM DATA_TR_HISTORY Z \r\n" + 
				") B \r\n" + 
				"WHERE T_BRNM=? \r\n" + 
				"GROUP BY T_BRCD\r\n" + 
				"" + 
				"");
		
		log.info(sql.toString());
		
		try {
			
			conn = DbConnUtil.getInstance().getConnection();
			prep =  conn.prepareStatement(sql.toString());
			prep.setString(1, brName);
			rs = prep.executeQuery();
			
			int index=1;
			if ( rs.next() ) {
				retValue = new WorkNumber4Vo();
				retValue.setBrCode(rs.getString(index++));
				retValue.setBrName(rs.getString(index++));
				retValue.setSumAmt(rs.getString(index++));
			}
			
		} catch ( SQLException e ) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if ( rs != null ) rs.close();
				if ( prep != null ) prep.close();
				if ( conn != null ) conn.close();
			} catch ( Exception e ) {}
		}
		
		return retValue;
	}

}
