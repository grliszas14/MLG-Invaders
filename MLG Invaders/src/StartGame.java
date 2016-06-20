import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.util.Scanner;

import javax.swing.*;
/**
 * Main class of whole game
 * @author Grzegorz Wojciechowski, Jakub Szajner
 *
 */
public class StartGame {
	
	/**
	 * Main function of game
	 */
	public static void main(String[] args) throws IOException {
		/**
		 * Variables storing ip address and port number
		 */
		String ip, port;
		/**
		 * Scanner to load input from user
		 */
		Scanner skaner = new Scanner(System.in);
	
		System.out.print("Podaj ip serwera: ");
		ip = skaner.nextLine();
		System.out.print("podaj port: ");
		port = skaner.nextLine();
		Client.connection("get_properties", ip, port);
		System.out.println("Status online: " + Config.getProperties().getProperty("server"));
		
		StartWindow startWindow = new StartWindow();				
	}
}
	
	/**
	 * Class in which GUI is laid
	 */
	class StartWindow{
		
		/**
		 * Frame of the game
		 */
		private JFrame gameFrame;
		/**
		 * Height of the game frame
		 */
		int WindowHeight	= Integer.parseInt(Config.getProperties().getProperty("WindowHeight"));
		/**
		 * Width of the game frame
		 */
		int WindowWidth		= Integer.parseInt(Config.getProperties().getProperty("WindowWidth"));

		/**
		 * Constructor of a class
		 * Here whole GUI is built
		 * Frames, buttons, actionListeners
		 */
		public StartWindow() throws IOException {

			gameFrame = new JFrame();
			gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			ImageIcon startGamePic = new ImageIcon("src/Icons/startGamePic.png");
			ImageIcon highScoresPic = new ImageIcon("src/Icons/highScoresPic.png");
			JMenuBar menuBar = new JMenuBar();
			gameFrame.setJMenuBar(menuBar);
			Logo logo = new Logo();

			JMenu file = new JMenu("Plik");
			JMenu help = new JMenu("Pomoc");
			menuBar.add(file);
			menuBar.add(help);
			
			/**
			 * Adding buttons
			 */
			JMenuItem newGame = new JMenuItem("Nowa gra");
			JMenuItem exit = new JMenuItem("Wyjscie");
			JMenuItem instructions = new JMenuItem("Instrukcja gry");
			JMenuItem aboutUs = new JMenuItem("O nas");
			file.add(newGame);
			file.add(exit);
			help.add(instructions);
			help.add(aboutUs);
			
			JButton newGame_Button = new JButton(startGamePic);
			JButton highScores_Button = new JButton(highScoresPic);
		
			gameFrame.add(logo, BorderLayout.CENTER);
			JToolBar toolbar = new JToolBar();
			toolbar.add(newGame_Button);
			toolbar.add(highScores_Button);
			gameFrame.add(toolbar, BorderLayout.NORTH);
			gameFrame.setVisible(true);
			gameFrame.pack();
				
			/**
			 * Methods to make buttons work
			 */
			ActionListener startGameEvent = new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					Sound.playSound("/Sounds/MLGAirHorn.wav");
					GamePanel gamePanel = new GamePanel();
					Nickname nicknameWindow = new Nickname(gameFrame, gamePanel, logo);			
				}
			};
			
			ActionListener highScoresEvent = new ActionListener() {
				public void actionPerformed(ActionEvent event){
					HighScores list = new HighScores();
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
							"Szczegolowe zasady gry: \nZabijaj przeciwnikow, unikaj spadajacych bomb\nzbieraj wypadajace bonusy\nSTEROWANIE:\nPoruszanie sie postacia: strzałki\nStrzał: SPACJA\nPauzowanie gry: p", "Instructions", JOptionPane.INFORMATION_MESSAGE);
				}
			};
			
			
			ActionListener exitEvent = new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					System.exit(0);
				}
			};
			/**
			 * Setting events for buttons
			 */
			newGame_Button.addActionListener(startGameEvent);
			highScores_Button.addActionListener(highScoresEvent);
			newGame.addActionListener(startGameEvent);
			instructions.addActionListener(instructionsEvent);
			aboutUs.addActionListener(aboutUsEvent);
			exit.addActionListener(exitEvent);
			
			gameFrame.setSize(WindowWidth,WindowHeight);
		}
		
	}