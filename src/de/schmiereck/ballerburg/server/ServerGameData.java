package de.schmiereck.ballerburg.server;

import java.awt.Point;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

import de.schmiereck.ballerburg.shared.Cannon;
import de.schmiereck.ballerburg.shared.GameData;
import de.schmiereck.ballerburg.shared.Part;

/**
 * <p>
 * 	TODO	docu
 * </p> 
 * 
 * @author smk
 * @version <p>30.08.2003:	created</p>
 */
public class ServerGameData
extends GameData
{
	private Cannon lastBlowCannon = null;

	/**
	 * Wenn null und {@see #gameStarted} true ist, dann ist das Spiel beendet,
	 * Da weniger als zwei Spieler noch Kanonen haben.
	 */
	private Object actualPlayerNumber = null;

	private boolean gameStarted = false;

	private HashMap players = new HashMap();

	/**
	 * 	Constructor.
	 * 
	 * @param name
	 */
	public ServerGameData(String name)
	{
		super(name);
	}

	private Random rnd = new Random(); 

	/**
	 * @return
	 */
	public Point calcFreeCannonPoint(boolean calcFromRight)
	{
		Point ret = null;
		
		if (calcFromRight == true)
		{
			for (int x = this.getGameSizeX() - 50; x > 50; x -= (40 + rnd.nextInt(10)))
			{
				if (this.checkFreeCannonPoint(x) == true)
				{
					int y = this.calcFreePartY(x - 15, 30);
					ret = new Point(x, y);
					break;
				}
			}		
		}
		else
		{
			for (int x = 50; x < this.getGameSizeX() - 50; x += (40 + rnd.nextInt(10)))
			{
				if (this.checkFreeCannonPoint(x) == true)
				{
					int y = this.calcFreePartY(x - 15, 30);
					ret = new Point(x, y);
					break;
				}
			}		
		}
		
		return ret;
	}
	
	private boolean checkFreeCannonPoint(int x)
	{
		boolean ret = true;
		
		Iterator partsIterator = this.getPartsIterator();
		
		while (partsIterator.hasNext())
		{
			Part part = (Part)partsIterator.next();
			
			if (part instanceof Cannon)
			{
				// Zu nahe an anderer Kannone ?
				if ((part.getPosX() > (x - 60)) &&
					(part.getPosX() < (x + 60)))
				{
					ret = false;
					break;
				}
			}
		}
		
		return ret;
	}

	void addPlayer(Player player)
	{
		this.players.put(player.getName(), player);
	}

	Iterator getPlayersIterator()
	{
		return this.players.values().iterator();
	}

	/**
	 * @param playerNumber
	 */
	void removePlayer(Object playerNumber)
	{
		this.players.remove(playerNumber);
	}

	/**
	 * @param b
	 */
	public void setGameStarted(boolean gameStarted)
	{
		this.gameStarted = gameStarted;		
	}

	public boolean getGameStarted()
	{
		return this.gameStarted;		
	}

	/**
	 * @param playerNumber
	 * @return
	 */
	public boolean checkPlayerNumberExist(Integer playerNumber)
	{
		return this.players.containsKey(playerNumber);
	}

	/**
	 * @return
	 */
	public Player getStartPlayer()
	{
		Player firstPlayer = (Player)this.players.values().iterator().next();
		return firstPlayer;
	}

	/**
	 * @param startPlayer
	 */
	public void setActualPlayerNumber(Object actualPlayerNumber)
	{
		this.actualPlayerNumber = actualPlayerNumber;		
	}

	public Object getActualPlayerNumber()
	{
		return this.actualPlayerNumber;		
	}

	/**
	 * @param cannon
	 */
	public void setLastBlowCannon(Cannon lastBlowCannon)
	{
		this.lastBlowCannon = lastBlowCannon;		
	}

	public Cannon getLastBlowCannon()
	{
		return this.lastBlowCannon;		
	}

	/**
	 * @param playerNumber
	 * @return
	 */
	public Integer makeNextPlayerNumber(Object playerNumber)
	{
		Integer nextPlayerNumber = null;
		Iterator playersIterator = this.players.values().iterator();
		
		boolean found = false;
		
		while (playersIterator.hasNext())
		{
			Player player = (Player)playersIterator.next();
			
			if (found == true)
			{
				nextPlayerNumber = (Integer)player.getName();
				break;
			}
			
			if (playerNumber.equals(player.getName()))
			{
				found = true;
			}
		}
		
		// letzen in Liste erwischt ?
		if ((found == true) && (nextPlayerNumber == null))
		{
			nextPlayerNumber = (Integer)this.getStartPlayer().getName();
		}
				
		return nextPlayerNumber;
	}

	/**
	 * @param x
	 * @return
	 */
	public int calcFreePartY(int x, int sizeX)
	{
		int retY = 0;
		
		Iterator partsIterator = this.getPartsIterator();
		
		while (partsIterator.hasNext())
		{
			Part part = (Part)partsIterator.next();
			
			// Überschneidung ?
			if ((
				 (x > part.getPosX()) &&
				 (x < (part.getPosX() + part.getSizeX()))
				) ||
				(
				 ((x + sizeX) > part.getPosX()) &&
				 ((x + sizeX) < (part.getPosX() + part.getSizeX()))
				))
			{
				// Dann auf den Part draufsetzen.
				int py = part.getPosY() + part.getSizeY();
				
				if (py > retY)
				{
					retY = py;
				}
			}
		}
		
		return retY;
	}

	/**
	 * @param object
	 * @return
	 */
	public boolean checkHaveCannons(Object playerNumber)
	{
		boolean haveCannons = false;
		
		Iterator partsIterator = this.getPartsIterator();
		
		while (partsIterator.hasNext())
		{
			Part part = (Part)partsIterator.next();
			
			if (part instanceof Cannon)
			{
				Cannon cannon = (Cannon)part;
				
				if (playerNumber.equals(cannon.getPlayerNumber()))
				{
					haveCannons = true;
					break;
				}
			}
		}
		
		return haveCannons;
	}
}
