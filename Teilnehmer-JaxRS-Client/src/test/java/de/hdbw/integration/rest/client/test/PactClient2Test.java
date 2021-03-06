package de.hdbw.integration.rest.client.test;

import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.client.RestTemplate;

import au.com.dius.pact.consumer.PactProviderRuleMk2;
import au.com.dius.pact.consumer.PactVerification;

/**
 * 
 * Consumer Driven Contracts with Pact
 * https://www.baeldung.com/pact-junit-consumer-driven-contracts
 *
 */
public class PactClient2Test {
	
//	@Rule
	public PactProviderRuleMk2 mockProvider
	  = new PactProviderRuleMk2("test_provider", "localhost", 9090, this);	
		
	@Test  
	@PactVerification
	public void givenGet_whenSendRequest_shouldReturn200WithProperHeaderAndBody() {	  
//	    // when
//	    ResponseEntity<String> response = new RestTemplate()
//	      .getForEntity(mockProvider.getConfig().url() + "/pact", String.class);
//	 
//	    // then
//	    assertTrue(response.getStatusCode().value()==200);
//	    assertTrue(response.getHeaders().get("Content-Type").contains("application/json"));
//	    assertTrue(response.getBody().contains("condition") 
//	    		&& response.getBody().contains("true")
//	    		&& response.getBody().contains("name")
//	    		&& response.getBody().contains("tom"));
	}		
}