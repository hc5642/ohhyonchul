package com.ohc.kakaopay.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.ohc.kakaopay.dao.WorkNumber2Dao;
import com.ohc.kakaopay.dao.vo.WorkNumber2Vo;
import com.ohc.kakaopay.util.DbConnUtil;

@Repository
public class WorkNumber2DaoImpl implements WorkNumber2Dao{
	
	@Override
	public List<WorkNumber2Vo> doWork() {
		
		Connection conn = null; 
		Statement stat = null; 
		ResultSet rs = null;
		WorkNumber2Vo vo = null;
		
		List<WorkNumber2Vo> retValue = new ArrayList<WorkNumber2Vo>();
		StringBuffer sql = new StringBuffer("/*2. 2018년 또는 2019년에 거래가 없는 고객을 추출하는 API 개발. */\n");
		sql.append("SELECT SUBSTR(Z.MOM_DATA,1,4), SUBSTR(Z.MOM_DATA,6),\r\n" + 
				"	(SELECT ACNM FROM DATA_AC_INFO T WHERE T.ACNO=SUBSTR(Z.MOM_DATA,6))\r\n" + 
				"FROM (\r\n" + 
				"	SELECT DISTINCT SUBSTR(A.TRXDAT, 1, 4) || '_' || B.ACNO AS MOM_DATA\r\n" + 
				"	FROM DATA_TR_HISTORY A, DATA_AC_INFO B\r\n" + 
				") Z LEFT OUTER JOIN (\r\n" + 
				"	SELECT	SUBSTR(TRXDAT, 1, 4) || '_' || ACNO AS MOM_DATA\r\n" + 
				"	FROM 	DATA_TR_HISTORY\r\n" + 
				"	WHERE 	CANCELYN='N'\r\n" + 
				") Y\r\n" + 
				"ON Z.MOM_DATA=Y.MOM_DATA\r\n" + 
				"WHERE Y.MOM_DATA IS NULL");
		
		System.out.println(sql.toString());
		
		try {
			
			conn = DbConnUtil.getInstance().getConnection();
			stat =  conn.createStatement();
			rs = stat.executeQuery(sql.toString());
			
			while ( rs.next() ) {
				vo = new WorkNumber2Vo();
				vo.setYear(rs.getString(1));
				vo.setAcctNo(rs.getString(2));
				vo.setName(rs.getString(3));
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
