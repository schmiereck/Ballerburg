/**
 * Kanone.java
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

public class Kanone extends Gegenstand{

	private int Kugelvorrat,Pulvervorrat,Winkel,Bereich;
   public Spielbereich spielbereich;
   private Vector Punktmengenvektor;
   private Kugel kugel;
   public Eingabefenster eingabefenster;

   public Kanone(Spielbereich spielbereich,Point Startpunkt,Point Endpunkt){
      Kugelvorrat = 30; // siehe Beschreibung
      Pulvervorrat = 1000;
      this.spielbereich = spielbereich;
      Bereich = spielbereich.welcher_Bereich();
      Punktmengenvektor = Punkte_einfuegen(Startpunkt,Endpunkt);
      eingabefenster = new Eingabefenster(new Frame(),
                                        "Bitte geben Sie die Werte ein",
                                        true,this);
      kugel = null;
   }

   public synchronized Kugel schiessen(){
      if (eingabefenster.isVisible() == false)
      	eingabefenster.setVisible(true);
	   return kugel;
   }

   public void erzeuge_Kugel(int Pulververbrauch,int Winkel){
 // es ist noch nicht auf Spielende eingegangen (Kugel- und Pulververbrauch)
      Kugelvorrat = Kugelvorrat - 1;
      Pulvervorrat = Pulvervorrat - Pulververbrauch;
      this.Winkel = Winkel;
      kugel = new Kugel(Pulververbrauch,Winkel,Bereich);
   } //Ende Methode erzeuge_Kugel
   
   public int get_Pulvervorrat(){
      return Pulvervorrat;
   }

   public int get_Kugelvorrat(){
      return Kugelvorrat;
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

   public Vector get_Kanonenvektor(){
      return Punktmengenvektor;
   }

   public Kugel get_Kugel(){
      return kugel;
   }


} //Ende Klasse Kanone