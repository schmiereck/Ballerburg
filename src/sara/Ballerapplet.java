/*
 * (c) Sahara, 1999
 *
 * Dieses Applet ist sozusagen der Client der Netzwerkverbindung.
 * Es verbindet sich über Connection mit dem Server.
 * Dieses Applet soll aber nicht eigenständig ausgeführt werden,
 * sondern vom Hauptprogamm mitausgeführt werden und im Eingabefenster
 * mitlaufen.
 *
 */

package sara;
import java.net.*;
import java.io.*;
import java.awt.*;
import java.applet.*;

public class Ballerapplet extends Applet implements Runnable
{
	public static final int PORT = 4444;
	Socket socket;
	DataInputStream in;
	PrintStream out;
	TextField inputfield;
	TextArea outputarea;
	Thread thread;

// Die Init-Methode legt im Wesentlichen die Graphischen Eigenschaften des
// Applets fest.(Größe,Farbe,Hintergrund etc.)
	public void init()
	{
		inputfield = new TextField();
		outputarea = new TextArea();
		outputarea.setFont( new Font("Dialog", Font.BOLD, 15));
		outputarea.setEditable(false);

		this.setLayout(new BorderLayout());
		this.add("South", inputfield);
		this.add("Center", outputarea);

		this.setBackground(Color.lightGray);
		this.setForeground(Color.black);
		inputfield.setBackground(Color.white);
		outputarea.setBackground(Color.white);
	}

//Die Start-Methode stellt die Verbindung zum Server her.
	public void start()
	{
		try
		{
// Erstellen einer Socket auf dem gleichen Port wie die des Servers			
			socket = new Socket(this.getCodeBase().getHost(), PORT);

// Ermöglichung der Ein/Ausgabe durch Erstellen neuer DataStreams
			in = new DataInputStream(socket.getInputStream());
			out = new PrintStream(socket.getOutputStream());

// Handling für Ausnahmefälle mit Ausgabe einer Fehlermeldung
		} catch (IOException e)
		{
			this.showStatus(e.toString());
			say("Verbindung zum Server fehlgeschlagen!");
			System.exit(1);
		}

// Ausgabe einer Meldung bei Hergestellter Verbindung
		say("Verbindung zum Server hergestellt ...");

		if (thread == null)
		{
			thread = new Thread(this);
			thread.setPriority(Thread.MIN_PRIORITY);
			thread.start();
		}
	}


// Die Stop-Methode des Haupt-Threads schließt die Socket-Verbindung
// und beendet die Threads
	public void stop()
	{
		try
		{
			socket.close();
		} catch (IOException e)
		{
			this.showStatus(e.toString());
		}

		if ((thread !=null) && thread.isAlive())
		{
			thread.stop();
			thread = null;
		}
	}


	public void run()
	{
		String line;

		try
		{

// Keinen Anwendungsbedingten Abbruch des Lesens vom Server deshalb Endlosschleife 
			while(true)
			{
				line = in.readLine();
				if(line!=null)

// Ausgabe der vom Server zurückgeschickten Texte
					outputarea.appendText(line+'\n' );
			}

// Fehlermeldung falls Server vorzeitig beendet
		} catch (IOException e) { say("Verbindung zum Server abgebrochen"); }
	}


	public boolean action(Event e, Object what)
	{
		if (e.target==inputfield)
		{
			String inp=(String) e.arg;

			out.println(inp);
			inputfield.setText("");
			return true;
		}

		return false;
	}


	public void say(String msg)
	{
		outputarea.appendText("*** "+msg+" ***\n");
	}
}
