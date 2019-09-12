
import java.util.*;
import javafx.collections.*;
import javafx.event.*;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn.*;
import javafx.scene.control.cell.*;
import javafx.scene.layout.*;
import javafx.util.converter.*;


public class TabellaVisualeMisurazioniPeriodo extends TableView<Misurazione> {  //00
    
    private final ObservableList<Misurazione> listaOsservabileMisurazioni;
    private final Button delete;
    private final VBox box;
    private final EventHandler<ActionEvent> handlerDelete;    
    
    public TabellaVisualeMisurazioniPeriodo(InterfacciaUpDown interfaccia, ParametriDiConfigurazione config) {
        setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);    
        setEditable(true);              //01)
        TableColumn colonnaUsername = new TableColumn("USERNAME");
        TableColumn colonnaData = new TableColumn("DATA");
        TableColumn colonnaPressioneMin = new TableColumn("PRESSIONE MIN");
        TableColumn colonnaPressioneMax = new TableColumn("PRESSIONE MAX");
        colonnaUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        colonnaData.setCellValueFactory(new PropertyValueFactory<>("dataMisurazione"));
        colonnaPressioneMin.setCellValueFactory(new PropertyValueFactory<>("pressioneMin"));
        colonnaPressioneMin.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));    //02
        colonnaPressioneMin.setOnEditCommit(new EventHandler<CellEditEvent<Misurazione, Integer>>(){        //03
            public void handle(CellEditEvent<Misurazione, Integer> c) {     //04
                ((Misurazione) c.getTableView().getItems().get(c.getTablePosition().getRow())).setPressioneMin(c.getNewValue());
                int ix = getSelectionModel().getSelectedIndex();
                if (getItems().size() != 0 || ix < 0 || ix > getItems().size()) {
                    Misurazione m = (Misurazione) getSelectionModel().getSelectedItem();
                    DBMisurazioni.aggiornaMisurazione(config, interfaccia.getUser(), m);
                    aggiornaTabella(interfaccia, config);
                    interfaccia.getGrafico().aggiornaGrafico(interfaccia, config);
                    InvioLogDiNavigazione.creaLog("Modifica della cella pressione minima", config);
                }
            }
        });
        colonnaPressioneMax.setCellValueFactory(new PropertyValueFactory<>("pressioneMax"));
        colonnaPressioneMax.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        colonnaPressioneMax.setOnEditCommit(new EventHandler<CellEditEvent<Misurazione, Integer>>(){
            public void handle(CellEditEvent<Misurazione, Integer> c) {     //04
                ((Misurazione) c.getTableView().getItems().get(c.getTablePosition().getRow())).setPressioneMax(c.getNewValue());
                int ix = getSelectionModel().getSelectedIndex();
                if (getItems().size() != 0 || ix < 0 || ix > getItems().size()) {
                    Misurazione m = (Misurazione) getSelectionModel().getSelectedItem();
                    DBMisurazioni.aggiornaMisurazione(config, interfaccia.getUser(), m);
                    aggiornaTabella(interfaccia, config);
                    interfaccia.getGrafico().aggiornaGrafico(interfaccia, config);
                    InvioLogDiNavigazione.creaLog("Modifica della cella pressione massima", config);
                }
            }
        });
        listaOsservabileMisurazioni = FXCollections.observableArrayList();
        setItems(listaOsservabileMisurazioni);
        getColumns().addAll(colonnaUsername, colonnaData, colonnaPressioneMin, colonnaPressioneMax);
        colonnaPressioneMin.setPrefWidth(150.0);
        colonnaPressioneMax.setPrefWidth(150.0);
        delete = new Button("ELIMINA");
        box = new VBox(2);
        box.getChildren().addAll(this, delete);   
        handlerDelete = (ActionEvent ae) -> {       //05
            int ix = getSelectionModel().getSelectedIndex();
            if (getItems().size() != 0 || ix < 0 || ix > getItems().size()) {
                Misurazione m = (Misurazione) getSelectionModel().getSelectedItem();
                DBMisurazioni.cancellaMisurazione(config, interfaccia.getUser(), m);
                aggiornaTabella(interfaccia, config);
                interfaccia.getGrafico().aggiornaGrafico(interfaccia, config);
                InvioLogDiNavigazione.creaLog("Eliminazione di una misurazione", config);
            } 
        };
        delete.setOnAction(handlerDelete);
        
        this.getSelectionModel().selectedIndexProperty().addListener((event) -> {   //06
            InvioLogDiNavigazione.creaLog("Selezione di una riga", config);
        });
        
    }

    public void stileElementi(){        //07
        
        setMaxHeight(180);
        box.setLayoutX(20);
        box.setLayoutY(300);
        this.prefHeight(200.0);
    }
    
    public void aggiornaTabella(InterfacciaUpDown interfaccia, ParametriDiConfigurazione config) {  //08
        
        List l = new ArrayList<>();
        l = DBMisurazioni.caricaMisurazioniPeriodo(config, interfaccia.getUser(), interfaccia.getPeriodo().getDataInizio().getValue(), interfaccia.getPeriodo().getDataFine().getValue());
        listaOsservabileMisurazioni.clear();
        listaOsservabileMisurazioni.addAll(l);
    }
    
    public VBox getBox() { return box; }

}

/*

Note:

    00) Classe che rappresenta la tabella. Estende TableView
    01) La tabella è editabile.
    02) Imposta il valore della proprietà cellFactory.
        forTabelColumn() fornisce un campo di testo che consente la modifica del 
        contenuto della cella quando si fa doppio click. Questo metodo funziona
        su qualsiasi istanza di TableColumn, indipendentemente dal tipo generico.
        Tuttavia, per abilitarlo, è necessario fornire un StringConverter che 
        converta la stringa specificata (da ciò che l'utente ha digitato).
    03) Rendo la cella modificabile.
    04) Funzione che in seguito alla modifica del campo della tabella, aggiorna
        la misurazione nel DB, aggiorna grafico e tabella e crea un evento di
        log.
    05) Alla pressione del Button 'ELIMINA' la misurazione corrispondente alla
        riga selezionata viene eliminata dal DB. Si aggiornano anche tabella
        e grafico e si crea un evento di log.
    06) Handler relativo alla selezione di una riga. Crea un evento di log.
    07) Raccoglie gli statement relativi all'estetica e al posizionamento.
    08) Funzione che aggionra la tabella caricando nuovamente i dati dal DB.

*/