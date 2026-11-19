/**
 * Spielfeld.java
 *
 *
  * Created: 1999
 *
 * @author Sahara
 * @version 1.0  BETA
**/

package sara;
 import java.math.*;
 
 public class Fahne{

	private String Windrichtung;
	private int Windgeschwindigkeit; // Wind < 25
  private double Zufallszahl;

	public Fahne(){
	   Windrichtung = "West";
	   Windgeschwindigkeit = 0;
	}

	public void Zufall_Richtung(){
	   Zufallszahl = java.lang.Math.random();
     if (Zufallszahl >= 0.5)
        Windrichtung = "West";
     else
        Windrichtung = "Ost";
	}

  public void Zufall_Geschwindigkeit(){
	   Zufallszahl = java.lang.Math.random();
     if (Zufallszahl <= 0.3)
        Windgeschwindigkeit = 0;
     if (Zufallszahl >= 0.7)
        Windgeschwindigkeit = 25;
     else
        Windgeschwindigkeit = 10;
	}
	
	public String get_Windrichtung(){
	   Zufall_Richtung();
	   return Windrichtung;
	}
	
	public int get_Windgeschwindigkeit(){
  	 Zufall_Geschwindigkeit();
	   return Windgeschwindigkeit;
	}
	
 } //Ende Klasse Fahne