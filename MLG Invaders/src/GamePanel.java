import javax.swing.ImageIcon;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.*;
import java.util.ArrayList;
import java.awt.event.*;

public class GamePanel extends JPanel implements Runnable, KeyListener {
	
	/**
	 * Main thread of the game
	 */
	private Thread 	thread;
	/**
	 * Variable on which depends execute of main loop of the game
	 */
	private boolean running;
	/**
	 * Variable needed to pause the thread
	 */
	private boolean suspended;
	
	/**
	 * Height of the game panel
	 */
	public static int HEIGHT = 		Integer.parseInt(Config.getProperties().getProperty("GameHeight"));	
	/**
	 * Width of the game panel
	 */
	public static int WIDTH = 		Integer.parseInt(Config.getProperties().getProperty("GameWidth"));	
	/**
	 * Height of the side panel
	 */
	public static int HEIGHTPANEL =	Integer.parseInt(Config.getProperties().getProperty("SidePanelHeight"));	
	/**
	 * Width of the side panel
	 */
	public static int WIDTHPANEL =	Integer.parseInt(Config.getProperties().getProperty("SidePanelWidth"));	
	
	/**
	 * Surface on which game is draw
	 */
	private BufferedImage image;
	/**
	 * First draw buffer
	 */
	private Graphics2D 	g;
	/**
	 * Second draw buffer
	 */
	private Graphics2D 	g2;
	/**
	 * Number of frames per second
	 */
	private int FPS = 				Integer.parseInt(Config.getProperties().getProperty("FPS"));
	/**
	 * Average number of frames per second
	 */
	private double averageFPS;
	/**
	 * Variable storing player
	 */
	private static Player player;
	/**
	 * Bullet container
	 */
	public static ArrayList<Bullet> 	bullets;
	/**
	 * Enemy container
	 */
	public static ArrayList<Enemy> 		enemies;
	/**
	 * Power-up container
	 */
	public static ArrayList<PowerUp> 	powerups;
	/**
	 * Bomb container
	 */
	public static ArrayList<Bomb> 		bombs;
	/**
	 * Explosion container
	 */
	public static ArrayList<Explosion> 	explosions;
	/**
	 * Variable storing background image
	 */
	private Image background;
	/**
	 * Variable needed to handle mechanism of following levels
	 */
	private long waveStartTimer;
	/**
	 * Variable needed to handle mechanism of following levels
	 */
	private long waveStartTimerDiff;
	/**
	 * Variable storing number of current wave
	 */
	private int waveNumber;
	/**
	 * Variable needed to start the mechanism of following levels
	 */
	private boolean waveStart;
	/**
	 * Delay between following levels
	 */
	private int waveDelay = 2000;
	/**
	 * Currently checked height of game panel
	 */
	private double newHeight;
	/**
	 * Currently checked width of game panel
	 */
	private double newWidth;
	/**
	 * Width zoom factor 
	 */
	private double factorWidth;
	/**
	 * Height zoom factor
	 */
	private double factorHeight;
	
	
	/**
	 * This method creates game panel
	 */
	public GamePanel( ){ 
		super();
		setPreferredSize(new Dimension(WIDTH + WIDTHPANEL, HEIGHT + HEIGHTPANEL));
		setFocusable(true);
		//requestFocus();
		//setEnabled(true);
		
		
	}
	
	
	public void addNotify(){
		super.addNotify();
		if(thread == null){
			thread = new Thread(this);
			thread.start();
			addKeyListener(this);
		}
		
	}
	
	/**
	 * Method to pause the thread
	 */
	public void suspend(){
		suspended = true;
	}
	
	/**
	 * Method to resume the thread
	 */
	public void resume(){
		suspended = false;
		notify();
	}
	/**
	 * Load background
	 * Create ArrayLists for enemies, bullets, bombs, power-ups, explosions
	 * Adding enemies
	 * Main loop of the game
	 * Mechanism of frames per second
	 * Double-buffered drawing
	 */
	public void run(){
		running = true;
		suspended = false;
		
		
		image = new BufferedImage(2000, 2000 , BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) image.getGraphics();
		g.setRenderingHint(
				RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(
				RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g.setRenderingHint(
				RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.setRenderingHint(
				RenderingHints.KEY_RENDERING,
				RenderingHints.VALUE_RENDER_QUALITY);
		background = new ImageIcon(Config.getProperties().getProperty(
				"Background")).getImage();
		
		
		
		setPlayer(new Player());
		bullets = new ArrayList<Bullet>();
		enemies = new ArrayList<Enemy>();
		powerups = new ArrayList<PowerUp>();
		bombs = new ArrayList<Bomb>();
		explosions = new ArrayList<Explosion>();
		
		
		waveStartTimer = 0;
		waveStartTimerDiff = 0;
		waveStart = true;
		waveNumber = 0;
		
		long startTime;
		long URDTimeMillis;
		long waitTime;
		long totalTime = 0;
		long targetTime = 1000 / FPS;
		
		int frameCount = 0;
		int maxFrameCount = Integer.parseInt(Config.getProperties().getProperty("maxFrameCount"));

		/**
		 * Game loop 
		 */

			while(running){
				
				startTime = System.nanoTime();
				newHeight = super.getHeight();
				newWidth = super.getWidth() - WIDTHPANEL + 20;
				factorWidth = newWidth/Integer.parseInt(Config.getProperties().getProperty("GameWidth"));
				factorHeight = newHeight/Integer.parseInt(Config.getProperties().getProperty("GameHeight"));
				gameUpdate();
				
				URDTimeMillis = (System.nanoTime() - startTime) / 1000000;
				
				waitTime = targetTime - URDTimeMillis;
				
				
				try{
					Thread.sleep(waitTime);
					synchronized(this){
					while(suspended){
						Thread.yield();
					}
					}
				}
				catch (IllegalArgumentException e){
					// nothing
				}
				catch(Exception e){
					e.printStackTrace();
				}
				
				totalTime += System.nanoTime() - startTime;
				frameCount++;
				if(frameCount == maxFrameCount){
					averageFPS = 1000.0 / ((totalTime / frameCount) / 1000000);
					frameCount = 0;
					totalTime = 0;
				}
			}
			
		g.setColor(new Color(10,10,10));
		g.fillRect(0, 0, (int)(WIDTH*factorWidth), (int) (HEIGHT*factorHeight));
		g.setColor(Color.WHITE);
		g.setFont(new Font("Century Gothic", Font.PLAIN, 20));
		if(waveNumber < 11){
			String end = "G A M E  O V E R";
			int lengthend = (int) g.getFontMetrics().getStringBounds(end, g).getWidth();
			g.drawString(end, ((int)(WIDTH*factorWidth) - lengthend) / 2, (int) (HEIGHT*factorHeight) / 2 );
			Sound.playSound("/Sounds/IlluConfirmed.wav");
		}
		else {
			String end = "YOU WIN";
			int lengthend = (int) g.getFontMetrics().getStringBounds(end, g).getWidth();
			g.drawString(end, ((int)(WIDTH*factorWidth) - lengthend) / 2, (int) (HEIGHT*factorHeight) / 2 );
			Sound.playSound("/Sounds/ThugLife.wav");
		}
		
		String wynik = "Twoj wynik: " + getPlayer().score;
		int lengthwynik = (int) g.getFontMetrics().getStringBounds(wynik, g).getWidth();
		g.drawString(wynik, ((int)(WIDTH*factorWidth) - lengthwynik)/2, (int) (HEIGHT*factorHeight) / 2 + 30);
		
		g2 = (Graphics2D) this.getGraphics(); // poprzednio: Graphics g2 = this.getGraphics();
		g2.drawImage(image,0,0,null); // na ostatnim miejscu obiekt ktory ma byc powiadomiony, ze rysowanie sie udalo tzw ImageObserver
		g2.dispose();
	}
	
	
	/**
	 * Player, Bullet, Enemy update and collisions
	 * drawing window, player, enemies, bullets
	 * Adding new waves of enemies
	 */
	private void gameUpdate(){
		
		//new level
		if(waveStartTimer == 0 && enemies.size() == 0) {
			waveNumber++;
			waveStart = false;
			waveStartTimer = System.nanoTime();
			if (waveNumber == 1) { Sound.playSound("/Sounds/Intro.wav"); }
		}
		else {
			waveStartTimerDiff = (System.nanoTime() - waveStartTimer) / 1000000;
			if(waveStartTimerDiff > waveDelay){
				waveStart = true;
				waveStartTimer = 0;
				waveStartTimerDiff = 0;
			}
		}
		
		//create enemies
		if(waveStart && enemies.size() == 0){
			createNewEnemies();
		}
		
		//player update
		getPlayer().update();
		
		//bullet update
		for(int i = 0; i < bullets.size(); i++){
			boolean remove = bullets.get(i).update();
			if(remove){
				bullets.remove(i);
				i--;
			}
		}
		//enemy update
		for( int i = 0; i < enemies.size(); i++){
			enemies.get(i).update();
		}
		
		//powerup update
		for(int i = 0; i < powerups.size(); i++){
			boolean remove = powerups.get(i).update();
			if(remove){
				powerups.remove(i);
				i--;
			}
		}
		
		//bomb update
		for(int i = 0; i < bombs.size(); i++){
			boolean remove = bombs.get(i).update();
			if(remove){
				bombs.remove(i);
				i--;
			}
		}
		
		//explosion update
		for( int i = 0; i < explosions.size(); i++){
			boolean remove = explosions.get(i).update();
			if(remove){
				explosions.remove(i);
				i--;
			}
		}
		
		// bullet-enemy collision
		for(int i = 0; i < bullets.size(); i++){
			
			Bullet b = bullets.get(i);
			double bx = b.getx();
			double by = b.gety();
			double br = b.getr();
			
			for( int j = 0; j < enemies.size(); j++){
				
				Enemy e = enemies.get(j);
				double ex = e.getx();
				double ey = e.gety();
				double er = e.getr();
				
				double dx = bx - ex;
				double dy = by - ey;
				double dist = Math.sqrt(dx * dx + dy * dy);
				
				if( dist < br + er){
					e.hit();
					bullets.remove(i);
					i--;
					break;
				}
				
			}
		}
		//check dead enemies
		for(int i = 0; i < enemies.size(); i++){
			if(enemies.get(i).isDead()){
				Enemy e = enemies.get(i);
				
				//chance for powerup
				double random = Math.random();
				if(random < 0.001) powerups.add(new PowerUp(1, e.getx(), e.gety()));
				else if(random < 0.01) powerups.add(new PowerUp(4, e.getx(), e.gety()));
				else if(random < 0.02) powerups.add(new PowerUp(3, e.getx(), e.gety()));
				else if(random < 0.12) powerups.add(new PowerUp(2, e.getx(), e.gety()));
				
				
				getPlayer().addScore(e.getType() + e.getRank());
				enemies.remove(i);
				i--;
			}
			else{
				Enemy e = enemies.get(i);
				
				//chance for enemy to drop the bomb
				double randombomb = Math.random();
				if(randombomb < 0.01) bombs.add(new Bomb(e.getx(), e.gety()));
			}
		}
		
		//check dead player
		if(player.isDead()){
			running = false;
			
		}
		//check win
		if(waveNumber == 11){
			running = false;
		}
		
		// player-enemy collision
		if(!getPlayer().isRecovering()){
			int px = getPlayer().getix();
			int py = getPlayer().getiy();
			int pr = getPlayer().getir();
			for(int i = 0; i < enemies.size(); i++){
				Enemy e = enemies.get(i);
				double ex = e.getx();
				double ey = e.gety();
				double er = e.getr();
				
				double dx = px - ex;
				double dy = py - ey;
				double dist = Math.sqrt(dx * dx + dy * dy);
				
				if(dist < pr + er){
					getPlayer().loseLife();
					if(getPlayer().getLives()!=0) { Sound.playSound("/Sounds/Punch.wav"); }
				}
			}
		}
		
		//player-powerup collision
		int px = getPlayer().getix();
		int py = getPlayer().getiy();
		int pr = getPlayer().getir();
		for(int i = 0; i < powerups.size(); i++){
			PowerUp p = powerups.get(i);
			double x = p.getx();
			double y = p.gety();
			double r = p.getr();
			
			double dx = px - x;
			double dy = py - y;
			double dist = Math.sqrt(dx * dx + dy * dy);
			
			//collected powerup
			
			if(dist < pr + r){
				Sound.playSound("/Sounds/PowerUp.wav");
				int type = p.getType();
				
				if(type == 1){
					getPlayer().gainLife();
				}
				if(type == 2){
					getPlayer().increasePower(1);
				}
				if(type ==3){
					getPlayer().increasePower(2);
				}
				if(type == 4){
					getPlayer().addScore(50);
				}
				powerups.remove(i);
				i--;
			}
		}
		
		// player - bomb collision
		if(!getPlayer().isRecovering()){
			for(int i = 0; i < bombs.size(); i++){
				Bomb b = bombs.get(i);
				double x = b.getx();
				double y = b.gety();
				double r = b.getr();
				
				double dx = px - x;
				double dy = py - y;
				double dist = Math.sqrt(dx * dx + dy * dy);
				
				if(dist < pr + r){
					getPlayer().loseLife();
					if(getPlayer().lives!=0) { Sound.playSound("/Sounds/WTFBoom_cut.wav"); }
				}
			}
		}
		
		// bullet - bomb collision (explosions)
		for(int i = 0; i < bullets.size(); i++){
			
			Bullet b = bullets.get(i);
			double bx = b.getx();
			double by = b.gety();
			double br = b.getr();
			
			for( int j = 0; j < bombs.size(); j++){
				
				Bomb bomba = bombs.get(j);
				double bombx = bomba.getx();
				double bomby = bomba.gety();
				double bombr = bomba.getr();
				
				double dx = bx - bombx;
				double dy = by - bomby;
				double dist = Math.sqrt(dx * dx + dy * dy);
				
				if( dist < br + bombr){
					explosions.add(new Explosion(bombx,bomby, (int) bombr, (int) bombr + 20));
					Sound.playSound("/Sounds/Boom.wav");
					bullets.remove(i);
					bombs.remove(j);
					i--;
					break;
					
					
				}
				
			}
		}
			
		g.setColor(Color.WHITE);
		g.drawImage(background,0,0,(int) (WIDTH*factorWidth), (int)(HEIGHT*factorHeight), null);
		g.fillRect((int) (WIDTH*factorWidth),0,WIDTHPANEL,(int) (HEIGHTPANEL*factorHeight));
	
		String scor = "Wynik: ";
		String life = "Zycia:";
		String power = "Moc: ";
		String valuescore = Integer.toString(player.score);
		String valuehealth = Integer.toString(player.lives);
		
		g.setColor(Color.BLACK);
		g.setFont(new Font("Century Gothic", Font.PLAIN, 18));
		int lengthscor = (int) g.getFontMetrics().getStringBounds(scor, g).getWidth();
		int lengthlife = (int) g.getFontMetrics().getStringBounds(life, g).getWidth();
		int lengthpower = (int) g.getFontMetrics().getStringBounds(power, g).getWidth();
		int lengthvaluescor = (int) g.getFontMetrics().getStringBounds(valuescore, g).getWidth();
		int lengthvaluehealth = (int) g.getFontMetrics().getStringBounds(valuehealth, g).getWidth();
		
		g.drawString(scor, (int) (WIDTH*factorWidth) + (WIDTHPANEL - lengthscor)/ 2, (int) (HEIGHT*factorHeight) / 12);
		
		g.drawString(power, (int) (WIDTH*factorWidth) + (WIDTHPANEL - lengthpower)/ 2, 5 * (int) (HEIGHT*factorHeight) / 12);
		g.drawString(life, (int) (WIDTH*factorWidth) + (WIDTHPANEL - lengthlife)/ 2, 9 *(int) (HEIGHT*factorHeight) / 12);
		g.drawString(valuescore, (int) (WIDTH*factorWidth) + (WIDTHPANEL - lengthvaluescor)/ 2, 2 * (int) (HEIGHT*factorHeight) /12);
		g.setColor(Color.YELLOW);
		g.fillRect((int) (WIDTH*factorWidth) + 55, 6 * (int) (HEIGHT*factorHeight) / 12, getPlayer().getPower() * 16 , 16);
		g.setColor(Color.YELLOW.darker());
		g.setStroke(new BasicStroke(2));
		for( int i = 0; i < getPlayer().getRequiredPower(); i++){
		 g.drawRect((int) (WIDTH*factorWidth) + 55 + 16 * i, 6 * (int) (HEIGHT*factorHeight) / 12, 16,16);
		}
		for(int i = 0; i < player.getLives(); i++){
			g.setColor(Color.WHITE);
			g.fillOval((int) (WIDTH*factorWidth) + 70 + (20 * i), 10 * (int) (HEIGHT*factorHeight) / 12, (int) 10 * 2, (int) 10 * 2);
			((Graphics2D) g).setStroke( new BasicStroke(3));
			g.setColor(Color.WHITE.darker());
			g.drawOval((int) (WIDTH*factorWidth) + 70 + (20 * i), 10 * (int) (HEIGHT*factorHeight) / 12, (int) 10 * 2, (int) 10 * 2);
			((Graphics2D) g).setStroke( new BasicStroke(1));
		}
		
		
		//draw player
		getPlayer().draw(g, factorWidth, factorHeight);
		
		//draw bullets
		for( int i = 0; i < bullets.size(); i++){
			bullets.get(i).draw(g, factorWidth, factorHeight);
		}
		
		//draw enemies
		for( int i = 0; i < enemies.size(); i++){
			enemies.get(i).draw(g, factorWidth, factorHeight);
		}
		
		//draw powerups
		for( int i = 0; i < powerups.size(); i++){
			powerups.get(i).draw(g, factorWidth, factorHeight);
		}
		
		// draw bombs
		for( int i = 0; i < bombs.size(); i++){
			bombs.get(i).draw(g, factorWidth, factorHeight);
		}
		
		// draw explosions
		for( int i = 0; i < explosions.size(); i++){
			explosions.get(i).draw(g, factorWidth, factorHeight);
		}
		
		//draw wave number
		if(waveStartTimer != 0){
			g.setFont(new Font("Century Gothic", Font.PLAIN, 23));
			String s = "- P O Z I O M  " + waveNumber + " -";
			int length = (int) g.getFontMetrics().getStringBounds(s, g).getWidth();
			int alpha = (int) (255 * Math.sin(3.14 * waveStartTimerDiff  / waveDelay));
			if(alpha > 255) alpha = 255;
			g.setColor(new Color(0,0,0, alpha));
			g.drawString(s, ((int) (WIDTH*factorWidth) - length) / 2 , 3 * (int) (HEIGHT*factorHeight) / 8);
		}
		
		
		g2 = (Graphics2D) this.getGraphics();
		g2.drawImage(image,0,0,null);
		g2.dispose();
	}
	
	
	/**
	 * Creates new enemies depending on number of wave
	 */
	private void createNewEnemies(){
		
		enemies.clear();
		Enemy e;
		
		for(int i = 0; i < waveNumber; i++){
			
			if(waveNumber == 1 || waveNumber == 2){
				enemies.add(new Enemy(1,1));
				enemies.add(new Enemy(1,1));
			}
			if(waveNumber > 2 && waveNumber < 6) {
				enemies.add(new Enemy(1,1));
				enemies.add(new Enemy(2,1));
			}
			if(waveNumber > 5 && waveNumber < 10){
				enemies.add(new Enemy(1,1));
				enemies.add(new Enemy(2,1));
				enemies.add(new Enemy(3,1));
			}
		}
		if(waveNumber == 10){
			enemies.add(new Enemy(4,1));
		}
	}
	
	
	/**
	 * pusta metoda
	 */
	public void keyTyped(KeyEvent key){}
	
	
	/**
	 * Key events
	 */
	public void keyPressed(KeyEvent key){
		
		int keyCode = key.getKeyCode();
		if(keyCode == KeyEvent.VK_LEFT){
			getPlayer().setLeft(true);
		}
		if(keyCode == KeyEvent.VK_RIGHT){
			getPlayer().setRight(true);
		}
		if(keyCode == KeyEvent.VK_UP){
			getPlayer().setUp(true);
		}
		if(keyCode == KeyEvent.VK_DOWN){
			getPlayer().setDown(true);
		}
		if(keyCode == KeyEvent.VK_SPACE){
			getPlayer().setFiring(true);
		}
		if(keyCode == KeyEvent.VK_P){
			if(suspended == false){
				this.suspend();
			}
			else if( suspended == true){
				this.resume();
			}
		}
	}
	/**
	 * key events
	 */
	public void keyReleased(KeyEvent key){
		int keyCode = key.getKeyCode();
		if(keyCode == KeyEvent.VK_LEFT){
			getPlayer().setLeft(false);
		}
		if(keyCode == KeyEvent.VK_RIGHT){
			getPlayer().setRight(false);
		}
		if(keyCode == KeyEvent.VK_UP){
			getPlayer().setUp(false);
		}
		if(keyCode == KeyEvent.VK_DOWN){
			getPlayer().setDown(false);
		}
		if(keyCode == KeyEvent.VK_SPACE){
			getPlayer().setFiring(false);
		}
	}

	/**
	 * Method returning current player
	 * @return player
	 */
	public static Player getPlayer() {
		return player;
	}

	/**
	 * Method setting a player
	 * @param player
	 */
	public static void setPlayer(Player player) {
		GamePanel.player = player;
	}
}