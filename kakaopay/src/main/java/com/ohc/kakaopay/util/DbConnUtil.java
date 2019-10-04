package com.ohc.kakaopay.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnUtil {
	
	private static DbConnUtil instance;
	public static DbConnUtil getInstance() {
		if ( instance == null ) 
			instance = new DbConnUtil();
		return instance;
	}
	
	private DbConnUtil() {}
	
	public Connection getConnection() throws ClassNotFoundException, SQLException {
		
		Class.forName("org.sqlite.JDBC");
		return DriverManager.getConnection("jdbc:sqlite:src/main/resources/static/db/custom.db");
		
	}

}
