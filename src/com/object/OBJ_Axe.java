package com.object;

import com.entity.Entity;
import com.main.GamePanel;

public class OBJ_Axe extends Entity {

	public OBJ_Axe(GamePanel gp) {
		super(gp);

		price = 100;

		attack = 1;
		knockBackPower = 5;
		type = weapon;
		name = "Wood Axe";
		description = "It is little rusty,\nbut can used to cut\nsome trees";
		down1 = setup("/objects/axe",gp.tileSize, gp.tileSize);
		direction = "down";
		attackArea.height = 30;
		attackArea.width = 30;
	}
}