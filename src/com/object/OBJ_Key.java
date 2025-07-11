package com.object;

import com.entity.Entity;
import com.main.GamePanel;

public class OBJ_Key extends Entity {

	GamePanel gp;

	public OBJ_Key (GamePanel gp) {
		super (gp);
		this.gp = gp;

		price = 25;
		type = consumable;

		stackable = true;
		direction = "down";
		name = "Key";
		description = "Used to open a door";
		down1 = setup ("/objects/key",gp.tileSize,gp.tileSize);
	}

	@Override
	public boolean useItem (Entity entity) {

		int itemIndex = entity.getDetected(entity,gp.obj,"door");

		if (itemIndex != 999) {
			gp.obj[gp.currentMap][itemIndex] = null;
			gp.ui.currentDialogue = "you opened a door using a key !";
			gp.gameState = gp.singleDialogueState;
			return true;
		} else {
			gp.ui.currentDialogue = "What are you doing !";
			gp.gameState = gp.singleDialogueState;
			return false;
		}
	}
}










