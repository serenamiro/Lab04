package it.polito.tdp.lab04.model;

public class TestModel {

	public static void main(String[] args) {

		Model model = new Model();
		
		for(Corso c :model.getTuttiICorsi()) {
			System.out.println(c.toLongerString());
		}
		
		System.out.println("++++++++");
		Corso corso = new Corso("02CIXPG", 0, null, 0);
		System.out.println(model.getCorso(corso).toString());
		
		for(Studente s : model.getStudentiIscrittiAlCorso(corso)) {
			System.out.println(s.toString());

		}
		
		System.out.println("++++++++");
		Studente studente = new Studente(155793, null, null, null);
		System.out.println(model.getStudente(studente).toString());
		
		System.out.println("++++++++");
		for(Corso c : model.getCorsiStudente(studente)) {
			System.out.println(c.toLongerString());
		}
		
		System.out.println("++++++++");
		if(model.verificaIscrizione(studente, corso) == true) {
			System.out.println("Lo studente è iscritto al corso.");
		} else {
			System.out.println("BUGIA");
		}
	}

}
