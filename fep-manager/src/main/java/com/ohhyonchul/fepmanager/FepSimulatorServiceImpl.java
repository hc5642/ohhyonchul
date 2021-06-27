package com.ohhyonchul.fepmanager;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ohhyonchul.fepmanager.thread.FepSimulatorThread;

@Service
public class FepSimulatorServiceImpl implements FepSimulatorService {
	
	private Map<String, String> data = new HashMap<String, String>();
	private Map<String, FepSimulatorThread> threadMap = new HashMap<String, FepSimulatorThread>();
	
	@Override
	public byte [] agenyResponse(byte [] input, String rule) {
		
		byte [] output = null;
		
		/* 전문의 최종길이를 구한다 */
		int totalSizeOfResponseMssage = 0;
		for ( String k : rule.split("\\$\\{|\\}") )  {
			if ( k.length() == 0 )
				continue;
			if ( k.indexOf(",") > 0 ) {
				totalSizeOfResponseMssage += Integer.parseInt(k.split(",")[1]) - Integer.parseInt(k.split(",")[0]);
			} else if ( k.startsWith("'") ) {
				byte [] col = k.replaceAll("'", "").getBytes();
				totalSizeOfResponseMssage += col.length;
			}
		}
		
		/* 요청전문에 대한 대행응답 전문을 매핑한다 */
		output = new byte[totalSizeOfResponseMssage];
		int outputMsgPoint = 0;
		for ( String k : rule.split("\\$\\{|\\}") )  {
			if ( k.length() == 0 )
				continue;
			if ( k.indexOf(",") > 0 ) {
				String [] indexs = k.split(",");
				int startIndex = Integer.parseInt(indexs[0]);
				int endIndex = Integer.parseInt(indexs[1]);
				System.arraycopy(input, startIndex, output, outputMsgPoint, endIndex-startIndex);
				outputMsgPoint += endIndex-startIndex;
			} else if ( k.startsWith("'") ) {
				byte [] col = k.replaceAll("'", "").getBytes();
				System.arraycopy(col, 0, output, outputMsgPoint, col.length);
				outputMsgPoint += col.length;
			}
		}
		
		return output;
	}
	
	public FepSimulatorServiceImpl() {
		data.put("sim.130O.0200_300000.msgname", "타행수취조회");
		data.put("sim.130O.0200_300000.reqcolumn", "${트랜잭션코;C;9}${은행코드;C;3}${종별코드;C;4}${거래코드;C;6}${거래일자;C;8}${거래시간;C;6}${응답코드;C;3}${예비필드;C;150}${주민번호;C;13}");
		data.put("sim.130O.0200_300000.rescolumn", "${트랜잭션코;C;9}${은행코드;C;3}${종별코드;C;4}${거래코드;C;6}${거래일자;C;8}${거래시간;C;6}${응답코드;C;3}${예비필드;C;150}${주민번호;C;13}${계좌번호;C;20}${실명번호;C;13}");
		data.put("sim.130O.0200_300000.rule", "${0,9}${9,12}${'0210'}${16,22}${22,30}${30,36}${'000'}${39,189}${189,202}${'14806223802019'}${'8201211337413'}");
	}
	
	public void startThread(String app, String msg) {
		FepSimulatorThread thread = new FepSimulatorThread(this.getRule(app, msg));
		this.threadMap.put(app + "_" + msg, thread);
		thread.start();
	}
	
	@Override
	public void stopThread(String app, String msg) {
		FepSimulatorThread thread = this.threadMap.get(app + "_" + msg);
		Socket socket = new Socket();
		OutputStream os = null;
		try {
			socket.connect(new InetSocketAddress("127.0.0.1", thread.getPortNumber()), 500);
			os = socket.getOutputStream();
			os.write("aaaa".getBytes());
			os.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if ( os!=null) os.close();
				if ( socket!=null) socket.close();
			} catch ( Exception e ) {}
		}
		this.threadMap.remove(app+ "_" + msg);
	}
	
	public List<String> getThreadList() {
		List<String> retValue = new ArrayList<String>();
		for ( String key : threadMap.keySet() ) {
			retValue.add(key);
		}
		return retValue;
	}
	
	/* 화면에 보여주기 위한 써머리 */
	public List<String> getAppList() {
		List<String> retValue = new ArrayList<String>();
		for ( String key : data.keySet()) {
			String [] keyCol = key.split("\\.");
			if ( !retValue.contains(keyCol[1])) {
				retValue.add(keyCol[1]);
			}
		}
		return retValue;
	}
	public List<String> getMsgList(String app) {
		List<String> retValue = new ArrayList<String>();
		for ( String key : data.keySet()) {
			if ( !key.startsWith("sim." + app))
				continue;
			String [] keyCol = key.split("\\.");
			if ( !retValue.contains(keyCol[2])) {
				retValue.add(keyCol[2]);
			}
		}
		return retValue;
	}
	
	/* 정보 가져오기 서비스 */
	public String getRule(String app, String msg) {
		return data.get("sim."+app+"."+msg+".rule");
	}
	public String getMsgName(String app, String msg) {
		return data.get("sim."+app+"."+msg+".msgname");
	}
	public String getReqColumn(String app, String msg) {
		return data.get("sim."+app+"."+msg+".reqcolumn");
	}
	public String getResColumn(String app, String msg) {
		return data.get("sim."+app+"."+msg+".rescolumn");
	}
	
	/* 정보 저장하기 서비스 */
	@Override
	public void setData(String app, String msg, String rule, String msgName, String reqColumn, String resColumn) {
		data.put("sim."+app+"."+msg+".rule", rule);
		data.put("sim."+app+"."+msg+".msgname", msgName);
		data.put("sim."+app+"."+msg+".reqcolumn", reqColumn);
		data.put("sim."+app+"."+msg+".rescolumn", resColumn);
	}
	

}
