package com.ohhyonchul.fepmanager.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 최초실행되어 매 5분마다 특정 파일시스템 사용량을 파일에 기록한다.
 * @author hyonchuloh
 *
 */
public class FepVolumeThread extends Thread {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private boolean isContinue = true;
	
	public FepVolumeThread() {
		
	}
	
	public void destory() {
		this.isContinue = false;
		
	}
	
	@Override
	public void run() {
		
	}
	

}
