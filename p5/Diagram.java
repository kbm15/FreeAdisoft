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
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
public class Diagram
		extends JPanel 
		implements MouseListener, 
			   MouseMotionListener, 
			   KeyListener {
	
	//atributos
	private Window window;//Ventana en la que está el diagrama
	public Class clase; 
	
	private Vector<Class> classes = new Vector(); //las clases que crea el usuario
	private Vector<Association> associations = new Vector(); // las asociaciones que crea el usuario
	
	// ... (otros posibles atributos)
	private int createdClasses=0;
	private boolean hovering = false;
	private boolean dragging = false;
	private boolean creatingAssociacion = false;

	//metodos
	public Diagram(Window theWindow) {
		window = theWindow;
		
		addMouseListener(this);
		addMouseMotionListener(this);
		addKeyListener(this);
		
		setBorder(BorderFactory.createLineBorder(Color.black));
	}
	
	public void addClass() { //hecho
		//Añade una clase al diagrama
		Random rand = new Random();
		Class newClass = new Class(++createdClasses,(float)rand.nextInt(50) + 30,(float)rand.nextInt(50) + 30);
		classes.addElement(newClass); // se anyade al final
		window.updateNClasses(this);
		repaint();
	}
	public int getNClasses(){//hecho
		//Devuelve el número de clases
		return classes.size();
	}
	public int getNAssociations(){//hecho
		//Devuelve el numero de asociaciones
		return associations.size();
	}
	@Override
	public void paint(Graphics g) {
		//Esta funcion dibuja el diagrama de clases
 		super.paint(g);		//si se reimplementa paint() se debe invocar el metodo de la superclase
		//dibuja las clases y asociaciones
		for(Association a: associations)
			a.draw(g);
		for(Class c: classes)
			c.draw(g);

	}


	/********************************************/
	/** Metodos de MouseListener               **/
	/********************************************/

	public void mousePressed(MouseEvent e) {
		//si hay clases, y hay clic izquierdo...
		if(!classes.isEmpty()){
			Class c = classes.elementAt(classes.size()-1);
			//si estaba seleccionada la clase donde clicamos CLIC.IZDA, creamos asociacion
			if(c.isSelected()&& c.hovering((float)e.getX(),(float)e.getY())&& SwingUtilities.isLeftMouseButton(e)){//asociar
				dragging=false;
				creatingAssociacion=true; //crea asociacion, la pone en el centro de la clase
				associations.addElement(new Association(c, c.getPosX(),c.getPosY()));
			}else
				creatingAssociacion=false;
			//si no estaba seleccionada la clase donde clicamos
			if(!c.isSelected()&&c.hovering((float)e.getX(),(float)e.getY())&&SwingUtilities.isLeftMouseButton(e)){ //la arrastramos
				if(!dragging)
					dragging=true;
				else
					dragging=false;
			}
 		}

	}
    
    	public void mouseReleased(MouseEvent e) { //conecta las asociaciones
			if(!associations.isEmpty()&& creatingAssociacion) {//si estamos creando asociacion
				Association a = associations.get(associations.size() - 1); //la asociacion actual
				Class c = classes.get(classes.size() - 1);//la clase que esta arriba
				if (c.hovering((float)e.getX(),(float)e.getY())) {//si la linea acaba en una clase conectamos
					a.connect(c);
					c.unsetEndsAssociation();
				}else{// si la linea acaba en ningun punto no se crea asociacion
					associations.remove(associations.size()-1);
					creatingAssociacion=false;
				}
				window.updateNAssociations(this);
				repaint();
			}
			window.updateNAssociations(this);
			repaint();
    	}
    
	    public void mouseEntered(MouseEvent e) {
    	}
    
	public void mouseExited(MouseEvent e) {
    	}
    
	public void mouseClicked(MouseEvent e) { //borra con clic derecho
		if(SwingUtilities.isRightMouseButton(e)){
			if(hovering&&!classes.isEmpty()){
				Class c = classes.elementAt(classes.size()-1);
				int k = 0;
				while(k<associations.size()){
					if(associations.elementAt(k).connectsTo(c))
						associations.removeElementAt(k);
					else
						k++;
				}

				classes.remove(classes.size()-1);
				window.updateNClasses(this);
				window.updateNAssociations(this);
				repaint();
			}

		}

	}

	/********************************************/
	/** MÈtodos de MouseMotionListener         **/
	/********************************************/

	public void mouseMoved(MouseEvent e) { // pone al frente las clases que apunta el raton

		int k = classes.size()-1;
		//todavia no se ha movido ninguna clase-semioculta
		while(k>=0){//recorre las clases que haya
			if(classes.get(k).hovering((float)e.getX(),(float)e.getY())){//si apunta una clase
				hovering=true;
				classes.addElement(classes.remove(k));
				repaint();
				break;//si trae adelante una semioculta para
			}else
				hovering=false;
			k--;
		}
	}
	public void mouseDragged(MouseEvent e) { //mueve clases y asociaciones
		int k = classes.size()-1;
 		if(k!=(-1)&&SwingUtilities.isLeftMouseButton(e)){ //si hay clases
  			while ((k)>=0 ){
 				if(classes.get(k).hovering((float)e.getX(),(float)e.getY())){
 					classes.addElement(classes.remove(k));
 					hovering=true;
 					repaint();
 					break;//cuando ya ha movido una para y no mueve otras o parpadearian constantemente
				}else
 					hovering=false;
				k--;
			}
 			if(dragging)//las arrastra centradas en el raton
 				classes.get(classes.size()-1).move((float)e.getX()-(float)47.5,
						(float)e.getY()-(float)65);
 			else if(creatingAssociacion) {
				associations.get(associations.size() - 1).move((float) e.getX(), (float) e.getY());
				Class c= classes.get(classes.size()-1);
				if(c.hovering((float) e.getX(), (float) e.getY())){
					c.setEndsAssociaion();
				}else
					c.unsetEndsAssociation();
			}
			repaint();
		}
	}
    
	/********************************************/
	/** MÈtodos de KeyListener                 **/
	/********************************************/

	public void keyTyped(KeyEvent e) {
    	}
    
	public void keyPressed(KeyEvent e) {
		if(!classes.isEmpty()&&hovering){//si esta apuntando a una clase y pulsas una tecla
			char keyPressed = e.getKeyChar();
			if(keyPressed=='s'||keyPressed=='S'){
				if((classes.get(classes.size()-1)).isSelected()){//si estaba seleccionada
					classes.get(classes.size()-1).deselect(); //la deseleccionamos
				}else{//no sabemos si habia mas seleccionadas, deseleccionamos todas
					for( Class c: classes)
						c.deselect();
					classes.get(classes.size()-1).select(); //seleccionamos la nueva
				}
			}
		}else if(!classes.isEmpty()){//si pulsas una tecla y no apuntas una clase deselecciona todas
			for( Class c: classes)
				c.deselect();
		}
		repaint();
	}
    
    	public void keyReleased(KeyEvent e) {
    	}
}
