package com.qa.test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.TestUtil.TestUtil;
import com.base.Base;
import com.client.restClient;
import com.jayway.jsonpath.JsonPath;

/**
 * Unit test for simple App.
 */
public class getAPITest extends Base {
	Base base;
	String serviceUrl,serviceURLGetOnly;
	String apiurl;
	String url;
	restClient restClient;
	CloseableHttpResponse closeablehttpResponse;
	TestUtil testUtil;

	@BeforeMethod
	public void setup() throws ClientProtocolException, IOException, JSONException {

		base = new Base();
		serviceUrl = prop.getProperty("URL");
		serviceURLGetOnly = prop.getProperty("serviceURL_GET");
		apiurl = prop.getProperty("serviceURL_GET_Array");
		url = serviceUrl + apiurl;
		
	}
	
	@Test
	public void getAPIOnly() {

		url = serviceUrl+serviceURLGetOnly;
		restClient = new restClient();
		try {
			closeablehttpResponse = restClient.get(url);
			
			// status code
			int statusCode = closeablehttpResponse.getStatusLine().getStatusCode();
			System.out.println(statusCode);
			System.out.println(closeablehttpResponse.getStatusLine().getProtocolVersion());
			
			
			Assert.assertEquals(statusCode, 200, "Status code is not 200");
			// reading string
			String response = EntityUtils.toString(closeablehttpResponse.getEntity(), "UTF-8");
			System.out.println(response);

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

	@Test
	public void getAPIWithoutHeader() {
		url = apiurl+serviceURLGetOnly;
		restClient = new restClient();
		try {
			closeablehttpResponse = restClient.get(url);
			// status code
			int statusCode = closeablehttpResponse.getStatusLine().getStatusCode();
			System.out.println(statusCode);
			// System.out.println(closeablehttpResponse.getStatusLine().getReasonPhrase().getProtocolVersion());
			Assert.assertEquals(statusCode, 200, "Status code is not 200");
			// reading string
			String response = EntityUtils.toString(closeablehttpResponse.getEntity(), "UTF-8");
			System.out.println(response);
			JSONObject jsonobject = new JSONObject(response);

			System.out.println("response json from api" + jsonobject);

			// get all header array
			Header[] headerArray = closeablehttpResponse.getAllHeaders();

			HashMap<String, String> headerMap = new HashMap<String, String>();

			for (Header header : headerArray) {
				headerMap.put(header.getName(), header.getValue());
			}
			System.out.println("Header Map -->" + headerMap);

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

	@Test(enabled = true)
	public void getAPIWithHeader() {
		restClient = new restClient();
		try {
			// adding headers
			HashMap<String, String> header = new HashMap<String, String>();
			
			header.put("Content-Type", "application/json");

			closeablehttpResponse = restClient.get(url, header);
			// status code
			int statusCode = closeablehttpResponse.getStatusLine().getStatusCode();
			System.out.println(statusCode);
			Assert.assertEquals(statusCode, 200, "Status code is not 200");
			// reading string
			String response = EntityUtils.toString(closeablehttpResponse.getEntity(), "UTF-8"); // characterSet format
			JSONObject jsonobject = new JSONObject(response);
			System.out.println("response json from api" + jsonobject);
			// get all header array
			Header[] headerArray = closeablehttpResponse.getAllHeaders();

			HashMap<String, String> headerMap = new HashMap<String, String>();

			for (Header headers : headerArray) {
				headerMap.put(headers.getName(), headers.getValue());
			}
			System.out.println("Header Map -->" + headerMap);

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

	@Test
	public void getAPITestWithArrays() throws ParseException, ClientProtocolException, IOException, JSONException {
		Properties prop = new Properties();
		testUtil = new TestUtil();
		restClient = new restClient();
		FileInputStream fis = new FileInputStream(
				System.getProperty("user.dir") + "/src/test/java/testData/postData.properties");
		try {
			prop.load(fis);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// closeablehttpResponse object return
		closeablehttpResponse = restClient.get(url);
		System.out.println(closeablehttpResponse.getStatusLine().getStatusCode());
		HashMap<String, String> ResponseMap = new HashMap();
		ResponseMap = testUtil.extractResponseToMap(closeablehttpResponse);

		// prnt value in haspmap
		for (Map.Entry<String, String> map : ResponseMap.entrySet()) {
			System.out.println(map.getKey() + "   " + map.getValue());
		}

		System.out.println(ResponseMap.get("BODY"));

		List<String> list = new ArrayList<String>();
		list = testUtil.getValueFronJsonArray(ResponseMap);

		System.out.println(list);

		/*
		 * // Object to Json String String jsonString =
		 * EntityUtils.toString(closeablehttpResponse.getEntity(),"UTF-8");
		 * System.out.println(jsonString);
		 * 
		 * 
		 * 
		 * //Json Object JSONObject responseJson = new JSONObject(jsonString);
		 * System.out.println("Response JSON from API---> "+ responseJson);
		 */

		// JsonPath jp = JsonPath.read(responseBody, "$.data[*]");
		// System.out.println(jp);
		// TestUtil.getValueFronJasonArray(responseJson, "/data[0]/last_name");

	}
}
