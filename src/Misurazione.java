import java.sql.*;
import javafx.beans.property.*;

public class Misurazione {      //00
    private final SimpleStringProperty username;
    private final SimpleObjectProperty dataMisurazione;
    private final SimpleIntegerProperty pressioneMin;
    private final SimpleIntegerProperty pressioneMax;

    public Misurazione(String user, Date data, int min, int max) {
        username = new SimpleStringProperty(user);
        dataMisurazione = new SimpleObjectProperty(data);
        pressioneMin = new SimpleIntegerProperty(min);
        pressioneMax = new SimpleIntegerProperty(max);
    }
    
    public String getUsername() {return username.get(); }
    public Date getDataMisurazione() { return (Date)dataMisurazione.get(); }
    public int getPressioneMin() { return pressioneMin.get(); }
    public int getPressioneMax() { return pressioneMax.get(); }
    public void setPressioneMin(int m){ pressioneMin.set(m); }
    public void setPressioneMax(int m){ pressioneMax.set(m); }
}

/*

Note:

    00) Classe base del progetto. Rappresenta la Misurazione con le sue informazioni.
        E' una classe bean.

*/