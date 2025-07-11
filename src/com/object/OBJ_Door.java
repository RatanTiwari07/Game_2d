package com.object;

import com.entity.Entity;
import com.main.GamePanel;

public class OBJ_Door extends Entity {

	GamePanel gp;

	public OBJ_Door (GamePanel gp) {
		super(gp);
		this.gp = gp;

		type = obstacle;

		name="door";
		down1=setup("/objects/door",gp.tileSize,gp.tileSize);
		direction = "down";
		collision = true;
	}

	@Override
	public void interact () {

		gp.ui.currentDialogue = "You need a key to open this !";
		gp.gameState = gp.singleDialogueState;

	}
}
