package com.ohhyonchul.fepmanager;

public class FepManagerVo {
	
	private String userid;
	private String username;
	public FepManagerVo(String userid, String username) {
		this.userid = userid;
		this.username = username;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	} 
	
	

}
