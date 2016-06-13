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
	private String place1;
	private String place2;
	private String place3;
	private String place4;
	private String place5;
	
	
	public HighScores(){
		
		place1 = ("S³awek 562");
		place2 = ("Kulpan 480");
		place3 = ("Czarek 70");
		place4 = ("Matylda 2");
		place5 = ("Glinek -80");
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
