package de.schmiereck.ballerburg.client;

import java.awt.Event;
import java.awt.Frame;

/**
 * <p>
 * 	TODO	docu
 * </p> 
 * 
 * @author smk
 * @version <p>30.08.2003:	created</p>
 */
public class MainFrame 
extends Frame
{

	public MainFrame(String str)
	{
		super(str);
	}

	/**
	 * Schlieﬂe Fenster, wenn es das tun soll.
	 */
	public boolean handleEvent(Event evt)
	{
		boolean ret;
		
		switch (evt.id)
		{
			case Event.WINDOW_DESTROY:
			{
				dispose();
				System.exit(0);
				ret = true;
			}
			default :
			{
				ret = super.handleEvent(evt);
			}
		}
		return ret;
	}
}
