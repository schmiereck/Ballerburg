package de.schmiereck.ballerburg.shared;

/**
 * <p>
 * 	TODO	docu
 * </p> 
 * 
 * @author smk
 * @version <p>30.08.2003:	created</p>
 */
public class Cannon
extends Part
{
	private Object playerNumber;
	
	private boolean isMyOwn = false;
	
	private int angel;
	private int blowPowder = 15;
	
	/**
	 * 	Constructor.
	 * 
	 * @param i
	 * @param j
	 */
	public Cannon(String name, int posX, int posY, int angel, Object playerNumber)
	{
		super(name, posX, posY, -16, 0, 32, 18);
		
		this.angel = angel;
		this.playerNumber = playerNumber;
	}

	public String serialize()
	{
		return "cannon:" + super.serialize() + ":" + this.angel + ":" + this.getPlayerNumber();
	}

	/**
	 * @return
	 */
	public Object getPlayerNumber()
	{
		return this.playerNumber;
	}

	/**
	 * @return
	 */
	public boolean getIsMyOwn()
	{
		return this.isMyOwn;
	}

	/**
	 * @param isMyOwn
	 */
	public void setIsMyOwn(boolean isMyOwn)
	{
		this.isMyOwn = isMyOwn;
	}

	/**
	 * @return
	 */
	public int getAngel()
	{
		return this.angel;
	}

	/**
	 * @param angel
	 */
	public void setAngel(int angel)
	{
		this.angel = angel;
	}

	/**
	 * @return
	 */
	public int getBlowPowder()
	{
		return this.blowPowder;
	}

	/**
	 * @param blowPowder
	 */
	public void setBlowPowder(int blowPowder)
	{
		this.blowPowder = blowPowder;
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.ballerburg.shared.Part#checkHit(int, int)
	 */
	public boolean checkHit(int bx, int by)
	{
		boolean ret;

		int dx = bx - (this.getPosX());
		int dy = by - (this.getPosY());

		int s = (int)Math.sqrt(dx * dx + dy * dy);

		if (s < 16)
		{
			ret = true;
		}
		else
		{
			ret = false;
		}
		return ret;
	}

}
