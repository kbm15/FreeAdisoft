public class Torre extends Ficha {
    public Torre(Aplicacion1 aplicacion1, int columna) {
        this.aplicacion1 = aplicacion1;
        this.columna = columna;
    }

    @Override
    protected boolean puedeAtacar(int filaPreguntada, int columnaPreguntada) {
        /**Como le pregunta una ficha que esta a su dcha, esta solo
         * puede matarla en la misma fila, ya que es una torre .*/
        return (filaPreguntada == fila) ? true : false;
    }
}
