package de.schmiereck.ballerburg.shared;

/**
 * <p>
 * 	TODO	docu
 * </p> 
 * 
 * @author smk
 * @version <p>30.08.2003:	created</p>
 */
public class Rock extends Part
{

	/**
	 * 	Constructor.
	 * 
	 * @param name
	 * @param posX
	 * @param posY
	 * @param sizeX
	 * @param sizeY
	 */
	public Rock(String name, int posX, int posY, int sizeX, int sizeY)
	{
		super(name, posX, posY, sizeX, sizeY);
	}

	public String serialize()
	{
		return "rock:" + super.serialize();
	}

}
