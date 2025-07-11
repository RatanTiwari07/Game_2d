package com.tile;

import com.entity.Entity;
import com.main.GamePanel;

public class Interactive_tree extends InteractiveTiles {

	public Interactive_tree(GamePanel gp, int worldX, int worldY) {
		super(gp, worldX, worldY);

		direction = "down";
		down1 = setup("/tile/drytree",gp.tileSize,gp.tileSize);
		destructable = true;
		life = 1;
		maxLife = life;
	}

	@Override
	public boolean isCorrectWeapon (Entity entity) {

		boolean isCorrect = false;
		if (entity.currentWeapon.name.equalsIgnoreCase("Wood Axe")) {
			isCorrect = true;
		}
		return isCorrect;
	}

	@Override
	public InteractiveTiles getDisposedForm () {

		return new Interactive_Trunk(gp,worldY/gp.tileSize,worldX/gp.tileSize);
	}
}












