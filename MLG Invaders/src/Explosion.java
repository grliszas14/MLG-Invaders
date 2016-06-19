import java.awt.*;
/**
 * Class of explosions effect after shooting bomb
 */
public class Explosion extends Engine{
	/**
	 * Coordinate x
	 */
	private double x;
	/**
	 * Coordinate y
	 */
	private double y;
	/**
	 * Starting radius of explosion
	 */
	private int r;
	/**
	 * Maximum radius of explosion
	 */
	private int maxRadius;
	/**
	 * Constructor of explosion, setting parameters
	 * @param x
	 * @param y
	 * @param r
	 * @param max maxRadius
	 */
	public Explosion(double x, double y, int r, int max){
		this.x = x;
		this.y = y;
		this.r = r;
		this.maxRadius = max;
		
	}
	/**
	 * Method updating position of explosion
	 */
	public boolean update(){
		r++;
		if(r >= maxRadius){
			return true;
		}
		return false;
	}
	/**
	 * Method drawing explosion
	 * @param g
	 * @param factorWidth factor to resize explosion
	 * @param factorHeight factor to resize explosion
	 */
	public void draw(Graphics2D g, double factorWidth, double factorHeight){
		g.setColor(new Color(255,255,255,128));
		g.setStroke(new BasicStroke(3));
		g.drawOval((int) ((x-r)*factorWidth), (int) ((y-r)*factorHeight), (int) (2 * r * factorWidth), (int) (2 * r * factorHeight));
	}
}