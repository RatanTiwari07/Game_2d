package com.main;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class UtilityTool {

	public BufferedImage scaleImage (BufferedImage original, int width, int height) {

		BufferedImage image = new BufferedImage(width, height, original.getType());
		Graphics2D g2 = image.createGraphics();
		g2.drawImage(original, 0, 0, width, height ,null);
		g2.dispose();

		return image;
	}

	public int findMidLength () {
		return 0;
	}

}
