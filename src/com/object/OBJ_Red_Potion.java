package com.object;

import com.entity.Entity;
import com.main.GamePanel;

public class OBJ_Red_Potion extends Entity {

	GamePanel gp;

	public OBJ_Red_Potion(GamePanel gp) {
		super(gp);
		this.gp = gp;

		price = 50;

		stackable = true;
		type = consumable;
		value = 5;
		name = "Red Potion";
		description = "It will increase\nyour health by " +value;
		direction = "down";
		down1 = setup("/objects/potion_red", gp.tileSize, gp.tileSize);
	}

	@Override
	public boolean useItem (Entity entity) {


		if (entity.life != entity.maxLife) {
			entity.life += value;
			gp.ui.addMessage("Red Potion consumed");
			gp.ui.addMessage("Health +5");
			return true;
		} else {
			gp.ui.currentDialogue = "What are you doing";
			gp.gameState = gp.singleDialogueState;
			return false;
		}
	}
}








