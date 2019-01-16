package com.qa.test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.base.Base;
import com.client.restClient;

public class putAPITest {

	Base base;
	String serviceUrl ;
	String apiurl ;
	String url;
	restClient restClient ;
	CloseableHttpResponse closeablehttpResponse;
	
	@BeforeMethod
	public void  setup() throws ClientProtocolException, IOException, JSONException {
		Properties prop  = new Properties();
		base  = new Base();
		 serviceUrl=prop.getProperty("URL");
		apiurl= prop.getProperty("service_DELETE");
		 url= serviceUrl+apiurl;	
	}
	
	@Test
	public void putAPI() {
		
		restClient = new restClient();
		try {
			closeablehttpResponse =restClient.delete(url);
		//status code	
			int statusCode=closeablehttpResponse.getStatusLine().getStatusCode();
			System.out.println(statusCode);
			//System.out.println(closeablehttpResponse.getStatusLine().getReasonPhrase().getProtocolVersion());
			Assert.assertEquals(statusCode, 204,"Status code is not 200");
		//reading string
		String response=	EntityUtils.toString(closeablehttpResponse.getEntity(),"UTF-8");
		System.out.println(response);
		JSONObject jsonobject = new JSONObject(response);
		
		System.out.println("response json from api"+ jsonobject);
		
		//get all header array
		Header[] headerArray=closeablehttpResponse.getAllHeaders();
		
		HashMap headerMap= new HashMap<String,String>();
		
			for (Header header : headerArray) {
				headerMap.put(header.getName(), header.getValue());
			}
			System.out.println("Header Map -->"+ headerMap);
			
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	@Test(enabled = false)
	public void getAPIWithHeader() {
		restClient = new restClient();
		try {
			//adding headers
			HashMap<String, String> header = new HashMap<String, String>();
			header.put("Content-Type", "application/json");
			
			
			closeablehttpResponse =restClient.get(url,header);
		//status code	
			int statusCode=closeablehttpResponse.getStatusLine().getStatusCode();
			System.out.println(statusCode);
			Assert.assertEquals(statusCode, 200,"Status code is not 200");
		//reading string
		String response=	EntityUtils.toString(closeablehttpResponse.getEntity(),"UTF-8"); //characterSet format
		JSONObject jsonobject = new JSONObject(response);
		System.out.println("response json from api"+ jsonobject);
		//get all header array
		Header[] headerArray=closeablehttpResponse.getAllHeaders();
		
		HashMap headerMap= new HashMap<String,String>();
		
			for (Header headers : headerArray) {
				headerMap.put(headers.getName(), headers.getValue());
			}
			System.out.println("Header Map -->"+ headerMap);
			
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



}
