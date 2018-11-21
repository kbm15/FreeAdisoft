import java.util.concurrent.Semaphore;

public abstract class Ficha extends Thread {
    //Atributos privados de la clase
    protected Aplicacion1 aplicacion1;
    protected int fila = 1;
    protected int columna;
    protected Ficha vecinaIzda;
    protected Ficha vecinaDcha;
    //Variables donde se recoge una peticion
    protected boolean fin = false;
    protected boolean meMatan = true;
    protected int filaVecina;
    protected int columnaVecina;
    protected Estado estado;
    //Herramienta de sincronizacion
    protected Semaphore esperar = new Semaphore(0, false);

    protected boolean puedeAtacar(int filaPreguntada, int columnaPreguntada) {
        /**Como le pregunta una ficha que esta a su derecha, solo
         * puede matarla en la misma fila.*/
        boolean resultado = false;
        resultado = true;

        return resultado;
    }

    @Override
    public void run() {
        //System.err.println("Empieza la Reina "+columna);
        //la primera reina nunca muere
        meMatan = (vecinaIzda == null) ? false : true;
        do {
            switch (estado) {

                case MOVERSE:
                    //System.err.println("^Moviendo la Reina "+columna);
                    while (meMatan && (fila < 8)) {//pregunta hasta 8 veces, moviendose
                        //System.err.println("<Pregunta la Reina "+columna);

                        vecinaIzda.preguntarSiMeMatan(fila, columna);

                        espera();

                        if (meMatan) {
                            //System.err.println("La reina "+columna+" la matan en fila "+ fila);
                            fila += 1;
                            aplicacion1.actualizarFila(fila, columna);
                            //si la matan, se mueve a la siguiente, hasta 8 veces
                        }

                    }//fin while
                    if (fila == 8) {
                        vecinaIzda.preguntarSiMeMatan(fila, columna);
                        espera();

                        if (meMatan) {
                            //System.err.println("La reina "+columna+" la matan en fila "+ fila);
                            fila = 1;
                            aplicacion1.actualizarFila(fila, columna);
                        }
                    }
                    /*si pese a que me he movido 8 posiciones me siguen matando, me quedo
                     * en la ultima posicion y mando recolocarse a la reina izquierda.
                     * si no me matan, me quedo en la que no me matan y mando moverse
                     * a la reina de la derecha*/
                    //System.err.println("Reina "+columna+" en fila "+ fila+ ((meMatan == true) ? " y la matan":" y no la matan") );

                    aplicacion1.actualizarFila(fila, columna);


                    if (meMatan && fila == 1) {
                        estado = Estado.ESPERAR;
                        vecinaIzda.exploraSiguientePos(); //la mandamos volver a moverse a la izquierda
                    } else if (vecinaDcha != null) {
                        vecinaDcha.mandaMoverse();// mandamos moverse a la siguiente
                        estado = Estado.ESPERAR;
                    } else {
                        aplicacion1.printTablero();
                        aplicacion1.incrementarContador();
                        meMatan = true;
                        if (fila == 8) {
                            vecinaIzda.exploraSiguientePos();
                            fila = 1;
                            estado = Estado.ESPERAR;
                        } else {
                            fila += 1;
                        }
                        aplicacion1.actualizarFila(fila, columna);
                    }
                    break;
                case ESPERAR:
                    //System.err.println("_Esperando la Reina "+columna);
                    espera();

                    break;
                case RESPONDER:
                    //nos mete aqui la reina derecha, nos cambia filaVecina y columnaVecina
                    //System.err.println(">recibe pregunta la Reina "+columna);

                    boolean respuesta = puedeAtacar(filaVecina, columnaVecina);

                    /*si no la matamos y no somos la primera reina, preguntamos
                     * a la reina izquierda si puede matar esa posicion*/
                    if (!respuesta && vecinaIzda != null) {
                        //la ponemos en el estado responder
                        vecinaIzda.preguntarSiMeMatan(filaVecina, columnaVecina);
                        espera();

                        respuesta = meMatan;
                    }
                    //respondemos y volvemos a esperar
                    //System.err.println(">contesta la Reina "+columna);

                    vecinaDcha.contestar(respuesta);
                    estado = Estado.ESPERAR;
                    //meMatan = false;
                    break;
                default:
                    //do nothing
                    break;
            }// fin del switch
        } while (!fin);//fin del do-while

        //System.err.println("Posicion: ["+fila+"],["+columna+"]");
        aplicacion1.countDownLatch.countDown();

    }//End of Run

    public void asignaVecinas(Ficha izquierda, Ficha derecha) {
        this.vecinaIzda = izquierda;
        this.vecinaDcha = derecha;
        //El estado inicial sera de moverse para la primera reina y de esperar para las siguientes
        this.estado = (this.vecinaIzda == null) ? Estado.MOVERSE : Estado.ESPERAR;
        start();
    }

    public boolean estoyEsperando() {
        return getState() == Thread.State.RUNNABLE;
    }

    public void mandaMoverse() {
        estado = Estado.MOVERSE;
        esperar.release();
    }

    public void espera() {
        try {
            if (vecinaDcha != null) {
                while (vecinaDcha.estoyEsperando()) {

                    Thread.sleep(10);
                }
            }
            if (vecinaIzda != null) {
                while (vecinaIzda.estoyEsperando()) {
                    Thread.sleep(10);
                }
            }
            esperar.acquire();
            if (vecinaDcha != null) {
                while (vecinaDcha.estoyEsperando()) {

                    Thread.sleep(10);
                }
            }
            if (vecinaIzda != null) {
                while (vecinaIzda.estoyEsperando()) {
                    Thread.sleep(10);
                }
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void exploraSiguientePos() { //la vecina derecha nos obliga a buscar una nueva posicion
        meMatan = true;
        if (fila == 8 && columna != 1) {
            fila = 1;
            aplicacion1.actualizarFila(fila, columna);
            vecinaIzda.exploraSiguientePos();
        } else if (columna != 1) {
            fila += 1;
            aplicacion1.actualizarFila(fila, columna);
            estado = Estado.MOVERSE;
            esperar.release();
        } else if (fila != 8) {
            fila += 1;
            aplicacion1.actualizarFila(fila, columna);
            meMatan = false;
            vecinaDcha.mandaMoverse();
        } else {
            /*Fin del programa*/
            System.exit(0);
        }
    }

    public void preguntarSiMeMatan(int filaReinaDcha, int columnaReinaDcha) {//la vecina dcha nos pregunta
        filaVecina = filaReinaDcha;
        columnaVecina = columnaReinaDcha;
        estado = Estado.RESPONDER;
        //System.err.println("Se formaliza la pregunta a la reina "+ columna);

        esperar.release();
    }

    public void contestar(boolean contestacion) {
        meMatan = contestacion;
        esperar.release();
    }

    //Estados de una Reina
    protected enum Estado {
        MOVERSE, ESPERAR, RESPONDER
    }


}
