
import java.sql.*;
import java.time.*;
import java.util.*;


public class DBMisurazioni {    //00
    
    public static List<Misurazione> caricaMisurazioniPeriodo(ParametriDiConfigurazione config,  //01
                       String username, LocalDate dataInizio, LocalDate dataFine)   
    {
        List listaMisurazioni = new ArrayList<>();
         if(dataInizio == null || dataFine == null)
            return listaMisurazioni;
        
        String s = "jdbc:mysql://" + config.ipDBMS + ":" + config.portaDBMS + "/updown";
        try ( Connection co = DriverManager.getConnection(s, config.nomeUtenteDBMS, config.passwordDBMS);
            PreparedStatement ps = co.prepareStatement("SELECT * "
                                                    + "FROM archiviomisurazioni "
                                                    + "WHERE username = ? AND dataMisurazione >= ? AND dataMisurazione <= ? "
                                                    + "ORDER BY dataMisurazione ASC LIMIT " + config.numeroGiorni + ";");
                ){
            ps.setString(1, username);
            ps.setString(2, dataInizio.toString());
            ps.setString(3, dataFine.toString());
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                listaMisurazioni.add(new Misurazione(rs.getString("username"), rs.getDate("dataMisurazione"),
                                                    rs.getInt("pressioneMin"), rs.getInt("pressioneMax")));
            }
        } catch (SQLException e) {System.err.println(e.getMessage());}
        
        return listaMisurazioni;
        
    }
    
    public static boolean utenteRegistrato(String user, ParametriDiConfigurazione config) {     //02
        
            String s = "jdbc:mysql://" + config.ipDBMS + ":" + config.portaDBMS + "/updown";
            try(Connection co = DriverManager.getConnection(s, config.nomeUtenteDBMS, config.passwordDBMS); 
                PreparedStatement ps = co.prepareStatement("SELECT * FROM valorisoglia WHERE username = ?;"); 
            ) {
                ps.setString(1, user);
                ResultSet rs = ps.executeQuery();
                if(rs.next())
                    return true;
            } catch(SQLException e) {
                System.err.println(e.getMessage());
            }
            return false;
            
    }
    
    public static void inserisciValoriSoglia(String user, int sogliaMin, int sogliaMax, ParametriDiConfigurazione config) { //03
        
        String s = "jdbc:mysql://" + config.ipDBMS + ":" + config.portaDBMS + "/updown";
        try(Connection co = DriverManager.getConnection(s, config.nomeUtenteDBMS, config.passwordDBMS); 
            PreparedStatement ps = co.prepareStatement("INSERT INTO valorisoglia(username, sogliamin, sogliamax) "
                                                                                                        + "VALUES(?, ?, ?);"); 
        ) {
            ps.setString(1, user);
            ps.setInt(2, sogliaMin);
            ps.setInt(3, sogliaMax);
            ps.executeUpdate();
        } catch(SQLException e) {
            System.err.println(e.getMessage());
        } 
        
    }
    
        public static void aggiornaValoriSoglia (ParametriDiConfigurazione config, String user, int sogliaMin, int sogliaMax){  //04
        String s = "jdbc:mysql://" + config.ipDBMS + ":" + config.portaDBMS + "/updown";
        try(Connection co = DriverManager.getConnection(s, config.nomeUtenteDBMS, config.passwordDBMS);
            PreparedStatement ps = co.prepareStatement("UPDATE valorisoglia SET sogliamin = ?, sogliamax = ? WHERE username = ?;"); 
        ) {
           
            ps.setInt(1, sogliaMin);
            ps.setInt(2, sogliaMax);
            ps.setString(3, user);
            ps.executeUpdate();
            
        } catch(SQLException e) {
            System.err.println(e.getMessage());
        }
    }
    
    public static void inserisciMisurazione(ParametriDiConfigurazione config, String user,  //05
                                            int min, int max, LocalDate data, int sogliaMin, int sogliaMax) {
             
        String s = "jdbc:mysql://" + config.ipDBMS + ":" + config.portaDBMS + "/updown";
        try(Connection co = DriverManager.getConnection(s, config.nomeUtenteDBMS, config.passwordDBMS); 
            PreparedStatement ps = co.prepareStatement("INSERT INTO archiviomisurazioni(username, datamisurazione, pressionemin, pressionemax) "
                                                                                                        + "VALUES(?, ?, ?, ?);"); 
        ) {
            ps.setString(1, user);
            ps.setString(2, data.toString());
            ps.setInt(3, min);
            ps.setInt(4, max);
            ps.executeUpdate();
            if(!utenteRegistrato(user, config)) {
                inserisciValoriSoglia(user, sogliaMin, sogliaMax, config);
            } 
        } catch(SQLException e) {
            System.err.println(e.getMessage());
        }        
    }
    
    public static void cancellaMisurazione(ParametriDiConfigurazione config, String user, Misurazione m){   //06
        String s = "jdbc:mysql://" + config.ipDBMS + ":" + config.portaDBMS + "/updown";
        try(Connection co = DriverManager.getConnection(s, config.nomeUtenteDBMS, config.passwordDBMS); 
            PreparedStatement ps = co.prepareStatement("DELETE FROM archiviomisurazioni WHERE username = ? AND datamisurazione = ?;"); 
        ) {
            ps.setString(1, user);
            ps.setString(2, m.getDataMisurazione().toString());
            ps.executeUpdate();
            
        } catch(SQLException e) {
            System.err.println(e.getMessage());
        }
    }
    
    public static void aggiornaMisurazione (ParametriDiConfigurazione config, String user, Misurazione m){  //07
        String s = "jdbc:mysql://" + config.ipDBMS + ":" + config.portaDBMS + "/updown";
        try(Connection co = DriverManager.getConnection(s, config.nomeUtenteDBMS, config.passwordDBMS);
            PreparedStatement ps = co.prepareStatement("UPDATE archiviomisurazioni SET pressioneMin = ?, pressioneMax = ? WHERE username = ? AND dataMisurazione = ?;"); 
        ) {
           
            ps.setInt(1, m.getPressioneMin());
            ps.setInt(2, m.getPressioneMax());
            ps.setString(3, user);
            ps.setString(4, m.getDataMisurazione().toString());
            ps.executeUpdate();
            
        } catch(SQLException e) {
            System.err.println(e.getMessage());
        }
    }
    
    public static String prelevaSogliaMinima(ParametriDiConfigurazione config, String user) {   //08
        
        String s = "jdbc:mysql://" + config.ipDBMS + ":" + config.portaDBMS + "/updown";
        try (Connection co = DriverManager.getConnection(s, config.nomeUtenteDBMS, config.passwordDBMS);
            PreparedStatement ps = co.prepareStatement("SELECT sogliaMin FROM valorisoglia WHERE username = ?;");
        ){
            ps.setString(1, user);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                String min = String.valueOf(rs.getInt("sogliamin"));
                return min;
            }
        } catch (SQLException e) { System.err.println(e.getMessage()); }
        return "";
    }
    
    public static String prelevaSogliaMassima(ParametriDiConfigurazione config, String user) {  //09
        
        String s = "jdbc:mysql://" + config.ipDBMS + ":" + config.portaDBMS + "/updown";
        try (Connection co = DriverManager.getConnection(s, config.nomeUtenteDBMS, config.passwordDBMS);
            PreparedStatement ps = co.prepareStatement("SELECT sogliaMax FROM valorisoglia WHERE username = ?;");
        ){
            ps.setString(1, user);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String max = String.valueOf(rs.getInt("sogliamax"));
                return max;
            }
        } catch (SQLException e) { System.err.println(e.getMessage()); }
        return "";
    }
    
}

/*

Note:

    00) La classe DBMisurazioni si occupa di comunicare con il DB creando le 
        connessioni necessarie.
    01) Funzione che preleva dal DB le misurazioni che ricadono all'interno
        del periodo selezionato dall'utente mediante i due DatePicker.
    02) Funzione che verifica la presenza nel DB dell'utente inserito nel TextField
        e lo cerca nella tabella in cui sono memorizzati i valori soglia preferiti.
    03) Funzione che permette di memorizzare nel DB i valori soglia min e max
        relativi all'utente inserito.
    04) Permette di aggiornare i valori soglia di un utente.
    05) Inserisce una nuova misurazione a quelle dell'utente inserito.
    06) Elimina la misurazione selezionata dalla tabella. Viene richiamata alla
        pressione del button 'Elimina'.
    07) Aggiorna uno o più campi di una misurazione già archiviata.
    08) Funzione che riceve una stringa contenente il nome dell'user e restituisce
        il valore soglia minimo relativo ad esso prelevandolo dal DB.
    09) Funzione che riceve una stringa contenente il nome dell'user e restituisce
        il valore soglia massimo relativo ad esso prelevandolo dal DB.

*/