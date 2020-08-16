package com.capt.dm.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TimeZone;
import java.util.function.Predicate;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.olingo.odata2.api.edm.Edm;
import org.apache.olingo.odata2.api.ep.entry.ODataEntry;
import org.apache.olingo.odata2.api.ep.feed.ODataFeed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.web.client.RestTemplate;

import com.capt.dm.model.FieldSet;
import com.capt.dm.model.MetaDataObj;
import com.capt.dm.upsert.objects.Metadata;
import com.capt.dm.upsert.objects.UpsertObject;
import com.shd.Util.Utility;
import com.shd.keymap.D;
import com.shd.keymap.KeyMap;
import com.shd.keymap.Result;

public class FOUtility {

	private static final Logger logger = LoggerFactory.getLogger(FOUtility.class);

	public String getSplitRight(List<MetaDataObj> rowData, int index, String company, Map<String, String> clientSystem,
			String isTestRun, Map<String, Map> initVal) throws Exception {

//		logger.info("FOUtility: Inside getSplitRight Method");
//		String newValue = oldValue.substring(oldValue.lastIndexOf("-")+1, oldValue.length());
		String legacyValue = ((MetaDataObj) rowData.get(index)).getFieldValue();
//		logger.info("FOUtility: getSplitRight: legacyValue:"+legacyValue+"::"+index);
		String newValue = "";

		if (null != legacyValue && !legacyValue.isEmpty()) {

			// Senthil
			if (!(legacyValue.contains("-"))) {
				return legacyValue;

			}
			// Senthil

			newValue = legacyValue.substring(legacyValue.lastIndexOf("-") + 1, legacyValue.length()).trim();
		}

//		logger.info("FOUtility: getSplitRight: newValue:" + newValue);
//		getSplitLeft(rowData, index, clientSystem);
		return newValue;
	}

	public String getSplitLeft(List<MetaDataObj> rowData, int index, String company, Map<String, String> clientSystem,
			String isTestRun, Map<String, Map> initVal) throws Exception {

//		logger.info("FOUtility: Inside getSplitRight Method");
//		String newValue = oldValue.substring(oldValue.lastIndexOf("-")+1, oldValue.length());
		String legacyValue = ((MetaDataObj) rowData.get(index)).getFieldValue();
//		logger.info("FOUtility: getSplitRight: legacyValue:"+legacyValue);
		String newValue = "";

		if (null != legacyValue && !legacyValue.isEmpty()) {

			// Senthil
			if (!(legacyValue.contains("-"))) {
				return legacyValue;

			}
			// Senthil

			newValue = legacyValue.substring(0, legacyValue.lastIndexOf("-")).trim();
		}
//		logger.info("FOUtility: getSplitLeft: newValue:" + newValue);
		return newValue;
	}

	public String getDepartmentID(List<MetaDataObj> rowData, int index, String company,
			Map<String, String> clientSystem, String isTestRun, Map<String, Map> initVal) throws Exception {

		String effectiveStartDate = null;
		for (MetaDataObj metaDataObj : rowData) {

			if (null != metaDataObj.getFieldName()
					&& "effectiveStartDate".equalsIgnoreCase(metaDataObj.getFieldName())) {
				effectiveStartDate = metaDataObj.getFieldValue();
			}
		}

		String epochDate = getEpoch(effectiveStartDate);

		Calendar calendar = Calendar.getInstance();
		calendar.setTimeZone(TimeZone.getTimeZone("Asia/Singapore"));
		String excode = calendar.getTimeInMillis() + getRandomString(6);
		String externalCode = excode;
		String externalName = excode;
		String legacyValue = ((MetaDataObj) rowData.get(index)).getFieldValue();
		String newValue = null;

		if ((isTestRun.equalsIgnoreCase("No") || (isTestRun.equalsIgnoreCase("NoSim")))) {

			String url = clientSystem.get("URL");
			String userID = clientSystem.get("USER_ID");
			String password = clientSystem.get("PWD");

			Metadata metaData = new Metadata();
			metaData.setUri(url + "/cust_Keymapping");
			metaData.setType("SFOData.cust_Keymapping");

			UpsertObject upsertObject = new UpsertObject();
			upsertObject.setMetadata(metaData);
			upsertObject.setExternalCode(externalCode);

			upsertObject.setEffectiveStartDate(epochDate);
			upsertObject.setCustLegacyID(legacyValue);
			upsertObject.setExternalName(externalName);
			upsertObject.setCustCompany(company);
			upsertObject.setCustSFID("");

			upsertObject.setCustObjectType("01");

			if (((isTestRun.equalsIgnoreCase("NoSim")))) {

				upsertObject.setCust_sim("Y");

			} else {
				upsertObject.setCust_sim("N");

			}

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			String upsertURL = url + "/upsert";

			String fetchURL = url + "/cust_Keymapping?$filter=externalCode+eq+'" + externalCode + "'";

			RestTemplate restTemplate = new RestTemplate();

			restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(userID, password));

			HttpEntity<UpsertObject> entity = new HttpEntity<UpsertObject>(upsertObject, headers);

			String custSFID = null;
			boolean isEmpty = true;
			do {
				// Step-1: Post the values to get generate new department Id
				String result = restTemplate.postForObject(upsertURL, upsertObject, String.class);

				logger.info("FOUtility: getDepartmentID Method: Upsert result:" + result);

				// Step-2: Get new department Id using external code
				FieldSet fetchResult = restTemplate.getForObject(fetchURL, FieldSet.class);
//			System.out.println("fetchResult : " + fetchResult.getD().getResults().get(0).getCustSFID());

				if (null != fetchResult.getD().getResults() && !fetchResult.getD().getResults().isEmpty()) {
					custSFID = fetchResult.getD().getResults().get(0).getCustSFID();
				}

				// Step-3: Check if any values are present already for the newly generated id.
				String checkUrl = url + "/FODepartment?$format=JSON&$filter=externalCode+eq+'" + custSFID + "'";

				FieldSet checkObject = restTemplate.getForObject(checkUrl, FieldSet.class);

//			System.out.println("checkObject D : " + checkObject.getD().getResults().isEmpty());
				isEmpty = checkObject.getD().getResults().isEmpty();

			} while (!isEmpty);
			return custSFID;
		} else {
			return legacyValue;

		}

	}

	// Get job id
	public String getJobClassID(List<MetaDataObj> rowData, int index, String company, Map<String, String> clientSystem,
			String isTestRun, Map<String, Map> initVal) throws Exception {

//		logger.info("FOUtility: Inside getDepartmentID Method");
		String effectiveStartDate = null;

		for (MetaDataObj metaDataObj : rowData) {
//			logger.info("FOUtility: Inside getDepartmentID Method: metaDataObj.getFieldName():"
//					+ metaDataObj.getFieldName());
			if (null != metaDataObj.getFieldName()
					&& "effectiveStartDate".equalsIgnoreCase(metaDataObj.getFieldName())) {
				effectiveStartDate = metaDataObj.getFieldValue();
			}
		}

//		logger.info("FOUtility: getDepartmentID Method: effectiveStartDate:" + effectiveStartDate);

		String epochDate = getEpoch(effectiveStartDate);

//		logger.info("FOUtility: getDepartmentID Method: epochDate:" + epochDate);

		Calendar calendar = Calendar.getInstance();
		calendar.setTimeZone(TimeZone.getTimeZone("Asia/Singapore"));
		String excode = calendar.getTimeInMillis() + getRandomString(6);
		String externalCode = excode;
		String externalName = excode;
		String legacyValue = ((MetaDataObj) rowData.get(index)).getFieldValue();
		String newValue = null;

		if ((isTestRun.equalsIgnoreCase("No") || (isTestRun.equalsIgnoreCase("NoSim")))) {
//		String url = "https://api12preview.sapsf.eu/odata/v2/";
//		String url = "https://apisalesdemo2.successfactors.eu/odata/v2/";
			String url = clientSystem.get("URL");
			String userID = clientSystem.get("USER_ID");
			String password = clientSystem.get("PWD");

			Metadata metaData = new Metadata();
			metaData.setUri(url + "/cust_Keymapping");
			metaData.setType("SFOData.cust_Keymapping");

			UpsertObject upsertObject = new UpsertObject();
			upsertObject.setMetadata(metaData);
			upsertObject.setExternalCode(externalCode);
//		upsertObject.setEffectiveStartDate("/Date(946665000000)/");
			upsertObject.setEffectiveStartDate(epochDate);
			upsertObject.setCustLegacyID(legacyValue);
			upsertObject.setExternalName(externalName);
			upsertObject.setCustCompany(company);
			upsertObject.setCustSFID("");
			upsertObject.setCustObjectType("02");

			if (((isTestRun.equalsIgnoreCase("NoSim")))) {

				upsertObject.setCust_sim("Y");

			} else {
				upsertObject.setCust_sim("N");
			}

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
//		RestTemplate postTemplate = new RestTemplate();
//		postTemplate.getInterceptors().add(new BasicAuthorizationInterceptor("sfadmin@SFPART046830", "Welcome1"));

			String upsertURL = url + "/upsert";

			String fetchURL = url + "/cust_Keymapping?$filter=externalCode+eq+'" + externalCode + "'";

			RestTemplate restTemplate = new RestTemplate();
//		restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor("VKUMAR@shiseidocoT1", "Welcome@3"));
//		restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor("sfadmin@SFPART046830", "Welcome1"));
			restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(userID, password));

			HttpEntity<UpsertObject> entity = new HttpEntity<UpsertObject>(upsertObject, headers);

//		Map <String, String> dummy = new HashMap<String, String>();
			String custSFID = null;
			boolean isEmpty = true;
			do {
				// Step-1: Post the values to get generate new department Id
				String result = restTemplate.postForObject(upsertURL, upsertObject, String.class);

				logger.info("FOUtility: getJobclassID Method: Upsert result:" + result);

				// Step-2: Get new department Id using external code
				FieldSet fetchResult = restTemplate.getForObject(fetchURL, FieldSet.class);
//			System.out.println("fetchResult : " + fetchResult.getD().getResults().get(0).getCustSFID());

				if (null != fetchResult.getD().getResults() && !fetchResult.getD().getResults().isEmpty()) {
					custSFID = fetchResult.getD().getResults().get(0).getCustSFID();
				}

				// Step-3: Check if any values are present already for the newly generated id.
				String checkUrl = url + "/FOJobCode?$format=JSON&$filter=externalCode+eq+'" + custSFID + "'";

				FieldSet checkObject = restTemplate.getForObject(checkUrl, FieldSet.class);

//			System.out.println("checkObject D : " + checkObject.getD().getResults().isEmpty());
				isEmpty = checkObject.getD().getResults().isEmpty();

			} while (!isEmpty);

			return custSFID;
		} else {

			return legacyValue;
		}
	}

	// Get Pos id
	public String getPosID(List<MetaDataObj> rowData, int index, String company, Map<String, String> clientSystem,
			String isTestRun, Map<String, Map> initVal) throws Exception {

//		logger.info("FOUtility: Inside getDepartmentID Method");
		String effectiveStartDate = null;

		for (MetaDataObj metaDataObj : rowData) {
//			logger.info("FOUtility: Inside getDepartmentID Method: metaDataObj.getFieldName():"
//					+ metaDataObj.getFieldName());
			if (null != metaDataObj.getFieldName()
					&& "effectiveStartDate".equalsIgnoreCase(metaDataObj.getFieldName())) {
				effectiveStartDate = metaDataObj.getFieldValue();
			}
		}

//		logger.info("FOUtility: getDepartmentID Method: effectiveStartDate:" + effectiveStartDate);

		String epochDate = getEpoch(effectiveStartDate);

//		logger.info("FOUtility: getDepartmentID Method: epochDate:" + epochDate);
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeZone(TimeZone.getTimeZone("Asia/Singapore"));
		String excode = calendar.getTimeInMillis() + getRandomString(6);

		String externalCode = excode;
		String externalName = excode;
		String legacyValue = ((MetaDataObj) rowData.get(index)).getFieldValue();
		String newValue = null;

		if ((isTestRun.equalsIgnoreCase("No") || (isTestRun.equalsIgnoreCase("NoSim")))) {
//		String url = "https://api12preview.sapsf.eu/odata/v2/";
//		String url = "https://apisalesdemo2.successfactors.eu/odata/v2/";
			String url = clientSystem.get("URL");
			String userID = clientSystem.get("USER_ID");
			String password = clientSystem.get("PWD");

			Metadata metaData = new Metadata();
			metaData.setUri(url + "/cust_Keymapping");
			metaData.setType("SFOData.cust_Keymapping");

			UpsertObject upsertObject = new UpsertObject();
			upsertObject.setMetadata(metaData);
			upsertObject.setExternalCode(externalCode);
//		upsertObject.setEffectiveStartDate("/Date(946665000000)/");
			upsertObject.setEffectiveStartDate(epochDate);
			upsertObject.setCustLegacyID(legacyValue);
			upsertObject.setExternalName(externalName);
			upsertObject.setCustCompany(company);
			upsertObject.setCustSFID("");
			upsertObject.setCustObjectType("09");

			if (((isTestRun.equalsIgnoreCase("NoSim")))) {

				upsertObject.setCust_sim("Y");

			} else {
				upsertObject.setCust_sim("N");
			}

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
//		RestTemplate postTemplate = new RestTemplate();
//		postTemplate.getInterceptors().add(new BasicAuthorizationInterceptor("sfadmin@SFPART046830", "Welcome1"));

			String upsertURL = url + "/upsert";

			String fetchURL = url + "/cust_Keymapping?$filter=externalCode+eq+'" + externalCode + "'";

			RestTemplate restTemplate = new RestTemplate();
//		restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor("VKUMAR@shiseidocoT1", "Welcome@3"));
//		restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor("sfadmin@SFPART046830", "Welcome1"));
			restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(userID, password));

			HttpEntity<UpsertObject> entity = new HttpEntity<UpsertObject>(upsertObject, headers);

//		Map <String, String> dummy = new HashMap<String, String>();
			String custSFID = null;
			boolean isEmpty = true;
			do {
				// Step-1: Post the values to get generate new department Id
				String result = restTemplate.postForObject(upsertURL, upsertObject, String.class);

				logger.info("FOUtility: getPosclassID Method: Upsert result:" + result);

				// Step-2: Get new department Id using external code
				FieldSet fetchResult = restTemplate.getForObject(fetchURL, FieldSet.class);
//			System.out.println("fetchResult : " + fetchResult.getD().getResults().get(0).getCustSFID());

				if (null != fetchResult.getD().getResults() && !fetchResult.getD().getResults().isEmpty()) {
					custSFID = fetchResult.getD().getResults().get(0).getCustSFID();
				}

				// Step-3: Check if any values are present already for the newly generated id.
				String checkUrl = url + "/Position?$format=JSON&$filter=code+eq+'" + custSFID + "'";

				FieldSet checkObject = restTemplate.getForObject(checkUrl, FieldSet.class);

//			System.out.println("checkObject D : " + checkObject.getD().getResults().isEmpty());
				isEmpty = checkObject.getD().getResults().isEmpty();

			} while (!isEmpty);

			return custSFID;
		} else {

			return legacyValue;
		}
	}
// PayGrade

	// Get Grade id
	public String getCreateGradeID(List<MetaDataObj> rowData, int index, String company,
			Map<String, String> clientSystem, String isTestRun, Map<String, Map> initVal) throws Exception {

//			logger.info("FOUtility: Inside getDepartmentID Method");
		String effectiveStartDate = null;

		for (MetaDataObj metaDataObj : rowData) {
//				logger.info("FOUtility: Inside getDepartmentID Method: metaDataObj.getFieldName():"
//						+ metaDataObj.getFieldName());
			if (null != metaDataObj.getFieldName() && "start-date".equalsIgnoreCase(metaDataObj.getFieldName())) {
				effectiveStartDate = metaDataObj.getFieldValue();
			}
		}

//			logger.info("FOUtility: getDepartmentID Method: effectiveStartDate:" + effectiveStartDate);

		String epochDate = getEpoch(effectiveStartDate);

//			logger.info("FOUtility: getDepartmentID Method: epochDate:" + epochDate);
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeZone(TimeZone.getTimeZone("Asia/Singapore"));
		String excode = calendar.getTimeInMillis() + getRandomString(6);

		String externalCode = excode;
		String externalName = excode;
		String legacyValue = ((MetaDataObj) rowData.get(index)).getFieldValue();
		String newValue = null;

		if ((isTestRun.equalsIgnoreCase("No") || (isTestRun.equalsIgnoreCase("NoSim")))) {
//			String url = "https://api12preview.sapsf.eu/odata/v2/";
//			String url = "https://apisalesdemo2.successfactors.eu/odata/v2/";
			String url = clientSystem.get("URL");
			String userID = clientSystem.get("USER_ID");
			String password = clientSystem.get("PWD");

			Metadata metaData = new Metadata();
			metaData.setUri(url + "/cust_Keymapping");
			metaData.setType("SFOData.cust_Keymapping");

			UpsertObject upsertObject = new UpsertObject();
			upsertObject.setMetadata(metaData);
			upsertObject.setExternalCode(externalCode);
//			upsertObject.setEffectiveStartDate("/Date(946665000000)/");
			upsertObject.setEffectiveStartDate(epochDate);
			upsertObject.setCustLegacyID(legacyValue);
			upsertObject.setExternalName(externalName);
			upsertObject.setCustCompany(company);
			upsertObject.setCustSFID("");
			upsertObject.setCustObjectType("06");

			if (((isTestRun.equalsIgnoreCase("NoSim")))) {

				upsertObject.setCust_sim("Y");

			} else {
				upsertObject.setCust_sim("N");
			}

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
//			RestTemplate postTemplate = new RestTemplate();
//			postTemplate.getInterceptors().add(new BasicAuthorizationInterceptor("sfadmin@SFPART046830", "Welcome1"));

			String upsertURL = url + "/upsert";

			String fetchURL = url + "/cust_Keymapping?$filter=externalCode+eq+'" + externalCode + "'";

			RestTemplate restTemplate = new RestTemplate();
//			restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor("VKUMAR@shiseidocoT1", "Welcome@3"));
//			restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor("sfadmin@SFPART046830", "Welcome1"));
			restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(userID, password));

			HttpEntity<UpsertObject> entity = new HttpEntity<UpsertObject>(upsertObject, headers);

//			Map <String, String> dummy = new HashMap<String, String>();
			String custSFID = null;
			boolean isEmpty = true;
			do {
				// Step-1: Post the values to get generate new department Id
				String result = restTemplate.postForObject(upsertURL, upsertObject, String.class);

				logger.info("FOUtility: getPosclassID Method: Upsert result:" + result);

				// Step-2: Get new department Id using external code
				FieldSet fetchResult = restTemplate.getForObject(fetchURL, FieldSet.class);
//				System.out.println("fetchResult : " + fetchResult.getD().getResults().get(0).getCustSFID());

				if (null != fetchResult.getD().getResults() && !fetchResult.getD().getResults().isEmpty()) {
					custSFID = fetchResult.getD().getResults().get(0).getCustSFID();
				}

				// Step-3: Check if any values are present already for the newly generated id.
				String checkUrl = url + "/FOPayGrade?$format=JSON&$filter=externalCode+eq+'" + custSFID + "'";

				FieldSet checkObject = restTemplate.getForObject(checkUrl, FieldSet.class);

//				System.out.println("checkObject D : " + checkObject.getD().getResults().isEmpty());
				isEmpty = checkObject.getD().getResults().isEmpty();

			} while (!isEmpty);

			return custSFID;
		} else {

			return legacyValue;
		}
	}

	// PayGrade

	// Create PayRange ID
	public String getCreatePayRangeID(List<MetaDataObj> rowData, int index, String company,
			Map<String, String> clientSystem, String isTestRun, Map<String, Map> initVal) throws Exception {

//				logger.info("FOUtility: Inside getDepartmentID Method");
		String effectiveStartDate = null;

		for (MetaDataObj metaDataObj : rowData) {
//					logger.info("FOUtility: Inside getDepartmentID Method: metaDataObj.getFieldName():"
//							+ metaDataObj.getFieldName());
			if (null != metaDataObj.getFieldName() && "start-date".equalsIgnoreCase(metaDataObj.getFieldName())) {
				effectiveStartDate = metaDataObj.getFieldValue();
			}
		}

//				logger.info("FOUtility: getDepartmentID Method: effectiveStartDate:" + effectiveStartDate);

		String epochDate = getEpoch(effectiveStartDate);

//				logger.info("FOUtility: getDepartmentID Method: epochDate:" + epochDate);
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeZone(TimeZone.getTimeZone("Asia/Singapore"));
		String excode = calendar.getTimeInMillis() + getRandomString(6);

		String externalCode = excode;
		String externalName = excode;
		String legacyValue = ((MetaDataObj) rowData.get(index)).getFieldValue();
		String newValue = null;

		if ((isTestRun.equalsIgnoreCase("No") || (isTestRun.equalsIgnoreCase("NoSim")))) {
//				String url = "https://api12preview.sapsf.eu/odata/v2/";
//				String url = "https://apisalesdemo2.successfactors.eu/odata/v2/";
			String url = clientSystem.get("URL");
			String userID = clientSystem.get("USER_ID");
			String password = clientSystem.get("PWD");

			Metadata metaData = new Metadata();
			metaData.setUri(url + "/cust_Keymapping");
			metaData.setType("SFOData.cust_Keymapping");

			UpsertObject upsertObject = new UpsertObject();
			upsertObject.setMetadata(metaData);
			upsertObject.setExternalCode(externalCode);
//				upsertObject.setEffectiveStartDate("/Date(946665000000)/");
			upsertObject.setEffectiveStartDate(epochDate);
			upsertObject.setCustLegacyID(legacyValue);
			upsertObject.setExternalName(externalName);
			upsertObject.setCustCompany(company);
			upsertObject.setCustSFID("");
			upsertObject.setCustObjectType("08");

			if (((isTestRun.equalsIgnoreCase("NoSim")))) {

				upsertObject.setCust_sim("Y");

			} else {
				upsertObject.setCust_sim("N");
			}

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
//				RestTemplate postTemplate = new RestTemplate();
//				postTemplate.getInterceptors().add(new BasicAuthorizationInterceptor("sfadmin@SFPART046830", "Welcome1"));

			String upsertURL = url + "/upsert";

			String fetchURL = url + "/cust_Keymapping?$filter=externalCode+eq+'" + externalCode + "'";

			RestTemplate restTemplate = new RestTemplate();
//				restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor("VKUMAR@shiseidocoT1", "Welcome@3"));
//				restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor("sfadmin@SFPART046830", "Welcome1"));
			restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(userID, password));

			HttpEntity<UpsertObject> entity = new HttpEntity<UpsertObject>(upsertObject, headers);

//				Map <String, String> dummy = new HashMap<String, String>();
			String custSFID = null;
			boolean isEmpty = true;
			do {
				// Step-1: Post the values to get generate new department Id
				String result = restTemplate.postForObject(upsertURL, upsertObject, String.class);

				logger.info("FOUtility: getPosclassID Method: Upsert result:" + result);

				// Step-2: Get new department Id using external code
				FieldSet fetchResult = restTemplate.getForObject(fetchURL, FieldSet.class);
//					System.out.println("fetchResult : " + fetchResult.getD().getResults().get(0).getCustSFID());

				if (null != fetchResult.getD().getResults() && !fetchResult.getD().getResults().isEmpty()) {
					custSFID = fetchResult.getD().getResults().get(0).getCustSFID();
				}

				// Step-3: Check if any values are present already for the newly generated id.
				String checkUrl = url + "/FOPayRange?$format=JSON&$filter=externalCode+eq+'" + custSFID + "'";

				FieldSet checkObject = restTemplate.getForObject(checkUrl, FieldSet.class);

//					System.out.println("checkObject D : " + checkObject.getD().getResults().isEmpty());
				isEmpty = checkObject.getD().getResults().isEmpty();

			} while (!isEmpty);

			return custSFID;
		} else {

			return legacyValue;
		}
	}

	// Create Location id
	public String getCreateLocationID(List<MetaDataObj> rowData, int index, String company,
			Map<String, String> clientSystem, String isTestRun, Map<String, Map> initVal) throws Exception {

//			logger.info("FOUtility: Inside getDepartmentID Method");
		String effectiveStartDate = null;

		for (MetaDataObj metaDataObj : rowData) {
//				logger.info("FOUtility: Inside getDepartmentID Method: metaDataObj.getFieldName():"
//						+ metaDataObj.getFieldName());
			if (null != metaDataObj.getFieldName() && "start-date".equalsIgnoreCase(metaDataObj.getFieldName())) {
				effectiveStartDate = metaDataObj.getFieldValue();
			}
		}

//			logger.info("FOUtility: getDepartmentID Method: effectiveStartDate:" + effectiveStartDate);

		String epochDate = getEpoch(effectiveStartDate);

//			logger.info("FOUtility: getDepartmentID Method: epochDate:" + epochDate);

		Calendar calendar = Calendar.getInstance();
		calendar.setTimeZone(TimeZone.getTimeZone("Asia/Singapore"));
		String excode = calendar.getTimeInMillis() + getRandomString(6);

		String externalCode = excode;
		String externalName = excode;
		String legacyValue = ((MetaDataObj) rowData.get(index)).getFieldValue();
		String newValue = null;

		if ((isTestRun.equalsIgnoreCase("No") || (isTestRun.equalsIgnoreCase("NoSim")))) {
//			String url = "https://api12preview.sapsf.eu/odata/v2/";
//			String url = "https://apisalesdemo2.successfactors.eu/odata/v2/";
			String url = clientSystem.get("URL");
			String userID = clientSystem.get("USER_ID");
			String password = clientSystem.get("PWD");

			Metadata metaData = new Metadata();
			metaData.setUri(url + "/cust_Keymapping");
			metaData.setType("SFOData.cust_Keymapping");

			UpsertObject upsertObject = new UpsertObject();
			upsertObject.setMetadata(metaData);
			upsertObject.setExternalCode(externalCode);
//			upsertObject.setEffectiveStartDate("/Date(946665000000)/");
			upsertObject.setEffectiveStartDate(epochDate);
			upsertObject.setCustLegacyID(legacyValue);
			upsertObject.setExternalName(externalName);
			upsertObject.setCustCompany(company);
			upsertObject.setCustSFID("");
			upsertObject.setCustObjectType("03");

			if (((isTestRun.equalsIgnoreCase("NoSim")))) {

				upsertObject.setCust_sim("Y");

			} else {
				upsertObject.setCust_sim("N");
			}

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
//			RestTemplate postTemplate = new RestTemplate();
//			postTemplate.getInterceptors().add(new BasicAuthorizationInterceptor("sfadmin@SFPART046830", "Welcome1"));

			String upsertURL = url + "/upsert";

			String fetchURL = url + "/cust_Keymapping?$filter=externalCode+eq+'" + externalCode + "'";

			RestTemplate restTemplate = new RestTemplate();
//			restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor("VKUMAR@shiseidocoT1", "Welcome@3"));
//			restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor("sfadmin@SFPART046830", "Welcome1"));
			restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(userID, password));

			HttpEntity<UpsertObject> entity = new HttpEntity<UpsertObject>(upsertObject, headers);

//			Map <String, String> dummy = new HashMap<String, String>();
			String custSFID = null;
			boolean isEmpty = true;
			do {
				// Step-1: Post the values to get generate new department Id
				String result = restTemplate.postForObject(upsertURL, upsertObject, String.class);

				logger.info("FOUtility: getPosclassID Method: Upsert result:" + result);

				// Step-2: Get new department Id using external code
				FieldSet fetchResult = restTemplate.getForObject(fetchURL, FieldSet.class);
//				System.out.println("fetchResult : " + fetchResult.getD().getResults().get(0).getCustSFID());

				if (null != fetchResult.getD().getResults() && !fetchResult.getD().getResults().isEmpty()) {
					custSFID = fetchResult.getD().getResults().get(0).getCustSFID();
				}

				// Step-3: Check if any values are present already for the newly generated id.
				String checkUrl = url + "/FOLocation?$format=JSON&$filter=externalCode+eq+'" + custSFID + "'";

				FieldSet checkObject = restTemplate.getForObject(checkUrl, FieldSet.class);

//				System.out.println("checkObject D : " + checkObject.getD().getResults().isEmpty());
				isEmpty = checkObject.getD().getResults().isEmpty();

			} while (!isEmpty);

			return custSFID;
		} else {

			return legacyValue;
		}
	}

	private String getRandomString(int length) {

		boolean useLetters = true;
		boolean useNumbers = true;
		String generatedString = RandomStringUtils.random(length, useLetters, useNumbers);
		return generatedString;
	}

	private String getEpoch(String strDate) {

//		DateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy");
		String epociInputDate = strDate.substring(6, 10) + "-" + strDate.substring(0, 2) + "-" + strDate.substring(3, 5)
				+ 'T' + "00:00";

		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
		formatter.setTimeZone(TimeZone.getTimeZone("SGT"));

		String formatedDate = null;
		Date today = null;
		try {
			today = (Date) formatter.parse(epociInputDate);

			// getTime() returns the number of milliseconds since January 1, 1970, 00:00:00
			// GMT represented by this Date object.
			long epochTime = today.getTime();
			formatedDate = "/Date(" + String.valueOf(epochTime) + ")/";

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return formatedDate;
	}

	/// *****************Default email *******************///

	public String getDefaultEmail(List<MetaDataObj> rowData, int index, String company,
			Map<String, String> clientSystem, String isTestRun, Map<String, Map> initVal) throws Exception {
		String legacyValue = ((MetaDataObj) rowData.get(index)).getFieldValue();
		String newValue = "";
		if (null != legacyValue && !legacyValue.isEmpty()) {
			newValue = "DummyEmail@sap.com";
		}
		return newValue;
	}

	/// *****************Default Date of Birth *******************///

	public String getScrambleDOB(List<MetaDataObj> rowData, int index, String company, Map<String, String> clientSystem,
			String isTestRun, Map<String, Map> initVal)

			throws Exception {

		String strDate = ((MetaDataObj) rowData.get(index)).getFieldValue();

		String newValue = "";

		if (null != strDate && !strDate.isEmpty()) {
			Date start = null;
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			sdf.setLenient(false);
			try {

				start = sdf.parse(strDate);

			} catch (Exception e) {
				return (strDate + "<-- Invalid date");

			}

			Calendar cal = Calendar.getInstance();

			cal.setTime(start);

			int startVal = cal.get(Calendar.YEAR);

			int endVal = startVal + 10;

			GregorianCalendar gc = new GregorianCalendar();

			int year = randBetween(startVal, endVal);

			gc.set(gc.YEAR, year);

			int dayOfYear = randBetween(1, gc.getActualMaximum(gc.DAY_OF_YEAR));

			gc.set(gc.DAY_OF_YEAR, dayOfYear);

			int date = randBetween(1, 28);

			if (date == 0) {

				date = date + 2;
			}

			gc.set(gc.DAY_OF_MONTH, date);

			int mon = randBetween(1, 11);
			logger.info("Month" + mon);
			if (mon == 0) {

				mon = mon + 2;
			}

			gc.set(gc.MONTH, mon);

			cal.setTime(start);

			newValue = (String.format("%02d", gc.get(gc.MONTH))) + "/"

					+ String.format("%02d", gc.get(gc.DAY_OF_MONTH)) + "/" + gc.get(gc.YEAR) + "";

		}

		return newValue;

	}

	public static int randBetween(int start, int end) {
		return start + (int) Math.round(Math.random() * (end - start));
	}

	/// *****************Scramble amount / Percentage *******************///
	public String getScrambleAmount(List<MetaDataObj> rowData, int index, String company,
			Map<String, String> clientSystem, String isTestRun, Map<String, Map> initVal) throws Exception {

		String Actualamount = ((MetaDataObj) rowData.get(index)).getFieldValue();
		float Actualamountnew;

		if (null != Actualamount && !Actualamount.isEmpty()) {
			Actualamountnew = Float.parseFloat(Actualamount);

			if (!(Actualamountnew == 0)) {

				if (randBetween(1, 2) == 1) {
					Actualamountnew = Actualamountnew + (randBetween(1, 50)) * Actualamountnew / 100;
				} else {
					Actualamountnew = Actualamountnew - (randBetween(1, 50)) * Actualamountnew / 100;
				}

			}
			return String.valueOf(Math.round(Actualamountnew));
		} else {
			return ("");
		}

	}

	/// *****************Default Sequence Number *******************///

	public String getDefaultSeqnr(List<MetaDataObj> rowData, int index, String company,
			Map<String, String> clientSystem) throws Exception {
		return ("01");

	}

	/// *****************Default Sequence Number *******************///

	public String getDepLevel1(List<MetaDataObj> rowData, int index, String company, Map<String, String> clientSystem,
			String isTestRun, Map<String, Map> initVal) throws Exception {

		String url = clientSystem.get("URL");
		String userID = clientSystem.get("USER_ID");
		String password = clientSystem.get("PWD");
		String LegacyValue = ((MetaDataObj) rowData.get(index)).getFieldValue();
		String usedFormat = "application/json";

		OlingoV2_1Main app = new OlingoV2_1Main();
		Edm edm = app.readEdmSf(url, userID, password);

		String query = url + "/cust_Keymapping?$filter=cust_Company+eq+'" + company
				+ "' and cust_ObjectType+eq+'09' and cust_LegacyID+eq+'" + LegacyValue + "'&$select=cust_SFID";

		ODataFeed Fed = app.readFeedSf(edm, query, usedFormat, "cust_Keymapping", userID, password);
		String SFValue = null;

		for (ODataEntry entry : Fed.getEntries()) {
			Map<String, Object> properties = entry.getProperties();
			Set<Entry<String, Object>> entries = properties.entrySet();

			for (Entry<String, Object> entry1 : entries) {
				// System.out.print (entry1.getKey()+":");
				if (entry1.getValue() instanceof Calendar) {
					Calendar cal = (Calendar) entry1.getValue();
					Object value = SimpleDateFormat.getInstance().format(cal.getTime());
					// System.out.println(value);
				} else {

					if (entry1.getValue() != null && entry1.getKey().equalsIgnoreCase("cust_SFID"))
						SFValue = entry1.getValue().toString();
				}
			}
			// System.out.println("-----------");
		}

		return SFValue;

	}

	public String CheckDate(List<MetaDataObj> rowData, int index, String company, Map<String, String> clientSystem,
			String isTestRun, Map<String, Map> initVal) throws Exception {

		String strDate = ((MetaDataObj) rowData.get(index)).getFieldValue();
		Date start = null;
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		sdf.setLenient(false);

		if (null != strDate && !strDate.isEmpty()) {

			try {

				start = sdf.parse(strDate);

			} catch (Exception e) {
				return (strDate + "<-- Invalid date");

			}

		}

//		Calendar cal = Calendar.getInstance();
//		cal.setTime(start);
//		TimeZone timeZone = TimeZone.getTimeZone("Asia/Singapore");
//		cal.setTimeZone(timeZone);
//		String value = cal.get(Calendar.DAY_OF_MONTH)+"/"+ cal.get(Calendar.DATE)+"/"+cal.get(Calendar.YEAR);

		return strDate;
	}

	public String defaultManager(List<MetaDataObj> rowData, int index, String company, Map<String, String> clientSystem,
			String isTestRun, Map<String, Map> initVal) throws Exception {

		String newValue = "NO_MANAGER";

		return newValue;
	}

	public String defaultHR(List<MetaDataObj> rowData, int index, String company, Map<String, String> clientSystem,
			String isTestRun, Map<String, Map> initVal) throws Exception {

		String newValue = "NO_HR";

		return newValue;
	}

	public String checkUserID(List<MetaDataObj> rowData, int index, String company, Map<String, String> clientSystem,
			String isTestRun, Map<String, Map> initVal) throws Exception {

		String strDate = ((MetaDataObj) rowData.get(index)).getFieldValue();

		String newValue = "";

		if (null != strDate && !strDate.isEmpty()) {
			Date start = null;
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			sdf.setLenient(false);
			try {
			} catch (Exception e) {

			}

		}
		return newValue;
	}

	public String getScrambleAccount(List<MetaDataObj> rowData, int index, String company,
			Map<String, String> clientSystem, String isTestRun, Map<String, Map> initVal) throws Exception {

		String AccountNo = ((MetaDataObj) rowData.get(index)).getFieldValue();

		String newValue = "";

		if (null != AccountNo && !AccountNo.isEmpty()) {

			long AccountNoInt = Long.valueOf(AccountNo);

			if (randBetween(1, 2) == 1) {
				AccountNoInt = AccountNoInt + (randBetween(1, 50)) + AccountNoInt;
			} else {
				AccountNoInt = AccountNoInt - (randBetween(1, 50)) + AccountNoInt;
			}

			newValue = Long.toString(AccountNoInt);
		}

		return newValue;
	}

	public String getDept(List<MetaDataObj> rowData, int index, String company, Map<String, String> clientSystem,
			String isTestRun, Map<String, Map> initVal) throws Exception {

		String cust_LegacyID = ((MetaDataObj) rowData.get(index)).getFieldValue();
		String ObjectType = "01";

		String newValue = "";

		String urlx = clientSystem.get("URL");
		String userID = clientSystem.get("USER_ID");
		String password = clientSystem.get("PWD");

		Metadata metaData = new Metadata();

		if (null != cust_LegacyID && !cust_LegacyID.isEmpty()) {

			RestTemplate restTemplate = new RestTemplate();

			String url = urlx + "/cust_Keymapping?$filter=cust_Company eq '" + company + "' and cust_ObjectType eq '"
					+ ObjectType + "' and cust_LegacyID eq '" + cust_LegacyID + "'&$select=cust_SFID";
			logger.info("url:" + url);
			System.out.println(url);

			restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(userID, password));

			KeyMap result = restTemplate.getForObject(url, KeyMap.class);

			if (result != null) {
				D d = result.getD();
				List<Result> res = d.getResults();
				for (Result result1 : res) {
					newValue = result1.getCustSFID();

				}

				if (newValue.isEmpty()) {

					RestTemplate restTemplate1 = new RestTemplate();

					String url1 = urlx + "/cust_Keymapping?$filter=cust_Company eq '" + company
							+ "' and cust_ObjectType eq '" + ObjectType + "' and cust_SFID eq '" + cust_LegacyID
							+ "'&$select=cust_SFID";
					logger.info("url:" + url1);
					System.out.println(url1);

					restTemplate1.getInterceptors().add(new BasicAuthorizationInterceptor(userID, password));

					KeyMap result1 = restTemplate1.getForObject(url1, KeyMap.class);

					if (result1 != null) {
						D d1 = result1.getD();
						List<Result> res1 = d1.getResults();
						for (Result result2 : res1) {
							newValue = result2.getCustSFID();

						}

					}

				}
			}

		}

		if (null != cust_LegacyID) {
			if (newValue.isEmpty()) {
				newValue = cust_LegacyID + "<-- Invalid Department";
			}
		}

		return newValue;
	}

	public String getLegacyDept(List<MetaDataObj> rowData, int index, String company, Map<String, String> clientSystem,
			String isTestRun, Map<String, Map> initVal) throws Exception {

		String SFDep = null;

		for (MetaDataObj metaDataObj : rowData) {

			if (null != metaDataObj.getFieldName() && "externalCode".equalsIgnoreCase(metaDataObj.getFieldName())) {
				SFDep = metaDataObj.getFieldValue();
			}
		}

		String cust_LegacyID = ((MetaDataObj) rowData.get(index)).getFieldValue();
		String ObjectType = "01";

		String newValue = "";

		String urlx = clientSystem.get("URL");
		String userID = clientSystem.get("USER_ID");
		String password = clientSystem.get("PWD");

		Metadata metaData = new Metadata();

		if (null != SFDep && !SFDep.isEmpty()) {

			RestTemplate restTemplate = new RestTemplate();

			String url = urlx + "/cust_Keymapping?$filter=cust_Company eq '" + company + "' and cust_ObjectType eq '"
					+ ObjectType + "' and cust_SFID eq '" + SFDep + "'&$select=cust_LegacyID";
			logger.info("url:" + url);
			System.out.println(url);

			restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(userID, password));

			KeyMap result = restTemplate.getForObject(url, KeyMap.class);

			if (result != null) {
				D d = result.getD();
				List<Result> res = d.getResults();
				for (Result result1 : res) {
					newValue = result1.getCust_LegacyID();

				}

				if (newValue.isEmpty()) {

					RestTemplate restTemplate1 = new RestTemplate();

					String url1 = urlx + "/cust_Keymapping?$filter=cust_Company eq '" + company
							+ "' and cust_ObjectType eq '" + ObjectType + "' and cust_SFID eq '" + cust_LegacyID
							+ "'&$select=cust_SFID";
					logger.info("url:" + url1);
					System.out.println(url1);

					restTemplate1.getInterceptors().add(new BasicAuthorizationInterceptor(userID, password));

					KeyMap result1 = restTemplate1.getForObject(url1, KeyMap.class);

					if (result1 != null) {
						D d1 = result1.getD();
						List<Result> res1 = d1.getResults();
						for (Result result2 : res1) {
							newValue = result2.getCustSFID();

						}

					}

				}

			}

		}
		if (null != cust_LegacyID) {
			if (newValue.isEmpty()) {
				newValue = cust_LegacyID;
			}

		}

		return newValue;
	}

	public String getDiv(List<MetaDataObj> rowData, int index, String company, Map<String, String> clientSystem,
			String isTestRun, Map<String, Map> initVal) throws Exception {

		String cust_LegacyID = ((MetaDataObj) rowData.get(index)).getFieldValue();
		String ObjectType = "11";

		String newValue = "";

		String urlx = clientSystem.get("URL");
		String userID = clientSystem.get("USER_ID");
		String password = clientSystem.get("PWD");

		Metadata metaData = new Metadata();

		if (null != cust_LegacyID && !cust_LegacyID.isEmpty()) {

			RestTemplate restTemplate = new RestTemplate();

			String url = urlx + "/cust_Keymapping?$filter=cust_Company eq '" + company + "' and cust_ObjectType eq '"
					+ ObjectType + "' and cust_LegacyID eq '" + cust_LegacyID + "'&$select=cust_SFID";
			logger.info("url:" + url);
			System.out.println(url);

			restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(userID, password));

			KeyMap result = restTemplate.getForObject(url, KeyMap.class);

			if (result != null) {
				D d = result.getD();
				List<Result> res = d.getResults();
				for (Result result1 : res) {
					newValue = result1.getCustSFID();

				}

				if (newValue.isEmpty()) {

					RestTemplate restTemplate1 = new RestTemplate();

					String url1 = urlx + "/cust_Keymapping?$filter=cust_Company eq '" + company
							+ "' and cust_ObjectType eq '" + ObjectType + "' and cust_SFID eq '" + cust_LegacyID
							+ "'&$select=cust_SFID";
					logger.info("url:" + url1);
					System.out.println(url1);

					restTemplate1.getInterceptors().add(new BasicAuthorizationInterceptor(userID, password));

					KeyMap result1 = restTemplate1.getForObject(url1, KeyMap.class);

					if (result1 != null) {
						D d1 = result1.getD();
						List<Result> res1 = d1.getResults();
						for (Result result2 : res1) {
							newValue = result2.getCustSFID();

						}

					}

				}

			}

		}

		if (null != cust_LegacyID) {
			if (newValue.isEmpty()) {
				newValue = cust_LegacyID + "<-- Invalid Division";
			}
		}

		return newValue;
	}
//// Get Job Classification

	public String getJobClassificationID(List<MetaDataObj> rowData, int index, String company,
			Map<String, String> clientSystem, String isTestRun, Map<String, Map> initVal) throws Exception {

		String cust_LegacyID = ((MetaDataObj) rowData.get(index)).getFieldValue();
		String ObjectType = "02";

		String newValue = "";

		String urlx = clientSystem.get("URL");
		String userID = clientSystem.get("USER_ID");
		String password = clientSystem.get("PWD");

		Metadata metaData = new Metadata();

		if (null != cust_LegacyID && !cust_LegacyID.isEmpty()) {

			RestTemplate restTemplate = new RestTemplate();

			String url = urlx + "/cust_Keymapping?$filter=cust_Company eq '" + company + "' and cust_ObjectType eq '"
					+ ObjectType + "' and cust_LegacyID eq '" + cust_LegacyID + "'&$select=cust_SFID";
			logger.info("url:" + url);
			System.out.println(url);

			restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(userID, password));

			KeyMap result = restTemplate.getForObject(url, KeyMap.class);

			if (result != null) {
				D d = result.getD();
				List<Result> res = d.getResults();
				for (Result result1 : res) {
					newValue = result1.getCustSFID();

				}
			}

			if (newValue.isEmpty()) // Check reverse mapping
			{

				RestTemplate restTemplate1 = new RestTemplate();

				String url1 = urlx + "/cust_Keymapping?$filter=cust_Company eq '" + company
						+ "' and cust_ObjectType eq '" + ObjectType + "' and cust_SFID eq '" + cust_LegacyID
						+ "'&$select=cust_SFID";
				logger.info("url:" + url1);
				System.out.println(url1);

				restTemplate1.getInterceptors().add(new BasicAuthorizationInterceptor(userID, password));

				KeyMap result1 = restTemplate1.getForObject(url1, KeyMap.class);

				if (result1 != null) {
					D d1 = result1.getD();
					List<Result> res1 = d1.getResults();
					for (Result result2 : res1) {
						newValue = result2.getCustSFID();

					}
				}
			}

			if (newValue.isEmpty()) // Check Jobcode
			{

				RestTemplate restTemplate2 = new RestTemplate();

				String url2 = urlx + "/FOJobCode?$filter=externalCode eq '" + cust_LegacyID + "'&$select=externalCode";
				logger.info("url:" + url2);
				System.out.println(url2);

				restTemplate2.getInterceptors().add(new BasicAuthorizationInterceptor(userID, password));

				com.shd.FOJobCode.JobCode result2 = restTemplate2.getForObject(url2, com.shd.FOJobCode.JobCode.class);

				if (result2 != null) {
					com.shd.FOJobCode.D d2 = result2.getD();
					List<com.shd.FOJobCode.Result> res2 = d2.getResults();
					for (com.shd.FOJobCode.Result result3 : res2) {
						newValue = result3.getExternalCode();

					}
				}

			}

		}

		if (null != cust_LegacyID) {
			if (newValue.isEmpty()) {
				newValue = cust_LegacyID + "<-- Invalid Job Classification";
			}
		}

		return newValue;
	}

////Get Job Position

	public String getJobPositionID(List<MetaDataObj> rowData, int index, String company,
			Map<String, String> clientSystem, String isTestRun, Map<String, Map> initVal) throws Exception {

		String cust_LegacyID = ((MetaDataObj) rowData.get(index)).getFieldValue();
		String ObjectType = "09";

		String newValue = "";

		String urlx = clientSystem.get("URL");
		String userID = clientSystem.get("USER_ID");
		String password = clientSystem.get("PWD");

		Metadata metaData = new Metadata();

		if (null != cust_LegacyID && !cust_LegacyID.isEmpty()) {

			RestTemplate restTemplate = new RestTemplate();

			String url = urlx + "/cust_Keymapping?$filter=cust_Company eq '" + company + "' and cust_ObjectType eq '"
					+ ObjectType + "' and cust_LegacyID eq '" + cust_LegacyID + "'&$select=cust_SFID";
			logger.info("url:" + url);
			System.out.println(url);

			restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(userID, password));

			KeyMap result = restTemplate.getForObject(url, KeyMap.class);

			if (result != null) {
				D d = result.getD();
				List<Result> res = d.getResults();
				for (Result result1 : res) {
					newValue = result1.getCustSFID();

				}

				if (newValue.isEmpty()) {

					RestTemplate restTemplate1 = new RestTemplate();

					String url1 = urlx + "/cust_Keymapping?$filter=cust_Company eq '" + company
							+ "' and cust_ObjectType eq '" + ObjectType + "' and cust_SFID eq '" + cust_LegacyID
							+ "'&$select=cust_SFID";
					logger.info("url:" + url1);
					System.out.println(url1);

					restTemplate1.getInterceptors().add(new BasicAuthorizationInterceptor(userID, password));

					KeyMap result1 = restTemplate1.getForObject(url1, KeyMap.class);

					if (result1 != null) {
						D d1 = result1.getD();
						List<Result> res1 = d1.getResults();
						for (Result result2 : res1) {
							newValue = result2.getCustSFID();

						}

					}

				}

			}

		}

		if (null != cust_LegacyID) {
			if (newValue.isEmpty()) {
				newValue = cust_LegacyID + "<-- Invalid Position";
			}
		}

		return newValue;
	}

////Get Grade Id

	public String getPaygradeID(List<MetaDataObj> rowData, int index, String company, Map<String, String> clientSystem,
			String isTestRun, Map<String, Map> initVal) throws Exception {

		String cust_LegacyID = ((MetaDataObj) rowData.get(index)).getFieldValue();
		String ObjectType = "06";

		String newValue = "";

		String urlx = clientSystem.get("URL");
		String userID = clientSystem.get("USER_ID");
		String password = clientSystem.get("PWD");

		Metadata metaData = new Metadata();

		if (null != cust_LegacyID && !cust_LegacyID.isEmpty()) {

			RestTemplate restTemplate = new RestTemplate();

			String url = urlx + "/cust_Keymapping?$filter=cust_Company eq '" + company + "' and cust_ObjectType eq '"
					+ ObjectType + "' and cust_LegacyID eq '" + cust_LegacyID + "'&$select=cust_SFID";
			logger.info("url:" + url);
			System.out.println(url);

			restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(userID, password));

			KeyMap result = restTemplate.getForObject(url, KeyMap.class);

			if (result != null) {
				D d = result.getD();
				List<Result> res = d.getResults();
				for (Result result1 : res) {
					newValue = result1.getCustSFID();

				}

				if (newValue.isEmpty()) {

					RestTemplate restTemplate1 = new RestTemplate();

					String url1 = urlx + "/cust_Keymapping?$filter=cust_Company eq '" + company
							+ "' and cust_ObjectType eq '" + ObjectType + "' and cust_SFID eq '" + cust_LegacyID
							+ "'&$select=cust_SFID";
					logger.info("url:" + url1);
					System.out.println(url1);

					restTemplate1.getInterceptors().add(new BasicAuthorizationInterceptor(userID, password));

					KeyMap result1 = restTemplate1.getForObject(url1, KeyMap.class);

					if (result1 != null) {
						D d1 = result1.getD();
						List<Result> res1 = d1.getResults();
						for (Result result2 : res1) {
							newValue = result2.getCustSFID();

						}

					}

				}

			}

		}

		if (null != cust_LegacyID) {
			if (newValue.isEmpty()) {
				newValue = cust_LegacyID + "<-- Invalid PayGrade";
			}
		}

		return newValue;
	}

////Get Location Id

	public String getLocationID(List<MetaDataObj> rowData, int index, String company, Map<String, String> clientSystem,
			String isTestRun, Map<String, Map> initVal) throws Exception {

		String cust_LegacyID = ((MetaDataObj) rowData.get(index)).getFieldValue();
		String ObjectType = "03";

		String newValue = "";

		String urlx = clientSystem.get("URL");
		String userID = clientSystem.get("USER_ID");
		String password = clientSystem.get("PWD");

		Metadata metaData = new Metadata();

		if (null != cust_LegacyID && !cust_LegacyID.isEmpty()) {

			RestTemplate restTemplate = new RestTemplate();

			String url = urlx + "/cust_Keymapping?$filter=cust_Company eq '" + company + "' and cust_ObjectType eq '"
					+ ObjectType + "' and cust_LegacyID eq '" + cust_LegacyID + "'&$select=cust_SFID";
			logger.info("url:" + url);
			System.out.println(url);

			restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(userID, password));

			KeyMap result = restTemplate.getForObject(url, KeyMap.class);

			if (result != null) {
				D d = result.getD();
				List<Result> res = d.getResults();
				for (Result result1 : res) {
					newValue = result1.getCustSFID();

				}

				if (newValue.isEmpty()) {

					RestTemplate restTemplate1 = new RestTemplate();

					String url1 = urlx + "/cust_Keymapping?$filter=cust_Company eq '" + company
							+ "' and cust_ObjectType eq '" + ObjectType + "' and cust_SFID eq '" + cust_LegacyID
							+ "'&$select=cust_SFID";
					logger.info("url:" + url1);
					System.out.println(url1);

					restTemplate1.getInterceptors().add(new BasicAuthorizationInterceptor(userID, password));

					KeyMap result1 = restTemplate1.getForObject(url1, KeyMap.class);

					if (result1 != null) {
						D d1 = result1.getD();
						List<Result> res1 = d1.getResults();
						for (Result result2 : res1) {
							newValue = result2.getCustSFID();

						}

					}

				}

			}

		}

		if (null != cust_LegacyID) {
			if (newValue.isEmpty()) {
				newValue = cust_LegacyID + "<-- Invalid Location";
			}
		}

		return newValue;
	}

////Get Cost Center Id

	public String getCCID(List<MetaDataObj> rowData, int index, String company, Map<String, String> clientSystem,
			String isTestRun, Map<String, Map> initVal) throws Exception {

		String cust_LegacyID = ((MetaDataObj) rowData.get(index)).getFieldValue();
		String ObjectType = "03";

		String newValue = "";

		String urlx = clientSystem.get("URL");
		String userID = clientSystem.get("USER_ID");
		String password = clientSystem.get("PWD");

		Metadata metaData = new Metadata();

		if (null != cust_LegacyID && !cust_LegacyID.isEmpty()) {

			RestTemplate restTemplate = new RestTemplate();

			String url = urlx + "/cust_Keymapping?$filter=cust_Company eq '" + company + "' and cust_ObjectType eq '"
					+ ObjectType + "' and cust_LegacyID eq '" + cust_LegacyID + "'&$select=cust_SFID";
			logger.info("url:" + url);
			System.out.println(url);

			restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(userID, password));

			KeyMap result = restTemplate.getForObject(url, KeyMap.class);

			if (result != null) {
				D d = result.getD();
				List<Result> res = d.getResults();
				for (Result result1 : res) {
					newValue = result1.getCustSFID();

				}

				if (newValue.isEmpty()) {

					RestTemplate restTemplate1 = new RestTemplate();

					String url1 = urlx + "/cust_Keymapping?$filter=cust_Company eq '" + company
							+ "' and cust_ObjectType eq '" + ObjectType + "' and cust_SFID eq '" + cust_LegacyID
							+ "'&$select=cust_SFID";
					logger.info("url:" + url1);
					System.out.println(url1);

					restTemplate1.getInterceptors().add(new BasicAuthorizationInterceptor(userID, password));

					KeyMap result1 = restTemplate1.getForObject(url1, KeyMap.class);

					if (result1 != null) {
						D d1 = result1.getD();
						List<Result> res1 = d1.getResults();
						for (Result result2 : res1) {
							newValue = result2.getCustSFID();

						}

					}

				}

			}

		}

		if (null != cust_LegacyID) {
			if (newValue.isEmpty()) {
				newValue = cust_LegacyID + "<-- Invalid Cost Center";
			}
		}

		return newValue;
	}

////Get address 1

	public String address1(List<MetaDataObj> rowData, int index, String company, Map<String, String> clientSystem,
			String isTestRun, Map<String, Map> initVal) throws Exception {

		String cust_LegacyID = ((MetaDataObj) rowData.get(index)).getFieldValue();
		String ObjectType = "12";

		String operation = null;

		for (MetaDataObj metaDataObj : rowData) {
			if (null != metaDataObj.getFieldName() && "operation".equalsIgnoreCase(metaDataObj.getFieldName())) {
				operation = metaDataObj.getFieldValue();
			}
		}

		String newValue = "";

		String urlx = clientSystem.get("URL");
		String userID = clientSystem.get("USER_ID");
		String password = clientSystem.get("PWD");

		Metadata metaData = new Metadata();

		if (null != cust_LegacyID && !cust_LegacyID.isEmpty()) {

			RestTemplate restTemplate = new RestTemplate();

			String url = urlx + "/cust_Keymapping?$filter=cust_Company eq '" + company + "' and cust_ObjectType eq '"
					+ ObjectType + "' and externalCode eq '" + operation + "'&$select=externalName";
			logger.info("url:" + url);
			System.out.println(url);

			restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(userID, password));

			KeyMap result = restTemplate.getForObject(url, KeyMap.class);

			if (result != null) {
				D d = result.getD();
				List<Result> res = d.getResults();
				for (Result result1 : res) {
					newValue = result1.getExternalName();

				}

			}

		}

//if (newValue.isEmpty()) {
//newValue = cust_LegacyID + "<-- Address 1 not found for :" +operation ;
//}

		return newValue;
	}

////Get address 2

	public String address2(List<MetaDataObj> rowData, int index, String company, Map<String, String> clientSystem,
			String isTestRun, Map<String, Map> initVal) throws Exception {

		String cust_LegacyID = ((MetaDataObj) rowData.get(index)).getFieldValue();
		String ObjectType = "12";

		String operation = null;

		for (MetaDataObj metaDataObj : rowData) {
			if (null != metaDataObj.getFieldName() && "operation".equalsIgnoreCase(metaDataObj.getFieldName())) {
				operation = metaDataObj.getFieldValue();
			}
		}

		String newValue = "";

		String urlx = clientSystem.get("URL");
		String userID = clientSystem.get("USER_ID");
		String password = clientSystem.get("PWD");

		Metadata metaData = new Metadata();

		if (null != cust_LegacyID && !cust_LegacyID.isEmpty()) {

			RestTemplate restTemplate = new RestTemplate();

			String url = urlx + "/cust_Keymapping?$filter=cust_Company eq '" + company + "' and cust_ObjectType eq '"
					+ ObjectType + "' and externalCode eq '" + operation + "'&$select=cust_LegacyID";
			logger.info("url:" + url);
			System.out.println(url);

			restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(userID, password));

			KeyMap result = restTemplate.getForObject(url, KeyMap.class);

			if (result != null) {
				D d = result.getD();
				List<Result> res = d.getResults();
				for (Result result1 : res) {
					newValue = result1.getCust_LegacyID();

				}

			}

		}

//if (newValue.isEmpty()) {
//newValue = cust_LegacyID + "<-- Address 2 not found for :" +operation ;
//}

		return newValue;
	}

////Get address 3

	public String address3(List<MetaDataObj> rowData, int index, String company, Map<String, String> clientSystem,
			String isTestRun, Map<String, Map> initVal) throws Exception {

		String cust_LegacyID = ((MetaDataObj) rowData.get(index)).getFieldValue();
		String ObjectType = "12";

		String operation = null;

		for (MetaDataObj metaDataObj : rowData) {
			if (null != metaDataObj.getFieldName() && "operation".equalsIgnoreCase(metaDataObj.getFieldName())) {
				operation = metaDataObj.getFieldValue();
			}
		}

		String newValue = "";

		String urlx = clientSystem.get("URL");
		String userID = clientSystem.get("USER_ID");
		String password = clientSystem.get("PWD");

		Metadata metaData = new Metadata();

		if (null != cust_LegacyID && !cust_LegacyID.isEmpty()) {

			RestTemplate restTemplate = new RestTemplate();

			String url = urlx + "/cust_Keymapping?$filter=cust_Company eq '" + company + "' and cust_ObjectType eq '"
					+ ObjectType + "' and externalCode eq '" + operation + "'&$select=cust_SFID";
			logger.info("url:" + url);
			System.out.println(url);

			restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(userID, password));

			KeyMap result = restTemplate.getForObject(url, KeyMap.class);

			if (result != null) {
				D d = result.getD();
				List<Result> res = d.getResults();
				for (Result result1 : res) {
					newValue = result1.getCustSFID();

				}

			}

		}

//if (newValue.isEmpty()) {
//newValue = cust_LegacyID + "<-- Address 3 not found for :" +operation ;
//}

		return newValue;
	}

	public String ValidateJHJobClass(List<MetaDataObj> rowData, int index, String company,
			Map<String, String> clientSystem, String isTestRun, Map<String, Map> initVal) throws Exception {

		String JobCode = ((MetaDataObj) rowData.get(index)).getFieldValue();

		if (!(JobCode == null)) // Already has errors
		{
			int index1 = JobCode.indexOf("<--");
			if (index1 != -1) {
				return JobCode;
			}
		}

		String position = null;
		String startDate = null;
		String StartDatePrint = null;

		for (MetaDataObj metaDataObj : rowData) {
			if (null != metaDataObj.getFieldName() && "position".equalsIgnoreCase(metaDataObj.getFieldName())) {
				position = metaDataObj.getFieldValue();
			}
			if (null != metaDataObj.getFieldName() && "start-date".equalsIgnoreCase(metaDataObj.getFieldName())) {
				startDate = metaDataObj.getFieldValue();
				StartDatePrint = startDate;
				startDate = startDate.substring(6, 10) + "-" + startDate.substring(3, 5) + "-"
						+ startDate.substring(0, 2);
			}

		}

		if (!(position == null)) // Already has errors
		{
			int index1 = position.indexOf("<--");
			if (index1 != -1) {
				return JobCode;
			}
		}

		String ValidJobCode = "";

		String urlx = clientSystem.get("URL");
		String userID = clientSystem.get("USER_ID");
		String password = clientSystem.get("PWD");

		Metadata metaData = new Metadata();

		if (null != JobCode && !JobCode.isEmpty()) {

			RestTemplate restTemplate = new RestTemplate();

			String url = urlx + "/Position?$filter=code eq '" + position + "'&$fromDate=" + startDate + "&toDate="
					+ startDate + "&$select=jobCode";
			logger.info("url:" + url);
			System.out.println(url);

			restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(userID, password));

			com.shd.Position.Position Position = restTemplate.getForObject(url, com.shd.Position.Position.class);

			if (Position != null) {
				com.shd.Position.D d = Position.getD();
				List<com.shd.Position.Result> res = d.getResults();
				for (com.shd.Position.Result result1 : res) {
					ValidJobCode = result1.getJobCode();

				}
			}

			if (!ValidJobCode.isEmpty()) {
				if (!(ValidJobCode.equalsIgnoreCase(JobCode))) {

					JobCode = JobCode + "<-- Not same as in Position :" + position + "effective " + StartDatePrint
							+ " Valid Value: " + ValidJobCode;
				} else {

				}

			} else {
				JobCode = JobCode + "<-- Not same for Position :" + position + "effective " + StartDatePrint;
			}

		}
		return JobCode;
	}

	public String ValidateJHJobFamily(List<MetaDataObj> rowData, int index, String company,
			Map<String, String> clientSystem, String isTestRun, Map<String, Map> initVal) throws Exception {

		String JobFamily = ((MetaDataObj) rowData.get(index)).getFieldValue();

		if (!(JobFamily == null)) // Already has errors
		{
			int index1 = JobFamily.indexOf("<--");
			if (index1 != -1) {
				return JobFamily;
			}
		}

		String position = null;
		String startDate = null;
		String StartDatePrint = null;

		for (MetaDataObj metaDataObj : rowData) {
			if (null != metaDataObj.getFieldName() && "position".equalsIgnoreCase(metaDataObj.getFieldName())) {
				position = metaDataObj.getFieldValue();
			}
			if (null != metaDataObj.getFieldName() && "start-date".equalsIgnoreCase(metaDataObj.getFieldName())) {
				startDate = metaDataObj.getFieldValue();
				StartDatePrint = startDate;
				startDate = startDate.substring(6, 10) + "-" + startDate.substring(3, 5) + "-"
						+ startDate.substring(0, 2);
			}

		}

		if (!(position == null)) // Already has errors
		{
			int index1 = position.indexOf("<--");
			if (index1 != -1) {
				return JobFamily;
			}
		}

		String ValidValue = "";

		String urlx = clientSystem.get("URL");
		String userID = clientSystem.get("USER_ID");
		String password = clientSystem.get("PWD");

		Metadata metaData = new Metadata();

		if (null != JobFamily && !JobFamily.isEmpty()) {

			RestTemplate restTemplate = new RestTemplate();

			String url = urlx + "/Position?$filter=code eq '" + position + "'&$fromDate=" + startDate + "&toDate="
					+ startDate + "&$select=cust_JobFamily";
			logger.info("url:" + url);
			System.out.println(url);

			restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(userID, password));

			com.shd.Position.Position Position = restTemplate.getForObject(url, com.shd.Position.Position.class);

			if (Position != null) {
				com.shd.Position.D d = Position.getD();
				List<com.shd.Position.Result> res = d.getResults();
				for (com.shd.Position.Result result1 : res) {
					ValidValue = result1.getCustJobFamily();

				}
			}

			if (!ValidValue.isEmpty()) {
				if (!(ValidValue.equalsIgnoreCase(JobFamily))) {

					JobFamily = JobFamily + "<-- Not same as in Position :" + position + "effective " + StartDatePrint
							+ " Valid Value: " + ValidValue;
				} else {

				}

			} else {
				JobFamily = JobFamily + "<-- Not same for Position :" + position + "effective " + StartDatePrint;
			}

		}
		return JobFamily;
	}

	public String ValidateJHJobFunction(List<MetaDataObj> rowData, int index, String company,
			Map<String, String> clientSystem, String isTestRun, Map<String, Map> initVal) throws Exception {

		String JobFunction = ((MetaDataObj) rowData.get(index)).getFieldValue();

		if (!(JobFunction == null)) // Already has errors
		{
			int index1 = JobFunction.indexOf("<--");
			if (index1 != -1) {
				return JobFunction;
			}
		}

		String position = null;
		String startDate = null;
		String StartDatePrint = null;

		for (MetaDataObj metaDataObj : rowData) {
			if (null != metaDataObj.getFieldName() && "position".equalsIgnoreCase(metaDataObj.getFieldName())) {
				position = metaDataObj.getFieldValue();
			}
			if (null != metaDataObj.getFieldName() && "start-date".equalsIgnoreCase(metaDataObj.getFieldName())) {
				startDate = metaDataObj.getFieldValue();
				StartDatePrint = startDate;
				startDate = startDate.substring(6, 10) + "-" + startDate.substring(3, 5) + "-"
						+ startDate.substring(0, 2);
			}

		}

		if (!(position == null)) // Already has errors
		{
			int index1 = position.indexOf("<--");
			if (index1 != -1) {
				return JobFunction;
			}
		}

		String ValidValue = "";

		String urlx = clientSystem.get("URL");
		String userID = clientSystem.get("USER_ID");
		String password = clientSystem.get("PWD");

		Metadata metaData = new Metadata();

		if (null != JobFunction && !JobFunction.isEmpty()) {

			RestTemplate restTemplate = new RestTemplate();

			String url = urlx + "/Position?$filter=code eq '" + position + "'&$fromDate=" + startDate + "&toDate="
					+ startDate + "&$select=cust_jobFunction";
			logger.info("url:" + url);
			System.out.println(url);

			restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(userID, password));

			com.shd.Position.Position Position = restTemplate.getForObject(url, com.shd.Position.Position.class);

			if (Position != null) {
				com.shd.Position.D d = Position.getD();
				List<com.shd.Position.Result> res = d.getResults();
				for (com.shd.Position.Result result1 : res) {
					ValidValue = result1.getCustJobFunction();

				}
			}

			if (!ValidValue.isEmpty()) {
				if (!(ValidValue.equalsIgnoreCase(JobFunction))) {

					JobFunction = JobFunction + "<-- Not same as in Position :" + position + " effective "
							+ StartDatePrint + " Valid Value: " + ValidValue;
				} else {

				}

			} else {
				JobFunction = JobFunction + "<-- Not a Valid for Position :" + position + " effective "
						+ StartDatePrint;
			}

		}
		return JobFunction;
	}

	public String ValidateJHDepLevel1(List<MetaDataObj> rowData, int index, String company,
			Map<String, String> clientSystem, String isTestRun, Map<String, Map> initVal) throws Exception {

		String DepLevel1 = ((MetaDataObj) rowData.get(index)).getFieldValue();

		if (!(DepLevel1 == null)) // Already has errors
		{
			int index1 = DepLevel1.indexOf("<--");
			if (index1 != -1) {
				return DepLevel1;
			}
		}

		String position = null;
		String startDate = null;
		String StartDatePrint = null;

		for (MetaDataObj metaDataObj : rowData) {
			if (null != metaDataObj.getFieldName() && "position".equalsIgnoreCase(metaDataObj.getFieldName())) {
				position = metaDataObj.getFieldValue();
			}
			if (null != metaDataObj.getFieldName() && "start-date".equalsIgnoreCase(metaDataObj.getFieldName())) {
				startDate = metaDataObj.getFieldValue();
				StartDatePrint = startDate;
				startDate = startDate.substring(6, 10) + "-" + startDate.substring(3, 5) + "-"
						+ startDate.substring(0, 2);

			}

		}

		if (!(position == null)) // Already has errors
		{
			int index1 = position.indexOf("<--");
			if (index1 != -1) {
				return DepLevel1;
			}
		}

		String ValidValue = "";

		String urlx = clientSystem.get("URL");
		String userID = clientSystem.get("USER_ID");
		String password = clientSystem.get("PWD");

		Metadata metaData = new Metadata();

		if (null != DepLevel1 && !DepLevel1.isEmpty()) {

			RestTemplate restTemplate = new RestTemplate();

			String url = urlx + "/Position?$filter=code eq '" + position + "'&$fromDate=" + startDate + "&toDate="
					+ startDate + "&$select=cust_deptLevel1";
			logger.info("url:" + url);
			System.out.println(url);

			restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(userID, password));

			com.shd.Position.Position Position = restTemplate.getForObject(url, com.shd.Position.Position.class);

			if (Position != null) {
				com.shd.Position.D d = Position.getD();
				List<com.shd.Position.Result> res = d.getResults();
				for (com.shd.Position.Result result1 : res) {
					ValidValue = result1.getCustDeptLevel1();

				}
			}

			if (!ValidValue.isEmpty()) {
				if (!(ValidValue.equalsIgnoreCase(DepLevel1))) {

					DepLevel1 = DepLevel1 + "<-- Not same as in Position :" + position + " effective " + StartDatePrint
							+ " Valid Value: " + ValidValue;
				} else {

				}

			} else {

				DepLevel1 = DepLevel1 + "<-- Not a Valid for Position  :" + position + " effective " + StartDatePrint;
			}

		}
		return DepLevel1;
	}

	public String ValidateJHPos(List<MetaDataObj> rowData, int index, String company, Map<String, String> clientSystem,
			String isTestRun, Map<String, Map> initVal) throws Exception {

		String position = ((MetaDataObj) rowData.get(index)).getFieldValue();

		if (!(position == null)) // Already has errors
		{
			int index1 = position.indexOf("<--");
			if (index1 != -1) {
				return position;
			}
		}

		String startDate = null;
		String StartDatePrint = null;

		for (MetaDataObj metaDataObj : rowData) {

			if (null != metaDataObj.getFieldName() && "start-date".equalsIgnoreCase(metaDataObj.getFieldName())) {
				startDate = metaDataObj.getFieldValue();
				StartDatePrint = startDate;
				startDate = startDate.substring(6, 10) + "-" + startDate.substring(3, 5) + "-"
						+ startDate.substring(0, 2);

			}

		}

		String ValidValue = "";

		String urlx = clientSystem.get("URL");
		String userID = clientSystem.get("USER_ID");
		String password = clientSystem.get("PWD");

		Metadata metaData = new Metadata();

		if (null != position && !position.isEmpty()) {

			RestTemplate restTemplate = new RestTemplate();

			String url = urlx + "/Position?$filter=code eq '" + position + "'&$fromDate=" + startDate + "&toDate="
					+ startDate + "&$select=code";
			logger.info("url:" + url);
			System.out.println(url);

			restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(userID, password));

			com.shd.Position.Position Position = restTemplate.getForObject(url, com.shd.Position.Position.class);

			if (Position != null) {
				com.shd.Position.D d = Position.getD();
				List<com.shd.Position.Result> res = d.getResults();
				for (com.shd.Position.Result result1 : res) {
					ValidValue = result1.getCode();

				}
			}

			if (!ValidValue.isEmpty()) {
				if (!(ValidValue.equalsIgnoreCase(position))) {

					position = position + "<-- Not valid Position :" + position + " effective " + StartDatePrint;
				} else {

				}

			} else {

				position = position + "<-- Not a Valid for Position  :" + position + " effective " + StartDatePrint;
			}

		}
		return position;
	}

	public String getLegacyJobClass(List<MetaDataObj> rowData, int index, String company,
			Map<String, String> clientSystem, String isTestRun, Map<String, Map> initVal) throws Exception {

		String SFjc = null;

		for (MetaDataObj metaDataObj : rowData) {
//			logger.info("FOUtility: Inside getDepartmentID Method: metaDataObj.getFieldName():"
//					+ metaDataObj.getFieldName());
			if (null != metaDataObj.getFieldName() && "externalCode".equalsIgnoreCase(metaDataObj.getFieldName())) {
				SFjc = metaDataObj.getFieldValue();
			}
		}

		String cust_LegacyID = ((MetaDataObj) rowData.get(index)).getFieldValue();
		String ObjectType = "02";

		String newValue = "";

		String urlx = clientSystem.get("URL");
		String userID = clientSystem.get("USER_ID");
		String password = clientSystem.get("PWD");

		Metadata metaData = new Metadata();

		if (null != SFjc && !SFjc.isEmpty()) {

			RestTemplate restTemplate = new RestTemplate();

			String url = urlx + "/cust_Keymapping?$filter=cust_Company eq '" + company + "' and cust_ObjectType eq '"
					+ ObjectType + "' and cust_SFID eq '" + SFjc + "'&$select=cust_LegacyID";
			logger.info("url:" + url);
			System.out.println(url);

			restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(userID, password));

			KeyMap result = restTemplate.getForObject(url, KeyMap.class);

			if (result != null) {
				D d = result.getD();
				List<Result> res = d.getResults();
				for (Result result1 : res) {
					newValue = result1.getCust_LegacyID();

				}

				if (newValue.isEmpty()) {

					RestTemplate restTemplate1 = new RestTemplate();

					String url1 = urlx + "/cust_Keymapping?$filter=cust_Company eq '" + company
							+ "' and cust_ObjectType eq '" + ObjectType + "' and cust_SFID eq '" + cust_LegacyID
							+ "'&$select=cust_SFID";
					logger.info("url:" + url1);
					System.out.println(url1);

					restTemplate1.getInterceptors().add(new BasicAuthorizationInterceptor(userID, password));

					KeyMap result1 = restTemplate1.getForObject(url1, KeyMap.class);

					if (result1 != null) {
						D d1 = result1.getD();
						List<Result> res1 = d1.getResults();
						for (Result result2 : res1) {
							newValue = result2.getCustSFID();

						}

					}

				}

			}

		}
		if (null != cust_LegacyID) {
			if (newValue.isEmpty()) {
				newValue = cust_LegacyID;
			}

		}

		return newValue;
	}

	public String getLegacyPos(List<MetaDataObj> rowData, int index, String company, Map<String, String> clientSystem,
			String isTestRun, Map<String, Map> initVal) throws Exception {

		String SFPos = null;

		for (MetaDataObj metaDataObj : rowData) {
//			logger.info("FOUtility: Inside getDepartmentID Method: metaDataObj.getFieldName():"
//					+ metaDataObj.getFieldName());
			if (null != metaDataObj.getFieldName() && "externalCode".equalsIgnoreCase(metaDataObj.getFieldName())) {
				SFPos = metaDataObj.getFieldValue();
			}
		}

		String cust_LegacyID = ((MetaDataObj) rowData.get(index)).getFieldValue();
		String ObjectType = "02";

		String newValue = "";

		String urlx = clientSystem.get("URL");
		String userID = clientSystem.get("USER_ID");
		String password = clientSystem.get("PWD");

		Metadata metaData = new Metadata();

		if (null != SFPos && !SFPos.isEmpty()) {

			RestTemplate restTemplate = new RestTemplate();

			String url = urlx + "/cust_Keymapping?$filter=cust_Company eq '" + company + "' and cust_ObjectType eq '"
					+ ObjectType + "' and cust_SFID eq '" + SFPos + "'&$select=cust_LegacyID";
			logger.info("url:" + url);
			System.out.println(url);

			restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(userID, password));

			KeyMap result = restTemplate.getForObject(url, KeyMap.class);

			if (result != null) {
				D d = result.getD();
				List<Result> res = d.getResults();
				for (Result result1 : res) {
					newValue = result1.getCust_LegacyID();

				}

				if (newValue.isEmpty()) {

					RestTemplate restTemplate1 = new RestTemplate();

					String url1 = urlx + "/cust_Keymapping?$filter=cust_Company eq '" + company
							+ "' and cust_ObjectType eq '" + ObjectType + "' and cust_SFID eq '" + cust_LegacyID
							+ "'&$select=cust_SFID";
					logger.info("url:" + url1);
					System.out.println(url1);

					restTemplate1.getInterceptors().add(new BasicAuthorizationInterceptor(userID, password));

					KeyMap result1 = restTemplate1.getForObject(url1, KeyMap.class);

					if (result1 != null) {
						D d1 = result1.getD();
						List<Result> res1 = d1.getResults();
						for (Result result2 : res1) {
							newValue = result2.getCustSFID();

						}

					}

				}

			}

		}
		if (null != cust_LegacyID) {
			if (newValue.isEmpty()) {
				newValue = cust_LegacyID;
			}

		}

		return newValue;
	}

//-------------------- Version 2 ----------------------------//

	public String getLegacyDept_v2(List<MetaDataObj> rowData, int index, String company,
			Map<String, String> clientSystem, String isTestRun, Map<String, Map> initVal) throws Exception {

		String SFDep = null;

		for (MetaDataObj metaDataObj : rowData) {

			if (null != metaDataObj.getFieldName() && "externalCode".equalsIgnoreCase(metaDataObj.getFieldName())) {
				SFDep = metaDataObj.getFieldValue();
			}
		}

		String cust_LegacyID = ((MetaDataObj) rowData.get(index)).getFieldValue();
		String ObjectType = "'01'";

		String newValue = "";

		String urlx = clientSystem.get("URL");
		String userID = clientSystem.get("USER_ID");
		String password = clientSystem.get("PWD");

		Metadata metaData = new Metadata();

		if (null != SFDep && !SFDep.isEmpty()) {

			RestTemplate restTemplate = new RestTemplate();

			String url = urlx + "/cust_Keymapping?$filter=cust_Company eq '" + company + "' and cust_ObjectType eq '"
					+ ObjectType + "' and cust_SFID eq '" + SFDep + "'&$select=cust_LegacyID";
			logger.info("url:" + url);
			System.out.println(url);

			restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(userID, password));

			KeyMap result = restTemplate.getForObject(url, KeyMap.class);

			if (result != null) {
				D d = result.getD();
				List<Result> res = d.getResults();
				for (Result result1 : res) {
					newValue = result1.getCust_LegacyID();

				}

				if (newValue.isEmpty()) {

					RestTemplate restTemplate1 = new RestTemplate();

					String url1 = urlx + "/cust_Keymapping?$filter=cust_Company eq '" + company
							+ "' and cust_ObjectType eq '" + ObjectType + "' and cust_SFID eq '" + cust_LegacyID
							+ "'&$select=cust_SFID";
					logger.info("url:" + url1);
					System.out.println(url1);

					restTemplate1.getInterceptors().add(new BasicAuthorizationInterceptor(userID, password));

					KeyMap result1 = restTemplate1.getForObject(url1, KeyMap.class);

					if (result1 != null) {
						D d1 = result1.getD();
						List<Result> res1 = d1.getResults();
						for (Result result2 : res1) {
							newValue = result2.getCustSFID();

						}

					}

				}

			}

		}
		if (null != cust_LegacyID) {
			if (newValue.isEmpty()) {
				newValue = cust_LegacyID;
			}

		}

		return newValue;
	}

//----------------------------------------------------- Init Methods V2 ------------------------------------------------------------//

//-------------------- Init Method for Department ---------------------------//
	public InitVal getInitDepartment(String client, String tempgrp, String template, String company,
			Map<String, String> clientSystem, String isTestRun) throws Exception {
		InitVal InitVal = new InitVal();

		String urlx = clientSystem.get("URL");
		String userID = clientSystem.get("USER_ID");
		String password = clientSystem.get("PWD");
		String ObjectType = "'01','11'"; // Department,Division
		RestTemplate restTemplateCount = new RestTemplate();

		Map<String, Object> ObjectMap = new HashMap<String, Object>();
		Map<String, Map> InitMap = new HashMap<String, Map>();

		int t = 1000;
		long s = 0;
		long x = 0;

		String url = null;
		restTemplateCount.getInterceptors().add(new BasicAuthorizationInterceptor(userID, password));

		if ((isTestRun.equalsIgnoreCase("YesSim") || (isTestRun.equalsIgnoreCase("NoSim")))) {

			url = urlx + "/cust_Keymapping/$count?" + "$format=JSON&" + "$filter=cust_Company+eq+'" + company
					+ "' and cust_sim eq 'Y' and cust_ObjectType in " + ObjectType
					+ "&fromDate=1900-12-31&toDate=9999-12-31";

		} else {

			url = urlx + "/cust_Keymapping/$count?" + "$format=JSON&" + "$filter=cust_Company+eq+'" + company
					+ "' and cust_sim ne 'Y' and cust_ObjectType in " + ObjectType
					+ "&fromDate=1900-12-31&toDate=9999-12-31";
		}

		String count = restTemplateCount.getForObject(url, String.class);
		System.out.println(count);

		long c = Long.parseLong(count);

		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(userID, password));
		while (c > s) {

			if ((isTestRun.equalsIgnoreCase("YesSim") || (isTestRun.equalsIgnoreCase("NoSim")))) {
				url = urlx + "/cust_Keymapping?" + "$format=JSON&" + "$filter=cust_Company+eq+'" + company
						+ "' and cust_sim eq 'Y' and cust_ObjectType in " + ObjectType
						+ "&fromDate=1900-12-31&toDate=9999-12-31&$top=" + t + "&$skip=" + s + "";

			} else {
				url = urlx + "/cust_Keymapping?" + "$format=JSON&" + "$filter=cust_Company+eq+'" + company
						+ "' and cust_sim ne 'Y' and cust_ObjectType in " + ObjectType
						+ "&fromDate=1900-12-31&toDate=9999-12-31&$top=" + t + "&$skip=" + s + "";

			}

			System.out.println(url);
			KeyMap result = restTemplate.getForObject(url, KeyMap.class);

			if (result != null) {
				D d1 = result.getD();
				List<Result> res1 = d1.getResults();
				for (Result result2 : res1) {

					ObjectMap.put(result2.getExternalCode(), result2);

				}

				s = s + t;

			}

		}

		InitMap.put("cust_Keymapping", ObjectMap);

		InitVal.setInitVal(InitMap);

		return InitVal;

	}

//-------------------- Init Method for Job Classification ---------------------------//
	public InitVal getInitJobClass(String client, String tempgrp, String template, String company,
			Map<String, String> clientSystem, String isTestRun) throws Exception {
		InitVal InitVal = new InitVal();

		String urlx = clientSystem.get("URL");
		String userID = clientSystem.get("USER_ID");
		String password = clientSystem.get("PWD");
		String ObjectType = "'06'"; // Pay Grade
		RestTemplate restTemplateCount = new RestTemplate();

		Map<String, Object> ObjectMap = new HashMap<String, Object>();
		Map<String, Map> InitMap = new HashMap<String, Map>();

		int t = 1000;
		long s = 0;
		long x = 0;

		String url = null;
		restTemplateCount.getInterceptors().add(new BasicAuthorizationInterceptor(userID, password));

//--- Key Mapping table ---//

		if ((isTestRun.equalsIgnoreCase("YesSim") || (isTestRun.equalsIgnoreCase("NoSim")))) {
			url = urlx + "/cust_Keymapping/$count?" + "$format=JSON&" + "$filter=cust_Company+eq+'" + company
					+ "' and cust_sim eq 'Y' and cust_ObjectType in " + ObjectType
					+ "&fromDate=1900-12-31&toDate=9999-12-31";

		} else {
			url = urlx + "/cust_Keymapping/$count?" + "$format=JSON&" + "$filter=cust_Company+eq+'" + company
					+ "' and cust_sim ne 'Y' and cust_ObjectType in " + ObjectType
					+ "&fromDate=1900-12-31&toDate=9999-12-31";

		}

		String count = restTemplateCount.getForObject(url, String.class);
		System.out.println(count);

		long c = Long.parseLong(count);

		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(userID, password));
		while (c > s) {

			if ((isTestRun.equalsIgnoreCase("YesSim") || (isTestRun.equalsIgnoreCase("NoSim")))) {

				url = urlx + "/cust_Keymapping?" + "$format=JSON&" + "$filter=cust_Company+eq+'" + company
						+ "' and cust_sim eq 'Y' and cust_ObjectType in " + ObjectType
						+ "&fromDate=1900-12-31&toDate=9999-12-31&$top=" + t + "&$skip=" + s + "";

			} else {

				url = urlx + "/cust_Keymapping?" + "$format=JSON&" + "$filter=cust_Company+eq+'" + company
						+ "' and cust_sim ne 'Y' and cust_ObjectType in " + ObjectType
						+ "&fromDate=1900-12-31&toDate=9999-12-31&$top=" + t + "&$skip=" + s + "";
			}

			System.out.println(url);
			KeyMap result = restTemplate.getForObject(url, KeyMap.class);

			if (result != null) {
				D d1 = result.getD();
				List<Result> res1 = d1.getResults();
				for (Result result2 : res1) {

					ObjectMap.put(result2.getExternalCode(), result2);

				}

				s = s + t;

			}

		}

		InitMap.put("cust_Keymapping", ObjectMap);

		InitVal.setInitVal(InitMap);

		return InitVal;

	}

//-------------------- Init Method for Job Classification to Job Function Relationship ---------------------------//
	public InitVal getInitJobClasstoJobFunc(String client, String tempgrp, String template, String company,
			Map<String, String> clientSystem, String isTestRun) throws Exception {
		InitVal InitVal = new InitVal();

		String urlx = clientSystem.get("URL");
		String userID = clientSystem.get("USER_ID");
		String password = clientSystem.get("PWD");
		String ObjectType = "'02'"; // Job Classification
		RestTemplate restTemplateCount = new RestTemplate();

		Map<String, Object> ObjectMap = new HashMap<String, Object>();
		Map<String, Map> InitMap = new HashMap<String, Map>();

		int t = 1000;
		long s = 0;
		long x = 0;

		String url = null;
		restTemplateCount.getInterceptors().add(new BasicAuthorizationInterceptor(userID, password));

		// ------- Key Mapping table --------------//
		if ((isTestRun.equalsIgnoreCase("YesSim") || (isTestRun.equalsIgnoreCase("NoSim")))) {
			url = urlx + "/cust_Keymapping/$count?" + "$format=JSON&" + "$filter=cust_Company+eq+'" + company
					+ "' and cust_sim eq 'Y' and cust_ObjectType in " + ObjectType
					+ "&fromDate=1900-12-31&toDate=9999-12-31";

		} else {

			url = urlx + "/cust_Keymapping/$count?" + "$format=JSON&" + "$filter=cust_Company+eq+'" + company
					+ "' and cust_sim ne 'Y' and cust_ObjectType in " + ObjectType
					+ "&fromDate=1900-12-31&toDate=9999-12-31";
		}

		String count = restTemplateCount.getForObject(url, String.class);
		System.out.println(count);

		long c = Long.parseLong(count);

		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(userID, password));
		while (c > s) {

			if ((isTestRun.equalsIgnoreCase("YesSim") || (isTestRun.equalsIgnoreCase("NoSim")))) {

				url = urlx + "/cust_Keymapping?" + "$format=JSON&" + "$filter=cust_Company+eq+'" + company
						+ "' and cust_sim eq 'Y' and cust_ObjectType in " + ObjectType
						+ "&fromDate=1900-12-31&toDate=9999-12-31&$top=" + t + "&$skip=" + s + "";

			} else {
				url = urlx + "/cust_Keymapping?" + "$format=JSON&" + "$filter=cust_Company+eq+'" + company
						+ "' and cust_sim ne 'Y' and cust_ObjectType in " + ObjectType
						+ "&fromDate=1900-12-31&toDate=9999-12-31&$top=" + t + "&$skip=" + s + "";
			}

			System.out.println(url);
			KeyMap result = restTemplate.getForObject(url, KeyMap.class);

			if (result != null) {
				D d1 = result.getD();
				List<Result> res1 = d1.getResults();
				for (Result result2 : res1) {

					ObjectMap.put(result2.getExternalCode(), result2);

				}

				s = s + t;

			}

		}

		InitMap.put("cust_Keymapping", ObjectMap);

		InitVal.setInitVal(InitMap);

		return InitVal;

	}

//-------------------- Init Method for Position ---------------------------//
	public InitVal getInitPos(String client, String tempgrp, String template, String company,
			Map<String, String> clientSystem, String isTestRun) throws Exception {
		InitVal InitVal = new InitVal();

		String urlx = clientSystem.get("URL");
		String userID = clientSystem.get("USER_ID");
		String password = clientSystem.get("PWD");
		String ObjectType = "'02','09','11','01','06','03','08'"; // JobClass,Position,Division,Dep,Pay
																	// Grade,Location,Pay Range
		RestTemplate restTemplateCount = new RestTemplate();

		Map<String, Object> ObjectMap = new HashMap<String, Object>();
		Map<String, Map> InitMap = new HashMap<String, Map>();

		int t = 1000;
		long s = 0;
		long x = 0;

		String url = null;
		restTemplateCount.getInterceptors().add(new BasicAuthorizationInterceptor(userID, password));

		// ------- Key Mapping table --------------//

		if ((isTestRun.equalsIgnoreCase("YesSim") || (isTestRun.equalsIgnoreCase("NoSim")))) {
			url = urlx + "/cust_Keymapping/$count?" + "$format=JSON&" + "$filter=cust_Company+eq+'" + company
					+ "' and cust_sim eq 'Y' and cust_ObjectType in " + ObjectType
					+ "&fromDate=1900-12-31&toDate=9999-12-31";

		} else {
			url = urlx + "/cust_Keymapping/$count?" + "$format=JSON&" + "$filter=cust_Company+eq+'" + company
					+ "' and cust_sim ne 'Y' and cust_ObjectType in " + ObjectType
					+ "&fromDate=1900-12-31&toDate=9999-12-31";

		}

		String count = restTemplateCount.getForObject(url, String.class);
		System.out.println(count);

		long c = Long.parseLong(count);

		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(userID, password));
		while (c > s) {

			if ((isTestRun.equalsIgnoreCase("YesSim") || (isTestRun.equalsIgnoreCase("NoSim")))) {
				url = urlx + "/cust_Keymapping?" + "$format=JSON&" + "$filter=cust_Company+eq+'" + company
						+ "' and cust_sim eq 'Y' and cust_ObjectType in " + ObjectType
						+ "&fromDate=1900-12-31&toDate=9999-12-31&$top=" + t + "&$skip=" + s + "";
			} else {
				url = urlx + "/cust_Keymapping?" + "$format=JSON&" + "$filter=cust_Company+eq+'" + company
						+ "' and cust_sim ne 'Y' and cust_ObjectType in " + ObjectType
						+ "&fromDate=1900-12-31&toDate=9999-12-31&$top=" + t + "&$skip=" + s + "";

			}

			System.out.println(url);
			KeyMap result = restTemplate.getForObject(url, KeyMap.class);

			if (result != null) {
				D d1 = result.getD();
				List<Result> res1 = d1.getResults();
				for (Result result2 : res1) {

					ObjectMap.put(result2.getExternalCode(), result2);

				}

				s = s + t;

			}

		}

		InitMap.put("cust_Keymapping", ObjectMap);

		InitVal.setInitVal(InitMap);

		return InitVal;

	}

//-------------------- Init Method for Job History ---------------------------//
	public InitVal getInitJobHistory(String client, String tempgrp, String template, String company,
			Map<String, String> clientSystem, String isTestRun) throws Exception {

		InitVal InitVal = new InitVal();

		String urlx = clientSystem.get("URL");
		String userID = clientSystem.get("USER_ID");
		String password = clientSystem.get("PWD");
		String ObjectType = "'02','09','11','01','06','03','08'"; // JobClass,Position,Division,Dep,Pay Grade,Location,
																	// Pay Range
		RestTemplate restTemplateCount = new RestTemplate();

		Map<String, Object> ObjectMap = new HashMap<String, Object>();
		Map<String, Map> InitMap = new HashMap<String, Map>();

		int t = 1000;
		long s = 0;
		long x = 0;

		String url = null;
		restTemplateCount.getInterceptors().add(new BasicAuthorizationInterceptor(userID, password));

		// ------- Key Mapping table --------------//

		if ((isTestRun.equalsIgnoreCase("YesSim") || (isTestRun.equalsIgnoreCase("NoSim")))) {
			url = urlx + "/cust_Keymapping/$count?" + "$format=JSON&" + "$filter=cust_Company+eq+'" + company
					+ "' and cust_sim eq 'Y' and cust_ObjectType in " + ObjectType
					+ "&fromDate=1900-12-31&toDate=9999-12-31";
		} else {
			url = urlx + "/cust_Keymapping/$count?" + "$format=JSON&" + "$filter=cust_Company+eq+'" + company
					+ "' and cust_sim ne 'Y' and cust_ObjectType in " + ObjectType
					+ "&fromDate=1900-12-31&toDate=9999-12-31";

		}

		String count = restTemplateCount.getForObject(url, String.class);
		System.out.println(count);

		long c = Long.parseLong(count);

		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(userID, password));
		while (c > s) {
			if ((isTestRun.equalsIgnoreCase("YesSim") || (isTestRun.equalsIgnoreCase("NoSim")))) {
				url = urlx + "/cust_Keymapping?" + "$format=JSON&" + "$filter=cust_Company+eq+'" + company
						+ "' and cust_sim eq 'Y' and cust_ObjectType in " + ObjectType
						+ "&fromDate=1900-12-31&toDate=9999-12-31&$top=" + t + "&$skip=" + s + "";
			} else {
				url = urlx + "/cust_Keymapping?" + "$format=JSON&" + "$filter=cust_Company+eq+'" + company
						+ "' and cust_sim ne 'Y' and cust_ObjectType in " + ObjectType
						+ "&fromDate=1900-12-31&toDate=9999-12-31&$top=" + t + "&$skip=" + s + "";
			}

			System.out.println(url);
			KeyMap result = restTemplate.getForObject(url, KeyMap.class);

			if (result != null) {
				D d1 = result.getD();
				List<Result> res1 = d1.getResults();
				for (Result result2 : res1) {

					ObjectMap.put(result2.getExternalCode(), result2);

				}

				s = s + t;

			}

		}

		InitMap.put("cust_Keymapping", ObjectMap);

		// ------- Position table -------//

		Map<String, Object> PosObjectMap = new HashMap<String, Object>();
		String LE = null;

		if (company.equalsIgnoreCase("02")) {

			LE = "'A0500','A9999','A0700'";

		}

		if (company.equalsIgnoreCase("01")) {

			LE = "'A0800'";

		}

		if (company.equalsIgnoreCase("03")) {

			LE = "'A1200'";

		}

		if (company.equalsIgnoreCase("04")) {

			LE = "'H0800'";

		}
		if (company.equalsIgnoreCase("05")) {

			LE = "'A2000'";

		}
		if (company.equalsIgnoreCase("06")) {

			LE = "'A0900'";

		}
		if (company.equalsIgnoreCase("07")) {

			LE = "'A1000'";

		}
		if (company.equalsIgnoreCase("08")) {

			LE = "'A1400'";

		}
		if (company.equalsIgnoreCase("09")) {

			LE = "'A0600'";

		}

		url = urlx + "/Position/$count?" + "$format=JSON&" + "$filter=company+in+" + LE
				+ "&fromDate=1900-12-31&toDate=9999-12-31";

		RestTemplate restTemplateCountPos = new RestTemplate();
		restTemplateCountPos.getInterceptors().add(new BasicAuthorizationInterceptor(userID, password));

		count = "";
		count = restTemplateCountPos.getForObject(url, String.class);
		System.out.println(count);

		c = Long.parseLong(count);
		RestTemplate restTemplatePos = new RestTemplate();
		restTemplatePos.getInterceptors().add(new BasicAuthorizationInterceptor(userID, password));
		s = 0;
		while (c > s) {

			url = urlx + "/Position?" + "$format=JSON&" + "$filter=company+in+" + LE
					+ "&fromDate=1900-12-31&toDate=9999-12-31&$top=" + t + "&$skip=" + s
					+ "&$select= code,effectiveEndDate,externalName_defaultValue,externalName_localized,effectiveStatus,effectiveStartDate,type,cust_keyPosition,criticality,description,cust_JobFamily,cust_jobFunction,jobCode,jobLevel,employeeClass,payGrade,cust_PayGradeLevel,payRange,targetFTE,vacant, company,division,cust_deptLevel1,cust_deptLevel2,cust_deptLevel3,cust_deptLevel4,cust_deptLevel5,department,location,costCenter,"
					+ "cust_ProfitCenter,cust_line,cust_BrandsGroup,multipleIncumbentsAllowed,positionControlled,standardHours,changeReason,cust_BusinessCategory,cust_EmployeePayType,cust_Budgeted_Salary,cust_ParentDivision,cust_LegacyPositionID,cust_Bonus_STIP,cust_Region,";

			System.out.println(url);
			com.shd.Position.Position resultPos = restTemplatePos.getForObject(url, com.shd.Position.Position.class);

			if (resultPos != null) {
				com.shd.Position.D dpos = resultPos.getD();
				List<com.shd.Position.Result> resPos = dpos.getResults();
				for (com.shd.Position.Result result2 : resPos) {

					PosObjectMap.put(result2.getCode(), result2);

				}

				s = s + t;

			}

		}

		InitMap.put("Position", PosObjectMap);

		InitVal.setInitVal(InitMap);

		return InitVal;

	}

	// ---------Init method for Cost Center-----------------//

	public InitVal getInitCostCenter(String client, String tempgrp, String template, String company,
			Map<String, String> clientSystem, String isTestRun) throws Exception {
		InitVal InitVal = new InitVal();

		String urlx = clientSystem.get("URL");
		String userID = clientSystem.get("USER_ID");
		String password = clientSystem.get("PWD");
		String ObjectType = "'01'"; // Dep
		RestTemplate restTemplateCount = new RestTemplate();

		Map<String, Object> ObjectMap = new HashMap<String, Object>();
		Map<String, Map> InitMap = new HashMap<String, Map>();

		int t = 1000;
		long s = 0;
		long x = 0;

		String url = null;
		restTemplateCount.getInterceptors().add(new BasicAuthorizationInterceptor(userID, password));

		// ------- Key Mapping table --------------//

		if ((isTestRun.equalsIgnoreCase("YesSim") || (isTestRun.equalsIgnoreCase("NoSim")))) {
			url = urlx + "/cust_Keymapping/$count?" + "$format=JSON&" + "$filter=cust_Company+eq+'" + company
					+ "' and cust_sim eq 'Y' and cust_ObjectType in " + ObjectType
					+ "&fromDate=1900-12-31&toDate=9999-12-31";

		} else {
			url = urlx + "/cust_Keymapping/$count?" + "$format=JSON&" + "$filter=cust_Company+eq+'" + company
					+ "' and cust_sim ne 'Y' and cust_ObjectType in " + ObjectType
					+ "&fromDate=1900-12-31&toDate=9999-12-31";

		}

		String count = restTemplateCount.getForObject(url, String.class);
		System.out.println(count);

		long c = Long.parseLong(count);

		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(userID, password));
		while (c > s) {

			if ((isTestRun.equalsIgnoreCase("YesSim") || (isTestRun.equalsIgnoreCase("NoSim")))) {
				url = urlx + "/cust_Keymapping?" + "$format=JSON&" + "$filter=cust_Company+eq+'" + company
						+ "' and cust_sim eq 'Y' and cust_ObjectType in " + ObjectType
						+ "&fromDate=1900-12-31&toDate=9999-12-31&$top=" + t + "&$skip=" + s + "";
			} else {
				url = urlx + "/cust_Keymapping?" + "$format=JSON&" + "$filter=cust_Company+eq+'" + company
						+ "' and cust_sim ne 'Y' and cust_ObjectType in " + ObjectType
						+ "&fromDate=1900-12-31&toDate=9999-12-31&$top=" + t + "&$skip=" + s + "";

			}

			System.out.println(url);
			KeyMap result = restTemplate.getForObject(url, KeyMap.class);

			if (result != null) {
				D d1 = result.getD();
				List<Result> res1 = d1.getResults();
				for (Result result2 : res1) {

					ObjectMap.put(result2.getExternalCode(), result2);

				}

				s = s + t;

			}

		}

		InitMap.put("cust_Keymapping", ObjectMap);

		InitVal.setInitVal(InitMap);

		return InitVal;

	}

	// ---------Init method for Cost Center-----------------//

	public InitVal getInitPayRange(String client, String tempgrp, String template, String company,
			Map<String, String> clientSystem, String isTestRun) throws Exception {
		InitVal InitVal = new InitVal();

		String urlx = clientSystem.get("URL");
		String userID = clientSystem.get("USER_ID");
		String password = clientSystem.get("PWD");
		String ObjectType = "'06'"; // PayGrade
		RestTemplate restTemplateCount = new RestTemplate();

		Map<String, Object> ObjectMap = new HashMap<String, Object>();
		Map<String, Map> InitMap = new HashMap<String, Map>();

		int t = 1000;
		long s = 0;
		long x = 0;

		String url = null;
		restTemplateCount.getInterceptors().add(new BasicAuthorizationInterceptor(userID, password));

		// ------- Key Mapping table --------------//

		if ((isTestRun.equalsIgnoreCase("YesSim") || (isTestRun.equalsIgnoreCase("NoSim")))) {
			url = urlx + "/cust_Keymapping/$count?" + "$format=JSON&" + "$filter=cust_Company+eq+'" + company
					+ "' and cust_sim eq 'Y' and cust_ObjectType in " + ObjectType
					+ "&fromDate=1900-12-31&toDate=9999-12-31";

		} else {
			url = urlx + "/cust_Keymapping/$count?" + "$format=JSON&" + "$filter=cust_Company+eq+'" + company
					+ "' and cust_sim ne 'Y' and cust_ObjectType in " + ObjectType
					+ "&fromDate=1900-12-31&toDate=9999-12-31";

		}

		String count = restTemplateCount.getForObject(url, String.class);
		System.out.println(count);

		long c = Long.parseLong(count);

		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(userID, password));
		while (c > s) {

			if ((isTestRun.equalsIgnoreCase("YesSim") || (isTestRun.equalsIgnoreCase("NoSim")))) {
				url = urlx + "/cust_Keymapping?" + "$format=JSON&" + "$filter=cust_Company+eq+'" + company
						+ "' and cust_sim eq 'Y' and cust_ObjectType in " + ObjectType
						+ "&fromDate=1900-12-31&toDate=9999-12-31&$top=" + t + "&$skip=" + s + "";
			} else {
				url = urlx + "/cust_Keymapping?" + "$format=JSON&" + "$filter=cust_Company+eq+'" + company
						+ "' and cust_sim ne 'Y' and cust_ObjectType in " + ObjectType
						+ "&fromDate=1900-12-31&toDate=9999-12-31&$top=" + t + "&$skip=" + s + "";

			}

			System.out.println(url);
			KeyMap result = restTemplate.getForObject(url, KeyMap.class);

			if (result != null) {
				D d1 = result.getD();
				List<Result> res1 = d1.getResults();
				for (Result result2 : res1) {

					ObjectMap.put(result2.getExternalCode(), result2);

				}

				s = s + t;

			}

		}

		InitMap.put("cust_Keymapping", ObjectMap);

		InitVal.setInitVal(InitMap);

		return InitVal;

	}

//----------------------------------------------------- Legacy to MIRAI Methods V2 ------------------------------------------------------------//	
// ------Get MIRAI Department from Legacy ID -------------//	

	public String getDept_v2(List<MetaDataObj> rowData, int index, String company, Map<String, String> clientSystem,
			String isTestRun, Map<String, Map> initVal) throws Exception {

		Map<String, Object> cust_KeymappingMap = new HashMap<String, Object>();
		List<com.shd.keymap.Result> cust_KeymappingLst = new ArrayList();

		String cust_LegacyID = ((MetaDataObj) rowData.get(index)).getFieldValue();

		String ObjectType = "01"; // Department

		String newValue = "";

		if (null != cust_LegacyID && !cust_LegacyID.isEmpty()) {

			cust_KeymappingMap = initVal.get("cust_Keymapping");

			Iterator<Map.Entry<String, Object>> itr = cust_KeymappingMap.entrySet().iterator();
			while (itr.hasNext()) {
				Map.Entry<String, Object> entry = itr.next();
				cust_KeymappingLst.add((com.shd.keymap.Result) entry.getValue());

			}

			// ---Check if legacy id is same as the legacy id?
			for (com.shd.keymap.Result cust_Keymapping : cust_KeymappingLst) {

				if (cust_Keymapping.getCust_ObjectType().equalsIgnoreCase(ObjectType)) {
					if (cust_Keymapping.getCust_LegacyID().equalsIgnoreCase(cust_LegacyID)) {

						newValue = cust_Keymapping.getCustSFID();
					}
				}
			}
			// ---Check if legacy id is same as the SFID?
			if (newValue.isEmpty()) {

				for (com.shd.keymap.Result cust_Keymapping : cust_KeymappingLst) {
					if (cust_Keymapping.getCust_ObjectType().equalsIgnoreCase(ObjectType)) {
						if (cust_Keymapping.getCustSFID().equalsIgnoreCase(cust_LegacyID)) {
							newValue = cust_Keymapping.getCustSFID();
						}

					}
				}

			}
		}

		if (null != cust_LegacyID) {
			if (newValue.isEmpty()) {
				newValue = cust_LegacyID + "<-- Invalid Department";
			}
		}

		return newValue;

	}

//------Get MIRAI Division from Legacy ID -------------//

	public String getDiv_v2(List<MetaDataObj> rowData, int index, String company, Map<String, String> clientSystem,
			String isTestRun, Map<String, Map> initVal) throws Exception {

		Map<String, Object> cust_KeymappingMap = new HashMap<String, Object>();
		List<com.shd.keymap.Result> cust_KeymappingLst = new ArrayList();

		String cust_LegacyID = ((MetaDataObj) rowData.get(index)).getFieldValue();
		String ObjectType = "11"; // Division

		String newValue = "";

		if (null != cust_LegacyID && !cust_LegacyID.isEmpty()) {

			cust_KeymappingMap = initVal.get("cust_Keymapping");

			Iterator<Map.Entry<String, Object>> itr = cust_KeymappingMap.entrySet().iterator();
			while (itr.hasNext()) {
				Map.Entry<String, Object> entry = itr.next();
				cust_KeymappingLst.add((com.shd.keymap.Result) entry.getValue());

			}

			// ---Check if legacy id is same as the legacy id?
			for (com.shd.keymap.Result cust_Keymapping : cust_KeymappingLst) {

				if (cust_Keymapping.getCust_ObjectType().equalsIgnoreCase(ObjectType)) {
					if (cust_Keymapping.getCust_LegacyID().equalsIgnoreCase(cust_LegacyID)) {

						newValue = cust_Keymapping.getCustSFID();
					}
				}
			}
			// ---Check if legacy id is same as the SFID?
			if (newValue.isEmpty()) {

				for (com.shd.keymap.Result cust_Keymapping : cust_KeymappingLst) {
					if (cust_Keymapping.getCust_ObjectType().equalsIgnoreCase(ObjectType)) {
						if (cust_Keymapping.getCustSFID().equalsIgnoreCase(cust_LegacyID)) {
							newValue = cust_Keymapping.getCustSFID();
						}

					}
				}

			}
		}

		if (null != cust_LegacyID) {
			if (newValue.isEmpty()) {
				newValue = cust_LegacyID + "<-- Invalid Division";
			}
		}

		return newValue;
	}

//------Get MIRAI Job Classification from Legacy ID + including FO Job Class check-------------//	
	public String getJobClassificationID_v2(List<MetaDataObj> rowData, int index, String company,
			Map<String, String> clientSystem, String isTestRun, Map<String, Map> initVal) throws Exception {

		String urlx = clientSystem.get("URL");
		String userID = clientSystem.get("USER_ID");
		String password = clientSystem.get("PWD");

		Map<String, Object> cust_KeymappingMap = new HashMap<String, Object>();
		List<com.shd.keymap.Result> cust_KeymappingLst = new ArrayList();
		String cust_LegacyID = ((MetaDataObj) rowData.get(index)).getFieldValue();
		String ObjectType = "02"; // Job Classification

		String newValue = "";

		if (null != cust_LegacyID && !cust_LegacyID.isEmpty()) {

			cust_KeymappingMap = initVal.get("cust_Keymapping");

			Iterator<Map.Entry<String, Object>> itr = cust_KeymappingMap.entrySet().iterator();
			while (itr.hasNext()) {
				Map.Entry<String, Object> entry = itr.next();
				cust_KeymappingLst.add((com.shd.keymap.Result) entry.getValue());

			}

			// ---Check if legacy id is same as the legacy id?
			for (com.shd.keymap.Result cust_Keymapping : cust_KeymappingLst) {

				if (cust_Keymapping.getCust_ObjectType().equalsIgnoreCase(ObjectType)) {
					if (cust_Keymapping.getCust_LegacyID().equalsIgnoreCase(cust_LegacyID)) {

						newValue = cust_Keymapping.getCustSFID();
					}
				}
			}
			// ---Check if legacy id is same as the SFID?
			if (newValue.isEmpty()) {

				for (com.shd.keymap.Result cust_Keymapping : cust_KeymappingLst) {
					if (cust_Keymapping.getCust_ObjectType().equalsIgnoreCase(ObjectType)) {
						if (cust_Keymapping.getCustSFID().equalsIgnoreCase(cust_LegacyID)) {
							newValue = cust_Keymapping.getCustSFID();
						}

					}
				}

			}

			if (newValue.isEmpty()) // Check Jobcode
			{

// Let it read for each entry as it will be very less
				RestTemplate restTemplate2 = new RestTemplate();

				String url2 = urlx + "/FOJobCode?$filter=externalCode eq '" + cust_LegacyID + "'&$select=externalCode";
				logger.info("url:" + url2);
				System.out.println(url2);

				restTemplate2.getInterceptors().add(new BasicAuthorizationInterceptor(userID, password));

				com.shd.FOJobCode.JobCode result2 = restTemplate2.getForObject(url2, com.shd.FOJobCode.JobCode.class);

				if (result2 != null) {
					com.shd.FOJobCode.D d2 = result2.getD();
					List<com.shd.FOJobCode.Result> res2 = d2.getResults();
					for (com.shd.FOJobCode.Result result3 : res2) {
						newValue = result3.getExternalCode();

					}
				}

			}

		}

		if (null != cust_LegacyID) {
			if (newValue.isEmpty()) {
				newValue = cust_LegacyID + "<-- Invalid Job Classification";
			}
		}

		return newValue;
	}

	// ------Get MIRAI Job Classification from Legacy ID -------------//
	public String getJobClassificationID_func(List<MetaDataObj> rowData, int index, String company,
			Map<String, String> clientSystem, String isTestRun, Map<String, Map> initVal) throws Exception {

		Map<String, Object> cust_KeymappingMap = new HashMap<String, Object>();
		List<com.shd.keymap.Result> cust_KeymappingLst = new ArrayList();
		String cust_LegacyID = ((MetaDataObj) rowData.get(index)).getFieldValue();
		String ObjectType = "02"; // Job Classification

		String newValue = "";

		if (null != cust_LegacyID && !cust_LegacyID.isEmpty()) {

			cust_KeymappingMap = initVal.get("cust_Keymapping");

			Iterator<Map.Entry<String, Object>> itr = cust_KeymappingMap.entrySet().iterator();
			while (itr.hasNext()) {
				Map.Entry<String, Object> entry = itr.next();
				cust_KeymappingLst.add((com.shd.keymap.Result) entry.getValue());

			}

			// ---Check if legacy id is same as the legacy id?
			for (com.shd.keymap.Result cust_Keymapping : cust_KeymappingLst) {

				if (cust_Keymapping.getCust_ObjectType().equalsIgnoreCase(ObjectType)) {
					if (cust_Keymapping.getCust_LegacyID().equalsIgnoreCase(cust_LegacyID)) {

						newValue = cust_Keymapping.getCustSFID();
					}
				}
			}
			// ---Check if legacy id is same as the SFID?
			if (newValue.isEmpty()) {

				for (com.shd.keymap.Result cust_Keymapping : cust_KeymappingLst) {
					if (cust_Keymapping.getCust_ObjectType().equalsIgnoreCase(ObjectType)) {
						if (cust_Keymapping.getCustSFID().equalsIgnoreCase(cust_LegacyID)) {
							newValue = cust_Keymapping.getCustSFID();
						}

					}
				}

			}

		}

		if (null != cust_LegacyID) {
			if (newValue.isEmpty()) {
				newValue = cust_LegacyID + "<-- Invalid Job Classification";
			}
		}

		return newValue;
	}

//------Get MIRAI Pay Grade from Legacy ID -------------//	

	public String getPaygradeID_v2(List<MetaDataObj> rowData, int index, String company,
			Map<String, String> clientSystem, String isTestRun, Map<String, Map> initVal) throws Exception {

		Map<String, Object> cust_KeymappingMap = new HashMap<String, Object>();
		List<com.shd.keymap.Result> cust_KeymappingLst = new ArrayList();
		String cust_LegacyID = ((MetaDataObj) rowData.get(index)).getFieldValue();
		String ObjectType = "06"; // Pay Grade

		String newValue = "";

		Metadata metaData = new Metadata();

		if (null != cust_LegacyID && !cust_LegacyID.isEmpty()) {

			cust_KeymappingMap = initVal.get("cust_Keymapping");

			Iterator<Map.Entry<String, Object>> itr = cust_KeymappingMap.entrySet().iterator();
			while (itr.hasNext()) {
				Map.Entry<String, Object> entry = itr.next();
				cust_KeymappingLst.add((com.shd.keymap.Result) entry.getValue());

			}

			// ---Check if legacy id is same as the legacy id?
			for (com.shd.keymap.Result cust_Keymapping : cust_KeymappingLst) {

				if (cust_Keymapping.getCust_ObjectType().equalsIgnoreCase(ObjectType)) {
					if (cust_Keymapping.getCust_LegacyID().equalsIgnoreCase(cust_LegacyID)) {

						newValue = cust_Keymapping.getCustSFID();
					}
				}
			}
			// ---Check if legacy id is same as the SFID?
			if (newValue.isEmpty()) {

				for (com.shd.keymap.Result cust_Keymapping : cust_KeymappingLst) {
					if (cust_Keymapping.getCust_ObjectType().equalsIgnoreCase(ObjectType)) {
						if (cust_Keymapping.getCustSFID().equalsIgnoreCase(cust_LegacyID)) {
							newValue = cust_Keymapping.getCustSFID();
						}

					}
				}
			}
		}
		if (null != cust_LegacyID) {
			if (newValue.isEmpty()) {
				newValue = cust_LegacyID + "<-- Invalid PayGrade";
			}
		}

		return newValue;
	}

	// ------Get MIRAI Pay Grade from Legacy ID -------------//

	public String getPayRangeID_v2(List<MetaDataObj> rowData, int index, String company,
			Map<String, String> clientSystem, String isTestRun, Map<String, Map> initVal) throws Exception {

		Map<String, Object> cust_KeymappingMap = new HashMap<String, Object>();
		List<com.shd.keymap.Result> cust_KeymappingLst = new ArrayList();
		String cust_LegacyID = ((MetaDataObj) rowData.get(index)).getFieldValue();
		String ObjectType = "08"; // Pay Grade

		String newValue = "";

		Metadata metaData = new Metadata();

		if (null != cust_LegacyID && !cust_LegacyID.isEmpty()) {

			cust_KeymappingMap = initVal.get("cust_Keymapping");

			Iterator<Map.Entry<String, Object>> itr = cust_KeymappingMap.entrySet().iterator();
			while (itr.hasNext()) {
				Map.Entry<String, Object> entry = itr.next();
				cust_KeymappingLst.add((com.shd.keymap.Result) entry.getValue());

			}

			// ---Check if legacy id is same as the legacy id?
			for (com.shd.keymap.Result cust_Keymapping : cust_KeymappingLst) {

				if (cust_Keymapping.getCust_ObjectType().equalsIgnoreCase(ObjectType)) {
					if (cust_Keymapping.getCust_LegacyID().equalsIgnoreCase(cust_LegacyID)) {

						newValue = cust_Keymapping.getCustSFID();
					}
				}
			}
			// ---Check if legacy id is same as the SFID?
			if (newValue.isEmpty()) {

				for (com.shd.keymap.Result cust_Keymapping : cust_KeymappingLst) {
					if (cust_Keymapping.getCust_ObjectType().equalsIgnoreCase(ObjectType)) {
						if (cust_Keymapping.getCustSFID().equalsIgnoreCase(cust_LegacyID)) {
							newValue = cust_Keymapping.getCustSFID();
						}

					}
				}
			}
		}
		if (null != cust_LegacyID) {
			if (newValue.isEmpty()) {
				newValue = cust_LegacyID + "<-- Invalid PayRange";
			}
		}

		return newValue;
	}

	// ------Get MIRAI Position from Legacy ID -------------//
	public String getJobPositionID_v2(List<MetaDataObj> rowData, int index, String company,
			Map<String, String> clientSystem, String isTestRun, Map<String, Map> initVal) throws Exception {

		Map<String, Object> cust_KeymappingMap = new HashMap<String, Object>();
		List<com.shd.keymap.Result> cust_KeymappingLst = new ArrayList();
		String cust_LegacyID = ((MetaDataObj) rowData.get(index)).getFieldValue();
		String ObjectType = "09";

		String newValue = "";

		if (null != cust_LegacyID && !cust_LegacyID.isEmpty()) {

			cust_KeymappingMap = initVal.get("cust_Keymapping");

			Iterator<Map.Entry<String, Object>> itr = cust_KeymappingMap.entrySet().iterator();
			while (itr.hasNext()) {
				Map.Entry<String, Object> entry = itr.next();
				cust_KeymappingLst.add((com.shd.keymap.Result) entry.getValue());

			}

			// ---Check if legacy id is same as the legacy id?
			for (com.shd.keymap.Result cust_Keymapping : cust_KeymappingLst) {

				if (cust_Keymapping.getCust_ObjectType().equalsIgnoreCase(ObjectType)) {
					if (cust_Keymapping.getCust_LegacyID().equalsIgnoreCase(cust_LegacyID)) {

						newValue = cust_Keymapping.getCustSFID();
					}
				}
			}
			// ---Check if legacy id is same as the SFID?
			if (newValue.isEmpty()) {

				for (com.shd.keymap.Result cust_Keymapping : cust_KeymappingLst) {
					if (cust_Keymapping.getCust_ObjectType().equalsIgnoreCase(ObjectType)) {
						if (cust_Keymapping.getCustSFID().equalsIgnoreCase(cust_LegacyID)) {
							newValue = cust_Keymapping.getCustSFID();
						}

					}
				}
			}
		}

		if (null != cust_LegacyID) {
			if (newValue.isEmpty()) {
				newValue = cust_LegacyID + "<-- Invalid Position";
			}
		}

		return newValue;
	}

	// ------Get MIRAI Location from Legacy ID -------------//

	public String getLocationID_v2(List<MetaDataObj> rowData, int index, String company,
			Map<String, String> clientSystem, String isTestRun, Map<String, Map> initVal) throws Exception {

		Map<String, Object> cust_KeymappingMap = new HashMap<String, Object>();
		List<com.shd.keymap.Result> cust_KeymappingLst = new ArrayList();
		String cust_LegacyID = ((MetaDataObj) rowData.get(index)).getFieldValue();
		String ObjectType = "03";

		String newValue = "";

		if (null != cust_LegacyID && !cust_LegacyID.isEmpty()) {

			cust_KeymappingMap = initVal.get("cust_Keymapping");

			Iterator<Map.Entry<String, Object>> itr = cust_KeymappingMap.entrySet().iterator();
			while (itr.hasNext()) {
				Map.Entry<String, Object> entry = itr.next();
				cust_KeymappingLst.add((com.shd.keymap.Result) entry.getValue());

			}

			// ---Check if legacy id is same as the legacy id?
			for (com.shd.keymap.Result cust_Keymapping : cust_KeymappingLst) {

				if (cust_Keymapping.getCust_ObjectType().equalsIgnoreCase(ObjectType)) {
					if (cust_Keymapping.getCust_LegacyID().equalsIgnoreCase(cust_LegacyID)) {

						newValue = cust_Keymapping.getCustSFID();
					}
				}
			}
			// ---Check if legacy id is same as the SFID?
			if (newValue.isEmpty()) {

				for (com.shd.keymap.Result cust_Keymapping : cust_KeymappingLst) {
					if (cust_Keymapping.getCust_ObjectType().equalsIgnoreCase(ObjectType)) {
						if (cust_Keymapping.getCustSFID().equalsIgnoreCase(cust_LegacyID)) {
							newValue = cust_Keymapping.getCustSFID();
						}

					}
				}
			}
		}

		if (null != cust_LegacyID) {
			if (newValue.isEmpty()) {
				newValue = cust_LegacyID + "<-- Invalid Location";
			}
		}

		return newValue;
	}

//----------------------------------------------------- Validation to MIRAI Methods V2 ------------------------------------------------------------//		
//------Get MIRAI Location from Legacy ID -------------//	

	public String ValidateJHPos_v2(List<MetaDataObj> rowData, int index, String company,
			Map<String, String> clientSystem, String isTestRun, Map<String, Map> initVal) throws Exception {

		Utility ut = new Utility();
		String position = ((MetaDataObj) rowData.get(index)).getFieldValue();

		if ((isTestRun.equalsIgnoreCase("YesSim") || (isTestRun.equalsIgnoreCase("NoSim")))) {
			return position;
		}

		if (!(position == null)) // Already has errors
		{
			int index1 = position.indexOf("<--");
			if (index1 != -1) {
				return position;
			}
		}

		String startDate = null;
		String StartDatePrint = null;

		// Read effective date in job history
		for (MetaDataObj metaDataObj : rowData) {

			if (null != metaDataObj.getFieldName() && "start-date".equalsIgnoreCase(metaDataObj.getFieldName())) {
				startDate = metaDataObj.getFieldValue();
				StartDatePrint = startDate;
				startDate = startDate.substring(6, 10) + "-" + startDate.substring(3, 5) + "-"
						+ startDate.substring(0, 2);

			}

		}

		Map<String, Object> PositionMap = new HashMap<String, Object>();
		List<com.shd.Position.Result> PositionLst = new ArrayList();

		String ValidValue = "";

		if (null != position && !position.isEmpty()) {

			PositionMap = initVal.get("Position");

			Iterator<Map.Entry<String, Object>> itr = PositionMap.entrySet().iterator();
			while (itr.hasNext()) {
				Map.Entry<String, Object> entry = itr.next();
				PositionLst.add((com.shd.Position.Result) entry.getValue());

			}

			Date begda = null;
			Date endda = null;
			Date posstartdate = null;

			posstartdate = new SimpleDateFormat("yyyy-MM-dd").parse(startDate);

			// ---Check if legacy id is same as the legacy id?
			for (com.shd.Position.Result Position : PositionLst) {

				begda = new SimpleDateFormat("yyyy-MM-dd")
						.parse(ut.getOdataEpochiToJavaDsh(Position.getEffectiveStartDate()));
				endda = new SimpleDateFormat("yyyy-MM-dd")
						.parse(ut.getOdataEpochiToJavaDsh(Position.getEffectiveEndDate()));

				if (Position.getCode().equalsIgnoreCase(position)) {
					if ((begda.compareTo(posstartdate) <= 0) && (endda.compareTo(posstartdate) >= 0)) {

						ValidValue = Position.getCode();
					}
				}
			}

//			RestTemplate restTemplate = new RestTemplate();
//
//			String url = urlx + "/Position?$filter=code eq '" + position + "'&$fromDate=" + startDate + "&toDate="
//					+ startDate + "&$select=code";
//			logger.info("url:" + url);
//			System.out.println(url);
//
//			restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(userID, password));
//
//			com.shd.Position.Position Position = restTemplate.getForObject(url, com.shd.Position.Position.class);
//
//			if (Position != null) {
//				com.shd.Position.D d = Position.getD();
//				List<com.shd.Position.Result> res = d.getResults();
//				for (com.shd.Position.Result result1 : res) {
//					ValidValue = result1.getCode();
//
//				}
//			}

			if (!ValidValue.isEmpty()) {
				if (!(ValidValue.equalsIgnoreCase(position))) {

					position = position + "<-- Not valid Position :" + position + " effective " + StartDatePrint;
				} else {

				}

			} else {

				position = position + "<-- Not a Valid for Position  :" + position + " effective " + StartDatePrint;
			}

		}
		return position;
	}

	public String ValidateJHDepLevel1_v2(List<MetaDataObj> rowData, int index, String company,
			Map<String, String> clientSystem, String isTestRun, Map<String, Map> initVal) throws Exception {

		Utility ut = new Utility();
		String DepLevel1 = ((MetaDataObj) rowData.get(index)).getFieldValue();

		if ((isTestRun.equalsIgnoreCase("YesSim") || (isTestRun.equalsIgnoreCase("NoSim")))) {
			return DepLevel1;
		}
		if (!(DepLevel1 == null)) // Already has errors
		{
			int index1 = DepLevel1.indexOf("<--");
			if (index1 != -1) {
				return DepLevel1;
			}
		}

		String position = null;
		String startDate = null;
		String StartDatePrint = null;

		for (MetaDataObj metaDataObj : rowData) {
			if (null != metaDataObj.getFieldName() && "position".equalsIgnoreCase(metaDataObj.getFieldName())) {
				position = metaDataObj.getFieldValue();
			}
			if (null != metaDataObj.getFieldName() && "start-date".equalsIgnoreCase(metaDataObj.getFieldName())) {
				startDate = metaDataObj.getFieldValue();
				StartDatePrint = startDate;
				startDate = startDate.substring(6, 10) + "-" + startDate.substring(3, 5) + "-"
						+ startDate.substring(0, 2);

			}

		}

		if (!(position == null)) // Already has errors
		{
			int index1 = position.indexOf("<--");
			if (index1 != -1) {
				return DepLevel1;
			}
		}

		String ValidValue = "";

		Map<String, Object> PositionMap = new HashMap<String, Object>();
		List<com.shd.Position.Result> PositionLst = new ArrayList();

		Metadata metaData = new Metadata();

		if (null != DepLevel1 && !DepLevel1.isEmpty()) {

			PositionMap = initVal.get("Position");

			Iterator<Map.Entry<String, Object>> itr = PositionMap.entrySet().iterator();
			while (itr.hasNext()) {
				Map.Entry<String, Object> entry = itr.next();
				PositionLst.add((com.shd.Position.Result) entry.getValue());

			}

			Date begda = null;
			Date endda = null;
			Date posstartdate = null;

			posstartdate = new SimpleDateFormat("yyyy-MM-dd").parse(startDate);

			// ---Check if legacy id is same as the legacy id?
			for (com.shd.Position.Result Position : PositionLst) {

				begda = new SimpleDateFormat("yyyy-MM-dd")
						.parse(ut.getOdataEpochiToJavaDsh(Position.getEffectiveStartDate()));
				endda = new SimpleDateFormat("yyyy-MM-dd")
						.parse(ut.getOdataEpochiToJavaDsh(Position.getEffectiveEndDate()));

				if (Position.getCode().equalsIgnoreCase(position)) {
					if ((begda.compareTo(posstartdate) <= 0) && (endda.compareTo(posstartdate) >= 0)) {

						ValidValue = Position.getCustDeptLevel1();
					}
				}
			}

			if (!ValidValue.isEmpty()) {
				if (!(ValidValue.equalsIgnoreCase(DepLevel1))) {

					DepLevel1 = DepLevel1 + "<-- Not same as in Position :" + position + " effective " + StartDatePrint
							+ " Valid Value: " + ValidValue;
				} else {

				}

			} else {

				DepLevel1 = DepLevel1 + "<-- Not a Valid for Position  :" + position + " effective " + StartDatePrint;
			}

		}
		return DepLevel1;
	}

	public String ValidateJHJobFamily_v2(List<MetaDataObj> rowData, int index, String company,
			Map<String, String> clientSystem, String isTestRun, Map<String, Map> initVal) throws Exception {

		Utility ut = new Utility();
		String JobFamily = ((MetaDataObj) rowData.get(index)).getFieldValue();

		if ((isTestRun.equalsIgnoreCase("YesSim") || (isTestRun.equalsIgnoreCase("NoSim")))) {
			return JobFamily;
		}

		if (!(JobFamily == null)) // Already has errors
		{
			int index1 = JobFamily.indexOf("<--");
			if (index1 != -1) {
				return JobFamily;
			}
		}

		String position = null;
		String startDate = null;
		String StartDatePrint = null;

		for (MetaDataObj metaDataObj : rowData) {
			if (null != metaDataObj.getFieldName() && "position".equalsIgnoreCase(metaDataObj.getFieldName())) {
				position = metaDataObj.getFieldValue();
			}
			if (null != metaDataObj.getFieldName() && "start-date".equalsIgnoreCase(metaDataObj.getFieldName())) {
				startDate = metaDataObj.getFieldValue();
				StartDatePrint = startDate;
				startDate = startDate.substring(6, 10) + "-" + startDate.substring(3, 5) + "-"
						+ startDate.substring(0, 2);
			}

		}

		if (!(position == null)) // Already has errors
		{
			int index1 = position.indexOf("<--");
			if (index1 != -1) {
				return JobFamily;
			}
		}

		String ValidValue = "";

		Map<String, Object> PositionMap = new HashMap<String, Object>();
		List<com.shd.Position.Result> PositionLst = new ArrayList();

		Metadata metaData = new Metadata();

		if (null != JobFamily && !JobFamily.isEmpty()) {

			PositionMap = initVal.get("Position");

			Iterator<Map.Entry<String, Object>> itr = PositionMap.entrySet().iterator();
			while (itr.hasNext()) {
				Map.Entry<String, Object> entry = itr.next();
				PositionLst.add((com.shd.Position.Result) entry.getValue());

			}

			Date begda = null;
			Date endda = null;
			Date posstartdate = null;

			posstartdate = new SimpleDateFormat("yyyy-MM-dd").parse(startDate);

			// ---Check if legacy id is same as the legacy id?
			for (com.shd.Position.Result Position : PositionLst) {

				begda = new SimpleDateFormat("yyyy-MM-dd")
						.parse(ut.getOdataEpochiToJavaDsh(Position.getEffectiveStartDate()));
				endda = new SimpleDateFormat("yyyy-MM-dd")
						.parse(ut.getOdataEpochiToJavaDsh(Position.getEffectiveEndDate()));

				if (Position.getCode().equalsIgnoreCase(position)) {
					if ((begda.compareTo(posstartdate) <= 0) && (endda.compareTo(posstartdate) >= 0)) {

						ValidValue = Position.getCustJobFamily();
					}
				}
			}

			if (!ValidValue.isEmpty()) {
				if (!(ValidValue.equalsIgnoreCase(JobFamily))) {

					JobFamily = JobFamily + "<-- Not same as in Position :" + position + "effective " + StartDatePrint
							+ " Valid Value: " + ValidValue;
				} else {

				}

			} else {
				JobFamily = JobFamily + "<-- Not same for Position :" + position + "effective " + StartDatePrint;
			}

			if (!(JobFamily == null)) {
				int index1 = JobFamily.indexOf("<--");
				if (index1 == -1) {
					if ((JobFamily.equalsIgnoreCase("NONE")) || (JobFamily.equalsIgnoreCase("Not Used"))) {

						JobFamily = JobFamily + "<-- Do not use NONE or Not used";

					}
				}
			}

		}
		return JobFamily;
	}

	public String ValidateJHJobFunction_v2(List<MetaDataObj> rowData, int index, String company,
			Map<String, String> clientSystem, String isTestRun, Map<String, Map> initVal) throws Exception {

		Utility ut = new Utility();
		String JobFunction = ((MetaDataObj) rowData.get(index)).getFieldValue();

		if ((isTestRun.equalsIgnoreCase("YesSim") || (isTestRun.equalsIgnoreCase("NoSim")))) {
			return JobFunction;
		}

		if (!(JobFunction == null)) // Already has errors
		{
			int index1 = JobFunction.indexOf("<--");
			if (index1 != -1) {
				return JobFunction;
			}
		}

		String position = null;
		String startDate = null;
		String StartDatePrint = null;

		for (MetaDataObj metaDataObj : rowData) {
			if (null != metaDataObj.getFieldName() && "position".equalsIgnoreCase(metaDataObj.getFieldName())) {
				position = metaDataObj.getFieldValue();
			}
			if (null != metaDataObj.getFieldName() && "start-date".equalsIgnoreCase(metaDataObj.getFieldName())) {
				startDate = metaDataObj.getFieldValue();
				StartDatePrint = startDate;
				startDate = startDate.substring(6, 10) + "-" + startDate.substring(3, 5) + "-"
						+ startDate.substring(0, 2);
			}

		}

		if (!(position == null)) // Already has errors
		{
			int index1 = position.indexOf("<--");
			if (index1 != -1) {
				return JobFunction;
			}
		}

		String ValidValue = "";

		Map<String, Object> PositionMap = new HashMap<String, Object>();
		List<com.shd.Position.Result> PositionLst = new ArrayList();

		String urlx = clientSystem.get("URL");
		String userID = clientSystem.get("USER_ID");
		String password = clientSystem.get("PWD");

		Metadata metaData = new Metadata();

		if (null != JobFunction && !JobFunction.isEmpty()) {

			PositionMap = initVal.get("Position");

			Iterator<Map.Entry<String, Object>> itr = PositionMap.entrySet().iterator();
			while (itr.hasNext()) {
				Map.Entry<String, Object> entry = itr.next();
				PositionLst.add((com.shd.Position.Result) entry.getValue());

			}

			Date begda = null;
			Date endda = null;
			Date posstartdate = null;

			posstartdate = new SimpleDateFormat("yyyy-MM-dd").parse(startDate);

			// ---Check if legacy id is same as the legacy id?
			for (com.shd.Position.Result Position : PositionLst) {

				begda = new SimpleDateFormat("yyyy-MM-dd")
						.parse(ut.getOdataEpochiToJavaDsh(Position.getEffectiveStartDate()));
				endda = new SimpleDateFormat("yyyy-MM-dd")
						.parse(ut.getOdataEpochiToJavaDsh(Position.getEffectiveEndDate()));

				if (Position.getCode().equalsIgnoreCase(position)) {
					if ((begda.compareTo(posstartdate) <= 0) && (endda.compareTo(posstartdate) >= 0)) {

						ValidValue = Position.getCustJobFunction();
					}
				}
			}

			if (!ValidValue.isEmpty()) {
				if (!(ValidValue.equalsIgnoreCase(JobFunction))) {

					JobFunction = JobFunction + "<-- Not same as in Position :" + position + " effective "
							+ StartDatePrint + " Valid Value: " + ValidValue;
				} else {

				}

			} else {
				JobFunction = JobFunction + "<-- Not a Valid for Position :" + position + " effective "
						+ StartDatePrint;
			}

			if (!(JobFunction == null)) {
				int index1 = JobFunction.indexOf("<--");
				if (index1 == -1) {
					if ((JobFunction.equalsIgnoreCase("NONE")) || (JobFunction.equalsIgnoreCase("Not Used"))) {

						JobFunction = JobFunction + "<-- Do not use NONE or Not used";

					}
				}
			}

		}
		return JobFunction;
	}

	public String ValidateJHJobClass_v2(List<MetaDataObj> rowData, int index, String company,
			Map<String, String> clientSystem, String isTestRun, Map<String, Map> initVal) throws Exception {

		Utility ut = new Utility();
		String JobCode = ((MetaDataObj) rowData.get(index)).getFieldValue();
		if ((isTestRun.equalsIgnoreCase("YesSim") || (isTestRun.equalsIgnoreCase("NoSim")))) {
			return JobCode;
		}

		if (!(JobCode == null)) // Already has errors
		{
			int index1 = JobCode.indexOf("<--");
			if (index1 != -1) {
				return JobCode;
			}
		}

		String position = null;
		String startDate = null;
		String StartDatePrint = null;

		for (MetaDataObj metaDataObj : rowData) {
			if (null != metaDataObj.getFieldName() && "position".equalsIgnoreCase(metaDataObj.getFieldName())) {
				position = metaDataObj.getFieldValue();
			}
			if (null != metaDataObj.getFieldName() && "start-date".equalsIgnoreCase(metaDataObj.getFieldName())) {
				startDate = metaDataObj.getFieldValue();
				StartDatePrint = startDate;
				startDate = startDate.substring(6, 10) + "-" + startDate.substring(3, 5) + "-"
						+ startDate.substring(0, 2);
			}

		}

		if (!(position == null)) // Already has errors
		{
			int index1 = position.indexOf("<--");
			if (index1 != -1) {
				return JobCode;
			}
		}

		String ValidJobCode = "";

		Map<String, Object> PositionMap = new HashMap<String, Object>();
		List<com.shd.Position.Result> PositionLst = new ArrayList();

		Metadata metaData = new Metadata();

		if (null != JobCode && !JobCode.isEmpty()) {

			PositionMap = initVal.get("Position");

			Iterator<Map.Entry<String, Object>> itr = PositionMap.entrySet().iterator();
			while (itr.hasNext()) {
				Map.Entry<String, Object> entry = itr.next();
				PositionLst.add((com.shd.Position.Result) entry.getValue());

			}

			Date begda = null;
			Date endda = null;
			Date posstartdate = null;

			posstartdate = new SimpleDateFormat("yyyy-MM-dd").parse(startDate);

			// ---Check if legacy id is same as the legacy id?
			for (com.shd.Position.Result Position : PositionLst) {

				begda = new SimpleDateFormat("yyyy-MM-dd")
						.parse(ut.getOdataEpochiToJavaDsh(Position.getEffectiveStartDate()));
				endda = new SimpleDateFormat("yyyy-MM-dd")
						.parse(ut.getOdataEpochiToJavaDsh(Position.getEffectiveEndDate()));

				if (Position.getCode().equalsIgnoreCase(position)) {
					if ((begda.compareTo(posstartdate) <= 0) && (endda.compareTo(posstartdate) >= 0)) {

						ValidJobCode = Position.getJobCode();
					}
				}
			}

			if (!ValidJobCode.isEmpty()) {
				if (!(ValidJobCode.equalsIgnoreCase(JobCode))) {

					JobCode = JobCode + "<-- Not same as in Position :" + position + "effective " + StartDatePrint
							+ " Valid Value: " + ValidJobCode;
				} else {

				}

			} else {
				JobCode = JobCode + "<-- Not same for Position :" + position + "effective " + StartDatePrint;
			}

		}
		return JobCode;
	}

	/*
	 * -----------------------------------------------------------------------------
	 * ------------------------------------------------
	 */
	/*
	 * Production New version Legacy Id management /*
	 * -----------------------------------------------------------------------------
	 * ------------------------------------------------
	 */

	/*
	 * -----------------------------------------------------------------------------
	 * ------------------------------------------------
	 */
	/*
	 * legacy id Department /*
	 * -----------------------------------------------------------------------------
	 * ------------------------------------------------
	 */

	public String getDepartmentID_P(List<MetaDataObj> rowData, int index, String company,
			Map<String, String> clientSystem, String isTestRun, Map<String, Map> initVal) throws Exception {

		String effectiveStartDate = null;
		String MIRAI_ID = null;
		for (MetaDataObj metaDataObj : rowData) {

			if (null != metaDataObj.getFieldName()
					&& "effectiveStartDate".equalsIgnoreCase(metaDataObj.getFieldName())) {
				effectiveStartDate = metaDataObj.getFieldValue();
			}

			if (null != metaDataObj.getFieldName() && "MIRAI_ID".equalsIgnoreCase(metaDataObj.getFieldName())) {
				MIRAI_ID = metaDataObj.getFieldValue();
			}

		}

		String epochDate = getEpoch(effectiveStartDate);

		Calendar calendar = Calendar.getInstance();
		calendar.setTimeZone(TimeZone.getTimeZone("Asia/Singapore"));
		String excode = calendar.getTimeInMillis() + getRandomString(6);
		String legacyValue = ((MetaDataObj) rowData.get(index)).getFieldValue();
		String externalCode = MIRAI_ID +"_"+ company+"_"+ "01";
		String externalName = externalCode + legacyValue ;
		
		String newValue = null;

		if (MIRAI_ID == null || MIRAI_ID.isEmpty()) {

			return (legacyValue + " <--Error: MIRAI_ID is missing in template");
		}

		if (legacyValue == null || legacyValue.isEmpty()) {

			return "<--Error: legacyValue is missing in template";
		}

		if ((isTestRun.equalsIgnoreCase("No") || (isTestRun.equalsIgnoreCase("NoSim")))) {

			String url = clientSystem.get("URL");
			String userID = clientSystem.get("USER_ID");
			String password = clientSystem.get("PWD");

			Metadata metaData = new Metadata();
			metaData.setUri(url + "/cust_Keymapping");
			metaData.setType("SFOData.cust_Keymapping");

			UpsertObject upsertObject = new UpsertObject();
			upsertObject.setMetadata(metaData);
			upsertObject.setExternalCode(externalCode);

			upsertObject.setEffectiveStartDate(epochDate);
			upsertObject.setCustLegacyID(legacyValue);
			upsertObject.setExternalName(externalName);
			upsertObject.setCustCompany(company);
			upsertObject.setCustSFID(MIRAI_ID);

			upsertObject.setCustObjectType("01");

			if (((isTestRun.equalsIgnoreCase("NoSim")))) {

				upsertObject.setCust_sim("Y");

			} else {
				upsertObject.setCust_sim("N");

			}

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			String upsertURL = url + "/upsert";

			String fetchURL = url + "/cust_Keymapping?$filter=externalCode+eq+'" + externalCode + "'";

			RestTemplate restTemplate = new RestTemplate();

			restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(userID, password));

			HttpEntity<UpsertObject> entity = new HttpEntity<UpsertObject>(upsertObject, headers);

			String custSFID = null;
			boolean isEmpty = true;

			// Step-1: Check if any values are present already for the newly generated id.
			String checkUrl = url + "/FODepartment?$format=JSON&$filter=externalCode+eq+'" + MIRAI_ID + "'";

			FieldSet checkObject = restTemplate.getForObject(checkUrl, FieldSet.class);

			isEmpty = checkObject.getD().getResults().isEmpty();

			if (!isEmpty) {
				return MIRAI_ID + "<--Error: MIRAI_ID already used in MIRAI System";
			}
			
			
			// Step-2: Post the values to update new department Id
			String result = restTemplate.postForObject(upsertURL, upsertObject, String.class);

			logger.info("FOUtility: getDepartmentID Method: Upsert result:" + result);

			// Step-3: Get new department Id using external code
			FieldSet fetchResult = restTemplate.getForObject(fetchURL, FieldSet.class);

			if (null != fetchResult.getD().getResults() && !fetchResult.getD().getResults().isEmpty()) {
				custSFID = fetchResult.getD().getResults().get(0).getCustSFID();
			}

//			// Step-3: Check if any values are present already for the newly generated id.
//			String checkUrl = url + "/FODepartment?$format=JSON&$filter=externalCode+eq+'" + custSFID + "'";
//
//			FieldSet checkObject = restTemplate.getForObject(checkUrl, FieldSet.class);
//
//			isEmpty = checkObject.getD().getResults().isEmpty();
//
//			if (!isEmpty) {
//				return custSFID + "<--Error: MIRAI_ID already used in MIRAI System";
//			}

			return custSFID;
		} else {
			return legacyValue;

		}

	}

	/*
	 * -----------------------------------------------------------------------------
	 * ------------------------------------------------
	 */
	/*
	 * legacy id Location /*
	 * -----------------------------------------------------------------------------
	 * ------------------------------------------------
	 */

// Create Location id
	public String getCreateLocationID_P(List<MetaDataObj> rowData, int index, String company,
			Map<String, String> clientSystem, String isTestRun, Map<String, Map> initVal) throws Exception {

		String effectiveStartDate = null;
		String MIRAI_ID = null;

		for (MetaDataObj metaDataObj : rowData) {

			if (null != metaDataObj.getFieldName() && "start-date".equalsIgnoreCase(metaDataObj.getFieldName())) {
				effectiveStartDate = metaDataObj.getFieldValue();
			}

			if (null != metaDataObj.getFieldName() && "MIRAI_ID".equalsIgnoreCase(metaDataObj.getFieldName())) {
				MIRAI_ID = metaDataObj.getFieldValue();
			}

		}

		String epochDate = getEpoch(effectiveStartDate);

//		logger.info("FOUtility: getDepartmentID Method: epochDate:" + epochDate);

		Calendar calendar = Calendar.getInstance();
		calendar.setTimeZone(TimeZone.getTimeZone("Asia/Singapore"));
		String excode = calendar.getTimeInMillis() + getRandomString(6);
		String legacyValue = ((MetaDataObj) rowData.get(index)).getFieldValue();
		String externalCode = MIRAI_ID +"_"+ company+"_"+ "03";
		String externalName = externalCode + legacyValue ;

		String legacyValue = ((MetaDataObj) rowData.get(index)).getFieldValue();
		String newValue = null;

		if (MIRAI_ID == null || MIRAI_ID.isEmpty()) {

			return (legacyValue + " <--Error: MIRAI_ID is missing in template");
		}

		if (legacyValue == null || legacyValue.isEmpty()) {

			return "<--Error: legacyValue is missing in template";
		}

		if ((isTestRun.equalsIgnoreCase("No") || (isTestRun.equalsIgnoreCase("NoSim")))) {
//		String url = "https://api12preview.sapsf.eu/odata/v2/";
//		String url = "https://apisalesdemo2.successfactors.eu/odata/v2/";
			String url = clientSystem.get("URL");
			String userID = clientSystem.get("USER_ID");
			String password = clientSystem.get("PWD");

			Metadata metaData = new Metadata();
			metaData.setUri(url + "/cust_Keymapping");
			metaData.setType("SFOData.cust_Keymapping");

			UpsertObject upsertObject = new UpsertObject();
			upsertObject.setMetadata(metaData);
			upsertObject.setExternalCode(externalCode);
//		upsertObject.setEffectiveStartDate("/Date(946665000000)/");
			upsertObject.setEffectiveStartDate(epochDate);
			upsertObject.setCustLegacyID(legacyValue);
			upsertObject.setExternalName(externalName);
			upsertObject.setCustCompany(company);
			upsertObject.setCustSFID(MIRAI_ID);
			upsertObject.setCustObjectType("03");

			if (((isTestRun.equalsIgnoreCase("NoSim")))) {

				upsertObject.setCust_sim("Y");

			} else {
				upsertObject.setCust_sim("N");
			}

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
//		RestTemplate postTemplate = new RestTemplate();
//		postTemplate.getInterceptors().add(new BasicAuthorizationInterceptor("sfadmin@SFPART046830", "Welcome1"));

			String upsertURL = url + "/upsert";

			String fetchURL = url + "/cust_Keymapping?$filter=externalCode+eq+'" + externalCode + "'";

			RestTemplate restTemplate = new RestTemplate();
//		restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor("VKUMAR@shiseidocoT1", "Welcome@3"));
//		restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor("sfadmin@SFPART046830", "Welcome1"));
			restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(userID, password));

			HttpEntity<UpsertObject> entity = new HttpEntity<UpsertObject>(upsertObject, headers);

//		Map <String, String> dummy = new HashMap<String, String>();
			String custSFID = null;
			boolean isEmpty = true;

			// Step-1: Check if any values are present already for the newly generated id.
			String checkUrl = url + "/FOLocation?$format=JSON&$filter=externalCode+eq+'" + MIRAI_ID + "'";

			FieldSet checkObject = restTemplate.getForObject(checkUrl, FieldSet.class);

//						System.out.println("checkObject D : " + checkObject.getD().getResults().isEmpty());
			isEmpty = checkObject.getD().getResults().isEmpty();

			if (!isEmpty) {
				return MIRAI_ID + "<--Error: MIRAI_ID already used in MIRAI System";
			}

						
						
			// Step-2: Post the values to get generate new department Id
			String result = restTemplate.postForObject(upsertURL, upsertObject, String.class);

			logger.info("FOUtility: getPosclassID Method: Upsert result:" + result);

			// Step-3: Get new department Id using external code
			FieldSet fetchResult = restTemplate.getForObject(fetchURL, FieldSet.class);
//			System.out.println("fetchResult : " + fetchResult.getD().getResults().get(0).getCustSFID());

			if (null != fetchResult.getD().getResults() && !fetchResult.getD().getResults().isEmpty()) {
				custSFID = fetchResult.getD().getResults().get(0).getCustSFID();
			}

//			// Step-3: Check if any values are present already for the newly generated id.
//			String checkUrl = url + "/FOLocation?$format=JSON&$filter=externalCode+eq+'" + custSFID + "'";
//
//			FieldSet checkObject = restTemplate.getForObject(checkUrl, FieldSet.class);
//
////			System.out.println("checkObject D : " + checkObject.getD().getResults().isEmpty());
//			isEmpty = checkObject.getD().getResults().isEmpty();
//
//			if (!isEmpty) {
//				return custSFID + "<--Error: MIRAI_ID already used in MIRAI System";
//			}

			return custSFID;
		} else {

			return legacyValue;
		}
	}

	/*
	 * -----------------------------------------------------------------------------
	 * ------------------------------------------------
	 */
	/*
	 * legacy id Position /*
	 * -----------------------------------------------------------------------------
	 * ------------------------------------------------
	 */
	
	
	// Get Pos id
		public String getPosID_P(List<MetaDataObj> rowData, int index, String company, Map<String, String> clientSystem,
				String isTestRun, Map<String, Map> initVal) throws Exception {

//			logger.info("FOUtility: Inside getDepartmentID Method");
			String effectiveStartDate = null;
			String MIRAI_ID=null;

			for (MetaDataObj metaDataObj : rowData) {
//				logger.info("FOUtility: Inside getDepartmentID Method: metaDataObj.getFieldName():"
//						+ metaDataObj.getFieldName());
				if (null != metaDataObj.getFieldName()
						&& "effectiveStartDate".equalsIgnoreCase(metaDataObj.getFieldName())) {
					effectiveStartDate = metaDataObj.getFieldValue();
				}
				
				if (null != metaDataObj.getFieldName() && "MIRAI_ID".equalsIgnoreCase(metaDataObj.getFieldName())) {
					MIRAI_ID = metaDataObj.getFieldValue();
				}
				
			}

//			logger.info("FOUtility: getDepartmentID Method: effectiveStartDate:" + effectiveStartDate);

			String epochDate = getEpoch(effectiveStartDate);

//			logger.info("FOUtility: getDepartmentID Method: epochDate:" + epochDate);
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeZone(TimeZone.getTimeZone("Asia/Singapore"));
			String excode = calendar.getTimeInMillis() + getRandomString(6);
			String legacyValue = ((MetaDataObj) rowData.get(index)).getFieldValue();
			String externalCode = MIRAI_ID +"_"+ company+"_"+ "09";
			String externalName = externalCode + legacyValue ;
			
			String newValue = null;
			
			if (MIRAI_ID==null || MIRAI_ID.isEmpty()) {
				
				return (legacyValue+" <--Error: MIRAI_ID is missing in template");
			}

			if (legacyValue==null || legacyValue.isEmpty()) {
				
				return "<--Error: legacyValue is missing in template";
			}


			if ((isTestRun.equalsIgnoreCase("No") || (isTestRun.equalsIgnoreCase("NoSim")))) {

				String url = clientSystem.get("URL");
				String userID = clientSystem.get("USER_ID");
				String password = clientSystem.get("PWD");

				Metadata metaData = new Metadata();
				metaData.setUri(url + "/cust_Keymapping");
				metaData.setType("SFOData.cust_Keymapping");

				UpsertObject upsertObject = new UpsertObject();
				upsertObject.setMetadata(metaData);
				upsertObject.setExternalCode(externalCode);
//			upsertObject.setEffectiveStartDate("/Date(946665000000)/");
				upsertObject.setEffectiveStartDate(epochDate);
				upsertObject.setCustLegacyID(legacyValue);
				upsertObject.setExternalName(externalName);
				upsertObject.setCustCompany(company);
				upsertObject.setCustSFID(MIRAI_ID);

				upsertObject.setCustObjectType("09");

				if (((isTestRun.equalsIgnoreCase("NoSim")))) {

					upsertObject.setCust_sim("Y");

				} else {
					upsertObject.setCust_sim("N");
				}

				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);
//			RestTemplate postTemplate = new RestTemplate();
//			postTemplate.getInterceptors().add(new BasicAuthorizationInterceptor("sfadmin@SFPART046830", "Welcome1"));

				String upsertURL = url + "/upsert";

				String fetchURL = url + "/cust_Keymapping?$filter=externalCode+eq+'" + externalCode + "'";

				RestTemplate restTemplate = new RestTemplate();
//			restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor("VKUMAR@shiseidocoT1", "Welcome@3"));
//			restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor("sfadmin@SFPART046830", "Welcome1"));
				restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(userID, password));

				HttpEntity<UpsertObject> entity = new HttpEntity<UpsertObject>(upsertObject, headers);

//			Map <String, String> dummy = new HashMap<String, String>();
				String custSFID = null;
				boolean isEmpty = true;
				
					
				// Step-1: Check if any values are present already for the newly generated id.
				String checkUrl = url + "/Position?$format=JSON&$filter=code+eq+'" + MIRAI_ID + "'";

				FieldSet checkObject = restTemplate.getForObject(checkUrl, FieldSet.class);

//			System.out.println("checkObject D : " + checkObject.getD().getResults().isEmpty());
				isEmpty = checkObject.getD().getResults().isEmpty();

				if (!isEmpty) {
					return MIRAI_ID+"<--Error: MIRAI_ID already used in MIRAI System";
				}
				
				// Step-2: Post the values to get generate new department Id
					String result = restTemplate.postForObject(upsertURL, upsertObject, String.class);

					logger.info("FOUtility: getPosclassID Method: Upsert result:" + result);

					// Step-3: Get new department Id using external code
					FieldSet fetchResult = restTemplate.getForObject(fetchURL, FieldSet.class);
//				System.out.println("fetchResult : " + fetchResult.getD().getResults().get(0).getCustSFID());

					if (null != fetchResult.getD().getResults() && !fetchResult.getD().getResults().isEmpty()) {
						custSFID = fetchResult.getD().getResults().get(0).getCustSFID();
					}

//					// Step-3: Check if any values are present already for the newly generated id.
//					String checkUrl = url + "/Position?$format=JSON&$filter=code+eq+'" + custSFID + "'";
//
//					FieldSet checkObject = restTemplate.getForObject(checkUrl, FieldSet.class);
//
////				System.out.println("checkObject D : " + checkObject.getD().getResults().isEmpty());
//					isEmpty = checkObject.getD().getResults().isEmpty();
//
//					if (!isEmpty) {
//						return custSFID+"<--Error: MIRAI_ID already used in MIRAI System";
//					}

				return custSFID;
			} else {

				return legacyValue;
			}
		}

		/*
		 * -----------------------------------------------------------------------------
		 * ------------------------------------------------
		 */
		/*
		 * legacy id Job Classification /*
		 * -----------------------------------------------------------------------------
		 * ------------------------------------------------
		 */
		
		
		// Get job id
		public String getJobClassID_P(List<MetaDataObj> rowData, int index, String company, Map<String, String> clientSystem,
				String isTestRun, Map<String, Map> initVal) throws Exception {

//			logger.info("FOUtility: Inside getDepartmentID Method");
			String effectiveStartDate = null;
			String MIRAI_ID=null;


			for (MetaDataObj metaDataObj : rowData) {
//				logger.info("FOUtility: Inside getDepartmentID Method: metaDataObj.getFieldName():"
//						+ metaDataObj.getFieldName());
				if (null != metaDataObj.getFieldName()
						&& "effectiveStartDate".equalsIgnoreCase(metaDataObj.getFieldName())) {
					effectiveStartDate = metaDataObj.getFieldValue();
				}
				
				if (null != metaDataObj.getFieldName() && "MIRAI_ID".equalsIgnoreCase(metaDataObj.getFieldName())) {
					MIRAI_ID = metaDataObj.getFieldValue();
				}
			
				
			}

//			logger.info("FOUtility: getDepartmentID Method: effectiveStartDate:" + effectiveStartDate);

			String epochDate = getEpoch(effectiveStartDate);

//			logger.info("FOUtility: getDepartmentID Method: epochDate:" + epochDate);

			Calendar calendar = Calendar.getInstance();
			calendar.setTimeZone(TimeZone.getTimeZone("Asia/Singapore"));
			String excode = calendar.getTimeInMillis() + getRandomString(6);
			String legacyValue = ((MetaDataObj) rowData.get(index)).getFieldValue();
			String externalCode = MIRAI_ID +"_"+ company+"_"+ "02";
			String externalName = externalCode + legacyValue ;
			
			String newValue = null;
			
			if (MIRAI_ID==null || MIRAI_ID.isEmpty()) {
				
				return (legacyValue+" <--Error: MIRAI_ID is missing in template");
			}

			if (legacyValue==null || legacyValue.isEmpty()) {
				
				return "<--Error: legacyValue is missing in template";
			}


			if ((isTestRun.equalsIgnoreCase("No") || (isTestRun.equalsIgnoreCase("NoSim")))) {
//			String url = "https://api12preview.sapsf.eu/odata/v2/";
//			String url = "https://apisalesdemo2.successfactors.eu/odata/v2/";
				String url = clientSystem.get("URL");
				String userID = clientSystem.get("USER_ID");
				String password = clientSystem.get("PWD");

				Metadata metaData = new Metadata();
				metaData.setUri(url + "/cust_Keymapping");
				metaData.setType("SFOData.cust_Keymapping");

				UpsertObject upsertObject = new UpsertObject();
				upsertObject.setMetadata(metaData);
				upsertObject.setExternalCode(externalCode);
//			upsertObject.setEffectiveStartDate("/Date(946665000000)/");
				upsertObject.setEffectiveStartDate(epochDate);
				upsertObject.setCustLegacyID(legacyValue);
				upsertObject.setExternalName(externalName);
				upsertObject.setCustCompany(company);
				upsertObject.setCustSFID(MIRAI_ID);
				upsertObject.setCustObjectType("02");

				if (((isTestRun.equalsIgnoreCase("NoSim")))) {

					upsertObject.setCust_sim("Y");

				} else {
					upsertObject.setCust_sim("N");
				}

				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);
//			RestTemplate postTemplate = new RestTemplate();
//			postTemplate.getInterceptors().add(new BasicAuthorizationInterceptor("sfadmin@SFPART046830", "Welcome1"));

				String upsertURL = url + "/upsert";

				String fetchURL = url + "/cust_Keymapping?$filter=externalCode+eq+'" + externalCode + "'";

				RestTemplate restTemplate = new RestTemplate();
//			restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor("VKUMAR@shiseidocoT1", "Welcome@3"));
//			restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor("sfadmin@SFPART046830", "Welcome1"));
				restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(userID, password));

				HttpEntity<UpsertObject> entity = new HttpEntity<UpsertObject>(upsertObject, headers);

//			Map <String, String> dummy = new HashMap<String, String>();
				String custSFID = null;
				boolean isEmpty = true;
				
				// Step-1: Check if any values are present already for the newly generated id.
				String checkUrl = url + "/FOJobCode?$format=JSON&$filter=externalCode+eq+'" + MIRAI_ID + "'";

				FieldSet checkObject = restTemplate.getForObject(checkUrl, FieldSet.class);

//			System.out.println("checkObject D : " + checkObject.getD().getResults().isEmpty());
				isEmpty = checkObject.getD().getResults().isEmpty();

				if (!isEmpty) {
					return MIRAI_ID+"<--Error: MIRAI_ID already used in MIRAI System";
				}

				
				
					// Step-2: Post the values to get generate new department Id
					String result = restTemplate.postForObject(upsertURL, upsertObject, String.class);

					logger.info("FOUtility: getJobclassID Method: Upsert result:" + result);

					// Step-3: Get new department Id using external code
					FieldSet fetchResult = restTemplate.getForObject(fetchURL, FieldSet.class);
//				System.out.println("fetchResult : " + fetchResult.getD().getResults().get(0).getCustSFID());

					if (null != fetchResult.getD().getResults() && !fetchResult.getD().getResults().isEmpty()) {
						custSFID = fetchResult.getD().getResults().get(0).getCustSFID();
					}

				

				return custSFID;
			} else {

				return legacyValue;
			}
		}
		

		/*
		 * -----------------------------------------------------------------------------
		 * ------------------------------------------------
		 */
		/*
		 * legacy id PayGrade /*
		 * -----------------------------------------------------------------------------
		 * ------------------------------------------------
		 */
		
		// Get Grade id
		public String getCreateGradeID_P(List<MetaDataObj> rowData, int index, String company,
				Map<String, String> clientSystem, String isTestRun, Map<String, Map> initVal) throws Exception {

//				logger.info("FOUtility: Inside getDepartmentID Method");
			String effectiveStartDate = null;
			String MIRAI_ID=null;


			for (MetaDataObj metaDataObj : rowData) {
//					logger.info("FOUtility: Inside getDepartmentID Method: metaDataObj.getFieldName():"
//							+ metaDataObj.getFieldName());
				if (null != metaDataObj.getFieldName() && "start-date".equalsIgnoreCase(metaDataObj.getFieldName())) {
					effectiveStartDate = metaDataObj.getFieldValue();
				}
				
				if (null != metaDataObj.getFieldName() && "MIRAI_ID".equalsIgnoreCase(metaDataObj.getFieldName())) {
					MIRAI_ID = metaDataObj.getFieldValue();
				}
			}

//				logger.info("FOUtility: getDepartmentID Method: effectiveStartDate:" + effectiveStartDate);

			String epochDate = getEpoch(effectiveStartDate);

//				logger.info("FOUtility: getDepartmentID Method: epochDate:" + epochDate);
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeZone(TimeZone.getTimeZone("Asia/Singapore"));
			String excode = calendar.getTimeInMillis() + getRandomString(6);
			String legacyValue = ((MetaDataObj) rowData.get(index)).getFieldValue();
			String externalCode = MIRAI_ID +"_"+ company+"_"+ "06";
			String externalName = externalCode + legacyValue ;
			
			String newValue = null;
			
			if (MIRAI_ID==null || MIRAI_ID.isEmpty()) {
				
				return (legacyValue+" <--Error: MIRAI_ID is missing in template");
			}

			if (legacyValue==null || legacyValue.isEmpty()) {
				
				return "<--Error: legacyValue is missing in template";
			}


			if ((isTestRun.equalsIgnoreCase("No") || (isTestRun.equalsIgnoreCase("NoSim")))) {
//				String url = "https://api12preview.sapsf.eu/odata/v2/";
//				String url = "https://apisalesdemo2.successfactors.eu/odata/v2/";
				String url = clientSystem.get("URL");
				String userID = clientSystem.get("USER_ID");
				String password = clientSystem.get("PWD");

				Metadata metaData = new Metadata();
				metaData.setUri(url + "/cust_Keymapping");
				metaData.setType("SFOData.cust_Keymapping");

				UpsertObject upsertObject = new UpsertObject();
				upsertObject.setMetadata(metaData);
				upsertObject.setExternalCode(externalCode);
//				upsertObject.setEffectiveStartDate("/Date(946665000000)/");
				upsertObject.setEffectiveStartDate(epochDate);
				upsertObject.setCustLegacyID(legacyValue);
				upsertObject.setExternalName(externalName);
				upsertObject.setCustCompany(company);
				upsertObject.setCustSFID(MIRAI_ID);

				upsertObject.setCustObjectType("06");

				if (((isTestRun.equalsIgnoreCase("NoSim")))) {

					upsertObject.setCust_sim("Y");

				} else {
					upsertObject.setCust_sim("N");
				}

				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);
//				RestTemplate postTemplate = new RestTemplate();
//				postTemplate.getInterceptors().add(new BasicAuthorizationInterceptor("sfadmin@SFPART046830", "Welcome1"));

				String upsertURL = url + "/upsert";

				String fetchURL = url + "/cust_Keymapping?$filter=externalCode+eq+'" + externalCode + "'";

				RestTemplate restTemplate = new RestTemplate();
//				restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor("VKUMAR@shiseidocoT1", "Welcome@3"));
//				restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor("sfadmin@SFPART046830", "Welcome1"));
				restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(userID, password));

				HttpEntity<UpsertObject> entity = new HttpEntity<UpsertObject>(upsertObject, headers);

//				Map <String, String> dummy = new HashMap<String, String>();
				String custSFID = null;
				boolean isEmpty = true;
				
					
				
				// Step-1: Check if any values are present already for the newly generated id.
				String checkUrl = url + "/FOPayGrade?$format=JSON&$filter=externalCode+eq+'" + MIRAI_ID + "'";

				FieldSet checkObject = restTemplate.getForObject(checkUrl, FieldSet.class);

//				System.out.println("checkObject D : " + checkObject.getD().getResults().isEmpty());
				isEmpty = checkObject.getD().getResults().isEmpty();

				if (!isEmpty) {
					return MIRAI_ID+"<--Error: MIRAI_ID already used in MIRAI System";
				}

				// Step-2: Post the values to get generate new department Id
					String result = restTemplate.postForObject(upsertURL, upsertObject, String.class);

					logger.info("FOUtility: getPosclassID Method: Upsert result:" + result);

					// Step-3: Get new department Id using external code
					FieldSet fetchResult = restTemplate.getForObject(fetchURL, FieldSet.class);
//					System.out.println("fetchResult : " + fetchResult.getD().getResults().get(0).getCustSFID());

					if (null != fetchResult.getD().getResults() && !fetchResult.getD().getResults().isEmpty()) {
						custSFID = fetchResult.getD().getResults().get(0).getCustSFID();
					}

//					// Step-3: Check if any values are present already for the newly generated id.
//					String checkUrl = url + "/FOPayGrade?$format=JSON&$filter=externalCode+eq+'" + custSFID + "'";
//
//					FieldSet checkObject = restTemplate.getForObject(checkUrl, FieldSet.class);
//
////					System.out.println("checkObject D : " + checkObject.getD().getResults().isEmpty());
//					isEmpty = checkObject.getD().getResults().isEmpty();
//
//					if (!isEmpty) {
//						return custSFID+"<--Error: MIRAI_ID already used in MIRAI System";
//					}

				return custSFID;
			} else {

				return legacyValue;
			}
		}


		
		
		
		/*
		 * -----------------------------------------------------------------------------
		 * ------------------------------------------------
		 */
		/*
		 * legacy id PayRange /*
		 * -----------------------------------------------------------------------------
		 * ------------------------------------------------
		 */
		

		// Create PayRange ID
		
		
		public String getCreatePayRangeID_P(List<MetaDataObj> rowData, int index, String company,
				Map<String, String> clientSystem, String isTestRun, Map<String, Map> initVal) throws Exception {

//					logger.info("FOUtility: Inside getDepartmentID Method");
			String effectiveStartDate = null;
			String MIRAI_ID=null;


			for (MetaDataObj metaDataObj : rowData) {
//						logger.info("FOUtility: Inside getDepartmentID Method: metaDataObj.getFieldName():"
//								+ metaDataObj.getFieldName());
				if (null != metaDataObj.getFieldName() && "start-date".equalsIgnoreCase(metaDataObj.getFieldName())) {
					effectiveStartDate = metaDataObj.getFieldValue();
				}
				
				if (null != metaDataObj.getFieldName() && "MIRAI_ID".equalsIgnoreCase(metaDataObj.getFieldName())) {
					MIRAI_ID = metaDataObj.getFieldValue();
				}

				
			}

//					logger.info("FOUtility: getDepartmentID Method: effectiveStartDate:" + effectiveStartDate);

			String epochDate = getEpoch(effectiveStartDate);

//					logger.info("FOUtility: getDepartmentID Method: epochDate:" + epochDate);
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeZone(TimeZone.getTimeZone("Asia/Singapore"));
			String excode = calendar.getTimeInMillis() + getRandomString(6);
			String legacyValue = ((MetaDataObj) rowData.get(index)).getFieldValue();
			String externalCode = MIRAI_ID +"_"+ company+"_"+ "08";
			String externalName = externalCode + legacyValue ;

			
			String newValue = null;

			if (MIRAI_ID==null || MIRAI_ID.isEmpty()) {
				
				return (legacyValue+" <--Error: MIRAI_ID is missing in template");
			}

			if (legacyValue==null || legacyValue.isEmpty()) {
				
				return "<--Error: legacyValue is missing in template";
			}

			
			if ((isTestRun.equalsIgnoreCase("No") || (isTestRun.equalsIgnoreCase("NoSim")))) {
//					String url = "https://api12preview.sapsf.eu/odata/v2/";
//					String url = "https://apisalesdemo2.successfactors.eu/odata/v2/";
				String url = clientSystem.get("URL");
				String userID = clientSystem.get("USER_ID");
				String password = clientSystem.get("PWD");

				Metadata metaData = new Metadata();
				metaData.setUri(url + "/cust_Keymapping");
				metaData.setType("SFOData.cust_Keymapping");

				UpsertObject upsertObject = new UpsertObject();
				upsertObject.setMetadata(metaData);
				upsertObject.setExternalCode(externalCode);
//					upsertObject.setEffectiveStartDate("/Date(946665000000)/");
				upsertObject.setEffectiveStartDate(epochDate);
				upsertObject.setCustLegacyID(legacyValue);
				upsertObject.setExternalName(externalName);
				upsertObject.setCustCompany(company);
				upsertObject.setCustSFID(MIRAI_ID);

				upsertObject.setCustObjectType("08");

				if (((isTestRun.equalsIgnoreCase("NoSim")))) {

					upsertObject.setCust_sim("Y");

				} else {
					upsertObject.setCust_sim("N");
				}

				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);
//					RestTemplate postTemplate = new RestTemplate();
//					postTemplate.getInterceptors().add(new BasicAuthorizationInterceptor("sfadmin@SFPART046830", "Welcome1"));

				String upsertURL = url + "/upsert";

				String fetchURL = url + "/cust_Keymapping?$filter=externalCode+eq+'" + externalCode + "'";

				RestTemplate restTemplate = new RestTemplate();
//					restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor("VKUMAR@shiseidocoT1", "Welcome@3"));
//					restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor("sfadmin@SFPART046830", "Welcome1"));
				restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(userID, password));

				HttpEntity<UpsertObject> entity = new HttpEntity<UpsertObject>(upsertObject, headers);

//					Map <String, String> dummy = new HashMap<String, String>();
				String custSFID = null;
				boolean isEmpty = true;
				
				
				// Step-1: Check if any values are present already for the newly generated id.
				String checkUrl = url + "/FOPayRange?$format=JSON&$filter=externalCode+eq+'" + MIRAI_ID + "'";

				FieldSet checkObject = restTemplate.getForObject(checkUrl, FieldSet.class);

//					System.out.println("checkObject D : " + checkObject.getD().getResults().isEmpty());
				isEmpty = checkObject.getD().getResults().isEmpty();

				if (!isEmpty) {
					return MIRAI_ID+"<--Error: MIRAI_ID already used in MIRAI System";
				}
				
					// Step-2: Post the values to get generate new department Id
					String result = restTemplate.postForObject(upsertURL, upsertObject, String.class);

					logger.info("FOUtility: getPosclassID Method: Upsert result:" + result);

					// Step-3: Get new department Id using external code
					FieldSet fetchResult = restTemplate.getForObject(fetchURL, FieldSet.class);
//						System.out.println("fetchResult : " + fetchResult.getD().getResults().get(0).getCustSFID());

					if (null != fetchResult.getD().getResults() && !fetchResult.getD().getResults().isEmpty()) {
						custSFID = fetchResult.getD().getResults().get(0).getCustSFID();
					}

//					// Step-3: Check if any values are present already for the newly generated id.
//					String checkUrl = url + "/FOPayRange?$format=JSON&$filter=externalCode+eq+'" + custSFID + "'";
//
//					FieldSet checkObject = restTemplate.getForObject(checkUrl, FieldSet.class);
//
////						System.out.println("checkObject D : " + checkObject.getD().getResults().isEmpty());
//					isEmpty = checkObject.getD().getResults().isEmpty();
//
//					if (!isEmpty) {
//						return custSFID+"<--Error: MIRAI_ID already used in MIRAI System";
//					}

				return custSFID;
			} else {

				return legacyValue;
			}
		}

	
		/*
		 * -----------------------------------------------------------------------------
		 * ------------------------------------------------
		 */
		/*
		 * legacy id Division /*
		 * -----------------------------------------------------------------------------
		 * ------------------------------------------------
		 */
		
		public String getCreateDivisionID_P(List<MetaDataObj> rowData, int index, String company,
				Map<String, String> clientSystem, String isTestRun, Map<String, Map> initVal) throws Exception {

//					logger.info("FOUtility: Inside getDepartmentID Method");
			String effectiveStartDate = null;
			String MIRAI_ID=null;


			for (MetaDataObj metaDataObj : rowData) {
//						logger.info("FOUtility: Inside getDepartmentID Method: metaDataObj.getFieldName():"
//								+ metaDataObj.getFieldName());
				if (null != metaDataObj.getFieldName() && "start-date".equalsIgnoreCase(metaDataObj.getFieldName())) {
					effectiveStartDate = metaDataObj.getFieldValue();
				}
				
				if (null != metaDataObj.getFieldName() && "MIRAI_ID".equalsIgnoreCase(metaDataObj.getFieldName())) {
					MIRAI_ID = metaDataObj.getFieldValue();
				}

				
			}

//					logger.info("FOUtility: getDepartmentID Method: effectiveStartDate:" + effectiveStartDate);

			String epochDate = getEpoch(effectiveStartDate);

//					logger.info("FOUtility: getDepartmentID Method: epochDate:" + epochDate);
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeZone(TimeZone.getTimeZone("Asia/Singapore"));
			String excode = calendar.getTimeInMillis() + getRandomString(6);
			String legacyValue = ((MetaDataObj) rowData.get(index)).getFieldValue();

			String externalCode = MIRAI_ID +"_"+ company+"_"+ "11";
			String externalName = externalCode + legacyValue ;

			
			String newValue = null;

			if (MIRAI_ID==null || MIRAI_ID.isEmpty()) {
				
				return (legacyValue+" <--Error: MIRAI_ID is missing in template");
			}

			if (legacyValue==null || legacyValue.isEmpty()) {
				
				return "<--Error: legacyValue is missing in template";
			}

			
			if ((isTestRun.equalsIgnoreCase("No") || (isTestRun.equalsIgnoreCase("NoSim")))) {
//					String url = "https://api12preview.sapsf.eu/odata/v2/";
//					String url = "https://apisalesdemo2.successfactors.eu/odata/v2/";
				String url = clientSystem.get("URL");
				String userID = clientSystem.get("USER_ID");
				String password = clientSystem.get("PWD");

				Metadata metaData = new Metadata();
				metaData.setUri(url + "/cust_Keymapping");
				metaData.setType("SFOData.cust_Keymapping");

				UpsertObject upsertObject = new UpsertObject();
				upsertObject.setMetadata(metaData);
				upsertObject.setExternalCode(externalCode);
//					upsertObject.setEffectiveStartDate("/Date(946665000000)/");
				upsertObject.setEffectiveStartDate(epochDate);
				upsertObject.setCustLegacyID(legacyValue);
				upsertObject.setExternalName(externalName);
				upsertObject.setCustCompany(company);
				upsertObject.setCustSFID(MIRAI_ID);

				upsertObject.setCustObjectType("11");

				if (((isTestRun.equalsIgnoreCase("NoSim")))) {

					upsertObject.setCust_sim("Y");

				} else {
					upsertObject.setCust_sim("N");
				}

				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);
//					RestTemplate postTemplate = new RestTemplate();
//					postTemplate.getInterceptors().add(new BasicAuthorizationInterceptor("sfadmin@SFPART046830", "Welcome1"));

				String upsertURL = url + "/upsert";

				String fetchURL = url + "/cust_Keymapping?$filter=externalCode+eq+'" + externalCode + "'";

				RestTemplate restTemplate = new RestTemplate();
//					restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor("VKUMAR@shiseidocoT1", "Welcome@3"));
//					restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor("sfadmin@SFPART046830", "Welcome1"));
				restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(userID, password));

				HttpEntity<UpsertObject> entity = new HttpEntity<UpsertObject>(upsertObject, headers);

//					Map <String, String> dummy = new HashMap<String, String>();
				String custSFID = null;
				boolean isEmpty = true;
				
				
				// Step-1: Check if any values are present already for the newly generated id.
				String checkUrl = url + "/FODivision?$format=JSON&$filter=externalCode+eq+'" + MIRAI_ID + "'";

				FieldSet checkObject = restTemplate.getForObject(checkUrl, FieldSet.class);

//					System.out.println("checkObject D : " + checkObject.getD().getResults().isEmpty());
				isEmpty = checkObject.getD().getResults().isEmpty();

				if (!isEmpty) {
					return MIRAI_ID+"<--Error: MIRAI_ID already used in MIRAI System";
				}
				
					// Step-2: Post the values to get generate new department Id
					String result = restTemplate.postForObject(upsertURL, upsertObject, String.class);

					logger.info("FOUtility: getPosclassID Method: Upsert result:" + result);

					// Step-3: Get new department Id using external code
					FieldSet fetchResult = restTemplate.getForObject(fetchURL, FieldSet.class);
//						System.out.println("fetchResult : " + fetchResult.getD().getResults().get(0).getCustSFID());

					if (null != fetchResult.getD().getResults() && !fetchResult.getD().getResults().isEmpty()) {
						custSFID = fetchResult.getD().getResults().get(0).getCustSFID();
					}

//					// Step-3: Check if any values are present already for the newly generated id.
//					String checkUrl = url + "/FOPayRange?$format=JSON&$filter=externalCode+eq+'" + custSFID + "'";
//
//					FieldSet checkObject = restTemplate.getForObject(checkUrl, FieldSet.class);
//
////						System.out.println("checkObject D : " + checkObject.getD().getResults().isEmpty());
//					isEmpty = checkObject.getD().getResults().isEmpty();
//
//					if (!isEmpty) {
//						return custSFID+"<--Error: MIRAI_ID already used in MIRAI System";
//					}

				return custSFID;
			} else {

				return legacyValue;
			}
		}

		
}