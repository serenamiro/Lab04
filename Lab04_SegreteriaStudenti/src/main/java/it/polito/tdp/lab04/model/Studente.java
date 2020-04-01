package it.polito.tdp.lab04.model;

public class Studente {
	
	private Integer matricola;
	private String cognome;
	private String nome;
	private String CDS; // corso di studi
	
	public Studente(int matricola, String cognome, String nome, String cDS) {
		this.matricola = matricola;
		this.cognome = cognome;
		this.nome = nome;
		this.CDS = cDS;
	}

	public int getMatricola() {
		return matricola;
	}

	public void setMatricola(int matricola) {
		this.matricola = matricola;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCDS() {
		return CDS;
	}

	public void setCDS(String cDS) {
		CDS = cDS;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + matricola;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Studente other = (Studente) obj;
		if (matricola != other.matricola)
			return false;
		return true;
	}

	@Override
	public String toString() {
		String s = String.format("%-15s %-25s %-20s %-15s", matricola, cognome, nome, CDS);
		return s;
	}
}
