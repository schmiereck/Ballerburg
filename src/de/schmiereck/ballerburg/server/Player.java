package de.schmiereck.ballerburg.server;

/**
 * <p>
 * 	TODO	docu
 * </p> 
 * 
 * @author smk
 * @version <p>30.08.2003:	created</p>
 */
public class Player
{
	private Integer playerNumber;

	/**
	 * 	Constructor.
	 * 
	 * 
	 */
	public Player(Integer playerNumber)
	{
		super();
		
		this.playerNumber = playerNumber;
	}

	Object getName()
	{
		return this.playerNumber;
	}
}
