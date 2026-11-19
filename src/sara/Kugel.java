/**
 * Kugel.java
 *
 *
 * Created: 1999
 *
 * @author Sahara
 * @version 1.0  BETA
**/

package sara;
import java.awt.*;

public class Kugel
{

	private Point Startpunkt;
	private int Startgeschwindigkeit, welcheKanone;
	private int Windrichtung, Windgeschwindigkeit;
	private double BWinkel, Zeit;
	private final double tk = 0.01; // Zeitkonstante
	private final int g = 10; // Erdbeschleunigung
	private Kanone kanone;
	private Point aktuelle_Position;

	public Kugel(int Pulvermenge, int Winkel, int welcheKanone)
	{
		this.Startgeschwindigkeit = Pulvermenge;
		this.welcheKanone = welcheKanone;
		BWinkel = Winkel * (java.lang.Math.PI) / 180; // Bogenmaß
		// Startpunkt fuer kanone1 und kanone2 zuweisen
		if (welcheKanone == 0) // 0 entspricht linke Kanone
			Startpunkt = new Point(30, 145);
		else
			Startpunkt = new Point(270, 145);
		aktuelle_Position = Startpunkt;
		Zeit = 0;
	}

	//Methode zum setzen der Windgeschwindigkeit und der Windrichtung
	public void set_Wind(int Windgeschwindigkeit, String Windrichtung)
	{
		this.Windgeschwindigkeit = Windgeschwindigkeit;
		if (Windrichtung == "West")
			this.Windrichtung = 2;
		else
			this.Windrichtung = 0;
	}

	/**
	* Algorithmus:
	* hier wird die Flugbahn berechnet für ein bestimmten Flugintervall (tk = 0.01)
	* hierzu benutzen wir Rechenmethoden aus der Klasse Math.
	* Für jeden Spielbereich und für jede Koordinatenrichtung ist
	* ein eigener Algorithmus zuständig.
	* Resultierend erhalten wir eine Wurfparabel
	**/

	// Methode(Algorithmus) zur Flugbahnberechnung: gibt aktuelle Position zurueck
	// Zeitkonstante muss eventuell noch angepasst werden.
	public Point get_flugbahn()
	{
		if (welcheKanone == 0)
		{
			aktuelle_Position.x =
				(int) (aktuelle_Position.x
					+ Startgeschwindigkeit * java.lang.Math.cos(BWinkel) * tk
					+ (Windrichtung - 1) * 0.5 * Windgeschwindigkeit * java.lang.Math.pow(tk, 2));
				aktuelle_Position.y = (int) (aktuelle_Position.y - Startgeschwindigkeit * java.lang.Math.sin(BWinkel) * tk - //sin???
	0.5 * g * java.lang.Math.pow(tk, 2));
			Zeit += tk;
		}
		else
		{
			aktuelle_Position.x =
				(int) (aktuelle_Position.x
					- Startgeschwindigkeit * java.lang.Math.cos(BWinkel) * tk
					- (Windrichtung - 1) * 0.5 * Windgeschwindigkeit * java.lang.Math.pow(tk, 2));
				aktuelle_Position.y = (int) (aktuelle_Position.y - Startgeschwindigkeit * java.lang.Math.sin(BWinkel) * tk - //sin??
	0.5 * g * java.lang.Math.pow(tk, 2));
			Zeit += tk;
		}
		return aktuelle_Position;
	}

	public Point get_Pos()
	{
		return aktuelle_Position;
	}

} // Ende class Kugel
