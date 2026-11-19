/** 
 * Ballerburg.java 
 * 
 * 
 * Created: 1999 
 * 
 * @author Sahara 
 * @version 1.0  BETA 
**/

package sara;
import java.applet.*;
import java.awt.*;

/** 
* Ballerburg erbt von Applet, d.h. es ist eines. 
* Hier ist es die ausführbare Hauptklasse. 
* 
* Allgemein ist ein Applet eine Unterklasse von Panel, die zum 
* Erstellen eines Programms verwendet wird, das in eine HTML-Seite 
* eingebettet werden und in einem HTML-Browser oder Applet_Viewer 
* laufen soll. Da Applet eine Unterklasse von Panel ist, kann es 
* Komponenten enthalten, hat aber keinen Rand oder Titel. 
* 
* Ballerburg ist für den Spielablauf die zentrale Steuereinheit. 
* 
* 
* Wir ueberschreiben in unserem Applet die Methoden init(), start() & 
* stop(). 
* 
**/

public class Ballerburg extends Applet implements Runnable
{

	private Spieler[] spieler;
	private Spielfeld spielfeld;
	private boolean no_kugel;
	private Thread thread;
	private Endefenster endefenster;
	private Kugel kugel;

	public void init()
	{
		setBackground(Color.red);
		setForeground(Color.blue);
		spielfeld = new Spielfeld(300, 200, this);
		spieler = new Spieler[2];
		spieler[0] = new Spieler("Testperson1", 1234, spielfeld.spielbereich[0]);
		spieler[1] = new Spieler("Testperson2", 5678, spielfeld.spielbereich[1]);
		add(spielfeld);
		thread = null;
		endefenster = new Endefenster(new Frame(), "Spielende", true);
	}

	public void start()
	{
		if (thread == null)
		{
			thread = new Thread(this);
			thread.start();
		}
	}

	public void stop()
	{
		if (thread != null)
		{
			thread.stop();
			thread = null;
		}
	}

	/** 
	* Hauptschleife 
	**/

	/** 
	* Thread 
	* Ballerburg muss als Thread ausgeführt werden, da ständig Prozesse laufen. 
	* So belegt Ballerburg nicht die gesamten Systemresourcen. Mehrere Prozesse 
	* bzw. mehrere Thread können nun gleichzeitig laufen. 
	* Hauptmethode: run() 
	**/

	public void run()
	{
		boolean no_kugel = true;
		while (no_kugel)
		{
			/** 
			*Exception: 
			*Mit der Methode starten() von spielfeld wird ein Kugelobjekt zurück erwartet. 
			*Wenn kein Kugelobjekt zurückgegeben wird erzeugt Java eine 
			*NullPointerException. Damit das Programm nicht beendet wird, fangen wir 
			*diese Exception mit der try-cathch - Methode ab. 
			*(es wird so lange gewartet bis die kugel erzeugt wird) 
			**/

			try
			{
				//Spieler1 schießt zuerst 
				kugel = spielfeld.starten(spieler[1]);
				no_kugel = false;
			}
			catch (NullPointerException e)
			{
			}
		}
		spielfeld.set_Kugel(kugel);
		kugel = null;
		//warten bis Schuß zu ende 
		while (spielfeld.Kugelunterwegs)
		{
			try
			{
				thread.sleep(500);
			}
			catch (InterruptedException e)
			{
			}
		}
		// Schußauswertung 
		if (spielfeld.Spielende)
			endefenster.show();
		while (endefenster.isVisible())
		{
			try
			{
				thread.sleep(500);
			}
			catch (InterruptedException e)
			{
			}
		}
		// Spieler2 schießt 
		no_kugel = true;
		while (no_kugel)
		{
			try
			{
				kugel = spielfeld.starten(spieler[2]);
				no_kugel = false;
			}	// TODO Einegfügt !!!
			catch (NullPointerException e)
			{
			}
		}	// TODO Einegfügt !!!
		spielfeld.set_Kugel(kugel);
		//warten bis Schuß zu ende 
		while (spielfeld.Kugelunterwegs)
		{
			try
			{
				thread.sleep(500);
			}
			catch (InterruptedException e)
			{
			}
		}
		// Schußauswertung 
		if (spielfeld.Spielende)
			endefenster.show();
		while (endefenster.isVisible())
		{
			try
			{
				thread.sleep(500);
			}
			catch (InterruptedException e)
			{
			}
		}

	} // Ende void run 

} // Ende Klasse Ballerburg /** 
