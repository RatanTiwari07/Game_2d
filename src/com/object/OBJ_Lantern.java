package com.object;

import com.entity.Entity;
import com.main.GamePanel;

public class OBJ_Lantern extends Entity {

	GamePanel gp;

	public OBJ_Lantern (GamePanel gp) {
		super(gp);
		this.gp = gp;

		type = light;
		lightRadius = 200;
		price = 200;

		direction = "down";
		name = "Lantern";
		description = "illuminate surroundings\nduring night time";
		down1 = setup ("/objects/lantern",gp.tileSize,gp.tileSize);
	}

}
