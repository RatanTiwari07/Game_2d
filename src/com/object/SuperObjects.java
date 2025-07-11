package com.object;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.main.GamePanel;
import com.main.UtilityTool;

public class SuperObjects {

	GamePanel gp;
	public BufferedImage image, image2, image3;
	public String name;
	public int worldX;
	public int worldY;
	public boolean collision = false;
	public Rectangle solidArea = new Rectangle(0,0,48,48);
	UtilityTool uTool = new UtilityTool ();
	public int defaultSolidX = solidArea.x;
	public int defaultSolidY = solidArea.y;

	public void draw (Graphics2D g2) {

		int screenX = worldX - gp.player.worldX + gp.player.screenX;
		int screenY = worldY - gp.player.worldY + gp.player.screenY ;

		if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
			worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
			worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
			worldY - gp.tileSize < gp.player.worldY + gp.player.screenY ) {

			g2.drawImage(image, screenX, screenY, null);
		}
	}
}















