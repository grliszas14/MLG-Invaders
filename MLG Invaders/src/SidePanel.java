import javax.swing.ImageIcon;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.image.*;

public class SidePanel extends JPanel {
	
	private BufferedImage image;
	private int score = 0;
	private int lives = 0;
	private Graphics2D g;
	public static int HEIGHT = 	Integer.parseInt(Config.getProperties().getProperty("SidePanelHeight"));	
	public static int WIDTH = 	Integer.parseInt(Config.getProperties().getProperty("SidePanelWidth"));
	
	
	
	public SidePanel(){
		super();
		setPreferredSize(new Dimension(200, 590));
		this.setBackground(Color.WHITE);
		setVisible(true);
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) image.getGraphics();
		paint(g);
		setFocusable(true);
		
	}
	
	public void paint(Graphics g){
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		String s = "Wynik: ";
		String l = "Zycia:";
		String p = Integer.toString(score);
		String h = Integer.toString(lives);
		
		g.setColor(Color.BLACK);
		g.setFont(new Font("Century Gothic", Font.PLAIN, 18));
		int lengths = (int) g.getFontMetrics().getStringBounds(s, g).getWidth();
		int lengthl = (int) g.getFontMetrics().getStringBounds(l, g).getWidth();
		int lengthp = (int) g.getFontMetrics().getStringBounds(p, g).getWidth();
		int lengthh = (int) g.getFontMetrics().getStringBounds(h, g).getWidth();
		g.drawString(s, (WIDTH - lengths)/ 2, HEIGHT / 8);
		g.drawString(p, (WIDTH - lengthp)/ 2, 2 * HEIGHT / 8);
		g.drawString(l, (WIDTH - lengthl)/ 2, 5* HEIGHT /8);
		g.drawString(h, (WIDTH - lengthh)/ 2, 6* HEIGHT /8);
		
	}
}
