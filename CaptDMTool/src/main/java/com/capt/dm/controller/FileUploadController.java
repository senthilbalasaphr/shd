package com.capt.dm.controller;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.capt.dm.binding.objects.BindingObject;
import com.capt.dm.delegate.ODataDelegate;
import com.capt.dm.model.FieldSet;
import com.capt.dm.model.MetaDataObj;
import com.capt.dm.model.Result;
import com.capt.dm.view.CreateExcelView;

@Controller
public class FileUploadController {

	private static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);
	public int columnSize;
	public int valIndex;
	public int headIndex;
	public Map<String, Map<String, String>> initfuncMap=null;
	public Map<String, String> funcRules =null;
	private static final String utilPath = "com.capt.dm.util.";
	@Autowired
	ODataDelegate oDataDelegate;

//	@RequestMapping(value = "/uploadExcelFile", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	@RequestMapping(value = "/uploadExcelFile", method = RequestMethod.POST)
	public ModelAndView uploadExcelFile(MultipartFile file, @RequestParam("comboClient") String client,
			@RequestParam("comboTemplateGrp") String templateGrp, @RequestParam("comboTemplate") String template,
			@RequestParam("comboCompany") String company, @RequestParam("headerIndex") String headerIndex,
			@RequestParam("valueIndex") String valueIndex, @RequestParam("comboTest") String isTestRun)
			throws Exception {

		logger.info("FileUploadController: Inside uploadExcelFile Method");

		logger.info("=======================================================================");
		logger.info("FileUploadController: uploadExcelFile: Client: " + client);
		logger.info("FileUploadController: uploadExcelFile: templateGrp: " + templateGrp);
		logger.info("FileUploadController: uploadExcelFile: template: " + template);
		logger.info("FileUploadController: uploadExcelFile: Company: " + company);
		logger.info("FileUploadController: uploadExcelFile: headerIndex: " + headerIndex);
		logger.info("FileUploadController: uploadExcelFile: valueIndex: " + valueIndex);
		logger.info("FileUploadController: uploadExcelFile: isTestRun: " + isTestRun);
		logger.info("=======================================================================");

		ModelAndView model = null;
		this.valIndex = Integer.parseInt(valueIndex);
		this.headIndex = Integer.parseInt(headerIndex);

		logger.info("FileUploadController: File Name:" + file.getOriginalFilename());
		logger.info("FileUploadController: File Size in bytes:" + file.getSize());

//		String[] input = templateGrp.split(" - ");
		String tempGrp = templateGrp;

		FieldSet fieldSet = oDataDelegate.getFieldset(client, tempGrp, template);
		List<BindingObject> resultBean = new ArrayList<BindingObject>();

		Map<String, BindingObject> fieldMap = new HashMap<String, BindingObject>();

		BindingObject bindingObj = null;
		if (!fieldSet.getD().getResults().isEmpty()) {
			this.columnSize = fieldSet.getD().getResults().size();
			for (Result result : fieldSet.getD().getResults()) {
				bindingObj = new BindingObject();
				bindingObj.setClient(result.getClient());
				bindingObj.setFieldLength(String.valueOf(result.getFIELDLENGTH()));
				bindingObj.setFieldType(result.getFIELDTYPE());
				bindingObj.setFieldName(result.getFIELDNAME());
				bindingObj.setFunctionId(result.getFUNCTIONID());
				bindingObj.setFunctionRoutine(result.getFUNCTIONROUTINE());
				bindingObj.setSeqNo(String.valueOf(result.getSEQNR()));
				bindingObj.setTemplate(result.getTEMPLATE());
				bindingObj.setTemplateGrp(result.getTEMPLATEGRP());
				bindingObj.setValueMapping(result.getVALUEMAPPING());
				resultBean.add(bindingObj);

				fieldMap.put(result.getFIELDNAME(), bindingObj);
			}
		}

		Map<String, String> valueMap = getValueMap(client);
		Map<String, Map<String, String>> funcMap = getFuncMap(client, tempGrp, template);
		initfuncMap = getInitFuncMap(client, tempGrp, template);
		funcRules = getFuncRules(client);
		Map<String, String> clientSystem = oDataDelegate.getClientSystem(client);
		List<List<MetaDataObj>> excelData = readFileData(file.getInputStream());
		model = new ModelAndView(new CreateExcelView());
		model.addObject("client", client);
		model.addObject("headIndex", this.headIndex);
		model.addObject("valIndex", this.valIndex);
		model.addObject("tempGrp", tempGrp);
		model.addObject("template", template);
		
///		Senthil Bala
		model.addObject("isTestRun", isTestRun);
		
		
		model.addObject("company", company);
		model.addObject("funcRules", funcRules);
		model.addObject("clientSystem", clientSystem);
		model.addObject("fieldMap", fieldMap);
		model.addObject("valueMap", valueMap);
		model.addObject("excelData", excelData);
		model.addObject("funcMap", funcMap);
		model.addObject("initfuncMap", initfuncMap);
		model.addObject("funcRules", funcRules);

		return model;
	}

	private Map<String, Map<String, String>> getFuncMap(String client, String tempGrp, String template)
			throws Exception {

		logger.info("FileUploadController: Inside getFuncMap()");

		Map<String, Map<String, String>> funcMap = new HashMap<String, Map<String, String>>();
		FieldSet fieldSet = oDataDelegate.getFuncMap(client, tempGrp, template);
		String mKey = null;
		if (!fieldSet.getD().getResults().isEmpty()) {
			for (Result result : fieldSet.getD().getResults()) {
				mKey = result.getFIELDNAME();
				String key = String.valueOf(result.getSEQNR());
				String funcId = result.getFUNCTIONID();
				String funcRoutine = result.getFUNCTIONROUTINE();
				Map<String, String> subMap = null;
				if (null != mKey && !mKey.isEmpty()) {
					logger.info("FileUploadController: getFuncMap(): funcMap:" + funcMap);
					if (null != funcMap && !funcMap.containsKey(mKey)) {
						logger.info("FileUploadController: getFuncMap(): mKey:" + mKey);
						subMap = new HashMap<String, String>();
					} else if (null != funcMap && !funcMap.isEmpty() && funcMap.containsKey(mKey)) {
						subMap = funcMap.get(mKey);
						funcMap.remove(mKey);
					}
					logger.info("FileUploadController: getFuncMap(): subMap:" + subMap);
					subMap.put(key, funcId + ":" + funcRoutine);
					logger.info("FileUploadController: getFuncMap(): subMap string:" + subMap.toString());
					funcMap.put(mKey, subMap);
				}
			}
		}
		logger.info("FileUploadController: getFuncMap(): funcMap:" + funcMap.toString());
		return funcMap;
	}
	
	public Map<String, Map<String, String>> getInitFuncMap(String client, String tempGrp, String template)
			throws Exception {

		logger.info("FileUploadController: Inside getFuncMap()");

		Map<String, Map<String, String>> funcMap = new HashMap<String, Map<String, String>>();
		FieldSet fieldSet = oDataDelegate.getInitFuncMap(client, tempGrp, template);
		String mKey = null;
		if (!fieldSet.getD().getResults().isEmpty()) {
			for (Result result : fieldSet.getD().getResults()) {
				mKey = result.getTEMPLATE();
				String key = String.valueOf(result.getSEQNR());
				String funcId = result.getFUNCTIONID();
				String funcRoutine = result.getFUNCTIONROUTINE();
				Map<String, String> subMap = null;
				if (null != mKey && !mKey.isEmpty()) {
					logger.info("FileUploadController: getFuncMap(): funcMap:" + funcMap);
					if (null != funcMap && !funcMap.containsKey(mKey)) {
						logger.info("FileUploadController: getFuncMap(): mKey:" + mKey);
						subMap = new HashMap<String, String>();
					} else if (null != funcMap && !funcMap.isEmpty() && funcMap.containsKey(mKey)) {
						subMap = funcMap.get(mKey);
						funcMap.remove(mKey);
					}
					logger.info("FileUploadController: getFuncMap(): subMap:" + subMap);
					subMap.put(key, funcId + ":" + funcRoutine);
					logger.info("FileUploadController: getFuncMap(): subMap string:" + subMap.toString());
					funcMap.put(mKey, subMap);
				}
			}
		}
		logger.info("FileUploadController: getFuncMap(): funcMap:" + funcMap.toString());
		return funcMap;
	}
	

	private Map<String, String> getFuncRules(String client) throws Exception {

		logger.info("FileUploadController: Inside getFuncRules()");
		FieldSet fieldSet = oDataDelegate.getFuncRules(client);

		Map<String, String> funcRules = new HashMap<String, String>();
		if (!fieldSet.getD().getResults().isEmpty()) {
			for (Result result : fieldSet.getD().getResults()) {
				String key = result.getFUNCTIONID() + ":" + result.getfUNCTIONROUTINEID();
				String value = result.getcLASS() + ":" + result.getmETHOD();
				funcRules.put(key, value);
			}
		}
		logger.info("FileUploadController: getFuncMap(): funcRules:" + funcRules.toString());
		return funcRules;

	}

	private Map<String, String> getValueMap(String client) throws Exception {

		logger.info("FileUploadController: Inside getValueMap()");

		Map<String, String> valueMap = new HashMap<String, String>();
		FieldSet fieldSet = oDataDelegate.getValueMap(client);

		if (!fieldSet.getD().getResults().isEmpty()) {
			for (Result result : fieldSet.getD().getResults()) {
//				DataResultBean bindingObj = new DataResultBean();
				String key = null;
				String valMapping = result.getVALUEMAPPING();
				String sourceVal = result.getSourceVal();
				String targetVal = result.getTargetVal();
				logger.info("FileUploadController: getValueMap: Value Mapping:" + valMapping);
				logger.info("FileUploadController: getValueMap: sourceVal:" + sourceVal);
				logger.info("FileUploadController: getValueMap: targetVal:" + targetVal);
				key = client.concat(valMapping).concat(sourceVal);
				logger.info("FileUploadController: getValueMap: key:" + key);
				valueMap.put(key, targetVal);

			}
		}
		logger.info("FileUploadController: getValueMap: valueMap:" + valueMap.toString());
		return valueMap;
	}

	private List<List<MetaDataObj>> readFileData(InputStream file) {

		logger.info("FileUploadController: inside readFileData()");
		List<List<MetaDataObj>> rowList = new ArrayList<List<MetaDataObj>>();

		List<MetaDataObj> columnList = null;
		List<MetaDataObj> headerList = null;

		MetaDataObj metaDataObj = null;
		try {

			// TODO Auto-generated method stub
//			FileInputStream file = new FileInputStream(new File("C:\\Users\\Dhinesh\\Downloads\\Department.xlsx"));

			// Create Workbook instance holding reference to .xlsx file
			//Set TimeZone to Singapore - Senthil
			
			 TimeZone zone = TimeZone.getTimeZone("Asia/Singapore");
		     TimeZone.setDefault(zone);
			
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			workbook.setMissingCellPolicy(MissingCellPolicy.RETURN_BLANK_AS_NULL);

			// Get first/desired sheet from the workbook
			XSSFSheet sheet = workbook.getSheetAt(0);

			// Iterate through each rows one by one
//			Iterator<Row> rowIterator = sheet.iterator();
			Iterator<Row> rowIterator = sheet.rowIterator();
			int rowCount = 1;
			int totalColumnSize = this.columnSize;
//			int columnCount;
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				// For each row, iterate through all the columns
//				Iterator<Cell> cellIterator = row.cellIterator();
				Iterator<Cell> cellIterator = row.iterator();

				if (rowCount > this.valIndex - 1) {
					columnList = new ArrayList<MetaDataObj>();
				} else {
					headerList = new ArrayList<MetaDataObj>();
				}

				// ======================
//				columnCount = 1;
				for (int cn = 0; cn < row.getLastCellNum(); cn++) {
					if (cn >= totalColumnSize) {
						break;
					}
					metaDataObj = new MetaDataObj();
					Cell cell = row.getCell(cn);

					if (cell == null) {
						// This cell is empty/blank/un-used, handle as needed
//						System.out.println("cellStr null : ");
					} else {

						String cellStr = "";
						switch (cell.getCellType()) {
						case STRING:
							cellStr = cell.getStringCellValue();

							if (rowCount > this.valIndex - 1) {
								String headerName = getHeaderName(rowList, cn);
//								System.out.println("headerName : " + headerName);
//								System.out.println("cellStr : " + cellStr);
								metaDataObj.setFieldValue(cellStr);
								metaDataObj.setFieldName(headerName);
							} else {
								metaDataObj.setFieldValue(cellStr);
								metaDataObj.setFieldName("");
							}
							metaDataObj.setFieldType("STRING");
//							metaDataObj.setFieldName("");

							break;
						case NUMERIC:
							if (HSSFDateUtil.isCellDateFormatted(row.getCell(cn))) {
//								System.out.println(
//										"Row No.: " + row.getRowNum() + " " + row.getCell(cn).getDateCellValue());
// Senthil							
								
								
								Date dateVal = cell.getDateCellValue();
								
							
								
								String pattern = "MM/dd/yyyy";
		                     	SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		                     	String dateC = simpleDateFormat.format(dateVal);
		                     	
		                    //	cellStr = String.valueOf(cell.getLocalDateTimeCellValue());
		                    	cellStr = dateC;
		                    	metaDataObj.setFieldType("Date");
		                     	
//Senthil 
//								System.out.println("cellStr: " + cellStr);
							} else {
//								System.out.println("cellStrNNN : " + cellStr);
								cellStr = String.valueOf(cell.getNumericCellValue());
								metaDataObj.setFieldType("NUMERIC");
								DataFormatter df = new DataFormatter();
								cellStr = df.formatCellValue(cell);
							}
							if (rowCount > this.valIndex - 1) {
								String headerName = getHeaderName(rowList, cn);
//								System.out.println("headerNameN : " + headerName);
//								System.out.println("cellStrN : " + cellStr);
								metaDataObj.setFieldValue(cellStr);
								metaDataObj.setFieldName(headerName);
							} else {
								metaDataObj.setFieldValue(cellStr);
								metaDataObj.setFieldName("");
							}
							break;
						case BLANK:
							cellStr = String.valueOf(cell.getStringCellValue());
							if (rowCount > this.valIndex - 1) {
								String headerName = getHeaderName(rowList, cn);
								metaDataObj.setFieldValue(cellStr);
								metaDataObj.setFieldName(headerName);
							} else {
								metaDataObj.setFieldValue(cellStr);
								metaDataObj.setFieldName("");
							}
							metaDataObj.setFieldType("STRING");

							break;
						}

						// Do something with the value
					}

					if (rowCount > this.valIndex - 1) {
						columnList.add(metaDataObj);
					} else {
						headerList.add(metaDataObj);
					}
//					columnCount++;
				}
				if (rowCount > this.valIndex - 1) {
					rowList.add(columnList);
				} else {
					rowList.add(headerList);
				}
				rowCount++;
			}
			file.close();
		} catch (

		Exception e) {
			e.printStackTrace();
		}

//		System.out.println("Finaly obj result :" + rowList.toString());
		return rowList;
	}

	private String getHeaderName(List<List<MetaDataObj>> rowList, int cn) {
		// TODO Auto-generated method stub
		System.out.println("rowList Size:" + rowList.size());
		return rowList.get(this.headIndex-1).get(cn).getFieldValue();
	}
	
	public  Map<String,Map> getInitFunc(Map<String, String> funcRules,String template,String company,
			Map<String, String> clientSystem,String isTestRun) throws NoSuchMethodException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		Map<String,Map> ValueMap=null;
		Map<String,Object> ObjectsMap=null;
		

		

		if (initfuncMap.containsKey(template)) {
		Map<String, String> initMap = new HashMap<String, String>();
		
		initMap = initfuncMap.get(template);
		if (!initMap.isEmpty()) {
			Iterator<Map.Entry<String, String>> itr = initMap.entrySet().iterator(); 
			while (itr.hasNext()) {
				Map.Entry<String, String> entry = itr.next();
				String ruleKey = entry.getValue();
//				logger.info("CreateExcelView: writeExcel: ruleKey:" + ruleKey);
				if (funcRules.containsKey(ruleKey)) {
					String funcRule = funcRules.get(ruleKey);
					String rule[] = funcRule.split(":");
					String className = rule[0];
					String methodName = rule[1];
					Method meth = Class.forName(utilPath + className).getMethod(methodName,
							List.class, int.class, String.class, Map.class,String.class);
					Object utilObject = Class.forName(utilPath + className).newInstance();
					ValueMap = (Map<String,Map>) meth.invoke(utilObject, template, company,
							clientSystem,isTestRun);
				}
			}
		}
		}
//		initfuncMap
//		Method meth = Class.forName(utilPath + className).getMethod(methodName,
//				List.class, int.class, String.class, Map.class,String.class);
//		Object utilObject = Class.forName(utilPath + className).newInstance();
//		oldValue = (String) meth.invoke(utilObject, rowData, colIndex, company,
//				clientSystem,isTestRun);
		
		return ValueMap;
		
		

	}

}
