package sara;
/**
 * Spieler.java
 *
 *
 * Created: 1999
 *
 * @author Sahara
 * @version 1.0  BETA
**/
 
public class Spieler{

	private String Name;
	private int Adresse;
	private int Punktzahl;
  	private Spielbereich spielbereich;

 	public Spieler(String Name,int Adresse,Spielbereich spielbereich){
	   this.Name = Name;
	   this.Adresse = Adresse;
     	this.spielbereich = spielbereich;
	}

  /**
  * schieﬂt
  */
  public Kugel schiessen(){
	  return	spielbereich.schiessen();
  }

}