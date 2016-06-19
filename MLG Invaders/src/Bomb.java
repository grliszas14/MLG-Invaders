import java.awt.*;

import javax.swing.ImageIcon;

public class Bomb extends Engine{
	/**
	 * Coordinate x
	 */
	private double x;
	/**
	 * Coordinate y
	 */
	private double y;
	/**
	 * Radius of bomb
	 */
	private int r;

	/**
	 * x getter
	 */
	public double getx() { return x;}
	/**
	 * y getter
	 */
	public double gety() { return y;}
	/**
	 * radius getter
	 */
	public double getr() { return r;}
	/**
	 * Image of a bomb
	 */
	private Image bombka;
	
	/**
	 * Creates a bomb
	 * @param x
	 * @param y
	 */
	public Bomb(double x, double y){
		bombka = new ImageIcon(Config.getProperties().getProperty("Bombka")).getImage();
		this.x = x;
		this.y = y + 25;
		r = 10;
	}
	
	/**
	 * Updates position of a bomb
	 */
	public boolean update() {
		y += 2;
		
		if(y > GamePanel.HEIGHT + r){
			return true;
		}
		
		return false;
	}
	
	/**
	 * Method drawing a bomb
	 * @param g to draw bomb
	 * @param factorWidth variable to resize bomb
	 * @param factorHeight variable to resize bomb
	 */
	public void draw(Graphics2D g, double factorWidth, double factorHeight){
		g.drawImage(bombka, (int) ((x - r)*factorWidth), (int) ((y-r)*factorHeight), (int) ( 25*factorWidth), (int) (19*factorHeight), null);
	}
	
}