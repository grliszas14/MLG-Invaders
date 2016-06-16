import java.awt.*;

import javax.swing.ImageIcon;

public class Bomb extends Engine{

	private double x;
	private double y;
	private int r;
	private Color color1;
	private Color color2;
	
	public double getx() { return x;}
	public double gety() { return y;}
	public double getr() { return r;}
	
	private Image bombka;
	
	/**
	 * Tworzy bombe, jej grafike, ustawia jej rozmiar i polozenie
	 * @param x
	 * @param y
	 */
	public Bomb(double x, double y){
		bombka = new ImageIcon(Config.getProperties().getProperty("Bombka")).getImage();
		this.x = x;
		this.y = y + 50;
		
		color1 = Color.BLACK;
		color2 = Color.RED;
		r = 10;
	}
	
	/**
	 * zmienia polozenie bomby
	 */
	public boolean update() {
		y += 2;
		
		if(y > GamePanel.HEIGHT + r){
			return true;
		}
		
		return false;
	}
	
	/**
	 * rysuje bombe 
	 */
	public void draw(Graphics2D g, double factorWidth, double factorHeight){
		//g.setColor(color1);
		//g.fillOval((int) x - r, (int) y - r + 50, 2 * r, 2 * r);
		//g.setStroke(new BasicStroke(3));
		//g.setColor(color2);
		//g.drawOval((int) x - r, (int) y - r + 50, 2 * r, 2 * r);
		//g.setStroke(new BasicStroke(1));
		g.drawImage(bombka, (int) ((x - r)*factorWidth), (int) (y*factorHeight), (int) ( 25*factorWidth), (int) (19*factorHeight), null);
	}
	
}