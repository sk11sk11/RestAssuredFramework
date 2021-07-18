package com.qa.api.gorest.tests;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

import com.qa.api.gorest.restclient.RestClient;
import com.qa.api.gorest.util.Token;

import io.restassured.response.Response;

public class GetUserTest {
	
	String baseURI = "https://gorest.co.in";
	String basePath = "/public-api/users";
	String token = "dced938b7dc1d5d936b985c6c0a23ff010c2b1e52126ac7f90412500c1bd7d1b";
	
	@Test(priority = 1)
	public void getAllUserListAPITest() {
		Map<String, String> authTokenMap = new HashMap<String, String>();
		authTokenMap.put("Authorization", "Bearer "+ token);
		
		Response response = RestClient.doGet("JSON", baseURI, basePath, authTokenMap, null, true);
		System.out.println(response.getStatusCode());
		System.out.println(response.prettyPrint());
		
	}
	
	@Test(priority = 2)
	public void getUserWithQueryParamsAPITest() {
		Map<String, String> authTokenMap = new HashMap<String, String>();
		authTokenMap.put("Authorization", "Bearer "+ token);
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("name", "Chandak Varrier");
		params.put("gender","Female");
		
		Response response = RestClient.doGet("JSON", baseURI, basePath, authTokenMap, params, true);
		System.out.println(response.getStatusCode());
		System.out.println(response.prettyPrint());
		
	}

}
