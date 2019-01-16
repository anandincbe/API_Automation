package com.client;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONException;
import org.json.simple.JSONObject;

public class restClient {

	// get method call
	public CloseableHttpResponse get(String url) throws ClientProtocolException, IOException, JSONException {

		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(url);
		CloseableHttpResponse closeablehttpResponse = httpClient.execute(httpGet);
		return closeablehttpResponse;

	}

	// with headers
	public CloseableHttpResponse get(String url, HashMap<String, String> headerMap)
			throws ClientProtocolException, IOException, JSONException {

		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(url);

		for (Map.Entry<String, String> entry : headerMap.entrySet()) {
			httpGet.addHeader(entry.getKey(), entry.getValue());
		}

		CloseableHttpResponse closeablehttpResponse = httpClient.execute(httpGet);

		return closeablehttpResponse;

	}

	// post MEthod

	/*
	 * public CloseableHttpResponse post(String url,JSONObject
	 * json,HashMap<String,String> headerMap) throws ClientProtocolException,
	 * IOException { CloseableHttpClient httpClient = HttpClients.createDefault();
	 * HttpPost httpPost = new HttpPost(url); httpPost.setEntity(new
	 * StringEntity(json));//adding payload //headers for(Map.Entry<String,String>
	 * entry: headerMap.entrySet()){
	 * httpPost.addHeader(entry.getKey(),entry.getValue()); } CloseableHttpResponse
	 * closeablehttpResponse=httpClient.execute(httpPost); return
	 * closeablehttpResponse; }
	 */

	public CloseableHttpResponse post(String url, String entityString, HashMap<String, String> headerMap)
			throws ClientProtocolException, IOException {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		//adding payload for post call
		httpPost.setEntity(new StringEntity(entityString));// adding payload
		// adding header to your post call
		for (Map.Entry<String, String> entry : headerMap.entrySet()) {
			httpPost.addHeader(entry.getKey(), entry.getValue());
		}		
		//executing your post call
		CloseableHttpResponse closeablehttpResponse = httpClient.execute(httpPost);
		
		//reuring post response
		return closeablehttpResponse;
	}

	// delete method call
	public CloseableHttpResponse delete(String url) throws ClientProtocolException, IOException, JSONException {

		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpDelete httpGet = new HttpDelete(url);
		CloseableHttpResponse closeablehttpResponse = httpClient.execute(httpGet);

		return closeablehttpResponse;

	}

	// put or update method call
	public CloseableHttpResponse put(String url) throws ClientProtocolException, IOException, JSONException {

		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPut httpGet = new HttpPut(url);
		CloseableHttpResponse closeablehttpResponse = httpClient.execute(httpGet);

		return closeablehttpResponse;

	}
}
