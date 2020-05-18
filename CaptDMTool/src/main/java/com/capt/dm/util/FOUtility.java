package com.capt.dm.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TimeZone;

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
import com.shd.keymap.D;
import com.shd.keymap.KeyMap;
import com.shd.keymap.Result;

public class FOUtility {

	private static final Logger logger = LoggerFactory.getLogger(FOUtility.class);

	public String getSplitRight(List<MetaDataObj> rowData, int index, String company, Map<String, String> clientSystem,String isTestRun)
			throws Exception {

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

	public String getSplitLeft(List<MetaDataObj> rowData, int index, String company, Map<String, String> clientSystem,String isTestRun)
			throws Exception {

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
			Map<String, String> clientSystem,String isTestRun) throws Exception {

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
		String externalCode = getRandomString(6);
		String externalName = getRandomString(6);
		String legacyValue = ((MetaDataObj) rowData.get(index)).getFieldValue();
		String newValue = null;

		if (isTestRun.equalsIgnoreCase("No")) {
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
		upsertObject.setCustObjectType("01");

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
		}
		else {
			return legacyValue;

		}


	}
	
	
	// Get job id	
	public String getJobClassID(List<MetaDataObj> rowData, int index, String company,
			Map<String, String> clientSystem,String isTestRun) throws Exception {

		
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
		String externalCode = getRandomString(6);
		String externalName = getRandomString(6);
		String legacyValue = ((MetaDataObj) rowData.get(index)).getFieldValue();
		String newValue = null;

		if (!company.equalsIgnoreCase("99")) {
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
	}else {
		
		return legacyValue;
	}
	}
	
	
	// Get Pos id	
	public String getPosID(List<MetaDataObj> rowData, int index, String company,
			Map<String, String> clientSystem,String isTestRun) throws Exception {

		
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
		String externalCode = getRandomString(6);
		String externalName = getRandomString(6);
		String legacyValue = ((MetaDataObj) rowData.get(index)).getFieldValue();
		String newValue = null;

		if (isTestRun.equalsIgnoreCase("No")) {
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
	}else {
		
		return legacyValue;
	}
	}
// PayGrade
	
	// Get Grade id	
		public String getCreateGradeID(List<MetaDataObj> rowData, int index, String company,
				Map<String, String> clientSystem,String isTestRun) throws Exception {

			
//			logger.info("FOUtility: Inside getDepartmentID Method");
			String effectiveStartDate = null;
		
			for (MetaDataObj metaDataObj : rowData) {
//				logger.info("FOUtility: Inside getDepartmentID Method: metaDataObj.getFieldName():"
//						+ metaDataObj.getFieldName());
				if (null != metaDataObj.getFieldName()
						&& "start-date".equalsIgnoreCase(metaDataObj.getFieldName())) {
					effectiveStartDate = metaDataObj.getFieldValue();
				}
			}

//			logger.info("FOUtility: getDepartmentID Method: effectiveStartDate:" + effectiveStartDate);
			
			String epochDate = getEpoch(effectiveStartDate);

//			logger.info("FOUtility: getDepartmentID Method: epochDate:" + epochDate);
			String externalCode = getRandomString(6);
			String externalName = getRandomString(6);
			String legacyValue = ((MetaDataObj) rowData.get(index)).getFieldValue();
			String newValue = null;

			if (isTestRun.equalsIgnoreCase("No")) {
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
		}else {
			
			return legacyValue;
		}
		}
		
		// PayGrade
		
		// Create PayRange ID
			public String getCreatePayRangeID(List<MetaDataObj> rowData, int index, String company,
					Map<String, String> clientSystem,String isTestRun) throws Exception {

				
//				logger.info("FOUtility: Inside getDepartmentID Method");
				String effectiveStartDate = null;
			
				for (MetaDataObj metaDataObj : rowData) {
//					logger.info("FOUtility: Inside getDepartmentID Method: metaDataObj.getFieldName():"
//							+ metaDataObj.getFieldName());
					if (null != metaDataObj.getFieldName()
							&& "start-date".equalsIgnoreCase(metaDataObj.getFieldName())) {
						effectiveStartDate = metaDataObj.getFieldValue();
					}
				}

//				logger.info("FOUtility: getDepartmentID Method: effectiveStartDate:" + effectiveStartDate);
				
				String epochDate = getEpoch(effectiveStartDate);

//				logger.info("FOUtility: getDepartmentID Method: epochDate:" + epochDate);
				String externalCode = getRandomString(6);
				String externalName = getRandomString(6);
				String legacyValue = ((MetaDataObj) rowData.get(index)).getFieldValue();
				String newValue = null;

				if (isTestRun.equalsIgnoreCase("No")) {
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
			}else {
				
				return legacyValue;
			}
			}
			
			
		
		// Create Location id	
		public String getCreateLocationID(List<MetaDataObj> rowData, int index, String company,
				Map<String, String> clientSystem,String isTestRun) throws Exception {

			
//			logger.info("FOUtility: Inside getDepartmentID Method");
			String effectiveStartDate = null;
		
			for (MetaDataObj metaDataObj : rowData) {
//				logger.info("FOUtility: Inside getDepartmentID Method: metaDataObj.getFieldName():"
//						+ metaDataObj.getFieldName());
				if (null != metaDataObj.getFieldName()
						&& "start-date".equalsIgnoreCase(metaDataObj.getFieldName())) {
					effectiveStartDate = metaDataObj.getFieldValue();
				}
			}

//			logger.info("FOUtility: getDepartmentID Method: effectiveStartDate:" + effectiveStartDate);
			
			String epochDate = getEpoch(effectiveStartDate);

//			logger.info("FOUtility: getDepartmentID Method: epochDate:" + epochDate);
			String externalCode = getRandomString(6);
			String externalName = getRandomString(6);
			String legacyValue = ((MetaDataObj) rowData.get(index)).getFieldValue();
			String newValue = null;

			if (isTestRun.equalsIgnoreCase("No")) {
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
		}else {
			
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
		String epociInputDate = strDate.substring(6, 10)+"-"+strDate.substring(0, 2)+"-"+strDate.substring(3, 5)+'T'+"00:00";
		
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
			Map<String, String> clientSystem,String isTestRun) throws Exception {
		String legacyValue = ((MetaDataObj) rowData.get(index)).getFieldValue();
		String newValue = "";
		if (null != legacyValue && !legacyValue.isEmpty()) {
			newValue = "DummyEmail@sap.com";
		}
		return newValue;
	}

	/// *****************Default Date of Birth *******************///

	public String getScrambleDOB(List<MetaDataObj> rowData, int index, String company, Map<String, String> clientSystem,String isTestRun)

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
			Map<String, String> clientSystem,String isTestRun) throws Exception {

		String Actualamount = ((MetaDataObj) rowData.get(index)).getFieldValue();
		float Actualamountnew ;

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
		}
		else {
			return ("");
		}
		
	}

	/// *****************Default Sequence Number *******************///

	public String getDefaultSeqnr(List<MetaDataObj> rowData, int index, String company,
			Map<String, String> clientSystem) throws Exception {
		return ("01");

	}

	/// *****************Default Sequence Number *******************///

	public String getDepLevel1(List<MetaDataObj> rowData, int index, String company, Map<String, String> clientSystem,String isTestRun)
			throws Exception {

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

	public String CheckDate(List<MetaDataObj> rowData, int index, String company, Map<String, String> clientSystem,String isTestRun)
			throws Exception {

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

	public String defaultManager(List<MetaDataObj> rowData, int index, String company, Map<String, String> clientSystem,String isTestRun)
			throws Exception {

		String newValue = "NO_MANAGER";

		return newValue;
	}

	public String defaultHR(List<MetaDataObj> rowData, int index, String company, Map<String, String> clientSystem,String isTestRun)
			throws Exception {

		String newValue = "NO_HR";

		return newValue;
	}

	public String checkUserID(List<MetaDataObj> rowData, int index, String company, Map<String, String> clientSystem,String isTestRun)
			throws Exception {

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
			Map<String, String> clientSystem,String isTestRun) throws Exception {

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
	
	public String getDept(List<MetaDataObj> rowData, int index, String company,
			Map<String, String> clientSystem,String isTestRun) throws Exception {

		String cust_LegacyID = ((MetaDataObj) rowData.get(index)).getFieldValue();
		String ObjectType="01";

		String newValue = "";
		
		String urlx = clientSystem.get("URL");
		String userID = clientSystem.get("USER_ID");
		String password = clientSystem.get("PWD");

		Metadata metaData = new Metadata();



		if (null != cust_LegacyID && !cust_LegacyID.isEmpty()) {

			RestTemplate restTemplate = new RestTemplate();
			
			String url = urlx+"/cust_Keymapping?$filter=cust_Company eq '"+company+"' and cust_ObjectType eq '"+ObjectType+"' and cust_LegacyID eq '"+cust_LegacyID+"'&$select=cust_SFID";
			logger.info("url:" + url);
			System.out.println(url);	
			
			restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(userID,password));


			KeyMap result = restTemplate.getForObject(url,KeyMap.class);
		
			if ( result != null) {
				D d = result.getD();
				List<Result> res = d.getResults();
				for (Result result1 :res) {
					newValue = result1.getCustSFID();	
					
				}
				
				
			}
			
		}
		
		if (newValue.isEmpty()) {
			newValue = cust_LegacyID + "<-- Invalid Department";
		}
		
	
		
		return newValue;
	}

	
	public String getLegacyDept(List<MetaDataObj> rowData, int index, String company,
			Map<String, String> clientSystem,String isTestRun) throws Exception {

		String SFDep = null;
		
		for (MetaDataObj metaDataObj : rowData) {
//			logger.info("FOUtility: Inside getDepartmentID Method: metaDataObj.getFieldName():"
//					+ metaDataObj.getFieldName());
			if (null != metaDataObj.getFieldName()
					&& "externalCode".equalsIgnoreCase(metaDataObj.getFieldName())) {
				SFDep = metaDataObj.getFieldValue();
			}
		}

		
		String cust_LegacyID = ((MetaDataObj) rowData.get(index)).getFieldValue();
		String ObjectType="01";

		String newValue = "";
		
		String urlx = clientSystem.get("URL");
		String userID = clientSystem.get("USER_ID");
		String password = clientSystem.get("PWD");

		Metadata metaData = new Metadata();



		if (null != SFDep && !SFDep.isEmpty()) {

			RestTemplate restTemplate = new RestTemplate();
			
			String url = urlx+"/cust_Keymapping?$filter=cust_Company eq '"+company+"' and cust_ObjectType eq '"+ObjectType+"' and cust_SFID eq '"+SFDep+"'&$select=cust_LegacyID";
			logger.info("url:" + url);
			System.out.println(url);	
			
			restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(userID,password));


			KeyMap result = restTemplate.getForObject(url,KeyMap.class);
		
			if ( result != null) {
				D d = result.getD();
				List<Result> res = d.getResults();
				for (Result result1 :res) {
					newValue = result1.getCust_LegacyID();	
					
				}
				
				
			}
			
		}
		
		if (newValue.isEmpty()) {
			newValue = cust_LegacyID ;
		}
		
	
		
		return newValue;
	}



public String getDiv(List<MetaDataObj> rowData, int index, String company,
		Map<String, String> clientSystem,String isTestRun) throws Exception {

	String cust_LegacyID = ((MetaDataObj) rowData.get(index)).getFieldValue();
	String ObjectType="11";

	String newValue = "";
	
	String urlx = clientSystem.get("URL");
	String userID = clientSystem.get("USER_ID");
	String password = clientSystem.get("PWD");

	Metadata metaData = new Metadata();



	if (null != cust_LegacyID && !cust_LegacyID.isEmpty()) {

		RestTemplate restTemplate = new RestTemplate();
		
		String url = urlx+"/cust_Keymapping?$filter=cust_Company eq '"+company+"' and cust_ObjectType eq '"+ObjectType+"' and cust_LegacyID eq '"+cust_LegacyID+"'&$select=cust_SFID";
		logger.info("url:" + url);
		System.out.println(url);	
		
		restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(userID,password));


		KeyMap result = restTemplate.getForObject(url,KeyMap.class);
	
		if ( result != null) {
			D d = result.getD();
			List<Result> res = d.getResults();
			for (Result result1 :res) {
				newValue = result1.getCustSFID();	
				
			}
			
			
		}
		
	}
	
	if (newValue.isEmpty()) {
		newValue = cust_LegacyID + "<-- Invalid Division";
	}


	return newValue;
}
//// Get Job Classification

public String getJobClassificationID(List<MetaDataObj> rowData, int index, String company,
		Map<String, String> clientSystem,String isTestRun) throws Exception {

	String cust_LegacyID = ((MetaDataObj) rowData.get(index)).getFieldValue();
	String ObjectType="02";

	String newValue = "";
	
	String urlx = clientSystem.get("URL");
	String userID = clientSystem.get("USER_ID");
	String password = clientSystem.get("PWD");

	Metadata metaData = new Metadata();



	if (null != cust_LegacyID && !cust_LegacyID.isEmpty()) {

		RestTemplate restTemplate = new RestTemplate();
		
		String url = urlx+"/cust_Keymapping?$filter=cust_Company eq '"+company+"' and cust_ObjectType eq '"+ObjectType+"' and cust_LegacyID eq '"+cust_LegacyID+"'&$select=cust_SFID";
		logger.info("url:" + url);
		System.out.println(url);	
		
		restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(userID,password));


		KeyMap result = restTemplate.getForObject(url,KeyMap.class);
	
		if ( result != null) {
			D d = result.getD();
			List<Result> res = d.getResults();
			for (Result result1 :res) {
				newValue = result1.getCustSFID();	
				
			}
			
			
		}
		
	}
	
	if (newValue.isEmpty()) {
		newValue = cust_LegacyID + "<-- Invalid Job Classification";
	}


	return newValue;
}

////Get Job Position

public String getJobPositionID(List<MetaDataObj> rowData, int index, String company,
	Map<String, String> clientSystem,String isTestRun) throws Exception {

String cust_LegacyID = ((MetaDataObj) rowData.get(index)).getFieldValue();
String ObjectType="09";

String newValue = "";

String urlx = clientSystem.get("URL");
String userID = clientSystem.get("USER_ID");
String password = clientSystem.get("PWD");

Metadata metaData = new Metadata();



if (null != cust_LegacyID && !cust_LegacyID.isEmpty()) {

	RestTemplate restTemplate = new RestTemplate();
	
	String url = urlx+"/cust_Keymapping?$filter=cust_Company eq '"+company+"' and cust_ObjectType eq '"+ObjectType+"' and cust_LegacyID eq '"+cust_LegacyID+"'&$select=cust_SFID";
	logger.info("url:" + url);
	System.out.println(url);	
	
	restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(userID,password));


	KeyMap result = restTemplate.getForObject(url,KeyMap.class);

	if ( result != null) {
		D d = result.getD();
		List<Result> res = d.getResults();
		for (Result result1 :res) {
			newValue = result1.getCustSFID();	
			
		}
		
		
	}
	
}

if (newValue.isEmpty()) {
	newValue = cust_LegacyID + "<-- Invalid Position";
}


return newValue;
}





////Get Grade Id

public String getPaygradeID(List<MetaDataObj> rowData, int index, String company,
Map<String, String> clientSystem,String isTestRun) throws Exception {

String cust_LegacyID = ((MetaDataObj) rowData.get(index)).getFieldValue();
String ObjectType="06";

String newValue = "";

String urlx = clientSystem.get("URL");
String userID = clientSystem.get("USER_ID");
String password = clientSystem.get("PWD");

Metadata metaData = new Metadata();



if (null != cust_LegacyID && !cust_LegacyID.isEmpty()) {

RestTemplate restTemplate = new RestTemplate();

String url = urlx+"/cust_Keymapping?$filter=cust_Company eq '"+company+"' and cust_ObjectType eq '"+ObjectType+"' and cust_LegacyID eq '"+cust_LegacyID+"'&$select=cust_SFID";
logger.info("url:" + url);
System.out.println(url);	

restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(userID,password));


KeyMap result = restTemplate.getForObject(url,KeyMap.class);

if ( result != null) {
	D d = result.getD();
	List<Result> res = d.getResults();
	for (Result result1 :res) {
		newValue = result1.getCustSFID();	
		
	}
	
	
}

}

if (newValue.isEmpty()) {
newValue = cust_LegacyID + "<-- Invalid PayGrade";
}


return newValue;
}



////Get Location Id

public String getLocationID(List<MetaDataObj> rowData, int index, String company,
Map<String, String> clientSystem,String isTestRun) throws Exception {

String cust_LegacyID = ((MetaDataObj) rowData.get(index)).getFieldValue();
String ObjectType="03";

String newValue = "";

String urlx = clientSystem.get("URL");
String userID = clientSystem.get("USER_ID");
String password = clientSystem.get("PWD");

Metadata metaData = new Metadata();



if (null != cust_LegacyID && !cust_LegacyID.isEmpty()) {

RestTemplate restTemplate = new RestTemplate();

String url = urlx+"/cust_Keymapping?$filter=cust_Company eq '"+company+"' and cust_ObjectType eq '"+ObjectType+"' and cust_LegacyID eq '"+cust_LegacyID+"'&$select=cust_SFID";
logger.info("url:" + url);
System.out.println(url);	

restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(userID,password));


KeyMap result = restTemplate.getForObject(url,KeyMap.class);

if ( result != null) {
D d = result.getD();
List<Result> res = d.getResults();
for (Result result1 :res) {
	newValue = result1.getCustSFID();	
	
}


}

}

if (newValue.isEmpty()) {
newValue = cust_LegacyID + "<-- Invalid Location";
}


return newValue;
}



////Get Cost Center Id

public String getCCID(List<MetaDataObj> rowData, int index, String company,
Map<String, String> clientSystem,String isTestRun) throws Exception {

String cust_LegacyID = ((MetaDataObj) rowData.get(index)).getFieldValue();
String ObjectType="03";

String newValue = "";

String urlx = clientSystem.get("URL");
String userID = clientSystem.get("USER_ID");
String password = clientSystem.get("PWD");

Metadata metaData = new Metadata();



if (null != cust_LegacyID && !cust_LegacyID.isEmpty()) {

RestTemplate restTemplate = new RestTemplate();

String url = urlx+"/cust_Keymapping?$filter=cust_Company eq '"+company+"' and cust_ObjectType eq '"+ObjectType+"' and cust_LegacyID eq '"+cust_LegacyID+"'&$select=cust_SFID";
logger.info("url:" + url);
System.out.println(url);	

restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(userID,password));


KeyMap result = restTemplate.getForObject(url,KeyMap.class);

if ( result != null) {
D d = result.getD();
List<Result> res = d.getResults();
for (Result result1 :res) {
newValue = result1.getCustSFID();	

}


}

}

if (newValue.isEmpty()) {
newValue = cust_LegacyID + "<-- Invalid Cost Center";
}


return newValue;
}




////Get address 1

public String address1(List<MetaDataObj> rowData, int index, String company,
Map<String, String> clientSystem,String isTestRun) throws Exception {

String cust_LegacyID = ((MetaDataObj) rowData.get(index)).getFieldValue();
String ObjectType="12";


String operation = null;

for (MetaDataObj metaDataObj : rowData) {
	if (null != metaDataObj.getFieldName()
			&& "operation".equalsIgnoreCase(metaDataObj.getFieldName())) {
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

String url = urlx+"/cust_Keymapping?$filter=cust_Company eq '"+company+"' and cust_ObjectType eq '"+ObjectType+"' and externalCode eq '"+operation+"'&$select=externalName";
logger.info("url:" + url);
System.out.println(url);	

restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(userID,password));


KeyMap result = restTemplate.getForObject(url,KeyMap.class);

if ( result != null) {
D d = result.getD();
List<Result> res = d.getResults();
for (Result result1 :res) {
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

public String address2(List<MetaDataObj> rowData, int index, String company,
Map<String, String> clientSystem,String isTestRun) throws Exception {

String cust_LegacyID = ((MetaDataObj) rowData.get(index)).getFieldValue();
String ObjectType="12";


String operation = null;

for (MetaDataObj metaDataObj : rowData) {
if (null != metaDataObj.getFieldName()
		&& "operation".equalsIgnoreCase(metaDataObj.getFieldName())) {
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

String url = urlx+"/cust_Keymapping?$filter=cust_Company eq '"+company+"' and cust_ObjectType eq '"+ObjectType+"' and externalCode eq '"+operation+"'&$select=cust_LegacyID";
logger.info("url:" + url);
System.out.println(url);	

restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(userID,password));


KeyMap result = restTemplate.getForObject(url,KeyMap.class);

if ( result != null) {
D d = result.getD();
List<Result> res = d.getResults();
for (Result result1 :res) {
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

public String address3(List<MetaDataObj> rowData, int index, String company,
Map<String, String> clientSystem,String isTestRun) throws Exception {

String cust_LegacyID = ((MetaDataObj) rowData.get(index)).getFieldValue();
String ObjectType="12";


String operation = null;

for (MetaDataObj metaDataObj : rowData) {
if (null != metaDataObj.getFieldName()
	&& "operation".equalsIgnoreCase(metaDataObj.getFieldName())) {
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

String url = urlx+"/cust_Keymapping?$filter=cust_Company eq '"+company+"' and cust_ObjectType eq '"+ObjectType+"' and externalCode eq '"+operation+"'&$select=cust_SFID";
logger.info("url:" + url);
System.out.println(url);	

restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(userID,password));


KeyMap result = restTemplate.getForObject(url,KeyMap.class);

if ( result != null) {
D d = result.getD();
List<Result> res = d.getResults();
for (Result result1 :res) {
newValue = result1.getCustSFID();	

}


}

}

//if (newValue.isEmpty()) {
//newValue = cust_LegacyID + "<-- Address 3 not found for :" +operation ;
//}


return newValue;
}




}


