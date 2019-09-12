
import javafx.scene.paint.*;
import java.util.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.chart.*;
import javafx.scene.shape.*;


public class GraficoMisurazioni extends LineChart<String, Number> {     //00
    
    private final XYChart.Series pressioneDiastolica;   //01
    private final XYChart.Series pressioneSistolica;    //02
    private final Line sogliaMin, sogliaMax;            //03
    private double yShift;                              //04
    
    public GraficoMisurazioni() {       
        
        super(new CategoryAxis(), new NumberAxis("Pressione (mmHg)", 0, 200, 20));  //05
        getXAxis().setLabel("Data");
        setTitle("Andamento Misurazioni");                      
        pressioneDiastolica = new XYChart.Series();
        pressioneSistolica = new XYChart.Series();
        pressioneDiastolica.setName("Pressione Diastolica");
        pressioneSistolica.setName("Pressione Sistolica");
        
        setLegendSide(Side.TOP);
        getData().addAll(pressioneDiastolica, pressioneSistolica);   
        sogliaMin = new Line();
        sogliaMax = new Line();
        
    }
    
    public void stileElementi(){    //06
        
        setAnimated(false);
        setPrefHeight(300);
        setPrefWidth(510);
        setLayoutX(480);
        setLayoutY(210);
        sogliaMin.setStroke(Color.RED);
        sogliaMax.setStroke(Color.RED);
        this.getStylesheets().add("style.css"); //07
        getXAxis().setTickLabelRotation(360);

   }
    
    public void disegnaAndamentoMinima(List<Misurazione> l){    //08
        
        pressioneDiastolica.getData().clear();
        for(Misurazione m : l){ 
            pressioneDiastolica.getData().add(new XYChart.Data(m.getDataMisurazione().toString(), m.getPressioneMin()));
        }
        
    }
    
    public void disegnaAndamentoMassima(List<Misurazione> l){   //09
        
        pressioneSistolica.getData().clear();
        for(Misurazione m : l){    
            pressioneSistolica.getData().add(new XYChart.Data(m.getDataMisurazione().toString(), m.getPressioneMax()));
        }
        
    }
    
    public void disegnaSogliaMinima(InterfacciaUpDown interfaccia, ParametriDiConfigurazione config){   //10
      
        Node chartArea = lookup(".chart-plot-background");  //11
        Bounds chartAreaBounds = chartArea.localToScene(chartArea.getBoundsInLocal());  //12
        yShift = chartAreaBounds.getMinY();     //13
        sogliaMin.setStartX(chartAreaBounds.getMinX());     
        sogliaMin.setEndX(chartAreaBounds.getMaxX());       
        double min = Double.parseDouble(DBMisurazioni.prelevaSogliaMinima(config, interfaccia.getUser()));
        double displayPosition = (this.getYAxis()).getDisplayPosition(min); //14
        sogliaMin.setStartY(yShift + displayPosition);              
        sogliaMin.setEndY(yShift + displayPosition);
    }
    
    public void disegnaSogliaMassima(InterfacciaUpDown interfaccia, ParametriDiConfigurazione config){  //10
        
        Node chartArea = lookup(".chart-plot-background");      //11
        Bounds chartAreaBounds = chartArea.localToScene(chartArea.getBoundsInLocal());  //12
        yShift = chartAreaBounds.getMinY();     //13
        sogliaMax.setStartX(chartAreaBounds.getMinX());         
        sogliaMax.setEndX(chartAreaBounds.getMaxX());
        double max = Double.parseDouble(DBMisurazioni.prelevaSogliaMassima(config, interfaccia.getUser()));
        double displayPosition = (this.getYAxis()).getDisplayPosition(max); //14
        sogliaMax.setStartY(yShift + displayPosition);
        sogliaMax.setEndY(yShift + displayPosition);
        
    }
    
    public void aggiornaGrafico(InterfacciaUpDown interfaccia, ParametriDiConfigurazione config){   //15
        
        List<Misurazione> l  = new ArrayList<>();
        l = DBMisurazioni.caricaMisurazioniPeriodo(config, interfaccia.getUser(), interfaccia.getPeriodo().getDataInizio().getValue(), interfaccia.getPeriodo().getDataFine().getValue());
        disegnaAndamentoMinima(l); 
        disegnaAndamentoMassima(l);
        if(!(interfaccia.getUser().equals(""))) {
            disegnaSogliaMinima(interfaccia, config);
            disegnaSogliaMassima(interfaccia, config);
        }
    }
    
    public Line getSogliaMin() { return sogliaMin; }
    public Line getSogliaMax() { return sogliaMax; }
    
}

/*

Note:

    00) Classe che contiene tutte le funzioni relative al grafico; estende la classe
        LineChart.
    01) Series che disegna la pressione diastolica nel grafico.
    02) Series che disegna la pressione sistolica nel grafico.
    03) Questi due elementi Line appaiono nel grafico come due linee orizzontali 
        che rappresentano i valori limite all'interno dei quali dovrebbero stare i
        due andamenti delle Series per essere in buona salute.
    04) Variabile di utilità: vedi nota )
    05) Costruttore del grafico che richiama quello della superclasse.
    06) Funzione che raccoglie tutti gli statement relativi all'estetica e al 
        posizionamento del grafico.
    07) Per modificare i colori delle due Series è stato inserito el codice CSS 
        in un file a parte.
    08) Disegna la funzione di pressione minima.
    09) Disegna la dunzione di pressione massima.
    10) Disegna la Line che rappresenta il valore soglia.
        https://stackoverflow.com/questions/24149830/get-display-position-of-a-certain-value-inside-a-javafx-chart
    11) Memorizza chart area Node.
    12) Memorizza i limiti del grafico.
    13) Memorizza la scene position della chart area.
    14) Memorizza la display position lungo l'asse per il valore dato.
    15) Preleva nuovamente i dati dal DB e richiama tutte le funzioni utili a
        disegnare il grafico.

*/