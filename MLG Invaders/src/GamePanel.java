import javax.swing.ImageIcon;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.*;
import java.util.ArrayList;
import java.awt.event.*;

public class GamePanel extends JPanel implements Runnable, KeyListener {
	
	
	private Thread thread;
	private boolean running;
	
	
	public static int HEIGHT = 	Integer.parseInt(Config.getProperties().getProperty("GameHeight"));	
	public static int WIDTH = 	Integer.parseInt(Config.getProperties().getProperty("GameWidth"));	
	//public static int HEIGHTPANEL = Integer.parseInt(Config.getProperties().getProperty("SidePanelHeight"));	
	//public static int WIDTHPANEL = Integer.parseInt(Config.getProperties().getProperty("SidePanelWidth"));	
	
	private BufferedImage image;
	private Graphics2D g;
	private Graphics2D g2;
	private int FPS = Integer.parseInt(Config.getProperties().getProperty("FPS"));
	private double averageFPS;
	private int numberOfEnemies = Integer.parseInt(Config.getProperties().getProperty("numberOfEnemies"));
	
	public static Player player;
	public static ArrayList<Bullet> bullets;
	public static ArrayList<Enemy> enemies;
	public static ArrayList<PowerUp> powerups;
	private Image background;
	
	private long waveStartTimer;
	private long waveStartTimerDiff;
	private int waveNumber;
	private boolean waveStart;
	private int waveDelay = 2000;
	
	
	/**
	 * create JPanel 
	 * focus
	 */
	public GamePanel( ){ 
		super();	// create JPanel with double buffer
		setPreferredSize(new Dimension(WIDTH, HEIGHT)); //TU TRZEBA BEDZIE DODAC FUNKCJE DOWOLNEGO ROZSZERZANIA OKNA
		setFocusable(true);
		requestFocus();
		setEnabled(true);
		
		
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
	 * Load background
	 * Create ArrayLists for enemies and bullets
	 * Adding enemies
	 * Game loop, FPS
	 */
	public void run(){
		running = true;
		
		
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) image.getGraphics();
		g.setRenderingHint(
				RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(
				RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		background = new ImageIcon(Config.getProperties().getProperty(
				"Background")).getImage();
		
		
		player = new Player();
		bullets = new ArrayList<Bullet>();
		enemies = new ArrayList<Enemy>();
		powerups = new ArrayList<PowerUp>();
		
		
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
			
				gameUpdate();
			
			URDTimeMillis = (System.nanoTime() - startTime) / 1000000;
			
			waitTime = targetTime - URDTimeMillis;
			
			try{
				Thread.sleep(waitTime);
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
	}
	
	
	/**
	 * Player, Bullet, Enemy update and collisions
	 * drawing window, player, enemies, bullets
	 */
	private void gameUpdate(){
		
		//new level
		if(waveStartTimer == 0 && enemies.size() == 0) {
			waveNumber++;
			waveStart = false;
			waveStartTimer = System.nanoTime();
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
		player.update();
		
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
				else if(random < 0.02) powerups.add(new PowerUp(3, e.getx(), e.gety()));
				else if(random < 0.12) powerups.add(new PowerUp(2, e.getx(), e.gety()));
				
				player.addScore(e.getType() + e.getRank());
				enemies.remove(i);
				i--;
			}
		}
		
		// player-enemy collision
		if(!player.isRecovering()){
			int px = player.getix();
			int py = player.getiy();
			int pr = player.getir();
			for(int i = 0; i < enemies.size(); i++){
				Enemy e = enemies.get(i);
				double ex = e.getx();
				double ey = e.gety();
				double er = e.getr();
				
				double dx = px - ex;
				double dy = py - ey;
				double dist = Math.sqrt(dx * dx + dy * dy);
				
				if(dist < pr + er){
					player.loseLife();
				}
			}
		}
		
		//player-powerup collision
		int px = player.getix();
		int py = player.getiy();
		int pr = player.getir();
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
				
				int type = p.getType();
				
				if(type == 1){
					player.gainLife();
				}
				if(type == 2){
					player.increasePower(1);
				}
				if(type ==3){
					player.increasePower(2);
				}
				powerups.remove(i);
				i--;
			}
		}
		
		/**
		 *  Tworzy okno i rysuje 
		 */
		
			
		g.setColor(Color.WHITE);
		g.fillRect(0,0,WIDTH,HEIGHT);
		g.drawImage(background,0,0,null);
		//g.setColor(Color.BLACK);
		//g.drawString("FPS: " + averageFPS,440,300);
		g.setColor(Color.BLUE);
		
		//TYMCZASOWE
		g.drawString("Score: " + player.score, 300, 530);
		g.drawString("Lives: " + player.lives, 300, 510);
		g.setColor(Color.YELLOW);
		g.fillRect(300, 470, player.getPower() * 8 , 8);
		g.setColor(Color.YELLOW.darker());
		g.setStroke(new BasicStroke(2));
		for( int i = 0; i < player.getRequiredPower(); i++){
		 g.drawRect(300 + 8 *i,  470, 8,8);
		}
		//AZ DOTAD
		
		//draw side panel
		//g.setColor(Color.WHITE);
		//g.fillRect(500, 0, WIDTHPANEL, HEIGHTPANEL);
		

		
		
		//draw player
		player.draw(g);
		
		//draw bullets
		for( int i = 0; i < bullets.size(); i++){
			bullets.get(i).draw(g);
		}
		
		//draw enemies
		for( int i = 0; i < enemies.size(); i++){
			enemies.get(i).draw(g);
		}
		
		//draw powerups
		for( int i = 0; i < powerups.size(); i++){
			powerups.get(i).draw(g);
		}
		
		//draw wave number
		if(waveStartTimer != 0){
			g.setFont(new Font("Century Gothic", Font.PLAIN, 23));
			String s = "- P O Z I O M  " + waveNumber + " -";
			int length = (int) g.getFontMetrics().getStringBounds(s, g).getWidth();
			int alpha = (int) (255 * Math.sin(3.14 * waveStartTimerDiff  / waveDelay));
			if(alpha > 255) alpha = 255;
			g.setColor(new Color(0,0,0, alpha));
			g.drawString(s, (WIDTH - length) / 2 , 3 * HEIGHT / 8);
		}
		
		
		
		/*
		 *  poprzedwnie gameDraw
		 */
		
		g2 = (Graphics2D) this.getGraphics(); // poprzednio: Graphics g2 = this.getGraphics();
		g2.drawImage(image,0,0,null); // na ostatnim miejscu obiekt ktory ma byc powiadomiony, ze rysowanie sie udalo tzw ImageObserver
		g2.dispose();
	}
	
	private void createNewEnemies(){
		
		enemies.clear();
		Enemy e;
		
		if(waveNumber == 1) {
			for(int i = 0; i < 4; i++){
				enemies.add(new Enemy(1,1));
			}
		}
		if(waveNumber == 2) {
			for(int i = 0; i < 8; i++){
				enemies.add(new Enemy(1,1));
			}
		}
		if(waveNumber == 3) {
			for(int i = 0; i < 8; i++){
				enemies.add(new Enemy(1,1));
			}
			for(int i = 0; i < 4; i++){
				enemies.add(new Enemy(2,1));
			}
		}
		if(waveNumber == 4) {
			for(int i = 0; i < 4; i++){
				enemies.add(new Enemy(1,1));
			}
			for(int i = 0; i < 4; i++){
				enemies.add(new Enemy(2,1));
			}
			for(int i = 0; i < 4; i++){
				enemies.add(new Enemy(3,1));
			}
		}
	}
	
	
	public void keyTyped(KeyEvent key){}
	
	
	/**
	 * Key events
	 */
	public void keyPressed(KeyEvent key){
		
		int keyCode = key.getKeyCode();
		if(keyCode == KeyEvent.VK_LEFT){
			player.setLeft(true);
		}
		if(keyCode == KeyEvent.VK_RIGHT){
			player.setRight(true);
		}
		if(keyCode == KeyEvent.VK_UP){
			player.setUp(true);
		}
		if(keyCode == KeyEvent.VK_DOWN){
			player.setDown(true);
		}
		if(keyCode == KeyEvent.VK_SPACE){
			player.setFiring(true);
		}
	}
	public void keyReleased(KeyEvent key){
		int keyCode = key.getKeyCode();
		if(keyCode == KeyEvent.VK_LEFT){
			player.setLeft(false);
		}
		if(keyCode == KeyEvent.VK_RIGHT){
			player.setRight(false);
		}
		if(keyCode == KeyEvent.VK_UP){
			player.setUp(false);
		}
		if(keyCode == KeyEvent.VK_DOWN){
			player.setDown(false);
		}
		if(keyCode == KeyEvent.VK_SPACE){
			player.setFiring(false);
		}
	}
}