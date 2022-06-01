package de.hdbw.hgy.db.access;

import java.util.List;


public interface DBZugriff {
	
	/**
	 * Return den Wert der Tabellen-Spalte "rolle", wenn der Benutzer registriert ist.
	 */
	public Teilnehmer insertRecordInDatabase(Teilnehmer teilnehmer);

	/**
	 * Return den Boolean-Wert:
	 * true: Update erfolgreich
	 * false: Update failed.
	 */
	public boolean updateRecordInDatabase(Teilnehmer teilnehmer);

	/**
	 * Return die gefundenen Records
	 */
	public List<Teilnehmer> sucheRecordInDatabase(String userName, String userVorname, String userKurs, String userRolle);

	/**
	 * Return den maximalen Wert ID in der Tabelle Teilnehmer.
	 */
	public Teilnehmer getRecordInTableById(String tableName, int id);

	/**
	 * Return den Wert der Tabellen-Spalte "rolle", wenn der Benutzer registriert ist.
	 */
	public String checkUserInDatabase(String user, String passwd);

}