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
public class Loan {
    User user;
    int id_item;
    Date ini_date;
    Date fin_date;
    float total_costs;
    float startup;
    
    public Loan(User user, int id_item, Date ini_date, Date fin_date, float total_costs, float startup){
        this.user = user;
        this.id_item = id_item;
        this.ini_date = ini_date;
        this.fin_date = fin_date;
        this.total_costs = total_costs;
        this.startup = startup;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getId_item() {
        return id_item;
    }

    public void setId_item(int id_item) {
        this.id_item = id_item;
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

    public float getTotal_costs() {
        return total_costs;
    }

    public void setTotal_costs(float total_costs) {
        this.total_costs = total_costs;
    }

    public float getStartup() {
        return startup;
    }

    public void setStartup(float startup) {
        this.startup = startup;
    }
    
    @Override
    public String toString(){
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return 
               "\t \t Nombre del cliente: " + user.getName() + "\n" +
               "\t \t Fecha del prestamo: " + dateFormat.format(ini_date) + " - " + dateFormat.format(fin_date) + "\n" +
               "\t \t Importe del prestamo: " + total_costs + "\n" +
               "\t \t Importe del startup: " + startup;
    }
}
