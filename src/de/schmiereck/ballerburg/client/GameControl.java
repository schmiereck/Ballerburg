package de.schmiereck.ballerburg.client;

import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Iterator;

import de.schmiereck.ballerburg.client.controls.*;
import de.schmiereck.ballerburg.shared.*;

/**
 * <p>
 * 	TODO	docu
 * </p> 
 * 
 * @author smk
 * @version <p>30.08.2003:	created</p>
 */
public class GameControl
implements MouseListener
{
	private TextControl powderTextControl = null;

	private String lastBumCannonName = null;

	private Client client;
	
	private HashMap controls = new HashMap();
	
	private UpdateManager viewUpdate;
	private MainData mainData;

	private TextControl gameNameTextControl = null;
	private TextControl angelTextControl	= null;
	
	private Bullet bullet = null;

	private static final String all = "abcdefghijklmnopqrstuvwxyz *_<>";
	
	/**
	 * 	Constructor.
	 * 
	 * 
	 */
	public GameControl(UpdateManager viewUpdate, MainData mainData, String server)
	{
		super();
		
		this.viewUpdate = viewUpdate;
		this.mainData = mainData;
		
		this.client = new Client(server);

		this.doStartSelectGames();
	}

	SelectControl gamesSelectControl = null;
	SelectControl playersSelectControl = null;

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	public void mouseClicked(java.awt.event.MouseEvent e)
	{
		System.out.println("mouseClicked");
		BaseControl control = this.checkControlClick(e);

		SelectControl selectControl = null;

		if (control instanceof SelectControl)
		{
			selectControl = (SelectControl)control;
		}

		String controlName = null;

		if (control != null)
		{
			controlName = control.getName();
		}
		
		if (controlName != null)
		{
			if (controlName.equals("gameSelect"))
			{
				String gameNames[] = this.client.listOpenGameNames();
				
				this.gamesSelectControl = new SelectControl("games", 30, 50, gameNames);
				
				this.addControl(this.gamesSelectControl);
				
				this.mainData.setGameStatus(MainData.GSGameSelect);
				
				this.viewUpdate.notifyDataChanged();
			}
			else
			{
				if (controlName.equals("games"))
				{
					int no = (e.getY() - selectControl.getPosY()) / selectControl.getPaintedStringHeight();
					
					String gameName = selectControl.getValue(no);
					
					if (gameName != null)
					{
						this.removeAllConrols();

						this.doSelectGame(gameName);
				
						this.mainData.setGameStatus(MainData.GSGameOpen);
				
						this.viewUpdate.notifyDataChanged();
					}
				}
				else 
				{
					if (controlName.equals("start"))
					{
						this.removeAllConrols();

						this.doStartGame();

						this.mainData.setGameStatus(MainData.GSGamePlay);
				
						this.viewUpdate.notifyDataChanged();
					}
					else
					{
						if (controlName.equals("playersBack"))
						{
							this.removeAllConrols();

							this.client.removeGamePlayer(this.mainData.getGameData().getName(), this.mainData.getGameData().getPlayerNumber());
							
							this.doStartSelectGames();

							this.mainData.setGameStatus(MainData.GSNew);
				
							this.viewUpdate.notifyDataChanged();
						}
						else
						{
							if (controlName.equals("addGame"))
							{
								this.doAddGame();
	
								this.mainData.setGameStatus(MainData.GSGameAdd);
					
								this.viewUpdate.notifyDataChanged();
							}
							else
							{
								if (controlName.equals("delete"))
								{
									String gameName = this.gameNameTextControl.getTitle();
									
									if (gameName.length() > 0)
									{
										gameName = gameName.substring(0, gameName.length() - 1);
									
										this.gameNameTextControl.setTitle(gameName);
	
										this.viewUpdate.notifyDataChanged();
									}
								}
								else
								{
									if (controlName.equals("add"))
									{
										String gameName = this.gameNameTextControl.getTitle();
									
										if (gameName.length() > 0)
										{
											if (this.client.addGame(gameName) == null)
											{
												// Name wird schon verwendet.
											}
											else
											{
												this.removeAllConrols();

												this.doSelectGame(gameName);
	
												this.mainData.setGameStatus(MainData.GSGameOpen);
											}
											
											this.viewUpdate.notifyDataChanged();
										}
									}
									else
									{
										if (controlName.equals("back"))
										{
											this.removeAllConrols();
											
											this.doStartSelectGames();
	
											this.mainData.setGameStatus(MainData.GSNew);
	
											this.viewUpdate.notifyDataChanged();
										}
										else
										{
											if (controlName.equals("bum"))
											{
												ClientGameData gameData = this.mainData.getGameData();
												if (gameData.getSelectedCannon() != null)
												{
													// Nur neuen Schuss zulassen wenn nichts mehr in der Luft.
													if (this.bullet == null)
													{
														gameData.setPlayThisRound(false);
														gameData.setMyBumIsRunning(true);

														this.doBum(gameData.getSelectedCannon());

														this.client.startBum(this.mainData.getGameData().getName(), this.mainData.getGameData().getPlayerNumber(), this.mainData.getGameData().getSelectedCannon());

														this.doDeselectCannon();

														// Erst zurücksetzen, wenn selber geschossen.
														this.lastBumCannonName = null;
													}
												}
											}
											else
											{
												if (controlName.equals("goStart"))
												{
													this.removeAllConrols();

													this.client.removeGamePlayer(this.mainData.getGameData().getName(), this.mainData.getGameData().getPlayerNumber());
							
													this.doStartSelectGames();

													this.mainData.setGameStatus(MainData.GSNew);
				
													this.viewUpdate.notifyDataChanged();
												}
												else
												{
													if (controlName.length() == 1)
													{
														this.gameNameTextControl.setTitle(this.gameNameTextControl.getTitle() + controlName);
			
														this.viewUpdate.notifyDataChanged();
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
		else
		{
			// Kein Control ausgewählt ?

			if (this.mainData.getGameStatus() == MainData.GSGamePlay)
			{
				if (this.mainData.getGameData().getPlayThisRound() == true)
				{
					ClientGameData gameData = this.mainData.getGameData();

					Cannon foundCannon = null;

					Iterator partsIterator = gameData.getPartsIterator();

					while (partsIterator.hasNext())
					{
						Part part = (Part)partsIterator.next();

						int sizeX = part.getSizeX();
						int sizeY = part.getSizeY();
						int posX = part.getPosX() - sizeX / 2;
						int posY = (gameData.getGameSizeY() - part.getPosY()) - sizeY;

						if ((e.getX() > posX) &&
							(e.getY() > posY) &&
							(e.getX() < posX + sizeX) &&
							(e.getY() < posY + sizeY))
						{
							if (part instanceof Cannon)
							{			
								Cannon cannon = (Cannon)part;
				
								if (cannon.getIsMyOwn() == true)
								{
									foundCannon = cannon;
									break;
								}
							}
						}
					}
				
					// Neue Auswahl ?
					if (foundCannon != null)
					{
						// War noch keine selektiert ?
						if (this.mainData.getGameData().getSelectedCannon() == null)
						{
							ButtonControl leftButtonControl = new ButtonControl("left", 195, 25, "<");
		
							this.addControl(leftButtonControl);

							this.angelTextControl = new TextControl("angel", 210, 25, null);
		
							this.addControl(this.angelTextControl);

							ButtonControl rightButtonControl = new ButtonControl("right", 240, 25, ">");
		
							this.addControl(rightButtonControl);

							ButtonControl upButtonControl = new ButtonControl("powderUp", 280, 5, "more");
		
							this.addControl(upButtonControl);

							this.powderTextControl = new TextControl("powder", 285, 25, null);
		
							this.addControl(this.powderTextControl);

							ButtonControl downButtonControl = new ButtonControl("powderDown", 280, 45, "less");
		
							this.addControl(downButtonControl);

							ButtonControl bumButtonControl = new ButtonControl("bum", 370, 25, "bum !!!");
		
							this.addControl(bumButtonControl);
						}

						this.mainData.getGameData().setSelectedCannon(foundCannon);

						this.angelTextControl.setTitle(Integer.toString(foundCannon.getAngel()));
						this.powderTextControl.setTitle(Integer.toString(foundCannon.getBlowPowder()));
					}
					else
					{
						// Keine Auswahl mehr:

						// War schon eine selektiert ?
						if (this.mainData.getGameData().getSelectedCannon() != null)
						{
							this.doDeselectCannon();
						}
					}

					this.viewUpdate.notifyDataChanged();
				}
			}
		}
	}
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	public void mouseEntered(java.awt.event.MouseEvent e)
	{
		System.out.println("mouseEntered");
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	public void mouseExited(java.awt.event.MouseEvent e)
	{
		System.out.println("mouseExited");
		// TODO Auto-generated method stub

	}

	ClickControl	clickControl	= null;

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	public void mousePressed(java.awt.event.MouseEvent e)
	{
		System.out.println("mousePressed");
		
		BaseControl control = this.checkControlClick(e);

		if (control != null)
		{
			String controlName = control.getName();
		
			if (controlName != null)
			{
				if (controlName.equals("left"))
				{
					this.startClick(controlName);
				}
				else
				{
					if (controlName.equals("right"))
					{
						this.startClick(controlName);
					}
					else
					{
						if (controlName.equals("powderUp"))
						{
							this.startClick(controlName);
						}
						else
						{
							if (controlName.equals("powderDown"))
							{
								this.startClick(controlName);
							}
						}
					}
				}
			}
		}
	}

	public void clickRepeat(String controlName)
	{
		if (controlName != null)
		{
			if (controlName.equals("left"))
			{
				this.doPressLeft();
			}
			else
			{
				if (controlName.equals("right"))
				{
					this.doPressRight();
				}
				else
				{
					if (controlName.equals("powderUp"))
					{
						Cannon selectedCannon = this.mainData.getGameData().getSelectedCannon();

						if (selectedCannon != null)
						{
							int p = selectedCannon.getBlowPowder() + 1;
							selectedCannon.setBlowPowder(p);

							this.powderTextControl.setTitle(Integer.toString(p));

							this.viewUpdate.notifyDataChanged();
						}
					}
					else
					{
						if (controlName.equals("powderDown"))
						{
							Cannon selectedCannon = this.mainData.getGameData().getSelectedCannon();

							if (selectedCannon != null)
							{
								int p = selectedCannon.getBlowPowder() - 1;
																	
								if (p > 5)
								{
									selectedCannon.setBlowPowder(p);

									this.powderTextControl.setTitle(Integer.toString(p));
								}

								this.viewUpdate.notifyDataChanged();
							}
						}
					}
				}
			}
		}
	}

	private void startClick(String controlName)
	{
		if (this.clickControl != null)
		{
			this.clickControl.stopRepeat();
			this.clickControl = null;
		}
		this.clickControl = new ClickControl(this, controlName);
		this.clickControl.startRepeat();	
	}

	private void stopClick()
	{
		if (this.clickControl != null)
		{
			this.clickControl.stopRepeat();
			this.clickControl = null;
		}
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	public void mouseReleased(java.awt.event.MouseEvent e)
	{
		this.stopClick();
	}





	private BaseControl checkControlClick(java.awt.event.MouseEvent e)
	{
		BaseControl retControl = null;
		
		Iterator controlsIterator = this.getControlValuesIterator();
		
		while (controlsIterator.hasNext())
		{
			BaseControl control = (BaseControl)controlsIterator.next();
		
			if ((e.getX() > control.getPosX()) &&
				(e.getY() > control.getPosY()) &&
				(e.getX() < control.getPosX() + control.getPaintedSizeX()) &&
				(e.getY() < control.getPosY() + control.getPaintedSizeY()))
			{
				retControl = control;
				break;		
			}
		}
		return retControl;
	}
	private void doPressRight()
	{
		Cannon selectedCannon = this.mainData.getGameData().getSelectedCannon();

		if (selectedCannon != null)
		{
			selectedCannon.setAngel(selectedCannon.getAngel() - 1);
		
			this.angelTextControl.setTitle(Integer.toString(selectedCannon.getAngel()));
		
			this.viewUpdate.notifyDataChanged();
		}
	}
	private void doPressLeft()
	{
		Cannon selectedCannon = this.mainData.getGameData().getSelectedCannon();
		
		if (selectedCannon != null)
		{
			selectedCannon.setAngel(selectedCannon.getAngel() + 1);
		
			this.angelTextControl.setTitle(Integer.toString(selectedCannon.getAngel()));
		
			this.viewUpdate.notifyDataChanged();
		}
	}
	private void doDeselectCannon()
	{
		this.removeControl("left");
		this.removeControl("angel");
		this.removeControl("right");
		this.removeControl("bum");
		
		this.removeControl("powderUp");
		this.removeControl("powder");
		this.removeControl("powderDown");
		
		this.mainData.getGameData().setSelectedCannon(null);
	}
	private void doStartSelectGames()
	{
		this.mainData.setGameData(null);

		this.addControl(new ButtonControl("gameSelect", 10, 20, "list open games >>"));
		
		this.addControl(new ButtonControl("addGame", 190, 20, "open new game..."));
	}
	
	private void doSelectGame(String gameName)
	{
		ClientGameData gameData = new ClientGameData(gameName);
		
		this.mainData.setGameData(gameData);
		
		this.initPlayer();
		
		ButtonControl backButtonControl = new ButtonControl("playersBack", 20, 10, "<< back to game selection");
		
		this.addControl(backButtonControl);
		
		ButtonControl startButtonControl = new ButtonControl("start", 20, 40, "start the game \"" + gameData.getName() + "\"...");
		
		this.addControl(startButtonControl);
		
		String playerNames[] = this.client.listGamePlayerNames(gameData.getName());
		
		this.playersSelectControl = new SelectControl("players", 30, 70, playerNames);
		
		this.addControl(playersSelectControl);
	}
	private void doAddGame()
	{
		this.removeControl("gameSelect");
		this.removeControl("games");
		this.removeControl("addGame");
		
		for (int i = 0; i < all.length(); i++)
		{
			char c = all.charAt(i);
			String cStr = String.valueOf(c);
			
			int pos = i;
			int x = pos % 9;
			int y = pos / 9; 
			
			ButtonControl cControl = new ButtonControl(cStr, 10 + 30 * x, 60 + 30 * y, " " + cStr + " ");
		
			this.addControl(cControl);
		}
		
		TextControl titleTextControl = new TextControl("title", 10, 20, "Enter the name of the game:");
		
		this.addControl(titleTextControl);
		
		this.gameNameTextControl = new TextControl("gameName", 10, 200, "");
		
		this.addControl(this.gameNameTextControl);
		
		ButtonControl deleteButtonControl = new ButtonControl("delete", 170, 200, "<== delete char");
		
		this.addControl(deleteButtonControl);
		
		ButtonControl addButtonControl = new ButtonControl("add", 10, 250, "add this game");
		
		this.addControl(addButtonControl);
		
		ButtonControl backButtonControl = new ButtonControl("back", 110, 250, "<< back to game selection");
		
		this.addControl(backButtonControl);
	}
	
	private void doStartGame()
	{
		ClientGameData gameData = this.mainData.getGameData();
		Integer playerNumber = gameData.getPlayerNumber();
		
		this.client.startGame(gameData.getName());

		String worldStr = this.client.getWorldData(gameData.getName(), gameData.getPlayerNumber());
		
		String partStrings[] = worldStr.split("&");
		
		for (int i = 0; i < partStrings.length; i++)
		{
			String partString = partStrings[i];

			String part[] = partString.split(":");
			
			if ("cannon".equals(part[0]))
			{
				Cannon cannon = new Cannon(part[1], Integer.parseInt(part[2]), Integer.parseInt(part[3]), Integer.parseInt(part[6]), new Integer(part[7]));
				
				if (playerNumber.equals(cannon.getPlayerNumber()))
				{
					cannon.setIsMyOwn(true);
				}

				gameData.addPart(cannon);
			}
			else
			{
				if ("rock".equals(part[0]))
				{
					Rock rock = new Rock(part[1], Integer.parseInt(part[2]), Integer.parseInt(part[3]), Integer.parseInt(part[4]), Integer.parseInt(part[5]));
				
					gameData.addPart(rock);
				}
				else
				{
					if ("actualPlayerNumber".equals(part[0]))
					{
						Integer actualPlayerNumber = new Integer(part[1]);
						
						if (playerNumber.equals(actualPlayerNumber))
						{
							gameData.setPlayThisRound(true);
						}
						else
						{
							gameData.setPlayThisRound(false);
						}
					}
					else
					{
						throw new RuntimeException("unknown part: \"" + partString + "\" in: \"" + worldStr + "\"");
					}
				}
			}
		} 
	}

	/**
	 * 
	 */
	public void initPlayer()
	{
		Integer playerNumber = this.client.retrieveNewPlayerNumber();
		
		ClientGameData gameData = this.mainData.getGameData();
		
		gameData.setPlayerNumber(playerNumber);

		String ret = this.client.initNewGamePlayer(gameData.getName(), playerNumber);
	}

	/**
	 * 
	 */
	public void killPlayer()
	{
		ClientGameData gameData = this.mainData.getGameData();

		if (gameData != null)
		{
			Integer playerNumber = gameData.getPlayerNumber();
		
			this.client.removeGamePlayer(gameData.getName(), playerNumber);
			this.client.killPlayerNumber(playerNumber);
		}
	}

	/**
	 * @param button
	 */
	public void addControl(BaseControl control)
	{
		this.controls.put(control.getName(), control);
	}

	/**
	 * @param controlName
	 */
	private void removeControl(String controlName)
	{
		this.controls.remove(controlName);
	}

	/**
	 * 
	 */
	private void removeAllConrols()
	{
		this.controls.clear();
	}

	Iterator getControlValuesIterator()
	{
		return this.controls.values().iterator(); 
	}

	/**
	 * 
	 */
	void pollServer()
	{
		ClientGameData gameData = this.mainData.getGameData();
		
		switch (this.mainData.getGameStatus())
		{
			case MainData.GSGameSelect:
			{
				String gameNames[] = this.client.listOpenGameNames();

				this.gamesSelectControl.setValues(gameNames);
				
				this.viewUpdate.notifyDataChanged();

				break;
			}
			case MainData.GSGameOpen:
			{
				String gameStarted = this.client.checkGameStarted(gameData.getName());

				if ("ok".equals(gameStarted))
				{
					this.removeAllConrols();
					
					this.doStartGame();

					this.mainData.setGameStatus(MainData.GSGamePlay);
				}
				else
				{
					String playerNames[] = this.client.listGamePlayerNames(gameData.getName());

					this.playersSelectControl.setValues(playerNames);
				}
				
				this.viewUpdate.notifyDataChanged();

				break;
			}
			case MainData.GSGamePlay:
			{
				// Prüfen, wer der nächste Spieler ist.

				String gameState = this.client.checkGameState(gameData.getName(), gameData.getPlayerNumber());

				if (gameState != null)
				{
					String gameStateParams[] = gameState.split(":");

					String isGameOver = gameStateParams[0];
					
					if ("1".equals(isGameOver))
					{
						gameData.setPlayThisRound(false);
				
						this.removeAllConrols();
						
						TextControl titleTextControl = new TextControl("title", 20, 20, "|_-==-- Game Over --==-_|");

						this.addControl(titleTextControl);

						ButtonControl goStartButtonControl = new ButtonControl("goStart", 20, 60, "go to game selection >>");
						
						this.addControl(goStartButtonControl);
						
						this.mainData.setGameStatus(MainData.GSScoreShow);

						this.viewUpdate.notifyDataChanged();
					}
					else
					{
						Integer actualPlayerNumber = new Integer(gameStateParams[1]);

						// Habe die aktuelle Runde schon ?
						if (gameData.getPlayThisRound() == true)
						{
							// bin nicht mehr eingetragen ?
							if (!actualPlayerNumber.equals(gameData.getPlayerNumber()))
							{
								// Nehm mich weg.
								gameData.setPlayThisRound(false);

								this.viewUpdate.notifyDataChanged();
							}
						}
						else
						{
							// Bin ich der aktuelle Spieler und weiss es noch nicht ?
							if (actualPlayerNumber.equals(gameData.getPlayerNumber()))
							{
								gameData.setPlayThisRound(true);

								this.viewUpdate.notifyDataChanged();
							}
						}
					}
				}

				// Prüfen, ob von einem anderen Spieler ein Schuss abgegeben wurde.
				String haveBum = this.client.checkBum(gameData.getName(), gameData.getPlayerNumber());

				if (haveBum != null)
				{
					String bumParams[] = haveBum.split(":");

					String cannonName = bumParams[0];

					// Nur einen Schuss aus der letzten Kannone zulassen.
					if (!cannonName.equals(this.lastBumCannonName))
					{
						this.lastBumCannonName = cannonName;
						
						int angel = Integer.parseInt(bumParams[1]);
						int blowPoweder = Integer.parseInt(bumParams[2]);

						Cannon cannon = (Cannon)gameData.getPart(cannonName);
					
						cannon.setAngel(angel);
						cannon.setBlowPowder(blowPoweder);

						this.doBum(cannon);

						this.viewUpdate.notifyDataChanged();
					}
				}

				break;
			}
		}
	}

	/**
	 * @param cannon
	 */
	private void doBum(Cannon cannon)
	{
		double	speed = cannon.getBlowPowder() / 100.0;
		double	angel = cannon.getAngel();
		
		this.bullet = new Bullet("bullet", cannon.getPosX(), cannon.getPosY(), speed, angel);
		
		ClientGameData gameData = this.mainData.getGameData();
		
		gameData.addPart(this.bullet);
		
		this.animationControl = new AnimationControl(this);
		
		this.animationControl.startAnimation();
	}
	
	private AnimationControl animationControl = null;
	
	/**
	 * Wird als Callback vom {@see AnimationControll} aufgerufen.
	 */
	public void bumAnimation()
	{
		if (this.bullet != null)
		{
			this.bullet.calcMove(1000 / 25);

			Part part = this.checkHit(this.bullet);
			
			if (part != null)
			{
				ClientGameData gameData = this.mainData.getGameData();
		
				if (this.bullet != part)
				{
					gameData.removePart(part);
				}

				this.animationControl.stopAnimation();
				this.animationControl = null;

				gameData.removePart(this.bullet);
				this.bullet = null;
				
				// Dieser Spieler hatte den Schuss abgegeben ?				
				if (gameData.getMyBumIsRunning() == true)
				{
					// Server melden, was getroffen wurde.
					this.client.endBum(gameData.getName(), part);	

					gameData.setMyBumIsRunning(false);
				}
			}

			this.viewUpdate.notifyDataChanged();
		}
	}
	
	private Part checkHit(Bullet bullte)
	{
		Part ret = null;

		if (this.bullet.getPosY() < 0)
		{
			ret = bullet;
		}
		else
		{
			ClientGameData gameData = this.mainData.getGameData();
		
			int bx = this.bullet.getPosX();
			int by = this.bullet.getPosY();
		
			Iterator partsIterator = gameData.getPartsIterator();
			
			while (partsIterator.hasNext())
			{
				Part part = (Part)partsIterator.next();

				// TODO Koannone wird nicht richtig überprüft.

				// Nicht sich selbst ?
				if (part != bullet)	
				{
					if (part.checkHit(bx, by))			
					{
						ret = part;
						break;
					}
				}
			} 
		}
		
		return ret;
	}
}
