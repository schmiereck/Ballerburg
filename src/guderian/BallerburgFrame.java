package guderian;
import java.net.*; 
import java.io.*; 
import java.applet.*; 
import java.awt.*; 
import java.lang.Thread; 

/** BallerburgFrame ist Ableitung der Fensterklasse. Es wird ein Anwendungsfenster erstellt. */ 

public class BallerburgFrame extends Frame { 

		public BallerburgFrame(String str)        { 
				super (str); 
		} 

/** Schlieﬂe Fenster, wenn es das tun soll. */ 

		public boolean handleEvent(Event evt) { 

				switch (evt.id) { 
						case Event.WINDOW_DESTROY: 
								dispose(); 
								System.exit(0); 
								return true; 

						default: 
								return super.handleEvent(evt); 
				} 
		} 
} 

