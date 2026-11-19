package de.schmiereck.ballerburg.client;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.image.ColorModel;
import java.awt.image.DirectColorModel;
import java.util.Iterator;

import de.schmiereck.ballerburg.client.images.*;

/**
 * <p>
 * 	TODO	docu
 * </p> 
 * 
 * @author smk
 * @version <p>30.08.2003:	created</p>
 */
public class MainApplet
extends Applet
implements UpdateListener
{
	/**
	 * true: Wurde die Anwendung als Applikation gestartet?
	 * false: Wurde die Anwendung als Applet gestartet?
	 */
	private boolean runAsApplication = false;
	
	private Image      	dbImage		= null;
	private Graphics   	dbGraphics	= null;
	private Dimension	dbDimension = null;
	
	private static Color colorBackground		= new Color(150, 150, 200);
	
	private MainData 	mainData;
	private GameControl control		= null;
	private Painter		painter 	= new Painter(this);
	
	private String		serverParameter = null;

	//private BaseImage softImage = new RaytraceImage(24, 24);

	/**
	 * 	Constructor.
	 * 
	 * @throws java.awt.HeadlessException
	 */
	public MainApplet() throws HeadlessException
	{
		super();

		this.mainData = new MainData();
		
		//this.gameData = controll.getGameData();
	}

	/* (non-Javadoc)
	 * @see java.applet.Applet#init()
	 */
	public void init()
	{
		super.init();

		this.setSize(this.getSizeX(), this.getSizeY());
		
		//this.softImage.createImage(this);
		
		System.out.println("CLIENT: init");

		String server;
		
		try
		{
			server = this.getParameter("server");
		}
		catch (Exception e)
		{
			if (this.serverParameter != null)
			{
				server = this.serverParameter;
			}
			else
			{
				server = "localhost";
			}
		}

		System.out.println("CLIENT: server: " + server);

		UpdateManager viewUpdate = new UpdateManager();
		
		viewUpdate.addUpdateListener(this);

		this.control = new GameControl(viewUpdate, this.mainData, server);

		this.addMouseListener(control);
		
		PollControl pollControl = new PollControl(this.control);
		
		pollControl.startPolling();
	}

	/* (non-Javadoc)
	 * @see java.applet.Applet#stop()
	 */
	public void stop()
	{
		super.stop();

		this.control.killPlayer();
	}

	/**
	 * @see #runAsApplication
	 */
	public void setRunAsApplication(boolean runAsApplication)
	{
		this.runAsApplication = runAsApplication;
	}

	/**
	 * @see #sizeX
	 */
	public int getSizeX()
	{
		int sizeX;
		
		ClientGameData gameData = this.mainData.getGameData();

		if (gameData != null)
		{
			sizeX = gameData.getGameSizeX();
		}
		else
		{
			sizeX = 640;
		}
		
		return sizeX;
	}

	/**
	 * @see #sizeY
	 */
	public int getSizeY()
	{
		int sizeY;

		ClientGameData gameData = this.mainData.getGameData();
		
		if (gameData != null)
		{
			sizeY = gameData.getGameSizeY();
		}
		else
		{
			sizeY = 420;
		}
		
		return sizeY;
	}

	/* (non-Javadoc)
	 * @see java.awt.Component#update(java.awt.Graphics)
	 */
	public void update(Graphics g)
	{
		//System.out.println("update()");
		this.paint(g);
	}

	/* (non-Javadoc)
	 * @see java.awt.Component#paint(java.awt.Graphics)
	 */
	public void paint(Graphics g)
	{
		//System.out.println("paint()");
		Dimension d = this.getSize();
    	
			// Double-Buffer nicht initialisiert oder
			// Größe geändert ?
		if ((this.dbImage == null) || 
			(d.width != this.dbDimension.width) ||
			(d.height != this.dbDimension.height))
		{
			//System.out.println("new this.dbImage()");
				// Create the offscreen graphics context
			this.dbDimension = d;
			this.dbImage = this.createImage(d.width, d.height);
			this.dbGraphics = this.dbImage.getGraphics();
			
			//this.viewerData.setSize(d.width, d.height);
		}

		// Erase the previous image
//		this.dbGraphics.setColor(this.getBackground());
		this.dbGraphics.setColor(colorBackground);
		this.dbGraphics.fillRect(0, 0, d.width, d.height);
		
		// Offscreen Zeichnen.
		this.paintFrame(this.dbGraphics);
		
		//this.softImage.render();
		
		//this.softImage.drawImage(this.dbGraphics, 20, 60);

			// Offscreen anzeigen.
		g.drawImage(this.dbImage, 0, 0, this);
	}
	
	/**
	 * @param g
	 */
	public void paintFrame(Graphics g)
	{
		int controlStatus = this.mainData.getGameStatus();
		
		switch (controlStatus)
		{
			case MainData.GSNew:
			{
				break;
			}
			case MainData.GSGameSelect:
			{
				break;
			}
			case MainData.GSGameOpen:
			{
				break;
			}
			case MainData.GSGamePlay:
			{
				this.painter.paintGame(g, this.mainData.getGameData());
				break;
			}
		}
		Iterator buttonsIterator = this.control.getControlValuesIterator();

		this.painter.paintControls(g, buttonsIterator);
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.ballerburg.client.UpdateListener#notifyUpdate()
	 */
	public void notifyUpdate()
	{
		//this.setSize(this.getSizeX(), this.getSizeY());

		this.repaint();
	}

	/**
	 * @see #serverParameter
	 */
	public void setServerParameter(String serverParameter)
	{
		this.serverParameter = serverParameter;
	}

}
