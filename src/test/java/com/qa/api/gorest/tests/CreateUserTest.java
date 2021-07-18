package com.qa.api.gorest.tests;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.api.gorest.pojo.User;
import com.qa.api.gorest.restclient.RestClient;
import com.qa.api.gorest.util.ExcelUtil;

import io.restassured.response.Response;

public class CreateUserTest {
	
	String baseURI = "https://gorest.co.in";
	String basePath = "/public-api/users";
	String token = "dced938b7dc1d5d936b985c6c0a23ff010c2b1e52126ac7f90412500c1bd7d1b";
	
	
	//the return type of dataprovider is always a 2-D array
	@DataProvider
	public Object[][] getUserData() {
		Object userdata[][] = ExcelUtil.getTestData("userdata");
		return userData;
	}
	
	//create a connection to DataProvider
	@Test(dataProvider = "getUserData")
	//@Test
	public void createUserAPIPOSTTest(String name, String gender, String email, String status) {
		
		Map<String, String> authTokenMap = new HashMap<String, String>();
		authTokenMap.put("Authorization", "Bearer "+ token);
		
		//to get the obj, create a POJO class;  under src/main/java create a package
		//User user = new User("Aditya Soman", "Male", "aditya234@gmail.com", "Active");
		
		User user = new User(name, gender, email, status);
		Response response = RestClient.doPost("JSON", baseURI, basePath, authTokenMap, null, true, user);
		
		System.out.println(response.getStatusCode());
		System.out.println(response.prettyPrint());
		
		
	}
	
	

}
