/**
 * Spielbereich.java
 *
 *
 * Created: 1999
 *
 * @author Sahara
 * @version 1.0  BETA
**/

package sara;
import java.awt.*;

public class Spielbereich
{

	public Burg burg;
	public Thron thron;
	public Kanone kanone;
	private int Bereich;
	private Point[] Punkte;
	public Spielfeld spielfeld;

	public Spielbereich(Spielfeld spielfeld, int Bereich)
	{
		this.spielfeld = spielfeld;
		this.Bereich = Bereich;
		burg = new Burg(this, get_Punkte_von_Burg());
		thron = new Thron(this, new Point(65 + (160 * (Bereich - 1)), 175), new Point(75 + (160 * (Bereich - 1)), 190));
		kanone = new Kanone(this, new Point(25 + (245 * (Bereich - 1)), 145), new Point(30 + (245 * (Bereich - 1)), 150));
	}

	public Kugel schiessen()
	{
		return kanone.schiessen();
	}

	public Point[] get_Punkte_von_Burg()
	{ // Fehler???
		Punkte = new Point[12]; // entspricht 6 Rechtecke
		Punkte[0] = new Point(25 + (240 * (Bereich - 1)), 151);
		Punkte[1] = new Point(35 + (240 * (Bereich - 1)), 170);
		Punkte[2] = new Point(25 + (210 * (Bereich - 1)), 171);
		Punkte[3] = new Point(64 + (210 * (Bereich - 1)), 200);
		Punkte[4] = new Point(45 + (190 * (Bereich - 1)), 140);
		Punkte[5] = new Point(65 + (190 * (Bereich - 1)), 170);
		Punkte[6] = new Point(65 + (160 * (Bereich - 1)), 171);
		Punkte[7] = new Point(75 + (160 * (Bereich - 1)), 174);
		Punkte[8] = new Point(65 + (160 * (Bereich - 1)), 191);
		Punkte[9] = new Point(75 + (160 * (Bereich - 1)), 200);
		Punkte[10] = new Point(76 + (139 * (Bereich - 1)), 151);
		Punkte[11] = new Point(85 + (139 * (Bereich - 1)), 200);
		return Punkte; // TODO Kompilierfehler - kann kein Array zurueckgeben??
	}

	public int welcher_Bereich()
	{
		return Bereich;
	}

	public boolean getroffen(Point Position)
	{
		if (burg.ueberpruefe(Position))
			return true;
		if (kanone.ueberpruefe(Position))
			return true;
		else
			return false;
	}

	public boolean voelligAbgeschossen(Point Position)
	{
		if (thron.ueberpruefe(Position))
			return true;
		// if(Kanone.voelligAbgeschossen(Position))
		//    return true;
		else
			return false;
	}

} //Ende Klasse Spielbereich
