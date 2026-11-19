package de.schmiereck.ballerburg.client.images;

/**
 * @author smk
 * @version 01.09.2003
 */
public class RaytraceImage 
extends BaseImage
{

	/**
	 * Constructor.
	 * 
	 * @param width
	 * @param height
	 */
	public RaytraceImage(int width, int height)
	{
		super(width, height);
	}

	public void render()
	{
		for (int y = 0; y < this.getHeight(); y++)
		{
			for (int x = 0; x < this.getWidth(); x++)
			{
				//int adr = x + y * this.sizeX;
				
				int red 	= 0;
				int green 	= 0;
				int blue 	= 0;

				this.setPixel(x, y, red, green, blue);
			}
		}
		
		this.makeImage();
	}
}
