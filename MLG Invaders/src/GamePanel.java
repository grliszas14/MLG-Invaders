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
	
	
	public static int HEIGHT = 		Integer.parseInt(Config.getProperties().getProperty("GameHeight"));	
	public static int WIDTH = 		Integer.parseInt(Config.getProperties().getProperty("GameWidth"));	
	public static int HEIGHTPANEL =	Integer.parseInt(Config.getProperties().getProperty("SidePanelHeight"));	
	public static int WIDTHPANEL =	Integer.parseInt(Config.getProperties().getProperty("SidePanelWidth"));	
	
	private BufferedImage image;
	private Graphics2D g;
	private Graphics2D g2;
	private int FPS = Integer.parseInt(Config.getProperties().getProperty("FPS"));
	private double averageFPS;
	private int numberOfEnemies = Integer.parseInt(Config.getProperties().getProperty("numberOfEnemies"));
	
	private static Player player;
	public static ArrayList<Bullet> bullets;
	public static ArrayList<Enemy> enemies;
	public static ArrayList<PowerUp> powerups;
	public static ArrayList<Bomb> bombs;
	public static ArrayList<Explosion> explosions;
	private Image background;
	
	
	private long waveStartTimer;
	private long waveStartTimerDiff;
	private int waveNumber;
	private boolean waveStart;
	private int waveDelay = 2000;	// pars
	
	// tests 15.05.16
	/**
	 * Ukryty bufor
	 */
	//Image offscr = null;
	
	/**
	 * kontekst graficzny ukrytego bufora
	 */
	//Graphics offscrgr = null;
	
	
	/**
	 * create JPanel 
	 * focus
	 */
	public GamePanel( ){ 
		super();	// create JPanel with double buffer
		setPreferredSize(new Dimension(WIDTH + WIDTHPANEL, HEIGHT + HEIGHTPANEL)); //TU TRZEBA BEDZIE DODAC FUNKCJE DOWOLNEGO ROZSZERZANIA OKNA
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
		/// tests 15.05.16
		// setPreferredSize(new Dimension(WIDTH + WIDTHPANEL, HEIGHT + HEIGHTPANEL));
		//offscr = createImage(WIDTH + WIDTHPANEL, HEIGHT + HEIGHTPANEL);
		//offscrgr = offscr.getGraphics();
	
		
	}
	
	
	/**
	 * Load background
	 * Create ArrayLists for enemies and bullets
	 * Adding enemies
	 * Game loop, FPS
	 */
	public void run(){
		running = true;
		
		
		image = new BufferedImage(WIDTH + WIDTHPANEL, HEIGHT + HEIGHTPANEL, BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) image.getGraphics();
		g.setRenderingHint(
				RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(
				RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
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
			
				gameUpdate();
			
			URDTimeMillis = (System.nanoTime() - startTime) / 1000000;
			
			waitTime = targetTime - URDTimeMillis;
			
			
			// kontrola zmiennych 
			//System.out.println(targetTime );
			//System.out.println(URDTimeMillis);
			//System.out.println(waitTime);
			//System.out.println(" \t\t\t ...");
			
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
		
		g.setColor(new Color(10,10,10));
		g.fillRect(0, 0, WIDTH, HEIGHT);
		g.setColor(Color.WHITE);
		g.setFont(new Font("Century Gothic", Font.PLAIN, 20));
		String end = "G A M E  O V E R";
		int lengthend = (int) g.getFontMetrics().getStringBounds(end, g).getWidth();
		g.drawString(end, (WIDTH - lengthend) / 2, HEIGHT / 2 );
		String wynik = "Twoj wynik: " + getPlayer().score;
		int lengthwynik = (int) g.getFontMetrics().getStringBounds(wynik, g).getWidth();
		g.drawString(wynik, (WIDTH - lengthwynik)/2, HEIGHT / 2 + 30);
		g2 = (Graphics2D) this.getGraphics(); // poprzednio: Graphics g2 = this.getGraphics();
		g2.drawImage(image,0,0,null); // na ostatnim miejscu obiekt ktory ma byc powiadomiony, ze rysowanie sie udalo tzw ImageObserver
		g2.dispose();
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
		
		/**
		 *  Tworzy okno i rysuje 
		 */
		
			
		g.setColor(Color.WHITE);
		g.drawImage(background,0,0,null);
		g.fillRect(WIDTH,0,WIDTHPANEL,HEIGHTPANEL);
		//g.setColor(Color.BLACK);
		//g.drawString("FPS: " + averageFPS,440,300);
		g.setColor(Color.BLUE);
		
		
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
		
		g.drawString(scor, WIDTH + (WIDTHPANEL - lengthscor)/ 2, HEIGHT / 12);
		
		g.drawString(power, WIDTH + (WIDTHPANEL - lengthpower)/ 2, 5 * HEIGHT / 12);
		g.drawString(life, WIDTH + (WIDTHPANEL - lengthlife)/ 2, 9 *HEIGHT / 12);
		//g.drawString("Score: " + getPlayer().score, 300, 530);
		//g.drawString("Lives: " + getPlayer().lives, 300, 510);
		g.drawString(valuescore, WIDTH + (WIDTHPANEL - lengthvaluescor)/ 2, 2 * HEIGHT /12);
		g.setColor(Color.YELLOW);
		g.fillRect(WIDTH + 55, 6 * HEIGHT / 12, getPlayer().getPower() * 16 , 16);
		g.setColor(Color.YELLOW.darker());
		g.setStroke(new BasicStroke(2));
		for( int i = 0; i < getPlayer().getRequiredPower(); i++){
		 g.drawRect(WIDTH + 55 + 16 * i, 6 * HEIGHT / 12, 16,16);
		}
		for(int i = 0; i < player.getLives(); i++){
			g.setColor(Color.WHITE);
			g.fillOval(WIDTH + 70 + (20 * i), 10 * HEIGHT / 12, (int) 10 * 2, (int) 10 * 2);
			((Graphics2D) g).setStroke( new BasicStroke(3));
			g.setColor(Color.WHITE.darker());
			g.drawOval(WIDTH + 70 + (20 * i), 10 * HEIGHT / 12, (int) 10 * 2, (int) 10 * 2);
			((Graphics2D) g).setStroke( new BasicStroke(1));
		}
		
		
		//draw player
		getPlayer().draw(g);
		
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
		
		// draw bombs
		for( int i = 0; i < bombs.size(); i++){
			bombs.get(i).draw(g);
		}
		
		// draw explosions
		for( int i = 0; i < explosions.size(); i++){
			explosions.get(i).draw(g);
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
		
		
		
		/**
		 *  poprzednie gameDraw
		 *  tutaj dopiero pokazujemy wszystko na ekran 
		 */
		
		g2 = (Graphics2D) this.getGraphics(); // poprzednio: Graphics g2 = this.getGraphics();
		g2.drawImage(image,0,0,null); // na ostatnim miejscu obiekt ktory ma byc powiadomiony, ze rysowanie sie udalo tzw ImageObserver
		g2.dispose();
	}
	
	
	/**
	 * tworzy przeciwnikow w zaleznosci od numeru fali
	 */
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


	public static Player getPlayer() {
		return player;
	}


	public static void setPlayer(Player player) {
		GamePanel.player = player;
	}
}
