package com.ohhyonchul.fepmanager.thread;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DecimalFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ohhyonchul.fepmanager.FepSimulatorService;

public class FepSimulatorThread extends Thread {
	
	@Autowired
	FepSimulatorService svc;
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private boolean IS_COUNTINUE = true;
	private int PORT = 9999;
	private String rule;
	private final String lengthStyle = "0000";
	
	public FepSimulatorThread(String rule) {
		this.rule = rule;
		logger.info("--- 스레드 created.");
	}
	
	public int getPortNumber() {
		return this.PORT;
	}
	
	public void destory() {
		
		logger.info("--- 스레드 ["+this.PORT+"] destroy start");
		this.IS_COUNTINUE = false;
		Socket socket = null;
		OutputStream os = null;
		try {
			socket = new Socket();
			socket.connect(new InetSocketAddress("127.0.0.1", 9999));
			os = socket.getOutputStream();
			os.write("bbbb".getBytes());
			os.flush();
		} catch ( Exception e ) {
			logger.error("--- ERROR (process down): " + e, e);
		} finally {
			try {
				if ( os != null ) os.close();
				if ( socket != null ) socket.close();
			} catch ( Exception e ) {}
		}
		logger.info("--- 스레드 ["+this.PORT+"]destroy end.");
	}
	
	@Override
	public void run() {
		DecimalFormat df = new DecimalFormat(lengthStyle);
		
		ServerSocket server = null;
		Socket socket = null;
		
		InputStream is = null;
		OutputStream os = null;
		DataInputStream dis = null;
		DataOutputStream dos = null;
		
		int readLength = 0;
		int bodyLength = 0;
		String tempStr = "";
		
		try {
			
			server = new ServerSocket(9999);
			
			while ( true ) {
				
				if ( IS_COUNTINUE == false ) {
					logger.info("--- 종료시그널...");
					break;
				}
				
				try {
					
					socket = server.accept();
					is = socket.getInputStream();
					os = socket.getOutputStream();
					
					dis = new DataInputStream(is);
					dos = new DataOutputStream(os);
					
					byte [] lengthBytes = new byte[lengthStyle.length()];
					readLength = dis.read(lengthBytes);
					tempStr = new String(lengthBytes);
					bodyLength = df.parse(tempStr).intValue();
					logger.info("--- READ LENGTH ["+readLength+"]=["+new String(lengthBytes)+"]");
					
					byte [] bodyBytes = new byte[bodyLength];
					readLength = dis.read(bodyBytes);
					
					byte [] responseBytes = svc.agenyResponse(bodyBytes, this.rule);
					tempStr = df.format(responseBytes.length);
					dos.write(tempStr.getBytes());
					dos.write(responseBytes);
					dos.flush();
					
				} catch ( IOException e ) {
					logger.error("--- ERROR : " + e, e);
				} finally {
					try {
						if ( dos != null ) dos.close();
						if ( dis != null ) dis.close();
						if ( os != null ) os.close();
						if ( is != null ) is.close();
						if ( socket != null ) socket.close();
						Thread.sleep(20*1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			} 
			
		} catch ( Exception e ) {
			logger.error("--- ERROR (process down): " + e, e);
		} finally {
			try {
				if ( socket != null ) socket.close();
				if ( server != null ) server.close();
			} catch (Exception e ) {}
		}
		
	}
}
