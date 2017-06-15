package it.polito.tdp.music;

import java.net.URL;
import java.time.Month;
import java.util.ResourceBundle;

import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.music.model.ArtistAndNum;
import it.polito.tdp.music.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class MusicController {
	private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Month> boxMese;

    @FXML
    private Button btnArtisti;

    @FXML
    private Button btnNazioni;

    @FXML
    private TextArea txtResult;

    @FXML
    void cercaDistanza(ActionEvent event) {
    	Month mese=this.boxMese.getValue();
    	
    	if(mese==null){
    		txtResult.appendText("ERRORE: selezionare un mese !\n");
    		return;
    	}
    	int m=mese.getValue();
    	
    	DefaultWeightedEdge e=model.getMax(m);
    	txtResult.appendText("Paesi con più artisti in comune "+e.toString());
    }

    @FXML
    void doElenco(ActionEvent event) {
    	txtResult.clear();
    	Month mese=this.boxMese.getValue();
    	
    	if(mese==null){
    		txtResult.appendText("ERRORE: selezionare un mese !\n");
    		return;
    	}
    	int m=mese.getValue();
    	
    	for(ArtistAndNum an:model.getClassificaForMese(m)){
    		txtResult.appendText(an.getArtist().toString()+" "+an.getAscolti()+"\n");
    	}
   
    	
    	

    }

    @FXML
    void initialize() {
        assert boxMese != null : "fx:id=\"boxMese\" was not injected: check your FXML file 'MusicA.fxml'.";
        assert btnArtisti != null : "fx:id=\"btnArtisti\" was not injected: check your FXML file 'MusicA.fxml'.";
        assert btnNazioni != null : "fx:id=\"btnNazioni\" was not injected: check your FXML file 'MusicA.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'MusicA.fxml'.";

    }

	public void setModel(Model model) {
		this.model=model;
		this.boxMese.getItems().addAll(model.getMesi());
		
	}
}

