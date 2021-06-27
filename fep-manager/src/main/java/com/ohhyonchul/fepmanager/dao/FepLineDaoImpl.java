package com.ohhyonchul.fepmanager.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.ohhyonchul.fepmanager.dto.FepLineDto;
@Repository
public class FepLineDaoImpl implements FepLineDao {
	
	@Override
	public List<FepLineDto> selectList(String searchKey) {
		List<FepLineDto> retValue = new ArrayList<FepLineDto>();
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement prep = null;
		Statement stat = null;
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM LINE ");
		if ( searchKey != null && searchKey.trim().length() > 0 ) {
			sql.append("WHERE ");
			sql.append("    INST_NAME LIKE '%"+searchKey+"%' ");
			sql.append("    AND APPL_NAME LIKE '%"+searchKey+"%' ");
			sql.append("    AND TYPE_CLCD LIKE '%"+searchKey+"%' ");
		}
		sql.append("ORDER BY INST_CODE, APPL_CODE ");
		
		try {
			conn = getConnection();
			stat = conn.createStatement();
            stat.executeUpdate("CREATE TABLE IF NOT EXISTS LINE "
            		+ "(SEQ_NUM INTEGER PRIMARY KEY AUTOINCREMENT, INST_CODE, INST_NAME, APPL_CODE, APPL_NAME, TYPE_CLCD);");
            
            prep = conn.prepareStatement(sql.toString());
            rs = prep.executeQuery();
            
            FepLineDto dto = null;
            while ( rs.next() ) {
            	dto = new FepLineDto();
            	dto.setSeqNum(rs.getInt("SEQ_NUM"));
            	dto.setInstCode(rs.getString("INST_CODE"));
            	dto.setInstName(rs.getString("INST_NAME"));
            	dto.setApplCode(rs.getString("APPL_CODE"));
            	dto.setApplName(rs.getString("APPL_NAME"));
            	dto.setTypeClcd(rs.getString("TYPE_CLCD"));
            	retValue.add(dto);
            }
			
		} catch ( Exception e ) {
			e.printStackTrace();
		} finally {
			try {
				if ( stat !=null ) stat.close();
				if ( prep != null ) prep.close();
				if ( rs != null ) rs.close();
				if ( conn != null ) conn.close();
			} catch ( Exception ie ) {
				ie.printStackTrace();
			}
		}
		return retValue;
	}

	@Override
	public int deleteItem(int seqNum) {
		int retValue = 0;
		Connection conn = null;
		PreparedStatement prep = null;
		try {
			conn = getConnection();
            prep = conn.prepareStatement("DELETE FROM LINE WHERE SEQ_NUM=? ");
            prep.setInt(1, seqNum);
            retValue = prep.executeUpdate();
            
		} catch ( Exception e ) {
			e.printStackTrace();
		} finally {
			try {
				if ( prep != null ) prep.close();
				if ( conn != null ) conn.close();
			} catch ( Exception ie ) {
				ie.printStackTrace();
			}
		}
		return retValue;
	}
	
	@Override
	public int insertItem(FepLineDto dto) {
		int retValue = 0;
		Connection conn = null;
		PreparedStatement prep = null;
		try {
			conn = getConnection();
            prep = conn.prepareStatement("INSERT INTO LINE (INST_CODE, INST_NAME, APPL_CODE, APPL_NAME, TYPE_CLCD) "
            		+ "VALUES (?,?,?,?,?)");
            prep.setString(1, dto.getInstCode());
            prep.setString(2, dto.getInstName());
            prep.setString(3, dto.getApplCode());
            prep.setString(4, dto.getApplName());
            prep.setString(5, dto.getTypeClcd());
            retValue = prep.executeUpdate();
            
		} catch ( Exception e ) {
			e.printStackTrace();
		} finally {
			try {
				if ( prep != null ) prep.close();
				if ( conn != null ) conn.close();
			} catch ( Exception ie ) {
				ie.printStackTrace();
			}
		}
		return retValue;
	}
	
	@Override
	public int updateItem(FepLineDto dto) {
		int retValue = 0;
		Connection conn = null;
		Statement stat = null;
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE LINE SET ");
		boolean isFirst = true;
		if ( dto.getInstCode() !=null && dto.getInstCode().trim().length() > 0 ) {
			if ( isFirst == false ) sql.append(",");
			sql.append("    INST_CODE='"+dto.getInstCode()+"' ");
			isFirst = false;
		}
		if ( dto.getInstName() !=null && dto.getInstName().trim().length() > 0 ) {
			if ( isFirst == false ) sql.append(",");
			sql.append("    INST_NAME='"+dto.getInstName()+"'' ");
			isFirst = false;
		}
		if ( dto.getInstName() !=null && dto.getApplCode().trim().length() > 0 ) {
			if ( isFirst == false ) sql.append(",");
			sql.append("    APPL_CODE='"+dto.getApplCode()+"'' ");
			isFirst = false;
		}
		if ( dto.getInstName() !=null && dto.getApplName().trim().length() > 0 ) {
			if ( isFirst == false ) sql.append(",");
			sql.append("    APPL_NAME='"+dto.getApplName()+"'' ");
			isFirst = false;
		}
		if ( dto.getInstName() !=null && dto.getTypeClcd().trim().length() > 0 ) {
			if ( isFirst == false ) sql.append(",");
			sql.append("    TYPE_CLCD='"+dto.getTypeClcd()+"'' ");
			isFirst = false;
		}
		sql.append("WHERE SEQ_NUM=" + dto.getSeqNum());
		try {
			conn = getConnection();
			retValue = stat.executeUpdate(sql.toString());
		} catch ( Exception e ) {
			e.printStackTrace();
		} finally {
			try {
				if ( stat != null ) stat.close();
				if ( conn != null ) conn.close();
			} catch ( Exception ie ) {
				ie.printStackTrace();
			}
		}
		return retValue;
	}
	
	private Connection getConnection() throws ClassNotFoundException, SQLException {
		Class.forName("org.sqlite.JDBC");  // 로그인 절차가 없다.
        return  DriverManager.getConnection("jdbc:sqlite:C:/__dev__/ohcapi/git/repository/fep-manager/target/line.db");  
	}
	
}
