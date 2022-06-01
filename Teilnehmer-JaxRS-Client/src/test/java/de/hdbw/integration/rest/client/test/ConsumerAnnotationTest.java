package de.hdbw.integration.rest.client.test;

import au.com.dius.pact.consumer.*;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.PactFragment;
import org.junit.*;
import static org.junit.Assert.assertEquals;

/**
 * 
 * Ein Consumer-Test mit Annotations und Rule
 *
 */
public class ConsumerAnnotationTest {
	@Rule  public PactProviderRule mockProvider = new PactProviderRule("My Annotation Provider", this);
	
	@Pact(consumer = "My Annotation Consumer")
	public PactFragment createFragment(PactDslWithProvider builder) {
		return builder.uponReceiving("a root request").method("GET").path("/")      
				.willRespondWith().status(200).body("Hello, world anno!")      
				.toFragment();  
		}
	
	@Test  
	@PactVerification  
	public void runTest() throws Exception {    
		final Consumer consumer = Consumer.of(mockProvider.getConfig().url());    
		assertEquals(consumer.run(), "Hello, world anno!");  
	}
	
}