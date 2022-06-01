package de.hdbw.integration.jaxrs.client.test;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import com.sun.jersey.api.client.Client;

import de.hdbw.integration.jaxrs.client.TeilnehmerJaxRSClient;
import de.hdbw.integration.jaxrs.client.TeilnehmerClient;


public class TeilnehmerRestClientTest {

	Client client = Client.create();
	
	String getUrl  		= "http://localhost:8080/Teilnehmer-JaxRS-Service/rest/teilnehmer/23/lesen";
	String postUrl 		= "http://localhost:8080/Teilnehmer-JaxRS-Service/rest/teilnehmer/speichern";
	String putUrl 		= "http://localhost:8080/Teilnehmer-JaxRS-Service/rest/teilnehmer/aendern";
	String getUrlSuchen = "http://localhost:8080/Teilnehmer-JaxRS-Service/rest/teilnehmer/suchen";
	
	/**
	 * Lesen eines Records mit der ID
	 */
	public void getRequest(){
		
		String id = "1";
		
		System.out.println("Finden einer ID (GET) " + id);
		
		String resultJsonString = TeilnehmerJaxRSClient.getRequest(getUrl, null);
		System.out.println("Response vom Server (GET): " + resultJsonString);

		Gson gson = new GsonBuilder().create();
		TeilnehmerClient gefoundTeilnehmerWithGivenId = gson.fromJson(resultJsonString, TeilnehmerClient.class);
	}
	
	/**
	 * Einfuegen eines Records und bekommt eine ID vergeben
	 */
	public void postRequest(){
		
		Gson gson = new GsonBuilder().create();

		TeilnehmerClient newTeilnehmer = new TeilnehmerClient();
		newTeilnehmer.setNachname("Postname");
		newTeilnehmer.setVorname("Postvorname");
		newTeilnehmer.setKurs("Kurs1");
		newTeilnehmer.setRolle("Rolle1");
		newTeilnehmer.setPasswort("postpwd");
		
		String inputJSon = gson.toJson(newTeilnehmer);
		
		String resultJsonString = TeilnehmerJaxRSClient.postRequest(postUrl, inputJSon);

		System.out.println("Response vom Server (POST): " + resultJsonString);
		
		TeilnehmerClient createdTeilnehmer = gson.fromJson(resultJsonString, TeilnehmerClient.class);
	}
	
	/**
	 * Aendern eines Records mit einer bestimmten ID
	 */
	public void putRequest(){
		
		Gson gson = new GsonBuilder().create();
		
		int id = 13;
		TeilnehmerClient changeteilnehmer = new TeilnehmerClient();
		changeteilnehmer.setId(id);
		changeteilnehmer.setNachname("Postname2");
		changeteilnehmer.setVorname("Postvorname");
		changeteilnehmer.setKurs("Kurs2");
		changeteilnehmer.setRolle("Rolle1");
		changeteilnehmer.setPasswort("postpwd");
		
		String inputJSon = gson.toJson(changeteilnehmer);

		String resultString = TeilnehmerJaxRSClient.putRequest(putUrl, inputJSon);

		System.out.println("Response vom Server (PUT): " + resultString);
	}
	
	/**
	 * Suchen Records
	 */
	public void getRequest4Suchen(){

		String resultJsonString = TeilnehmerJaxRSClient.getRequest(getUrlSuchen, null);
		System.out.println("Response vom Server (Suchen): " + resultJsonString);
		
		Gson gson = new GsonBuilder().create();
		
		Type typeOfT = new TypeToken<List<TeilnehmerClient>>(){}.getType();
		
		List<TeilnehmerClient> gefundenTeilnehmers = gson.fromJson(resultJsonString, typeOfT);
		
		System.out.println("Die Suche hat ergeben: " + gefundenTeilnehmers.size());
	}
	
	public static void main(String[] args) {
		TeilnehmerRestClientTest restClient = new TeilnehmerRestClientTest();
		
		// Senden post request
		restClient.postRequest();
		
		// Senden put request
		restClient.putRequest();
		
		// Senden get (Suchen) request
		restClient.getRequest4Suchen();
		
		// Senden get request
		restClient.getRequest();
		
		System.exit(0);
	}
}
