import java.rmi.Naming;
import java.rmi.RMISecurityManager;

//TODO: imports necesarios
public class Cliente
{
    public static void main(String[] args){
        if (System.getSecurityManager() == null) {
//TODO: fijar el directorio donde se encuentra el java.policy
            System.setProperty("java.security.policy","file:/D:\\java.policy");
            System.setSecurityManager(new SecurityManager());        }
        try
        {
//Paso 1 - Obtener una referencia al objeto servidor
            String hostname = "10.3.17.176:32001"; //se puede usar "IP:puerto"
            Collection server =
                    (Collection) Naming.lookup("//"+ hostname + "/MyCollection");
//Paso 2 - Invocar los metodos del objeto servidor:
            System.out.println("Nombre coleccion: " + server.name_of_collection() + ", numero de libros: " + server.number_of_books());
        }
        catch (Exception ex)
        {
            System.err.println(ex);
        }
    }
}