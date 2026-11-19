package de.schmiereck.ballerburg.client;

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
	 * Wird nur aufgerufen, wenn die Klasse als Anwendung gestartet wird. 
	 * 'main' erzeugt ein Fenster, erzeugt eine neue Instanz der Klasse 
	 * und legt sie in das Fenster.
	 */ 
	public static void main(String args[] )
	{ 
		MainFrame frame = new MainFrame("Ballerburg by schmiereck");

		MainApplet mainApplet = new MainApplet(); 
		
		if (args.length > 0)
		{
			mainApplet.setServerParameter(args[0]);
		}

		// Größe des Frame:
		int maxX = mainApplet.getSizeX(); 
		int maxY = mainApplet.getSizeY(); 

		frame.show(); 
		frame.hide(); 
		frame.setSize(frame.getInsets().left + frame.getInsets().right  + maxX, 
					  frame.getInsets().top  + frame.getInsets().bottom + maxY); 

		frame.add("Center", mainApplet);  //*Einfügen des Applets 'Baller' in das Frame*/ 
		mainApplet.setRunAsApplication(true);  //*Ballerburg wurde als Applikation gestartet*/ 
		mainApplet.init();   //*init-Methode aufrufen*/ 
		mainApplet.start();   //*los geht's!*/ 
		frame.show(); 
	 } 
}
