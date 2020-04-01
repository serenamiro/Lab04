package it.polito.tdp.lab04.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Studente;

public class CorsoDAO {
	
	/*
	 * Ottengo tutti i corsi salvati nel Db
	 */
	public List<Corso> getTuttiICorsi() {

		final String sql = "SELECT * FROM corso";

		List<Corso> corsi = new LinkedList<Corso>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				String codins = rs.getString("codins");
				int numeroCrediti = rs.getInt("crediti");
				String nome = rs.getString("nome");
				int periodoDidattico = rs.getInt("pd");

				// System.out.println(codins + " " + numeroCrediti + " " + nome + " " + periodoDidattico);

				// Crea un nuovo JAVA Bean Corso
				// Aggiungi il nuovo oggetto Corso alla lista corsi
				Corso c = new Corso(codins, numeroCrediti, nome, periodoDidattico);
				corsi.add(c);
			}
			
			if(corsi.size() == 0)
				throw new NullPointerException("Non esiste alcun insegnamento registrato.\n");

			conn.close();

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore nella lettura del Db", e);
		}
		Corso vuoto = new Corso(null, 0, null, 0);
		corsi.add(vuoto);
		return corsi;
	}
	
	
	/*
	 * Dato un codice insegnamento, ottengo il corso
	 */
	public Corso getCorso(Corso corso) {
		String sql = "SELECT c.nome, c.crediti, c.pd " + 
				"FROM corso as c " + 
				"WHERE c.codins=?";
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setString(1, corso.getCodins());
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				corso.setNome(rs.getString("nome"));
				corso.setCrediti(rs.getInt("crediti"));
				corso.setPd(rs.getInt("pd"));
			}
			conn.close();
			
			if(corso == null)
				throw new NullPointerException("Non esiste un corso con quel codice.\n");
			
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
		return corso;
	}

	/*
	 * Ottengo tutti gli studenti iscritti al Corso
	 */
	public List<Studente> getStudentiIscrittiAlCorso(Corso corso) {
		String sql = "SELECT s.matricola, s.cognome, s.nome, s.CDS " + 
				"FROM iscrizione AS i, corso AS c, studente AS s " + 
				"WHERE c.codins=i.codins AND s.matricola=i.matricola AND c.codins=?";
		List<Studente> result = new LinkedList<Studente>();
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setString(1, corso.getCodins());
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				Studente s = new Studente(rs.getInt("matricola"), rs.getString("cognome"),
								rs.getString("nome"), rs.getString("CDS"));
				result.add(s);
			}
			conn.close();
			
			if(result.size() == 0)
				throw new NullPointerException("Lo studente esiste ma non frequenta corsi.\n");
			
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
		return result;
	}
	
	public List<Corso> getCorsiStudente(Studente studente){
		String sql = "SELECT c.codins, c.crediti, c.nome, c.pd " + 
				"FROM corso AS c, iscrizione AS i, studente AS s " + 
				"WHERE c.codins=i.codins AND s.matricola=i.matricola AND s.matricola=?";
		
		List<Corso> result = new LinkedList<Corso>();
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setInt(1, studente.getMatricola());
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				String codins = rs.getString("codins");
				int numeroCrediti = rs.getInt("crediti");
				String nome = rs.getString("nome");
				int periodoDidattico = rs.getInt("pd");
				Corso c = new Corso(codins, numeroCrediti, nome, periodoDidattico);
				result.add(c);
			}
			conn.close();
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
		return result;
	}
	
	public boolean verificaIscrizione(Studente studente, Corso corso) {
		String sql = "SELECT c.codins, c.crediti, c.nome, c.pd " + 
				"FROM corso AS c, iscrizione AS i, studente AS s " + 
				"WHERE c.codins=i.codins AND s.matricola=i.matricola AND s.matricola=? AND c.codins=?";
		Corso c = null;
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setInt(1, studente.getMatricola());
			st.setString(2, corso.getCodins());
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				String codins = rs.getString("codins");
				int numeroCrediti = rs.getInt("crediti");
				String nome = rs.getString("nome");
				int periodoDidattico = rs.getInt("pd");
				
				c = new Corso(codins, numeroCrediti, nome, periodoDidattico);
			}
			conn.close();
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
		if (c.equals(corso)) {
			return true;
		} else {
			return false;
		}
	}
	

	/*
	 * Data una matricola ed il codice insegnamento, iscrivi lo studente al corso.
	 */
	public boolean inscriviStudenteACorso(Studente studente, Corso corso) {
		// TODO
		// ritorna true se l'iscrizione e' avvenuta con successo
		
		String sql = "INSERT INTO iscrizione (matricola, codins) VALUES (?,?)";
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setInt(1, studente.getMatricola());
			st.setString(2, corso.getCodins());
			
			Integer rs = st.executeUpdate();
			
			if(rs == 1) 
				return true;
			
			} catch(SQLException e) {
				throw new RuntimeException("Errore nella lettura dati dal database.\n", e);
			}
			return false;
	}

}
