import javax.swing.JPanel;
import java.awt.*;
import java.awt.image.*;

public class GamePanel extends JPanel implements Runnable {
	
	private Thread thread;
	private boolean running;
	public static int HEIGHT = 400;
	public static int WIDTH = 400;
	
	private BufferedImage image;
	private Graphics2D g;
	
	public GamePanel(){
		super();
		//TU TRZEBA BEDZIE DODAC FUNKCJE DOWOLNEGO ROZSZERZANIA OKNA
		setFocusable(true);
		requestFocus();
	}
		
	public void addNotify(){
		super.addNotify();
		if(thread == null){
			thread = new Thread(this);
			thread.start();
		}
	}
	
	public void run(){
		
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) image.getGraphics();
		running = true;
		//Game loop
		while(running){
			gameUpdate();
			gameRender();
			gameDraw();
		}
	}
	
	private void gameUpdate(){
		
	}
	
	private void gameRender(){
		g.setColor(Color.WHITE);
		g.fillRect(0,0,WIDTH,HEIGHT);
		g.setColor(Color.BLACK);
		g.drawString("TEST STRING",100,100);
	}
	
	private void gameDraw(){
		Graphics g2 = this.getGraphics();
		g2.drawImage(image,0,60,null);
		g2.dispose();
	}
}
