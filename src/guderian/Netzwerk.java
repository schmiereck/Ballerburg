package guderian;
import java.io.*; 
import java.net.*; 

/** Klasse Netzwerk kümmert sich um alle Netzwerkangelegenheiten.*/ 

public class Netzwerk { 

/** Sobald diese Variable true wird, besteht eine funktionierende Netzwerkanbindung */ 
  
 public boolean bereit =false; 
  
private Socket mvSocket = null; 
 private PrintWriter outclient, outserver = null; 
 private DataInputStream inclient,inserver; 
 Spiel ST; 

/** wenn Computer Server ist, ist diese Variable true, ansonsten false */ 

 private boolean bin_ich_server = false; 

/** Verbindet den Computer mit dem gewünschten Gegner-Computer.*/ 

 public void verbinde_mit(String name, int nummer) throws IOException { 

  String s; 
  
  try {         //*Versucht sich als Client zu installieren*/ 
   mvSocket = new Socket(name, nummer); 
   outclient = new PrintWriter(mvSocket.getOutputStream(), true); 
   inclient = new DataInputStream(mvSocket.getInputStream()); 
  } 
  catch (UnknownHostException e) { 
   System.err.println("Host nicht gefunden!"); 
   System.exit(1); 
  } 
  catch (IOException e) { 
  
   bin_ich_server = true;      //*Keinen Server gefunden? Dann selber Server.*/ 
   System.out.println("Computer ist selber Server"); 
   ServerSocket serverSocket = null; 

   try { 
	serverSocket = new ServerSocket(nummer); 
   } 
   catch (IOException f) { 
	System.err.println("Leider ist der port " +nummer+ " belegt :-("); 
	System.exit(1); 
   } 
  
   Socket clientSocket = null; 

   try { 
	clientSocket = serverSocket.accept(); 
   } 
   catch (IOException g) { 
	System.err.println("Accept failed."); 
	System.exit(1); 
   } 

   outserver = new PrintWriter(clientSocket.getOutputStream(), true); 
   inserver = new DataInputStream(clientSocket.getInputStream()); 
   outserver.println("Verbindung!"); 

   bereit = true;       //*Teste die Verbindung anhand eines Teststrings.*/ 
   return;        // *Server eingerichtet. Routine verlassen.*/ 
  } 

  bin_ich_server = false;       //* Wenn Server bereits vorhanden, Clientstatus übernehmen!*/ 
  System.out.println("Computer ist Client"); 
  s=inclient.readLine(); 

  if (s==null) System.out.println("Fehler!");     //*Funktionalität anhand eines Teststrings überprüfen.*/ 
  bereit=true; 
 } 

/** Sendet Daten zu dem vorher in "verbinde_mit(String,int)" angebenen Computer */ 

 public void sende(String info) { 
  
  if (bin_ich_server == true)  {      //*sendet nur, wenn Netzwerkverbindung steht*/ 
   if (outserver!=null) outserver.println(info); 
  } else 
   if (outclient!=null) outclient.println(info); 
	} 
  
/** Empfängt Daten zu dem vorher in"verbinde_mit8String,int)" angebeben Computer. */ 

 public String empfangedaten() throws IOException { 
  
  String s; 
  if (bin_ich_server == true) 
   return inserver.readLine(); 
  else 
   return inclient.readLine(); 
 } 

/** Wenn Daten vom Netzwerk vorhanden sind, werden diese übergeben. */ 

public synchronized String empfange() throws IOException { 
  String s= ""; 
  if (bin_ich_server) { 
   if (inserver.available()>0) s= inserver.readLine(); 
  } 
  else 
   if (inclient.available()>0) s= inclient.readLine(); 
  return s; 
 } 

/**Wenn diese Variable true zurückliefert, ist der Computer Server, ansonsten Client */ 

 public boolean bin_ich_server() { 
  return bin_ich_server; 
 } 
} 
