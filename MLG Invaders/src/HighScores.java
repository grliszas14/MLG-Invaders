import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class HighScores {
	
	private JFrame frame;
	private String nick1;
	private String nick2;
	private String nick3;
	private String nick4;
	private String nick5;
	
	private int result1;
	private int result2;
	private int result3;
	private int result4;
	private int result5;
	
	private String place1;
	private String place2;
	private String place3;
	private String place4;
	private String place5;
	
	
	public HighScores(){
		
		place1 = (nick1 + " " + result1);
		place2 = (nick2 + " " + result2);
		place3 = (nick3 + " " + result3);
		place4 = (nick4 + " " + result4);
		place5 = (nick5 + " " + result5);
		frame = new JFrame();
		frame.setSize(300, 400);
		frame.setTitle("High Scores");
		JPanel wyniki = new JPanel();
		wyniki.setLayout(new BoxLayout(wyniki, BoxLayout.PAGE_AXIS));
		JButton close = new JButton("---- Close ----");
		JLabel first = new JLabel(place1);
		JLabel second = new JLabel(place2);
		JLabel third = new JLabel(place3);
		JLabel fourth = new JLabel(place4);
		JLabel fifth = new JLabel(place5);
		
		wyniki.add(first);
		wyniki.add(second);
		wyniki.add(third);
		wyniki.add(fourth);
		wyniki.add(fifth);
		wyniki.setAlignmentX(Component.CENTER_ALIGNMENT);
		frame.add(wyniki, BorderLayout.NORTH);
		frame.add(close, BorderLayout.SOUTH);
		//frame.pack();
		
		frame.setVisible(true);
		
		
		ActionListener exitEvent = new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				frame.dispose();
			}
		};
		
		close.addActionListener(exitEvent);
	}
}
