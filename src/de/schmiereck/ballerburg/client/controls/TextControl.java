package de.schmiereck.ballerburg.client.controls;

/**
 * <p>
 * 	TODO	docu
 * </p> 
 * 
 * @author smk
 * @version <p>30.08.2003:	created</p>
 */
public class TextControl 
extends BaseControl
{
	private String	title;
	/**
	 * 	Constructor.
	 * 
	 * 
	 */
	public TextControl(String name, int posX, int posY, String title)
	{
		super(name, posX, posY);
		
		this.title	= title;
	}

	/**
	 * @return
	 */
	public String getTitle()
	{
		return this.title;
	}

	/**
	 * @param title
	 */
	public void setTitle(String title)
	{
		this.title = title;
	}
}
