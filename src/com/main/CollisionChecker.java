package com.main;

import com.entity.Entity;

public class CollisionChecker {

	GamePanel gp;

	public CollisionChecker(GamePanel gp) {
		this.gp = gp;
	}

	public void checkTile(Entity e) {

		int entityLeftWorldX = e.worldX + e.solidArea.x;
		int entityRightWorldX = e.worldX + e.solidArea.x + e.solidArea.width;
		int entityTopWorldY = e.worldY + e.solidArea.y;
		int entityBottomWorldY = e.worldY + e.solidArea.y + e.solidArea.height;

		int entityLeftCol = entityLeftWorldX / gp.tileSize;
		int entityRightCol = entityRightWorldX / gp.tileSize;
		int entityTopRow = entityTopWorldY / gp.tileSize;
		int entityBottomRow = entityBottomWorldY / gp.tileSize;

		int tileNum1=0;
		int tileNum2=0;

		switch (e.direction) {

		case "up":
			entityTopRow = (entityTopWorldY - e.speed) / gp.tileSize;
			tileNum1 = gp.tileM.mapNum[gp.currentMap][entityLeftCol][entityTopRow];
			tileNum2 = gp.tileM.mapNum[gp.currentMap][entityRightCol][entityTopRow];
			break;


		case "down":
			entityBottomRow = (entityBottomWorldY + e.speed) / gp.tileSize;
			tileNum1 = gp.tileM.mapNum[gp.currentMap][entityLeftCol][entityBottomRow];
			tileNum2 = gp.tileM.mapNum[gp.currentMap][entityRightCol][entityBottomRow];
			break;

		case "left":
			entityLeftCol = (entityLeftWorldX - e.speed) / gp.tileSize;
			tileNum1 = gp.tileM.mapNum[gp.currentMap][entityLeftCol][entityTopRow];
			tileNum2 = gp.tileM.mapNum[gp.currentMap][entityLeftCol][entityBottomRow];
			break;

		case "right":
			entityRightCol = (entityRightWorldX + e.speed) / gp.tileSize;
			tileNum1 = gp.tileM.mapNum[gp.currentMap][entityRightCol][entityTopRow];
			tileNum2 = gp.tileM.mapNum[gp.currentMap][entityRightCol][entityBottomRow];
			break;
		}

		if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
			e.collisionOn = true;
		}
	}

	public int checkObject(Entity entity, boolean player) {

		int index = -1;

		for (int i = 0; i < gp.obj[gp.currentMap].length; i++) {

			if (gp.obj[gp.currentMap][i] != null) {

				// GET ENTITY SOLID AREA COORDINATES
				entity.solidArea.x = entity.worldX + entity.solidArea.x;
				entity.solidArea.y = entity.worldY + entity.solidArea.y;

				// GET OBJECTS SOLID AREA COORDINATES
				gp.obj[gp.currentMap][i].solidArea.x = gp.obj[gp.currentMap][i].worldX + gp.obj[gp.currentMap][i].solidArea.x;
				gp.obj[gp.currentMap][i].solidArea.y = gp.obj[gp.currentMap][i].worldY + gp.obj[gp.currentMap][i].solidArea.y;

				switch (entity.direction) {

				case "up": entity.solidArea.y -= entity.speed; break;
				case "down": entity.solidArea.y += entity.speed; break;
				case "left": entity.solidArea.x -= entity.speed; break;
				case "right": entity.solidArea.x += entity.speed; break;
				}

				if (entity.solidArea.intersects(gp.obj[gp.currentMap][i].solidArea)) {
					gp.player.collisionOn = true;
					if (player) {
						index = i;
					}
				}

				gp.obj[gp.currentMap][i].solidArea.x = gp.obj[gp.currentMap][i].defaultSolidX;
				gp.obj[gp.currentMap][i].solidArea.y = gp.obj[gp.currentMap][i].defaultSolidY;
				entity.solidArea.x = entity.defaultSolidX;
				entity.solidArea.y = entity.defaultSolidY;
			}
		}

		return index;
	}

	public int checkEntity(Entity entity, Entity target[]) {

		int index = -1;

		for (int i = 0; i < target.length; i++) {

			if (target[i] != null) {

				// GET ENTITY SOLID AREA COORDINATES
				entity.solidArea.x = entity.worldX + entity.solidArea.x;
				entity.solidArea.y = entity.worldY + entity.solidArea.y;

				// GET OBJECTS SOLID AREA COORDINATES
				target[i].solidArea.x = target[i].worldX + target[i].solidArea.x;
				target[i].solidArea.y = target[i].worldY + target[i].solidArea.y;

				switch (entity.direction) {

				case "up": entity.solidArea.y -= entity.speed; break;
				case "down": entity.solidArea.y += entity.speed; break;
				case "left": entity.solidArea.x -= entity.speed; break;
				case "right": entity.solidArea.x += entity.speed; break;
				}

				if (entity.solidArea.intersects(target[i].solidArea)) {
					if (target[i] != entity) {
						entity.collisionOn = true;
						index = i;
					}
				}

				target[i].solidArea.x = target[i].defaultSolidX;
				target[i].solidArea.y = target[i].defaultSolidY;
				entity.solidArea.x = entity.defaultSolidX;
				entity.solidArea.y = entity.defaultSolidY;
			}
		}

		return index;
	}

	public boolean checkPlayer (Entity entity) {

		boolean contact = false;

		// GET ENTITY SOLID AREA COORDINATES
		entity.solidArea.x = entity.worldX + entity.solidArea.x;
		entity.solidArea.y = entity.worldY + entity.solidArea.y;

		// GET OBJECTS SOLID AREA COORDINATES
		gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
		gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;

		switch (entity.direction) {
		case "up": entity.solidArea.y -= entity.speed; break;
		case "down": entity.solidArea.y += entity.speed; break;
		case "left": entity.solidArea.x -= entity.speed; break;
		case "right": entity.solidArea.x += entity.speed; break;
		}

		if (entity.solidArea.intersects(gp.player.solidArea)) {
			entity.collisionOn = true;
			contact = true;
		}

		gp.player.solidArea.x = gp.player.defaultSolidX;
		gp.player.solidArea.y = gp.player.defaultSolidY;
		entity.solidArea.x = entity.defaultSolidX;
		entity.solidArea.y = entity.defaultSolidY;

		return contact;
	}

}
