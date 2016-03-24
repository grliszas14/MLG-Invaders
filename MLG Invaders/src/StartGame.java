import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.swing.*;

public class StartGame {
	
	public static void main(String[] args) {
		/*
		 * aglaglafdgllandfbiuaen
		 * @id galgorgj
		 */
		JFrame startWindow = new StartWindow();
		startWindow.setTitle("Space Invaders");
		startWindow.setSize(300,400);
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
		
		//JToolBar sideBar = new JToolBar();
		//sideBar.setOrientation(VERTICAL);
		
		
		JMenu file = new JMenu("Plik");
		JMenu help = new JMenu("Pomoc");
		menuBar.add(file);
		menuBar.add(help);
		
		JMenuItem newGame = new JMenuItem("Nowa gra");
		JMenuItem options = new JMenuItem("Opcje");
		JMenuItem exit = new JMenuItem("Wyjœcie");
		JMenuItem sound = new JMenuItem("DŸwiêk");
		JMenuItem instructions = new JMenuItem("Instrukcja gry");
		JMenuItem aboutUs = new JMenuItem("O nas");
		file.add(newGame);
		file.add(options);
		file.add(sound);
		file.add(exit);
		help.add(instructions);
		help.add(aboutUs);
		
		JButton newGame_Button = new JButton(startGamePic);
		JButton options_Button = new JButton(optionsPic);
		
		final JToolBar toolbar = new JToolBar();
		toolbar.add(newGame_Button);
		toolbar.add(options_Button);
		add(toolbar, BorderLayout.NORTH);
		
		ActionListener startGameEvent = new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				Sounds.playSound("/Sounds/MLGAirHorn.wav");
			}
		};
		
		ActionListener aboutUsEvent = new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				JOptionPane.showMessageDialog(null,
						"Grzegorz Wojciechowski\nJakub Szajner");
			}
		};

		aboutUs.addActionListener(aboutUsEvent);
		
		ActionListener exitEvent = new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				System.exit(0);
			}
		};

		newGame_Button.addActionListener(startGameEvent);
		newGame.addActionListener(startGameEvent);
		exit.addActionListener(exitEvent);
	}
}