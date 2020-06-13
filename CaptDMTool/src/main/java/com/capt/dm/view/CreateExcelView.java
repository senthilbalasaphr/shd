package com.capt.dm.view;

import java.lang.reflect.InvocationTargetException;
import com.capt.dm.util.InitVal;
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
import com.capt.dm.util.FOUtility;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.sl.usermodel.TextRun.FieldType;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import com.capt.dm.binding.objects.BindingObject;
import com.capt.dm.controller.FileUploadController;
import com.capt.dm.model.MetaDataObj;
import com.capt.dm.util.InitVal;

public class CreateExcelView extends AbstractXlsxView {

	private static final Logger logger = LoggerFactory.getLogger(CreateExcelView.class);
	private static final String utilPath = "com.capt.dm.util.";
	public Map<String, Map<String, String>> initfuncMap = null;

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub

		logger.info("CreateExcelView: Inside buildExcelDocument Method");

		try {
			HttpSession session = request.getSession();
			String data = session.getAttribute("data").toString();
			String userName = session.getAttribute("UserName").toString();
			String client = model.get("client").toString();
			Map<String, BindingObject> fieldMap = (Map<String, BindingObject>) model.get("fieldMap");
			Map<String, String> valueMap = (Map<String, String>) model.get("valueMap");
			List<List<MetaDataObj>> excelData = (List<List<MetaDataObj>>) model.get("excelData");
			Map<String, Map<String, String>> funcMaps = (Map<String, Map<String, String>>) model.get("funcMap");
			Map<String, String> funcRules = (Map<String, String>) model.get("funcRules");
			Map<String, String> clientSystem = (Map<String, String>) model.get("clientSystem");
			initfuncMap = (Map<String, Map<String, String>>) model.get("initfuncMap");
			int headerIndex = (Integer) model.get("headIndex");
			int valueIndex = (Integer) model.get("valIndex");
			String company = (String) model.get("company");
			String template = (String) model.get("template");
			String tempGrp = (String) model.get("tempGrp");

//			XSSFWorkbook workbook = new XSSFWorkbook();
			Sheet sheet = workbook.createSheet("Department_Odata");

			String fieldValue = "";
			String fieldType = "";

			int rowNum = 0;
			logger.info("Creating excel");

			String isTestRun = (String) model.get("isTestRun");
			logger.info("isTestRun" + isTestRun);
			if (isTestRun.equalsIgnoreCase("0")) {
				logger.info("Select Testrun");
				System.exit(0);
			}

			if (!(userName.equalsIgnoreCase("capt"))) {

				if (isTestRun.equalsIgnoreCase("No")) {
					logger.info("Please run live using capt user");
					System.exit(0);
				}

			}

///// Map (<String>,<ArrayList>)
//			Map<String,Map> initVal = null;
//			FileUploadController fuc = new FileUploadController();
//			fuc.getInitFuncMap( client,  tempGrp,  template);
//			Map<String,Map> initVal = getInitFunc(funcRules, client,tempGrp,template, company, clientSystem, isTestRun);

//-------------------------------Init Method	

			Map<String, Map> initVal = null;
			initVal = new HashMap<String, Map>();
			

			if (initfuncMap.containsKey(template)) {
				Map<String, String> initMap = new HashMap<String, String>();

				initMap = initfuncMap.get(template);
				if (!initMap.isEmpty()) {
					Iterator<Map.Entry<String, String>> itr = initMap.entrySet().iterator();
					while (itr.hasNext()) {
						Map.Entry<String, String> entry = itr.next();
						String ruleKey = entry.getValue();
						if (funcRules.containsKey(ruleKey)) {
							String funcRule = funcRules.get(ruleKey);
							String rule[] = funcRule.split(":");
							String className = rule[0];
							String methodName = rule[1];

							Class<?> clas = Class.forName(utilPath + className);

							Method meth = clas.getMethod(methodName, String.class, String.class, String.class,
									String.class, Map.class, String.class);
							Object utilObject = Class.forName(utilPath + className).newInstance();
							// ValueMap
							InitVal iV = (InitVal) meth.invoke(utilObject, client, tempGrp, template, company,
									clientSystem, isTestRun);
							
							initVal = iV.getInitVal();
						
						}
					}
				}
			}

//-------------------------------Excel Row Iterate			

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
						logger.info("headerCol:" + headerCol);
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
//							newValue = getDate(oldValue);
							newValue = oldValue;

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
										logger.info("CreateExcelView: writeExcel: className:" + className);
										logger.info("CreateExcelView: writeExcel: methodName:" + methodName);
										Method meth = Class.forName(utilPath + className).getMethod(methodName,
												List.class, int.class, String.class, Map.class, String.class,
												Map.class);
										Object utilObject = Class.forName(utilPath + className).newInstance();
										oldValue = (String) meth.invoke(utilObject, rowData, colIndex, company,
												clientSystem, isTestRun, initVal);

										// Sen
										if (!(rowData.get(colIndex).getFieldValue() == null))
											if (!(rowData.get(colIndex).getFieldValue().equalsIgnoreCase(oldValue))) {
												rowData.get(colIndex).setFieldValue(oldValue);
											}
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

						// Print log
						data = "<br>" + headerCol + ":" + newValue + data;
						session.setAttribute("data", data);
						// Print log
					}

					// Senthil
					// **** Set the error values to red color

					if (!(fieldValue == null)) {
						int index = fieldValue.indexOf("<--");
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

//					if (fieldType.equals("STRING")) {
//					cell.setCellType(CellType.STRING);
					cell.setCellValue(fieldValue);
//					cell.setCellFormula(fieldType);
//					} else if (fieldType.equals("NUMERIC")) {
//						cell.setCellValue(fieldValue);
//					}

				}
			}

			logger.info("Session Value::::" + session.getAttribute("data").toString());
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

	private String getInitialObjectsList(String date) throws Exception {

		// String company = (String) model.get("company");

		return null;

	}

//	public Map<String, Map> getInitFunc(Map<String, String> funcRules, String client, String tempGrp, String template,
//			String company, Map<String, String> clientSystem, String isTestRun)
//			throws NoSuchMethodException, SecurityException, ClassNotFoundException, InstantiationException,
//			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
//
//		Map<String, Map> ValueMap = null;
//		Map<String, Object> ObjectsMap = null;
//
//		if (initfuncMap.containsKey(template)) {
//			Map<String, String> initMap = new HashMap<String, String>();
//
//			initMap = initfuncMap.get(template);
//			if (!initMap.isEmpty()) {
//				Iterator<Map.Entry<String, String>> itr = initMap.entrySet().iterator();
//				while (itr.hasNext()) {
//					Map.Entry<String, String> entry = itr.next();
//					String ruleKey = entry.getValue();
//					if (funcRules.containsKey(ruleKey)) {
//						String funcRule = funcRules.get(ruleKey);
//						String rule[] = funcRule.split(":");
//						String className = rule[0];
//						String methodName = rule[1];
//						Method meth = Class.forName(utilPath + className).getMethod(methodName, String.class,
//								String.class, String.class, String.class, Map.class, String.class);
//						Object utilObject = Class.forName(utilPath + className).newInstance();
//						// ValueMap
//						String x = (String) meth.invoke(utilObject, client, tempGrp, template, company, clientSystem,
//								isTestRun);
//					}
//				}
//			}
//		}
//
////		Method meth = Class.forName(utilPath + className).getMethod(methodName,
////				List.class, int.class, String.class, Map.class,String.class);
////		Object utilObject = Class.forName(utilPath + className).newInstance();
////		oldValue = (String) meth.invoke(utilObject, rowData, colIndex, company,
////				clientSystem,isTestRun);
//
////		initfuncMap
////		Method meth = Class.forName(utilPath + className).getMethod(methodName,
////				List.class, int.class, String.class, Map.class,String.class);
////		Object utilObject = Class.forName(utilPath + className).newInstance();
////		oldValue = (String) meth.invoke(utilObject, rowData, colIndex, company,
////				clientSystem,isTestRun);
//
//		return ValueMap;
//
//	}

}
