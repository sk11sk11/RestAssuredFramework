package com.qa.api.gorest.util;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;
import java.util.Map;

import io.restassured.path.json.JsonPath;

public class Token {

	// declare at class level:
	public static Map<Object, Object> appTokenMap;
	// initialized a blank tokenMap:
	public static Map<String, String> tokenMap = new HashMap<String, String>();

	public static String clientId = "68ee0603499e19b";

	public static Map<Object, Object> getAccessToken() {
		// this is the same as Generate Access Token POST call in Postman.
		Map<String, String> formParams = new HashMap<String, String>();
		formParams.put("refresh_token", "ee4dd22c9df21e497b301aba07660727122e9eb5");
		formParams.put("client_id", "68ee0603499e19b");
		formParams.put("client_secret", "ae5ec631b651efd6890002013b9a7b53b535d0a4");
		formParams.put("grant_type", "refresh_token");

		JsonPath tokenJson = given().formParams(formParams).when().post("https://api.imgur.com/oauth2/token").then()
				.extract().jsonPath();

		// gives you complete json when giving ""
		System.out.println(tokenJson.getMap(""));

		// return tokenJson.getMap("");
		appTokenMap = tokenJson.getMap("");
		return appTokenMap;

	}

	public static Map<String, String> getAuthToken() {
		String authToken = appTokenMap.get("access_token").toString();
		System.out.println("Auth Token =====> " + authToken);
		// authToken needs to be appended with Bearer. so, create another Map.
		tokenMap.put("Authorization", "Bearer " + authToken);
		return tokenMap;
	}

	public static Map<String, String> getClientId() {
		System.out.println("Client id is  =====> " + clientId);
		tokenMap.put("Authorization", "Client-ID " + clientId);
		return tokenMap;
	}

}
