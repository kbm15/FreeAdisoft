/*
 * AUTOR: Daniel Martinez, Arturo Hermosea
 * NIA: 601472,
 * FICHERO: Application.java
 * TIEMPO: 3 dias aproximadamente
 * DESCRIPCION: La clases Application y Window ya estaban hechas, por lo que quedaba realizar la clase diagram que
 * extiende JPanel y es la que contiene las instancias de las clases Class y Association. Tambien actualiza la clase
 * Window cada vez que se realiza un cambio, y ejecuta repaint() para ver los cambios por pantalla.
 */

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
//otros import

public class Class {

    //Atributos
    // …
    private String className;//por defecto ya que es version preliminar, no se puede cambiar
    private float x, y;
    private float width = 95;
    private float height = 130;
    private String attributes = "attributes";
    private String operations = "operations";
    private boolean selected = false;
    private boolean dragged = false;
    private boolean endsAssociaion = false;
    public Class(int i, float initX, float initY) {
        //…
        className = "Clase " + i;
        x = initX;
        y = initY;

    }

    public void draw(Graphics g) {
        //Dibuja la clase
        Graphics2D g2 = (Graphics2D) g;
        //relleno la clase de blanco u azul
        if (selected)
            g2.setPaint(Color.cyan);
        else if(endsAssociaion)
            g2.setPaint(Color.green);
        else
            g2.setPaint(Color.white);
        Rectangle2D.Float r = new Rectangle2D.Float(x, y, width, height);
        g2.fill(r);
        //pinto el contorno con lineas...
        g2.setPaint(Color.black); //de negro
        g2.draw(new Line2D.Float(x, y, x + width, y)); //barra superior
        g2.draw(new Line2D.Float(x, y + height / 5, x + width, y + height / 5));//1a barra
        g2.draw(new Line2D.Float(x, y + 6 * height / 10, x + width, y + 6 * height / 10));//2a barra
        g2.draw(new Line2D.Float(x, y + height, x + width, y + height));//base
        g2.draw(new Line2D.Float(x, y, x, y + height));
        g2.draw(new Line2D.Float(x + width, y, x + width, y + height));
        //pongo el nombre, atritubos, operaciones...
        g2.drawString(className, x + 20, y + 17);
        g2.drawString(attributes, x + 18, y + 45);
        g2.drawString(operations, x + 18, y + 100);
    }

    //Otros metodos
    // …
    public void select() {
        selected = true;
    }

    public void deselect() {
        selected = false;
    }

    public boolean isSelected() {
        return selected;
    }

    public void drag() {
        dragged = true;
    }

    public void drop() {
        dragged = false;
    }

    public void move(float destX, float destY) {
        x = destX;
        y = destY;
    }

    public boolean hovering(float pointX, float pointY) {
        boolean hoveringX = (pointX > x) && (pointX < (x + width));
        boolean hoveringY = (pointY > y) && (pointY < (y + height));
        if (hoveringX & hoveringY)
            return true;
        else
            return false;
    }
    public void setEndsAssociaion(){
        endsAssociaion=true;
    }
    public void unsetEndsAssociation(){
        endsAssociaion=false;
    }
    public float getPosX() {
        return x + width / 2;
    }

    public float getPosY() {
        return y + height / 2;
    }
}
