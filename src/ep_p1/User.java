package ep_p1;

/**
 *
 * @author Pablo Jiménez Risueño
 * @author Mónica Romeu Magraner
 *
 */
public class User {
    int id_user;
    String name;
    String email;
    
    public User(){
    }
    
    public User (int id_user, String name, String email){
        this.id_user = id_user;
        this.name = name;
        this.email = email;
    }
    
    public String toString(){
        return "PROPIETARIO " + id_user + "\n" +
               "Nombre del propietario: " + name + "\n" +
               "Correo Electronico: " + email + "\n";
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    
}
