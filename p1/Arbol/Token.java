public abstract class Token {

    protected String valor;
    protected boolean numero;
    protected boolean resultado = false;

    public boolean esNumero() { //devuelve false si es operador
        return numero;
    }

    public String devuelveValor() {
        return valor;
    }

    public boolean esLog() {
        return valor.equals("log") ? true : false;
    }


}
