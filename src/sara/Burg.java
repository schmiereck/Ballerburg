/**
 * Burg.java
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

public class Burg extends Gegenstand
{

	private Spielbereich spielbereich;
	private Vector[] Punktmengenvektor;
	private int Arraylaenge, Rechteckanzahl;
	private boolean Rueckgabewert;

	/**
	* Util: Vector
	*  Ein Vector kann eine Menge von Objekten beinhalten (ähnlich wie Array)
	*  Es ist möglich die einzelnen Objekte, die nummeriert sind, einzeln heraus-
	*  zunehmen und auch Neue hinzuzufügen.
	*  hier: Punktmengenvektor 
	**/

	public Burg(Spielbereich spielbereich, Point[] Start_End_Punkte)
	{
		this.spielbereich = spielbereich;
		Arraylaenge = Start_End_Punkte.length;
		Rechteckanzahl = Arraylaenge / 2;
		Punktmengenvektor = new Vector[Rechteckanzahl];
		for (int i = 0; i < Rechteckanzahl; i++)
		{
			Punktmengenvektor[i] = new Vector(); //ist das nötig???
			Punktmengenvektor[i] = Punkte_einfuegen(Start_End_Punkte[i * 2], Start_End_Punkte[(i * 2) + 1]);
		}
	}

	public boolean ueberpruefe(Point Position)
	{
		boolean Wert = false;
		int Laufwert = 0;
		while (Wert == false)
		{
			if (Punktmengenvektor[Laufwert].contains(Position) == true)
			{
				int Löschmich = Punktmengenvektor[Laufwert].indexOf(Position);
				Punktmengenvektor[Laufwert].removeElementAt(Löschmich);
				// TODO for(){es muessen noch mehr Punkte entfernt werden} #
				Rueckgabewert = true;
				Wert = true;
			}
			else
				Laufwert += 1;
			if (Laufwert == 6)
			{
				Rueckgabewert = false;
				Wert = true;
			}
		} // Ende while Schleife
		return Rueckgabewert;
	}

	public Vector[] get_Burgvektor()
	{
		return Punktmengenvektor;
	}

} //Ende Klasse Burg