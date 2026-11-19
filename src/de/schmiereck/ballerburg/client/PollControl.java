package de.schmiereck.ballerburg.client;

/**
 * <p>
 * 	TODO	docu
 * </p> 
 * 
 * @author smk
 * @version <p>30.08.2003:	created</p>
 */
public class PollControl
implements Runnable
{
	private Thread thread = null;
	
	private GameControl gameControl;

	/**
	 * 	Constructor.
	 * 
	 * 
	 */
	public PollControl(GameControl gameControl)
	{
		super();
		
		this.gameControl = gameControl;
	}

	public void stopPolling()
	{
		if (this.thread == null)
		{
			throw new RuntimeException("polling not startet");
		}

		this.thread = null;
	}

	public void startPolling()
	{
		if (this.thread != null)
		{
			throw new RuntimeException("polling already startet");
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
			try
			{
				Thread.sleep(1000);
			}
			catch (InterruptedException e)
			{
				//e.printStackTrace(System.err);
				//this.stopPolling();
			}
			
			this.gameControl.pollServer();
		}
	}

}
