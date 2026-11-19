
/**
 * Eingabefenster.java
 *
 *
  * Created: 1999
 *
 * @author Sahara
 * @version 1.0  BETA
**/

package sara;
 import java.awt.*;
 import java.awt.event.*;


 public class Eingabefenster extends Dialog
 implements ActionListener{

 private int Windgeschwindigkeit,Kugelvorrat,Pulvervorrat;
 private Integer Winkel,Pulververbrauch;      // vom Spieler anzugeben
 private String Windrichtung;
 private TextField Winkel_Textfeld,Pulver_Textfeld;
 private Button OK;
 private Kanone kanone;

 /**
* AWT:
* Es wird eine Benutzeroberfläche mit Hilfe der AWT-Klassen erstellt
* GrindLayout: 2 Spalten,7 Zeilen,
* Beispiel:
*  Windgeschwindigkeit:					10
*  Windrichtung:							Süd
*  Pulvervorrat:							6
*  Kugelvorrat:							2
*  Winkel:									... (vom Benutzer)
*  Pulvermenge:							... (vom Benutzer)
* Ok-Button als Abschluss
*
**/

	public Eingabefenster(Frame Fenster, String Titel,
                        boolean modal, Kanone kanone){
     super(Fenster, Titel, modal);
     this.kanone = kanone;
	   setLayout(new GridLayout(7,2));
	   add(new Label("Windgeschwindigkeit"));
	   add(new Label(Integer.toString(Windgeschwindigkeit)));
	   add(new Label("Windrichtung"));
	   add(new Label(Windrichtung));
	   add(new Label("Pulvervorrat"));
	   add(new Label(Integer.toString(Pulvervorrat)));
     add(new Label("Kugelvorrat"));
     add(new Label(Integer.toString(Kugelvorrat)));
	   add(new Label("Winkel"));
	   Winkel_Textfeld = new TextField("45",3);
	   add(Winkel_Textfeld);
	   add(new Label("Pulvermenge"));
	   Pulver_Textfeld = new TextField("10",3);
	   add(Pulver_Textfeld);
	   OK = new Button("OK");
	   OK.addActionListener(this);
	   add(new Button("OK"));
	}

/**
*Wenn der OK- Button gedrückt wird, wird eine neue Kugel erzeugt.
*Falls einzugebene Zahlen nicht vorhanden oder zu groß:
*Default: Winkel: 45    und     Pulvermenge:  10
*
**/

	public void actionPerformed(ActionEvent e){
      if(Winkel_Textfeld.getText() == "")
		     Winkel_Textfeld.setText("45");
		  if(Pulver_Textfeld.getText() == "")
		     Pulver_Textfeld.setText("10");
		  try {
         Winkel = Integer.valueOf(Winkel_Textfeld.getText());
         Pulververbrauch = Integer.valueOf(Pulver_Textfeld.getText());
		  }
		  catch (NumberFormatException i){
		     Winkel = new Integer(45);
			   Pulververbrauch = new Integer(10);
		  }
      if (Pulververbrauch.intValue() > Pulvervorrat){
         Pulververbrauch = new Integer(Pulvervorrat);
      }
      this.setVisible(false);
     kanone.erzeuge_Kugel(Pulververbrauch.intValue(),Winkel.intValue());
	} // Ende Methode actionPerformed

 } //Ende Klasse Eingabefenster
