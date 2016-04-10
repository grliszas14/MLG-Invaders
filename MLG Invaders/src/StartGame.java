import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.swing.*;

public class StartGame {
	
	public static void main(String[] args) throws IOException {
		/*
		 * Setting params of start window
		 */
		StartWindow startWindow = new StartWindow();
		startWindow.setTitle("Space Invaders");
		
		int WindowHeight	= Integer.parseInt(GetProperties.getProperties().getProperty("WindowHeight"));
		int WindowWidth		= Integer.parseInt(GetProperties.getProperties().getProperty("WindowWidth"));
		startWindow.setSize(WindowWidth,WindowHeight);	
										
		startWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		startWindow.show();
		
		
		
		// test wczytywania konfigu 
		
		//String test = GetProperties.getProperties().getProperty("mlg.WindowHeight");
		//System.out.println(test);					
	}
}