package de.schmiereck.ballerburg.client.controls;

/**
 * <p>
 * 	TODO	docu
 * </p> 
 * 
 * @author smk
 * @version <p>30.08.2003:	created</p>
 */
public class SelectControl
extends BaseControl
{
	String	values[];

	private int paintedStringHeight = 0;
	
	/**
	 * 	Constructor.
	 * 
	 * @param name
	 * @param posX
	 * @param posY
	 */
	public SelectControl(String name, int posX, int posY, String values[])
	{
		super(name, posX, posY);

		this.values = values;
	}
	/**
	 * @return
	 */
	public String[] getValues()
	{
		return this.values;
	}

	/**
	 * @param values
	 */
	public void setValues(String[] values)
	{
		this.values = values;
	}

	public void setPaintedSize(int paintedSizeX, int paintedSizeY, int paintedStringHeight)
	{
		super.setPaintedSize(paintedSizeX, paintedSizeY);
		
		this.paintedStringHeight = paintedStringHeight;
	}

	public int getPaintedStringHeight()
	{
		return this.paintedStringHeight;
	}
	/**
	 * @param no
	 */
	public String getValue(int no)
	{
		String ret = null;
		
		if (this.values != null)
		{
			ret = this.values[no];
		}
		return ret;
	}
}