

package com.sfec.shd.UtilityFunction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.plaf.synth.SynthOptionPaneUI;

import com.sfec.shd.FieldSet;

public class FOUitlityUnitTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String result=null;
		Map<String, String> map = new HashMap<String, String>();
		map.put("URL", "https://api12preview.sapsf.eu/odata/v2");
	
	
		map.put("USER_ID", "VKUMAR@shiseidocoT1");
		map.put("PWD", "Welcome@3");
		
		
	//	map.put("URL", "https://apisalesdemo2.successfactors.eu/odata/v2");
	//	map.put("USER_ID", "sfadmin@SFPART046830");
	//	map.put("PWD", "Welcome1");
		
		
		List<MetaDataObj> MetaDataObj = new ArrayList();
		MetaDataObj MO = new MetaDataObj();
		
		MO.setFieldName("Status");
		MO.setFieldValue("HQ-Headquarters");
		
		MetaDataObj.add(MO);
		
//getSplitRight(List<MetaDataObj> rowData, int index, String company, Map<String, String> clientSystem)
		
		FOUtility FU = new FOUtility();
		try {
			result = FU.getSplitRight(MetaDataObj, 0, "9", map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(result);
		
//getSplitLeft(List<MetaDataObj> rowData, int index, String company, Map<String, String> clientSystem)		
		try {
			result = FU.getSplitLeft(MetaDataObj, 0, "9", map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(result);
		
// getDefaultEmail(List<MetaDataObj> rowData, int index, String company, Map<String, String> clientSystem)	
		
		
		
		try {
			result = FU.getDefaultEmail(MetaDataObj, 0, "9", map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(result);
		
// getDefaultEmail(List<MetaDataObj> rowData, int index, String company, Map<String, String> clientSystem)	
		
		
		MO.setFieldName("DOB");
		MO.setFieldValue("05/06/1991");
	//	MO.setFieldValue("1988-21-04T00:00");
	
		MetaDataObj.add(MO); //1

		
				try {
					result = FU.getScrambleDOB(MetaDataObj, 1, "9", map);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				System.out.println(result);		

// getDefaultEmail(List<MetaDataObj> rowData, int index, String company, Map<String, String> clientSystem)	
				
				
				MO.setFieldName("Amt");
				MO.setFieldValue("20.25");
				
				MetaDataObj.add(MO); //2

				
						try {
							result = FU.getScrambleAmount(MetaDataObj, 1, "9", map);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						System.out.println(result);		
						
						
// getDepLevel1(List<MetaDataObj> rowData, int index, String company, Map<String, String> clientSystem)	
						
						
						MO.setFieldName("DepLevel1");
						MO.setFieldValue("GA_H_B");
						//MO.setFieldValue("FI DL 2 _17");
						
						MetaDataObj.add(MO); //3

						
								try {
								//	result = FU.getDepLevel1(MetaDataObj, 3, "04", map);
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								
								System.out.println(result);	
				
								MO.setFieldName("DOB");
								MO.setFieldValue("M - Male"); //4
								
								MetaDataObj.add(MO);
								try {
									result = FU.getSplitRight(MetaDataObj, 4, "9", map);
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								System.out.println(result);	
								
								try {
									result = FU.getSplitLeft(MetaDataObj, 4, "9", map);
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								System.out.println(result);	
								
	}

}
