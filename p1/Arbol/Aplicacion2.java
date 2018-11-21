import java.util.LinkedList;
import java.util.Scanner;

/**
 * Aplicacion 2: se introduce la expresion a evaluar
 * por teclado, y el programa va clasificando los numeros
 * y funciones, atendiendo a la prioridad que introducen los
 * parentesis. Al final se evalua el resultado y se muestra
 * el arbol de nodos formado, permitiendo evaluar cada nodo
 * intermedio de forma individual.
 *
 * @author grupo de practicas Arturo y Daniel
 */
public class Aplicacion2 {
    public static void colocarOperador(LinkedList<Operador> inStack, LinkedList<Token> outStack, Operador newOperator) {
        /**
         * @param inStack lista con los operadores que ya estaban introducidos en la pila de operadores
         * @param outStack lista de salida con numeros y operadores
         * @param newOperator nuevo operador a colocar en la lista de operadores
         * @return void
         */
        while ((!inStack.isEmpty()) && newOperator.getPrecedencia() <= inStack.getLast().getPrecedencia() && !inStack.getLast().esParentesis()) {
            outStack.add(inStack.removeLast());
        }
        inStack.addLast(newOperator);

    }

    public static void cerrarParentesis(LinkedList<Operador> inStack, LinkedList<Token> outStack) {
        /**
         * Se encarga de eliminar un parentesis.
         * @param inStack lista con los operadores que ya estaban introducidos en la pila de operadores
         * @param outStack lista de salida con numeros y operadores
         * @return void
         */
        while (inStack.getLast().esParentesis() == false) {
            outStack.addLast(inStack.removeLast());
        }
        inStack.removeLast();
    }

    public static boolean esNumero(String teclado) {
        /**
         * @param teclado String introducido por teclado
         * @return boolean devuelve true si el String es un numero
         */
        for (char c : teclado.toCharArray()) {
            if (!Character.isDigit(c)) return false;
        }
        return true;
    }

    public static void printPilas(LinkedList<Operador> inStack, LinkedList<Token> outStack) {
        /**
         * funciona de debugger, se encarga de mostrar por pantalla ambas pilas, para ver los errores
         * @param inStack lista con los operadores que ya estaban introducidos en la pila de operadores
         * @param outStack lista de salida con numeros y operadores
         * @return void
         */

        for (int l = 0; l < outStack.size(); l += 1)
            System.out.print(outStack.get(l).devuelveValor() + " ");
        System.out.print("\n");
        for (int l = 0; l < inStack.size(); l += 1)
            System.out.print(inStack.get(l).devuelveValor() + " ");
        System.out.print("\n");

    }

    public static void mensajeError() {
        System.out.println("\nEl programa debe ejecutarse de la forma siguiente:" +
                "\n$java Aplicacion2 \"expresion a evaluar\"\nPor ejemplo: " +
                "$java Aplicacion2 \"3*(12+log(7+5))\"\n");
        System.exit(-1);
    }

    public static void main(String[] args) {
        final int FUNCION = 4, SUMA = 2, RESTA = 2, PRODUCTO = 3, DIVISION = 3, PARENTESIS = 0; // prioridades
        String texto = "3*(12+log(7+5))";
        if (args.length != 1)
            mensajeError();
        else
            texto = args[0];
        System.out.println("Expresion:\t\t\t" + texto);
        String dato = "";
        LinkedList<Operador> pilaDeOperadores = new LinkedList<Operador>();
        LinkedList<Token> pilaDeSalida = new LinkedList<Token>();
        char[] charArray = texto.toCharArray();
        int k = 0;
        while (k < charArray.length) {
            if (charArray[k] == '#') //caracter nulo
                k += 1;
            else if (Character.isDigit(charArray[k])) {
                while (k < charArray.length && Character.isDigit(charArray[k])) {
                    dato += Character.toString(charArray[k]);
                    k += 1;
                }
                pilaDeSalida.add(new Numero(dato));
                dato = "";
            } else if (Character.isAlphabetic(charArray[k])) {    //funcion
                while (k < charArray.length && Character.isAlphabetic(charArray[k])) { // '(' no es alfabetico
                    dato += Character.toString(charArray[k]);
                    k += 1;
                }
                colocarOperador(pilaDeOperadores, pilaDeSalida, new Operador(dato, FUNCION));
                dato = "";
            } else if (charArray[k] == '+' || charArray[k] == '-') {
                if (charArray[k] == '+')
                    colocarOperador(pilaDeOperadores, pilaDeSalida, new Operador("+", SUMA));
                else
                    colocarOperador(pilaDeOperadores, pilaDeSalida, new Operador("-", RESTA));
                k += 1;
            } else if (charArray[k] == '*') {
                colocarOperador(pilaDeOperadores, pilaDeSalida, new Operador("*", PRODUCTO));
                k += 1;
            } else if (charArray[k] == '/') {
                colocarOperador(pilaDeOperadores, pilaDeSalida, new Operador("/", DIVISION));
                k += 1;
            } else if (charArray[k] == '(') {
                pilaDeOperadores.add(new Operador("(", PARENTESIS));
                k += 1;
            } else if (charArray[k] == ')') {
                cerrarParentesis(pilaDeOperadores, pilaDeSalida);
                k += 1;
            }
        }// END OF WHILE
        while (!pilaDeOperadores.isEmpty())
            pilaDeSalida.add(pilaDeOperadores.removeLast());
        k = 0;
        System.out.print("Notacion postfix:\t");
        while (k < (pilaDeSalida.size()))
            System.out.print(pilaDeSalida.get(k++).devuelveValor() + " ");
        k = 0;
        LinkedList<Nodo> aux = new LinkedList<>();
        for (Token t : pilaDeSalida)
            aux.addLast(new Nodo(0, t.devuelveValor()));
        int numeroNodo = 1;
        LinkedList<Nodo> arbol = new LinkedList<>();
        boolean terminado = false;
        do {
            if (k < aux.size() && aux.get(k).esLog() && !aux.get(k).esRes()) { // logaritmo no resultado -> absorbe nodo anterior
                if (aux.get(k - 1).esNum())
                    aux.get(k - 1).asignarHijos(numeroNodo++, aux.remove(k - 1));
                else if (aux.get(k - 1).esRes()) {
                    arbol.addLast(aux.remove(k - 1)); //si era un resultado, se guarda en el otro arbol
                    aux.get(k - 1).asignarHijos(numeroNodo++, arbol.getLast());
                }
            } else if (k < aux.size() && !aux.get(k).esNum() && !aux.get(k).esRes() && !aux.get(k).esLog()) { // funcion no resultado -> absorbe nodo anterior
                if (aux.get(k - 1).esNum() && aux.get(k - 2).esNum()) {
                    aux.get(k).asignarHijos(numeroNodo++, aux.remove(k - 1), aux.remove(k - 2));
                } else if (aux.get(k - 1).esRes() && aux.get(k - 2).esNum()) {
                    arbol.addLast(aux.remove(k - 1)); //si era un resultado, se guarda en el otro arbol
                    aux.get(k - 1).asignarHijos(numeroNodo++, aux.remove(k - 2), arbol.getLast());
                } else if (aux.get(k - 1).esRes() && aux.get(k - 2).esRes()) {
                    arbol.addLast(aux.remove(k - 2)); //si era un resultado, se guarda en el otro arbol
                    arbol.addLast(aux.remove(k - 2)); //si era un resultado, se guarda en el otro arbol
                    aux.get(k - 2).asignarHijos(numeroNodo++, arbol.getLast(), arbol.get(arbol.size() - 2));
                } else if (aux.get(k - 1).esNum() && aux.get(k - 2).esRes()) {
                    arbol.addLast(aux.remove(k - 2)); //si era un resultado, se guarda en el otro arbol
                    aux.get(k - 1).asignarHijos(numeroNodo++, aux.remove(k - 2), arbol.getLast());
                }
            }
            for (Nodo n : aux) {
                int numeroRes = 0;
                if (n.esRes())
                    numeroRes += 1;
                if (numeroRes == aux.size())
                    terminado = true;
            }
            if (terminado)
                for (int m = 0; m < aux.size(); m++)
                    arbol.addLast(aux.removeLast());
            k = k < (aux.size() - 1) ? ++k : 0;
        } while (!terminado); // fin While
        System.out.println("\n╔═══════════════════════════════╗\n" +
                "║\tResultado:\t" + arbol.getLast().evaluar() + "\t║\n" +
                "╚═╦═════════════════════════════╝");
        arbol.getLast().imprimir2();
        Scanner sc = new Scanner(System.in);
        String str;
        do {
            System.out.print("Introduce el numero del nodo a evaluar, o escribe \"exit\" para salir...\n>");
            str = sc.nextLine();
            if (str.equals("exit"))
                System.exit(0);
            else if (esNumero(str))
                for (Nodo n : arbol)
                    if (n.getNum() == Integer.parseInt(str)) {
                        System.out.println("Nodo " + str + ": " + n.evaluar() + "\t");
                        n.imprimir2();
                    }

        } while (true);


    }//end of main


}