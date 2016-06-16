import java.awt.*;
/**
*Klasa opisujaca postac gry, aktualizuje polozenie
*/
public class Player extends Engine{
	
	private int x;
	private int y;
	private int r;
	
	private int dx;
	private int dy;
	private int speed;
	
	private boolean left;
	private boolean right;
	private boolean up;
	private boolean down;
	
	private boolean firing;
	private long firingTimer;
	private long firingDelay;
	
	private boolean recovering;
	private long recoveryTimer;
	
	public int lives;
	public int score;
	private Color color1;
	private Color color2;
	
	private int powerLevel;
	private int power;
	private int[] requiredPower = { 1, 2, 3, 4, 5 };
	
	/**
	 * Konstruktor playera
	 */
	public Player(){
		
		x = GamePanel.WIDTH / 2;
		y = GamePanel.HEIGHT / 2;
		r = 10;
		speed = 5;
		lives = 3;
		color1 = Color.WHITE;
		color2 = Color.RED;
		
		firing = false;
		firingTimer = System.nanoTime();
		firingDelay = Integer.parseInt(Config.getProperties().getProperty("firingDelay"));
		
				
		recovering = false;
		recoveryTimer = 0;
		
		score = 0;
	}
	
	public int getLives() { return lives; }
	public int getScore() { return score; }	
	public boolean isDead() { return lives <=0; }
	

	public boolean isRecovering() { return recovering; }
	
	public void setLeft(boolean b){ left = b; }
	public void setRight(boolean b){ right = b; }
	public void setUp(boolean b){ up = b; }
	public void setDown(boolean b){ down = b; }
	
	public void setFiring(boolean b) { firing = b; }
	
	public void addScore(int i) { score += i; }
	
	public int getix() { return x;}
	public int getiy() { return y;}
	public int getir() { return r;}
	
	/**
	 * Zmniejsza liczbe zyc o jeden
	 */
	public void loseLife() {
		lives--;
		recovering = true;
		recoveryTimer = System.nanoTime();
	}
	
	/**
	 * zwieksza liczbe zyc o jeden
	 */
	public void gainLife() {
		lives++;
	}
	
	/**
	 * zwieksza moc ataku
	 */
	public void increasePower(int i){
		power += i;
		if(power >= requiredPower[powerLevel]){
			power -= requiredPower[powerLevel];
			powerLevel++;
		}
	}
	
	public int getPowerLevel(){return powerLevel; }
	public int getPower(){ return power; }
	public int getRequiredPower(){ return requiredPower[powerLevel]; }
	
	/**
	*Metoda aktualizujaca po³o¿enie postaci
	*/
	public boolean update() {
		if(left){
			dx = -speed;
		}
		if(right){
			dx = speed;
		}
		if(up){
			dy = -speed;
		}
		if(down){
			dy = speed;
		}
		
		x += dx;
		y += dy;
		
		if(x < r) x = r;
		if(y < r) y = r;
		if(x > GamePanel.WIDTH - r) x = GamePanel.WIDTH - r;
		if(y > GamePanel.HEIGHT - r) y = GamePanel.HEIGHT - r;
		
		dx = 0;
		dy = 0;
		
		if(firing){
			long elapsed = (System.nanoTime() - firingTimer) / 1000000;
			
			if(elapsed > firingDelay){
				
				firingTimer = System.nanoTime();
				
				if(powerLevel < 2){
					GamePanel.bullets.add(new Bullet(270,x,y));
					if(lives!=0) { Sound.playSound("/Sounds/m4a1.wav"); }
				}
				else if(powerLevel < 4){
					GamePanel.bullets.add(new Bullet(270,x+5,y));
					GamePanel.bullets.add(new Bullet(270,x-5,y));
					Sound.playSound("/Sounds/ump45.wav"); 
				}
				else{
					GamePanel.bullets.add(new Bullet(275,x+5,y));
					GamePanel.bullets.add(new Bullet(265,x-5,y));
					GamePanel.bullets.add(new Bullet(270,x,y));
					Sound.playSound("/Sounds/m249.wav"); 
				}
			}
		}
		
		// 2 sekundy niesmiertelnosci po stracie jedenego zycia 
		long elapsed = (System.nanoTime() - recoveryTimer) / 1000000;
		if(elapsed > 2000) {
			recovering = false;
			recoveryTimer = 0;
		}
		return true;
	}
	
	/**
	*Metoda rysujaca postac gracza
	*/
	public void draw(Graphics2D g, double factorWidth, double factorHeight){
		
		if(recovering) {
			g.setColor(color2);
			g.fillOval((int) ((x - r)*factorWidth),(int) ((y - r)*factorHeight), (int) (2 * r * factorWidth), (int)(2 * r * factorHeight));
			
			g.setStroke(new BasicStroke(3));
			g.setColor(color2.darker());
			g.drawOval((int) ((x - r)*factorWidth),(int) ((y - r)*factorHeight),(int) ( 2 * r * factorWidth),(int) (2 * r * factorHeight));
			g.setStroke(new BasicStroke(1));
		}
		else{
			g.setColor(color1);
			g.fillOval((int) ((x - r)*factorWidth),(int) ((y - r)*factorHeight),(int) ( 2 * r * factorWidth),(int) (2 * r * factorHeight));
			
			g.setStroke(new BasicStroke(3));
			g.setColor(color1.darker());
			g.drawOval((int) ((x - r)*factorWidth),(int) ((y - r)*factorHeight),(int) ( 2 * r * factorWidth),(int) (2 * r * factorHeight));
			g.setStroke(new BasicStroke(1));
		}
		
	}
}