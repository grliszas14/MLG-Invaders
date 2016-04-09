import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.*;
import java.util.ArrayList;
import java.awt.event.*;

public class GamePanel extends JPanel implements Runnable, KeyListener {
	
	private Thread thread;
	private boolean running;
	public static int HEIGHT = 590;
	public static int WIDTH = 500;
	
	private BufferedImage image;
	private Graphics2D g;
	private int FPS = 30;
	private double averageFPS;
	
	public static Player player;
	public static ArrayList<Bullet> bullets;
	public static ArrayList<Enemy> enemies;
	
	public GamePanel(){
		super();
		setPreferredSize(new Dimension(WIDTH,HEIGHT)); //TU TRZEBA BEDZIE DODAC FUNKCJE DOWOLNEGO ROZSZERZANIA OKNA
		setFocusable(true);
		requestFocus();
	}
	
	
	public void addNotify(){
		super.addNotify();
		if(thread == null){
			thread = new Thread(this);
			thread.start();
			addKeyListener(this);
		}
	}
	
	public void run(){
		
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) image.getGraphics();
		running = true;
		
		player = new Player();
		bullets = new ArrayList<Bullet>();
		enemies = new ArrayList<Enemy>();
		for( int i = 0; i < 5; i++){
			enemies.add(new Enemy(1,1));
		}
		
		long startTime;
		long URDTimeMillis;
		long waitTime;
		long totalTime = 0;
		long targetTime = 1000 / FPS;
		
		int frameCount = 0;
		int maxFrameCount = 30;
		
		//Game loop
		while(running){
			
			startTime = System.nanoTime();
			
			gameUpdate();
			gameRender();
			gameDraw();
			
			URDTimeMillis = (System.nanoTime() - startTime) / 1000000;
			
			waitTime = targetTime - URDTimeMillis;
			
			try{
				Thread.sleep(waitTime);
			}
			catch(Exception e){
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
		
	}
	
	private void gameRender(){
		g.setColor(Color.WHITE);
		g.fillRect(0,0,WIDTH,HEIGHT);
		g.setColor(Color.BLACK);
		g.drawString("FPS: " + averageFPS,100,100);
		
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
	}
	
	private void gameDraw(){
		Graphics g2 = this.getGraphics();
		g2.drawImage(image,0,0,null);
		g2.dispose();
	}
	
	public void keyTyped(KeyEvent key){}
	
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
