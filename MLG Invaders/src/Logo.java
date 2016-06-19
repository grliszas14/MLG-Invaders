import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.*;
/**
 * Class displaying logo of the game
 * @author Grzegorz Wojciechowski, Jakub Szajner
 *
 */
public class Logo extends JPanel {
	/**
	 * Height of the logo
	 */
	public static int LOGOHEIGHT = 480;
	/**
	 * Width of the logo
	 */
	public static int LOGOWIDTH = 360;
	
	/**
	 * Makes panel and shows logo in it
	 */
	public Logo(){
		super();
		setPreferredSize(new Dimension(LOGOWIDTH,LOGOHEIGHT));
		showPNG();
	//	setFocusable(true);
	}
	/**
	 * Method showing logo 
	 */
	public void showPNG(){
		ImageIcon icon = new ImageIcon("src/Icons/majn.png");
		JLabel label = new JLabel();
		label.setIcon(icon);
		add(label);
	
	}
}