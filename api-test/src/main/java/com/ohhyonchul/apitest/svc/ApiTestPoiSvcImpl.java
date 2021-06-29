package com.ohhyonchul.apitest.svc;

import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class ApiTestPoiSvcImpl implements ApiTestPoiSvc {
	
	@Override
	public String doService() {
		Workbook workbook = new HSSFWorkbook();
		Sheet sheet = workbook.createSheet("FEP LINE INFO");
		
		Row row = null;
		Cell cell = null;
		int rowNo = 0;
		
		Font font = workbook.createFont();
		font.setFontName("d2coding");
		
		CellStyle headStyle = workbook.createCellStyle();
		headStyle.setFont(font);
		row = sheet.createRow(rowNo++);
		cell = row.createCell(0);
		cell.setCellStyle(headStyle);
		cell.setCellValue("NO");
		
		try {
			workbook.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
