package com.capt.dm.view;

import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.sl.usermodel.TextRun.FieldType;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Color;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import com.capt.dm.binding.objects.BindingObject;
import com.capt.dm.model.MetaDataObj;

public class CreateExcelView_old extends AbstractXlsxView {

	private static final Logger logger = LoggerFactory.getLogger(CreateExcelView_old.class);
	private static final String utilPath = "com.capt.dm.util.";
	static final short SOLID_FOREGROUND = 1;

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub

		logger.info("CreateExcelView: Inside buildExcelDocument Method");

		try {
			String client = model.get("client").toString();
			Map<String, BindingObject> fieldMap = (Map<String, BindingObject>) model.get("fieldMap");
			Map<String, BindingObject> fieldInitMap = (Map<String, BindingObject>) model.get("InitfuncMap");
			Map<String, String> valueMap = (Map<String, String>) model.get("valueMap");
			List<List<MetaDataObj>> excelData = (List<List<MetaDataObj>>) model.get("excelData");
			Map<String, Map<String, String>> funcMaps = (Map<String, Map<String, String>>) model.get("funcMap");
			Map<String, String> funcRules = (Map<String, String>) model.get("funcRules");
			Map<String, String> clientSystem = (Map<String, String>) model.get("clientSystem");
			int headerIndex = (Integer) model.get("headIndex");
			int valueIndex = (Integer) model.get("valIndex");
			String company = (String) model.get("company");

//			XSSFWorkbook workbook = new XSSFWorkbook();
			Sheet sheet = workbook.createSheet("Department_Odata");

			String fieldValue = "";
			String fieldType = "";

			int rowNum = 0;
			logger.info("Creating excel");
			String isTestRun = (String) model.get("isTestRun");
			logger.info("isTestRun"+isTestRun);
			if (isTestRun.equalsIgnoreCase("0")) {
				logger.info("Select Testrun");
				System.exit(0);
			}
			
			

			for (List<MetaDataObj> rowData : excelData) {
				Row row = sheet.createRow(rowNum++);
				int colNum = 0;
				for (MetaDataObj columnData : rowData) {
					int colIndex = colNum++;
					Cell cell = row.createCell(colIndex, CellType.BLANK); 
					if (rowNum <= valueIndex - 1) {
						fieldValue = columnData.getFieldValue();
						fieldType = columnData.getFieldType();
					} else {
						String headerCol = excelData.get(headerIndex - 1).get(colIndex).getFieldValue(); 
						String typeCol = excelData.get(1).get(colIndex).getFieldValue();
						String valueMapping = fieldMap.get(headerCol).getValueMapping();
						String colValue = columnData.getFieldValue();
						/*
						 * logger.info("CreateExcelView: writeExcel: client:" + client);
						 * logger.info("CreateExcelView: writeExcel: valueMapping:" + valueMapping);
						 * logger.info("CreateExcelView: writeExcel: colValue:" + colValue);
						 */
//						logger.info("CreateExcelView: writeExcel: colIndex:" + colIndex);
//						logger.info("CreateExcelView: writeExcel: colValue:" + colValue);
//						logger.info("CreateExcelView: writeExcel: headerCol:" + headerCol);
//						logger.info("CreateExcelView: writeExcel: typeCol:" + typeCol);
//						logger.info("CreateExcelView: writeExcel: funcMaps:" + funcMaps.toString());
//						logger.info("CreateExcelView: writeExcel: funcRules:" + funcRules.toString());
						String key = client + valueMapping + colValue;
//						logger.info("CreateExcelView: writeExcel: key:" + key);
						String oldValue = colValue;
						String newValue = null;
						if (null != columnData.getFieldType() && "date".equalsIgnoreCase(columnData.getFieldType())) {
//							logger.info("CreateExcelView: writeExcel: Type:" + columnData.getFieldType());
//Senthil						
							// newValue = getDate(oldValue);
							newValue = oldValue;
//Senthil	
						}

						if (null != valueMap.get(key) && !valueMap.get(key).isEmpty()) {
							newValue = valueMap.get(key); // this is for fetching values using value mapping
						}

						if (funcMaps.containsKey(headerCol)) {
							Map<String, String> funcMap = new HashMap<String, String>();
							funcMap = funcMaps.get(headerCol);
//							logger.info("CreateExcelView: writeExcel: funcValue:" + funcMap.toString());
							if (!funcMap.isEmpty()) {
								Iterator<Map.Entry<String, String>> itr = funcMap.entrySet().iterator();
								while (itr.hasNext()) {
									Map.Entry<String, String> entry = itr.next();
									String ruleKey = entry.getValue();
//									logger.info("CreateExcelView: writeExcel: ruleKey:" + ruleKey);
									if (funcRules.containsKey(ruleKey)) {
										String funcRule = funcRules.get(ruleKey);
										String rule[] = funcRule.split(":");
										String className = rule[0];
										String methodName = rule[1];
//										logger.info("CreateExcelView: writeExcel: className:" + className);
//										logger.info("CreateExcelView: writeExcel: methodName:" + methodName);
										Method meth = Class.forName(utilPath + className).getMethod(methodName,
												List.class, int.class, String.class, Map.class,String.class);
										Object utilObject = Class.forName(utilPath + className).newInstance();
										oldValue = (String) meth.invoke(utilObject, rowData, colIndex, company,
												clientSystem,isTestRun);

//										logger.info("CreateExcelView: writeExcel: New Value:" + oldValue);
									}
								}
								newValue = oldValue;
							}
						}
						//
						if (null != newValue && !newValue.isEmpty()) {
							fieldValue = newValue;
						} else {
							fieldValue = oldValue;
						}
						fieldType = columnData.getFieldType();
					}

// Senthil
//**** Set the error values to red color
					
					if (!(fieldValue == null)) {
					int index=fieldValue.indexOf("<--");
					logger.info("fieldValue:" + fieldValue);
					if (index != -1) {
						CellStyle style = workbook.createCellStyle();
						Font font = workbook.createFont();
						font.setColor(HSSFColor.HSSFColorPredefined.RED.getIndex());
						style.setFont(font);

						cell.setCellStyle(style);
			        }
					}
					
// Senthil				
					cell.setCellValue(fieldValue);

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Done");

	}

	private String getDate(String date) throws Exception {

		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
		Date newDate = (Date) formatter.parse(date);
//		System.out.println(date);

		Calendar cal = Calendar.getInstance();
		cal.setTime(newDate);
		String formatedDate = String.valueOf((cal.get(Calendar.MONTH) + 1)) + "/"
				+ String.valueOf(cal.get(Calendar.DATE)) + "/" + cal.get(Calendar.YEAR);
		/*
		 * DateTimeFormatter f = DateTimeFormatter.ofPattern( "E MMM dd HH:mm:ss z uuuu"
		 * ) .withLocale( Locale.US ); ZonedDateTime zdt = ZonedDateTime.parse( date , f
		 * ); LocalDate ld = zdt.toLocalDate(); DateTimeFormatter fLocalDate =
		 * DateTimeFormatter.ofPattern( "MM/dd/uuuu" ); String formatedDate = ld.format(
		 * fLocalDate) ;
		 */
		System.out.println("formatedDate : " + formatedDate);
		return formatedDate;
	}
	
	

}
