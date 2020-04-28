package com.sfec.shd.UtilityFunction;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.TimeZone;

import javax.xml.soap.SOAPHeader;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.DateFormatConverter;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
 
/**
 * A dirty simple program that reads an Excel file.
 * @author www.codejava.net
 *
 */
public class SimpleExcelReaderExample {
     
    public static void main(String[] args) throws IOException {
        String excelFilePath = "/Users/baps/Downloads/SVI (1)/01.1 SVI_Basic User Import _Vi/SVI_BasicUserInfo.xlsx";
        FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
        TimeZone zone = TimeZone.getTimeZone("Asia/Singapore");
        TimeZone.setDefault(zone);
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet firstSheet = workbook.getSheetAt(0);
        Iterator<Row> iterator = firstSheet.iterator();
         
        while (iterator.hasNext()) {
            Row nextRow = iterator.next();
            Iterator<Cell> cellIterator = nextRow.cellIterator();
             
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                
              
                
                
                switch (cell.getCellType()) {
                    case STRING:
                        System.out.print(cell.getStringCellValue());
                        break;
                    case BOOLEAN:
                    //    System.out.print(cell.getBooleanCellValue());
                        break;
                    case NUMERIC:
                        System.out.print(cell.getNumericCellValue());
                        
                        // System.out.print(cell.getCellType());
                        if( HSSFDateUtil.isCellDateFormatted(cell)) {
                     	   
                      
                        	SimpleDateFormat sf = 	new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
                        	Date date = cell.getDateCellValue();
                        	
                        	
                     	 // System.out.print(cell.getDateCellValue().toGMTString()+" =");
                     	 System.out.print(cell.getDateCellValue());
                     	 
                     	Calendar c = Calendar.getInstance(); 
                     	System.out.print(c.getTimeZone());
                     	c.setTime(date);
                     	String datex =  c.getTime().toString();
                     			

                     	String pattern = "MM/dd/yyyy";
                     	SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                     	String dateC = simpleDateFormat.format(date);
                     	
                     	 System.out.print(dateC) ;
                     	 
                     	 
                        }
                        
                        break;
                   
                }
                System.out.print(" - ");
            }
            System.out.println();
        }
         
        workbook.close();
        inputStream.close();
    }
 
}