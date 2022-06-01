package de.hdbw.hgy.db.access;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class DBZugriffProviderH2 implements DBZugriff {
	
	static Connection connection;
	
	static int maxTeilnehmerId = 0;

	/**
	 * Static Init (einmalig wg.Performance)
	 */
	static {
		// Verbindung zu Datenbank herstellen
		try {
			// H2 DB
			Class.forName("org.h2.Driver");
			connection = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/testd;IFEXISTS=TRUE", "sa", "");
			//Connection c = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/testwd", "sa", "");
			//Connection c = DriverManager.getConnection("jdbc:h2:~/testwd", "sa", "");

			// MySQL DB
			// Class.forName("com.mysql.cj.jdbc.Driver");
			// Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/testdb2?useUnicode=true&serverTimezone=UTC", "testuser1", "testuser1");
	 
		} catch (ClassNotFoundException | SQLException e) {
			System.err.println("Datenbank-Fehler: " + e.getMessage());
		}
	}

	/**
	 * Return den Wert der Tabellen-Spalte "rolle", wenn der Benutzer registriert ist.
	 */
	public Teilnehmer insertRecordInDatabase(Teilnehmer teilnehmer) {

		System.out.println("INSERT: " + teilnehmer);
		
		Teilnehmer retval = null;
		
		int maxId = getCurrentMaxIdInTable("teilnehmer");
		int userId = maxId + 1;
		System.out.println("Die maximale User-ID in der Tabelle ist: " + maxId + ", die naechste hoehere Max-Nummer " + userId + " wird fuer den neuen Record benutzt.");
		teilnehmer.setId(userId);
		
		// Verbindung zu mysql Datenbank und Pruefen des Benutzers	
		try {
			//PreparedStatement ps = connection.prepareStatement("insert into teilnehmer ('id', 'nachname', 'vorname', 'kurs', 'rolle', 'passwort') values (?, ?, ?, ?, ?, ?)");
			PreparedStatement ps = connection.prepareStatement("insert into teilnehmer values (?, ?, ?, ?, ?, ?)");
	 
			ps.setInt(1, teilnehmer.getId()); 
			ps.setString(2, teilnehmer.getNachname());
			ps.setString(3, teilnehmer.getVorname()); 
			ps.setString(4, teilnehmer.getKurs());
			ps.setString(5, teilnehmer.getRolle()); 
			ps.setString(6, teilnehmer.getPasswort());

			ps.execute();
			
			retval = teilnehmer;
			System.out.println("DB-LAYER: Record with ID : " + userId + " for Table: TEILNEHMER is: " + retval);
			
		} catch (SQLException e) {
			System.err.println("Datenbank-Fehler: " + e.getMessage());
		}
		
		return retval;
	}

	/**
	 * Return den Boolean-Wert:
	 * true: Update erfolgreich
	 * false: Update failed.
	 */
	public boolean updateRecordInDatabase(Teilnehmer teilnehmer) {

		System.out.println("UPDATE for Teilnehmer: " + teilnehmer);
		
		boolean retval = false;
		
		// Verbindung zu mysql Datenbank und Pruefen des Benutzers	
		try {
			PreparedStatement ps = connection.prepareStatement("update teilnehmer set nachname=?, vorname=?, kurs=?, rolle=?, passwort=? where id=" + teilnehmer.getId());
	 
			ps.setString(1, teilnehmer.getNachname());
			ps.setString(2, teilnehmer.getVorname()); 
			ps.setString(3, teilnehmer.getKurs());
			ps.setString(4, teilnehmer.getRolle()); 
			ps.setString(5, teilnehmer.getPasswort());

			int updateCount = ps.executeUpdate();
			
			if (updateCount==1) {
				retval = true;
			}
			
		} catch (SQLException e) {
			System.err.println("Datenbank-Fehler: " + e.getMessage());
		}
		
		return retval;
	}

	/**
	 * Return die gefundenen Records
	 */
	public List<Teilnehmer> sucheRecordInDatabase(String userName, String userVorname, String userKurs, String userRolle) {

		System.out.println("SEARCH QUERY: " + userVorname+" "+userName+" ist Teilnehmer des Kurses "+ userKurs+" als "+ userRolle);
		
		List<Teilnehmer> returnObjekte = new ArrayList<Teilnehmer>();
		
		// Verbindung zu mysql Datenbank und Pruefen des Benutzers	
		try {
			PreparedStatement ps = connection.prepareStatement("select id, nachname, vorname, kurs, rolle from teilnehmer "
					+ "where nachname like ? and vorname like ? and kurs like ? and rolle like ?");
	 
			ps.setString(1, (userName==null || userName.isEmpty())? "%" : "%" + userName + "%");
			ps.setString(2, (userVorname==null || userVorname.isEmpty())? "%" : "%" + userVorname + "%");
			ps.setString(3, (userKurs==null || userKurs.isEmpty())? "%" : "%" + userKurs + "%");
			ps.setString(4, (userRolle==null || userRolle.isEmpty())? "%" : "%" + userRolle + "%");
	 
			ResultSet rs = ps.executeQuery();
			
			boolean hasNext = false;
			while(hasNext = rs.next()) {
				Teilnehmer einTeilnehmer = new Teilnehmer();
				returnObjekte.add(einTeilnehmer);
				einTeilnehmer.setId(rs.getInt("id"));
				einTeilnehmer.setNachname(rs.getString("nachname"));
				einTeilnehmer.setVorname(rs.getString("vorname"));
				//einTeilnehmer.setPasswort(rs.getString("passwort"));
				einTeilnehmer.setKurs(rs.getString("kurs"));
				einTeilnehmer.setRolle(rs.getString("rolle"));
			}
			
			System.out.println("DB-LAYER: Suche hat ergeben: " + returnObjekte.size());
			
		} catch (SQLException e) {
			System.err.println("Datenbank-Fehler: " + e.getMessage());
		}
		
		return returnObjekte;
	}

	/**
	 * Return den maximalen Wert ID in der Tabelle Teilnehmer.
	 */
	public Teilnehmer getRecordInTableById(String tableName, int id) {

		Teilnehmer retval = null;
		
		try {
			PreparedStatement ps = connection.prepareStatement("select * from " + tableName + " where id=?");
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			
			boolean exists = rs.next();
			
			if (exists) {
				retval = new Teilnehmer();
				retval.setId(rs.getInt("id"));
				retval.setNachname(rs.getString("nachname"));
				retval.setVorname(rs.getString("vorname"));
				retval.setPasswort(rs.getString("passwort"));
				retval.setKurs(rs.getString("kurs"));
				retval.setRolle(rs.getString("rolle"));
				System.out.println("DB-LAYER: Record with ID : " + id + " for Table: " + tableName + " is: " + retval);
			}
			
		} catch (SQLException e) {
			System.err.println("Datenbank-Fehler: " + e.getMessage());
		}
		
		return retval;
	}

	/**
	 * Return den maximalen Wert ID in der Tabelle Teilnehmer.
	 */
	private static int getCurrentMaxIdInTable(String tableName) {

		int retval = 0;
		
		try {
			PreparedStatement ps = connection.prepareStatement("select max(id) maxid from " + tableName);
	 
			ResultSet rs = ps.executeQuery();
			
			boolean exists = rs.next();
			
			if (exists) {
				retval = rs.getInt("maxid");
				System.out.println("DB-LAYER: Max-ID : " + retval + " for Table: " + tableName);
			}
			
		} catch (SQLException e) {
			System.err.println("Datenbank-Fehler: " + e.getMessage());
		}
		
		return retval;
	}

	/**
	 * Return den Wert der Tabellen-Spalte "rolle", wenn der Benutzer registriert ist.
	 */
	public String checkUserInDatabase(String user, String passwd) {

		System.out.println("DB-LAYER: Test-Benutzer: " + user);
		System.out.println("DB-LAYER: Test-Benutzer-Pwd: " + passwd);
		
		String retval = "";
		
		// Verbindung zu mysql Datenbank und Pruefen des Benutzers	
		try {
			PreparedStatement ps = connection.prepareStatement("select nachname, vorname, rolle from teilnehmer where nachname=? and vorname=?");
			// PreparedStatement ps = connection.prepareStatement("select username, pass, rolle from benutzer where userName=? and pass=?");
	 
			ps.setString(1, user); 
			ps.setString(2, passwd);
	 
			ResultSet rs = ps.executeQuery();
			
			boolean exists = rs.next();
			
			if (exists) {
				retval = rs.getString("rolle");
				System.out.println("DB-LAYER: Test-Benutzer-Rolle: " + retval);
			}
			
		} catch (SQLException e) {
			System.err.println("Datenbank-Fehler: " + e.getMessage());
		}
		
		return retval;
	}

}