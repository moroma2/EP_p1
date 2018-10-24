package ep_p1;

/**
 *
 * @author Pablo Jiménez Risueño
 * @author Mónica Romeu Magraner
 *
 */
public class User implements Comparable{
    int id_user;
    String name, email;
    String direccion, poblacion, provincia;
    float total_alquiler;
    
    public User(){
    }
    
    public User (int id_user, String name, String email, String direccion, String poblacion, String provincia){
        this.id_user = id_user;
        this.name = name;
        this.email = email;
        this.direccion = direccion;
        this.poblacion = poblacion;
        this.provincia = provincia;
        this.total_alquiler = 0;
    }
    
    public String toString(){
        return "PROPIETARIO " + id_user + "\n" +
               "Nombre del propietario: " + name + "\n" +
               "Correo Electronico: " + email + "\n" +
               "Direccion: " + direccion + "\n" +
               "Poblacion: " + poblacion + "\n" +
               "Provincia: " + provincia + "\n" +
               "Total Importe Alquiler: " + total_alquiler + "\n\n";
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getPoblacion() {
        return poblacion;
    }

    public void setPoblacion(String poblacion) {
        this.poblacion = poblacion;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public float getTotal_alquiler() {
        return total_alquiler;
    }

    public void setTotal_alquiler(float total_alquiler) {
        this.total_alquiler = total_alquiler;
    }
    
    public void incrementar (float precio){
        this.total_alquiler +=  precio;
    }
    
    @Override
    public int compareTo(Object compararUser) {
        float compararTotal = ((User)compararUser).getTotal_alquiler();

        return (int) (compararTotal - this.total_alquiler);
    }
}
