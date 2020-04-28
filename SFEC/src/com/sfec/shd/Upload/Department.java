package com.sfec.shd.Upload;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.olingo.odata2.api.edm.Edm;
import org.apache.olingo.odata2.api.ep.entry.ODataEntry;
import org.apache.olingo.odata2.api.ep.feed.ODataFeed;
import org.apache.olingo.odata2.api.exception.ODataException;

import com.sf.Olingo.OlingoV2Main;
import com.sf.Olingo.W4IntegrationONBMapping;

public class Department {

	public static final String APPLICATION_JSON = "application/json";
	public static String serviceUrl = null;
	public static String usedFormat = null;
	public static Edm edm = null;
	public static OlingoV2Main app;
	
	
	String		OPERATOR;
	public String getExternalCode() {
		return externalCode;
	}
	public void setExternalCode(String externalCode) {
		this.externalCode = externalCode;
	}
	public Date getEffectiveStartDate() {
		return effectiveStartDate;
	}
	public void setEffectiveStartDate(Date effectiveStartDate) {
		this.effectiveStartDate = effectiveStartDate;
	}
	public String getName_defaultValue() {
		return name_defaultValue;
	}
	public void setName_defaultValue(String name_defaultValue) {
		this.name_defaultValue = name_defaultValue;
	}
	public String getName_en_US() {
		return name_en_US;
	}
	public void setName_en_US(String name_en_US) {
		this.name_en_US = name_en_US;
	}
	public String getName_ja_JP() {
		return name_ja_JP;
	}
	public void setName_ja_JP(String name_ja_JP) {
		this.name_ja_JP = name_ja_JP;
	}
	public String getName_th_TH() {
		return name_th_TH;
	}
	public void setName_th_TH(String name_th_TH) {
		this.name_th_TH = name_th_TH;
	}
	public String getName_ko_KR() {
		return name_ko_KR;
	}
	public void setName_ko_KR(String name_ko_KR) {
		this.name_ko_KR = name_ko_KR;
	}
	public String getName_zh_TW() {
		return name_zh_TW;
	}
	public void setName_zh_TW(String name_zh_TW) {
		this.name_zh_TW = name_zh_TW;
	}
	public String getName_bs_ID() {
		return name_bs_ID;
	}
	public void setName_bs_ID(String name_bs_ID) {
		this.name_bs_ID = name_bs_ID;
	}
	public String getName_vi_VN() {
		return name_vi_VN;
	}
	public void setName_vi_VN(String name_vi_VN) {
		this.name_vi_VN = name_vi_VN;
	}
	public String getDescription_defaultValue() {
		return description_defaultValue;
	}
	public void setDescription_defaultValue(String description_defaultValue) {
		this.description_defaultValue = description_defaultValue;
	}
	public String getDescription_en_US() {
		return description_en_US;
	}
	public void setDescription_en_US(String description_en_US) {
		this.description_en_US = description_en_US;
	}
	public String getDescription_JP() {
		return description_JP;
	}
	public void setDescription_JP(String description_JP) {
		this.description_JP = description_JP;
	}
	public String getDescription_th_TH() {
		return description_th_TH;
	}
	public void setDescription_th_TH(String description_th_TH) {
		this.description_th_TH = description_th_TH;
	}
	public String getDescription_ko_KR() {
		return description_ko_KR;
	}
	public void setDescription_ko_KR(String description_ko_KR) {
		this.description_ko_KR = description_ko_KR;
	}
	public String getDescription_zh_TW() {
		return description_zh_TW;
	}
	public void setDescription_zh_TW(String description_zh_TW) {
		this.description_zh_TW = description_zh_TW;
	}
	public String getDescription_bs_ID() {
		return description_bs_ID;
	}
	public void setDescription_bs_ID(String description_bs_ID) {
		this.description_bs_ID = description_bs_ID;
	}
	public String getDescription_vi_VN() {
		return description_vi_VN;
	}
	public void setDescription_vi_VN(String description_vi_VN) {
		this.description_vi_VN = description_vi_VN;
	}
	public String getEffectiveStatus() {
		return effectiveStatus;
	}
	public void setEffectiveStatus(String effectiveStatus) {
		this.effectiveStatus = effectiveStatus;
	}
	public String getHeadOfUnit() {
		return headOfUnit;
	}
	public void setHeadOfUnit(String headOfUnit) {
		this.headOfUnit = headOfUnit;
	}
	public String getParentDepartment_externalCode() {
		return parentDepartment_externalCode;
	}
	public void setParentDepartment_externalCode(String parentDepartment_externalCode) {
		this.parentDepartment_externalCode = parentDepartment_externalCode;
	}
	public String getCostCenter_externalCode() {
		return costCenter_externalCode;
	}
	public void setCostCenter_externalCode(String costCenter_externalCode) {
		this.costCenter_externalCode = costCenter_externalCode;
	}
	public String getCust_deptLevel_externalCode() {
		return cust_deptLevel_externalCode;
	}
	public void setCust_deptLevel_externalCode(String cust_deptLevel_externalCode) {
		this.cust_deptLevel_externalCode = cust_deptLevel_externalCode;
	}
	String		externalCode;
	Date		effectiveStartDate;
	String		name_defaultValue;
	String		name_en_US;
	String		name_ja_JP;
	String		name_th_TH;
	String		name_ko_KR;
	String		name_zh_TW;
	String		name_bs_ID;
	String		name_vi_VN;
	String		description_defaultValue;
	String		description_en_US;
	String		description_JP;
	String		description_th_TH;
	String		description_ko_KR;
	String		description_zh_TW;
	String		description_bs_ID;
	String		description_vi_VN;
	String		effectiveStatus;
	String		headOfUnit;
	String		parentDepartment_externalCode;
	String		costCenter_externalCode;
	String		cust_deptLevel_externalCode;
			
	
	public Department getDepartment(String		OPERATOR,
									String		externalCode,
									Date		effectiveStartDate,
									String		name_defaultValue,
									String		name_en_US,
									String		name_ja_JP,
									String		name_th_TH,
									String		name_ko_KR,
									String		name_zh_TW,
									String		name_bs_ID,
									String		name_vi_VN,
									String		description_defaultValue,
									String		description_en_US,
									String		description_JP,
									String		description_th_TH,
									String		description_ko_KR,
									String		description_zh_TW,
									String		description_bs_ID,
									String		description_vi_VN,
									String		effectiveStatus,
									String		headOfUnit,
									String		parentDepartment_externalCode,
									String		costCenter_externalCode,
									String		cust_deptLevel_externalCode)
	{
		
		Department d = new Department();
		d.OPERATOR=OPERATOR;
		d.externalCode = externalCode;
		d.effectiveStartDate = effectiveStartDate;
		d.name_defaultValue = name_defaultValue;
		d.name_en_US = name_en_US;
		d.name_ja_JP = name_ja_JP;
		d.name_th_TH = name_th_TH;
		d.name_ko_KR = name_ko_KR;
		d.name_zh_TW = name_zh_TW;
		d.name_bs_ID = name_bs_ID;
		d.name_vi_VN = name_vi_VN;
		d.description_defaultValue = description_defaultValue;
		d.description_en_US = description_en_US;
		d.description_JP = description_JP;
		d.description_th_TH = description_th_TH;
		d.description_ko_KR = description_ko_KR;
		d.description_zh_TW = description_zh_TW;
		d.description_bs_ID = description_bs_ID;
		d.description_vi_VN = description_vi_VN;
		d.effectiveStatus = effectiveStatus;
		d.headOfUnit = headOfUnit;
		d.parentDepartment_externalCode = parentDepartment_externalCode;
		d.costCenter_externalCode = costCenter_externalCode;
		d.cust_deptLevel_externalCode = cust_deptLevel_externalCode;
		return d;							
	}
	
	
	
	public String getDepartmentExternalCode(String externalCode,Date effectiveStartDate,String cust_Company,String cust_ObjectType, String cust_LegacyID) {
		String ExternalCode=null;

		boolean b=true;
	
while(b) {
		
	
		 serviceUrl = "https://api12preview.sapsf.eu/odata/v2/cust_Keymapping";
		 usedFormat = APPLICATION_JSON;
		 
		 try {
				edm = app.readEdmSf(serviceUrl, "VKUMAR@shiseidocoT1", "Welcome@3");
			} catch (IOException e) {
				
			} catch (ODataException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		 
		 	String Status = null;
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("externalCode", externalCode);
			data.put("effectiveStartDate", effectiveStartDate);
			data.put("cust_Company", cust_Company);
			data.put("cust_ObjectType", cust_ObjectType);
			data.put("cust_LegacyID", cust_LegacyID);
			
			
			
			ODataEntry createdEntry = null;
			try {
				Status = app.createEntrySf(edm, serviceUrl, usedFormat, "cust_Keymapping", data,
						"VKUMAR@shiseidocoT1", "Welcome@3");
			} catch (Exception e) {
				e.printStackTrace();
			}

			System.out.println(Status);
			
			if (Status.equalsIgnoreCase( "Successful") ) {
			
//************Read***********// 
				
				String serviceUrl_external = "https://api12preview.sapsf.eu/odata/v2/";

				String usedFormat = APPLICATION_JSON;

				OlingoV2Main app = new OlingoV2Main();
				Edm edm=null;
				try {
					edm = app.readEdmSf(serviceUrl_external, "VKUMAR@shiseidocoT1", "Welcome@3");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ODataException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
//*********Read cust_Keymapping to get the SFID
				
	//			https://api12preview.sapsf.eu/odata/v2/cust_Keymapping?$format=JSON&$filter=externalCode in '784'&$select=externalCode
				
				serviceUrl="https://api12preview.sapsf.eu/odata/v2/cust_Keymapping?$format=JSON&$filter=externalCode in '"+externalCode+"'&$select=externalCode";
				try {
					ODataFeed Fed = app.readFeedSf(edm, serviceUrl, usedFormat, "cust_Keymapping","VKUMAR@shiseidocoT1", "Welcome@3");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ODataException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				
				
			//	https://api12preview.sapsf.eu/odata/v2/FODepartment?$format=JSON&$filter=externalCode in '60001006'&$select=externalCode

				serviceUrl="https://api12preview.sapsf.eu/odata/v2/FODepartment?$format=JSON&$filter=externalCode+eq+'60001006'&fromDate=2002-12-31&toDate=9999-12-31&$select=externalCode";
				try {
					ODataFeed Fed = app.readFeedSf(edm, serviceUrl, usedFormat, "FODepartment","VKUMAR@shiseidocoT1", "Welcome@3");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ODataException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				
			}
		
			
			
			
	}
			
			
		return ExternalCode;
		
	}
				
}
