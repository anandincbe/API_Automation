package com.TestUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.jayway.jsonpath.JsonPath;

public class TestUtil {
	
	
	public static String getValueFronJasonArray(JSONObject responsejson,String jString) throws JSONException {
		
		Object obj =  responsejson;
		for(String s:jString.split("/")) {
			if(!s.isEmpty()) 
				{
					if(!  (s.contains("[") || s.contains("]")) ) {
						obj = ((JSONObject) obj).get(s);
					}else if (  (s.contains("[") || s.contains("]")) ) {
						
						obj = ((JSONArray)((JSONObject)obj).get(s.split("\\[")[0])).get(Integer.parseInt(s.split("\\[")[1].replaceAll("]", "")));
					}
				}
		}
		return obj.toString();
	}
	
	
	
	public HashMap<String, String> extractResponseToMap(CloseableHttpResponse httpResponse) throws ParseException, IOException {
		HashMap<String, String > responseMap =  new HashMap<String, String>();
		
		responseMap.put("STATUSCODE", String.valueOf(httpResponse.getStatusLine().getStatusCode()));
		responseMap.put("STATUSCODEMESSAGE", httpResponse.getStatusLine().getReasonPhrase());
		responseMap.put("HTTPPROTOCOLVERSION", String.valueOf(httpResponse.getStatusLine().getProtocolVersion()));			
		String responseBody = EntityUtils.toString(httpResponse.getEntity());
		responseMap.put("BODY", responseBody);
		
		try {
			Header[] headers = httpResponse.getAllHeaders();
			
			JSONObject responceHeaderJson = new JSONObject();
			for (Header header : headers) {
				responseMap.put(header.getName().toUpperCase(), header.getValue());
				responceHeaderJson.putOnce(header.getName(), header.getValue());
				
			}
			responseMap.put("RESPONSEHEADER", responceHeaderJson.toString());
			responceHeaderJson = null;
		} catch (Exception ex) {
			responseMap.put("HEADEREXCEPTIONMESSAGE", ex.getMessage());
		}
		
		return responseMap;
	}
	public List<String> getValueFronJsonArray(HashMap<String, String>  response) {
		
		return JsonPath.read(response.get("BODY"), "$.data[*].first_name");
		
	}

}
