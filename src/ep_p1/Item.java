package ep_p1;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Pablo Jiménez Risueño
 * @author Mónica Romeu Magraner
 *
 */
public class Item {
    int id_owner;
    int id_item;
    String description;
    Date ini_date;
    Date fin_date;
    float costs;
    
    public Item (int id_owner, int id_item, String description, Date ini_date, Date fin_date, float costs){
        this.id_owner = id_owner;
        this.id_item = id_item;
        this.description = description;
        this.ini_date = ini_date;
        this.fin_date = fin_date;
        this.costs = costs;
    }

    @Override
    public String toString(){
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return 
               "\t Codigo del objeto: " + id_item + "\n" +
               "\t Descripcion: " + description + "\n" +
               "\t Fecha de disponibilidad: " + dateFormat.format(ini_date) + " - " + dateFormat.format(fin_date) + "\n" +
               "\t Coste del prestamo por dia: " + costs + "\n";
    }
    
    public int getId_owner() {
        return id_owner;
    }

    public void setId_owner(int id_owner) {
        this.id_owner = id_owner;
    }

    public int getId_item() {
        return id_item;
    }

    public void setId_item(int id_item) {
        this.id_item = id_item;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getIni_date() {
        return ini_date;
    }

    public void setIni_date(Date ini_date) {
        this.ini_date = ini_date;
    }

    public Date getFin_date() {
        return fin_date;
    }

    public void setFin_date(Date fin_date) {
        this.fin_date = fin_date;
    }

    public float getCosts() {
        return costs;
    }

    public void setCosts(float costs) {
        this.costs = costs;
    }
}
