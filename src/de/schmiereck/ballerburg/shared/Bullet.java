package de.schmiereck.ballerburg.shared;

/**
 * <p>
 * 	TODO	docu
 * </p> 
 * 
 * @author smk
 * @version <p>30.08.2003:	created</p>
 */
public class Bullet 
extends Part
{
	private double	mx;
	private double	my;

	/**
	 * 	Constructor.
	 * 
	 * @param posX
	 * @param posY
	 * @param sizeX
	 * @param sizeY
	 */
	public Bullet(String name, int posX, int posY, double speed, double angel)
	{
		super(name, posX, posY, 8, 8);

		double a = Math.toRadians(angel);

		this.mx = Math.cos(a) * (speed);
		this.my = Math.sin(a) * (speed);

		this.movePosX(Math.cos(a) * 19);
		this.movePosY(Math.sin(a) * 19);
	}
	/**
	 * @param milliseconds
	 */
	public void calcMove(int milliseconds)
	{
		// Schwerkraft:
		this.my -= 0.00005 * milliseconds;
		
		// Luftwiderstand:
		this.mx *= 1.0 - (0.00001 * milliseconds);

		this.movePosX((this.mx * (milliseconds * 1.0)));
		this.movePosY((this.my * (milliseconds * 1.0)));
	}

}
