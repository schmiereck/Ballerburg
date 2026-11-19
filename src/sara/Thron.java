/**
 * Thron.java
 *
 *
 * Created: 1999
 *
 * @author Sahara
 * @version 1.0  BETA
**/
 
package sara;
 import java.util.*;
 import java.awt.*;
 
 public class Thron extends Gegenstand{
 
    private Spielbereich spielbereich;
    private Vector Punktmengenvektor;

    public Thron(Spielbereich spielbereich,Point Startpunkt,
                 Point Endpunkt){
       this.spielbereich = spielbereich;
       Punktmengenvektor = Punkte_einfuegen(Startpunkt,Endpunkt);
    }

    public boolean ueberpruefe(Point Position){
    if (Punktmengenvektor.contains(Position) == true){
       int Löschmich = Punktmengenvektor.indexOf(Position);
       Punktmengenvektor.removeElementAt(Löschmich);
       //for(){es muessen noch mehr Punkte entfernt werden}
       return true;
    }
    else
       return false;
    }

    public Vector get_Thronvektor(){
       return Punktmengenvektor;
    }

 
 } // Ende class Thron
 
 