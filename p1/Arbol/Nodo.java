import java.util.LinkedList;

public class Nodo {
    private String valor = null;
    private Tipo tipo = null;
    private LinkedList<Nodo> lista = new LinkedList<>();
    private int numeroNodo = 0;
    private boolean resultado = false;

    public Nodo(int numeroNodo, String valor, Nodo... hijos) {
        this.numeroNodo = numeroNodo;
        this.valor = valor;
        this.tipo = clasificar(valor);
        if (hijos != null)
            for (Nodo n : hijos)
                lista.addLast(n);
    }
    public void asignarHijos(int numeroNodo, Nodo... hijos){
        this.numeroNodo = numeroNodo;
        this.resultado = true;
        for (Nodo n: hijos)
            lista.addLast(n);
    }
    public boolean esLog(){
        return tipo == Tipo.logaritmo ? true : false;
    }
    public boolean esRes(){
        return resultado;
    }
    public void setRes(){
        this.resultado = true;
    }
    public boolean esNum(){
        return tipo == Tipo.numero ? true : false;
    }
    public String toStr(){
        return this.valor;
    }
    public int getNum(){
        return this.numeroNodo;
    }

    public static void main(String[] args) {
        LinkedList<Nodo> arbol = new LinkedList<>();
        arbol.add(new Nodo(1,"1"));
        arbol.add(new Nodo(2,"1"));
        int[] vector = {1, 2, 3};
        for (int i : vector)
            System.err.println("i:" + i);
        Nodo a = new Nodo(4,"3");
        arbol.add(new Nodo(3,"9", a, a));

    }

    private Tipo clasificar(String contenido) {
        if (contenido.equals("*"))
            return Tipo.producto;
        else if (contenido.equals("/"))
            return Tipo.division;
        else if (contenido.equals("+"))
            return Tipo.suma;
        else if (contenido.equals("-"))
            return Tipo.resta;
        else if (contenido.equals("log"))
            return Tipo.logaritmo;
        else
            return Tipo.numero;
    }

    public int evaluar() {
        int resultado = -1;
        if (tipo == Tipo.numero)
            resultado = Integer.parseInt(valor);
        else if (tipo != Tipo.logaritmo) {
            resultado = lista.getFirst().evaluar();
            if (tipo == Tipo.suma)
                for (int k = 1; k < lista.size(); k += 1)
                    resultado += lista.get(k).evaluar();
            else if (tipo == Tipo.resta)
                for (int k = 1; k < lista.size(); k += 1)
                    resultado -= lista.get(k).evaluar();
            else if (tipo == Tipo.producto)
                for (int k = 1; k < lista.size(); k += 1)
                    resultado *= lista.get(k).evaluar();
            else if (tipo == Tipo.division)
                for (int k = 1; k < lista.size(); k += 1)
                    resultado /= lista.get(k).evaluar();
        } else
            resultado = (int) Math.log10(lista.getFirst().evaluar());
        return resultado;
    }

    public void imprimir2() {
        crearArbol2("",true);
    }
    private void crearArbol2(String prefijo,boolean ultimo) {
        System.out.print(numeroNodo == 0 ? " " : numeroNodo);
        System.out.println(prefijo + (ultimo ? " ╚═══════" : " ╠═══════") + "( " + valor + " ) ");
        for (int i = 0; i < lista.size() - 1; i++) {
            lista.get(i).crearArbol2( prefijo + (ultimo ? "         " : " ║        "),false);
        }
        if (lista.size() > 0) {
            lista.get(lista.size() - 1).crearArbol2(prefijo + (ultimo ? "         " : " ║        "), true);
        }
    }

    private enum Tipo {
        numero, logaritmo, suma, resta, producto, division, resultado
    }

}
