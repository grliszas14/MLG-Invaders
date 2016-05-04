import java.awt.*;

public class Enemy extends Engine{
	
	private double x;
	private double y;
	private int r;
	
	private double dx;
	private double dy;
	private double rad;
	private double speed;
	
	private int health;
	private int rank;
	private int type;
	
	private Color color1;
	
	private boolean ready;
	private boolean dead;
	
	/**
	* Konstruktor
	*/
	public Enemy( int type, int rank){
		this.type = type;
		this.rank = rank;
		
		if(type == 1) {
			color1 = Color.RED;
			if(rank == 1){
				speed = (double)Integer.parseInt(Config.getProperties().getProperty("EnemySpeed"));
				r = Integer.parseInt(Config.getProperties().getProperty("EnemyRadius"));
				health = Integer.parseInt(Config.getProperties().getProperty("EnemyHealth"));
								
				// metoda: GetProperties.getProperties().getProperty("EnemySpeed") 
				// w "..." podajesz klucz, zwraca wartoœæ jako String. Nasz plik jest jak s³ownik, tzn klucz=wartosc
				// Integer.parseInt zamienia Stringa na inta
				
			}
		}
		//faster, stronger enemy
		if(type == 2) {
			color1 = Color.BLUE;
			if(rank == 1){
				speed = (double)Integer.parseInt(Config.getProperties().getProperty("EnemySpeed2"));
				r = Integer.parseInt(Config.getProperties().getProperty("EnemyRadius2"));
				health = Integer.parseInt(Config.getProperties().getProperty("EnemyHealth2"));
			}
		} 
		//slow but hard to kill enemy
		if(type == 3) {
			color1 = Color.GREEN;
			if(rank == 1){
				speed = (double)Integer.parseInt(Config.getProperties().getProperty("EnemySpeed3"));
				r = Integer.parseInt(Config.getProperties().getProperty("EnemyRadius3"));
				health = Integer.parseInt(Config.getProperties().getProperty("EnemyHealth3"));
			}
		} 
		
		x = Math.random() * GamePanel.WIDTH / 2 + GamePanel.WIDTH / 4;
		y = -r + 15;
		
		double angle = 0;//;
		rad = Math.toRadians(angle);
		
		dx = Math.cos(rad) * speed;
		dy = Math.sin(rad) * speed;
		
		ready = false;
		dead = false;
	}
	
	/**
	*Metoda zwracajaca wspolrzedna x
	*/
	public double getx() { return x;}
	/**
	*Metoda zwracajaca wspolrzedna y
	*/
	public double gety() { return y;}
	/**
	*Metoda zwracajaca promien wroga
	*/
	public double getr() { return r;}
		/**
		*Metoda zwracajaca czy obiekt zostal zabity
		*/
	public boolean isDead() { return dead; }
	
	public int getType() { return type; }
	
	public int getRank() { return rank; }
	/**
	*Metoda aktualizujaca stan przeciwnika po trafieniu pociskiem
	*/
	public void hit(){
		health--;
		if(health <= 0 ) {
			dead = true;
		}
	}
	
	/**
	*Metoda aktualizująca położenie przeciwnika
	*/
	public boolean update(){
		x += dx;
		y += dy;
		
		if(!ready){
			if( x > r && x < GamePanel.WIDTH - r && y > r && y < GamePanel.HEIGHT - r){
				ready = true;
			}
		}
		if(x < r && dx < 0) dx = -dx;
		if(y < r && dy < 0) dy = -dy;
		if(x > GamePanel.WIDTH - r && dx > 0) dx = -dx;
		if(y > GamePanel.HEIGHT - r && dy > 0) dy = -dy;
		return true;
	}
	
	/**
	*Metoda rysująca przeciwnika
	*/
	public void draw(Graphics2D g){
		g.setColor(color1);
		g.fillOval((int) x - r, (int) y - r, 2 * r, 2 * r);
		
		g.setStroke(new BasicStroke(3));
		g.setColor(color1.darker());
		g.drawOval((int) x - r,(int) y - r, 2 * r, 2 * r);
		g.setStroke(new BasicStroke(1));
	}
	
}