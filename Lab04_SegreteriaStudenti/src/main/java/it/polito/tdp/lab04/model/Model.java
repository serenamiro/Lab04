package it.polito.tdp.lab04.model;

import java.util.List;

import it.polito.tdp.lab04.DAO.CorsoDAO;
import it.polito.tdp.lab04.DAO.StudenteDAO;

public class Model {
	
	private CorsoDAO Cdao;
	private StudenteDAO Sdao;
	
	public Model() {
		Cdao = new CorsoDAO();
		Sdao = new StudenteDAO();
	}
	
	public List<Corso> getTuttiICorsi(){
		return Cdao.getTuttiICorsi();
	}
	
	public Corso getCorso (Corso corso) {
		return Cdao.getCorso(corso);
	}
	
	public List<Studente> getStudentiIscrittiAlCorso(Corso corso){
		return Cdao.getStudentiIscrittiAlCorso(corso);
	}
	
	public Studente getStudente(Studente studente) {
		return Sdao.getStudente(studente);
	}
	
	public List<Corso> getCorsiStudente(Studente studente){
		return Cdao.getCorsiStudente(studente);
	}
	
	public boolean verificaIscrizione(Studente studente, Corso corso) {
		return Cdao.verificaIscrizione(studente, corso);
	}
	
	public boolean inscriviStudenteACorso(Studente studente, Corso corso) {
		List<Studente> studenti = Cdao.getStudentiIscrittiAlCorso(corso);
		
		if(studenti.contains(studente)) {
			throw new IllegalStateException("L'utente è iscritto al corso selezionato.");
		}
		
		return Cdao.inscriviStudenteACorso(studente, corso);
	}
}
