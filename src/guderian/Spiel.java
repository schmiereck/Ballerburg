package guderian;
import java.awt.*; 
import java.awt.Graphics; 
import java.awt.event.*; 
import java.awt.Image; 
import java.applet.Applet; 

//**Spiel ist unser Spielfeld, das als Canvas erzeugt wird und dann im Applet, bzw. der Applikation Baller angezeigt wird.*/ 

public class Spiel extends Canvas { 
 int [] [] Feld = new int [50] [30];      //*Erstellen eines Array-Objektes zum Speichern des Spielfeldes.*/ 
 int kx = -1;        //*Kugelposition x*/ 
 int ky = -1;        //*Kugelposition y*/ 
 boolean dran = false; 
  
 Infozeile inf;        //*Zwischenspeicherung in Spiel, weil eigentlich alle Objekte einen Verweis hierauf haben...*/ 
 Baller Main;         //...und nun einfach auf die Infozeile links unten zugreifen können!*/ 

 boolean GameOverFlag = false;      //*Wenn true, wird in die Mitte des Schirmes ein dickes 'Game Over gepackt'*/ 
 Image Bild = null;        //*Variable Bild vom Typ Image ist leer.*/ 

  public Dimension getPreferredSize() {     //*Größe Fensterbereich für Spiel*/ 
  return new Dimension(500,300); 
 } 

  public Dimension getMinimumSize() {     //*Größe Fensterbereich für Spiel*/ 
  return new Dimension(500,300); 
 } 

//**Initialisierungsmethode für unser Spielfeld.*/ 

 public void init (Infozeile in, Baller b) { 
  int i, j,k; 
  inf = in; 
  Main = b; 
  
  for ( i = 0 ; i < 50 ; i++) {     //*Array durchnummerieren für Boden*/ 
   Feld [ i ] [ 29 ] = 1 ; 
  } 
  for ( i = 0 ; i < 50 ; i++) {     //*Array durchnummerieren für Gras*/ 
   Feld [ i ] [ 28 ] = 2 ; 
  } 
  for (i=3; i < 10; i++)  {     //*Array durchnummerieren für Burg links*/ 
   for (j=22; j<29;j++)  { 
	Feld [i] [j] = 3; 
   } 
  } 
  for ( i =40 ; i < 48 ; i++) {     //*Array durchnummerieren für Burg rechts*/ 
   for (j=22; j<29; j++) { 
	Feld [ i ] [j] = 3; 
   } 
  } 
  for (i=20; i<30; i++) { 
   for (j=19; j<29; j++) {    //*Array durchnummerieren für Berg*/ 
	Feld [i] [j] = 4; 
   } 
  } 

  Feld [6] [27] = 5;       //*Array für Thronsaal*/ 
  Feld [44] [27] = 5; 

  for (k=0;k<Infozeile.StartKanonenzahl;k++) {    //* Array für Kanonen*/ 
   Feld [inf.kx1[k]][30-inf.ky1[k]] = 6;    //* 30, weil dann Kugel 1 Feld über Kanone startet und eigene Kanone nicht zerstört*/ 
   Feld [inf.kx2[k]][30-inf.ky2[k]] = 6; 
  } 
 } 
  
//**Paint-Methode, die endlich unsere Spiel-Klasse anzeigt.*/ 

 public void paint (Graphics g1) { 
  if (Bild==null)       //*Wenn das Bild tatsächlich leer ist, dann wird es neu gezeichnet.*/ 
   Bild = createImage(500,300);    //*Größe*/ 
  
  Graphics g = Bild.getGraphics();     //*Doppelpufferung der Grafik, damit kein 'Flackern' auftritt*/ 

  int bx, by;       //*Variablen für Bildpunkte*/ 
  for ( bx = 0 ; bx < 50 ; bx ++) {    //*Schleife für Arraydurchzählen*/ 
   for ( by = 0 ; by < 30 ; by ++) { 
	if (Feld [bx] [by] == 0) {   //*Füllen 0 = Hellblaues Quadrat ==> Himmel, Hintergrund, Standardfarbe*/ 
	 g.setColor (new Color (45, 185, 200)); 
	 g.fillRect (bx * 10, by * 10, 10, 10); 
	} 
	if (Feld [bx] [by] == 1) {   //*Füllen 1 = Grafik holen ==> Boden, wird nicht gelöscht*/ 
	 g.drawImage (Main.Boden, bx * 10, by * 10, this); 
	} 
	if (Feld [bx] [by] == 2) {   //*Füllen 2 = Grafik holen ==> Gras*/ 
	 g.drawImage(Main.Gras, bx * 10, by * 10, this); 
	} 
	if (Feld [bx] [by] == 3) {   //*Füllen 3 = Grafik holen ==> Burgen*/ 
	 g.drawImage (Main.Burg, bx * 10, by * 10, this); 
	} 
	if (Feld [bx] [by] ==4) {   //*Füllen 4 = Grafik holen ==> Berg*/ 
	 g.drawImage (Main.Berg, bx * 10, by * 10, this); 
	} 
	if (Feld [bx] [by] ==5) {   //*Füllen 5 = Grafik holen ==> Thronsaal, bei Treffer Spielende*/ 
	 g.drawImage (Main.Thron, bx * 10, by * 10, this); 
	} 
	if (Feld [bx] [by] ==6) {   //*Füllen 6 = Grafik holen ==> Kanone*/ 
	 g.drawImage (Main.Kanone, bx * 10, by * 10, this); 
	} 
   } 
  } 
  
  if ( kx > -1) {      //* Kugel zeichnen, falls nötig*/ 
   g.setColor (Color.black); 
   g.fillOval (kx * 10, ky * 10 , 8, 8);    //* Zeichnen der Kanonenkugel*/ 
  } 

  if  (GameOverFlag) {      //* GAMEOVER zeichnen, falls nötig*/ 
   g.setFont(new Font("Helvetica",Font.BOLD,40)); 
   g.setColor(Color.red); 
   g.drawString("GAME OVER",100,200); 
  } 

  g1.drawImage(Bild,0,0,this);               //* Nach Abschluß aller Zeichnungen im Bild: Setze Bild auf Position (0,0);  'this' muß, ohne geht's leider nicht - ?*/ 
 } 

 public void update(Graphics g) {      //*komplett neu zeichnen*/ 
  paint(g); 
 } 
  
}