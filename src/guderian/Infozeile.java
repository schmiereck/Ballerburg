package guderian;
import java.awt.*; 
import java.io.*; 
import java.awt.event.*; 
import java.beans.*; 
import java.util.*; 

//** Infozeile gibt dem Spieler Infos über die aktuelle Situation.*/ 

public class Infozeile extends Panel { 

 Baller b; 

 int PM1 = 1000;    //*Variablendeklaration*/ 
 int PM2 = 1000; 
 int KA1 = 2; 
 int KA2 = 2; 
 int KM1 = 10; 
 int KM2 = 10; 

 double ZG = Math.random()*100;  //* lustiger Windeinfluß, damit das Spielen auch Spaß macht!!*/ 
 int Gebläse = (int) ZG; 
 double ZR = Math.random(); 

 static int StartKanonenzahl = 2; 

 int[] kx1 = {3,5}; 
 int[] ky1 = {9,9}; 
 boolean [] kb1= {false, false}; 

 int[] kx2 = {44,42}; 
 int[] ky2 = {9,9}; 
 boolean [] kb2= {false, false}; 

 int SN = 1; 
 int PM = PM1; 
 int KA = KA1; 
 int KM = KM1; 
  
 Label Spieler; 
 Label Pulverbestand; 
 Label Kanonenanzahl; 
 Label Kugelbestand; 
 Label Wind; 

//**Übergabe der passenden Variablen an die Hauptklasse 'Baller'.*/ 

 public Infozeile(Baller b1) { 
  b = b1; 
 } 

//**Anzeige definiert das Layout der Infozeile in der linken unteren Ecke des Applets / der Applikation.*/ 

 public void Anzeige () { 
  
  if (ZR < 0.5) 
  Gebläse  = Gebläse * (-1); 
  

  setLayout (new GridLayout (5,2));    //*verschiedene Layoutstile*/ 
  add (new Label ("Spieler")); 
  Spieler=new Label (String.valueOf (SN)); 
  add(Spieler); 
  add(new Label ("Pulverbestand")); 
  Pulverbestand=new Label (String.valueOf (PM)); 
  add(Pulverbestand); 
  add (new Label ("Kanonenanzahl")); 
  Kanonenanzahl=new Label (String.valueOf (KA)); 
  add(Kanonenanzahl); 
  add (new Label ("Kugelbestand")); 
  Kugelbestand=new Label (String.valueOf (KM)); 
  add(Kugelbestand); 
  add(new Label ("Windeinfluß")); 
  Wind = new Label (String.valueOf (Gebläse)); 
  add (Wind); 
  
 } 

//**Aus den anderen Klassen übergebene Werte in das Layout einfügen.*/ 

 public void WerteEinsetzen() { 
  Spieler.setText(String.valueOf(SN));   //*Alle Labels mit den gerade aktuellen Strings auffüllen...*/ 
  Pulverbestand.setText(String.valueOf(PM)); 
  Kanonenanzahl.setText(String.valueOf(KA)); 
  Kugelbestand.setText(String.valueOf(KM)); 
  validate();     //*... und natürlich hinterher neu zeichnen!*/ 
  repaint(); 
  
 } 

//**Methode, die sich darum kümmert, welcher Spieler gerade am Zug ist. Außerdem werden die jeweiligen Daten mit den passenden Variablennamen versehen.*/ 

 public void Spielerwechsel () { 
  
  if (SN ==1) {     //*Wenn der erste Spieler dran ist.*/ 
   PM2 = PM;     //*aktuelle Pulvermenge wird zu Pulvermenge des zweiten Spielers*/ 
   PM = PM1;    //*Pulvermenge des ersten Spielers wird zu aktueller Pulvermenge - mit dieser wird dann jeweils gerechnet.*/ 
   KA2 = KA; 
   KA = KA1; 
   KM2 = KM; 
   KM = KM1; 
  } 
  else { 
   PM1 = PM; 
   PM = PM2; 
   KA1 = KA; 
   KA = KA2; 
   KM1 = KM; 
   KM = KM2; 
  } 

  if ((KM1 < 1) && (KM2 < 1))  {  //*Spielende wenn alle Kugeln verschossen*/ 
   b.Spielende(); 
   System.out.println("Null"); 
  } 
  
  if ((PM1 < 1) && (PM2 < 1))  {  //*Spielende wenn alles Pulver verschossen*/ 
   b.Spielende(); 
   System.out.println("Null"); 
  } 

  WerteEinsetzen(); 
 } 
} 
