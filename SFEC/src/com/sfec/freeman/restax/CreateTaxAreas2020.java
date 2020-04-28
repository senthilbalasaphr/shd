package com.sfec.freeman.restax;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
 
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
 

public class CreateTaxAreas2020 {
	

	public static void main(String[] args)
{
	try {
		CreateTaxAreas();
	} catch (ClientProtocolException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
}
	 public static void CreateTaxAreas() throws ClientProtocolException, IOException {
	 
	 String deleteEndpoint = "https://api8preview.sapsf.com:443/odata/v2/cust_ResTaxAreas(effectiveStartDate=datetime'2020-01-01T00:00:00',externalCode='GA')/$links/cust_TotaxGroupKey(effectiveStartDate=datetime'2020-01-01T00:00:00',externalCode='1838')";
	 
	 CloseableHttpClient httpclient = HttpClients.createDefault();
	 
	 String authHeader = "Basic " + new String("U0ZFQ19FRV9PQVNTQGZyZWVtYW50ZXN0OlNGRUNfRUVfT0FTUzAx");

	 
	 
	 
	 HttpDelete httpDelete = new HttpDelete(deleteEndpoint);
	 System.out.println("Executing request " + httpDelete.getRequestLine());
	 httpDelete.setHeader("authorization", authHeader);
	 
	 HttpResponse response = httpclient.execute(httpDelete);
	 
	 
	 
	// BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent()))); 
	 
	 //Throw runtime exception if status code isn't 200 
	 
	 System.out.println(response.getStatusLine().getStatusCode());

	 

	 if (response.getStatusLine().getStatusCode() != 204) { 
		 System.out.println("Error");
	 }
	 else {
		
		 System.out.println("Successful");
	 } 
	 

	 } 
	 
	
}
