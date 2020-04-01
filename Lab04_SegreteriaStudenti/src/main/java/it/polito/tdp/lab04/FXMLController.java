package it.polito.tdp.lab04;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Model;
import it.polito.tdp.lab04.model.Studente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ComboBoxBase;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Corso> comboCorso;

    @FXML
    private Button btnCercaIscritti;

    @FXML
    private TextField txtMatricola;

    @FXML
    private CheckBox btnCompletamento;

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtCognome;

    @FXML
    private Button btnCercaCorsi;

    @FXML
    private Button btnIscrivi;

    @FXML
    private TextArea txtRisposta;

    @FXML
    private Button btnReset;

	private Model model;

    @FXML
    void CercaCorsi(ActionEvent event) {
    	txtRisposta.clear();
    	String input = txtMatricola.getText();
    	int matricola;
    	try {
    		matricola = Integer.parseInt(input);
    	} catch (NumberFormatException e) {
    		txtRisposta.appendText("Inserire una matricola valida.");
    		txtMatricola.clear();
    		return;
    	}
    	Studente stud = new Studente(matricola, null, null, null);
    	if(model.getStudente(stud).getNome().equals(null) && 
    			model.getStudente(stud).getCognome().equals(null)) {
    		txtRisposta.appendText("Non esiste studente con matricola "+stud.getMatricola());
    		return;
    	} else {
    		String str = "";
    		for (Corso c : model.getCorsiStudente(stud)) {
    			str += c.toLongerString()+"\n";
    		}
    		txtRisposta.appendText(str);
    		return;
    	}
    }
    

    @FXML
    void CercaIscrittiCorso(ActionEvent event) {
    	txtRisposta.clear();
    	// ricerca di studenti iscritti a un corso
    	Corso desiderato = comboCorso.getValue();
    	if(desiderato == null) {
    		txtRisposta.appendText("Selezionare un corso. \n");
    	}
    	String str = "";
    	for(Studente s : model.getStudentiIscrittiAlCorso(desiderato)) {
			str += s.toString()+"\n";
		}
    	txtRisposta.appendText(str);
    }

    @FXML
    void Completamento(ActionEvent event) {
    	String input = txtMatricola.getText();
    	int matricola;
    	try {
    		matricola = Integer.parseInt(input);
    	} catch (NumberFormatException e) {
    		txtRisposta.appendText("Inserire una matricola valida.");
    		txtMatricola.clear();
    		return;
    	}
    	
    	Studente stud = new Studente(matricola, null, null, null);
    	if(model.getStudente(stud).getNome().equals(null) && 
    			model.getStudente(stud).getCognome().equals(null)) {
    		txtRisposta.appendText("Non esiste studente con matricola "+stud.getMatricola());
    		return;
    	} else {
    		btnCompletamento.setIndeterminate(false);
    		txtNome.setDisable(false);
    		txtNome.appendText(stud.getNome());
    		txtCognome.setDisable(false);
    		txtCognome.appendText(stud.getCognome());
    		return;
    	}
    }

    @FXML
    void Iscrivi(ActionEvent event) {
    	txtRisposta.clear();
    	String input = txtMatricola.getText();
    	int matricola;
    	try {
    		matricola = Integer.parseInt(input);
    	} catch (NumberFormatException e) {
    		txtRisposta.appendText("Inserire una matricola valida. \n");
    		txtMatricola.clear();
    		return;
    	}
    	Studente stud = new Studente(matricola, null, null, null);
    	Corso desiderato = comboCorso.getValue();
    	if(desiderato == null) {
    		txtRisposta.appendText("Selezionare un corso. \n");
    		return;
    	}
    	boolean iscrizione = model.inscriviStudenteACorso(stud, desiderato);  	
    	if(iscrizione == true) {
    		txtRisposta.appendText("Lo studente "+stud.getMatricola()+" è stato iscritto al corso "+ desiderato.getCodins()+".\n");
    	} else if(iscrizione == false) {
    		txtRisposta.appendText("Lo studente "+stud.getMatricola()+" è già iscritto al corso"+ desiderato.getCodins()+".\n");
    	}
    }

    @FXML
    void Reset(ActionEvent event) {
    	txtMatricola.clear();
    	txtNome.clear();
    	txtCognome.clear();
    	txtRisposta.clear();
    }

    @FXML
    void initialize() {
        assert comboCorso != null : "fx:id=\"comboCorso\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCercaIscritti != null : "fx:id=\"btnCercaIscritti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtMatricola != null : "fx:id=\"txtMatricola\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCompletamento != null : "fx:id=\"btnCompletamento\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtNome != null : "fx:id=\"txtNome\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtCognome != null : "fx:id=\"txtCognome\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCercaCorsi != null : "fx:id=\"btnCercaCorsi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnIscrivi != null : "fx:id=\"btnIscrivi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtRisposta != null : "fx:id=\"txtRisposta\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnReset != null : "fx:id=\"btnReset\" was not injected: check your FXML file 'Scene.fxml'.";
    }

	public void setModel(Model model) {
		this.model = model;	
		comboCorso.getItems().addAll(this.model.getTuttiICorsi());
	}
}
