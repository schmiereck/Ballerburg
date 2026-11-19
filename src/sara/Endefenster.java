/**
 * Endefenster.java
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


	public class Endefenster extends Dialog implements ActionListener{

 	private Button OK ,Abbrechen;

 	public Endefenster(Frame Fenster, String Titel ,boolean modal){
    	super(Fenster, Titel, modal);
		setLayout(new BorderLayout());
		Abbrechen = new Button("Abbrechen");
      Abbrechen.addActionListener(this);
		add(Abbrechen,"South");
		OK = new Button("OK");
      OK.addActionListener(this);
		add(OK,"South");
 	}

 	public void actionPerformed(ActionEvent e){
    	this.setVisible(false);
 	}



} // Ende Klasse Endefenster