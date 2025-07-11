package com.entity;

import java.awt.Rectangle;
//import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.main.GamePanel;
import com.main.UtilityTool;

public class NpcOldMan extends Entity {

	BufferedImage image = null;

	public NpcOldMan(GamePanel gp) {
		super(gp);
		name = "old-man";
		direction = "down";
		speed = 1;
		solidArea = new Rectangle(8,16,32,32);
		getImage();
		setDialogue();
	}

	public void getImage() {

		UtilityTool uTool = new UtilityTool ();
		try {
			down1 = uTool.scaleImage(ImageIO.read(getClass().getResourceAsStream("/oldman/oldman_down_1.png")), gp.tileSize, gp.tileSize);
			down2 = uTool.scaleImage(ImageIO.read(getClass().getResourceAsStream("/oldman/oldman_down_2.png")), gp.tileSize, gp.tileSize);
			up1 = uTool.scaleImage(ImageIO.read(getClass().getResourceAsStream("/oldman/oldman_up_1.png")), gp.tileSize, gp.tileSize);
			up2 = uTool.scaleImage(ImageIO.read(getClass().getResourceAsStream("/oldman/oldman_up_2.png")), gp.tileSize, gp.tileSize);
			right1 = uTool.scaleImage(ImageIO.read(getClass().getResourceAsStream("/oldman/oldman_right_1.png")), gp.tileSize, gp.tileSize);
			right2 = uTool.scaleImage(ImageIO.read(getClass().getResourceAsStream("/oldman/oldman_right_2.png")), gp.tileSize, gp.tileSize);
			left1 = uTool.scaleImage(ImageIO.read(getClass().getResourceAsStream("/oldman/oldman_left_1.png")), gp.tileSize, gp.tileSize);
			left2 = uTool.scaleImage(ImageIO.read(getClass().getResourceAsStream("/oldman/oldman_left_2.png")), gp.tileSize, gp.tileSize);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setDialogue () {

		dialogues[0] = "Hello! Lad";
		dialogues[1] = "So, You have come to this island\nto find the treasure ?";
		dialogues[2] = "I used to be a great Wizard but\nnow... I am bit too old for\ntaking adventure";
		dialogues[3] = "Well! Good Luck on you";
	}

	@Override
	public void speak () {
		super.speak();
		pathOn =  true;
	}

	@Override
	public void setAction () {

		if (pathOn) {

			int goalCol = (gp.player.worldX + 10) / gp.tileSize ;
			int goalRow = (gp.player.worldY + 10) / gp.tileSize ;

			searchPath(goalCol, goalRow , true);

		}
		else {

			actionLockCounter ++;

			if (actionLockCounter > 120) {

				int num = random.nextInt(100)+1;

				if(num <= 25) {
					direction = "up";
				}
				if(num>25 && num<=50) {
					direction = "down";
				}
				if(num>50 && num<=75) {
					direction = "left";
				}
				if (num>75 && num<=100) {
					direction = "right";
				}

				actionLockCounter = 0;
			}
		}
	}
}









