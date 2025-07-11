package com.object;

import com.entity.Entity;
import com.main.GamePanel;

public class OBJ_Chest extends Entity {

	Entity loot;
	boolean opened;
	GamePanel gp;

	public OBJ_Chest (GamePanel gp , Entity loot) {
		super(gp);
		this.gp = gp;

		this.loot = loot;
		type = obstacle;

		name = "Chest";
		image = setup("/objects/chest",gp.tileSize,gp.tileSize);
		image2 = setup("/objects/chest_opened",gp.tileSize,gp.tileSize);
		down1 = image;
		collision = true;
		direction = "down";
	}

	@Override
	public void interact () {

		String message;

		if (!opened) {

			if (gp.player.canObtain(loot)) {

				opened = true;
				down1 = image2;
				message = "You find a treasure box /n And obtained " + loot.name + " !";

			} else {

				message = "You find a treasure box /n But you don't have a space to carry it !";

			}

		} else {

			message = "It is empty ! /n Actually already looted !";

		}

		gp.ui.currentDialogue = message;
		gp.gameState = gp.singleDialogueState;

	}
}











