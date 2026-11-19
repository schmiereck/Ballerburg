package guderian;
import java.io.*; 

//**Thread der sich um Gegnermeldungen kümmert.*/ 

public class Kontrolle implements Runnable { 

 Netzwerk netz;      //*Variable netz vom Typ Netzwerk.*/ 
 Baller ausg;      //*Variable aus vom Typ Baller.*/ 
 Spiel sp;       //*Variable sp vom Typ Spiel.*/ 
  
//**Methode, die aufegrufen werden muß, um die Referenzen des Hauptprogramms zu übergeben.*/ 

 public void ini (Netzwerk netz, Baller ausg, Spiel sp) { 
  this.netz=netz; 
  this.ausg=ausg; 
  this.sp = sp; 
 } 
  
//**run-Methode unseres Meldungen-Threads.*/ 

 public void run() { 
  String st=""; 
  while (true) { 
   try {      //*Warten auf Reaktionen vom Netzwerk.*/ 
	while ((st=netz.empfange())=="") { 
	 try { 
	  Thread.sleep(10);  //*alle 10ms nachgucken, ob was anliegt.*/ 
	 } 
	 catch(InterruptedException f) { 
	 } 
	} 
   } catch (IOException e) { 
	ausg.schreib("Fehler beim Datenempfang!"); 
	System.exit(1); 
   } 
   if (st.charAt(0)=='$') ausg.schreib(st.substring(1,st.length()));   //*Wenn im intern gesendeten String eines dieser Zeichen vorangestellt ist, ...*/ 
   if (st.charAt(0)=='~') ausg.k.ausfuehren(st); 
   if (st.charAt(0)=='%') { 
	ausg.i.Gebläse=(new Integer(st.substring(1,st.length()))).intValue(); //*...werden diese Methoden aufgerufen.*/ 
	ausg.i.Wind.setText(""+ausg.i.Gebläse); 
	ausg.i.validate(); 
	ausg.i.repaint(); 
   } 
  } 
 } 
} 
