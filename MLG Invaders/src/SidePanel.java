import javax.swing.ImageIcon;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.image.*;

public class SidePanel extends JPanel {
	
	private BufferedImage image;
	private int score;
	private int livesSidePanel = 0;
	private Graphics2D g;
	public static int HEIGHT = 	Integer.parseInt(Config.getProperties().getProperty("SidePanelHeight"));	
	public static int WIDTH = 	Integer.parseInt(Config.getProperties().getProperty("SidePanelWidth"));
	public static Player player2;
	
	
	public SidePanel(){
		super();
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.setBackground(Color.WHITE);
		setVisible(true);
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) image.getGraphics();
		paint(g);
		setFocusable(true);
		player2 = new Player();
		
	}
	
	public void paint(Graphics g){
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		String s = "Wynik: ";
		String l = "Zycia:";
		String p = Integer.toString(score);
		String h = Integer.toString(livesSidePanel);
		String pu = "Moc: ";
		
		g.setColor(Color.BLACK);
		g.setFont(new Font("Century Gothic", Font.PLAIN, 18));
		int lengths = (int) g.getFontMetrics().getStringBounds(s, g).getWidth();
		int lengthl = (int) g.getFontMetrics().getStringBounds(l, g).getWidth();
		int lengthp = (int) g.getFontMetrics().getStringBounds(p, g).getWidth();
		int lengthh = (int) g.getFontMetrics().getStringBounds(h, g).getWidth();
		g.drawString(s, (WIDTH - lengths)/ 2, HEIGHT / 12);
		g.drawString(p, (WIDTH - lengthp)/ 2, 2 * HEIGHT / 12);
		
		//g.drawString(player.getScore(), (WIDTH - lengthp)/ 2, 2 * HEIGHT / 8);
		
		g.drawString(l, (WIDTH - lengthl)/ 2, 8* HEIGHT /12);
		g.drawString(pu,(WIDTH - lengthl)/ 2, 5* HEIGHT /12);
		
		//tymczasowe rysowanie mocy
		for(int i = 0; i < 5; i++){
			g.setColor(Color.YELLOW);
			g.fillRect(50 + (20 * i), 6 * HEIGHT / 12, (int) 10 * 2, (int) 10 * 2);
			((Graphics2D) g).setStroke( new BasicStroke(3));
			g.setColor(Color.YELLOW.darker());
			g.drawRect(50 + (20 * i), 6 * HEIGHT / 12, (int) 10 * 2, (int) 10 * 2);
			((Graphics2D) g).setStroke( new BasicStroke(1));
		}
		
		/* rysowanie mocy powinno wygladac tak:
		 * g.setColor(Color.YELLOW);
		 * g.fillRect(50 + (20 * i), 6 * HEIGHT / 12, player.getPower() * 8 , 8);
		 * g.setColor(Color.YELLOW.darker());
		 * g.setStroke(new BasicStroke(2));
		 * for( int i = 0; i < player.getRequiredPower(); i++){
		 * 	g.drawRect(50 + 8 *i,  6 * HEIGHT / 12, 8,8);
		 * }
		 */
		
		// tu zamiast 3 ma byc player.getLives();
		for(int i = 0; i < 3; i++){
			g.setColor(Color.WHITE);
			g.fillOval(70 + (20 * i), 9 * HEIGHT / 12, (int) 10 * 2, (int) 10 * 2);
			((Graphics2D) g).setStroke( new BasicStroke(3));
			g.setColor(Color.WHITE.darker());
			g.drawOval(70 + (20 * i), 9 * HEIGHT / 12, (int) 10 * 2, (int) 10 * 2);
			((Graphics2D) g).setStroke( new BasicStroke(1));
		}
		
		
		
	}
	
	public static Player getPlayer() {
		return player2;
	}


	public static void setPlayer(Player player2) {
		SidePanel.player2 = player2;
	}
}
