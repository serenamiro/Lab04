package it.polito.tdp.lab04.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import it.polito.tdp.lab04.model.Studente;

public class StudenteDAO {

	public Studente getStudente(Studente studente) {
		String sql = "SELECT s.cognome, s.nome, s.CDS " + 
				"FROM studente AS s " + 
				"WHERE s.matricola=?";
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setInt(1, studente.getMatricola());
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				studente.setNome(rs.getString("nome"));
				studente.setCognome(rs.getString("cognome"));
				studente.setCDS(rs.getString("CDS"));
			}
			conn.close();
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
		return studente;
	}

}
