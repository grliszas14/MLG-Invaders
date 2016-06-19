import java.awt.*;

public class Bullet extends Engine{
	/**
	 * Coordinate x
	 */
	private double x;
	/**
	 * Coordinate y
	 */
	private double y;
	/**
	 * Radius of bullet
	 */
	private int r;
	
	/**
	 * Shift in x axis
	 */
	private double dx;
	/**
	 * Shift in y axis
	 */
	private double dy;
	/**
	 * Angle of bullet
	 */
	private double rad;
	/**
	 * Speed of bullet
	 */
	private double speed;
	/**
	 * Color of bullet
	 */
	private Color color1;
	
	/*
	* Constructor of bullet class, setting position, color, size and speed of bullet
	* @param angle
	* @param x
	* @param y 
	*/
	public Bullet(double angle, int x, int y){
		this.x = x;
		this.y = y;
		r = 5;
		
		rad = Math.toRadians(angle);
		speed = (double) Integer.parseInt(Config.getProperties().getProperty("BulletSpeed"));
		dx = Math.cos(rad) * speed;
		dy = Math.sin(rad) * speed;
		
		
		color1 = Color.YELLOW;
	}
	
	
	/*
	* x getter
	*/
	public double getx() { return x;}
	
	/*
	* y getter
	*/
	public double gety() { return y;}
	/*
	* radius getter
	*/
	public double getr() { return r;}
	
	/*
	* Updating position of a bullet
	*/
	public boolean update(){
		x += dx;
		y += dy;
		
		if(x < -r || x > (GamePanel.WIDTH + r) ||
				y <-r || y > GamePanel.HEIGHT + r) {
			return true;
		}
		return false;
	}
	
	/*
	* Method drawing a bullet
	*/
	public void draw(Graphics2D g, double factorWidth, double factorHeight){
		g.setColor(color1);
		g.fillOval( (int) ((x-r)*factorWidth),(int) ((y - r)*factorHeight) , (int) (2 * r*factorWidth), (int) (2 * r*factorHeight)) ;
	}
}