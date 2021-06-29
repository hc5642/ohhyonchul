package com.ohhyonchul.apitest.svc;

import java.io.BufferedWriter;
import java.io.FileWriter;

public class ApiTestPoiSvcImpl implements ApiTestPoiSvc {
	
	@Override
	public String doService() {
		
		BufferedWriter bw = null;
		FileWriter fw = null;
		try {
			fw = new FileWriter("test.csv");
			bw = new BufferedWriter(fw);
			
			fw.write("test, test, test, \"test\ntest\"");
			fw.flush();
		} catch ( Exception e ) {
			e.printStackTrace();
		} finally {
			try {
				if ( fw != null ) fw.close();
				if ( bw != null ) bw.close();
			} catch  ( Exception e1 ) {
				e1.printStackTrace();
			}
		}
		
		return null;
	}

}
