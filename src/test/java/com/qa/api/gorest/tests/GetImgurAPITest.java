package com.qa.api.gorest.tests;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.api.gorest.restclient.RestClient;
import com.qa.api.gorest.util.Token;

import io.restassured.response.Response;

public class GetImgurAPITest {

	Map<Object, Object> tokenMap;
	String accessToken;
	String accountUsername;
	String refreshToken;

	@BeforeMethod
	public void setup() {
		tokenMap = Token.getAccessToken();
		accessToken = tokenMap.get("access_token").toString();
		accountUsername = tokenMap.get("account_username").toString();
		refreshToken = tokenMap.get("refresh_token").toString();

	}

	@Test
	public void getAccountBlockStatusTest() {

		Map<String, String> authTokenMap = Token.getAuthToken();
		Response response = RestClient.doGet(null, "https://api.imgur.com", "/account/v1/" + accountUsername + "/block",
				authTokenMap, null, true);

		System.out.println(response.statusCode());
		System.out.println(response.prettyPrint());
	}
	
	@Test
	public void getAccountImagesTest() {

		Map<String, String> authTokenMap = Token.getAuthToken();
		Response response = RestClient.doGet(null, "https://api.imgur.com", "/3/account/me/images",
				authTokenMap, null, true);

		System.out.println(response.statusCode());
		System.out.println(response.prettyPrint());
	}
	
	
	
	@Test 
	public void uploadImagePostAPITest() {
		
		//when uploading image, contentType will be multipart.
		//Two problems:
		//1.  token has Client-ID so, createRequest method should handle Client-ID instead of Bearer. 
		//2.  also json is not there , but form params are there so how to handle it?  --addRequestPayload method should handle it.
		Map<String, String> clientIdMap = Token.getClientId();
		
		Map<String, String> formMap = new HashMap<String, String>();
		formMap.put("title", "test title API");
		formMap.put("description", "test description API");
		
		Response response = RestClient.doPost("multipart", "https://api.imgur.com", "/3/upload", clientIdMap, null, true, formMap);
		
		System.out.println(response.statusCode());
		System.out.println(response.prettyPrint());
		
	}

}
