package de.hdbw.integration.rest.client.test;

import javax.ws.rs.client.*;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

/**
 * Ein Consumer mittels JAX-RS-Client-AP
 * 
 * https://www.sigs-datacom.de/uploads/tx_dmjournals/vitz_JS_04_16_TaTZ.pdf
 *
 */
public final class Consumer {
	private final WebTarget target;

	public Consumer(WebTarget target) {
		this.target = target;
	}

	public String run() {    
		return target.path("/").request(TEXT_PLAIN).get(String.class);  
	}

	public static Consumer of(String uri) {    
		return new Consumer(ClientBuilder.newClient().target(uri));  
	}

	public static void main(String[] args) {    
		final Consumer consumer = Consumer.of("http://localhost:8080");    
		System.out.println(consumer.run()); 
	} 
}