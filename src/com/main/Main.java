package com.main;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class Main {

	public static JFrame frame;

	public static void main(String[] args) {

		frame = new JFrame ();
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setUndecorated(false);
		frame.setResizable(false);
		frame.setLocationByPlatform(true);
		frame.setLocation(300,100);
		frame.setTitle("2D Adventure");

		GamePanel gamePanel = new GamePanel ();
		frame.add(gamePanel);

		frame.pack();
//		gamePanel.setUpGame();
		frame.setVisible(true);
		gamePanel.startGameThread();
	}

}

