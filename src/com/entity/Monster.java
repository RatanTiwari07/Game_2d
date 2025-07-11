package com.entity;

import java.util.Random;

import com.main.GamePanel;
import com.object.OBJ_Coin_Bronze;
import com.object.OBJ_Heart;
import com.object.OBJ_Manna;
import com.object.OBJ_Rock;

public class Monster extends Entity {

	public Monster (GamePanel gp) {
		super (gp);

		name = "GreenSlime";
		type = green_slime;
		speed = 1;
		defaultSpeed = speed;
		maxLife = 4;
		life = maxLife;
		direction = "down";
		attack = 5;
		defense = 0;
		exp = 3;

		solidArea.x = 3 ;
		solidArea.y = 10 ;
		solidArea.height = 30 ;
		solidArea.width = 42 ;
		defaultSolidX = solidArea.x;
		defaultSolidY = solidArea.y;

		projectTile = new OBJ_Rock(gp);

		getImage();
	}

	public void getImage () {

		up1 = setup("/monster/greenslime_down_1",gp.tileSize,gp.tileSize);
		up2 = setup("/monster/greenslime_down_2",gp.tileSize,gp.tileSize);
		down1 = setup("/monster/greenslime_down_1",gp.tileSize,gp.tileSize);
		down2 = setup("/monster/greenslime_down_2",gp.tileSize,gp.tileSize);
		left1 = setup("/monster/greenslime_down_1",gp.tileSize,gp.tileSize);
		left2 = setup("/monster/greenslime_down_2",gp.tileSize,gp.tileSize);
		right1 = setup("/monster/greenslime_down_1",gp.tileSize,gp.tileSize);
		right2 = setup("/monster/greenslime_down_2",gp.tileSize,gp.tileSize);
	}

	@Override
	public void setAction () {

		if (pathOn) {

			int goalCol = goalCol(gp.player);
			int goalRow = goalRow(gp.player);

			searchPath(goalCol, goalRow , false);

			int i = new Random().nextInt(100)+1;

			if (i>90 && !projectTile.alive && shotAvailableCounter == 30) {

				projectTile.set(worldX, worldY, direction, true, this);

				for (int j=0; j<gp.projectList[gp.currentMap].length; j++) {
					if (gp.projectList[gp.currentMap][j] == null) {
						gp.projectList[gp.currentMap][j] = projectTile;
						break;
					}
				}

				shotAvailableCounter = 0;
			}

		} else {



			actionLockCounter ++;

			if (actionLockCounter > 120) {

				int num = random.nextInt(100)+1;

				if(num <= 25) {
					direction = "up";
				}
				if(num>25 && num<=50) {
					direction = "down";
				}
				if(num>50 && num<=75) {
					direction = "left";
				}
				if (num>75 && num<=100) {
					direction = "right";
				}

				actionLockCounter = 0;
			}

			int i = new Random().nextInt(100)+1;

			if (i>99 && !projectTile.alive && shotAvailableCounter == 30) {

				projectTile.set(worldX, worldY, direction, true, this);
				for (int j=0; j<gp.projectList[gp.currentMap].length; j++) {
					if (gp.projectList[gp.currentMap][j] == null) {
						gp.projectList[gp.currentMap][j] = projectTile;
						break;
					}
				}
				shotAvailableCounter = 0;
			}
		}
	}

	@Override
	public void damageReaction () {

		this.pathOn = true;
	}

	@Override
	public void checkDrop () {

		int i = new Random().nextInt(100)+1;

		if (i>=1 && i<50) {
			dropItem(new OBJ_Coin_Bronze(gp));
		}
		if (i>=50 && i<75) {
			dropItem(new OBJ_Heart(gp));
		}
		if (i>=75 && i<100) {
			dropItem(new OBJ_Manna(gp));
		}
	}

	@Override
	public void dropItem (Entity entity) {

		for (int i=0; i<gp.obj[gp.currentMap].length; i++) {
			if (gp.obj[gp.currentMap][i] == null) {
				gp.obj[gp.currentMap][i]=entity;
				gp.obj[gp.currentMap][i].worldX = worldX;
				gp.obj[gp.currentMap][i].worldY = worldY;
				break;
			}
		}
	}
}
















