package de.hdbw.integration.rest.client.test;

import javax.ws.rs.client.*;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

/**
 * Ein Consumer mittels JAX-RS-Client-AP
 * 
 * https://www.sigs-datacom.de/uploads/tx_dmjournals/vitz_JS_04_16_TaTZ.pdf
 *
 */
public final class Consumer2 {
	private final WebTarget target;

	public Consumer2(WebTarget target) {
		this.target = target;
	}

	public String runGet() {    
		return target.path("/pact").request(TEXT_PLAIN)
				.get(String.class);  
	}

	public String runPost() {    
		return target.path("/pact").request(TEXT_PLAIN)
				.post(Entity.entity("{\"name\": \"Michael\"}", APPLICATION_JSON), String.class);  
	}

	public Future<Response> runGetAsync() {    
		return target.path("/pact").request(TEXT_PLAIN).async().get();
	}

	public static Consumer2 of(String uri) {    
		return new Consumer2(ClientBuilder.newClient().target(uri));  
	}

	public static void main(String[] args) {    
		final Consumer2 consumer = Consumer2.of("http://localhost:8080");    
		System.out.println(consumer.runGet()); 
		System.out.println(consumer.runPost()); 
		
		Future<Response> future = consumer.runGetAsync();
		try {
			Response resp = future.get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} 
}