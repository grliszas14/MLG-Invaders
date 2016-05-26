import java.awt.BorderLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
/**
*Okienko które oczekuje wpisania nicku przy rozpoczêciu nowej gry
*/
public class Nickname {

		private JFrame frame;
		
	public Nickname(){
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
		//frame.pack();
		
		
		ActionListener exitEvent = new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				frame.dispose();
			}
		};
		
		ActionListener okEvent = new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				String nickname = nick.getText();
				
				frame.dispose();
				
			}
		};
		
		cancel.addActionListener(exitEvent);
		ok.addActionListener(okEvent);
	}
	
	
	
	
}