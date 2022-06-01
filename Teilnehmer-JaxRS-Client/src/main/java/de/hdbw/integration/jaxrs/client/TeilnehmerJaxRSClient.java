package de.hdbw.integration.jaxrs.client;

import javax.ws.rs.core.MultivaluedMap;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class TeilnehmerJaxRSClient {

	private static Client client = Client.create();
	
	public static String getRequest(String getUrl, MultivaluedMap<String, String> params) {
		WebResource webResource = client.resource(getUrl);
		if (params!=null && !params.isEmpty()) {
			webResource = webResource.queryParams(params);
		}
		ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);
		if(response.getStatus()!=200){
			throw new RuntimeException("HTTP GET Error: "+ response.getStatus());
		}	
		String result = response.getEntity(String.class);
		return result;
	}
	
	public static String postRequest(String postUrl, String inputData) {
		WebResource webResource = client.resource(postUrl);
		ClientResponse response = webResource.type("application/json").post(ClientResponse.class, inputData);
		if(response.getStatus()!=201){
			throw new RuntimeException("HTTP POST Error: "+ response.getStatus());
		}
		
		String result = response.getEntity(String.class);
		return result;
	}
	
	public static String putRequest(String putUrl, String inputData) {
		WebResource webResource = client.resource(putUrl);
		ClientResponse response = webResource.type("application/json").put(ClientResponse.class, inputData);
		if(response.getStatus()!=200){
			throw new RuntimeException("HTTP PUT Error: "+ response.getStatus());
		}
		
		String result = response.getEntity(String.class);
		return result;
	}
	
}
