package com.sf.Olingo;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream.Builder;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.MessageFactory;

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
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.ws.handler.MessageContext;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;
import org.xml.sax.helpers.DefaultHandler;

import com.google.gson.JsonParser;

import org.apache.olingo.odata2.api.exception.ODataException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.json.JSONException;
import org.json.JSONObject;

public class ReadONBData {

	public static void main(String[] args) throws SOAPException, IOException, TransformerException, ParserConfigurationException, SAXException, ODataException {
		// TODO Auto-generated method stub

		MessageFactory messageFactory = MessageFactory.newInstance();
		SOAPMessage soapMessage = messageFactory.createMessage();
		SOAPPart soapPart = soapMessage.getSOAPPart();
		SOAPEnvelope soapEnvelope = soapPart.getEnvelope();
		soapEnvelope.addNamespaceDeclaration("hrd",
				"http://ATS.online-onboarding.com/Client/HRDataServiceEx");
		
		
		
		SOAPHeader soapHeader = soapEnvelope.getHeader();
		SOAPElement soapElementH = soapHeader.addChildElement("CorrelationHeader", "hrd");

		SOAPElement elementh1 = soapElementH.addChildElement("CorrelationId", "hrd");
		elementh1.addTextNode("");

		SOAPElement elementh2 = soapElementH.addChildElement("SourceEndpoint", "hrd");
		elementh2.addTextNode("");
		
		SOAPBody soapBody = soapEnvelope.getBody();
		SOAPElement soapElement = soapBody.addChildElement("BeginSession", "hrd");

		SOAPElement element1 = soapElement.addChildElement("Name", "hrd");
		element1.addTextNode("SFEC_SYNC_ALL");

		SOAPElement element2 = soapElement.addChildElement("Password", "hrd");
		element2.addTextNode("FreemanHR1");

		soapMessage.saveChanges();
		System.out.println("----------SOAP Request------------");

//		soapEnvelope.removeNamespaceDeclaration(soapEnvelope.getPrefix());
//		soapEnvelope.addNamespaceDeclaration(PREFERRED_PREFIX, SOAP_ENV_NAMESPACE);
//		soapEnvelope.setPrefix(PREFERRED_PREFIX);
//	      header.setPrefix(PREFERRED_PREFIX);
//	      body.setPrefix(PREFERRED_PREFIX);
		
		
		
		soapMessage.writeTo(System.out);
		;

		SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
		SOAPConnection soapConnection = soapConnectionFactory.createConnection();
		String url = "https://onboarding8preview.sapsf.com/ONBPREM/Services/HRDataServiceEx.asmx?WSDL";
		SOAPMessage soapRequest = soapMessage;
		// hit soapRequest to the server to get response
		SOAPMessage soapResponse = soapConnection.call(soapRequest, url);

		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		Source sourceContent = soapResponse.getSOAPPart().getContent();
		System.out.println("\n----------SOAP Response-----------");
		StreamResult result = new StreamResult(System.out);
		transformer.transform(sourceContent, result);


		SOAPBody b1 = soapResponse.getSOAPBody();
		 NodeList list =  b1.getElementsByTagName("BeginSessionResponse");
		
		 for (int i = 0; i < list.getLength(); i++) {
	            NodeList innerList = list.item(i).getChildNodes();

	            for (int j = 0; j < innerList.getLength(); j++) {
	                System.out.println(innerList.item(j).getNodeName());
	                
	                String s = innerList.item(j).getTextContent();
	              //  System.out.println(innerList.item(j).getTextContent());
	                String dt[] = s.split("<BeginSessionResult><Ticket>");
	                String dt1[] = dt[1].split("</Ticket>");
	                
	               String key =  dt1[0];
	                
	     //          readONBData(key,"82597187-add2-4740-b3c0-63c987fdde46"); //DC
	     //          readONBData(key,"d3b3af6a-6f26-48dd-8b1f-fe6e0131011f"); //CA
	               readONBData(key,"a7413b39-6f60-4879-8a80-ad1476c33b75"); //HI
	               
	             
	                
	                
	                
	                
	            }
	        }
		
		
		
		
		soapConnection.close();

	}

	
	
	public static void readONBData(String Key, String NewhireID) throws SOAPException, IOException, TransformerException, ParserConfigurationException, SAXException, ODataException{
		
		
		MessageFactory messageFactory = MessageFactory.newInstance();
		SOAPMessage soapMessage = messageFactory.createMessage();
		SOAPPart soapPart = soapMessage.getSOAPPart();
		SOAPEnvelope soapEnvelope = soapPart.getEnvelope();
		soapEnvelope.addNamespaceDeclaration("hrd",
				"http://ATS.online-onboarding.com/Client/HRDataServiceEx");
		
		
		
		SOAPHeader soapHeader = soapEnvelope.getHeader();
		SOAPElement soapElementH = soapHeader.addChildElement("CorrelationHeader", "hrd");

		SOAPElement elementh1 = soapElementH.addChildElement("CorrelationId", "hrd");
		elementh1.addTextNode("");

		SOAPElement elementh2 = soapElementH.addChildElement("SourceEndpoint", "hrd");
		elementh2.addTextNode("");
		
		SOAPBody soapBody = soapEnvelope.getBody();
		SOAPElement soapElement = soapBody.addChildElement("GetNewhireRecord", "hrd");

		SOAPElement element1 = soapElement.addChildElement("Ticket", "hrd");
		element1.addTextNode(Key);

		SOAPElement element2 = soapElement.addChildElement("NewhireID", "hrd");
		element2.addTextNode(NewhireID);

		soapMessage.saveChanges();
		System.out.println("----------SOAP Request------------");

//		soapEnvelope.removeNamespaceDeclaration(soapEnvelope.getPrefix());
//		soapEnvelope.addNamespaceDeclaration(PREFERRED_PREFIX, SOAP_ENV_NAMESPACE);
//		soapEnvelope.setPrefix(PREFERRED_PREFIX);
//	      header.setPrefix(PREFERRED_PREFIX);
//	      body.setPrefix(PREFERRED_PREFIX);
		
		
		
	//	soapMessage.writeTo(System.out);
		;

		SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
		SOAPConnection soapConnection = soapConnectionFactory.createConnection();
		String url = "https://onboarding8preview.sapsf.com/ONBPREM/Services/HRDataServiceEx.asmx?WSDL";
		SOAPMessage soapRequest = soapMessage;
		// hit soapRequest to the server to get response
		SOAPMessage soapResponse = soapConnection.call(soapRequest, url);

		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		Source sourceContent = soapResponse.getSOAPPart().getContent();
		System.out.println("\n----------SOAP Response-----------");
		
		StreamResult result = new StreamResult(System.out);
		
		StringWriter sw = new StringWriter();

		
		TransformerFactory.newInstance().newTransformer().transform(
                new DOMSource(soapResponse.getSOAPPart()),
                new StreamResult(sw));
	

		
	
//		transformer.transform(sourceContent, result);

		String s=null;
		SOAPBody b1 = soapResponse.getSOAPBody();
		String x=null;
		 NodeList list =  b1.getElementsByTagName("GetNewhireRecordResult");
		 String res=null;
		 for (int i = 0; i < list.getLength(); i++) {
			 
			 Node GetNewhireRecordResult = list.item(i);
			 Element GetNewhireRecordResulte = (Element) GetNewhireRecordResult;
			 NodeList listNH = GetNewhireRecordResulte.getChildNodes();
			 

		for (int j = 0; j < listNH.getLength(); j++) {

	            System.out.println(listNH.item(j).getNodeName());
	              System.out.println(listNH.item(j).getTextContent());
	               x = listNH.item(j).getTextContent();
	        }
		 }
//		 
		
//
		 JSONObject xmlJSONObj = XML.toJSONObject(x);
		 String jsonPrettyPrintString = xmlJSONObj.toString(4);

		 
		 
		ArrayList <String> lst=  Read.getONBMapping();
	
		
		
	//	for (String onbmapping: lst) {
		//	System.out.println(onbmapping);
			
		 
		 JSONObject  jObject = new JSONObject(jsonPrettyPrintString);
	       JSONObject geoObject = jObject.getJSONObject("GetNewhireRecordResult");

	       JSONObject NewhireRecord = geoObject.getJSONObject ("NewhireRecord");
	       
	       JSONObject DictionarySerializer = NewhireRecord.getJSONObject ("DictionarySerializer");

	       JSONObject dictionary = DictionarySerializer.getJSONObject ("dictionary");
	       
	      JSONArray item =  dictionary.getJSONArray("item");
	      
	     
	      System.out.println("Mapped fields");
	  	
			for (String onbmapping: lst) {
			//	System.out.println(onbmapping);
				
	      for(int z=0; z<= item.length()-1; z++) {
	      
	    	  JSONObject dt=  (JSONObject) item.get(z);

	    	String key= dt.getString("key");
	    	// System.out.println(dt.getString("key")+":");
	    	 if (key.equalsIgnoreCase(onbmapping) ) {
	    		 
	    	 
	    		  System.out.print(dt.getString("key")+"=>");
	    		  try {
	    			  
	    			  String Val = dt.getString("value").toString();
	    			  System.out.println(Val);
	    		 }
	    		  catch(Exception e) {
	    			 
	    			  Long l = dt.getLong("value");
	    			  System.out.println(l);
	    			 
	    	 }
	    	 }
	    	 
	  
	    	 
	    	 }
		 
		}   
		
		
		
//			
			
			 System.out.println("All fields");
			 
			   for(int z=0; z<= item.length()-1; z++) {
				      
			    	  JSONObject dt=  (JSONObject) item.get(z);

			    	String key= dt.getString("key");
			    	// System.out.println(dt.getString("key")+":");
			    	 if ((key.equalsIgnoreCase("HireDate"))) {
			    		 
			    	 
			    		  System.out.print(dt.getString("key")+"=>");
			    		  try {
			    		 System.out.println(dt.getString("value"));}
			    		  catch(Exception e) {
			    			  Long l = dt.getLong("value");
			    			  System.out.println(l);
			    			  
			    			  int i = dt.getInt("value");
			    			  System.out.println(i);
			    			  
			    	 }
			    	 }
			    	 
			  
			    	 
			    	 }
				 

			   
			   // all
			   
			   for(int z=0; z<= item.length()-1; z++) {
				      
			    	  JSONObject dt=  (JSONObject) item.get(z);

			    	String key= dt.getString("key");
			    	// System.out.println(dt.getString("key")+":");
			   
			    		 
			    	 
			    		  System.out.print(dt.getString("key")+"=>");
			    		  try {
			    		 System.out.println(dt.getString("value"));}
			    		  catch(Exception e) {
			    	 }
			    	 
			    	 
			  
			    	 
			    	 }
				 
		
		
		
		
		
	}
}
