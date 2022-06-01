package de.hdbw.hgy.db.access;

public class Teilnehmer {

	String vorname;
	String nachname;
	String kurs;
	String rolle;
	int id;
	String passwort;
	
	public Teilnehmer() {
	}

	public Teilnehmer(int userId, String userName, String userVorname, String userPwd, String userKurs, String userRolle) {
		id = userId;
		nachname = userName;
		vorname = userVorname;
		passwort = userPwd;	
		kurs = userKurs;
		rolle = userRolle;
	}

	/**
	 * Zum Mocken
	 */
	public Teilnehmer(String userKurs, String userName, int userId, String userVorname, int randomPrice,
			boolean randomSoldState) {
		id = userId;
		nachname = userName;
		vorname = userVorname;
		kurs = userKurs;
		rolle = "Student";
		passwort = "pwd-" + userKurs;	
	}
	
	public String getVorname() {
		return vorname;
	}
	public void setVorname(String vorname) {
		this.vorname = vorname;
	}
	public String getNachname() {
		return nachname;
	}
	public void setNachname(String nachname) {
		this.nachname = nachname;
	}
	public String getKurs() {
		return kurs;
	}
	public void setKurs(String kurs) {
		this.kurs = kurs;
	}
	public String getRolle() {
		return rolle;
	}
	public void setRolle(String rolle) {
		this.rolle = rolle;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPasswort() {
		return passwort;
	}
	public void setPasswort(String passwort) {
		this.passwort = passwort;
	}
	
	@Override
	public String toString(){
		return vorname+" "+nachname+" ist Teilnehmer des Kurses "+ kurs+" als "+ rolle;
	}
}
