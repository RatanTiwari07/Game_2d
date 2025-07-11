package com.tile;

import java.awt.Color;
import java.awt.Graphics2D;

import com.entity.Entity;
import com.main.GamePanel;

public class InteractiveTiles extends Entity{

	GamePanel gp;
	public boolean destructable = true;

	public InteractiveTiles(GamePanel gp, int col, int row) {

		super(gp);
		this.gp = gp;

		this.worldY = col * gp.tileSize;
		this.worldX = row * gp.tileSize;
	}

	public boolean isCorrectWeapon (Entity entity) {return false;}

	public InteractiveTiles getDisposedForm () {return null;}

	@Override
	public void update () {

		if (invincible) {
			invincibleCounter ++;
			if (invincibleCounter > 20) {
				invincibleCounter = 0;
				invincible = false;
			}
		}
	}
	@Override
	public void draw (Graphics2D g2) {

		int screenX = worldX - gp.player.worldX + gp.player.screenX;
		int screenY = worldY - gp.player.worldY + gp.player.screenY;

		if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX
			&& worldX - gp.tileSize < gp.player.worldX + gp.player.screenX
			&& worldY + gp.tileSize > gp.player.worldY - gp.player.screenY
			&& worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

			g2.drawImage(down1, screenX, screenY, null);
		}
	}

	@Override
	public Color getParticleColour() {
		return Color.DARK_GRAY ;
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
}











