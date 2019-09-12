
import java.time.*;
import javafx.event.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;


public class InserimentoNuovaMisurazione {      //00
    
    private final VBox boxLabel, boxTextField, boxUnita, box;
    private final HBox boxButton, boxDati;
    private final Label max, min, data, titleMisurazione, unitaMin, unitaMax;
    private final TextField TFMax, TFMin;
    private final DatePicker dataPicker;
    private final Button invia, annulla;
    private final EventHandler<ActionEvent> handlerInvia, handlerAnnulla;
    
    public InserimentoNuovaMisurazione(InterfacciaUpDown interfaccia, ParametriDiConfigurazione config) 
    {
        titleMisurazione = new Label("Inserisci la misurazione odierna");
        boxLabel = new VBox(3);
        min = new Label("Pressione diastolica (minima):");
        max = new Label("Pressione sistolica (massima):");
        data = new Label("Data:");
        boxLabel.getChildren().addAll(min, max, data);
        boxTextField = new VBox(3);
        TFMin = new TextField(); TFMax = new TextField(); dataPicker = new DatePicker();
        boxTextField.getChildren().addAll(TFMin, TFMax, dataPicker);  
        boxUnita = new VBox(2);  unitaMin = new Label("mmHg"); unitaMax = new Label("mmHg");
        boxUnita.getChildren().addAll(unitaMin, unitaMax);
        boxDati = new HBox(3); boxDati.getChildren().addAll(boxLabel, boxTextField, boxUnita);
        invia = new Button("INVIA"); annulla = new Button("ANNULLA");
        boxButton = new HBox(2); boxButton.getChildren().addAll(invia, annulla);
        handlerInvia = (ActionEvent e) -> {     //01
            DBMisurazioni.inserisciMisurazione(config, interfaccia.getUser(), 
                                            Integer.parseInt(TFMin.getText()), 
                                            Integer.parseInt(TFMax.getText()), 
                                            dataPicker.getValue(),
                                            ((interfaccia.getTFSogliaMin().equals("")) ? config.sogliaMin : Integer.parseInt(interfaccia.getTFSogliaMin())),
                                            ((interfaccia.getTFSogliaMax().equals("")) ? config.sogliaMax : Integer.parseInt(interfaccia.getTFSogliaMax())));
            svuotaTextField();
            interfaccia.getTabella().aggiornaTabella(interfaccia, config);
            interfaccia.getGrafico().aggiornaGrafico(interfaccia, config);
            InvioLogDiNavigazione.creaLog("Pressione bottone INVIA", config);
        };
        invia.setOnAction(handlerInvia);
        handlerAnnulla = (ActionEvent e) -> {   //02
            svuotaTextField();
            InvioLogDiNavigazione.creaLog("Pressione bottone ANNULLA", config);
        };
        annulla.setOnAction(handlerAnnulla);
        
        box = new VBox(3);
        box.getChildren().addAll(titleMisurazione, boxDati, boxButton);
    }
    
    public void StileElementi(ParametriDiConfigurazione config) {   //03
        boxLabel.setSpacing(10); boxTextField.setSpacing(5); boxUnita.setSpacing(15);
        min.setFont(Font.font(config.font, config.dimensioneFont));
        max.setFont(Font.font(config.font, config.dimensioneFont));
        data.setFont(Font.font(config.font, config.dimensioneFont));
        dataPicker.setValue(LocalDate.now());
        unitaMin.setFont(Font.font(config.font, config.dimensioneFont));
        unitaMax.setFont(Font.font(config.font, config.dimensioneFont));
        boxDati.setSpacing(19);
        titleMisurazione.setFont(Font.font(config.font, FontWeight.findByName(config.fontWeightTitolo), config.dimensioneFontTitolo));
        boxTextField.setPrefWidth(170); 
    }
    
    private void svuotaTextField(){ //04
        TFMin.setText("");
        TFMax.setText("");
        dataPicker.getEditor().clear();
        dataPicker.setValue(LocalDate.now());
    }
    
    public VBox getBoxInput() { return box; }
    public String getTFMin() { return TFMin.getText(); }
    public String getTFMax() { return TFMax.getText(); }
    public LocalDate getDataMisurazione() { return dataPicker.getValue(); }
    public void setTFMin(String nuovoTFMin) { TFMin.setText(nuovoTFMin); }
    public void setTFMax(String nuovoTFMax) { TFMax.setText(nuovoTFMax); }
    public void setDataMisurazione(LocalDate nuovaDataMisurazione) { dataPicker.setValue(nuovaDataMisurazione); }
    
}

/*

Note:

    00) Classe che si occupa del box in cui vengono inserite le uove misurazioni.
    01) Handler che a seguito della pressione del Button 'INVIA' preleva i dati 
        dagli input del box, inserisce la misurazione nel DB associandola al nome
        inserito nel campo E-mail, svuota i campi, aggiorna tabella e grafico e 
        crea un nuovo evento di log.
    02) Handler che a seguito della pressione del Button 'ANNULLA' svuota i campi e 
        crea un nuovo evento di log.
    03) Funzione che raggruppa tutti gli statement relativi a estetica e 
        posizionamento.
    04) Si occupa di svuotare i TextField e impostare la data odierna nel DatePicker.

*/