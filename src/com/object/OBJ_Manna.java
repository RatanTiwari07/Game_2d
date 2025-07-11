package com.object;

import com.entity.Entity;
import com.main.GamePanel;

public class OBJ_Manna extends Entity {

	GamePanel gp;

	public OBJ_Manna(GamePanel gp) {

		super(gp);
		this.gp = gp;

		price = 25;

		type = pickable;
		name = "Manna";
		direction = "down";
		value = 1;
		description = "It is fuel for \nfiring in combats";
		image = setup("/objects/manacrystal_full", gp.tileSize, gp.tileSize);
		image2 = setup("/objects/manacrystal_blank", gp.tileSize, gp.tileSize);
		down1 = image;
	}

	@Override
	public boolean useItem (Entity entity) {

		entity.manna += value;
		gp.ui.addMessage("Manna +"+value);

		return true;
	}
}
