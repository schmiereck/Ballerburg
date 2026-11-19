package guderian;
import java.io.*; 
import java.awt.*; 
import java.awt.event.*; 

//**Diese Klasse enthält Möglichkeiten, die Pulvermenge, den Schußwinkel und die richtige Kanone auzuwählen.*/ 

public class Kanone extends Panel implements ActionListener { 

 Spiel Sp = null; 
 TextField Pulver; 
 TextField Winkel; 
 TextField Nummer; 
 boolean unterwegs; 
 int Nr; 
 Baller Haupt; 

//**Initialisierungsmethode dieser Klasse.*/ 

 public void initialisiere (Spiel sp, Baller z) {    //*Erstellen Methode, erwartet Variable vom Typ Spiel gespeichert unter sp*/ 
  Sp=sp;       //*Übergibt sp an Sp*/ 
  unterwegs=false;      //*Grundwert*/ 
  Haupt = z; 

  setLayout (new GridLayout (4,2));    //*Einfügen Eingabekästchen*/ 
  add (new Label ("Pulvermenge")); 
  Pulver = new TextField ("50"); 
  add (Pulver); 
  add (new Label ("Kanonenwinkel")); 
  Winkel = new TextField ("45"); 
  add (Winkel); 
  add (new Label ("Kanonennummer")); 
  Nummer = new TextField ("1"); 
  add (Nummer); 
  add (new Label ("")); 
  Button b = new Button ("Schuß"); 
  b.addActionListener(this); 
  add (b); 

 } 
  
//**Schußausführung mit Werten aus den TextFields. Dazu nötige Formatkonvertierungen.*/ 

 public void ausfuehren(String st) { 

  try { 
   Kugel k = new  Kugel ( Sp, this); 
  
   Nr =(new Integer (st.substring(1,2))).intValue()-1; 
   k.beta=(new Double (st.substring(2,st.indexOf("!")))).doubleValue();  //*Umwandlung String in Werte für Rechnung*/ 
   k.v=(new Double (st.substring(st.indexOf("!")+1,st.length()))).doubleValue(); 

   if (Sp.inf.SN==1) {       // *Wenn Spieler 1 dran ist*/ 
	if (Sp.inf.kb1 [Nr] == false) {     //* Wenn keine Verblockung von ausgewählter Wumme*/ 
	 k.x0=Sp.inf.kx1[Nr];     //* Auswahl der jeweiligen Kanone an k.x0*/ 
	 k.y0=Sp.inf.ky1[Nr]; 
	 unterwegs=true; 
	 k.start(); 
	} 
   } else {         //* Sonst muß Spieler 2 dran sein*/ 
	if (Sp.inf.kb2 [Nr] == false) {     //* Wenn keine Verblockung von ausgewählter Wumme*/ 
	 k.x0=Sp.inf.kx2[Nr];     //* Auswahl der jeweiligen Kanone an k.x0*/ 
	 k.y0=Sp.inf.ky2[Nr]; 
	 unterwegs=true; 
	 k.start(); 
	} 
   } 
   Sp.dran=!Sp.dran;       //*Spielerwechsel*/ 
  } 
  catch (NumberFormatException e2) {      //* Falls böse Benutzer keine Zahlenwerte schreiben wollen*/ 
   Pulver.setText("50"); 
   Winkel.setText("45"); 
   Nummer.setText("1"); 
  } 
  catch (ArrayIndexOutOfBoundsException e3) {     //*Falls böse Benutzer die falsche Kanone nehmen!*/ 
   Haupt.antwort.append ("\r\n"+"Bitte gib' eine gültige Kanonennummer ein!"); 
  } 
 } 

//**Methode, die den Schuß selbst ausführt und die gleichen Werte an den Gegner sendet, damit dieser auch den Schuß berechnen kann.*/ 

 public void actionPerformed(ActionEvent e) { 
  if  ((!unterwegs)&&(Sp.dran)) { 
  
   String st = "~"+Nummer.getText()+Winkel.getText()+"!"+Pulver.getText(); 
   Haupt.netz.sende(st); 
   ausfuehren(st); 
  } 
 } 
} 
