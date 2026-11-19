/**
 * Berg.java
 *
 *
 * Created: 1999
 *
 * @author Sahara
 * @version 1.0  BETA
**/

package sara;
 import java.awt.*;
 import java.util.*;

 /**
* Vererbung:   (extends Gegenstand)
*  Berg verwendet beide Methoden von der Klasse Gegenstand 
*
*
**/

 public class Berg extends Gegenstand{

   private Spielfeld spielfeld;
	private Vector Punktmengenvektor, Pruefvektor;

  public Berg(Spielfeld spielfeld, Point Startpunkt, Point Endpunkt){
	   this.spielfeld = spielfeld;
	   Punktmengenvektor = Punkte_einfuegen(Startpunkt,Endpunkt);
  }

  public boolean ueberpruefe(Point Position){
   Pruefvektor = super.pruefe(Position,Punktmengenvektor);
   if (Pruefvektor.size() != Punktmengenvektor.size()){
      Pruefvektor = Punktmengenvektor;
      return true;     //true heiﬂt getroffen
   }
   else
      return false;
  } // Ende Methode ueberpruefen

  public Vector get_Bergvektor(){
     return Punktmengenvektor;
  }

 } //Ende Klasse Berg