package de.hdbw.integration.jaxrs.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import de.hdbw.hgy.db.access.DBZugriff;
//import de.hdbw.hgy.db.access.DBZugriffProviderH2;
import de.hdbw.hgy.db.access.DBZugriffProviderMangoDB;
import de.hdbw.hgy.db.access.Teilnehmer;

@Path("/teilnehmer")
public class TeilnehmerRestService {
	
	static DBZugriff dbZugriff = null;
	static {
		// Benutzen H2 DB
		// dbZugriff = new DBZugriffProviderH2();
		// Benutzen MongoDB
		dbZugriff = new DBZugriffProviderMangoDB();
	}

	@GET
	@Path("/{id}/lesen")
	@Produces(MediaType.APPLICATION_JSON)
	public Teilnehmer getTeilnehmerRecord(@PathParam("id") String id) {
		int teilnehmerId = Integer.parseInt(id);
		Teilnehmer teilnehmer = dbZugriff.getRecordInTableById("teilnehmer", teilnehmerId);
		return teilnehmer;		
	}

	@GET
	@Path("/suchen")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Teilnehmer> suchenTeilnehmerRecords(
			@QueryParam("nachname") String userName,
			@QueryParam("vorname") String userVorname,
			@QueryParam("kurs") String userKurs,
			@QueryParam("rolle") String userRolle
			) {
		List<Teilnehmer> teilnehmers = dbZugriff.sucheRecordInDatabase(userName, userVorname, userKurs, userRolle);
		return teilnehmers;		
	}
	
	@POST
	@Path("/speichern")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response postTeilnehmerRecord(Teilnehmer teilnehmer){
		Teilnehmer newTeilnehmer = dbZugriff.insertRecordInDatabase(teilnehmer);
		return Response.status(201).entity(newTeilnehmer).build();
	}
	
	@PUT
	@Path("/aendern")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response putTeilnehmerRecord(Teilnehmer teilnehmer){
		boolean updateOK = dbZugriff.updateRecordInDatabase(teilnehmer);
		String returnMessage = "";
		if (updateOK) {
			returnMessage = "Update des Objektes mit der ID " + teilnehmer.getId() + " erfolgreich.";
		} else {
			returnMessage = "Update des Objektes mit der ID " + teilnehmer.getId() + " nicht erfolgreich.";
		}
		return Response.status(200).entity(returnMessage).build();
	}
}
