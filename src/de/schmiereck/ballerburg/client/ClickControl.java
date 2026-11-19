/*
 * Created on 31.08.2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package de.schmiereck.ballerburg.client;

/**
 * @author smk
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class ClickControl
implements Runnable
{
	private Thread thread = null;
	
	private GameControl gameControl;
	
	private String controlName;
	
	private int speed = 3;

	/**
	 * 	Constructor.
	 * 
	 * 
	 */
	public ClickControl(GameControl gameControl, String controlName)
	{
		super();
		
		this.gameControl = gameControl;
		this.controlName = controlName;
	}

	public void stopRepeat()
	{
		if (this.thread == null)
		{
			throw new RuntimeException("click not startet");
		}

		this.thread = null;
	}

	public void startRepeat()
	{
		if (this.thread != null)
		{
			throw new RuntimeException("click already startet");
		}

		this.thread = new Thread(this);
		
		this.thread.start();
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run()
	{
		while (Thread.currentThread() == this.thread)
		{
			this.gameControl.clickRepeat(this.controlName);

			try
			{
				Thread.sleep(1000 / this.speed);
			}
			catch (InterruptedException e)
			{
				//e.printStackTrace(System.err);
				//this.stopPolling();
			}
			
			if (this.speed < 100)
			{
				this.speed++;
			}
		}
	}
}
