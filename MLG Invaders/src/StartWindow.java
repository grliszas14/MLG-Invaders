import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;

public class StartWindow extends JFrame {
	
	//public Config config ;
	//public int a =5;
	public StartWindow() throws IOException {

		//Config config = new Config() ;
		ImageIcon startGamePic = new ImageIcon("src/Icons/startGamePic.png");
		ImageIcon optionsPic = new ImageIcon("src/Icons/optionsPic.png");
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		GamePanel gamePanel = new GamePanel(); //config
		Logo logo = new Logo();
		
		//JToolBar sideBar = new JToolBar();
		//sideBar.setOrientation(VERTICAL);
		
		
		JMenu file = new JMenu("Plik");
		JMenu help = new JMenu("Pomoc");
		menuBar.add(file);
		menuBar.add(help);
		
		/*
		 * Adding buttons
		 */
		JMenuItem newGame = new JMenuItem("Nowa gra");
		JMenuItem options = new JMenuItem("Opcje");
		JMenuItem exit = new JMenuItem("Wyjœcie");
		JMenuItem instructions = new JMenuItem("Instrukcja gry");
		JMenuItem aboutUs = new JMenuItem("O nas");
		file.add(newGame);
		file.add(options);
		file.add(exit);
		help.add(instructions);
		help.add(aboutUs);
		
		JButton newGame_Button = new JButton(startGamePic);
		JButton options_Button = new JButton(optionsPic);
		
		
		add(gamePanel, BorderLayout.CENTER);
		
		//add(logo, BorderLayout.SOUTH);
		JToolBar toolbar = new JToolBar();
		toolbar.add(newGame_Button);
		toolbar.add(options_Button);
		add(toolbar, BorderLayout.NORTH);
		/*toolbar.setFocusable(false);
		toolbar.setEnabled(false);
		
		gamePanel.set
		
		gamePanel.setFocusable(true);
		gamePanel.setEnabled(true);
		System.out.println("toolbar fokjusejbyl: " + toolbar.isFocusable());
		System.out.println("GamePnale fokjusejbyl: " + gamePanel.isFocusable());
		/*gamePanel.setFocusable(true);
		gamePanel.requestFocus();
		gamePanel.requestDefaultFocus();
		*/
		
		
		/*
		 * Methods to make buttons work
		 */
		ActionListener startGameEvent = new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				Sounds.playSound("/Sounds/MLGAirHorn.wav");
				Nickname nickname = new Nickname();
				nickname.setTitle("Nickname");
				nickname.setSize(300,140);
				//nickname.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				nickname.show();
				//add(gamePanel, BorderLayout.SOUTH);
				//gamePanel.requestFocus();
				
			}
		};
		
		ActionListener aboutUsEvent = new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				JOptionPane.showMessageDialog(null,
						"Grzegorz Wojciechowski\nJakub Szajner\n"
						+ "Studenci 4 semestru elektroniki Politechniki Warszawskiej", "About us", JOptionPane.INFORMATION_MESSAGE);
			}
		};

		ActionListener instructionsEvent = new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				JOptionPane.showMessageDialog(null,
						"Szczegó³owe zasady gry: ", "Instructions", JOptionPane.INFORMATION_MESSAGE);
			}
		};
		
		
		ActionListener exitEvent = new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				System.exit(0);
			}
		};
		/*
		 * Setting events for buttons
		 */
		newGame_Button.addActionListener(startGameEvent);
		newGame.addActionListener(startGameEvent);
		instructions.addActionListener(instructionsEvent);
		aboutUs.addActionListener(aboutUsEvent);
		exit.addActionListener(exitEvent);
	}
}
