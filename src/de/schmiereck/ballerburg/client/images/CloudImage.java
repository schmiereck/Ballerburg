package de.schmiereck.ballerburg.client.images;

import java.util.Random;

/**
 * @author smk
 * @version 01.09.2003
 */
public class CloudImage
extends BaseImage
{

	/**
	 * Constructor.
	 * 
	 * @param sizeX
	 * @param sizeY
	 */
	public CloudImage(int sizeX, int sizeY)
	{
		super(sizeX, sizeY);
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
		
		int posX = 0;
		int posY = 0;
		int sizeX = this.getWidth();
		int sizeY = this.getHeight();

		this.initSquare(0, 0, sizeX, sizeY);

		this.renderDiamondPoint(0, 0, sizeX, sizeY);

		this.renderSquare(0, 0, sizeX, sizeY);
		
		try
		{

		int divX = 2;
		int divY = 2;
		
		while (divX < sizeX && divY < sizeX)
		{
			int sx = sizeX / divX;
			int sy = sizeY / divY;
			System.out.println("----------: " + sy + ", " + sy);
			
			for (int x = 0; x < sizeX; x += sx)
			{
				for (int y = 0; y < sizeY; y += sy)
				{
					this.renderSquare(x, y, sx, sy);

					this.renderDiamondPoint(x, y, sx, sy);
				}
			}
			divX = divX * 2;
			divY = divY * 2;
		}

		}
		catch (Exception ex)
		{
		}

		/*
		int shX = sizeX / 2;
		int shY = sizeY / 2;

		this.renderDiamondPoint(posX, posY, shX, shY);

		this.renderSquare(posX, posY, shX, shY);
		*/
		this.makeImage();
	}
	
	private Random rnd = new Random();
	
	// http://www.javaworld.com/javaworld/jw-08-1998/jw-08-step_p.html

	private void initSquare(int posX, int posY, int sizeX, int sizeY)
	{
		System.out.println("----------: initSquare");
		/*
		this.renderPoint(posX, 					posY,				0, 255);
		this.renderPoint(posX + (sizeX - 1), 	posY,				0, 255);
		this.renderPoint(posX, 					posY + (sizeY - 1),	0, 255);
		this.renderPoint(posX + (sizeX - 1), 	posY + (sizeY - 1),	0, 255);
		*/
		this.setPixel(posX, 					posY,				255, 0, 0);
		this.setPixel(posX + (sizeX - 1), 		posY,				255, 0, 0);
		this.setPixel(posX, 					posY + (sizeY - 1),	255, 0, 0);
		this.setPixel(posX + (sizeX - 1), 		posY + (sizeY - 1),	255, 0, 0);
		
	}

	private void renderDiamondPoint(int posX, int posY, int sizeX, int sizeY)
	{
		System.out.println("----------: renderDiamondPoint");
		int c1 = this.getPoint(posX, 				posY);
		int c2 = this.getPoint(posX + (sizeX - 1), 	posY);
		int c3 = this.getPoint(posX, 				posY + (sizeY - 1));
		int c4 = this.getPoint(posX + (sizeX - 1), 	posY + (sizeY - 1));

		/*
		 *		c1	1	c2
		 *		2	cm	3
		 *		c3	4	c4
		 */

		int cm = (c1 + c2 + c3 + c4) / 4;		

		int shX = (sizeX - 1) / 2;
		int shY = (sizeY - 1) / 2;

		//this.renderPoint(posX + shX, posY + shY, cm, (sizeX + sizeY) / 2);
		this.renderPoint(posX + shX, posY + shY, cm);
	}

	private void renderSquare(int posX, int posY, int sizeX, int sizeY)
	{
		System.out.println("----------: renderSquare");
		int shX = (sizeX - 1) / 2;
		int shY = (sizeY - 1) / 2;

		int c1 = this.getPoint(posX, 				posY);
		int c2 = this.getPoint(posX + (sizeX - 1), 	posY);
		int c3 = this.getPoint(posX, 				posY + (sizeY - 1));
		int c4 = this.getPoint(posX + (sizeX - 1), 	posY + (sizeY - 1));
		
		int cm = this.getPoint(posX + shX,	 	posY + shY);

		/*
		 *		c1	1	c2
		 *		2	cm	3
		 *		c3	4	c4
		 */

		this.renderPoint(posX + shX, 		posY, 				(c1 + c2 + cm) / 3);	// 1
		this.renderPoint(posX,		 		posY + shY,			(c1 + c3 + cm) / 3);	// 2
		this.renderPoint(posX + (sizeX - 1),	posY + shY,			(c2 + c4 + cm) / 3);	// 3
		this.renderPoint(posX + shX, 		posY + (sizeY - 1),	(c3 + c4 + cm) / 3);	// 4
	}

	private int getPoint(int posX, int posY)
	{
		return this.getPixelRed(posX, posY);
	}
		
	private void renderPoint(int posX, int posY, int c1)
	{
		System.out.println(posX + ", " + posY + ": " + c1);
		this.setPixel(posX, posY, c1, c1, 255);
	}

	private void renderPoint(int posX, int posY, int c1, int r)
	{
		//int r = Math.min(c1, 255 - c1);

		int c = (c1 + (int)((rnd.nextDouble() * (r * 2)) - r));
		
		if (c < 0)
			c = 0;
		if (c > 255)
			c = 255;

		System.out.println(posX + ", " + posY + ": ## " + c + " ##");
		this.setPixel(posX, posY, c, c, 255);
	}
}
