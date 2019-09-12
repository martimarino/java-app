
import java.io.*;
import java.nio.file.*;
import javax.xml.*;
import javax.xml.parsers.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import javax.xml.validation.*;
import org.w3c.dom.*;
import org.xml.sax.*;


public class ValidazioneXML {   //00
 
    public static boolean valida(String xml, String xsd, boolean file) { //01
        try {
            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder(); 
            SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI); 
            Document d; 
            if(file) //02
                d = db.parse(new File(xml)); 
            else //03)
                d = db.parse(new InputSource(new StringReader(xml))); 
            Schema s = sf.newSchema(new StreamSource(new File(xsd.equals("config.xsd")?xsd:("../"+xsd)))); 
            s.newValidator().validate(new DOMSource(d)); 
        } catch(ParserConfigurationException | SAXException | IOException e) {
            if(e instanceof SAXException) 
                System.err.println("Errore di validazione");
            System.err.println(e.getMessage());
            return false;
        }
        return true; 
    }
    
    public static String leggiFile(String file) { // 04)
        try {
            return new String(Files.readAllBytes(Paths.get(file))); 
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return null; 
    }
    
}

/*

Note:

    00) Classe che gestisce la validazioni di file o stringhe XML.
    01) Il ritorno è boolean: ritorna true se la validazione è avvenuta
        correttamente, false altrimenti. In quest'ultimo caso vengono utilizzati
        valori di default.
    02) Se l'input XML e' da prelevare in un file. Effettua il parse del file XML e 
        restituisce un oggetto DOM.
        https://docs.oracle.com/javase/8/docs/api/javax/xml/parsers/DocumentBuilder.html#parse-java.io.File-
    03) Se l'input XML e' direttamente una stringa passata come primo parametro. 
        Si necessita di effettuare il parse di una stringa XML.
        DocumentBuiler permette il parsing non solo da file, ma anche da un InputSource:
        https://docs.oracle.com/javase/8/docs/api/javax/xml/parsers/DocumentBuilder.html#parse-org.xml.sax.InputSource-
        che rappresenta una sorgente di input XML ottenibile da un flusso di byte o
        di caratteri. E' possibile associare ad un InputSource un flusso di
        caratteri con il seguente costruttore:
        https://docs.oracle.com/javase/8/docs/api/org/xml/sax/InputSource.html#InputSource-java.io.Reader-
        che richiede un oggetto Reader. In questo caso viene utilizzata una sua
        sottoclasse, StringReader, il cui costruttore richiede come parametro la
        stringa XML in questione:
        https://docs.oracle.com/javase/8/docs/api/java/io/StringReader.html#StringReader-java.lang.String-
        Idea presa da:
        http://stackoverflow.com/questions/7607327/how-to-create-a-xml-object-from-string-in-java


*/