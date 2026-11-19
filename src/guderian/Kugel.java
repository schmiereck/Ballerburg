package guderian;
import java.lang.Thread; 

//**Wichtigster Thread in unserem Spiel, der die Flugbahn der Kanonenkugel berechnet und an alle anderen Klassen weitergibt, damit diese mit den Ergebnissen arbeiten können.*/ 

public class Kugel extends Thread { 
 Spiel Sp;     //* Variable Sp vom Typ Spiel erzeugen*/ 
 Kanone Ka;    //* Variable Ka vom Typ Kanone erzeugen*/ 
 double beta = 0; 
 double v = 0; 
 int x0; 
 int y0; 
  
 public Kugel (Spiel s, Kanone k) { 
  Sp = s; 
  Ka = k; 
 } 

//**Startmethode der Kugel*/ 

 public void run () {       //*Starten Thread*/ 
  
  int k; 
  int x; 
  int y; 
  double t = 0.0; 

  while (true) {      //*Ausführen bis der Arzt kommt*/ 
   t = t + 0.001; 

   if (Sp.inf.SN==1) 
	x = x0+(int) (v*0.25 * Math.cos (beta*Math.PI/180)* t - 0.025 * Sp.inf.Gebläse * t * t  ); 
   else 
	x = x0-(int) (v*0.25 * Math.cos (beta*Math.PI/180)* t + 0.025 * Sp.inf.Gebläse * t * t); 

   y = y0+(int) (v*0.25 * Math.sin (beta*Math.PI/180) * t - 0.5* 9.81 * t * t); 
  
   Sp.kx = x;      //*x übergeben an kx in Klasse Spiel*/ 
   Sp.ky = 29 - y;      //*y übergeben an ky in Klasse Spiel*/ 

   if ((x >49) || (x < 0) || (y > 29 ) || (y < 0)) break;   //*Der Arzt kommt, also Schluß mit lustig*/ 
  
   if (Sp.Feld[x][29-y] == 5)     //* Bedingung für Spielende durch Thronsaaltreffer*/ 
	Sp.Main.Spielende(); 

   if (Sp.Feld[x][29-y] == 6) {    //* Kanonenarray nach Treffer ändern*/ 
	if ( Sp.inf.kx1 [0] == x)    //* Wenn Kugel an x-Position von Kanone 1 von Spieler 1*/ 
	 Sp.inf.kb1[0] = true;   //* Kanone kapotttt*/ 
	if ( Sp.inf.kx1 [1] == x) 
	 Sp.inf.kb1[1] = true; 
	if ( Sp.inf.kx2 [0] == x) 
	 Sp.inf.kb2[0] = true; 
	if ( Sp.inf.kx2 [1] == x) 
	 Sp.inf.kb2[1] = true; 
   } 
  
   if (Sp.Feld[x][29-y]!=0) {     //* Setzen auf Hintergrund wenn nicht schon Hintergrund*/ 
	if (Sp.Feld[x][29-y] != 1) {   //* Außer Boden !!!*/ 
	 Sp.Feld[x][29-y]=0; 
	  break; 
	} 
   } 
  

   try {       //*Schlafen*/ 
	sleep(5); 
   } 
   catch (InterruptedException e) {       } 
  } 

  if (Sp.inf.kb1 [0] && Sp.inf.kb1 [1] == true)    //* Wenn einer keine Kanone mehr hat, ist das Spiel zu Ende!*/ 
   Sp.Main.Spielende (); 

  if (Sp.inf.kb2 [0] && Sp.inf.kb2 [1] == true) 
   Sp.Main.Spielende (); 

  Sp.kx = -1;       //*alte Startwerte für Kugelposition kx, ky*/ 
  Sp.ky = -1; 
  
  Sp.inf.PM-= v;       //* Pulververbrauch*/ 
  Sp.inf.KM-= 1;        //* eine Kugel weniger*/ 

  if (Sp.inf.SN ==1) {      //* Spielernummern wechseln*/ 
   Sp.inf.SN =2; 
  } 
  else  { 
   Sp.inf.SN =1; 
  } 

  Sp.inf.Spielerwechsel ();      //* Spielerwechsel aufrufen*/ 
  
  Sp.validate (); 
  Sp.repaint ();       //*alter Bildschirmzustand*/ 
  Ka.unterwegs=false;      //*neuer Schuß möglich*/ 
 } 
} 

