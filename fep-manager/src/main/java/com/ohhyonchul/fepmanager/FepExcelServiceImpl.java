package com.ohhyonchul.fepmanager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;

@Service
public class FepExcelServiceImpl implements FepExcelService {
	
	@Override
	public void getExcelDown(HttpServletResponse response) {
			
        Map<String, Object> map = new HashMap<String, Object>();
        List<String> list = new ArrayList<String>();
        list.add("ohhyonchul");

        
        try{
            //Excel Down 시작
            Workbook workbook = new HSSFWorkbook();
            //시트생성
            Sheet sheet = workbook.createSheet("게시판");
            
            //행, 열, 열번호
            Row row = null;
            Cell cell = null;
            int rowNo = 0;
            
            // 폰트
            Font font = workbook.createFont();
            font.setFontName("d2coding");
            
            // 테이블 헤더용 스타일
            CellStyle headStyle = workbook.createCellStyle();
            headStyle.setFont(font);
    
            // 가는 경계선을 가집니다.
            headStyle.setBorderTop(BorderStyle.THIN);
            headStyle.setBorderBottom(BorderStyle.THIN);
            headStyle.setBorderLeft(BorderStyle.THIN);
            headStyle.setBorderRight(BorderStyle.THIN);
    
            // 배경색은 노란색입니다.
            headStyle.setFillForegroundColor(HSSFColorPredefined.YELLOW.getIndex());
            headStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    
            // 데이터는 가운데 정렬합니다.
            headStyle.setAlignment(HorizontalAlignment.CENTER);
    
            // 데이터용 경계 스타일 테두리만 지정
            CellStyle bodyStyle = workbook.createCellStyle();
            bodyStyle.setFont(font);
            bodyStyle.setWrapText(true);
            bodyStyle.setBorderTop(BorderStyle.THIN);
            bodyStyle.setBorderBottom(BorderStyle.THIN);
            bodyStyle.setBorderLeft(BorderStyle.THIN);
            bodyStyle.setBorderRight(BorderStyle.THIN);
    
            // 헤더 생성
            row = sheet.createRow(rowNo++);
    
            cell = row.createCell(0);
            cell.setCellStyle(headStyle);
            cell.setCellValue("번호");
    
            cell = row.createCell(1);
            cell.setCellStyle(headStyle);
            cell.setCellValue("이름");
    
            cell = row.createCell(2);
            cell.setCellStyle(headStyle);
            cell.setCellValue("제목");
    
            // 데이터 부분 생성
            for(String map1 : list) {
                row = sheet.createRow(rowNo++);
                cell = row.createCell(0);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue("TEST");
                cell = row.createCell(1);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue("• NAMECOL\nfdghjagfjdhsa");
                cell = row.createCell(2);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue("VALUECOL");
                
                
            }
    
            // 컨텐츠 타입과 파일명 지정
            response.setContentType("ms-vnd/excel");
            response.setHeader("Content-Disposition", "attachment;filename=test.xls");
 
            // 엑셀 출력
            workbook.write(response.getOutputStream());
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }



}
