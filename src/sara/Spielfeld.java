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
import java.awt.*;

public class Spielfeld extends Canvas implements Runnable
{

	public Spielbereich[] spielbereich;
	private Berg berg;
	private Point Maße; //Größe des Spielfeldes
	private Point aktuelle_Pos;
	public Fahne fahne;
	public Ballerburg ballerburg;
	private Kugel kugel;
	public boolean Getroffen, Spielende;
	// synchronized ???
	public boolean Kugelunterwegs;
	private Thread thread;

	public Spielfeld(int Breite, int Höhe, Ballerburg ballerburg)
	{
		this.ballerburg = ballerburg;
		setSize(Breite, Höhe);
		setBackground(Color.gray);
		setForeground(Color.pink);
		Maße = new Point(getSize().width, getSize().height);
		spielbereich = new Spielbereich[2];
		spielbereich[0] = new Spielbereich(this, 0); // 0 = linkes Spielfeld
		spielbereich[1] = new Spielbereich(this, 1); // 1 = rechtes Spielfeld
		berg = new Berg(this, new Point(110, 100), new Point(190, 200));
		fahne = new Fahne();
		Kugelunterwegs = false;
		thread = new Thread();
	} // Ende Konstruktor Spielfeld

	/**
	* - Kanone aufrufen
	* - Kugel einbetten
	* - Flugbahn starten
	*/

	public Kugel starten(Spieler spieler)
	{
		return spieler.schiessen();
	}

	public void set_Kugel(Kugel kugel)
	{
		this.kugel = kugel;
		Kugelunterwegs = true;
		thread.start();
	}

	/**
	* run: holt aktuelle Position,
	*      vergleicht Pos. ob ein Gegenstand getroffen,oder thron abgeschossen
	*
	*/

	public void run()
	{
		kugel.set_Wind(fahne.get_Windgeschwindigkeit(), fahne.get_Windrichtung());
		while (Kugelunterwegs)
		{
			aktuelle_Pos = kugel.get_flugbahn();
			Getroffen = getroffen(aktuelle_Pos);
			Spielende = abgeschossen(aktuelle_Pos);
			repaint(); // Doppelpuffer noch nicht eingebaut!!
			try
			{
				thread.sleep(500);
			}
			catch (InterruptedException e)
			{
			}
			if (Spielende || Getroffen) // Richtig????
				Kugelunterwegs = false;
		}
		thread.stop();
	}

	public Point get_Maße()
	{
		return Maße;
	} // Ende Methode get_Maße

	public boolean getroffen(Point Position)
	{
		// könnte mit logischen Oder verknuepft werden!!!
		if (berg.ueberpruefe(Position))
			return true;
		if (spielbereich[0].getroffen(Position))
			return true;
		if (spielbereich[1].getroffen(Position))
			return true;
		else
			return false;
	} // Ende Methode getroffen

	public boolean abgeschossen(Point Position)
	{
		if (spielbereich[0].voelligAbgeschossen(Position))
			return true;
		if (spielbereich[1].voelligAbgeschossen(Position))
			return true;
		else
			return false;
	}

	//public void update(){}

	public void paint(Graphics g)
	{ // Vielleicht laenge x,y,0,0 statt x,y,1,1
		for (int i = 0; i < berg.get_Bergvektor().size(); i++)
		{
			g.fillRect(((Point)berg.get_Bergvektor().elementAt(i)).x, ((Point)berg.get_Bergvektor().elementAt(i)).y, 1, 1);
		}
		for (int i = 0; i < spielbereich[0].thron.get_Thronvektor().size(); i++)
		{
			g.fillRect(((Point)spielbereich[0].thron.get_Thronvektor().elementAt(i)).x, ((Point)spielbereich[0].thron.get_Thronvektor().elementAt(i)).y, 1, 1);
		}
		for (int i = 0; i < spielbereich[1].thron.get_Thronvektor().size(); i++)
		{
			g.fillRect(((Point)spielbereich[1].thron.get_Thronvektor().elementAt(i)).x, ((Point)spielbereich[1].thron.get_Thronvektor().elementAt(i)).y, 1, 1);
		}
		for (int i = 0; i < spielbereich[0].kanone.get_Kanonenvektor().size(); i++)
		{
			g.fillRect(
				((Point)spielbereich[0].kanone.get_Kanonenvektor().elementAt(i)).x,
				((Point)spielbereich[0].kanone.get_Kanonenvektor().elementAt(i)).y,
				1,
				1);
		}
		for (int i = 0; i < spielbereich[1].kanone.get_Kanonenvektor().size(); i++)
		{
			g.fillRect(
				((Point)spielbereich[1].kanone.get_Kanonenvektor().elementAt(i)).x,
				((Point)spielbereich[1].kanone.get_Kanonenvektor().elementAt(i)).y,
				1,
				1);
		}
		for (int j = 0; j <= 5; j++)
		{
			for (int i = 0; i < spielbereich[0].burg.get_Burgvektor()[j].size(); i++)
			{
				g.fillRect(
					((Point)spielbereich[0].burg.get_Burgvektor()[j].elementAt(i)).x,
					((Point)spielbereich[0].burg.get_Burgvektor()[j].elementAt(i)).y,
					1,
					1);
			}
			for (int i = 0; i < spielbereich[1].burg.get_Burgvektor()[j].size(); i++)
			{
				g.fillRect(
					((Point)spielbereich[1].burg.get_Burgvektor()[j].elementAt(i)).x,
					((Point)spielbereich[1].burg.get_Burgvektor()[j].elementAt(i)).y,
					1,
					1);
			}
		}
		// TODO überprüfung eingebaut !!!
		if (aktuelle_Pos != null)
		{
			g.fillOval(aktuelle_Pos.x, aktuelle_Pos.y, 2, 2);
		}

	} // Ende Methode paint

} // Ende class Spielfeld