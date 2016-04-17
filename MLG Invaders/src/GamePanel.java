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
	
	private BufferedImage image;
	private Graphics2D g;
	private Graphics2D g2;
	private int FPS = Integer.parseInt(Config.getProperties().getProperty("FPS"));
	private double averageFPS;
	private int numberOfEnemies = Integer.parseInt(Config.getProperties().getProperty("numberOfEnemies"));
	
	public static Player player;
	public static ArrayList<Bullet> bullets;
	public static ArrayList<Enemy> enemies;
	private Image background;
	
	
	/**
	 * create JPanel 
	 * focus
	 */
	public GamePanel( ){ 
		super();	// create JPanel with double buffer
		setPreferredSize(new Dimension(WIDTH,HEIGHT)); //TU TRZEBA BEDZIE DODAC FUNKCJE DOWOLNEGO ROZSZERZANIA OKNA
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
	 * Create ArrayLists for enemies and bulets
	 * Adding enemies
	 * Game loop, FPS
	 */
	public void run(){
		
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) image.getGraphics();
		background = new ImageIcon(Config.getProperties().getProperty(
				"Background")).getImage();
		running = true;
		
		player = new Player();
		bullets = new ArrayList<Bullet>();
		enemies = new ArrayList<Enemy>();
		
		
		for( int i = 0; i < numberOfEnemies; i++){
			enemies.add(new Enemy(1,1));				
			
		}
		
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
				enemies.remove(i);
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
		g.drawString("NumberOfEnemies: " + enemies.size(), 375, 530);
		
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
		
		/*
		 *  poprzedwnie gameDraw
		 */
		
		g2 = (Graphics2D) this.getGraphics(); // poprzednio: Graphics g2 = this.getGraphics();
		g2.drawImage(image,0,0,null); // na ostatnim miejscu obiekt ktory ma byc powiadomiony, ze rysowanie sie udalo tzw ImageObserver
		g2.dispose();
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