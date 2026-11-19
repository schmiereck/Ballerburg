package de.schmiereck.ballerburg.client;

import java.util.Iterator;
import java.util.Vector;

import de.schmiereck.ballerburg.shared.*;

/**
 * <p>
 * 	TODO	docu
 * </p> 
 * 
 * @author smk
 * @version <p>30.08.2003:	created</p>
 */
public class ClientGameData
extends GameData
{
	private Integer	playerNumber = null;

	/**
	 * 	true, wenn der Spieler am Zug ist und einen Schuss auslösen
	 * 	soll. Wird zurückgesetzt, sobal ein Schuss ausgelöst wurde.
	 * 	Es wird dann aber sofort {@see #myBumIsRunning} true gesetzt.
	 */
	private boolean playThisRound = false;

	private Cannon selectedCannon = null;
	
	/**
	 * 	true, wenn Spieler selber den aktuellen Schuss ausgelöst hat
	 * 	und die Animation noch läuft.
	 * 	Wird verwendet, um zu entscheiden, ob der Server von diesem Client
	 * 	die Meldung über den Treffer bekommt.
	 */
	private boolean myBumIsRunning = false;

	/**
	 * 	Constructor.
	 * 
	 * @param name
	 */
	public ClientGameData(String name)
	{
		super(name);
	}

	/**
	 * @return
	 */
	public Integer getPlayerNumber()
	{
		return this.playerNumber;
	}

	/**
	 * @param playerNumber
	 */
	public void setPlayerNumber(int playerNumber)
	{
		this.playerNumber = new Integer(playerNumber);
	}

	/**
	 * @param playerNumber
	 */
	public void setPlayerNumber(Integer playerNumber)
	{
		this.playerNumber = playerNumber;
	}

	/**
	 * @return
	 */
	public boolean getPlayThisRound()
	{
		return this.playThisRound;
	}

	public void setPlayThisRound(boolean playThisRound)
	{
		this.playThisRound = playThisRound;
	}


	/**
	 * @return
	 */
	public Cannon getSelectedCannon()
	{
		return this.selectedCannon;
	}

	/**
	 * @param selectedCannon
	 */
	public void setSelectedCannon(Cannon selectedCannon)
	{
		this.selectedCannon = selectedCannon;
	}
	
	void setMyBumIsRunning(boolean myBumIsRunning)
	{
		this.myBumIsRunning = myBumIsRunning;
	}

	boolean getMyBumIsRunning()
	{
		return this.myBumIsRunning;
	}
}
