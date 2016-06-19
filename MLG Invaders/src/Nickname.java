import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
/**
* Window waiting for nickname to write in
*/
public class Nickname {
	/**
	 * Frame of nickname window
	 */
	private JFrame frame;
	/**
	 * Variable storing nickname
	 */
	private String nickname;
	/**
	 * Height of game panel
	 */
	public static int HEIGHT = 		Integer.parseInt(Config.getProperties().getProperty("GameHeight"));	
	/**
	 * Width of game panel
	 */
	public static int WIDTH = 		Integer.parseInt(Config.getProperties().getProperty("GameWidth"));	
	/**
	 * Height of side panel
	 */
	public static int HEIGHTPANEL =	Integer.parseInt(Config.getProperties().getProperty("SidePanelHeight"));	
	/**
	 * Width of side panel
	 */
	public static int WIDTHPANEL =	Integer.parseInt(Config.getProperties().getProperty("SidePanelWidth"));	
		
	/**
	 * Constructor of a class, making window to write nick in and start game
	 * @param gameFrame 
	 * @param gamePanel
	 * @param logo
	 */
	public Nickname(JFrame gameFrame, GamePanel gamePanel, Logo logo){
		JPanel nickPanel = new JPanel();
		JPanel belowPanel = new JPanel();
		JTextField nick = new JTextField(20);
		JButton ok = new JButton("Ok");
		JButton cancel = new JButton("Cancel");
		JLabel label = new JLabel("Please enter your nickname here");
		
		frame = new JFrame();
		frame.setSize(300,140);
		frame.setTitle("Nickname");
		belowPanel.add(ok, BorderLayout.WEST);
		belowPanel.add(cancel, BorderLayout.EAST);
		nickPanel.add(label, BorderLayout.NORTH);
		nickPanel.add(nick, BorderLayout.CENTER);
		nickPanel.add(belowPanel, BorderLayout.SOUTH);
		frame.add(nickPanel);
		frame.setVisible(true);
	
		ActionListener exitEvent = new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				frame.dispose();
			}
		};
		
		ActionListener okEvent = new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				nickname = nick.getText();
				gameFrame.remove(logo);
				
				gameFrame.add(gamePanel, BorderLayout.CENTER);
				gameFrame.setSize(new Dimension(WIDTH + WIDTHPANEL, HEIGHT + 100));
				frame.dispose();
				gameFrame.revalidate();
			}
		};
		
		cancel.addActionListener(exitEvent);
		ok.addActionListener(okEvent);
	}	
}