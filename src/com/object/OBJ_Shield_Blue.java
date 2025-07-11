package com.object;

import com.entity.Entity;
import com.main.GamePanel;

public class OBJ_Shield_Blue extends Entity {

	public OBJ_Shield_Blue (GamePanel gp) {
		super (gp);

		price = 50;

		type =  shield;
		name = "Blue Shield";
		defense = 2;
		description = "Blue shield it will\ndefineitily lesser \nthe damage";
		down1 = setup ("/objects/shield_blue", gp.tileSize, gp.tileSize);
		direction = "down";
	}

}
