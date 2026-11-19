package de.schmiereck.ballerburg.client.controls;

/**
 * <p>
 * 	TODO	docu
 * </p> 
 * 
 * @author smk
 * @version <p>30.08.2003:	created</p>
 */
public class BaseControl
{

	private String name;

	private int posX;

	private int posY;

	private int paintedSizeY = 0;

	private int paintedSizeX = 0;

	public BaseControl(String name, int posX, int posY)
	{
		super();
		
		this.name	= name;
		this.posX	= posX; 
		this.posY	= posY;
	}

	/**
		 * @return
		 */
	public int getPosX()
	{
		return this.posX;
	}

	/**
		 * @return
		 */
	public int getPosY()
	{
		return this.posY;
	}

	public void setPaintedSize(int paintedSizeX, int paintedSizeY)
	{
		this.paintedSizeX = paintedSizeX;
		this.paintedSizeY = paintedSizeY;
	}

	/**
		 * @return
		 */
	public int getPaintedSizeX()
	{
		return this.paintedSizeX;
	}

	/**
		 * @return
		 */
	public int getPaintedSizeY()
	{
		return this.paintedSizeY;
	}

	/**
		 * @return
		 */
	public String getName()
	{
		return this.name;
	}

}
