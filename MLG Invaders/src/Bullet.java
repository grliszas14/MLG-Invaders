import java.awt.*;

public class Bullet extends Engine{
	
	private double x;
	private double y;
	private int r;
	
	private double dx;
	private double dy;
	private double rad;
	private double speed;
	
	private Color color1;
	
	/*
	* Konstruktor
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
	* Metoda zwracajaca wspolrzedna x
	*/
	public double getx() { return x;}
	
	/*
	*Metoda zwracajaca wspolrzedna y
	*/
	public double gety() { return y;}
	/*
	*Metoda zwracajaca promien pocisku
	*/
	public double getr() { return r;}
	
	/*
	*Metoda aktualizujaca polo¿enie pocisku
	*/
	public boolean update(){
		x += dx;
		y += dy;
		
		if(x < -r || x > GamePanel.WIDTH + r ||
				y <-r || y > GamePanel.HEIGHT + r){
			return true;
		}
		return false;
	}
	
	/*
	* Metoda rysuj¹ca pocisk
	*/
	public void draw(Graphics2D g){
		
		g.setColor(color1);
		g.fillOval((int) (x-r),(int) (y - r) , 2 * r, 2 * r);
	}
}
