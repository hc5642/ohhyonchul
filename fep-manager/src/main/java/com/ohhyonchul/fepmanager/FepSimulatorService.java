package com.ohhyonchul.fepmanager;

import java.util.List;

public interface FepSimulatorService {
	
	public byte [] agenyResponse(byte [] input, String rule) ;
	
	public List<String> getAppList() ;
	
	public List<String> getMsgList(String app) ;
	
	public void startThread(String app, String msg);
	
	public void stopThread(String app, String msg);
	
	public List<String> getThreadList();
	
	public String getRule(String app, String msg) ;
	
	public String getMsgName(String app, String msg) ;
	
	public String getReqColumn(String app, String msg) ;
	
	public String getResColumn(String app, String msg);
	
	public void setData(String app, String msg, String rule, String msgName, String reqColumn, String resColumn);

}
