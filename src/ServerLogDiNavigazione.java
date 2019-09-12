
import java.io.*;
import java.net.*;
import java.nio.file.*;

public class ServerLogDiNavigazione {           //00
    
    private final static int PORTA = 6979;
    private final static String XML_LOG = "logDiNavigazione.xml", XSD_LOG = "logDiNavigazione.xsd";
    
    public static void main (String[] args) {
        
        System.out.println("Server log di navigazione avviato.\n");
        
        try (ServerSocket servsock = new ServerSocket(PORTA)) {
            while(true) {       //01
                try (
                        Socket sock = servsock.accept();
                        ObjectInputStream ois = new ObjectInputStream(sock.getInputStream());
                ){
                        String log = (String)ois.readObject();
                        System.out.println(log);
                        if(ValidazioneXML.valida(log, XSD_LOG, false)); //02
                            salvaSuFile(log, XML_LOG, true);    //02
                } 
            }
        }  catch (IOException | ClassNotFoundException e) { e.printStackTrace(); }
    }
    
    public static void salvaSuFile (String s, String file, boolean append) { // 02)
        try {
            Files.write(Paths.get("../" + file), s.getBytes(), (append) ? StandardOpenOption.APPEND : StandardOpenOption.TRUNCATE_EXISTING);
        } catch(IOException e){ System.err.println(e.getMessage()); }
    }
    
}

/*

Note:
    
    00) Server che riceve i log XML, li valida e li agginge al file di log 
        logDiNavigazione.xml.
    01) Il server è ciclico: dopo aver ricevuto un log si rimette in ascolto, in
        attesa di riceverne altri
    02) Se la singola  stringa XML viene validata correttamente viene aggiunta ad un
        file di log XML.

*/