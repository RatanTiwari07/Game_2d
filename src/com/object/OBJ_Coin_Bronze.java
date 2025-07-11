package com.object;

import com.entity.Entity;
import com.main.GamePanel;

public class OBJ_Coin_Bronze extends Entity {

	GamePanel gp;

	public OBJ_Coin_Bronze(GamePanel gp) {
		super(gp);
		this.gp = gp;

		type = pickable;
		name = "Bronze Coin";
		description = "It will be used in\ntrading.\nBut, later on.";
		value = 1;
		direction = "down";
		down1 = setup("/objects/coin_bronze", gp.tileSize, gp.tileSize);
	}

	@Override
	public boolean useItem (Entity entity) {

		entity.coin += value;
		gp.ui.addMessage("Coins +"+value);

		return true;
	}
}
