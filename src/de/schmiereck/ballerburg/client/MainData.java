package de.schmiereck.ballerburg.client;

/**
 * <p>
 * 	TODO	docu
 * </p> 
 * 
 * @author smk
 * @version <p>30.08.2003:	created</p>
 */
public class MainData
{
	public static final int GSNew			= 0;
	public static final int GSGameSelect	= 1;
	public static final int GSGameOpen		= 2;
	public static final int GSGameAdd		= 3;
	public static final int GSGamePlay		= 4;
	public static final int GSScoreShow		= 5;

	private int	gameStatus	= GSNew;
	
	private ClientGameData gameData = null;
	/**
	 * @return
	 */
	public ClientGameData getGameData()
	{
		return this.gameData;
	}

	/**
	 * @param gameData
	 */
	public void setGameData(ClientGameData gameData)
	{
		this.gameData = gameData;
	}

	
	/**
	 * @return
	 */
	public int getGameStatus()
	{
		return this.gameStatus;
	}

	/**
	 * @param gameStatus
	 */
	public void setGameStatus(int gameStatus)
	{
		this.gameStatus = gameStatus;
	}
}
