/*
 * AUTOR: Daniel Martinez, Arturo Hermosea
 * NIA: 601472, 601397
 * FICHERO: Application.java
 * TIEMPO: 3 dias aproximadamente
 * DESCRIPCION: La clases Application y Window ya estaban hechas, por lo que quedaba realizar la clase diagram que
 * extiende JPanel y es la que contiene las instancias de las clases Class y Association. Tambien actualiza la clase
 * Window cada vez que se realiza un cambio, y ejecuta repaint() para ver los cambios por pantalla.
 */

import javax.swing.*;
import java.awt.*;


public class Application {

    //metodos
    private static void createAndShowGUI() {
        //creamos el JFrame
        //JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame("Practica 5 : Daniel y Arturo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //creamos la ventana y sus componentes y los anyadimos al JFrame
        Window app = new Window();
        Component contents = app.createComponents();
        frame.getContentPane().add(contents, BorderLayout.CENTER);
        frame.pack();

        //establecemos los atributos del JFrame
        frame.setSize(800, 600);
        frame.setVisible(true);
        frame.setResizable(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
