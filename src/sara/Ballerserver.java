/*
 *(c) Sahara, 1999
 * Diese Klasse startet einen Server, der als Anwendung im Hintergrund läuft.
 * Dieser Server wird benötigt, um das Hauptprogramm netzwerkfähig zu machen.
 *
 */

package sara;
import java.net.*;
import java.io.*;
import java.util.*;

public class Ballerserver implements Runnable
{
	public static final int PORT = 4444;
	protected ServerSocket listen;
	protected Vector bestehndeVerbindungen;
	Thread connect;
	static int Spieler;
	static int Nummer;

	public Ballerserver()
	{
		try
		{
// Erstellen einer neuen Socket-Verbindung mit der Portanbindung 4444
// mit dem auf die Verbindung gewartet wird
			listen = new ServerSocket(PORT);

//Handling für Ausnahmefälle und Ausgabe einer Fehlermeldung
		} catch (IOException e)
		{
			System.err.println("Fehler beim Erzeugen der Sockets:"+e);
			System.exit(1);
		}

		bestehndeVerbindungen = new Vector();

//erzeugen und Starten des eigentlichen Threads um die Anfragen vom Programm
//über die Klasse "Verbindung" entgegenzunehmen und weiterzuleiten.
		connect = new Thread(this);
		connect.start();
	}

	public void run()
	{
		try
		{
//Der Server kehrt nie zurück, also eine Endlosschleife starten
			while(true)
			{

//Verwenden der accept()-Methode um den Port zu bedienen und um eine Verbindung aufbauen zu können.
				Socket client=listen.accept();

//Erstellen einer neuen Verbindung bei Anfragen
				Verbindung c = new Verbindung(this, client);
				bestehndeVerbindungen.addElement(c);
			}
//Handling für Ausnahmefälle und Ausgabe einer Fehlermeldung
		} catch (IOException e)
		{
			System.err.println("Fehler beim Warten auf Verbindungen:"+e);
			System.exit(1);
		}
	}


	public static void main(String[] args)
	{
		new Ballerserver();
	}

	public void broadcast(String msg)
	{
		int i;
		Verbindung you;


		for (i=0;i<bestehndeVerbindungen.size();i++)
		{
			
			you = (Verbindung) bestehndeVerbindungen.elementAt(i);

//Ausgabe der hereinkommenden Nachrichten auf dem Bildschirm
			you.out.println(msg);
			System.out.println("Message: "+msg);

		}
	}
}