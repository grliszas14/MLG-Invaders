import java.awt.*;

import javax.swing.ImageIcon;
/**
 * Class creating and drawing Enemies
 */
public class Enemy extends Engine{
	/**
	 * Coordinate x
	 */
	private double x;
	/**
	 * Coordinate y
	 */
	private double y;
	/**
	 * Radius of enemy
	 */
	private int r;
	/**
	 * Shift x axis
	 */
	private double dx;
	/**
	 * Shift y axis
	 */
	private double dy;
	/**
	 * Angle for enemy to go
	 */
	private double rad;
	/**
	 * Speed of enemy
	 */
	private double speed;
	/**
	 * Health of enemy
	 */
	private int health;
	/**
	 * Rank of enemy multiply score
	 */
	private int rank;
	/**
	 * Type of enemy
	 */
	private int type;
	/**
	 * 
	 */
	private boolean ready;
	private boolean dead;
	private Image illuminatiEnemy1;
	private Image illuminatiEnemy2;
	private Image illuminatiEnemy3;
	private Image illuminatiEnemy4;
	
	/**
	* Constructor of enemy class
	* Draws different enemies, sets speed and health
	*/
	public Enemy( int type, int rank){
		illuminatiEnemy1 = new ImageIcon(Config.getProperties().getProperty("IlluminatiEnemy1")).getImage();
		illuminatiEnemy2 = new ImageIcon(Config.getProperties().getProperty("IlluminatiEnemy2")).getImage();
		illuminatiEnemy3 = new ImageIcon(Config.getProperties().getProperty("IlluminatiEnemy3")).getImage();
		illuminatiEnemy4 = new ImageIcon(Config.getProperties().getProperty("IlluminatiEnemy4")).getImage();
		this.type = type;
		this.rank = rank;
		//basic enemy
		if(type == 1) {
			if(rank == 1){
				speed = (double)Integer.parseInt(Config.getProperties().getProperty("EnemySpeed"));
				r = Integer.parseInt(Config.getProperties().getProperty("EnemyRadius"));
				health = Integer.parseInt(Config.getProperties().getProperty("EnemyHealth"));		
			}
		}
		//faster, stronger enemy
		if(type == 2) {
			if(rank == 1){
				speed = (double)Integer.parseInt(Config.getProperties().getProperty("EnemySpeed2"));
				r = Integer.parseInt(Config.getProperties().getProperty("EnemyRadius"));
				health = Integer.parseInt(Config.getProperties().getProperty("EnemyHealth2"));
			}
		} 
		//slow but hard to kill enemy
		if(type == 3) {
			if(rank == 1){
				speed = (double)Integer.parseInt(Config.getProperties().getProperty("EnemySpeed3"));
				r = Integer.parseInt(Config.getProperties().getProperty("EnemyRadius"));
				health = Integer.parseInt(Config.getProperties().getProperty("EnemyHealth3"));
			}
		} 
		//the boss
		if(type == 4) {
			if(rank == 1){
				speed = (double)Integer.parseInt(Config.getProperties().getProperty("EnemySpeed4"));
				r = Integer.parseInt(Config.getProperties().getProperty("EnemyRadius4"));
				health = Integer.parseInt(Config.getProperties().getProperty("EnemyHealth4"));
			}
		}
		x = Math.random() * GamePanel.WIDTH / 2 + GamePanel.WIDTH / 4;
		y = r;
		
		double angle = 0;//;
		rad = Math.toRadians(angle);
		
		dx = Math.cos(rad) * speed;
		dy = Math.sin(rad) * speed;
		
		ready = false;
		dead = false;
	}
	
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
	
	/**
	* dead getter
	* @return dead
	*/
	public boolean isDead() { return dead; }
	/**
	 * type getter
	 * @return type
	 */
	public int getType() { return type; }
	/**
	 * rank getter
	 * @return rank
	 */
	public int getRank() { return rank; }
	/**
	*Method updating state of enemy after being hit by bullet
	*/
	public void hit(){
		health--;
		if(health <= 0 ) {
			dead = true;
			double random = Math.random();
			if(random < 0.1) Sound.playSound("/Sounds/Headshot.wav");
		}
	}
	
	/**
	*  Updates position of enemy
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
		if(x > GamePanel.WIDTH - r && dx > 0){
			dx = -dx;
			y += 50;
		}
		if(y > GamePanel.HEIGHT - r && dy > 0) dy = -dy;
		return true;
	}
	
	/**
	* Method drawing enemies
	*/
	public void draw(Graphics2D g, double factorWidth, double factorHeight){
		if(type == 1) g.drawImage(illuminatiEnemy1, (int) ((x - r)*factorWidth), (int) ((y-r)*factorHeight), (int) (50*factorWidth), (int) (52*factorHeight), null);
		if(type == 2) g.drawImage(illuminatiEnemy2, (int) ((x - r)*factorWidth), (int) ((y-r)*factorHeight), (int) (50*factorWidth), (int) (52*factorHeight), null);
		if(type == 3) g.drawImage(illuminatiEnemy3, (int) ((x - r)*factorWidth), (int) ((y-r)*factorHeight), (int) (50*factorWidth), (int) (52*factorHeight), null);
		if(type == 4) g.drawImage(illuminatiEnemy4, (int) ((x - r)*factorWidth), (int) ((y-r)*factorHeight), (int) (200*factorWidth), (int) (200*factorHeight), null);
	}
	
}