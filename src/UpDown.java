
import javafx.application.*;
import javafx.scene.*;
import javafx.stage.*;


public class UpDown extends Application {       //00
  
    private final static String XML_PARAMETRI_CONFIGURAZIONE = "config.xml",
                                XSD_PARAMETRI_CONFIGURAZIONE = "config.xsd",
                                FILE_CACHE = "cache.bin";
                                
    private InterfacciaUpDown interfaccia;  //01
    
    public void start(Stage stage) {
        
        ParametriDiConfigurazione config = 
                new ParametriDiConfigurazione(      //02
                        ValidazioneXML.valida(XML_PARAMETRI_CONFIGURAZIONE, XSD_PARAMETRI_CONFIGURAZIONE, true) ?
                                ValidazioneXML.leggiFile(XML_PARAMETRI_CONFIGURAZIONE) : null);     //03
        InvioLogDiNavigazione.creaLog("Apertura applicazione", config); //04
        interfaccia = new InterfacciaUpDown(config, FILE_CACHE);        //05
        interfaccia.StileElementi(config);
        
        stage.setOnCloseRequest((WindowEvent we) -> { //06
            CacheUltimaMisurazione.salvaCache(interfaccia, FILE_CACHE); 
            InvioLogDiNavigazione.creaLog("Chiusura applicazione", config);
        });
        stage.setOnShown((WindowEvent we) -> { // 07
                interfaccia.getGrafico().aggiornaGrafico(interfaccia, config);
        });
        stage.setTitle("Up&Down");
        Scene scene = new Scene(interfaccia.getBox(), config.dimensioni[0], config.dimensioni[1]);    //08
        stage.setScene(scene);         
        stage.show();
        
    }
    
}

/*

Note:
    
    00) Classe principale che estende Application da cui viene costruita 
        l'interfaccia.
    01) Viene istanziata la classe interfaccia che si occusa di costruire i 
        vari elementi che la costituiscono.
    02) All'avvio dell'applicazione vengono prelevati i parametri di 
        configurazione dal file config.xml che viene validato e da cui si
        istanzia un oggetto di tipo ParametriDiConfigurazione.
    03) Se il file xml non viene validato correttamente, l'oggetto 
        ParametriConfigurazioneXML viene istanziato lo stesso ma con dei valori 
        di default.
    04) Creato un eventp di log per l'apertura.
    05) All'apertura si utilizzano i parametri di configurazione e 
        le informazioni contenute nella cache (se presenti) per costruire
        l'interfaccia.
    06) Alla chiusura viene creato un oggetto Cache che salva gli input
        fatti dall'utente. Creato un eventp di log per la chiusura.
    07) Una volta caricati tutti gli elementi all'interno della finestra
        è necessario riaggiornare il grafico poiché le Line hanno bisogno
        di conoscere i limiti dell'area del grafico e questi ultimi si possono
        conoscere solo dopo che il grafico è stato completato.
    08) Dimensioni finestra.

*/