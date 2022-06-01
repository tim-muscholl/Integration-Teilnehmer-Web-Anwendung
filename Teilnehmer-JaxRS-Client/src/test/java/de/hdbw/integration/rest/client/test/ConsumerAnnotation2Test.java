package de.hdbw.integration.rest.client.test;

import au.com.dius.pact.consumer.*;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.RequestResponsePact;

import org.junit.*;
import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * Consumer Driven Contracts with Pact
 * https://www.baeldung.com/pact-junit-consumer-driven-contracts
 *
 */
public class ConsumerAnnotation2Test {
	
	@Rule
	public PactProviderRuleMk2 mockProvider
	  = new PactProviderRuleMk2("MY test_provider Anno2", "localhost", 9090, this);	
	
	@Pact(consumer = "MY test_consumer Anno2")
	public RequestResponsePact createPact(PactDslWithProvider builder) {
	    Map<String, String> headers = new HashMap();
	    headers.put("Content-Type", "application/json");
	 
	    return builder
	      .given("test GET")
	        .uponReceiving("GET REQUEST")
	        .path("/pact")
	        .method("GET")
	      .willRespondWith()
	        .status(200)
	        .headers(headers)
	        .body("{\"condition\": true, \"name\": \"tom\"}")
	    //    .toPact()
	    //    ;
	    //** POST Method */
	    //
		.given("test POST")
		.uponReceiving("POST REQUEST")
		  .method("POST")
		  .headers(headers)
		  .body("{\"name\": \"Michael\"}")
		  .path("/pact")
		.willRespondWith()
		  .status(201)
		  .body("{\"mldg\":\"ok\"}")
		.toPact();
	     
	}
	
	@Test  
	@PactVerification  
	public void runTest() throws Exception {    
		final Consumer2 consumer = Consumer2.of(mockProvider.getConfig().url());  
		String result = consumer.runGet();
		assertEquals(result.trim(), "{\"condition\":true,\"name\":\"tom\"}");  
		
		String result1 = consumer.runPost();
		assertEquals(result1.trim(), "{\"mldg\":\"ok\"}");  
	}
		
}