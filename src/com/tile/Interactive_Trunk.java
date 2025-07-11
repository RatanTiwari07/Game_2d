package com.tile;

import com.main.GamePanel;

public class Interactive_Trunk extends InteractiveTiles{

	public Interactive_Trunk(GamePanel gp, int worldX, int worldY) {
		super(gp, worldX, worldY);

		direction = "down";
		down1 = setup("/tile/trunk",gp.tileSize,gp.tileSize);
		destructable = false;

		solidArea.x = 0;
		solidArea.y = 0;
		solidArea.height = 0;
		solidArea.width = 0;
		defaultSolidX = 0;
		defaultSolidY = 0;
	}
}
