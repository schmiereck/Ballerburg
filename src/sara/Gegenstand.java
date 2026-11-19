/**
 * Gegenstand.java
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
*Vererbung:
* Gegenstand vererbt an Berg,Thron,Burg & Kanone.
* Gegenstand stellt zwei Methoden zur Verfügung: Punkte_einfuegen() & pruefe()
*
*
*
*
**/

public class Gegenstand{

   private Vector Punktmengenvektor;

/**
* bekommt ein Startpunkt und ein Endpunkt
* fuellt von oben nach unten und von links nach rechts einen Vector
* mit den einzelnen Pixeln. (es entsteht ein Rechteck)
* gibt den Vector zurück
**/

// Startpunkt oben links, da falschen Koordinatensystem, bitte beachten
   public Vector Punkte_einfuegen(Point Startpunkt,Point Endpunkt){
      Punktmengenvektor = new Vector();
	  	for(int i=Startpunkt.x;i<=Endpunkt.x;i++){
         for(int j=Startpunkt.y;j<=Endpunkt.y;j++){
				Punktmengenvektor.addElement(new Point(i,j));
		 	}
	  	}
      return Punktmengenvektor;
   }            //Ende Methode Punkte_einfuegen

/**
* bekommt einen Vektor und einen Punkt
* vergleicht, ob der Punkt im Vektor enthalten ist
* falls ja, wird der Punkt aus dem Vektor entfernt
* gibt den neuen Vektor zurück
**/

    public Vector pruefe(Point Position,Vector Gegenstandsvektor){
       if (Gegenstandsvektor.contains(Position) == true){
          int Löschmich = Punktmengenvektor.indexOf(Position);
          Gegenstandsvektor.removeElementAt(Löschmich);
          //for(){es muessen noch mehr Punkte entfernt werden}
       }
       return Gegenstandsvektor;
    }

    // methode völligabgeschossen hier rein???

 } //Ende Klasse Gegenstand