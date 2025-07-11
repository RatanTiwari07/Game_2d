package com.main;

public class EventHandler {

	GamePanel gp;
	EventRect eventRect [][];
	boolean canTouchEvent = false;
	int prevEventX = 0;
	int prevEventY = 0;
	int tempMap;
	int tempWorldX;
	int tempWorldY;

	public EventHandler (GamePanel gp) {

		this.gp = gp;
		eventRect = new EventRect [gp.maxWorldCol][gp.maxWorldRow];

		int col = 0;
		int row = 0;
		while (col < gp.maxWorldCol && row < gp.maxWorldRow) {

			eventRect[col][row] = new EventRect();
			eventRect[col][row].x = 23;
			eventRect[col][row].y = 23;
			eventRect[col][row].defaultX = eventRect[col][row].x;
			eventRect[col][row].defaultY = eventRect[col][row].y;
			eventRect[col][row].height = 2;
			eventRect[col][row].width = 2;

			row++;
			if (row == gp.maxWorldRow) {
				row = 0;
				col++;
			}
		}
	}

	public void checkEvent () {

		int xdistance = Math.abs(gp.player.worldX - prevEventX);
		int ydistance = Math.abs(gp.player.worldY - prevEventY);

		if (Math.max(xdistance, ydistance) > gp.tileSize)
			canTouchEvent = true;

		if (hit(0,27,15,"any"))
			damagePit(gp.singleDialogueState);

		if (hit(0,23,12,"up"))
			healingPool(gp.singleDialogueState);

		if (hit(0,10,39,"any"))
			teleport(1,13,12);

		if (hit(1,12,13,"any"))
			teleport(0,23,22);
	}

	public boolean hit (int map, int col, int row, String reqDirection) {

		boolean hit = false;

		if (map == gp.currentMap) {

			gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
			gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;

			eventRect[col][row].x = eventRect[col][row].x + col*gp.tileSize;
			eventRect[col][row].y = eventRect[col][row].y + row*gp.tileSize;

			if (gp.player.solidArea.intersects(eventRect[col][row])) {
				if (((gp.player.direction == reqDirection || reqDirection == "any") && canTouchEvent )) {
					hit = true;
					prevEventX = gp.player.worldX;
					prevEventY = gp.player.worldY;
					canTouchEvent = false;
				}
			}

			gp.player.solidArea.x = gp.player.defaultSolidX;
			gp.player.solidArea.y = gp.player.defaultSolidY;

			eventRect[col][row].x = eventRect[col][row].defaultX;
			eventRect[col][row].y = eventRect[col][row].defaultY;

		}

		return hit;
	}

	public void damagePit (int gameState) {

		gp.player.life -= 1;
		gp.gameState = gameState;
		gp.ui.currentDialogue = "You are dumped into a pit\n Taken a damage";
	}

	public void healingPool (int gameState) {

		gp.player.life = gp.player.maxLife;
		gp.gameState = gameState;
		gp.ui.currentDialogue = "You drinked a water\nHealth is filled";
		gp.asetter.setMonster();
	}

	public void teleport (int map, int col, int row) {

		gp.gameState = gp.transitionState;

		prevEventX = col * gp.tileSize;
		prevEventY = row * gp.tileSize;

		tempWorldX = gp.tileSize * row;
		tempWorldY = gp.tileSize * col;
		tempMap = map;
	}
}












