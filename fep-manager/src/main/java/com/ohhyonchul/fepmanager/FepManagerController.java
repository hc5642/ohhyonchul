package com.ohhyonchul.fepmanager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FepManagerController {
	
	@Autowired
	private FepManagerSvc svc;
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@RequestMapping("/ftp/list")
	@ResponseBody
	public String ftplist(
			@RequestParam(value="path") String path) {
		logger.info("---------------------------------------");
		logger.info("--- PARAM [path]=["+path+"]");
		File [] list = new File(path).listFiles();
		Map<String, String> folderlist = new TreeMap<String, String>();
		Map<String, String> filelist = new TreeMap<String, String>();
		for ( File file : list ) {
			if ( file.isDirectory() ) {
				folderlist.put(file.getName(), NumberFormat.getInstance().format(file.length()));
			} else {
				filelist.put(file.getName(),  NumberFormat.getInstance().format(file.length()));
			}
		}
		String pathback = path;
		String [] pathItems = path.split("\\/");
		if ( pathItems.length > 2 )
			pathback = path.substring(0, path.lastIndexOf("/"));
		StringBuffer retValue = new StringBuffer();
		retValue.append("<html><body><style>body { font-family: d2coding; font-size: 10pt;}</style>");
		retValue.append("<img src='/images/folder.png' style='width: 15px;' />");
		retValue.append("<a href='/ftp/list?path="+pathback+"'>" + path + "</a>");
		retValue.append("<br/>");
		for ( String folder : folderlist.keySet() )  {
			retValue.append("&nbsp;<img src='/images/folder.png' style='width: 15px;'/> ");
			retValue.append("<a href='/ftp/list?path="+path + "/" + folder +"'>" + folder + "</a>");
			retValue.append("<br/>");
		}
		for ( String file : filelist.keySet() )  {
			retValue.append("&nbsp;<img src='/images/file.png' style='width: 15px;'/> ");
			retValue.append("<a href='/ftp/file?path="+path + "/" + file +"'>" + file + " ("+filelist.get(file)+")</a>");
			retValue.append("<br/>");
		}
		retValue.append("</body></html>");
		logger.info("---------------------------------------");
		return retValue.toString();
	}
	
	@RequestMapping("/ftp/file")
	public ResponseEntity<Resource> file(
			@RequestParam("path") String path) throws FileNotFoundException {
		File file = new File(path);
		InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType("application/octet-stream"))
				.body(resource);
	}
	
	@RequestMapping("/ftp")
	public String ftp(
			@RequestParam(value="path", defaultValue="/Users/hyonchuloh") String path,
			@RequestParam(value="pathview", defaultValue="") String pathview,
			Model model) {
		
		logger.info("---------------------------------------");
		logger.info("--- PARAM [path]=["+path+"]");
		logger.info("--- PARAM [pathview]=["+pathview+"]");
		
		File [] list = new File(path).listFiles();
		Map<String, String> folderlist = new TreeMap<String, String>();
		Map<String, String> filelist = new TreeMap<String, String>();
		boolean isDown = false;
		for ( File file : list ) {
			if ( file.isDirectory() ) {
				folderlist.put(file.getName(), NumberFormat.getInstance().format(file.length()));
			} else {
				filelist.put(file.getName(),  NumberFormat.getInstance().format(file.length()));
				if ( pathview.length() > 0 && pathview.equals(file.getName())) {
					if ( file.length() > 2000000 )  {
						isDown = true;
					}
				}
			}
		}
		model.addAttribute("path", path);
		String [] pathItems = path.split("\\/");
		if ( pathItems.length > 2 )
			model.addAttribute("pathback", path.substring(0, path.lastIndexOf("/")));
		else
			model.addAttribute("pathback", path);
		
		StringBuffer viewtext = new StringBuffer();
		if ( pathview.length() > 0 && !isDown ) {
			FileReader fr = null;
			BufferedReader br = null;
			try {
				fr = new FileReader(path + "/" + pathview);
				br = new BufferedReader(fr);
				
				String temp = "";
				while ( ( temp = br.readLine() ) != null ) {
					viewtext.append(temp);
				}
			} catch ( IOException ioe ) {
				System.err.println(ioe);
			} finally {
				try {
					if ( br != null ) br.close();
					if ( fr != null ) fr.close();
				} catch ( Exception e )  {}
			}
		}
		
		model.addAttribute("folderlist", folderlist);
		model.addAttribute("filelist", filelist);
		model.addAttribute("viewtext", viewtext.toString());
		return "ftp";
	}
	
	@PostMapping("/test-out-http")
	public String testOutHttpPost(
			@RequestParam("req_url") String reqUrl,
			@RequestParam("req_headers") String reqHeaders,
			@RequestParam("req_body") String reqBody,
			@RequestParam("req_type") String reqType,
			@RequestParam("req_method") String reqMethod,
			@RequestParam("req_params") String reqParams,
			Model model
			) {
		model.addAttribute("req_url", reqUrl);
		model.addAttribute("req_headers", reqHeaders);
		model.addAttribute("req_params", reqParams);
		model.addAttribute("req_body", reqBody);
		model.addAttribute("req_type", reqType);
		return "test-out-http";
	}
	
	
	
	@GetMapping("/test-out-http")
	public String testOutHttpGet(Model model) {
		return "test-out-http";
	}
	
	@RequestMapping("/hello")
	public String hello(
			@RequestParam("userid") String userId,
			@RequestParam("username") String username, 
			Model model) {
		
		model.addAttribute("userid", userId);
		model.addAttribute("username", username);
		
		return "hello";
	}
	
	@GetMapping("/calendar/{name}")
	public String calelndar(
			@PathVariable(value="name") String name,
			@RequestParam(value="year", required=false) String year,
			@RequestParam(value="month", required=false) String month,
			Model model) {
		Calendar cal = Calendar.getInstance();
		int yearInt = cal.get(Calendar.YEAR);
		int monthInt = cal.get(Calendar.MONTH)+1;
		if ( year != null && month != null )  {
			yearInt = Integer.parseInt(year);
			monthInt = Integer.parseInt(month);
			if ( monthInt == 13 )  {
				yearInt += 1;
				monthInt = 1;
			} else if ( monthInt == 0 )  {
				yearInt -= 1;
				monthInt = 12;
			}
		}
		model.addAttribute("yearInt", yearInt);
		model.addAttribute("monthInt", monthInt);
		model.addAttribute("dayTable", svc.getCalendarTable(cal, yearInt, monthInt));
		model.addAttribute("contents", svc.loadMap("/Users/hyonchuloh/eclipse-workspace/fep-manager/target/calendar."+name+"."+yearInt+".dat"));
			
		return "calendar";
	}
	
	@PostMapping("/calendar/{name}")
	public String calelndarPost(
			@PathVariable(value="name") String name,
			@RequestParam(value="year") String year,
			@RequestParam(value="month") String month,
			@RequestParam(value="key") String key,
			@RequestParam(value="value") String value,
			Model model) {
		Calendar cal = Calendar.getInstance();
		int yearInt = Integer.parseInt(year);
		int monthInt = Integer.parseInt(month);
		System.out.println("--- INPUT PARAM : key=["+key+"], value=["+value+"]");
		String filePath = "/Users/hyonchuloh/eclipse-workspace/fep-manager/target/calendar."+name+"."+yearInt+".dat";
		System.out.println("--- SAVE RESULT : " + svc.saveMap(filePath, key, value));
		model.addAttribute("yearInt", yearInt);
		model.addAttribute("monthInt", monthInt);
		model.addAttribute("dayTable", svc.getCalendarTable(cal, yearInt, monthInt));
		model.addAttribute("contents", svc.loadMap(filePath));
			
		return "calendar";
	}
	
	@Autowired
	private FepExcelService excelSvc;
	
	@GetMapping(path="/excel")
	public void downloadExcel2(HttpServletResponse response) {
		excelSvc.getExcelDown(response);
	}
}
