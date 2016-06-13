import java.awt.*;

import javax.swing.ImageIcon;

public class PowerUp extends Engine{

	private double x;
	private double y;
	private int r;
	private int type;
	private Color color1;
	private Image doritosBonus;
	private Image bonus1;
	private Image bonus2;
	private Image bonusHP;
	
	public double getx() { return x;}
	public double gety() { return y;}
	public double getr() { return r;}
	/**
	 * Konstruktor powerupa
	 */
	
	public PowerUp(int type, double x, double y){
		this.type = type;
		this.x = x;
		this.y = y + 50;
		doritosBonus = new ImageIcon(Config.getProperties().getProperty("doritos")).getImage();
		bonus1 = new ImageIcon(Config.getProperties().getProperty("bonus1")).getImage();
		bonus2 = new ImageIcon(Config.getProperties().getProperty("bonus2")).getImage();
		bonusHP = new ImageIcon(Config.getProperties().getProperty("bonusHP")).getImage();
		
		if(type == 1) {
			color1 = Color.PINK;
			r = 10;
		}
		if(type == 2){
			color1 = Color.YELLOW;
			r = 10;
		}
		if( type == 3){
			color1 = Color.YELLOW;
			r = 10;
		}
		if( type == 4){
			r = 10;
		}
	}
	public int getType() { return type; }
	
	/**
	 * aktualizuje polozenie power upa
	 */
	public boolean update() {
		y += 2;
		
		if(y > GamePanel.HEIGHT + r){
			return true;
		}
		
		return false;
	}
	/**
	 * rysuje power upa
	 */
	public void draw(Graphics2D g){
		/*g.setColor(color1);
		g.fillRect((int) x - r, (int) y - r, 2 * r, 2 * r);
		g.setStroke(new BasicStroke(3));
		g.setColor(color1.darker());
		g.drawRect((int) x - r, (int) y - r, 2 * r, 2 * r);
		g.setStroke(new BasicStroke(1));
		*/
		if(type == 1) g.drawImage(bonusHP, (int) x - r, (int) y , null);
		if(type == 2) g.drawImage(bonus1, (int) x - r , (int) y , null);
		if(type == 3) g.drawImage(bonus2, (int) x - r, (int) y , null);
		if(type == 4) g.drawImage(doritosBonus, (int) x - r, (int) y , null);
	}
}