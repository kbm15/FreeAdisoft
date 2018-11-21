public class Operador extends Token {
    private int precedencia;
    private boolean parentesis;

    public Operador(String tipoDeOperador, int precedencia) {
        this.numero = false;
        this.valor = tipoDeOperador;
        this.precedencia = precedencia;
        this.parentesis = (precedencia == 0) ? true : false;
    }

    public int getPrecedencia() {
        return precedencia;
    }

    public boolean esParentesis() {
        return parentesis;
    }
}
