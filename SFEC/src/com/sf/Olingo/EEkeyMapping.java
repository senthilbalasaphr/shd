package com.sf.Olingo;

public class EEkeyMapping {
	
	String PERNR;
	public String getUSER_ID() {
		return USER_ID;
	}
	public void setUSER_ID(String uSER_ID) {
		USER_ID = uSER_ID;
	}
	public String getPERSON_ID() {
		return PERSON_ID;
	}
	public void setPERSON_ID(String pERSON_ID) {
		PERSON_ID = pERSON_ID;
	}
	public String getPERSON_ID_EXTER() {
		return PERSON_ID_EXTER;
	}
	public void setPERSON_ID_EXTER(String pERSON_ID_EXTER) {
		PERSON_ID_EXTER = pERSON_ID_EXTER;
	}
	public String getPERSON_GUID() {
		return PERSON_GUID;
	}
	public void setPERSON_GUID(String pERSON_GUID) {
		PERSON_GUID = pERSON_GUID;
	}
	public String getEMPLOYMENT_ID() {
		return EMPLOYMENT_ID;
	}
	public void setEMPLOYMENT_ID(String eMPLOYMENT_ID) {
		EMPLOYMENT_ID = eMPLOYMENT_ID;
	}
	String USER_ID;
	String PERSON_ID;
	String PERSON_ID_EXTER;
	String PERSON_GUID;
	String  EMPLOYMENT_ID;
	
	public EEkeyMapping getEEkeyMapping(String PERNR,String USER_ID,String PERSON_ID,String PERSON_ID_EXTER,String PERSON_GUID,String EMPLOYMENT_ID ) {
		this.PERNR = PERNR;
		this.USER_ID = USER_ID;
		this.PERSON_ID=PERSON_ID;
		this.PERSON_ID_EXTER=PERSON_ID_EXTER;
		this.EMPLOYMENT_ID=EMPLOYMENT_ID;
		this.PERSON_GUID=PERSON_GUID;
		
		return this;
	}

}
