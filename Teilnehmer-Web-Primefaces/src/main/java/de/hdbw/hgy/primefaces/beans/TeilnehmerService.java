package de.hdbw.hgy.primefaces.beans;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.ws.rs.core.MultivaluedMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sun.jersey.core.util.MultivaluedMapImpl;

import de.hdbw.integration.jaxrs.client.TeilnehmerJaxRSClient;

@ManagedBean(name = "teilnehmerService")
@ApplicationScoped
public class TeilnehmerService {
     
	String baseUrl  = "http://localhost:8080/Teilnehmer-JaxRS-Service/rest";
	
    public List<Teilnehmer> allTeilnehmers() {
    	
    	String getUrlSuchen = baseUrl + "/teilnehmer/suchen";
		String resultJsonString = TeilnehmerJaxRSClient.getRequest(getUrlSuchen, null);
		System.out.println("Response vom Server (Suchen): " + resultJsonString);
		
		Gson gson = new GsonBuilder().create();
		Type typeOfT = new TypeToken<List<Teilnehmer>>(){}.getType();
		List<Teilnehmer> gefundenTeilnehmers = gson.fromJson(resultJsonString, typeOfT);
		return gefundenTeilnehmers;
    }

	public int saveNewTeilnehmer(Teilnehmer newTeilnehmer) {
		Gson gson = new GsonBuilder().create();
		String inputJSon = gson.toJson(newTeilnehmer);
		
		String postUrl = baseUrl + "/teilnehmer/speichern";
		String resultJsonString = TeilnehmerJaxRSClient.postRequest(postUrl, inputJSon);

		System.out.println("Response vom Server (POST): " + resultJsonString);
		
		Teilnehmer createdTeilnehmer = gson.fromJson(resultJsonString, Teilnehmer.class);
		
		return createdTeilnehmer.getId();
	}

	public String aendernTeilnehmer(Teilnehmer changeteilnehmer) {
		
		Gson gson = new GsonBuilder().create();
		String inputJSon = gson.toJson(changeteilnehmer);
		
		String putUrl = baseUrl + "/teilnehmer/aendern";

		String resultString = TeilnehmerJaxRSClient.putRequest(putUrl, inputJSon);

		System.out.println("Response vom Server (PUT): " + resultString);
		
		return resultString;
	}

	public List<Teilnehmer> suchenTeilnehmers(Teilnehmer suchTeilnehmer) {
		
		String getUrlSuchen = baseUrl + "/teilnehmer/suchen";
		MultivaluedMap<String, String> params = new MultivaluedMapImpl();
			params.add("nachname", suchTeilnehmer.getNachname());
			params.add("vorname", suchTeilnehmer.getVorname());
			params.add("kurs", suchTeilnehmer.getKurs());
			params.add("rolle", suchTeilnehmer.getRolle());
		String resultJsonString = TeilnehmerJaxRSClient.getRequest(getUrlSuchen, params);
		System.out.println("Response vom Server (Suchen): " + resultJsonString);
		
		Gson gson = new GsonBuilder().create();		
		Type typeOfT = new TypeToken<List<Teilnehmer>>(){}.getType();
		List<Teilnehmer> gefundenTeilnehmers = gson.fromJson(resultJsonString, typeOfT);
		return gefundenTeilnehmers;
	}
     
}