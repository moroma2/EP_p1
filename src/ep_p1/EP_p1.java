package ep_p1;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Pablo Jiménez Risueño
 * @author Mónica Romeu Magraner
 *
 */
public class EP_p1 {

    static ArrayList<User> arrayUsers = new ArrayList();
    static ArrayList<Item> arrayItems = new ArrayList();
    static ArrayList<Loan> arrayLoans = new ArrayList();
    static int id_user = 1;
    static int id_item = 1;
    
    /**
     * Función principal
     * @param args
     * @throws ParseException 
     */
    public static void main(String[] args) throws ParseException {
       
        selectMenu();
    }
    
    /**
     * Esta función muestra el menú principal
     */
    public static void showMenu(){
        
        System.out.println("-----------------------------");
        System.out.println("1-Alta de usuario");
        System.out.println("2-Alta de objeto");
        System.out.println("3-Alquiler de objeto");
        System.out.println("4-Listar todos los objetos");
        System.out.println("5-Baja de objeto");
        System.out.println("6-Mostrar saldos");
        System.out.println("7-Modificar importe diario de alquiler");
        System.out.println("8-Guardar en un fichero todos los saldos");
        System.out.println("9-Eliminar usuario");
        System.out.println("10-Salir");
        System.out.println("-----------------------------");
    }
    
    /**
     * Comprueba si la cadena que se le pasa es un número
     * 
     * @param string La cadena
     * @return Si es un número o no
     */
    public static boolean isNum(String string){
        
        boolean result;
        
        try{
            Integer.parseInt(string);
            result = true;
        }
        catch(NumberFormatException exception){
            result = false;
        }
        
        return result;
    }
    
    /**
     * Ejecuta las diferentes opciones del menú principal
     * 
     * @throws ParseException 
     */
    public static void selectMenu() throws ParseException{
        
        int num = 0;
        int idUserX = 0;
        
        do
        {
            showMenu();
            System.out.println("Introduzca la opcion deseada: ");
            Scanner f = new Scanner(System.in);
            String numS = f.nextLine();
            System.out.println("-----------------------------");
                        
            if(isNum(numS)){
                num = Integer.parseInt(numS);
                switch(num){
                    case 1:
                        altaUser();
                        break;
                    case 2:
                        if(arrayUsers.size()>0)
                            altaItem();
                        else
                            System.out.println("Registre al menos un usuario.");
                        break;
                    case 3:
                        if(arrayItems.size()>0)
                            altaLoan();
                        else
                            System.out.println("Registre al menos un objeto.");
                        break;
                    case 4:
                        if(arrayUsers.size()>0)
                            mostrarListadoItems();
                        else
                            System.out.println("Registre al menos un usuario.");
                        break;
                    case 5:
                        if(arrayItems.size()>0){
                            mostrarUsers();
                            idUserX = pedirIdUser();
                            bajaItem(idUserX);
                        }
                        else
                            System.out.println("Registre al menos un objeto.");
                        break;
                    case 6:
                        if(arrayUsers.size()>0)
                            mostrarSaldoAcumulado();
                        else
                            System.out.println("Registre al menos un usuario.");
                        break;
                    case 7:
                        if(arrayItems.size()>0)
                            modificarImporte();
                        else
                            System.out.println("Registre al menos un objeto.");
                        break;
                    case 8:
                        if(arrayUsers.size()>0){
                            guardarSaldosFichero();
                            System.out.println("--Fichero 'Saldos.txt' creado-- ");
                        }
                        else
                            System.out.println("Registre al menos un usuario.");
                        break;
                    case 9:
                        if(arrayUsers.size()>0){
                            mostrarUsers();
                            idUserX = pedirIdUser();
                            bajaUser(idUserX);
                            System.out.println("Usuario con ID: " + idUserX + " eliminado. " );
                        }
                        else
                            System.out.println("Registre al menos un usuario.");
                        break;
                    case 10:
                        System.out.println("-- SALIDA --");
                        break;
                    default:
                        System.out.println("-- ERROR: Intoduzca un valor valido --");
                } 
            }
            else{
                System.out.println("-- ERROR: Introduzca un valor valido --");
            }
        }while(num != 10);
        
    }
    
    /**
     * Añade un nuevo usuario a la lista de usuarios
     */
    public static void altaUser(){
        
        System.out.println("Introduzca nombre completo de usuario: ");
        Scanner f1 = new Scanner(System.in);
        String name = f1.nextLine();
        
        System.out.println("Introduzca correo electronico de usuario: ");
        Scanner f2 = new Scanner(System.in);
        String email = f2.nextLine();
        
        if(validarEmail(email)){
            User user = new User(id_user, name, email);
            arrayUsers.add(user);
            System.out.println("-- USUARIO CON ID " + id_user + " CREADO CORRECTAMENTE --");
            id_user++;
        }
        else{
            System.out.println("ERROR. Email no válido. ");
        }        
    }
    
    /**
     * Elimina un usuario del programa de todas las listas
     * @param id_userX El usuario a eliminar
     */
    public static void bajaUser(int id_userX){
        
        if(comprobarUser(id_userX)){
            for(int i = 0; i < arrayUsers.size(); i++){
                if(arrayUsers.get(i).getId_user() == id_userX){
                    arrayUsers.remove(i);
                }
            }
            
            if(tieneItems(id_userX))
                bajaTodosItems(id_userX);
            
            
            for(int j = 0; j < arrayLoans.size(); j++){
                if(arrayLoans.get(j).getUser().getId_user() == id_userX){
                    arrayLoans.remove(j);
                }
            }
                
        }
        else{
            System.out.println("ERROR. El usuario introducido no existe. ");
        }
    }
    
    /**
     * Comprueba si el email introducido es válido
     * 
     * @param email La cadena introducida como email
     * @return Si el email es válido o no
     */
    public static boolean validarEmail(String email){
        
        String regex = "^([0-9a-zA-Z]+[-._+&])*[0-9a-zA-Z]+@([0-9a-zA-Z]+[.])+[a-zA-Z]{2,6}$";
        
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        
        return matcher.matches();
    }
    
    /**
     * Añade un objeto a la lista de objetos
     * 
     * @throws ParseException 
     */
    public static void altaItem() throws ParseException{
        
        boolean fechaOK;
        Date date_ini = new Date();
        Date date_fin = new Date();
        
        mostrarUsers();
        System.out.println("Introduzca el número de usuario: ");
        Scanner f1 = new Scanner(System.in);
        int id = f1.nextInt();
        
        if(comprobarUser(id)){
            do{
                System.out.println("Introduzca la fecha de inicio de disponibilidad (formato: dd/mm/yyyy): ");
                Scanner f2 = new Scanner(System.in);
                String entrada = f2.nextLine();
                fechaOK = comprobarFecha(entrada);
                if(fechaOK)
                    date_ini =new SimpleDateFormat("dd/MM/yyyy").parse(entrada);
            }while(!fechaOK);

            do{
                System.out.println("Introduzca la fecha de fin de disponibilidad (formato: dd/mm/yyyy): ");
                Scanner f3 = new Scanner(System.in);
                String entrada = f3.nextLine();
                fechaOK = comprobarFecha(entrada);
                if(fechaOK)
                    date_fin =new SimpleDateFormat("dd/MM/yyyy").parse(entrada);
                if(date_fin.compareTo(date_ini) <= 0){
                    fechaOK = false;
                    System.out.println("ERROR. La fecha final debe ser al menos un dia superior a la fecha de inicio");
                }
            }while(!fechaOK);

            System.out.println("Introduzca la descripcion del objeto: ");
            Scanner f2 = new Scanner(System.in);
            String description = f2.nextLine();

            System.out.println("Introduzca el coste por dia del prestamo del objeto: ");
            Scanner f3 = new Scanner(System.in);
            float costs = f3.nextFloat();

            Item item = new Item(id, id_item, description, date_ini, date_fin, costs);
            arrayItems.add(item);
            id_item++;
            System.out.println("------ CREADO -----");
            System.out.println(item);
        }
        else{
            System.out.println("ERROR. El usuario introducido no existe. \n");
        }
    }
    
    /**
     * Añade un préstamo a la lista de préstamos
     * 
     * @throws ParseException 
     */
    public static void altaLoan() throws ParseException{
  
        boolean fechaOK;
        Date date_ini = new Date();
        Date date_fin = new Date();
        Date date_ini_item = new Date();
        Date date_fin_item = new Date();
        float costs_item = 0;
        float total_costs;
        float startup;
        User userOK = new User();
        
        mostrarUsers();
        System.out.println("Introduzca el número de usuario que requiere el alquiler: ");
        Scanner f1 = new Scanner(System.in);
        int id_userS = f1.nextInt();
        
        mostrarItems();
        System.out.println("Introduzca el número de objeto: ");
        Scanner f4 = new Scanner(System.in);
        int id_itemS = f4.nextInt();
        
        if(comprobarUser(id_userS) && comprobarItem(id_itemS)){
            for(User user : arrayUsers){
                if(user.id_user == id_userS){
                    userOK = user;
                }
            }
            
            for(Item item : arrayItems){
                if(item.id_item == id_itemS){
                    date_ini_item = item.getIni_date();
                    date_fin_item = item.getFin_date();
                    costs_item = item.getCosts();
                }
            }
            do{
                System.out.println("Introduzca la fecha de inicio de alquiler (formato: dd/mm/yyyy): ");
                Scanner f2 = new Scanner(System.in);
                String entrada = f2.nextLine();
                fechaOK = comprobarFecha(entrada);
                if(fechaOK)
                    date_ini =new SimpleDateFormat("dd/MM/yyyy").parse(entrada);
                if(date_ini.compareTo(date_ini_item) < 0 || date_ini.compareTo(date_fin_item) >= 0){
                    fechaOK = false;
                    System.out.println("ERROR. Fecha fuera de rango de disponibilidad");
                }
            }while(!fechaOK);

            do{
                System.out.println("Introduzca la fecha de fin de alquiler (formato: dd/mm/yyyy): ");
                Scanner f3 = new Scanner(System.in);
                String entrada = f3.nextLine();
                fechaOK = comprobarFecha(entrada);
                if(fechaOK)
                    date_fin =new SimpleDateFormat("dd/MM/yyyy").parse(entrada);
                if(date_fin.compareTo(date_ini) <= 0 || date_fin.compareTo(date_fin_item) > 0){
                    fechaOK = false;
                    System.out.println("ERROR. Fecha fuera de rango de disponibilidad");
                }
            }while(!fechaOK);

            total_costs = calculaCosts(date_ini, date_fin, costs_item);
            startup = (total_costs * 10) / 100;

            Loan loan = new Loan(userOK, id_itemS, date_ini, date_fin, total_costs, startup);
            arrayLoans.add(loan);
            System.out.println("------ CREADO ------");
            System.out.println(loan);
        }
        else{
            System.out.println("ERROR. El usuario introducido no existe. ");
        }
    }
    
    /**
     * Muestra todos los usuarios de la lista arrayUsers
     */
    public static void mostrarUsers(){
        
        System.out.println("-----------------------------");
        for(User user : arrayUsers){
            System.out.println(user);
        }
        System.out.println("-----------------------------");
    }
    
    /**
     * Muestra todos los objetos de un usuario
     * 
     * @param id_userX El identificador del usuario
     * @return Si el usuario tiene items o no
     */
    public static boolean mostrarItemsUser(int id_userX){
        
        boolean tiene_items = false;

        System.out.println("-----------------------------");
        for(Item item : arrayItems){
            if(item.getId_owner() == id_userX){
                System.out.println(item);
                tiene_items = true;
            }
        }
        System.out.println("-----------------------------");

        return tiene_items;
    }
    
    /**
     * Muestra todos los objetos de la lista arrayItems
     */
    public static void mostrarItems(){
        
        System.out.println("-----------------------------");
        for(Item item : arrayItems){
            System.out.println(item);
        }
        System.out.println("-----------------------------");
    }
    
    /**
     * Comprueba si el usuario existe en la lista de usuarios
     * 
     * @param id_userX El identificador del usuario que se quiere buscar
     * @return Si existe o no en la lista
     */
    public static boolean comprobarUser(int id_userX){
        
        boolean existe = false;
        
        for(User user : arrayUsers){
            if(user.id_user == id_userX){
                existe = true;
            }
        }
            
        return existe;
    }
    
    public static boolean tieneItems(int id_userX){
        
        boolean tiene_items = false;
        
        for(int i = 0; i < arrayUsers.size() && !tiene_items; i++) {
            if(arrayUsers.get(i).getId_user() == id_userX){
                for(int j = 0; j < arrayItems.size() && !tiene_items; j++){
                    if(arrayItems.get(i).getId_owner() == id_userX)
                        tiene_items = true;
                }
            }            
        }
        
        return tiene_items;
    }
    
    /**
     * Comprueba si el objeto existe en la lista de objetos
     * 
     * @param id_itemX El identificador del objeto que se quiere buscar
     * @return Si existe o no en la lista
     */
    public static boolean comprobarItem(int id_itemX){
        
        boolean existe = false;
        
        for(Item item : arrayItems){
                if(item.id_item == id_itemX){
                    existe = true;
                }
        }
        
        return existe;
    }
    
    /**
     * Comprueba la validez de una fecha pasada como parámetro
     * 
     * @param entrada La fecha guardada en una cadena
     * @return Si la fecha es correcta o no
     * @throws ParseException 
     */
    public static boolean comprobarFecha(String entrada) throws ParseException{
       
        boolean fechaOK = false;
        String[] parts = entrada.split("/");
        String diaS = parts[0];
        String mesS = parts[1];
        String anyoS = parts[2];
        int dia = Integer.parseInt(diaS);
        int mes = Integer.parseInt(mesS);
        int anyo = Integer.parseInt(anyoS);
        
        if(anyo < 1){
            System.out.println("ERROR. Fecha incorrecta. ");
        }
        else{
            if(mes < 1 || mes > 12){
                System.out.println("ERROR. Fecha incorrecta. ");
            }
            else{
                if(mes == 1 || mes == 3 || mes == 5 || mes == 7 || mes == 8 || mes == 10 || mes == 12){ //meses con 31 dias
                    if(dia > 31 || dia < 1)
                        System.out.println("ERROR. Fecha incorrecta. ");
                    else{
                        fechaOK = true;
                    }

                }
                else{
                    if(mes == 2){ //febrero
                        if(anyo % 4 == 0){ //bisiesto
                            if(dia > 29 || dia < 1)
                                System.out.println("ERROR. Fecha incorrecta. ");
                            else{
                                fechaOK = true;
                            }
                        }
                        else{ //no bisiesto
                            if(dia > 28 || dia < 1)
                                System.out.println("ERROR. Fecha incorrecta. ");
                            else{
                                fechaOK = true;
                            }
                        }
                    }
                    else { //meses con 30 dias
                        if(dia > 30 || dia < 1)
                            System.out.println("ERROR. Fecha incorrecta. ");
                        else{
                            fechaOK = true;
                        }
                    }
                }
            }
        }
        return fechaOK;
    }
    
    /**
     * Calcula el coste total del préstamo de un objeto
     * 
     * @param date_ini La fecha de inicio del préstamo
     * @param date_fin La fecha fin del préstamo
     * @param costs El coste por día del préstamo
     * @return El coste total del préstamo del objeto
     */
    public static float calculaCosts(Date date_ini, Date date_fin, float costs){
        
        float total;
        int dias = 0;
       
        while(date_ini.compareTo(date_fin) != 0){
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date_ini); // Configuramos la fecha que se recibe
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            date_ini = calendar.getTime();
            dias++;
        }
        
        total = costs * dias;
        return total;
    }
    
    /**
     * Muestra la lista de los propietarios, sus objetos asociados,
     * y los alquileres de dichos objetos
     */
    public static void mostrarListadoItems(){
        
        int items, loans;
        
        for(User user : arrayUsers){
            items = 0;
            System.out.println(user);
            System.out.println("\n\t OBJETOS DEL PROPIETARIO " + user.getId_user() + "\n");
            for(Item item : arrayItems){
                loans = 0;
                if(item.getId_owner() == user.getId_user()){
                    System.out.println(item);
                    System.out.println("\t \t PRESTAMOS DEL OBJETO " + item.getId_item() + "\n");
                    for(Loan loan : arrayLoans){
                        if(loan.getId_item() == item.getId_item()){
                            System.out.println(loan);
                            System.out.println("\n");
                            loans++;
                        }
                    }
                    if(loans == 0){
                        System.out.println("\t \t El objeto " + item.getId_item() + " no tiene prestamos asociados. \n");
                    }
                    items++;
                }
            }
            if(items == 0){
                System.out.println("\t El propietario " + user.getId_user() + " no tiene objetos asociados. \n");
            }
        }
    }
    
    /**
     * Muestra la lista de los propietarios, sus objetos asociados,
     * y los alquileres de dichos objetos
     */
    public static void mostrarSaldoAcumulado(){
        
        int loans;
        for(User user : arrayUsers){
            if(tienePrestamo(user.getId_user())) {
                float total_startup = 0;
                System.out.println(user);
                
                for(Item item : arrayItems){
                    loans = 0;
                    if(item.getId_owner() == user.getId_user()){
                        System.out.println("\t \t PRESTAMOS DEL OBJETO " + item.getId_item() + "\n");
                        for(Loan loan : arrayLoans){
                            if(loan.getId_item() == item.getId_item()){
                                System.out.println(loan);
                                loans++;
                                total_startup += loan.getStartup();
                            }
                        }
                        if(loans == 0){
                            System.out.println("\t \t El objeto " + item.getId_item() + " no tiene prestamos asociados. \n");
                        }
                    }
                }
                if(total_startup > 0) {
                    System.out.println("\nImporte total acumulado para la startup: " + total_startup + " euros. \n");
                }
            }
        }
    }
    
    /**
     * Guarda los saldos de los propietarios con objetos alquilados
     * en un fichero de texto
     */
    public static void guardarSaldosFichero(){
        
        int loans;
        String file_name = "Saldos.txt";
        
        FileWriter fw = null;
        
        try{
            fw = new FileWriter(file_name);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter f = new PrintWriter(bw);
            
            for(User user : arrayUsers){
                if(tienePrestamo(user.getId_user())) {
                    float total_startup = 0;
                    f.println(user);

                    for(Item item : arrayItems){
                        loans = 0;
                        if(item.getId_owner() == user.getId_user()){
                            f.println("\t \t PRESTAMOS DEL OBJETO " + item.getId_item() + "\n");
                            
                            for(Loan loan : arrayLoans){
                                if(loan.getId_item() == item.getId_item()){
                                    f.println(loan);
                                    loans++;
                                    total_startup += loan.getStartup();
                                }
                            }
                            if(loans == 0){
                                f.println("\t \t El objeto " + item.getId_item() + " no tiene prestamos asociados. \n");
                            }
                        }
                    }
                    if(total_startup > 0) {
                        f.println("\nImporte total acumulado para la startup: " + total_startup + " euros. \n");
                    }
                }   
            }
            
            f.close();
            
        }
        catch (IOException ex){    
        }
        
        
    }
    
    /**
     * Pide por pantalla el número ID de un usuario
     * @return El ID del usuario 
     */
    public static int pedirIdUser(){
                
        System.out.println("Introduzca el número de usuario: ");
        Scanner f1 = new Scanner(System.in);
        int id = f1.nextInt();
        
        return id;
    }
    
    /**
     * Da de baja un objeto de un propietario
     * @param id_userS
     */
    public static void bajaItem(int id_userS){
                
        if((mostrarItemsUser(id_userS))){
            System.out.println("Introduzca el número de objeto: ");
            Scanner f2 = new Scanner(System.in);
            int id_itemS = f2.nextInt();
            
            for(int i = 0; i < arrayItems.size(); i++){
                if(arrayItems.get(i).getId_item() == id_itemS){
                    arrayItems.remove(i);
                }
            }
        }
        else{
            System.out.println("\t El propietario " + id_userS + " no tiene objetos asociados");
        }
    }  
    
    public static void bajaTodosItems(int id_userS){
                
        for(int i = 0; i < arrayItems.size(); i++){
                arrayItems.remove(i);
        }
    }
    
    /**
     * Comprueba si el usuario tiene algún objeto prestado
     * 
     * @param id_userX El identificador del usuario 
     * @return Si tiene algún objeto prestado o no
     */
    public static boolean tienePrestamo(int id_userX) {
        boolean tiene_prestamo = false;
        
        for(int i = 0; i < arrayItems.size() && !tiene_prestamo; i++) {
            if(arrayItems.get(i).getId_owner() == id_userX){
                for(int j = 0; j < arrayLoans.size() && !tiene_prestamo; j++){
                    if(arrayLoans.get(j).getId_item() == arrayItems.get(i).getId_item()){
                        tiene_prestamo = true;
                    }
                }
            }            
        }
        
        return tiene_prestamo;
    }
    
    /**
     * Modifica el importe de un objeto en alquiler
     */
    public static void modificarImporte(){
        float costs_item;
        int id_itemS = 0;
        
        mostrarUsers();
        System.out.println("Introduzca el número de usuario: ");
        Scanner f1 = new Scanner(System.in);
        int id_userS = f1.nextInt();
        
        if(mostrarItemsUser(id_userS)){
            System.out.println("Introduzca el número de objeto: ");
            Scanner f4 = new Scanner(System.in);
            id_itemS = f4.nextInt();
        }
        
        if(comprobarUser(id_userS) && mostrarItemsUser(id_userS)){
            System.out.println("Introduzca el nuevo importe diario: ");
            Scanner f5 = new Scanner(System.in);
            costs_item = f5.nextFloat();
            for(Item item : arrayItems){
                if(item.id_item == id_itemS){
                    item.setCosts(costs_item);
                }
            }
        }
        else{
            System.out.println("ERROR. El usuario introducido no existe o no tiene objetos asociados. ");
        }
    }
}
