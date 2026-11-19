package de.schmiereck.ballerburg.client;

/**
 * <p>
 * 	TODO	docu
 * </p> 
 * 
 * @author smk
 * @version <p>30.08.2003:	created</p>
 */
public class AnimationControl
implements Runnable
{
	private Thread thread = null;
	
	private GameControl gameControl;

	/**
	 * 	Constructor.
	 * 
	 * 
	 */
	public AnimationControl(GameControl gameControl)
	{
		super();
		
		this.gameControl = gameControl;
	}

	public void stopAnimation()
	{
		if (this.thread == null)
		{
			throw new RuntimeException("animation not startet");
		}

		this.thread = null;
	}

	public void startAnimation()
	{
		if (this.thread != null)
		{
			throw new RuntimeException("animation already startet");
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
				Thread.sleep(1000 / 25);
			}
			catch (InterruptedException e)
			{
				//e.printStackTrace(System.err);
				//this.stopPolling();
			}
			
			this.gameControl.bumAnimation();
		}
	}
}
