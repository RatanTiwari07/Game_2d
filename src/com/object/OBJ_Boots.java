package com.object;

import com.entity.Entity;
import com.main.GamePanel;

public class OBJ_Boots extends Entity {

	public OBJ_Boots (GamePanel gp) {
		super(gp);

		name = "Boots";
		down1 = setup("/images/boots",gp.tileSize,gp.tileSize);
	}

}











