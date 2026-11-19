/*
 * (c) Sahara 1999
 *
 * Diese Klasse ist das Verbindungsstück zwischen dem eigentlichen Hauptprogramm
 * (Client) und dem Server. Connection stellt die Verbindung her und übergibt
 * die jeweiligen Nachrichten vom Ballerapplet zum Server und umgekehrt.
 *
 */

package sara;
import java.net.*;
import java.io.*;

class Verbindung extends Thread
{
	protected Socket client;
	protected DataInputStream in;
	protected PrintStream out;
	protected Ballerserver server;

	public Verbindung(Ballerserver server, Socket client)
	{
		this.server=server;
		this.client=client;

		try
		{

//Erzeugen eines neuen DataInput-Output-Streams für die Ein/Ausgabe der Usertexte
			in = new DataInputStream(client.getInputStream());
			out = new PrintStream(client.getOutputStream());

//Handling für Ausnahmefälle und Ausgabe einer Fehlermeldung
		} catch (IOException e)
		{
			try { client.close(); } catch (IOException e2) {} ;
			System.err.println("Fehler beim Erzeugen der Streams: " + e);
			return;
		}

		this.start();
	}


	public void run()
	{
		String line;

		try
		{
//Kein´Programmbedingter Abbruch, deshalb Endlosschleife
			while(true)
			{
//Einlesen der Texte
				line=in.readLine();
//Schleife: Wenn vom User Eingabe erfolgt starte server.broadcast 
				if(line!=null)
					server.broadcast(line);
			}
// Handling für Ausnahmefälle und Ausgabe einer Fehlermeldung	  
	  } catch (IOException e)
		{
			System.out.println("Fehler:" + e);
		}
	}
}
