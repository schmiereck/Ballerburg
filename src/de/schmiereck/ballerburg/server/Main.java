package de.schmiereck.ballerburg.server;

/**
 * <p>
 * 	TODO	docu
 * </p> 
 * 
 * @author smk
 * @version <p>30.08.2003:	created</p>
 */
public class Main
{
	/**
	 * Enthält das Server Objekt der Anwendung.<br />
	 * Wird beim Start initialisiert.
	 */
	private static Server server;

	public static void main(String[] args)
	{
		System.out.println("ballerbug server: starting");

		server = Server.getInstance();

		System.out.println(server.toString());

		// Server auf Port 52323 starten.
		server.startServer(52323);

		System.out.println("ballerbug server: started");

		/*
		// 3s warten.
		try
		{
			Thread.sleep(3000);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		
		// Server stopen.
		server.stopServer();
		*/
	}
}
