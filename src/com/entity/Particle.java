package com.entity;

import java.awt.Color;
import java.awt.Graphics2D;

import com.main.GamePanel;

public class Particle extends Entity{

	Entity target;
	int xd;
	int yd;
	int speed;
	int size;
	Color color;

	public Particle (GamePanel gp, Entity target, int speed, int life, int size, Color color, int xd, int yd) {
		super(gp);

		this.target = target;
		this.speed = speed;
		this.maxLife = life;
		this.life = life;
		this.size = size;
		this.color = color;
		this.xd = xd;
		this.yd = yd;

		int offset = gp.tileSize/2 + size/2;

		this.worldX = target.worldX + offset;
		this.worldY = target.worldY + offset;
	}

	@Override
	public void update () {

		life --;

		worldX += speed * xd;
		worldY += speed * yd;

		if (life <= maxLife/2) {
			yd ++;
		}
		if (life < 0) {
			alive = false;
		}
	}

	@Override
	public void draw (Graphics2D g2) {

		int screenX = worldX - gp.player.worldX + gp.player.screenX;
		int screenY = worldY - gp.player.worldY + gp.player.screenY;

		g2.setColor(color);

		g2.fillRect(screenX, screenY, size, size);

	}
}
















