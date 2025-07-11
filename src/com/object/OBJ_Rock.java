package com.object;

import com.entity.ProjectTile;
import com.main.GamePanel;

public class OBJ_Rock extends ProjectTile {

	GamePanel gp;

	public OBJ_Rock(GamePanel gp) {
		super(gp);

		this.gp = gp;

		name = "Rock";
		knockBackPower = 2;
		attack = 4;
		maxLife = 200;
		life = maxLife;
		direction = "down";
		useCost = 1;
		speed = 5;
		alive = false;

		getImage();
	}

	public void getImage () {

		down1 = setup("/images/rock_down_1",gp.tileSize,gp.tileSize);
		down2 = setup("/images/rock_down_1",gp.tileSize,gp.tileSize);
		up1 = setup("/images/rock_down_1",gp.tileSize,gp.tileSize);
		up2 = setup("/images/rock_down_1",gp.tileSize,gp.tileSize);
		right1 = setup("/images/rock_down_1",gp.tileSize,gp.tileSize);
		right2 = setup("/images/rock_down_1",gp.tileSize,gp.tileSize);
		left1 = setup("/images/rock_down_1",gp.tileSize,gp.tileSize);
		left2 = setup("/images/rock_down_1",gp.tileSize,gp.tileSize);
	}
}
