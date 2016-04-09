import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.*;

public class Logo extends JPanel {

	public static int LOGOHEIGHT = 480;
	public static int LOGOWIDTH = 360;
	
	public Logo(){
		super();
		setPreferredSize(new Dimension(LOGOWIDTH,LOGOHEIGHT)); //TU TRZEBA BEDZIE DODAC FUNKCJE DOWOLNEGO ROZSZERZANIA OKNA
		showPNG();
		setFocusable(true);
	}
	public void showPNG(){
		ImageIcon icon = new ImageIcon("src/Icons/majn.png");
		JLabel label = new JLabel();
		label.setIcon(icon);
		add(label);
	}
}
