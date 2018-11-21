import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

//TODO: imports necesarios
public class CollectionImpl extends UnicastRemoteObject
        implements Collection
{
    //Private member variables
    private int                m_number_of_books;
    private String        m_name_of_collection;
    //Constructor
    public CollectionImpl(int num, String name) throws RemoteException
    {
        super();//Llama al constructor de UnicastRemoteObject
        m_name_of_collection = name;
        m_number_of_books = num;
    }
    //TODO: Implementar todos los metodos de la interface remota
    public int number_of_books() throws RemoteException
    {
        return m_number_of_books;
    }

    @Override
    public String name_of_collection() {
        return m_name_of_collection;
    }

    @Override
    public void name_of_collection(String _new_value) {
        m_name_of_collection = _new_value;
    }

    public static void main(String args[])
    {
//Crear administrador de seguridad
//TODO: fijar el directorio donde se encuentra el java.policy
        System.setProperty("java.security.policy","file:/D:\\java.policy");
        System.setSecurityManager(new SecurityManager());

//nombre o IP del host donde reside el objeto servidor
        String hostName = "10.3.17.176:32001"; //se puede usar "IP:puerto"
        try
        {
            // Crear objeto remoto
            CollectionImpl obj = new CollectionImpl(1, "foo");
            System.out.println("bar");

//Registrar el objeto remoto
            Naming.rebind("//" + hostName + "/MyCollection", obj);
            System.out.println("Estoy registrado!");
        }
        catch (Exception ex)
        {
            System.out.println(ex);
        }
    }
}
