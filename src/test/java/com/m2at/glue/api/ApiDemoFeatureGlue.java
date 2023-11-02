package com.m2at.glue.api;


import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import org.junit.Assert;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class ApiDemoFeatureGlue {	
	
	String baseURI = "https://jsonplaceholder.typicode.com";    
	String basePath = "/users";
	
	private RequestSpecification requestSpec;
	private ResponseSpecification responseSpec;
	private Response response;
	
	public RequestSpecification getRequestSpecification() {
		return requestSpec;
	}

	public void setRequestSpecification(RequestSpecification requestSpecification) {
		this.requestSpec = requestSpecification;
	}


	public ResponseSpecification getResponseSpecification() {
		return responseSpec;
	}


	public void setResponseSpecification(ResponseSpecification responseSpecification) {
		this.responseSpec = responseSpecification;
	}

	public void setRequestSpecBuilder() {
		requestSpec = new RequestSpecBuilder().
										setBaseUri(baseURI).
										setAccept(ContentType.JSON).
										setContentType(ContentType.JSON).
										build();	
		
		setRequestSpecification(requestSpec);
	}

	public void setResponseSpecBuilder() {
		responseSpec = new ResponseSpecBuilder().
									expectContentType(ContentType.JSON).
									build();
		
		setResponseSpecification(responseSpec);
	}

	@Before("@API")
	public void beforeScenario() {
		setRequestSpecBuilder();
		setResponseSpecBuilder();
	}
	
	@Given("the API for jsonplaceholder")
	public void the_API_for_jsonplaceholder() {
		System.out.println("*** Given the API for jsonplaceholder.." );
		requestSpec = RestAssured.
								given().
									spec(getRequestSpecification());
	}

	@When("I make a request to API")
	public void i_make_a_request_to_API() {
		System.out.println("*** When I make a request to API.." );
		response = requestSpec.
												when().
													get(basePath);
		
		System.out.println("Response: " + response.getBody().prettyPrint());
	}

	@Then("the status code should be {int}")
	public void the_status_code_should_be(Integer statusCode) {
		System.out.println("*** Then the status code should be 200.." );
		try {
			response.
					then().
						spec(getResponseSpecification()).
						assertThat().statusCode(is(equalTo(statusCode)));
			
			System.out.println("*** Test Passed" );
		} catch (AssertionError e) {
			System.out.println("*** Test Failed" );
			Assert.fail();
		}
	}

	
}
