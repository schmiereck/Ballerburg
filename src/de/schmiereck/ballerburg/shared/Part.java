package de.schmiereck.ballerburg.shared;

/**
 * <p>
 * 	TODO	docu
 * </p> 
 * 
 * @author smk
 * @version <p>30.08.2003:	created</p>
 */
public class Part
{
	private String name;
	private int	posX;
	private int	posY;
	private int	offX;
	private int	offY;
	private int	sizeX;
	private int	sizeY;
	private double	moveX = 0.0;
	private double	moveY = 0.0;

	public Part(String name, int posX, int posY, int sizeX, int sizeY)
	{
		this.name	= name;
		this.posX	= posX;
		this.posY	= posY;
		this.offX	= 0;
		this.offY	= 0;
		this.sizeX	= sizeX;
		this.sizeY	= sizeY;
	}

	public Part(String name, int posX, int posY, int offX, int offY, int sizeX, int sizeY)
	{
		this.name	= name;
		this.posX	= posX;
		this.posY	= posY;
		this.offX	= offX;
		this.offY	= offY;
		this.sizeX	= sizeX;
		this.sizeY	= sizeY;
	}

	/**
	 * @return
	 */
	public int getPosX()
	{
		return this.posX + (int)this.moveX;
	}

	/**
	 * @return
	 */
	public int getPosY()
	{
		return this.posY + (int)this.moveY;
	}

	public int getOffX()
	{
		return this.offX;
	}

	public int getOffY()
	{
		return this.offY;
	}

	/**
	 * @return
	 */
	public int getSizeX()
	{
		return this.sizeX;
	}

	/**
	 * @return
	 */
	public int getSizeY()
	{
		return this.sizeY;
	}

	/**
	 * @param sizeX
	 */
	public void setSizeX(int sizeX)
	{
		this.sizeX = sizeX;
	}

	/**
	 * @paramsizeYi
	 */
	public void setSizeY(int sizeY)
	{
		this.sizeY = sizeY;
	}

	public String serialize()
	{
		return 	this.name + ":" +
				this.posX + ":" +
				this.posY + ":" +
				this.sizeX + ":" +
				this.sizeY;
	}

	/**
	 * @param mx
	 */
	public void movePosX(double mx)
	{
		this.moveX += mx;
	}

	/**
	 * @param my
	 */
	public void movePosY(double my)
	{
		this.moveY += my;
	}
	
	public String getName()
	{
		return this.name;
	}

	public boolean checkHit(int bx, int by)
	{
		boolean ret;
					
		if ((bx > this.getPosX() + this.offX) &&
			(by > this.getPosY() + this.offY) &&
			(bx < this.getPosX() + this.offX + this.getSizeX()) &&
			(by < this.getPosY() + this.offY + this.getSizeY()))
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
