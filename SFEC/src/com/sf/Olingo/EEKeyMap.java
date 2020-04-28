package com.sf.Olingo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.Name;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.ws.handler.MessageContext;

import org.json.JSONObject;
import org.json.XML;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class EEKeyMap {

	public static void main(String[] args) throws SOAPException, IOException, TransformerException {
		// TODO Auto-generated method stub

		String USER_ID;
		String PERSON_ID;
		String PERSON_ID_EXTER;
		String PERSON_GUID;
		String  EMPLOYMENT_ID;
		ArrayList <EEkeyMapping> EEkeyMappingLst=  new ArrayList <EEkeyMapping>();
		
		MessageFactory messageFactory = MessageFactory.newInstance();
		SOAPMessage soapMessage = messageFactory.createMessage();
		SOAPPart soapPart = soapMessage.getSOAPPart();
		SOAPEnvelope soapEnvelope = soapPart.getEnvelope();
		soapEnvelope.addNamespaceDeclaration("urn",
				"urn:sfobject.sfapi.successfactors.com" + 
		"");
		
		SOAPBody soapBody = soapEnvelope.getBody();
		SOAPElement soapElementLogin = soapBody.addChildElement("login", "urn");
		SOAPElement soapElementcredential = soapElementLogin.addChildElement("credential", "urn");
		SOAPElement soapElementcompanyId = soapElementcredential.addChildElement("companyId", "urn");
		//soapElementcompanyId.addTextNode("freemanQA");
		soapElementcompanyId.addTextNode("freemanT1");
		
		SOAPElement soapElementusername = soapElementcredential.addChildElement("username", "urn");
		//soapElementusername.addTextNode("SFEC_SYNC_ALL");
		soapElementusername.addTextNode("SFEC_SYNC_ALL");
		
		SOAPElement soapElementpassword = soapElementcredential.addChildElement("password", "urn");
	//	soapElementpassword.addTextNode("FreemanHR1");
		soapElementpassword.addTextNode("FreemanHR1");
		
		soapMessage.saveChanges();
		System.out.println("----------SOAP Request------------");
		
		soapMessage.writeTo(System.out);
		;
		
		
		SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
		SOAPConnection soapConnection = soapConnectionFactory.createConnection();
		//String url = "https://api8.successfactors.com/sfapi/v1/soap?wsdl"; ////FreemanQA
		String url = "https://api8.successfactors.com/sfapi/v1/soap?wsdl"; //FreemanTst
		SOAPMessage soapRequest = soapMessage;
		// hit soapRequest to the server to get response
		SOAPMessage soapResponse = soapConnection.call(soapRequest, url);

		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		Source sourceContent = soapResponse.getSOAPPart().getContent();
		System.out.println("\n----------SOAP Response-----------");
		StreamResult result = new StreamResult(System.out);
		transformer.transform(sourceContent, result);
		
		
		// Get Session ID
		

		SOAPBody sess = soapResponse.getSOAPBody();
		NodeList loginResponseLst =  sess.getElementsByTagName("loginResponse");
		 
		
		 Node loginResponse = loginResponseLst.item(0);
		 Element loginResponseElmt = (Element) loginResponse;
		 
		 
		 NodeList resultLst1 =  loginResponseElmt.getElementsByTagName("result");
		 Node resultx = resultLst1.item(0);
		 Element resultElmt1 = (Element) resultx;
		 
		 
		 NodeList sessionLst =  resultElmt1.getChildNodes();
		 
		 String cookie = null;
		 for (int s = 0; s < sessionLst.getLength(); s++) {

		        //    System.out.println(data1.item(j).getNodeName());
		            
		            if (sessionLst.item(s).getNodeName() == "sessionId"){
		            	sessionLst.item(s).getTextContent();
		            	
		            	 cookie = "JSESSIONID="+sessionLst.item(s).getTextContent();
		            	 System.out.println(cookie);
		            }}
		 
		
		 ///Read file
		 
		 	
		  try (FileReader reader = new FileReader("/Users/baps/Documents/FREEMAN/Workspace/KeyMap/ee.txt");
		             BufferedReader br = new BufferedReader(reader)) {

		            // read line by line
		            String line;
		            while ((line = br.readLine()) != null) {
		                System.out.println(line);
		            
		  
		 
		 
		 
		 
		 
		 
		 
		
		MessageFactory messageFactoryq = MessageFactory.newInstance();
		SOAPMessage soapMessageq = messageFactoryq.createMessage();
		SOAPPart soapPartq = soapMessageq.getSOAPPart();
		SOAPEnvelope soapEnvelopeq = soapPartq.getEnvelope();
		soapEnvelopeq.addNamespaceDeclaration("urn",
				"urn:sfobject.sfapi.successfactors.com" + 
		"");
		
		SOAPBody soapBodyq = soapEnvelopeq.getBody();
		SOAPHeader soapHeaderq = soapEnvelopeq.getHeader();
		SOAPElement soapElementquery = soapBodyq.addChildElement("query", "urn");
		SOAPElement soapElementqueryString = soapElementquery.addChildElement("queryString", "urn");
		soapElementqueryString.addTextNode("SELECT person,employment_information FROM CompoundEmployee where USER_ID = '"+line+"'");
		
	

		MimeHeaders mh = soapMessageq.getMimeHeaders();
		mh.addHeader("Cookie", cookie);
		   
		soapMessageq.saveChanges();
		
//System.out.println("----------SOAP Request------------");
		
//		soapMessageq.writeTo(System.out);
		;
		
		
		SOAPMessage soapRequestq = soapMessageq;
		
		// hit soapRequest to the server to get response
		SOAPMessage soapResponseq = soapConnection.call(soapRequestq, url);

		TransformerFactory transformerFactoryq = TransformerFactory.newInstance();
		Transformer transformerq = transformerFactoryq.newTransformer();
		Source sourceContentq = soapResponseq.getSOAPPart().getContent();
		System.out.println("\n----------SOAP Response-----------");
		StreamResult resultq = new StreamResult(System.out);
		transformerq.transform(sourceContentq, resultq);
		
		SOAPBody b1 = soapResponseq.getSOAPBody();
		NodeList queryResponseLst =  b1.getElementsByTagName("queryResponse");
		 
		
		 Node queryResponse = queryResponseLst.item(0);
		 Element queryResponseElmt = (Element) queryResponse;
		 
		 NodeList resultLst = queryResponseElmt.getChildNodes();
		 Node result1 = resultLst.item(0);
		 Element resultElmt = (Element) result1;
		 
		 NodeList sfobjectLst = resultElmt.getChildNodes();
		 Node sfobject = sfobjectLst.item(0);
		 Element sfobjectElmt = (Element) sfobject;
		 
		 NodeList data1 = sfobjectElmt.getChildNodes();
		 String x=null;
		 
		 	 USER_ID ="";
			 PERSON_ID="";
			 PERSON_ID_EXTER="";
			 PERSON_GUID="";
			  EMPLOYMENT_ID="";
			for (int j = 0; j < data1.getLength(); j++) {

		        //    System.out.println(data1.item(j).getNodeName());
		            
		            if (data1.item(j).getNodeName() == "person"){

		            	
		            	NodeList valueLst = data1.item(j).getChildNodes();
		            	
		            	for (int a = 0; a < valueLst.getLength(); a++) {
		            	
		            		// System.out.println(valueLst.item(a).getNodeName());
		            		// System.out.println(valueLst.item(a).getTextContent());
		            	
		            		 
		            		   if (valueLst.item(a).getNodeName() == "employment_information"){
		   		            	
		   		  
		   		            	
		   		            	NodeList employment = valueLst.item(a).getChildNodes();
		   		            	
		   		            	for (int b = 0; b < employment.getLength(); b++) {
		   		            	
		   		            		 System.out.println(employment.item(b).getNodeName());
		   		            		 System.out.println(employment.item(b).getTextContent());
		   		            	 
		   		            		 
		   		            		 if (employment.item(b).getNodeName() == "user_id") {
		   		            			USER_ID=employment.item(b).getTextContent();
		   		            		 }
		   		            		 if (employment.item(b).getNodeName() == "employment_id") {
		   		            			EMPLOYMENT_ID=employment.item(b).getTextContent();
			   		            		 }
		   		            	
		   		            		
		   		            }
		   		            	
		   		            	
		            		
		            		   }
		            		   
		            		   if (valueLst.item(a).getNodeName() == "per_person_uuid"){
		            			   PERSON_GUID=valueLst.item(a).getTextContent();
		            			   }
		            		   
		            		   if (valueLst.item(a).getNodeName() == "person_id"){
		            			   PERSON_ID=valueLst.item(a).getTextContent();
		            			   }
		            
		            		   if (valueLst.item(a).getNodeName() == "person_id_external"){
		            			   PERSON_ID_EXTER=valueLst.item(a).getTextContent();
		            			   }
		            	}
		            }
		        }

			
			EEkeyMapping eeKeyMap = new EEkeyMapping();
			
			EEkeyMapping EEKeyMap = eeKeyMap.getEEkeyMapping(USER_ID,USER_ID,PERSON_ID,PERSON_ID_EXTER,PERSON_GUID,EMPLOYMENT_ID);
			
			
			EEkeyMappingLst.add(EEKeyMap);
		
	}
		  
		  }	

		  PrintWriter writer = new PrintWriter("/Users/baps/Documents/FREEMAN/Workspace/KeyMap/eeOut.txt", "UTF-8");
		  for (EEkeyMapping EEkeyMap: EEkeyMappingLst) {
			  System.out.println(EEkeyMap.PERNR+","+EEkeyMap.USER_ID+","+EEkeyMap.PERSON_ID+","+EEkeyMap.PERSON_ID_EXTER+","+EEkeyMap.PERSON_GUID+","+EEkeyMap.EMPLOYMENT_ID);
			  writer.println(EEkeyMap.PERNR+","+EEkeyMap.USER_ID+","+EEkeyMap.PERSON_ID+","+EEkeyMap.PERSON_ID_EXTER+","+EEkeyMap.PERSON_GUID+","+EEkeyMap.EMPLOYMENT_ID);
		  }
		  writer.close();
	}
	 
}


