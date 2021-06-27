package com.ohhyonchul.fepmanager;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class FepManagerSvcImpl implements FepManagerSvc {
	
	public ResponseEntity<Object> sendHttp(String reqUrl, String reqHeaders, String reqBody, String reqType) {
		RestTemplate rest = new RestTemplate();
		return null;
	}
	
	@Override
	public int [][] getCalendarTable(Calendar calendar, int year, int month) {
		calendar.set(year, month-1, 1);
		int lastDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		int firstDay = calendar.get(Calendar.DAY_OF_WEEK);
		int retValue[][] = new int[7][7];
		int daycount = 1;
		for ( int i=0; i<7; i++) {
			for ( int j=0; j<7; j++) {
				if ( firstDay -1 > 0 ) {
					retValue[i][j] = 0;
					firstDay--;
					continue;
				} else if ( daycount > lastDay ) {
					retValue[i][j] = -1;
				} else {
					retValue[i][j] = daycount;
				}
				daycount++;
			}
		}
		return retValue;
	}
	
	@Override
	public Map<String, String> loadMap(String filePath) {
		Map<String, String> map = new HashMap<String, String>();
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(new FileInputStream(filePath));
			map = (Map<String, String>) ois.readObject();
		} catch ( Exception e ) {
			e.printStackTrace();
		} finally {
			try {
				if ( ois != null ) ois.close();
			} catch ( Exception e ) {
				//
			}
		}
		return map;
	}
	
	/**
	 * key = CAL.${yearInt}.${monthInt}.${dayInt}
	 * value = td.innerHTML
	 */
	@Override
	public int saveMap(String filePath, String key, String value) {
		Map<String, String> map = loadMap(filePath);
		for ( String tempKey : map.keySet() ) {
			System.out.println("--- CURRENT MAP ["+tempKey+"]=["+map.get(tempKey)+"]");
		}
		if ( map == null )  map = new HashMap<String, String>();
		if ( value != null && value.trim().length() > 0 ) {
			map.put(key, value.trim());
			System.out.println("--- SAVE MAP ["+key+"]=["+value.trim()+"]");
		}
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(new FileOutputStream(filePath));
			oos.writeObject(map);
			System.out.println("--- SAVE SUCCESS!");
		} catch ( Exception e ) {
			e.printStackTrace();
		} finally {
			try {
				if ( oos !=null ) oos.close();
			} catch ( Exception e ) {
				//
			}
		}
		return 1;
	}
	
	

}
