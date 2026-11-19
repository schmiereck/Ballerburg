package de.schmiereck.ballerburg.client;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.Iterator;

import de.schmiereck.ballerburg.client.controls.BaseControl;
import de.schmiereck.ballerburg.client.controls.ButtonControl;
import de.schmiereck.ballerburg.client.controls.SelectControl;
import de.schmiereck.ballerburg.client.controls.TextControl;
import de.schmiereck.ballerburg.shared.Bullet;
import de.schmiereck.ballerburg.shared.Cannon;
import de.schmiereck.ballerburg.shared.Part;

public class Painter
{
	private MainApplet	mainApplet;
	
	private static Color colorBackground		= new Color(150, 150, 200);
	private static Color colorText				= new Color(0, 0, 0);
	private static Font  fontText				= new Font("SansSerif", Font.BOLD, 12);

	public Painter(MainApplet mainApplet)
	{
		super();
		
		this.mainApplet = mainApplet;
	}

	private int getSizeX()
	{
		return this.mainApplet.getSizeX();
	}

	private int getSizeY()
	{
		return this.mainApplet.getSizeY();
	}

	void paintControls(Graphics g, Iterator buttonsIterator)
	{
		while (buttonsIterator.hasNext())
		{
			BaseControl control = (BaseControl)buttonsIterator.next();
		
			if (control instanceof ButtonControl)
			{
				ButtonControl button = (ButtonControl)control;
			
				this.paintButtonControl(g, button);
			}
			else
			{
				if (control instanceof SelectControl)
				{
					SelectControl selectControl = (SelectControl)control;
			
					this.paintSelectControl(g, selectControl);
				}
				else
				{
					if (control instanceof TextControl)
					{
						TextControl textControl = (TextControl)control;
			
						this.paintTextControl(g, textControl);
					}
				}
			}
		}
	}

	private void paintSelectControl(Graphics g, SelectControl selectControl)
	{
		int posX = selectControl.getPosX();
		int posY = selectControl.getPosY();

		g.setColor(colorBackground);

		g.setFont(fontText);

		FontMetrics fm = g.getFontMetrics();

		int stringHeight   	= fm.getHeight();
		int stringAscent   = fm.getAscent();
		int stringDescent   = fm.getDescent();

		g.setColor(colorText);

		String gameNames[] = selectControl.getValues();

		int w = 0;
		int h = 0;

		if (gameNames != null)
		{
			h = gameNames.length * stringHeight;

			for (int i = 0; i < gameNames.length; i++)
			{
				String value = gameNames[i];

				int stringWidth		= fm.stringWidth(value) + 4;
			
				if (stringWidth > w)
				{
					w = stringWidth; 
				}
		
				g.drawString(value, posX + 2, (posY + stringAscent) + i * stringHeight);
			}
		}
		else
		{
			String msg = "[ no entrys listed ]";

			int stringWidth		= fm.stringWidth(msg) + 4;
			
			h = stringHeight + 4;
			w = stringWidth + 4; 

			g.drawString(msg, posX + 2, (posY + stringAscent));
		}

		selectControl.setPaintedSize(w, h, stringHeight);

		g.drawRect(posX, posY, w, h);
	}
	
	private void paintButtonControl(Graphics g, ButtonControl buttonControl)
	{
		int posX = buttonControl.getPosX();
		int posY = buttonControl.getPosY();
		String text = buttonControl.getTitle();

		g.setColor(colorBackground);
		g.setFont(fontText);

		FontMetrics fm = g.getFontMetrics();

		int stringHeight   	= fm.getHeight();
		int stringAscent   = fm.getAscent();
		int stringDescent   = fm.getDescent();
		int stringWidth		= fm.stringWidth(text); 
		
		int w = stringWidth + 8;
		int h = stringHeight + 4;
		
		g.draw3DRect(posX, posY, w, h, true);

		buttonControl.setPaintedSize(w, h);

		g.setColor(colorText);
		
		g.drawString(text, posX + 4, (posY + 2 + stringAscent));
	}

	private void paintTextControl(Graphics g, TextControl textControl)
	{
		int posX = textControl.getPosX();
		int posY = textControl.getPosY();
		String text = textControl.getTitle();

		g.setColor(colorBackground);
		g.setFont(fontText);

		FontMetrics fm = g.getFontMetrics();

		int stringHeight   	= fm.getHeight();
		int stringAscent   = fm.getAscent();
		int stringDescent   = fm.getDescent();
		int stringWidth		= fm.stringWidth(text); 
		
		int w = stringWidth + 8;
		int h = stringHeight + 4;
		
		//g.draw3DRect(posX, posY, w, h, true);

		textControl.setPaintedSize(w, h);

		g.setColor(colorText);
		
		g.drawString(text, posX + 4, (posY + 2 + stringAscent));
	}

	/**
	 * @param g
	 */
	void paintGame(Graphics g, ClientGameData gameData)
	{
		this.paintPlayerNumber(g, gameData);

		this.paintParts(g, gameData);
	}

	private void paintPlayerNumber(Graphics g, ClientGameData gameData)
	{
		Integer playerNumber = gameData.getPlayerNumber();

		String playerNumberMsg;

		if (playerNumber != null)
		{
			playerNumberMsg = "player no: " + playerNumber.toString();
		}
		else
		{
			playerNumberMsg = "no player from server";
		}
		
		if (gameData.getPlayThisRound() == true)
		{
			playerNumberMsg += " - your turn !";
		}

		g.setColor(colorText);
		g.drawString(playerNumberMsg, 10, 10);
	}

	private void paintParts(Graphics g, ClientGameData gameData)
	{
		Iterator partsIterator = gameData.getPartsIterator();
		
		while (partsIterator.hasNext())
		{
			Part part = (Part)partsIterator.next();

			int posX = part.getPosX();
			int posY = part.getPosY();
			int sizeX = part.getSizeX();
			int sizeY = part.getSizeY();

			if (part instanceof Cannon)
			{			
				Cannon cannon = (Cannon)part;
				
				if (cannon.getIsMyOwn() == true)
				{
					g.setColor(Color.BLUE);
				}
				else
				{
					g.setColor(Color.GRAY);
				}
				this.fillRect(g, posX + part.getOffX(), posY + part.getOffY(), sizeX, sizeY);
						   
				g.setColor(Color.WHITE);

				double a = Math.toRadians(cannon.getAngel());
				
				double x = Math.cos(a) * (part.getSizeX() / 2);
				double y = Math.sin(a) * (part.getSizeX() / 2);

				this.drawLine(g, posX, posY, (int)x, (int)y);
				
				if (gameData.getSelectedCannon() == cannon)
				{
					g.setColor(Color.ORANGE);
					this.drawRect(g, posX + part.getOffX(), posY + part.getOffY(), sizeX, sizeY);
				}
			}
			else
			{
				if (part instanceof Bullet)
				{
					Bullet bullet = (Bullet)part;

					g.setColor(Color.ORANGE);
					this.fillCircle(g, part.getPosX() + part.getOffY(), part.getPosY() + part.getOffY(), part.getSizeX(), part.getSizeY());
				}
				else
				{
					g.setColor(Color.GRAY);
					this.fill3DRect(g, posX + part.getOffX(), posY + part.getOffY(), sizeX, sizeY);
				}
			}
		}
	}
	
	private void fillRect(Graphics g, int posX, int posY, int sizeX, int sizeY)
	{
		g.fillRect(posX, (this.getSizeY() - posY) - sizeY, 
				   sizeX, sizeY);
	}
	
	private void fill3DRect(Graphics g, int posX, int posY, int sizeX, int sizeY)
	{
		g.fill3DRect(posX, (this.getSizeY() - posY) - sizeY, 
				   sizeX, sizeY, true);
	}
	
	private void drawRect(Graphics g, int posX, int posY, int sizeX, int sizeY)
	{
		g.drawRect(posX, (this.getSizeY() - posY) - sizeY, 
				   sizeX, sizeY);
	}
	
	private void drawLine(Graphics g, int posX, int posY, int sizeX, int sizeY)
	{
		int y = (this.getSizeY() - posY);
		g.drawLine(posX, y, 
				   posX + sizeX, y - sizeY);
	}

	private void fillCircle(Graphics g, int posX, int posY, int sizeX, int sizeY)
	{
		int sxh = sizeX / 2;
		int syh = sizeY / 2;
		g.fillOval(posX - sxh, (this.getSizeY() - posY) - syh, 
					sizeX, sizeY);
	}
	
}
