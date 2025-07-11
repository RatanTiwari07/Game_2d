package com.object;

import com.entity.Entity;
import com.main.GamePanel;

public class OBJ_Tent extends Entity {

	GamePanel gp;

	public OBJ_Tent (GamePanel gp) {

		super (gp);
		this.gp = gp;

		type = obstacle;

		price = 150;
		name = "Tent";
		direction = "down";
		description = "It will help you to\nsurpass the night";
		down1 = setup("/objects/tent", gp.tileSize, gp.tileSize);
	}

	@Override
	public void interact () {

		if (gp.enManager.lightning.currentState == gp.enManager.lightning.night) {

			gp.enManager.lightning.currentState ++;
			gp.enManager.lightning.stateCounter = 0;

			gp.ui.currentDialogue = "Good Morning !";
			gp.gameState = gp.singleDialogueState;

		} else {

			gp.ui.currentDialogue = "What are you doing";
			gp.gameState = gp.singleDialogueState;

		}
	}
}
