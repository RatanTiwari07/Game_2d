package com.object;

import com.entity.Entity;
import com.main.GamePanel;

public class OBJ_Heart extends Entity {

	GamePanel gp;

	public OBJ_Heart (GamePanel gp) {
		super (gp);
		this.gp = gp;

		type = pickable;

		name = "heart";
		image = setup("/objects/heart_blank",gp.tileSize,gp.tileSize);
		image2 = setup("/objects/heart_half",gp.tileSize,gp.tileSize);
		image3 = setup("/objects/heart_full",gp.tileSize,gp.tileSize);
		direction = "down";
		down1 = image3;
		value = 1;
	}


	@Override
	public boolean useItem (Entity entity) {

		entity.life += value;
		gp.ui.addMessage("Heath +"+value);

		return true;
	}
}









