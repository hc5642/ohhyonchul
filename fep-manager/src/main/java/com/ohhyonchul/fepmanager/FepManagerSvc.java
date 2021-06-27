package com.ohhyonchul.fepmanager;

import java.util.Calendar;
import java.util.Map;

public interface FepManagerSvc {
	
	public int [][] getCalendarTable(Calendar calendar, int year, int month);
	
	public Map<String, String> loadMap(String filePath) ;
	
	public int saveMap(String filePath, String key, String value);
	

}
