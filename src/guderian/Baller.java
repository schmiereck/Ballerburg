package guderian;
import java.awt.*; 
import java.awt.event.*; 
import java.io.*; 
import java.awt.Image; 

public class Baller extends java.applet.Applet implements ActionListener { 

 Spiel s;      //* Erstellung einer Variablen s vom Typ Spiel*/ 
 Kanone k;     //* Erstellung einer Variablen k vom Typ Kanone*/ 
 Infozeile i;     //* Erstellung einer Variablen i vom Typ Infozeile*/ 
 Netzwerk netz;     //* Erstellung einer Variablen netz vom Typ Netzwerk*/ 
 Kontrolle t;     //* Erstellung einer Variablen t vom Typ Kontrolle.*/ 
 Thread enemycontrol;    //*Thread der sich im Hintergrund um Meldungen vom Gegner kümmert.*/ 

 TextField chat;     //* Eingabefeld für Textnachrichten und Systembefehle*/ 
 TextArea antwort;    //*Textbereich zum Anzeigen von Nachrichten und Systemmeldungen*/ 

 Image Boden;     //* Grafiken*/ 
 Image Gras; 
 Image Burg; 
 Image Thron; 
 Image Berg; 
 Image Kanone; 

 boolean Alleine = false;    //*Wird 'Ballerburg' als Applet oder als Applikation gestartet?*/ 
  
/** Wird nur aufgerufen, wenn die Klasse als Anwendung gestartet wird. 'main' erzeugt ein Fenster, erzeugt eine 
neue Instanz der Klasse und legt sie in das Fenster. */ 

 public static void main(String args[] ){ 
  
  int maxX = 500;    //*Größe des Frame*/ 
  int maxY = 501; 

				 BallerburgFrame frame = new BallerburgFrame("BALLERBURG von GUDERIAN"); 

				 frame.show(); 
  frame.hide(); 
				 frame.resize(frame.insets().left + frame.insets().right  + maxX, frame.insets().top  + frame.insets().bottom + maxY); 

				 Baller applet_Baller = new Baller(); 

				 frame.add("Center", applet_Baller);  //*Einfügen des Applets 'Baller' in das Frame*/ 
				 applet_Baller.Alleine =true;  //*Ballerburg wurde als Applikation gestartet*/ 
				 applet_Baller.init();   //*init-Methode aufrufen*/ 
				 applet_Baller.start();   //*los geht's!*/ 
  frame.show(); 
 } 

/** Initialisierung */ 

 public void init () { 
  resize(500,501); 

  if (!Alleine) {       //*Wenn Ballerburg als Applet gestartet wurde, müssen die Grafiken mit der getImage-Methode geholt werden*/ 
   Boden = getImage(getCodeBase(), "guderian_Bilder/Boden.gif"); 
   Gras = getImage(getCodeBase(), "guderian_Bilder/Gras.gif"); 
   Burg = getImage(getCodeBase(), "guderian_Bilder/Burg.gif"); 
   Thron = getImage(getCodeBase(), "guderian_Bilder/Thron.gif"); 
   Berg = getImage(getCodeBase(), "guderian_Bilder/Berg.gif"); 
   Kanone = getImage(getCodeBase(), "guderian_Bilder/Kanone.gif"); 
  } else {        //*Wenn Ballerburg als Applikation gestartet wurde, müssen die Grafiken mit der Toolkit.getDefaultToolkit().getImage-Methode geholt werden*/ 
   Boden = Toolkit.getDefaultToolkit().getImage("guderian_Bilder/Boden.gif"); 
   Gras = Toolkit.getDefaultToolkit().getImage("guderian_Bilder/Gras.gif"); 
   Burg = Toolkit.getDefaultToolkit().getImage("guderian_Bilder/Burg.gif"); 
   Thron = Toolkit.getDefaultToolkit().getImage("guderian_Bilder/Thron.gif"); 
   Berg = Toolkit.getDefaultToolkit().getImage("guderian_Bilder/Berg.gif"); 
   Kanone = Toolkit.getDefaultToolkit().getImage("guderian_Bilder/Kanone.gif"); 
  } 

  i = new Infozeile (this);      //*Import der entsprechenden Variable*/ 
  setLayout(null); 
  s = new Spiel (); 
  s.init(i,this);       //*Aufruf der init-Methode in der Klasse Spiel*/ 
  s.setBounds (0, 0, 499, 299);     //*Grenzen für die Anzeige des Canvas aus Spiel im Frame / Applet*/ 
  add (s);        //*Einfügen des Canvas*/ 

  k = new Kanone ();      //*Import der entsprechenden Variable*/ 
  k.setBounds (250, 300, 249, 99);     //*Grenzen für die Anzeige der Klasse Kanone (Panels / TextFields) im Frame / Applet*/ 
  k.initialisiere(s, this);      //*Aufruf der initialisiere-Methode aus der Klasse Kanone*/ 
  add (k);        //*Einfügen*/ 

  i.setBounds (0,300, 249,99);     //*Grenzen für die Anzeige des Infozeilen-Panels*/ 
  i.Anzeige ();       //*Aufruf der Anzeige-Methode aus der Klasse Infozeile*/ 
  add (i);        //*Einfügen*/ 

  antwort = new TextArea();      //*Neue TextArea für die Anzeige der Gegner- / Systemmeldungen*/ 
  antwort.setBounds(0,400,500,70);     //*Grenzen für die Anzeige der TextArea*/ 
  antwort.setEditable(false);      //*Hier kann nur vom Rechner reingeschrieben werden*/ 
  add(antwort);       //*Einfügen*/ 

  chat = new TextField("localhost");     //*Neues TextField für die Chatzeile und Systembefehle. Standardmäßiger Eintrag auf 'localhost' gesetzt.*/ 
  chat.setBounds(0,470,500,30);     //*Grenzen für die Anzeige der Chatzeile*/ 
  add(chat);       //*Einfügen*/ 
  chat.addActionListener(this);     //*Die Chatzeile muß auf Eingaben reagieren können*/ 

  netz = new Netzwerk(); 
 } 

//**Methode um Text in die TextArea für Gegner- / Systemmeldungen einzufügen.*/ 

 public void schreib(String s) { 
  antwort.append(s+"\r\n");      //*Jeweiligen Text anzeigen, den nächsten aber erst eine Zeile später beginnen ('\r\n' bewirkt einen Zeilenvorschub).*) 
 } 

//**Methode für Spielende und Neustart.*/ 

 public void Spielende() { 

  s.GameOverFlag=true;      //*Bedingungen für Spielende sind in Spiel erfüllt worden.*/ 
  s.validate(); 
  s.repaint();       //*Canvas aus Spiel nochmal zeichnen.*/ 

  remove(k);       //*Anzeige der Klassen Kanone und Infozeile löschen.*/ 
  remove(i); 

  Label e = new Label("Möchten Sie noch einmal spielen?",Label.CENTER); //*Label mit entsprechender Aufschrift, passender Schrift und Größe an definierter Stelle einfügen.*/ 
  e.setFont(new Font("Helvitica",Font.BOLD,24)); 
  e.setBounds (0, 300, 499,50); 
  add(e); 
  
  Button b1 = new Button("Ja");      //*Zwei neue Buttons, um noch einmal zu spielen, oder aber aufzuhören.*// 
  Button b2 = new Button("Nein"); 
  b1.setBounds(0,350,250,50); 
  b2.setBounds(250,350,250,50); 

  b1.addActionListener(this);      //*Buttons müssen auf Eingabe warten*/ 
  b2.addActionListener(this); 
  add(b1);         //*Einfügen der beiden Buttons.*/ 
  add(b2); 
  
  validate(); 
  repaint();        //*Komplett noch einmal anzeigen.*/ 
  resize(500,501); 
 } 

//**Methode, die die gesamten in dieser Klasse auftretenden ActionListener verarbeitet.*/ 

 public void actionPerformed(ActionEvent e) { 
  if  ((e.getActionCommand()).equals("Ja")) {     //*Wenn der Button 'Ja' angeklickt wurde, komplett neues Spielfeld anzeigen.*/ 
   removeAll();       //*alles weg*/ 
   init();        //*init-Methode*/ 
   resize(500,500); 
   validate(); 
   repaint();       //*neu zeichnen*/ 
   return; 
  } 
  
  if  ((e.getActionCommand()).equals("Nein")) {    //*Wenn der Button 'Nein' angeklickt wurde, gesamtes Java-System verlassen.*/ 
								 System.exit(0); 
  } 

  String st = e.getActionCommand();      //*Importieren der Befehle aus der Chatzeile.*/ 
  
  if (!netz.bereit) {        //*Wenn die Verbindung zum Gegner noch nicht steht, folgende Schleife.*/ 
   String gegnername =st;       //*'gegnername' ist der im Textfeld eingegebene String*/ 
   schreib ("Versuche verbindung herzustellen. Gegner: "+st);   //*Entsprechender Text mit gewünschtem Spielgegner wird in der TextArea angezeigt.*/ 

   try {        //*Sollte kein Gegner sich mit diesem Rechner verbinden lassen wollen...*/ 
	netz.verbinde_mit(gegnername,4444);   //*(4444 ist eine beliebige Portnummer)*/ 
   } catch(IOException f) { 
	schreib("Konnte Computer nicht miteinander verbinden ! "+f.getMessage() ); //*...wird dies angezeigt.*/ 
	return; 
   } 

   schreib ("Verbindung steht!");     //*Ansonsten dies.*/ 
   t = new Kontrolle(); 
   t.ini(netz,this, s);       //*Aufruf der ini-Methode aus Kontrolle.*/ 
   enemycontrol = new Thread(t);     //*Neuer Thread der sich im Hintergrund um Meldungen des Gegners kümmert (entspricht unserer Kontrolle).*/ 
   enemycontrol.setDaemon(false);     //*Daemonthreadstatus*/ 
   enemycontrol.start();      //*Thread wird gestartet*/ 
   chat.setText("");       //*Chatzeile leer machen.*/ 
   s.dran = netz.bin_ich_server(); 
   if (s.dran) netz.sende("%"+i.Gebläse);    //*Windwert des Servers wird als Wert für das gesamte Spiel gesetzt.*/ 
   return; 
  } 
  netz.sende("$"+st);       //*Wenn die Eingabe in der Chatzeile kein Systembefehl ist, wird einfach nur an den Gegner gesendet - Chat.*/ 
 } 
} 
