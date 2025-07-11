package com.object;

import com.entity.Entity;
import com.main.GamePanel;

public class OBJ_Shield_Wood extends Entity {

	public OBJ_Shield_Wood (GamePanel gp) {
		super(gp);

		price = 30;

		name = "Wood Shield";
		type = shield;
		defense = 1;
		description = "Shield made up of\nwood";
		down1 = setup("/objects/shield_wood",gp.tileSize,gp.tileSize);
		direction = "down";
	}
}
