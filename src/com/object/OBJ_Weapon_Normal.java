package com.object;

import com.entity.Entity;
import com.main.GamePanel;

public class OBJ_Weapon_Normal extends Entity {

	public OBJ_Weapon_Normal (GamePanel gp) {
		super (gp);

		price = 30;

		name = "Normal Sword";
		knockBackPower = 2;
		type = weapon;
		attack = 1;
		description = "Sword made up of\nwood. It have little\nlong range";
		down1 = setup("/objects/sword_normal",gp.tileSize,gp.tileSize);
		direction = "down";
		attackArea.height = 36;
		attackArea.width = 36;
	}
}