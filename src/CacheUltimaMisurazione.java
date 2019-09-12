
import java.io.*;
import java.time.*;


public class CacheUltimaMisurazione implements Serializable {   //02
    
    private final String ultimoUser, ultimaPressioneMin, ultimaPressioneMax;
    private final LocalDate ultimaDataMisurazione;
    
    public CacheUltimaMisurazione(InterfacciaUpDown interfaccia) {      //00
     
        ultimoUser = interfaccia.getUser();
        ultimaPressioneMin = interfaccia.getNuovaMisurazione().getTFMin();
        ultimaPressioneMax = interfaccia.getNuovaMisurazione().getTFMax();
        ultimaDataMisurazione = interfaccia.getNuovaMisurazione().getDataMisurazione();
       
    }
    
    public final static CacheUltimaMisurazione caricaCache(String file) {   //01
        
        try (
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))    
        ){
            return (CacheUltimaMisurazione)ois.readObject();
        } catch(IOException | ClassNotFoundException e) {System.err.println(e.getMessage());}
            
        return null;
    }
    
    public final static void salvaCache(InterfacciaUpDown interfaccia, String file) {   //04
        
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))    //05)
        ){
            oos.writeObject(new CacheUltimaMisurazione(interfaccia));   //06)
        } catch (IOException e) {System.err.println(e.getMessage());}
        
    }
    
    public String getUltimoUser() { return ultimoUser; }    //03
    public String getUltimaPressioneMin() { return ultimaPressioneMin; }    //03
    public String getUltimaPressioneMax() { return ultimaPressioneMax; }    //03
    public LocalDate getUltimaDataMisurazione() { return ultimaDataMisurazione; }   //03
        
}

/*
Note:

    00) Il costruttore della classe si occupa di recuperare dall'interfaccia le informazioni 
        non inviate.
    01) Carica dal file binario la cache degli input. Questa funzione viene chiamata
        ad ogni avvio dell'applicazione per ripristinare lo stato precedente.
    02) L'oggetto salva nella cache locale gli input dekk'utente.
    03) Funzioni di utilità che restituiscono i campi private
    04) Salva la cache degli input prelevata dal parametro interfacciaReadNet
        nel file specificato come terzo parametro.
    05) Uso ObjectOutputStream perchè l'oggetto Cache viene salvato in un file binario.
    06) L'oggetto viene creato sul momento, prelevando gli input presenti
        nell'interfaccia. Viene anche passato l'oggetto ParametriDiConfigurazione
        perchè nel costruttore della cache ci sono delle chiamate al database.

*/