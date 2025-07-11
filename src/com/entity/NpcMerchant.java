package com.entity;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.main.GamePanel;
import com.main.UtilityTool;
import com.object.OBJ_Axe;
import com.object.OBJ_Key;
import com.object.OBJ_Manna;
import com.object.OBJ_Red_Potion;
import com.object.OBJ_Shield_Blue;
import com.object.OBJ_Shield_Wood;
import com.object.OBJ_Weapon_Normal;

public class NpcMerchant extends Entity {

	BufferedImage image = null;

	public NpcMerchant(GamePanel gp) {
		super(gp);
		name = "old-man";
		direction = "down";
		speed = 1;
		getImage();
		setDialogue();
		gp.ui.npc = this;
		addItems();
	}

	public void addItems () {

		inventory.add(new OBJ_Red_Potion(gp));
		inventory.add(new OBJ_Manna(gp));
		inventory.add(new OBJ_Key(gp));
		inventory.add(new OBJ_Axe(gp));
		inventory.add(new OBJ_Shield_Blue(gp));
		inventory.add(new OBJ_Shield_Wood(gp));
		inventory.add(new OBJ_Weapon_Normal(gp));
	}

	public void getImage() {

		UtilityTool uTool = new UtilityTool ();
		try {
			down1 = uTool.scaleImage(ImageIO.read(getClass().getResourceAsStream("/oldman/merchant_down_1.png")), gp.tileSize, gp.tileSize);
			down2 = uTool.scaleImage(ImageIO.read(getClass().getResourceAsStream("/oldman/merchant_down_2.png")), gp.tileSize, gp.tileSize);
			up1 = uTool.scaleImage(ImageIO.read(getClass().getResourceAsStream("/oldman/merchant_down_1.png")), gp.tileSize, gp.tileSize);
			up2 = uTool.scaleImage(ImageIO.read(getClass().getResourceAsStream("/oldman/merchant_down_2.png")), gp.tileSize, gp.tileSize);
			right1 = uTool.scaleImage(ImageIO.read(getClass().getResourceAsStream("/oldman/merchant_down_1.png")), gp.tileSize, gp.tileSize);
			right2 = uTool.scaleImage(ImageIO.read(getClass().getResourceAsStream("/oldman/merchant_down_2.png")), gp.tileSize, gp.tileSize);
			left1 = uTool.scaleImage(ImageIO.read(getClass().getResourceAsStream("/oldman/merchant_down_1.png")), gp.tileSize, gp.tileSize);
			left2 = uTool.scaleImage(ImageIO.read(getClass().getResourceAsStream("/oldman/merchant_down_2.png")), gp.tileSize, gp.tileSize);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setDialogue () {

		dialogues[0] = "Hello! Lad\nI have some good stuff\nThat you must look about";
	}

	@Override
	public void speak () {

		super.speak();

		gp.gameState = gp.tradeState;
	}

	@Override
	public void setAction () {

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









