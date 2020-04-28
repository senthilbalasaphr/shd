package com.sfec.freeman.restax;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
 
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
 

public class RemoveTaxGroup {
	

	public static void main(String[] args)
{
	try {
		deleteEmployee();
	} catch (ClientProtocolException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
}
	 public static void deleteEmployee() throws ClientProtocolException, IOException {
	 
	 String deleteEndpoint = null;
	 
	 CloseableHttpClient httpclient = HttpClients.createDefault();
	 
	 String authHeader = "Basic " + new String("U0ZFQ19FRV9PQVNTQGZyZWVtYW50ZXN0OlNGRUNfRUVfT0FTUzAx");

	
	 
	 File file = new File("/Users/baps/Documents/FREEMAN/Workspace/Cutover/test/remove20200101.txt");
		FileReader fr = null;
		try {
			fr = new FileReader(file);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		BufferedReader br = new BufferedReader(fr);
		String line;
		try {
			while ((line = br.readLine()) != null) {
				// process the line

				String[] attributes = line.split("\t");
				
				System.out.println("attributes " + attributes[0]+","+attributes[1]);
				
				deleteEndpoint = "https://api8preview.sapsf.com:443/odata/v2/cust_ResTaxAreas(effectiveStartDate=datetime'2020-01-01T00:00:00',externalCode='"+attributes[0]+"')/$links/cust_TotaxGroupKey(effectiveStartDate=datetime'2020-01-01T00:00:00',externalCode='"+attributes[1]+"')";
				 HttpDelete httpDelete = new HttpDelete(deleteEndpoint);
				 System.out.println("Executing request " + httpDelete.getRequestLine());
				 httpDelete.setHeader("authorization", authHeader);
				HttpResponse response = httpclient.execute(httpDelete);

				// System.out.println(response.getStatusLine().getStatusCode());

				 

				 if (response.getStatusLine().getStatusCode() != 204) { 
					 System.out.println("Error");
				 }
				 else {
					
					 System.out.println("Successful");
				 } 
				 

				 } 
			
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Read record and update

		
	 
	 
		}
	 
	
	 
	 
	 
	// BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent()))); 
	 
	 //Throw runtime exception if status code isn't 200 
	 
	
	 
	
}
