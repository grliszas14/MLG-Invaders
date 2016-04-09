import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.swing.*;

public class StartGame {
	
	public static void main(String[] args) {
		/*
		 * Setting params of start window
		 */
		JFrame startWindow = new StartWindow();
		startWindow.setTitle("Space Invaders");
		startWindow.setSize(700,700);
		startWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		startWindow.show();
	}
}

class StartWindow extends JFrame {
	public StartWindow() {

		
		ImageIcon startGamePic = new ImageIcon("src/Icons/startGamePic.png");
		ImageIcon optionsPic = new ImageIcon("src/Icons/optionsPic.png");
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		GamePanel gamePanel = new GamePanel();
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
		
		
		add(gamePanel, BorderLayout.SOUTH);
		//add(logo, BorderLayout.SOUTH);
		JToolBar toolbar = new JToolBar();
		toolbar.add(newGame_Button);
		toolbar.add(options_Button);
		add(toolbar, BorderLayout.NORTH);
		
		
		
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