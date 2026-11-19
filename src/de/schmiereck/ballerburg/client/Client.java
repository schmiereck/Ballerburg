package de.schmiereck.ballerburg.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import de.schmiereck.ballerburg.shared.Cannon;
import de.schmiereck.ballerburg.shared.Part;

/**
 * <p>
 * 	TODO	docu
 * </p> 
 * 
 * @author smk
 * @version <p>30.08.2003:	created</p>
 */
public class Client
{
	private InetSocketAddress serverInfo;
	
	/**
	 * 	Constructor.
	 * 
	 * 
	 */
	public Client(String server)
	{
		super();

		this.serverInfo = new InetSocketAddress(server, 52323);
	}

	public String sendMessage(InetSocketAddress serverInfo, String message)
	{
		System.out.println("CLIENT: sendMessage: " + message);
		
		String retStr = null;
		
		Socket socket = new Socket();

		try
		{
			socket.connect(serverInfo);

			OutputStream outputStream = socket.getOutputStream();
			
			outputStream.write(message.getBytes());

			outputStream.flush();
			
			InputStream inputStream = socket.getInputStream();

			//Reader reader = new InputStreamReader(inputStream, "UTF-8");

			int len;
			//char buf[] = new char[1024];
			byte buf[] = new byte[1024];
			StringBuffer	ret = new StringBuffer();

			//while ((len = reader.read(buf, 0, 1024)) != -1)
			while ((len = inputStream.read(buf, 0, buf.length)) != -1)
			{
				ret.append(new String(buf, 0, len));
				//break;
			}
			
			if (ret.length() > 0)
			{
				retStr = ret.toString();
			}

			//System.out.println("CLIENT:\n" + retStr);

			//System.out.print("CLIENT: ENDE");
			
			inputStream.close();
			outputStream.close();

			socket.close();
		}
		catch (IOException ex)
		{
			throw new RuntimeException("connect socket to send message: " + message, ex);
		}
	
		return retStr;
	}

	public void sendHello()
	{
		String ret = this.sendMessage(this.serverInfo, "hello");
		
		System.out.println("CLIENT returns from server hello: " + ret);
	}


	/**
	 * @return
	 */
	public Integer retrieveNewPlayerNumber()
	{
		String playerNumberStr = this.sendMessage(this.serverInfo, "retrieveNewPlayerNumber");
		
		Integer playerNumber = new Integer(playerNumberStr);

		return playerNumber;
	}

	/**
	 * @param playerNumber
	 */
	public void killPlayerNumber(Integer playerNumber)
	{
		String retStr = this.sendMessage(this.serverInfo, "killPlayerNumber:" + playerNumber);
	}

	/**
	 * @param playerNumber
	 * @return
	 */
	public String initNewGamePlayer(String gameName, Integer playerNumber)
	{
		String retStr = this.sendMessage(this.serverInfo, "initNewGamePlayer:" + gameName + ":" + playerNumber);

		return retStr;
	}

	/**
	 * @param gameName
	 * @param integer
	 */
	public String removeGamePlayer(String gameName, Integer playerNumber)
	{
		String retStr = this.sendMessage(this.serverInfo, "removeGamePlayer:" + gameName + ":" + playerNumber);

		return retStr;
	}

	/**
	 * 
	 */
	public String[] listOpenGameNames()
	{
		String gameNames[] = null;
		String retStr = this.sendMessage(this.serverInfo, "listOpenGameNames");

		if (retStr != null)
		{
			gameNames = retStr.split(":");
		}
		return gameNames;
	}

	/**
	 * @param string
	 * @return
	 */
	public String[] listGamePlayerNames(String gameName)
	{
		String playerNames[] = null;
		String retStr = this.sendMessage(this.serverInfo, "listGamePlayerNames:" + gameName);

		if (retStr != null)
		{
			playerNames = retStr.split(":");
		}
		return playerNames;
	}

	/**
	 * @param string
	 */
	public void startGame(String gameName)
	{
		String retStr = this.sendMessage(this.serverInfo, "startGame:" + gameName);
	}

	/**
	 * @param gameName
	 */
	public String addGame(String gameName)
	{
		String retStr = this.sendMessage(this.serverInfo, "addGame:" + gameName);
		return retStr;
	}

	/**
	 * @param gameName
	 * @param playerNumber
	 * @return
	 */
	public String getWorldData(String gameName, Integer playerNumber)
	{
		String worldStr = this.sendMessage(this.serverInfo, "getWorldData:" + gameName + ":" + playerNumber);
		return worldStr;
	}

	/**
	 * @param gameName
	 * @return
	 */
	public String checkGameStarted(String gameName)
	{
		String retStr = this.sendMessage(this.serverInfo, "checkGameStarted:" + gameName);
		return retStr;
	}

	/**
	 * Dem Server melden, das ein Schuss abgegeben wurde.
	 * @param gameName
	 * @param playerNumber
	 * @return
	 */
	public String startBum(String gameName, Integer playerNumber, Cannon cannon)
	{
		String retStr = this.sendMessage(this.serverInfo, "startBum:" + gameName + ":" + 
											playerNumber + ":" + 
											cannon.getName() + ":" +
											cannon.getAngel() + ":" +
											cannon.getBlowPowder());
		return retStr;
	}

	/**
	 * Prüfen, ob von einem anderen Spieler ein Schuss abgegeben wurde.
	 * 
	 * @param string
	 * @param integer
	 * @return
	 */
	public String checkBum(String gameName, Integer playerNumber)
	{
		String retStr = this.sendMessage(this.serverInfo, "checkBum:" + gameName + ":" + playerNumber);
		return retStr;
	}

	/**
	 * Dem Server melden, was getroffen wurde.
	 * 
	 * @param gameName
	 * @param part
	 */
	public void endBum(String gameName, Part part)
	{
		String retStr = this.sendMessage(this.serverInfo, "endBum:" + gameName + ":" + part.getName());
	}

	/**
	 * Status des Spiels und aktuellen Spieler überprüfen.
	 * 
	 * @param string
	 * @param integer
	 * @return
	 */
	public String checkGameState(String gameName, Integer playerNumber)
	{
		String retStr = this.sendMessage(this.serverInfo, "checkGameState:" + gameName + ":" + playerNumber);
		return retStr;
	}

}
