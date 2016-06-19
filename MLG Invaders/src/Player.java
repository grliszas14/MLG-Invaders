import java.awt.*;
/**
* Class describing player
*/
public class Player extends Engine{
	/**
	 * Coordinate x
	 */
	private int x;
	/**
	 * Coordinate y
	 */
	private int y;
	/**
	 * Radius of player
	 */
	private int r;
	/**
	 * Shift x axis
	 */
	private int dx;
	/**
	 * Shift y axis
	 */
	private int dy;
	/**
	 * Speed of player
	 */
	private int speed;
	/**
	 * Variable to tell if player is going left
	 */
	private boolean left;
	/**
	 * Variable to tell if player is going right
	 */
	private boolean right;
	/**
	 * Variable to tell if player is going up
	 */
	private boolean up;
	/**
	 * Variable to tell if player is going down
	 */
	private boolean down;
	/**
	 * Variable to tell if player is firing
	 */
	private boolean firing;
	/**
	 * Variable needed to shooting mechanism
	 */
	private long firingTimer;
	/**
	 * Variable needed to shooting mechanism
	 */
	private long firingDelay;
	/**
	 * Variable to tell if player is recovering
	 */
	private boolean recovering;
	/**
	 * Timer of recovery state
	 */
	private long recoveryTimer;
	/**
	 * Amount of player lives
	 */
	public int lives;
	/**
	 * Player score
	 */
	public int score;
	/**
	 * First color to draw player
	 */
	private Color color1;
	/**
	 * Second color do draw player
	 */
	private Color color2;
	/**
	 * Level of shooting power
	 */
	private int powerLevel;
	/**
	 * Variable needed to shooting power mechanism
	 */
	private int power;
	/**
	 * Table needed to shooting power mechanism
	 */
	private int[] requiredPower = { 1, 2, 3, 4, 5 };
	
	/**
	 * Constructor of player, setting position and other parameters
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
	/**
	 * lives getter
	 * @return lives
	 */
	public int getLives() { return lives; }
	/**
	 * score getter
	 * @return score
	 */
	public int getScore() { return score; }	
	/**
	 * method telling if player is dead or not
	 * @return
	 */
	public boolean isDead() { return lives <=0; }
	
	/**
	 * method telling if player is recovering
	 * @return recovering
	 */
	public boolean isRecovering() { return recovering; }
	/**
	 * Method setting player move left
	 * @param b
	 */
	public void setLeft(boolean b){ left = b; }
	/**
	 * Method setting player move right
	 * @param b
	 */
	public void setRight(boolean b){ right = b; }
	/**
	 * Method setting player move up
	 * @param b
	 */
	public void setUp(boolean b){ up = b; }
	/**
	 * Method setting player move down
	 * @param b
	 */
	public void setDown(boolean b){ down = b; }
	/**
	 * Method setting player firing
	 * @param b
	 */
	public void setFiring(boolean b) { firing = b; }
	/**
	 * Method adding player score
	 * @param i
	 */
	public void addScore(int i) { score += i; }
	/**
	 * get player x
	 * @return x
	 */
	public int getix() { return x;}
	/**
	 * get player y
	 * @return y
	 */
	public int getiy() { return y;}
	/**
	 * get player r
	 * @return r 
	 */
	public int getir() { return r;}
	
	/**
	 * Method to remove 1 life
	 */
	public void loseLife() {
		lives--;
		recovering = true;
		recoveryTimer = System.nanoTime();
	}
	
	/**
	 * Add 1 life
	 */
	public void gainLife() {
		lives++;
	}
	
	/**
	 * Add power of firing
	 */
	public void increasePower(int i){
		if(powerLevel < 4){
			power += i;
			if(power >= requiredPower[powerLevel]){
				power -= requiredPower[powerLevel];
				powerLevel++;
			}
		}
	}
	/**
	 * powerLevel getter
	 * @return powerLevel
	 */
	public int getPowerLevel(){return powerLevel; }
	/**
	 * power getter
	 * @return power
	 */
	public int getPower(){ return power; }
	/**
	 * requiredPower getter
	 * @return requiredPower
	 */
	public int getRequiredPower(){ return requiredPower[powerLevel]; }
	
	/**
	* Update player position
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

		long elapsed = (System.nanoTime() - recoveryTimer) / 1000000;
		if(elapsed > 2000) {
			recovering = false;
			recoveryTimer = 0;
		}
		return true;
	}
	
	/**
	* Method to draw player
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