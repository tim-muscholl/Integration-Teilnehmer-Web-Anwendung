package de.hdbw.integration.jaxrs.client;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.ClientResponseContext;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MultivaluedMap;

//import com.sun.jersey.api.client.Client;
//import com.sun.jersey.api.client.ClientResponse;
//import com.sun.jersey.api.client.WebResource;

public class TeilnehmerJaxRSClient2 {

	private static Client client = ClientBuilder.newClient();
	
	public static String getRequest(String getUrl, MultivaluedMap<String, String> params) {
		WebTarget webTarget = client.target(getUrl);
		if (params!=null && !params.isEmpty()) {
			while(params.entrySet().iterator().hasNext()) {
				String key = params.entrySet().iterator().next().getKey();
				List<String> values = params.entrySet().iterator().next().getValue();
				webTarget = webTarget.queryParam(key, values);
			}
		}
		String response = webTarget.request("application/json").get(String.class);
//		if(response.getStatus()!=200){
//			throw new RuntimeException("HTTP GET Error: "+ response.getStatus());
//		}	
//		String result = response.getEntityStream().toString();
		return response;
	}
/**	
	public static String postRequest(String postUrl, String inputData) {
		WebTarget webResource = client.target(postUrl);
		ClientResponseContext response = webResource.request("application/json")
				.post(new Entity(inputData, String.class), ClientResponseContext.class);
		if(response.getStatus()!=201){
			throw new RuntimeException("HTTP POST Error: "+ response.getStatus());
		}
		
		String result = response.getEntityStream().toString();
		return result;
	}
	
	public static String putRequest(String putUrl, String inputData) {
		WebTarget webResource = client.target(putUrl);
		ClientResponseContext response = webResource.request("application/json").put(inputData, ClientResponseContext.class);
		if(response.getStatus()!=200){
			throw new RuntimeException("HTTP PUT Error: "+ response.getStatus());
		}
		
		String result = response.getEntityStream().toString();
		return result;
	}
	**/
}
