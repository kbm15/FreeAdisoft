/*
 * AUTOR: Daniel Martinez, Arturo Hermosea
 * NIA: 601472,
 * FICHERO: Application.java
 * TIEMPO: 3 dias aproximadamente
 * DESCRIPCION: La clases Application y Window ya estaban hechas, por lo que quedaba realizar la clase diagram que
 * extiende JPanel y es la que contiene las instancias de las clases Class y Association. Tambien actualiza la clase
 * Window cada vez que se realiza un cambio, y ejecuta repaint() para ver los cambios por pantalla.
 */

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.Random;
//otros imports …


public class Association {

    // Atributos
    // ...
    private float x, y;
    private Class class1, class2;
    private boolean unconnected = true;
    private boolean autoAssociation = false;
    private float randWidth;
    private float randHeight;

    // Constructores
    // ...
    public Association(Class parent, float initX, float initY) {
        class1 = parent;
        x = initX;
        y = initY;
    }

    // Más métodos
    // ...
    public void draw(Graphics graphics) {
        // Dibuja la asociación
        Graphics2D graphics2d = (Graphics2D) graphics;
        // arrastrando la linea
        if (unconnected)
            graphics2d.draw(new Line2D.Float(class1.getPosX(), class1.getPosY(), x, y));
        else if (autoAssociation) {
            Random rand = new Random();
            graphics2d.draw(new Line2D.Float(class1.getPosX(), class1.getPosY() - randWidth, class2.getPosX() + randHeight, class2.getPosY() - randWidth));
            graphics2d.draw(new Line2D.Float(class1.getPosX() + randHeight, class1.getPosY() - randWidth, class2.getPosX() + randHeight, class2.getPosY() + randWidth));
            graphics2d.draw(new Line2D.Float(class1.getPosX(), class1.getPosY() + randWidth, class2.getPosX() + randHeight, class2.getPosY() + randWidth));
        } else
            graphics2d.draw(new Line2D.Float(class1.getPosX(), class1.getPosY(), class2.getPosX(), class2.getPosY()));
    }

    public void connect(Class child) {
        class2 = child;
        unconnected = false;
        if (class1 == class2) {
            Random rand = new Random();
            randWidth = (float) rand.nextInt(30) + 40;
            randHeight = (float) rand.nextInt(40) + 60;
            autoAssociation = true;
        }
    }

    public void move(float destX, float destY) {
        x = destX;
        y = destY;
    }

    public boolean connectsTo(Class c) {
        boolean r = false;
        if ((class1 == c) | (class2 == c))
            r = true;
        return r;
    }
}
