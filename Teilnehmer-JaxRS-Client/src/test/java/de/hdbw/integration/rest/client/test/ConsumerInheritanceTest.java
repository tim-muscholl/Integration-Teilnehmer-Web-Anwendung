package de.hdbw.integration.rest.client.test;

import au.com.dius.pact.consumer.ConsumerPactTest;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.PactFragment;
import java.io.IOException;
import static org.junit.Assert.assertEquals;

/**
 * 
 *  Ein Consumer-Test mit Vererbung 
 *
 */
public class ConsumerInheritanceTest extends ConsumerPactTest {
	@Override  protected PactFragment createFragment(PactDslWithProvider builder) { return builder.uponReceiving("a root request").method("GET").path("/").willRespondWith().status(200).body("Hello, world ding!").toFragment();  }

	@Override  protected String providerName() {    
	//	return "My Spring Boot Provider";  
		return "My-Pack-Ding";  
	}

	@Override  protected String consumerName() { return "My Consumer Ding"; }

	@Override
	protected void runTest(String url) throws IOException {
		final Consumer consumer = Consumer.of(url);
		assertEquals(consumer.run(), "Hello, world ding!");
	}
}