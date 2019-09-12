
import com.thoughtworks.xstream.*;
import java.io.*;
import java.text.*;
import java.util.*;

public class LogDiNavigazione implements Serializable {     //00
    
    private final String evento, ipClient, dataOraCorrente;     //01
    
    public LogDiNavigazione (String evento, String ipClient) {
        
        this.evento = evento;
        this.ipClient = ipClient;
        dataOraCorrente = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss").format(new Date());   //02
        
    }
    
    public String toString(){       //03
        
        XStream xs = new XStream();
        xs.useAttributeFor(LogDiNavigazione.class, "evento");
        xs.useAttributeFor(LogDiNavigazione.class, "ipClient");
        xs.useAttributeFor(LogDiNavigazione.class, "dataOraCorrente");
        return xs.toXML(this) + "\n";
        
    }
}

/*

Note:

    00) Classe che rappresenta un log da inviare al Server di log. Essi vengono 
        creati a partire da prestabilite interazioni con l'interfaccia:
        selezioni nella tabella, modifica di valori, pressione di pulsanti.
    01) Il log contiene le seguenti infomazioni: evento, indirizzo IP del client e
        data e ora corrente dell'azione.
    02) Per ottenere la data e l'ora corrente si crea un oggetto di tipo
        java.util.Date.
    03) Conversione in stringa XML.

*/
