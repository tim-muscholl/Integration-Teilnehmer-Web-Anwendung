package de.hdbw.hgy.primefaces.beans;

public class Teilnehmer {

	int id;
	String nachname;
	String vorname;
	String kurs;
	String rolle;
	String passwort;
	
	public Teilnehmer() {
	}

	public Teilnehmer(int userId, String userName, String userVorname, String userKurs, String userRolle, String userPwd) {
		id = userId;
		nachname = userName;
		vorname = userVorname;
		kurs = userKurs;
		rolle = userRolle;
		passwort = userPwd;	
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
		return vorname+" "+nachname+" ist Teilnehmer des Kurses "+ kurs+" als "+ rolle+" mit "+id;
	}
}
