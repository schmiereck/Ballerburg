package de.schmiereck.ballerburg.client.images;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ColorModel;
import java.awt.image.DirectColorModel;
import java.awt.image.ImageConsumer;
import java.awt.image.ImageProducer;

/**
 * @author smk
 */
public class BaseImage
implements ImageProducer
{
	private int width;
	private int height;
	private int pixels[];
	
	private Image image = null;

	private final ColorModel cm = new DirectColorModel(32, 0xFF0000, 0xFF00, 0xFF);

	private ImageConsumer consumer = null;
	
	public BaseImage(int width, int height)
	{
		this.width	= width;
		this.height	= height;
		
		this.pixels = new int[this.width * this.height];
	}

	public void createImage(Component appletComponent)
	{
		this.image = appletComponent.createImage(this);
	}

	public void drawImage(Graphics g, int posX, int posY)
	{
		g.drawImage(this.image, posX, posY, null);
	}

	public int getPixelRed(int x, int y)
	{
		int adr = x + y * this.width;
		int p;
		try
		{
			p = ((this.pixels[adr] >> 16) & 0xFF);
		}
		catch (Exception ex)
		{
			String msg = "x: " + x + ", " +
			"y: " + y + ", " +
			"this.width: " + this.width + ", " +
			"this.height: " + this.height + ", " +
			"adr: " + adr;
			System.err.println("ERROR GET: " + msg);
			ex.printStackTrace(System.err);
			throw new RuntimeException(
			msg + "\n" +
			ex);
		}
		return p;
	}

	public void setPixel(int x, int y, int red, int green, int blue)
	{
		int adr = x + y * this.width;

		try
		{
			this.pixels[adr] = (red << 16) | (green << 8) | (blue << 0);
		}
		catch (Exception ex)
		{
			String msg = "x: " + x + ", " +
			"y: " + y + ", " +
			"this.width: " + this.width + ", " +
			"this.height: " + this.height + ", " +
			"adr: " + adr;
			System.err.println("ERROR SET: " + msg);
			ex.printStackTrace(System.err);
			throw new RuntimeException(
			msg + "\n" +
			ex);
		}
	}

	public void render()
	{
		for (int y = 0; y < this.height; y++)
		{
			for (int x = 0; x < this.width; x++)
			{
				//int adr = x + y * this.width;
				
				int red 	= x;
				int green 	= (int)Math.sqrt(x * x + y * y);
				int blue 	= y;

				if (green > 255)
				{
					green = 0;
				}
				
				this.setPixel(x, y, red, green, blue);

				//this.pixels[adr] = (red << 16) | (green << 8) | (blue << 0);
			}
		}
	
		//this.makeImage();
	}
	
	protected void makeImage()
	{
		this.startProduction(this.consumer);
	}

	/* (non-Javadoc)
	 * @see java.awt.image.ImageProducer#addConsumer(java.awt.image.ImageConsumer)
	 */
	public void addConsumer(ImageConsumer ic)
	{
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see java.awt.image.ImageProducer#isConsumer(java.awt.image.ImageConsumer)
	 */
	public boolean isConsumer(ImageConsumer ic)
	{
		return consumer != null;
	}

	/* (non-Javadoc)
	 * @see java.awt.image.ImageProducer#removeConsumer(java.awt.image.ImageConsumer)
	 */
	public void removeConsumer(ImageConsumer ic)
	{
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see java.awt.image.ImageProducer#startProduction(java.awt.image.ImageConsumer)
	 */
	public void startProduction(ImageConsumer ic)
	{
		if (ic != null)
			this.consumer = ic;
		if (consumer != null)
		{
			this.consumer.setDimensions(width, height);
			this.consumer.setProperties(null);
			this.consumer.setColorModel(cm);
			this.consumer.setHints(
				ImageConsumer.TOPDOWNLEFTRIGHT
					| ImageConsumer.COMPLETESCANLINES
					| ImageConsumer.SINGLEPASS
					| ImageConsumer.SINGLEFRAME);
			this.consumer.setPixels(0, 0, width, height, cm, pixels, 0, width);
			this.consumer.imageComplete(ImageConsumer.SINGLEFRAMEDONE);
		}
	}

	/* (non-Javadoc)
	 * @see java.awt.image.ImageProducer#requestTopDownLeftRightResend(java.awt.image.ImageConsumer)
	 */
	public void requestTopDownLeftRightResend(ImageConsumer ic)
	{
		// TODO Auto-generated method stub
		
	}

	/**
	 * @return
	 */
	public int getWidth()
	{
		return this.width;
	}

	/**
	 * @return
	 */
	public int getHeight()
	{
		return this.height;
	}

}
