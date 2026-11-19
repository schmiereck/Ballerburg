package de.schmiereck.ballerburg.shared;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

/**
 * <p>
 * 	TODO	docu
 * </p> 
 * 
 * @author smk
 * @version <p>30.08.2003:	created</p>
 */
public class GameData
{
	private String name;

	private HashMap parts = new HashMap();

	/**
	 * 	Constructor.
	 * 
	 * @param name
	 */
	public GameData(String name)
	{
		this.name = name;
	}

	public void addPart(Part part)
	{
		this.parts.put(part.getName(), part);
	}

	/**
	 * @param bullet
	 */
	public void removePart(Part part)
	{
		this.parts.remove(part.getName());
	}

	/**
	 * @param partName
	 */
	public void removePart(String partName)
	{
		this.parts.remove(partName);
	}

	public Part getPart(String partName)
	{
		return (Part)this.parts.get(partName);
	}

	public Iterator getPartsIterator()
	{
		return this.parts.values().iterator();
	}

	public int getGameSizeX()
	{
		return 640;
	}

	public int getGameSizeY()
	{
		return 400;
	}

	/**
		 * @return
		 */
	public String getName()
	{
		// TODO Auto-generated method stub
		return this.name;
	}
}
