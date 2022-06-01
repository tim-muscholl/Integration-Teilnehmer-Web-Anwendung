package de.hdbw.hgy.db.access;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

/*
 * JUnit Test
 */
public class DBZugriffTest {
	
	private DBZugriff dbZugriff;
	
	@Before
	public void setUp() {
		dbZugriff = new DBZugriffProviderH2();
	}

	@Test
	public void insert_read_check() {
		Teilnehmer teilnehmer = new Teilnehmer();
			teilnehmer.setNachname("Halli");
			teilnehmer.setVorname("Hallo");
			teilnehmer.setKurs("EinKurs");
			teilnehmer.setRolle("EineRolle");
			teilnehmer.setPasswort("geheim");		
		teilnehmer = dbZugriff.insertRecordInDatabase(teilnehmer);
		
		assertNotNull(""+teilnehmer.getId(), "Eine automatisch vergebene Id (nicht null) erwartet");		
	}

	@Test
	public void insert_update_check() {
		Teilnehmer teilnehmer = new Teilnehmer();
			teilnehmer.setNachname("ZhangS1");
			teilnehmer.setVorname("Yiyi");
			teilnehmer.setKurs("KeinKurs");
			teilnehmer.setRolle("Regisseur");
			teilnehmer.setPasswort("ZhangS!!");
		teilnehmer = dbZugriff.insertRecordInDatabase(teilnehmer);
			
		assertNotNull(""+teilnehmer.getId(), "Eine automatisch vergebene Id (nicht null) erwartet");		
		
		teilnehmer.setKurs("DochEinKurs");
		teilnehmer.setRolle("DochKeinRegisseur");
		boolean erfolgt = dbZugriff.updateRecordInDatabase(teilnehmer);
		assertTrue("Update Record ergab einen false Flag.", erfolgt);
		
		assertNotNull(""+teilnehmer.getId(), "Eine automatisch vergebene Id (nicht null) erwartet");		
		
	}

	@Test
	public void searchUsers() {

		List<Teilnehmer> results = dbZugriff.sucheRecordInDatabase("Zhang", "Yi", "", null);
		
		System.out.println("Suche Test-Benutzer: " + results.size());
	}

	@Test
	public void getUserById() {

		int id = 23;
		Teilnehmer result = dbZugriff.getRecordInTableById("teilnehmer", id);
		
		System.out.println("Suche Test-Benutzer: " + result);
	}

	@Test
	public void searchAllUsers() {

		List<Teilnehmer> results = dbZugriff.sucheRecordInDatabase("", "", "", null);
		
		System.out.println("Suche Alle Test-Benutzer: " + results.size());
	}


}