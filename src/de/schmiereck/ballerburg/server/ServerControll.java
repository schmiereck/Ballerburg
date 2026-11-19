package de.schmiereck.ballerburg.server;

import java.awt.Point;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

import de.schmiereck.ballerburg.shared.Cannon;
import de.schmiereck.ballerburg.shared.Part;
import de.schmiereck.ballerburg.shared.Rock;

/**
 * <p>
 * 	TODO	docu
 * </p> 
 * 
 * @author smk
 * @version <p>30.08.2003:	created</p>
 */
public class ServerControll
{
	private HashMap players = new HashMap();
	
	private HashMap	games = new HashMap();


	private int lastPartNo = 0;

	/**
	 * 
	 */
	private String generateNextPartName()
	{
		this.lastPartNo++;
		
		return Integer.toString(this.lastPartNo);
	}

	Integer retrieveNewPlayerNumber()
	{
		int no = 1;
		
		Iterator playersIterator = this.players.keySet().iterator();
		
		while (playersIterator.hasNext())
		{
			Integer playerNumber = (Integer)playersIterator.next();
			
			if (playerNumber.intValue() >= no)
			{
				no = playerNumber.intValue() + 1;
			}
		}
		
		Integer playerNumber = new Integer(no); 
		
		this.players.put(playerNumber, new Player(playerNumber));
		
		return playerNumber; 
	}

	void killPlayerNumber(Integer playerNumber)
	{
		if (this.checkPlayerNumberExist(playerNumber) == false)
		{
			this.players.remove(playerNumber);
		}
	}

	/**
	 * @param playerNumber
	 * @return
	 */
	synchronized String initNewGamePlayer(String gameName, Integer playerNumber)
	{
		String ret = null;
		
		ServerGameData gameData = (ServerGameData)this.games.get(gameName);
		
		if (gameData != null)
		{
			Player player = (Player)this.players.get(playerNumber);
		
			if (player != null)
			{
				gameData.addPlayer(player);
				
				ret = "ok";
			}
		}
		
		return ret;
	}

	/**
	 * @param gameName
	 * @param playerNumber
	 * @return
	 */
	public String removeGamePlayer(String gameName, Integer playerNumber)
	{
		String ret = null;
		
		ServerGameData gameData = (ServerGameData)this.games.get(gameName);
		
		if (gameData != null)
		{
			//Player player = (Player)this.players.get(playerNumber);

			gameData.removePlayer(playerNumber);
		}

		if (this.checkPlayerNumberExist(playerNumber) == false)
		{
			this.players.remove(playerNumber);
		}
		
		// Keine Spieler mehr im Spiel ?
		if (gameData.getPlayersIterator().hasNext() == false)
		{
			this.games.remove(gameData.getName());
		}

		return ret;
	}

	private boolean checkPlayerNumberExist(Integer playerNumber)
	{
		boolean foundPlayer = false;
		
		Iterator gamesIterator = this.games.values().iterator();
		
		while (gamesIterator.hasNext())
		{
			ServerGameData gameData = (ServerGameData)gamesIterator.next();

			if (gameData.checkPlayerNumberExist(playerNumber))
			{
				foundPlayer = true;
				break;
			}
		}
		return foundPlayer;
	}

	/**
	 * @return
	 */
	public String listOpenGameNames()
	{
		StringBuffer ret = new StringBuffer();
		
		Iterator gamesIterator = this.games.values().iterator();
		
		while (gamesIterator.hasNext())
		{
			ServerGameData gameData = (ServerGameData)gamesIterator.next();

			if (gameData.getGameStarted() == false)
			{
				if (ret.length() > 0)
				{
					ret.append(":");
				}
				ret.append(gameData.getName());
			}
		}

		return ret.toString();
	}

	/**
	 * @param gameName
	 * @return
	 */
	public String listGamePlayerNames(String gameName)
	{
		StringBuffer ret = new StringBuffer();

		ServerGameData gameData = (ServerGameData)this.games.get(gameName);
		
		if (gameData != null)
		{
			Iterator playersIterator = gameData.getPlayersIterator();
		
			while (playersIterator.hasNext())
			{
				Player player = (Player)playersIterator.next();

				if (ret.length() > 0)
				{
					ret.append(":");
				}
				ret.append(player.getName().toString());
			}
		}

		return ret.toString();
	}

	/**
	 * @param gameName
	 * @return
	 */
	public String startGame(String gameName)
	{
		ServerGameData gameData = (ServerGameData)this.games.get(gameName);
		
		if (gameData.getGameStarted() == false)
		{
			Player startPlayer = gameData.getStartPlayer();
			
			gameData.setActualPlayerNumber(startPlayer.getName());

			this.initGameWorld(gameData);

			gameData.setGameStarted(true);
		}
		
		return "ok";
	}

	public String addGame(String gameName)
	{
		String ret = null; 
		ServerGameData	gameData = new ServerGameData(gameName);
		
		if (this.games.containsKey(gameName) == false)
		{
			this.games.put(gameName, gameData);
			ret = "ok";
		}
		return ret;
	}

	/**
	 * @param gameName
	 * @param playerNumber
	 * @return
	 */
	public String getWorldData(String gameName, Integer playerNumber)
	{
		StringBuffer worldStr = new StringBuffer();

		ServerGameData gameData = (ServerGameData)this.games.get(gameName);

		Object actualPlayerNumber = gameData.getActualPlayerNumber();

		worldStr.append("actualPlayerNumber:" + actualPlayerNumber);

		Iterator partsIterator = gameData.getPartsIterator();
		
		while (partsIterator.hasNext())
		{
			Part part = (Part)partsIterator.next();

			if (worldStr.length() > 0)
			{
				worldStr.append("&");
			}
			
			worldStr.append(part.serialize());
		}

		return worldStr.toString();
	}

	/**
	 * @param gameName
	 * @return
	 */
	public String checkGameStarted(String gameName)
	{
		String ret = null;
		
		ServerGameData gameData = (ServerGameData)this.games.get(gameName);
		
		if (gameData != null)
		{
			if (gameData.getGameStarted())
			{
				ret = "ok";
			}
		}
		
		return ret;
	}

	/**
	 * @param gameName
	 * @param playerNumber
	 * @return
	 */
	public String startBum(String gameName, Integer playerNumber, String cannonPartName, Integer angel, Integer blowPowder)
	{
		ServerGameData gameData = (ServerGameData)this.games.get(gameName);
		
		Cannon cannon = (Cannon)gameData.getPart(cannonPartName);
		
		cannon.setAngel(angel.intValue());
		cannon.setBlowPowder(blowPowder.intValue());
		
		gameData.setLastBlowCannon(cannon);

		Object nextPlayerNumber = gameData.makeNextPlayerNumber(playerNumber);
		
		gameData.setActualPlayerNumber(nextPlayerNumber);
		
		//nextPlayerNumber.toString();

		return "ok";
	}

	/**
	 * @param gameName
	 * @param playerNumber
	 * @return "<isGameOver>:<actualPlayerNumber>"
	 */
	public String checkGameState(String gameName, Integer playerNumber)
	{
		String ret = null;
		
		ServerGameData gameData = (ServerGameData)this.games.get(gameName);

		if (gameData != null)
		{
			if (gameData.getActualPlayerNumber() == null)
			{
				ret = "1";
			}
			else
			{
				ret = "0";
			}

			ret += ":" + gameData.getActualPlayerNumber();
		}
		
		return ret;
	}

	/**
	 * TODO auftrenne, letzten Schuss vom nächten spieler / gameOver Abfrage trennen. 
	 * 
	 * @param gameName
	 * @param playerNumber
	 * @return
	 */
	public String checkBum(String gameName, Integer playerNumber)
	{
		String ret = null;
		
		ServerGameData gameData = (ServerGameData)this.games.get(gameName);

		if (gameData != null)
		{
			Cannon cannon = gameData.getLastBlowCannon();

			if (cannon != null)
			{
				// Nicht der Spieler, der den Schuss selber abgefeuert hat ?
				if (!playerNumber.equals(cannon.getPlayerNumber()))
				{
					ret = // gameData.getActualPlayerNumber() + ":" +
							cannon.getName() + ":" + 
							cannon.getAngel() + ":" + 
							cannon.getBlowPowder();
				}
			}
		}
		
		return ret;
	}

	/**
	 * @param gameName
	 * @param partName
	 * @return
	 */
	public String endBum(String gameName, String partName)
	{
		String ret = null;
		
		ServerGameData gameData = (ServerGameData)this.games.get(gameName);

		if (gameData != null)
		{
			gameData.removePart(partName);

			Object actualPlayerNumber = gameData.getActualPlayerNumber();

			// Noch mal prüfen, ob der Spieler, der als aktuller eingetragen ist,
			// jetzt überhaupt noch Kanonen hat:

			Object nextPlayerNumber = actualPlayerNumber;
			
			boolean nextPlayerHaveCannons = false;
			
			while (true)
			{
				boolean haveCannons = gameData.checkHaveCannons(nextPlayerNumber);

				// Spieler hat Kanonen ?
				if (haveCannons == true)
				{
					nextPlayerHaveCannons = true;
					break;
				}

				// Dann muss der nächste Spieler ran:

				nextPlayerNumber = gameData.makeNextPlayerNumber(nextPlayerNumber);

				gameData.setActualPlayerNumber(nextPlayerNumber);

				// Wieder am Anfang angekommen ?
				if (nextPlayerNumber.equals(actualPlayerNumber))
				{
					// Suche erfolglos beenden.
					break;
				}
			}
			
			// Keiner hat mehr Kanonen ?
			if (nextPlayerHaveCannons == false)
			{
				// Spiel als beendet merkieren.
				gameData.setActualPlayerNumber(null);
			}
		}
		
		return ret;
	}
	
	private Random rnd = new Random(); 

	/**
	 * @param gameData
	 */
	private void initGameWorld(ServerGameData gameData)
	{
		int width = gameData.getGameSizeX() - 40;
		int wh = width / 4;
		this.initRocks(gameData, wh, wh);
		this.initRocks(gameData, width - wh, wh);
		
		Iterator playersIterator = gameData.getPlayersIterator();
		
		boolean calcFromRight = true;
		
		while (playersIterator.hasNext())
		{
			Player player = (Player)playersIterator.next();

			Point p1 = gameData.calcFreeCannonPoint(calcFromRight);

			// Noch ein Plätzchen gefunden ?
			if (p1 != null)
			{
				int angel;
					
				if (calcFromRight == true)
				{
					angel = 135;
				}
				else
				{
					angel = 45;
				}
					
				Cannon cannon1 = new Cannon(this.generateNextPartName(), p1.x, p1.y, angel, player.getName());
		
				gameData.addPart(cannon1);

				Point p2 = gameData.calcFreeCannonPoint(calcFromRight);
			
				// Noch ein Plätzchen gefunden ?
				if (p2 != null)
				{
					Cannon cannon2 = new Cannon(this.generateNextPartName(), p2.x, p2.y, angel, player.getName());
		
					gameData.addPart(cannon2);
				}
			}
			
			if (calcFromRight == true)
			{
				calcFromRight = false;
			}
			else
			{
				calcFromRight = true;
			}
		}
	}

	private void initRocks(ServerGameData gameData, int startX, int widthX)
	{
		double max = 30.0;
		
		for (int i = (int)max; i > 1; i--)
		{
			double weight = i / max;
			
			int g = (int)(((rnd.nextDouble() * (widthX * 2)) - widthX) * weight);
			
			if (g < -widthX)
			{
				g = -widthX;
			}
			else
			{
				if (g > widthX)
				{
					g = widthX;
				}
			}
			
			int x = startX + g; 
			
			int w = rnd.nextInt(20) + (20 + i / 2);
			int h = rnd.nextInt(20) + 10;
			
			int y = gameData.calcFreePartY(x, w);
			
			Rock rock = new Rock(this.generateNextPartName(), x, y, w, h);
			
			gameData.addPart(rock);
		}
	}
	
}
