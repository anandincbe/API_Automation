package com.qa.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.base.Base;
import com.client.restClient;
import com.fasterxml.jackson.databind.ObjectMapper;

public class postAPI extends Base {
	
	Base base;
	String serviceUrl ;
	String apiurl ;
	String url;
	restClient restClient ;
	CloseableHttpResponse closeablehttpResponse;
	
	@BeforeMethod
	public void  setup() throws ClientProtocolException, IOException, JSONException {
		
		base  = new Base();
		 serviceUrl=prop.getProperty("URL");
		apiurl= prop.getProperty("serviceURL");
		 url= serviceUrl+apiurl;
		
		
	}
	
	@Test
	public void postAPITest() throws ParseException, ClientProtocolException, IOException {
		  Properties prop  = new Properties();
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"/src/test/java/testData/postData.properties");
		try {
			prop.load(fis);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		restClient =  new restClient();
		HashMap<String,String> headerMap = new  HashMap<String, String>();
		headerMap.put("Content-Type", "application/json");
		String entityString = prop.getProperty("test1");
		//Jackson API
		
		/*JSONParser parser = new JSONParser(); 
		JSONObject json = (JSONObject) parser.parse(prop.getProperty("test1"));
		System.out.println(json);*/
		closeablehttpResponse = restClient.post(url, entityString, headerMap);
		int statusCode = closeablehttpResponse.getStatusLine().getStatusCode();
		System.out.println(statusCode);
		System.out.println(closeablehttpResponse.getStatusLine().getProtocolVersion());
		Assert.assertEquals(statusCode, 201, "Status code is not 200");
		// reading string
		String response = EntityUtils.toString(closeablehttpResponse.getEntity(), "UTF-8");
		System.out.println(response);

	}

}
