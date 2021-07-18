package com.qa.api.gorest.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestUtil {
	
	/**
	 * This method is used to convert POJO object to a JSON string...
	 * @param obj
	 * @return jsonString
	 */

	//this method takes in any Object obj, which it will convert to JSON
	public static String getSerializedJSON(Object obj) {
		
		
		ObjectMapper mapper = new ObjectMapper();

		String jsonString = null;
		try {
			jsonString = mapper.writeValueAsString(obj);
			System.out.println("JSON BODY PAYLOAD ======> "+jsonString);
		} catch (JsonProcessingException e) {
			
			e.printStackTrace();
		}
		return jsonString;
	}

}
