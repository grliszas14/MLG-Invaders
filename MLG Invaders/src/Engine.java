import java.awt.Graphics2D;
/**
 * Class that enemy, bullet, bomb, explosion inherit from
 */
public class Engine {
	/**
	 *  Coordinate x
	 */
	private double x;
	/**
	 * Coordinate y
	 */
	private double y;
	/**
	 * Radius
	 */
	private int r;
	
	/**
	 * Method to draw
	 * @param g
	 */
	public void draw(Graphics2D g){}
	/**
	 * Method to update the state of object
	 * @return
	 */
	public boolean 	update() { return false;}
	
	/**
	 * x getter
	 * @return x
	 */
	public double getx() { return x;}
	/**
	 * y getter
	 * @return y
	 */
	public double gety() { return y;}
	/**
	 * radius getter
	 * @return r
	 */
	public double getr() { return r;}
	
	
}