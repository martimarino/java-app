
import java.time.*;
import java.util.*;
import javafx.event.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;


public class SelezionaPeriodo {     //00
    
    private final VBox box;
    private final HBox boxDate;
    private final DatePicker dataInizio, dataFine;
    private final Label titlePeriodo, labelDataInizio, labelDataFine;
    private final EventHandler<ActionEvent> dataHandler;
    
    public SelezionaPeriodo(InterfacciaUpDown interfaccia, ParametriDiConfigurazione config)
    {
        titlePeriodo = new Label("Seleziona il periodo da visualizzare");
        dataInizio = new DatePicker(); dataInizio.setValue(LocalDate.now().minusDays(config.numeroGiorni));
        dataFine = new DatePicker(); dataFine.setValue(LocalDate.now());
        labelDataInizio = new Label("Da: ");
        labelDataFine = new Label("a: ");
        box = new VBox(2);
        boxDate = new HBox(2);
        
        List<Misurazione> m = new ArrayList<>();
        dataHandler = (ActionEvent ae) -> {     //01
                m.clear();
                m.addAll(DBMisurazioni.caricaMisurazioniPeriodo(config, interfaccia.getUser(), dataInizio.getValue(), dataFine.getValue()));
                interfaccia.getTabella().aggiornaTabella(interfaccia, config);
                interfaccia.getGrafico().aggiornaGrafico(interfaccia, config);
                interfaccia.setTFSogliaMin(DBMisurazioni.prelevaSogliaMinima(config, interfaccia.getUser()));
                interfaccia.setTFSogliaMax(DBMisurazioni.prelevaSogliaMassima(config, interfaccia.getUser()));
                InvioLogDiNavigazione.creaLog("Selezione intervallo temporale", config);            
        };
        
        dataInizio.setOnAction(dataHandler); 
        dataFine.setOnAction(dataHandler); 
        boxDate.getChildren().addAll(labelDataInizio, dataInizio, labelDataFine, dataFine);
        box.getChildren().addAll(titlePeriodo, boxDate);
        box.setLayoutY(340.0);
        titlePeriodo.setFont(Font.font(config.font, FontWeight.findByName(config.fontWeightTitolo), config.dimensioneFontTitolo));
        labelDataInizio.setFont(Font.font(config.font, config.dimensioneFont));
        labelDataFine.setFont(Font.font(config.font, config.dimensioneFont));
    }
    
    public VBox getBox () { return box; }
    public DatePicker getDataInizio() { return dataInizio; }
    public DatePicker getDataFine() { return dataFine; }
}

/*

Note:

    00) Classe che si occupa del box all'interno del quale è possibile selezionare
        un periodo di tempo per visualizzare i dati nella tabella e nel grafico.
    01) Handler che alla modifica dei campi di uno dei due DatePicker ricarica i 
        dati dal DB relativi al nuovo intervallo slezionato, aggiorna grafico e 
        tabella e crea un evento di log.

*/