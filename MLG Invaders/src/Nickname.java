import java.awt.BorderLayout;
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
public class Nickname extends JFrame{

	public Nickname(){
		JPanel nickPanel = new JPanel();
		JPanel belowPanel = new JPanel();
		JTextField nick = new JTextField(20);
		JButton ok = new JButton("Ok");
		JButton cancel = new JButton("Cancel");
		JLabel label = new JLabel("Please enter your nickname here");
		
		belowPanel.add(ok, BorderLayout.WEST);
		belowPanel.add(cancel, BorderLayout.EAST);
		nickPanel.add(label, BorderLayout.NORTH);
		nickPanel.add(nick, BorderLayout.CENTER);
		nickPanel.add(belowPanel, BorderLayout.SOUTH);
		add(nickPanel);
		
		
		
		ActionListener exitEvent = new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				nickPanel.setVisible(false);
			}
		};
		
		cancel.addActionListener(exitEvent);
	}
	
	
	
	
}
