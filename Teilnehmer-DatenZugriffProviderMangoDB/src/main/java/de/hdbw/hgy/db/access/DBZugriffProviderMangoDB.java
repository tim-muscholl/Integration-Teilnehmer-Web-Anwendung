package de.hdbw.hgy.db.access;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.UpdateResult;


public class DBZugriffProviderMangoDB implements DBZugriff {
	
	// Client Connection
	private MongoClient mongoClient;
	
	// Mango DB
	private MongoDatabase mongoDB;
	
	// Get Collection Teilnehmer
	private MongoCollection<Document> teilnehmerCollection;
	
	/**
	 * Konstruktor
	 */
	public DBZugriffProviderMangoDB() {
		// Verbindung zu Datenbank herstellen
		// Client Connection
		mongoClient = new MongoClient( "localhost" , 27017 );
		// Get DB, wenn nicht existiert, dann automatisch erstellt
//		mongoDB = mongoClient.getDatabase("testMongoDb");
		mongoDB = mongoClient.getDatabase("db");
		// Get Collection Teiln ehmer
		teilnehmerCollection = mongoDB.getCollection("Teilnehmer");
	}

	/**
	 * Return das Java-Object Teilnehmer
	 */
	public Teilnehmer insertRecordInDatabase(Teilnehmer teilnehmer) {
		System.out.println("INSERT: " + teilnehmer);
		
		Teilnehmer retval = null;
		
		int maxId = getCurrentMaxIdInCollection("teilnehmer");
		int userId = maxId + 1;
		System.out.println("Die maximale User-ID in der Collection ist: " + maxId + ", die naechste hoehere Max-Nummer " + userId + " wird fuer den neuen Record benutzt.");
		teilnehmer.setId(userId);
		
		Document document = umwandelnZuDocument(teilnehmer);
		
		teilnehmerCollection.insertOne(document);
		Object mangoDocId = document.get("_id");
		retval = teilnehmer;
		
		System.out.println("MONGO DB-LAYER: Doc with ID : " + mangoDocId + " for Collection: Teilnehmer is: " + retval);			
		
		return retval;
	}

	@Override
	public boolean updateRecordInDatabase(Teilnehmer teilnehmer) {
		System.out.println("UPDATE: " + teilnehmer);
		
		Bson filter = Filters.eq("id", teilnehmer.getId());

		Document document = umwandelnZuDocument(teilnehmer);
				
		// Benutze replaceOne statt updateOne
		UpdateResult result = teilnehmerCollection.replaceOne(filter, document);
		
		if (result.getModifiedCount()==1) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public List<Teilnehmer> sucheRecordInDatabase(String userName, String userVorname, String userKurs, String userRolle) {
		System.out.println("SUCHE-Kriterien: " + userVorname+" "+userName+" ist Teilnehmer des Kurses "+ userKurs+" als "+ userRolle);
        
		List<Teilnehmer> returnObjekte = new ArrayList<Teilnehmer>();
		
		Bson suchFilter = Filters.and(
				Filters.regex("nachname", (userName==null || userName.isEmpty())? ".*" : ".*" + userName + ".*"),
				Filters.regex("vorname", (userVorname==null || userVorname.isEmpty())? ".*" : ".*" + userVorname + ".*"),
				Filters.regex("kurs", 	(userKurs==null || userKurs.isEmpty())? ".*" : ".*" + userKurs + ".*"),
				Filters.regex("rolle", (userRolle==null || userRolle.isEmpty())? ".*" : ".*" + userRolle + ".*")
				);
        
        // Read Operation into Collection
        FindIterable<Document> treffer = teilnehmerCollection.find(suchFilter);
        MongoCursor<Document> cursor = treffer.iterator();
        try {
            while(cursor.hasNext()) {
				Teilnehmer einTeilnehmer = umwandelnZuObject(cursor.next());
				if (einTeilnehmer!=null) {
					returnObjekte.add(einTeilnehmer);
				}
            }
        } finally {
            cursor.close();
        }
        
		System.out.println("SUCHE gefunden: " + returnObjekte.size());
        
        return returnObjekte;
	}

	@Override
	public Teilnehmer getRecordInTableById(String tableName, int id) {
		// tableName wird hier nicht verwendet
		System.out.println("GET Teilnehmer mit ID: " + id);

		Teilnehmer retval = null;
        
        // Read Operation into Collection (Wir schleifen zwar, verarbeitet jedoch nur das erste Document)
        FindIterable<Document> treffer = teilnehmerCollection.find(Filters.eq("id", id));      
        MongoCursor<Document> cursor = treffer.iterator();
        try {
            while(cursor.hasNext()) {
            	retval = umwandelnZuObject(cursor.next());
				System.out.println("DB-LAYER: Record with ID : " + id + " for Table: " + tableName + " is: " + retval);
				if (retval!=null) {
					break;
				}
            }
        } finally {
            cursor.close();
        }
        
        return retval;
	}

	@Override
	public String checkUserInDatabase(String user, String passwd) {
		// Nicht implementiert
		return null;
	}
	
	/**
	 * Umwandeln eines JAVA-Objektes (Teilnehmer) in ein Mongo-Document
	 */
    private Document umwandelnZuDocument(Teilnehmer teilnehmer) {
		Document document = new Document();
		
		document.put("id", teilnehmer.getId()); 
		document.put("nachname", teilnehmer.getNachname());
		document.put("vorname", teilnehmer.getVorname()); 
		document.put("kurs", teilnehmer.getKurs());
		document.put("rolle", teilnehmer.getRolle()); 
		document.put("passwort", teilnehmer.getPasswort());

        return document;
    }
	
	/**
	 * Umwandeln eines Mongo-Documentes in ein JAVA-Objekt (Teilnehmer)
	 */
    private Teilnehmer umwandelnZuObject(Document docu) {
    	Teilnehmer einTeilnehmer = null;
        try {
        	einTeilnehmer = new Teilnehmer();
        	
			einTeilnehmer.setId(docu.getInteger("id"));
			einTeilnehmer.setNachname(docu.getString("nachname"));
			einTeilnehmer.setVorname(docu.getString("vorname"));
			einTeilnehmer.setPasswort(docu.getString("passwort"));
			einTeilnehmer.setKurs(docu.getString("kurs"));
			einTeilnehmer.setRolle(docu.getString("rolle"));
			
        } catch (Exception ex) {
        	einTeilnehmer = null;
        	System.err.println("JSON-Objekt hat nicht alle erwarteten Felder: " + ex.getMessage());
        }

        return einTeilnehmer;
    }

	/**
	 * Return den maximalen Wert ID in der Tabelle Teilnehmer.
	 */
	private int getCurrentMaxIdInCollection(String tableName) {

		int retval = 0;
		
        // Read Operation into Collection
        FindIterable<Document> treffer = teilnehmerCollection.find();
        MongoCursor<Document> cursor = treffer.iterator();
        try {
            while(cursor.hasNext()) {
            	Integer intgr = cursor.next().getInteger("id");
            	int id = intgr==null? 0 : intgr.intValue();
            	if (id>=retval) {
            		retval = id;
            	}
            }
        } finally {
            cursor.close();
        }
		
		return retval;
	}

}