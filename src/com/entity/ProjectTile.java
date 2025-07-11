package com.entity;

import java.awt.Color;

import com.main.GamePanel;

public class ProjectTile extends Entity {

	Entity user;

	public ProjectTile(GamePanel gp) {
		super(gp);
//		solidArea = new Rectangle(15,15,18,18);
	}

	@Override
	public Color getParticleColour() {
		return Color.RED;
	}
	@Override
	public int getParticleSize() {
		return 8;
	}
	@Override
	public int getParticleSpeed() {
		return 1;
	}
	@Override
	public int getParticleMaxLife() {
		return 15;
	}

	public boolean haveResources() {

		if (gp.player.manna >= this.useCost) {
			return true;
		}
		return false;
	}

	public void subractResources() {

		gp.player.manna -= this.useCost;
	}

	public void set(int worldX, int worldY, String direction, boolean alive, Entity user) {

		this.worldX = worldX;
		this.worldY = worldY;
		this.direction = direction;
		this.alive = alive;
		this.user = user;
		this.life = maxLife;
	}

	@Override
	public void update() {

		if (user.type == player) {
			int i = gp.colCheck.checkEntity(this, gp.monster[gp.currentMap]);
			if (i != -1) {
				gp.player.damageMonster(i, attack , knockBackPower);
				generateParticle(this,gp.monster[gp.currentMap][i]);
				alive = false;
			}
		}
		if (user.type != player) {
			boolean contact = gp.colCheck.checkPlayer(this);
			if (!gp.player.invincible && contact) {
				generateParticle(this,gp.player);
				user.damagePlayer(attack);
				alive = false;
			}
		}

//		it will not pass through the solid tiles

//		collisionOn = false;
//
//		gp.colCheck.checkTile(this);
//
//		if (this.collisionOn) {
//			life -= 40 ;
//		}

		switch (direction) {
		case "up":
			worldY -= speed;
			break;
		case "down":
			worldY += speed;
			break;
		case "right":
			worldX += speed;
			break;
		case "left":
			worldX -= speed;
			break;
		}

		life--;
		if (life <= 0) {
			alive = false;
		}

		spriteCounter++;
		if (spriteCounter == 10) {
			if (spriteNum == 1) {
				spriteNum = 2;
			} else {
				spriteNum = 1;
			}
			spriteCounter = 0;
		}

		if (shotAvailableCounter < 30) {
			shotAvailableCounter++;
		}
	}
}
