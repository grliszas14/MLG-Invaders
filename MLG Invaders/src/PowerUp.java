import java.awt.*;

import javax.swing.ImageIcon;

public class PowerUp extends Engine{

	/**
	 * Coordinate x
	 */
	private double x;
	/**
	 * Coordinate y
	 */
	private double y;
	/**
	 * Radius of power up
	 */
	private int r;
	/**
	 * Type of power up
	 */
	private int type;
	/**
	 * Image of dorito's bonus
	 */
	private Image doritosBonus;
	/**
	 * Image of basic bonus
	 */
	private Image bonus1;
	/**
	 * Image of improved bonus
	 */
	private Image bonus2;
	/**
	 * Image of HP bonus
	 */
	private Image bonusHP;
	
	/**
	 * x getter
	 */
	public double getx() { return x;}
	/**
	 * y getter
	 */
	public double gety() { return y;}
	/**
	 * r getter
	 */
	public double getr() { return r;}
	/**
	 * Constructor of power up
	 * @param type type of power up
	 * @param x coordinate x
	 * @param y coordinate y
	 */
	public PowerUp(int type, double x, double y){
		this.type = type;
		this.x = x;
		this.y = y + 50;
		doritosBonus = new ImageIcon(Config.getProperties().getProperty("doritos")).getImage();
		bonus1 = new ImageIcon(Config.getProperties().getProperty("bonus1")).getImage();
		bonus2 = new ImageIcon(Config.getProperties().getProperty("bonus2")).getImage();
		bonusHP = new ImageIcon(Config.getProperties().getProperty("bonusHP")).getImage();
		r=10;
	}
	public int getType() { return type; }
	
	/**
	 * Updates position of power up
	 */
	public boolean update() {
		y += 2;
		
		if(y > GamePanel.HEIGHT + r){
			return true;
		}
		
		return false;
	}
	/**
	 * Method drawing power-ups
	 * @param g to draw
	 * @param factorWidth to know how to resize width
	 * @param factorHeight to know how to resize height
	 */
	public void draw(Graphics2D g, double factorWidth, double factorHeight){
		if(type == 1) g.drawImage(bonusHP, (int) ((x - r)*factorWidth) , (int) (y*factorHeight) , (int) (25*factorWidth), (int) (28*factorHeight), null);
		if(type == 2) g.drawImage(bonus1, (int) ((x - r)*factorWidth) , (int) (y*factorHeight) , (int) (25*factorWidth), (int) (28*factorHeight), null);
		if(type == 3) g.drawImage(bonus2, (int) ((x - r)*factorWidth) , (int) (y*factorHeight) , (int) (25*factorWidth), (int) (28*factorHeight), null);
		if(type == 4) g.drawImage(doritosBonus, (int) ((x - r)*factorWidth) , (int) (y*factorHeight) , (int) (25*factorWidth), (int) (28*factorHeight), null);
	}
}