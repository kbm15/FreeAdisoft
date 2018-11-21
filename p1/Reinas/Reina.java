public class Reina extends Ficha {
    public Reina(Aplicacion1 aplicacion1, int columna) {
        this.aplicacion1 = aplicacion1;
        this.columna = columna;
    }

    @Override
    protected boolean puedeAtacar(int filaPreguntada, int columnaPreguntada) {
        /**Como le pregunta una ficha que esta a su dcha, solo
         * puede matarla en la misma fila o en diagonal.*/
        Boolean respuesta;
        respuesta = (filaPreguntada == fila) ? true : false;
        if (respuesta == false) {
            int p = 0;
            for (int k = 1; k <= 8; k += 1) {
                p += 1;
                if (((filaPreguntada) == (fila + p)) && ((columnaPreguntada) == (columna + p)))
                    respuesta = true;
                else if (((filaPreguntada) == (fila - p)) && ((columnaPreguntada) == (columna + p)))
                    respuesta = true;
            }
        }
        return respuesta;
    }
}
