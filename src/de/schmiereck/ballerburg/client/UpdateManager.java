package de.schmiereck.ballerburg.client;

import java.applet.Applet;

/**
 * <p>
 * 	TODO	docu
 * </p> 
 * 
 * @author smk
 * @version <p>30.08.2003:	created</p>
 */
public class UpdateManager
{
	private UpdateListener	updateListener;
	
	public void addUpdateListener(UpdateListener updateListener)
	{
		this.updateListener = updateListener;
	}
	
	public void notifyDataChanged()
	{
		if (this.updateListener != null)
		{
			this.updateListener.notifyUpdate();
		}
	}
}
