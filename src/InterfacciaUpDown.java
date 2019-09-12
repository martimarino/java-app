
import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;

public class InterfacciaUpDown { 

    private final AnchorPane box;   //00)
    private final VBox boxLabelDati, boxTextFieldDati;   //01)
    private final Label welcome, titleDati, email, max, min;
    private final TextField TFemail, TFSogliaMax, TFSogliaMin;
    private final InserimentoNuovaMisurazione nuovaMisurazione;     //03)
    private final SelezionaPeriodo periodo;     //04)
    private final TabellaVisualeMisurazioniPeriodo tabella;     
    private final GraficoMisurazioni grafico;
    private final EventHandler<ActionEvent> handlerValoriSoglia, handlerModificaUsername;
    
    public InterfacciaUpDown(ParametriDiConfigurazione config, String fileCache) { 
        
        box = new AnchorPane();
        boxTextFieldDati = new VBox(3); boxLabelDati = new VBox(4);
        welcome = new Label("Benvenuto!");
        titleDati = new Label("I tuoi dati");
        email = new Label("E-mail:");
        max = new Label("Valore soglia massimo (mmHg):");
        min = new Label("Valore soglia minimo (mmHg):");
        TFemail = new TextField(); TFSogliaMax = new TextField(); TFSogliaMin = new TextField();
        nuovaMisurazione = new InserimentoNuovaMisurazione(this, config);
        periodo = new SelezionaPeriodo(this, config);
        tabella = new TabellaVisualeMisurazioniPeriodo(this, config);
        grafico = new GraficoMisurazioni();
        CacheUltimaMisurazione cache = CacheUltimaMisurazione.caricaCache(fileCache);   //05)
        if(cache != null) {
            TFemail.setText(cache.getUltimoUser());
            nuovaMisurazione.setTFMin(cache.getUltimaPressioneMin());
            nuovaMisurazione.setTFMax(cache.getUltimaPressioneMax());
            nuovaMisurazione.setDataMisurazione(cache.getUltimaDataMisurazione());
        }
        if((TFemail != null) || (!(TFemail.getText().equals("")))) {
                TFSogliaMin.setText(DBMisurazioni.prelevaSogliaMinima(config, getUser()));
                TFSogliaMax.setText(DBMisurazioni.prelevaSogliaMassima(config, getUser()));
                getTabella().aggiornaTabella(this, config);
                getGrafico().aggiornaGrafico(this, config);
        }
        
        boxLabelDati.getChildren().addAll(titleDati, email, min, max);
        boxTextFieldDati.getChildren().addAll(TFemail, TFSogliaMin, TFSogliaMax);
        box.getChildren().addAll(welcome, boxLabelDati, boxTextFieldDati, nuovaMisurazione.getBoxInput(), 
                                    periodo.getBox(), tabella.getBox(), grafico, grafico.getSogliaMin(), grafico.getSogliaMax());
        handlerValoriSoglia = (ActionEvent e) -> {      //06)
            if(!getUser().equals("")) {
                DBMisurazioni.aggiornaValoriSoglia(config, getUser(), Integer.parseInt(getTFSogliaMin()), Integer.parseInt(getTFSogliaMax()));
                InvioLogDiNavigazione.creaLog("Aggiornati valori soglia", config);
                getGrafico().disegnaSogliaMinima(this, config);
                getGrafico().disegnaSogliaMassima(this, config);
            }
        };
        TFSogliaMin.setOnAction(handlerValoriSoglia); TFSogliaMax.setOnAction(handlerValoriSoglia);
        handlerModificaUsername = (ActionEvent e) -> {      //07)
            TFSogliaMin.setText(String.valueOf(DBMisurazioni.prelevaSogliaMinima(config, getUser())));
            TFSogliaMax.setText(String.valueOf(DBMisurazioni.prelevaSogliaMassima(config, getUser())));
            getTabella().aggiornaTabella(this, config);
            getGrafico().aggiornaGrafico(this, config);
        };
        TFemail.setOnAction(handlerModificaUsername);
    }
    
    public void StileElementi(ParametriDiConfigurazione config)     //08)
    {
        box.setStyle("-fx-background-color: " + config.coloreSfondo);
        AnchorPane.setTopAnchor(welcome, 20.0);  AnchorPane.setLeftAnchor(welcome, 20.0);   //09)
        AnchorPane.setTopAnchor(boxLabelDati, 50.0);  AnchorPane.setLeftAnchor(boxLabelDati, 20.0);     //09)
        AnchorPane.setTopAnchor(boxTextFieldDati, 80.0); AnchorPane.setLeftAnchor(boxTextFieldDati, 250.0);     //09)
        AnchorPane.setTopAnchor(nuovaMisurazione.getBoxInput(), 50.0); AnchorPane.setLeftAnchor(nuovaMisurazione.getBoxInput(), 500.0);     //09)
        AnchorPane.setTopAnchor(periodo.getBox(), 200.0); AnchorPane.setLeftAnchor(periodo.getBox(), 20.0);     //09)
        boxLabelDati.setSpacing(8); boxTextFieldDati.setSpacing(5); periodo.getBox().setSpacing(10);    //09)
        nuovaMisurazione.StileElementi(config);
        tabella.stileElementi();
        grafico.stileElementi();
        boxTextFieldDati.setAlignment(Pos.CENTER_RIGHT);    //09)
        TFemail.setPrefWidth(200);      //09)
        email.setFont(Font.font(config.font, config.dimensioneFont));   //10)
        max.setFont(Font.font(config.font, config.dimensioneFont));     //10)
        min.setFont(Font.font(config.font, config.dimensioneFont));     //10)
        welcome.setFont(Font.font(config.font, FontWeight.findByName(config.fontWeightTitolo), config.dimensioneFontTitolo));       //10)
        titleDati.setFont(Font.font(config.font, FontWeight.findByName(config.fontWeightTitolo), config.dimensioneFontTitolo));     //10)
    }   
    
    public AnchorPane getBox() { return box; }
    public String getUser() { return TFemail.getText(); }
    public TabellaVisualeMisurazioniPeriodo getTabella() { return tabella; }
    public GraficoMisurazioni getGrafico() { return grafico; }
    public SelezionaPeriodo getPeriodo() { return periodo; }
    public String getTFSogliaMin() { return TFSogliaMin.getText(); }
    public String getTFSogliaMax() { return TFSogliaMax.getText(); }
    public InserimentoNuovaMisurazione getNuovaMisurazione() { return nuovaMisurazione; } 
    public void setTFSogliaMin(String min) { TFSogliaMin.setText(min); }
    public void setTFSogliaMax(String max) { TFSogliaMax.setText(max); }
    
}

/* Note:

    00) Box più grande che include tutti gli elementi.
    01) Box dei dati relativi all'utente.
    02) Box in cui verranno inserite le misurazioni
    03) Costruzione del box per inserire una nuova misurazione.
    04) Costruzione del box per la selezione dell'intervallo di tempo.
    05) Caricamento dei dati dalla cache (se presenti).
    06) In seguito alla modifica dei TextField relativi ai due valori soglia essi vengono 
        aggiornati nel grafico e nel database.
    07) In seguito alla modifica del campo email vengono caricati i dati dell'utente.
    08) Funzione che imposta lo stile degli elementi dell'interfaccia richiamando 
        anche le funzioni delle altre classi con lo stesso scopo.
    09) Statement di posizionamento degli elementi.
    10) Valori caricati dal file di configurazione.

*/