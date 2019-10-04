package com.ohc.kakaopay.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.ohc.kakaopay.dao.WorkNumber3Dao;
import com.ohc.kakaopay.dao.vo.WorkNumber3Vo;
import com.ohc.kakaopay.util.DbConnUtil;

@Repository
public class WorkNumber3DaoImpl implements WorkNumber3Dao{
	
	@Override
	public List<WorkNumber3Vo> doWork() {
		Connection conn = null; 
		Statement stat = null; 
		ResultSet rs = null;
		WorkNumber3Vo vo = null;
		
		List<WorkNumber3Vo> retValue = new LinkedList<WorkNumber3Vo>();
		StringBuffer sql = new StringBuffer("/*3. 연도별 관리점별 거래금액 합계를 구하고 합계금액이 큰 순서로 출력하는 API 개발. */\n");
		sql.append("SELECT YEAR, BRCD, "
				+ "		(SELECT BRNM FROM DATA_BR_INFO Z WHERE Z.BRCD=B.BRCD) AS T_BRNM,"
				+ "		SUM(SUM_AMOUNT)\r\n" + 
				"FROM (\r\n" + 
				"	SELECT \r\n" + 
				"		SUBSTR(TRXDAT, 1, 4) AS YEAR, \r\n" + 
				"		(SELECT BRCD FROM DATA_AC_INFO B WHERE B.ACNO=A.ACNO) AS BRCD, \r\n" + 
				"   		SUM(D_AMOUNT) AS SUM_AMOUNT\r\n" + 
				"	FROM (\r\n" + 
				"		SELECT TRXDAT, ACNO, \r\n" + 
				"			CASE WHEN CANCELYN='Y' THEN (AMOUNT*-1) ELSE AMOUNT END AS D_AMOUNT \r\n" + 
				"		FROM DATA_TR_HISTORY \r\n" + 
				"	) A GROUP BY SUBSTR(TRXDAT, 1, 4), ACNO\r\n" + 
				") B \r\n" + 
				"GROUP BY YEAR, BRCD\r\n" + 
				"ORDER BY 1, 4 DESC"
				+ "");
		
		System.out.println(sql.toString());
		
		try {
			
			conn = DbConnUtil.getInstance().getConnection();
			stat =  conn.createStatement();
			rs = stat.executeQuery(sql.toString());
			
			while ( rs.next() ) {
				vo = new WorkNumber3Vo();
				int index = 1;
				vo.setYear(rs.getString(index++));
				vo.setBrCode(rs.getString(index++));
				vo.setBrName(rs.getString(index++));
				vo.setSumAmt(rs.getString(index++));
				retValue.add(vo);
			}
			
		} catch ( SQLException e ) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if ( rs != null ) rs.close();
				if ( stat != null ) stat.close();
				if ( conn != null ) conn.close();
			} catch ( Exception e ) {}
		}
		
		return retValue;
	}

}
