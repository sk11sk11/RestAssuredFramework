package com.qa.api.gorest.restclient;

import java.io.File;
import java.util.Map;

import com.qa.api.gorest.util.TestUtil;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

/**
 * 
 * This class is having all http methods which call the apis and having generic
 * methods for getting the response and fetch the values from response.
 * 
 * @author shashank
 *
 */

public class RestClient {

	// HTTP Methods: GET POST PUT DELETE

	// static means: how would you call this method? Static methods/variables can be
	// called by class name directly: RestClient.doGet()
	// Get call means we need to create a request with contentType, baseURI,
	// basePath, token, query params (hashmap), boolean -->.log().all if you want to
	// generate log, i'll pass true othewise
	// i'll pass false.
	// Set the baseURI
	//

	/**
	 * This method is used to call GET API
	 * 
	 * @param contentType
	 * @param baseURI
	 * @param basePath
	 * @param token
	 * @param paramsMap
	 * @param log
	 * @return this method is returning response from the GET call
	 */
	public static Response doGet(String contentType, String baseURI, String basePath, Map<String, String> token,
			Map<String, String> paramsMap, boolean log) {

		if (setBaseURI(baseURI)) {
			RequestSpecification request = createRequest(contentType, token, paramsMap, log);
			return getResponse("GET", request, basePath);
		}

		return null;
	}

	/**
	 * This method is used to call POST API
	 * 
	 * @param contentType
	 * @param baseURI
	 * @param basePath
	 * @param token
	 * @param paramsMap
	 * @param log
	 * @param obj
	 * @return
	 */
	// for post call, we need a payload (convert POJO to JSON string...using Jackson
	// API)
	// pass in Users object in Object obj; Object can hold any class reference. It's
	// a superclass of all the classes in java.
	// go to util package and create a method in TestUtil class
	public static Response doPost(String contentType, String baseURI, String basePath, Map<String, String> token,
			Map<String, String> paramsMap, boolean log, Object obj) {

		if (setBaseURI(baseURI)) {
			RequestSpecification request = createRequest(contentType, token, paramsMap, log);
			addRequestPayload(request, obj);
			return getResponse("POST", request, basePath);
		}

		return null;
	}

	public static void addRequestPayload(RequestSpecification request, Object obj) {

		// the obj we are getting , typecast it to Map
		if (obj instanceof Map) {
			request.formParams((Map<String, String>) obj);
		} else {
			String jsonPayload = TestUtil.getSerializedJSON(obj);
			request.body(jsonPayload);
		}
	}

	private static boolean setBaseURI(String baseURI) {

		if (baseURI == null || baseURI.isEmpty()) {
			System.out.println("Please pass the correct baseURI.......");
			return false;
		}
		try {
			RestAssured.baseURI = baseURI;
			return true;
		} catch (Exception e) {
			System.out.println("Some exception occurred while assigning the baseURI with Rest Assured");
			return false;
		}

	}

	// next is to prepare the request.
	private static RequestSpecification createRequest(String contentType, Map<String, String> token,
			Map<String, String> paramsMap, boolean log) {

		// return type of given() is RequestSpecification

		RequestSpecification request;

		if (log) {
			request = RestAssured.given().log().all();
		} else {// false will come inside this block
			request = RestAssured.given();
		}

		// if (token != null) {
		if (token.size() > 0) {
			// request.header("Authorization", "Bearer " + token);
			// how to handle headers with Client-ID?
			// you can pass headers with Map object; you can pass header with a Map of
			// token.
			request.headers(token);
		}

		if (!(paramsMap == null)) {
			request.queryParams(paramsMap);
		}

		if (contentType != null) {
			if (contentType.equalsIgnoreCase("JSON")) {
				request.contentType(ContentType.JSON);
			} else if (contentType.equalsIgnoreCase("XML")) {
				request.contentType(ContentType.XML);
			} else if (contentType.equalsIgnoreCase("TEXT")) {
				request.contentType(ContentType.TEXT);
			} else if (contentType.equalsIgnoreCase("multipart")) {
				request.multiPart("image", new File("/Users/shashank/Desktop/Collections In Java.png"));
			}
		}

		return request;
	}

	// next hit the api

	private static Response getResponse(String httpMethod, RequestSpecification request, String basePath) {

		return executeAPI(httpMethod, request, basePath);
	}

	// execute the api
	private static Response executeAPI(String httpMethod, RequestSpecification request, String basePath) {

		Response response = null;

		switch (httpMethod) {
		case "GET":
			response = request.get(basePath);
			break;
		case "POST":
			response = request.post(basePath);
			break;
		case "PUT":
			response = request.put(basePath);
			break;
		case "DELETE":
			response = request.delete(basePath);
			break;

		default:
			System.out.println("Please pass the correct http method...");
			break;
		}

		return response;
	}

}
