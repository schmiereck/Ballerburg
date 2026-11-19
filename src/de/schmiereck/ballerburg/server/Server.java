package de.schmiereck.ballerburg.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;

/**
 * <p>
 * 	Verwaltet die Informationen eines Servers.<br />
 * 	Ist als Singleton implementiert, um sicherzustellen, 
 * 	das jeweils nur ein Server pro Anwendung läuft.
 * </p> 
 * <p>
 * 	Die Funktion {@see #startServer(int)} startet den Server auf dem angegeben Port
 * 	in einem eigenen Thread.<br/>
 * 	Die Funktion {@see #stopServer()} stopt den Server wieder.<br/>
 * </p>
 * 
 * @author smk
 * @version <p>2003-07-11:	created</p>
 */
public class Server
implements Runnable
{
	private static Server instance = new Server();
	
	private Thread thread = null;

	private ServerSocket serverSocket = null;

	private ServerControll serverControll = new ServerControll();

	/**
	 * 	Constructor.
	 * 
	 * 
	 */
	private Server()
	{
		super();
		
		this.serverControll.addGame("hot shots");
		this.serverControll.addGame("basic instinct");
		this.serverControll.addGame("hot shots 2 sdjklfsdklöfj");
		this.serverControll.addGame("hot shots 3");
	}

	/**
	 * @return	die Instanz des Servers.
	 */
	public static Server getInstance()
	{
		return instance;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return "Server";
	}

	/**
	 * Startet den Server unter der angegebenen Portnummer.
	 * 
	 * @param port	ist die Port-Nummer unter der der Server lauschen soll.
	 */
	public void startServer(int port)
	{
		if (this.thread != null)
		{
			throw new RuntimeException("server already startet");
		}
		try
		{
			this.serverSocket = new ServerSocket(port);

			this.thread = new Thread(this);
		
			this.thread.start();

			System.out.println("SERVER: started on port " + port);
		}
		catch (IOException ex)
		{
			throw new RuntimeException("bind socket for server: " + port, ex);
		}
	}


	/**
	 * TODO Server stoppt erst, nachdem er eine Nachricht bekommen hat, da er in der run() hängt und wartet...
	 */
	public void stopServer()
	{
		if (this.thread == null)
		{
			throw new RuntimeException("server not startet");
		}
		

		//this.thread.stop();
		
		this.thread = null;
	}

	private static byte buf[] = new byte[1024 * 64];
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run()
	{
		System.out.println("SERVER: is running");
		
		while (Thread.currentThread() == this.thread)
		{
			try
			{
				Socket socket = this.serverSocket.accept();

				System.out.println("SERVER: Accept successful on port: " + this.serverSocket.getLocalPort());

				InputStream inputStream = socket.getInputStream();

				int len;
				StringBuffer	ret = new StringBuffer();

				while ((len = inputStream.read(buf, 0, buf.length)) != -1)
				{
					ret.append(new String(buf, 0, len));
					break;
				}
				System.out.println("SERVER: ret: " + ret.toString());
				
				String returnMessage = this.handleMessage(ret.toString());

				OutputStream outputStream = socket.getOutputStream();

				if (returnMessage != null)
				{
					outputStream.write(returnMessage.getBytes());
					//outputStream.write(new String("TEST").getBytes());
				}

				inputStream.close();
				outputStream.close();

				socket.close();
			}
			catch (IOException ex)
			{
				throw new RuntimeException("accept error", ex);
			}
		}

		System.out.println("SERVER: is stopped");
	}

	/**
	 * @param message
	 * @return
	 */
	private String handleMessage(String message)
	{
		String ret = null;
		
		if (message != null)
		{
			String messageParts[] = message.split(":");

			String messageName; 
			
			if (messageParts.length > 0)
			{
				messageName = messageParts[0]; 
			}
			else
			{
				messageName = message;
			}
			
			if ("retrieveNewPlayerNumber".equals(messageName))
			{
				Integer playerNumber = this.serverControll.retrieveNewPlayerNumber();
				
				ret = playerNumber.toString();
			}
			else
			{
				if ("killPlayerNumber".equals(messageName))
				{
					Integer playerNumber = new Integer(messageParts[1]);
					
					this.serverControll.killPlayerNumber(playerNumber);

					ret = "ok";
				}
				else
				{
					if ("initNewGamePlayer".equals(messageName))
					{
						String gameName = messageParts[1];
						Integer playerNumber = new Integer(messageParts[2]);
					
						ret = this.serverControll.initNewGamePlayer(gameName, playerNumber);
					}
					else
					{
						if ("listOpenGameNames".equals(messageName))
						{
							ret = this.serverControll.listOpenGameNames();
						}
						else
						{
							if ("openNewGame".equals(messageName))
							{
								String gameName = messageParts[1];

								ret = this.serverControll.addGame(gameName);
							}
							else
							{
								if ("listGamePlayerNames".equals(messageName))
								{
									String gameName = messageParts[1];

									ret = this.serverControll.listGamePlayerNames(gameName);
								}
								else
								{
									if ("startGame".equals(messageName))
									{
										String gameName = messageParts[1];

										ret = this.serverControll.startGame(gameName);
									}
									else
									{
										if ("addGame".equals(messageName))
										{
											String gameName = messageParts[1];

											ret = this.serverControll.addGame(gameName);
										}
										else
										{
											if ("removeGamePlayer".equals(messageName))
											{
												String gameName = messageParts[1];
												Integer playerNumber = new Integer(messageParts[2]);

												ret = this.serverControll.removeGamePlayer(gameName, playerNumber);
											}
											else
											{
												if ("getWorldData".equals(messageName))
												{
													String gameName = messageParts[1];
													Integer playerNumber = new Integer(messageParts[2]);

													ret = this.serverControll.getWorldData(gameName, playerNumber);
												}
												else
												{
													if ("checkGameStarted".equals(messageName))
													{
														String gameName = messageParts[1];

														ret = this.serverControll.checkGameStarted(gameName);
													}
													else
													{
														if ("startBum".equals(messageName))
														{
															String gameName = messageParts[1];
															Integer playerNumber = new Integer(messageParts[2]);
															String partName = messageParts[3];
															Integer angel = new Integer(messageParts[4]);
															Integer blowPowder = new Integer(messageParts[5]);

															ret = this.serverControll.startBum(gameName, playerNumber, partName, angel, blowPowder);
														}
														else
														{
															if ("checkBum".equals(messageName))
															{
																String gameName = messageParts[1];
																Integer playerNumber = new Integer(messageParts[2]);

																ret = this.serverControll.checkBum(gameName, playerNumber);
															}
															else
															{
																if ("endBum".equals(messageName))
																{
																	String gameName = messageParts[1];
																	String partName = messageParts[2];
									
																	ret = this.serverControll.endBum(gameName, partName);
																}
																else
																{
																	if ("checkGameState".equals(messageName))
																	{
																		String gameName = messageParts[1];
																		Integer playerNumber = new Integer(messageParts[2]);
									
																		ret = this.serverControll.checkGameState(gameName, playerNumber);
																	}
																}
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		
		return ret;
	}
	

}
