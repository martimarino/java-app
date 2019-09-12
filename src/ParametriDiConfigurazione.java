import com.thoughtworks.xstream.*;

public class ParametriDiConfigurazione { //00
    final static double LARGHEZZA_DEFAULT_FINESTRA = 500.0, 
                        ALTEZZA_DEFAULT_FINESTRA = 600.0,
                        DIMENSIONE_FONT_DEFAULT = 14,
                        DIMENSIONE_FONT_TITOLO_DEFAULT = 22; 
    final static int    NUMERO_GIORNI_DEFAULT = 30,  
                        PORTA_SERVER_LOG_DEFAULT = 6979,
                        PORTA_DBMS_DEFAULT = 3306,
                        SOGLIA_MIN_DEFAULT = 80,
                        SOGLIA_MAX_DEFAULT = 120;
                                  
    final static String FONT_DEFAULT = "Arial",  
                        FONT_WEIGHT_TITOLO_DEFAULT = "Bold",
                        COLORE_SFONDO_DEFAULT = "#c1ffe8",
                        IP_CLIENT_DEFAULT = "127.0.0.1",
                        IP_SERVER_LOG_DEFAULT = "127.0.0.1", 
                        NOME_UTENTE_DBMS_DEFAULT = "root",
                        PASSWORD_DBMS_DEFAULT = "",
                        IP_DBMS_DEFAULT ="localhost";

    public final String font; // 02)
    public final double dimensioneFont; // 03)
    public final String fontWeightTitolo;
    public final double dimensioneFontTitolo; //03)
    public final double[] dimensioni; // 04)
    public final String coloreSfondo;  // 02)
    public final int sogliaMin;
    public final int sogliaMax;
    public final int numeroGiorni; // 06)
    public final String ipClient; // 06)
    public final String ipServerLog; // 06)
    public final int portaServerLog; // 05)
    public final String nomeUtenteDBMS; // 06)
    public final String passwordDBMS; // 06)
    public final String ipDBMS; // 06)
    public final int portaDBMS; // 05)
    
    public ParametriDiConfigurazione(String xml) { // 01)
        dimensioni = new double[2];
        
        if(xml == null || xml.equals("")) { 
            font = FONT_DEFAULT;
            dimensioneFont = DIMENSIONE_FONT_DEFAULT;
            fontWeightTitolo = FONT_WEIGHT_TITOLO_DEFAULT;
            dimensioneFontTitolo = DIMENSIONE_FONT_TITOLO_DEFAULT;
            dimensioni[0] = LARGHEZZA_DEFAULT_FINESTRA;
            dimensioni[1] = ALTEZZA_DEFAULT_FINESTRA;
            coloreSfondo = COLORE_SFONDO_DEFAULT;
            sogliaMin = SOGLIA_MIN_DEFAULT;
            sogliaMax = SOGLIA_MAX_DEFAULT;
            numeroGiorni = NUMERO_GIORNI_DEFAULT;
            ipClient = IP_CLIENT_DEFAULT;
            ipServerLog = IP_SERVER_LOG_DEFAULT;
            portaServerLog = PORTA_SERVER_LOG_DEFAULT;
            nomeUtenteDBMS = NOME_UTENTE_DBMS_DEFAULT;
            passwordDBMS = PASSWORD_DBMS_DEFAULT;
            ipDBMS = IP_DBMS_DEFAULT;
            portaDBMS = PORTA_DBMS_DEFAULT;
        } 
        else {
            ParametriDiConfigurazione p = (ParametriDiConfigurazione)creaXStream().fromXML(xml);
            font = p.font;
            dimensioneFont = p.dimensioneFont;
            fontWeightTitolo = p.fontWeightTitolo;
            dimensioneFontTitolo = p.dimensioneFontTitolo;
            dimensioni[0] = p.dimensioni[0];
            dimensioni[1] = p.dimensioni[1];
            coloreSfondo = p.coloreSfondo;
            sogliaMin = p.sogliaMin;
            sogliaMax = p.sogliaMax;
            numeroGiorni = p.numeroGiorni;
            ipClient = p.ipClient;
            ipServerLog = p.ipServerLog;
            portaServerLog = p.portaServerLog;
            nomeUtenteDBMS = p.nomeUtenteDBMS;
            passwordDBMS = p.passwordDBMS;
            ipDBMS = p.ipDBMS;
            portaDBMS = p.portaDBMS;
        }
    }
    
    public final XStream creaXStream() { 
        XStream xs = new XStream();
        xs.useAttributeFor(ParametriDiConfigurazione.class, "numeroGiorni"); 
        xs.useAttributeFor(ParametriDiConfigurazione.class, "ipClient"); 
        xs.useAttributeFor(ParametriDiConfigurazione.class, "ipServerLog");
        xs.useAttributeFor(ParametriDiConfigurazione.class, "portaServerLog"); 
        xs.useAttributeFor(ParametriDiConfigurazione.class, "nomeUtenteDBMS");
        xs.useAttributeFor(ParametriDiConfigurazione.class, "passwordDBMS");
        xs.useAttributeFor(ParametriDiConfigurazione.class, "ipDBMS");
        xs.useAttributeFor(ParametriDiConfigurazione.class, "portaDBMS");
        return xs;
    }
    
    public String toString() { // 07)
        return creaXStream().toXML(this);
    }
}


/* Note

    00) Questa classe contenente si occupa di prelevare da un file XML i 
        parametri di configurazione.
        Il file XML contiene:
            1.1) Font, dimensioni, colore del background
            1.2) Numberi di giorni per il grafico e la tabella
            1.3) Indirizzo IP del client, indirizzo IP e porta del server log
            1.4) Indirizzo IP, nome e password del DBMS
    01) Il costruttore riceve una stringa xml: se il file configurazione.xml
        è stato validato correttamente inizializzo le variabili con i
        parametri contenuti nel file; in caso contrario la stringa xml avrà
        come valore null e inizializzerà i valori con i parametri di default.
    02) Font, Colore dello sfondo
        Viene modellato come elemento in quanto puo' assumere una moltitudine di 
        valori.
    03) Dimensione del font del testo e del titolo dell'interfaccia grafica.
        Viene modellato come elemento in quanto occorre specificare la sua presenza in 
        un certo ordine, ovvero dopo il campo font, in quanto le due informazioni sono 
        strettamente correlate (terza regola).
    04) Dimensioni della finestra in pixel.
        In base alle regole di buona progettazione XML, viene modellato come
        elemento in quanto e' strutturato su linee multiple (array) (prima regola).
    05) Numero di righe della tabella da visualizzare, Porta del server log,
        portaDBMS
        Viene modellato come attributo in quanto si tratta di un numero semplice 
        (seconda regola).
    06) Indirizzo IP del client, da inserire nei log, Indirizzo IP del server log,
        nomeUtenteDBMS, passwordDBMS, ipDBMS
        Viene modellato come attributo in quanto si tratta di una stringa semplice 
        (seconda regola).
    07) Uso di XStream per serializzare automaticamente un oggetto Java 
        come stringa codificata in XML. 

*/