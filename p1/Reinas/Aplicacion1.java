import java.util.LinkedList;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;


public class Aplicacion1 {
    private static final int NUM_REINAS = 8;
    Ficha[] reinas = new Ficha[NUM_REINAS];
    CountDownLatch countDownLatch = null;
    private int[] filaReina = null;
    private Semaphore mutex = new Semaphore(1, false);
    private int turnoReina;
    private int contadorSolucion = 1;
    private LinkedList<Ficha> fichas = null;
    private char[] figuras = new char[NUM_REINAS];

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String str = "";
        System.out.print("\n*Consejo: Es mejor redirigir stdOut y stdErr a un fichero de texto.\n" +
                "*Ejemplo en unix: $ java Aplicacion1 \"RTTRRRR\" > resultado.txt 2>&1\n" +
                "\"RRRRRRRT\" para colocar 7 reinas y una torre al final\n");
        if (args.length != 1)
            System.exit(-1);
        else
            str = args[0];
        if (str.length() != 8) {
            System.out.println("Has introducido " + str.length() + " Fichas. Debes introducir 8 Reinas o Torres.");
            System.exit(-1);
        }


        char[] charArray = str.toCharArray();


        Aplicacion1 aplicacion1 = new Aplicacion1(); //instancia de la clase aplicacion1
        aplicacion1.fichas = new LinkedList<>();

        aplicacion1.filaReina = new int[8];
        aplicacion1.turnoReina = 1;
        for (int k = 0; k < 8; k++)
            aplicacion1.filaReina[k] = 0;

        aplicacion1.countDownLatch = new CountDownLatch(8);

        for (int k = 0; k < 8; k += 1) { //crea 8 instancias de la clase Reina
            System.out.println("Una " + (charArray[k] == 'R' ? "Reina" : "Torre") + " en posicion " + (k + 1));
            if (charArray[k] == 'R')
                aplicacion1.reinas[k] = new Reina(aplicacion1, k + 1);
            else
                aplicacion1.reinas[k] = new Torre(aplicacion1, k + 1);
            aplicacion1.figuras[k] = charArray[k];
        }
        for (int k = 0; k < 8; k += 1) { //asigna las vecinas izquierda y derecha a cada reina
            aplicacion1.reinas[k].asignaVecinas(k == 0 ? null : aplicacion1.reinas[k - 1], k == 7 ? null : aplicacion1.reinas[k + 1]);
            //aplicacion1.reinas[k].start();
        }

        aplicacion1.printTablero();

        try {
            aplicacion1.countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void incrementarContador() {
        System.out.println(contadorSolucion);
        contadorSolucion += 1;
    }

    public void printTablero() {
        for (int row = NUM_REINAS - 1; row >= 0; row -= 1) {
            System.out.println("");
            System.out.println("---------------------------------");
            for (int column = 0; column < NUM_REINAS; column++)
                System.out.print("|" + " " + (filaReina[column] == (row) ? figuras[column] : " ") + " ");
            System.out.print("|");
        }
        System.out.println("");
        System.out.println("---------------------------------");
    }

    public void actualizarFila(int x, int y) {
        filaReina[y - 1] = x - 1;
    }

}
