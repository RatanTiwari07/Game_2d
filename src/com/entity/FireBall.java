package com.entity;

import com.main.GamePanel;

public class FireBall extends ProjectTile {

	public FireBall (GamePanel gp) {
		super (gp);

		name = "Fireball";
		knockBackPower = 0;
		attack = 5;
		maxLife = 180;
		speed = 7;
		alive = false;
		useCost = 1;
		getImage ();
	}

	public void getImage () {

		down1 = setup("/images/fireball_down_1",gp.tileSize,gp.tileSize);
		down2 = setup("/images/fireball_down_2",gp.tileSize,gp.tileSize);
		up1 = setup("/images/fireball_up_1",gp.tileSize,gp.tileSize);
		up2 = setup("/images/fireball_up_2",gp.tileSize,gp.tileSize);
		right1 = setup("/images/fireball_right_1",gp.tileSize,gp.tileSize);
		right2 = setup("/images/fireball_right_2",gp.tileSize,gp.tileSize);
		left1 = setup("/images/fireball_left_1",gp.tileSize,gp.tileSize);
		left2 = setup("/images/fireball_left_2",gp.tileSize,gp.tileSize);
	}
}










