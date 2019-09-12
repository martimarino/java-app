
import java.io.*;
import java.net.*;


public class InvioLogDiNavigazione {        //00
    
    private static void inviaLog(LogDiNavigazione log, String ipServerLog, int portaServerLog) {    //01
        
        try (Socket sock = new Socket(ipServerLog, portaServerLog);
            ObjectOutputStream oos = new ObjectOutputStream(sock.getOutputStream());
        ){
            oos.writeObject(log.toString());    //02
        } catch (IOException ie) { ie.printStackTrace(); }
    }
            
    public static void creaLog(String tipoEvento, ParametriDiConfigurazione config) {   //03
        
        LogDiNavigazione log = new LogDiNavigazione(tipoEvento, config.ipClient);
        inviaLog(log, config.ipServerLog, config.portaServerLog);
        
    }
    
}

/*

Note:

    00) Classe che gestisce i log XML. Essi vengono inviati al ServerLog ogni volta
        che viene aperta o chiusa l'applicazione e a seguito di altri tipi di
        interazioni con essa (pressione di pulsanti ecc.).
    01) Invia un oggetto di tipo LogDiNavigazione ad un server log il cui indirizzo
        IP e la cui porta sono passati como parametri.
    02) Viene inviato l'oggetto LogEventi, convertito in una stringa XML.
    03) Crea un log istanziando LogDiNavigazione e lo invia.

*/