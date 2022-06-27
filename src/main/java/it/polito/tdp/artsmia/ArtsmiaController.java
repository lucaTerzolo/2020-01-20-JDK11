package it.polito.tdp.artsmia;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.artsmia.model.Adiacenza;
import it.polito.tdp.artsmia.model.Artist;
import it.polito.tdp.artsmia.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ArtsmiaController {
	
	private Model model ;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnCreaGrafo;

    @FXML
    private Button btnArtistiConnessi;

    @FXML
    private Button btnCalcolaPercorso;

    @FXML
    private ComboBox<String> boxRuolo;

    @FXML
    private TextField txtArtista;

    @FXML
    private TextArea txtResult;

    @FXML
    void doArtistiConnessi(ActionEvent event) {
    	txtResult.clear();
    	String role=this.boxRuolo.getValue();
    	if(role==null)
    		return;
    	List<Adiacenza> msg=this.model.getArtistiConnessi(role);
    	
    	txtResult.appendText("Calcola artisti connessi:\n");
    	for(Adiacenza a:msg)
    		txtResult.appendText(""+a);

    }

    @FXML
    void doCalcolaPercorso(ActionEvent event) {
    	txtResult.clear();
    	txtResult.appendText("Calcola percorso:\n");
    	
    	int artistId=0;
    	try{
    		artistId=Integer.parseInt(this.txtArtista.getText());
    	}catch(NumberFormatException e) {
    		e.printStackTrace();
    	}
    	if(model.artistaValido(artistId)) {
    		//Artista verificato
    	}else {
    		this.txtResult.setText("Inserire un artist_id di un artista valido!");
    		return;
    	}
    	
    	List<Artist> percorso=this.model.calcolaPercorso(artistId);
    	for(Artist a:percorso) {
    		this.txtResult.appendText(a.getArtistId()+" "+a.getArtistName()+"\n");
    	}
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	String role=this.boxRuolo.getValue();
    	if(role==null)
    		return;
    	String msg=this.model.creaGrafo(role);
    	txtResult.setText(msg);
    	
    	this.btnArtistiConnessi.setDisable(false);
    	this.btnCalcolaPercorso.setDisable(false);
    }

    public void setModel(Model model) {
    	this.model = model;
    	this.boxRuolo.getItems().addAll(this.model.getListRole());
    	this.btnArtistiConnessi.setDisable(true);
    	this.btnCalcolaPercorso.setDisable(true);
    }

    
    @FXML
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert btnArtistiConnessi != null : "fx:id=\"btnArtistiConnessi\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert btnCalcolaPercorso != null : "fx:id=\"btnCalcolaPercorso\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert boxRuolo != null : "fx:id=\"boxRuolo\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert txtArtista != null : "fx:id=\"txtArtista\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Artsmia.fxml'.";

    }
}
