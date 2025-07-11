package com.main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.entity.Entity;
import com.object.OBJ_Coin_Bronze;
import com.object.OBJ_Heart;
import com.object.OBJ_Manna;

public class UI {

	BufferedImage heart_full, heart_half, heart_blank, manna_full, manna_blank, coin;
	Graphics2D g2;
	GamePanel gp;
	public String currentDialogue = "";
	Font arial28;
	Font arial80;
	Font colonna96;
	Font colonna32;
	Font trialFont;
	ArrayList<String> message = new ArrayList<>();
	ArrayList<Integer> messageCounter = new ArrayList<>();
	public int npcSlotCol = 0;
	public int npcSlotRow = 0;
	public int playerSlotCol = 0;
	public int playerSlotRow = 0;

	public int subState = 0;

	public Entity npc;

	public int transitionCounter = 0;

	public int commandNum;

	public UI(GamePanel gp) {
		this.gp = gp;
		arial28 = new Font("Purisa Bold", Font.PLAIN, 26);
		arial80 = new Font("Arial", Font.PLAIN, 80);
		colonna96 = new Font("x12y16pxMaruMonica", Font.BOLD, 75);
		colonna32 = new Font("x12y16pxMaruMonica", Font.BOLD, 32);
		trialFont = new Font("Arial", Font.PLAIN, 18);
		commandNum = 0;
		Entity heart = new OBJ_Heart(gp);
		heart_blank = heart.image;
		heart_half = heart.image2;
		heart_full = heart.image3;
		Entity manna = new OBJ_Manna(gp);
		manna_full = manna.image;
		manna_blank = manna.image2;
		coin = new OBJ_Coin_Bronze(gp).down1;
	}

	public void draw(Graphics2D g2) {

		this.g2 = g2;

		g2.setFont(arial80);
		g2.setColor(Color.WHITE);

		if (gp.gameState == gp.titleState) {
			drawTitleScreen();
		}
		if (gp.gameState == gp.playState) {
			drawPlayerLife();
			drawMessage();

			if (gp.keyH.debugPressed) {
				drawDebugScreen();
			}
		}
		if (gp.gameState == gp.pauseState) {
			drawPauseScreen();
		}
		if (gp.gameState == gp.dialogueState) {
			drawDialogueScreen();
		}
		if (gp.gameState == gp.singleDialogueState) {
			drawDialogueScreen();
		}
		if (gp.gameState == gp.characterState) {
			drawCharacterScreen();
			drawInventory(gp.player , true);
		}
		if (gp.gameState == gp.retryState) {
			drawRetryScreen();
		}
		if (gp.gameState == gp.transitionState) {
			drawTransition();
		}
		if (gp.gameState == gp.tradeState) {
			drawTradeState();
		}
	}

	public void addMessage(String text) {

		message.add(text);
		messageCounter.add(0);
	}

	public void drawMessage() {

		int messageX = gp.tileSize;
		int messageY = gp.tileSize * 4;

		g2.setColor(Color.WHITE);
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 28f));

		for (int i = 0; i < message.size(); i++) {

			if (message.get(i) != null) {

				g2.drawString(message.get(i), messageX, messageY);

				int counter = messageCounter.get(i) + 1;
				messageCounter.set(i, counter);
				messageY += 30;

				if (messageCounter.get(i) > 180) {
					message.remove(i);
					messageCounter.remove(i);
				}
			}
		}
	}

	public void drawTitleScreen() {

		String text = "BLUE BOY ADVENTURE";
		g2.setFont(colonna96);
		int x = getXforCenteredText(text);
		int y = gp.tileSize * 3;

		// TEXT SHADOW
		g2.setColor(Color.GRAY);
		g2.drawString(text, x + 4, y + 4);

		// MAIN TEXT
		g2.setColor(Color.WHITE);
		g2.drawString(text, x, y);

		// DRAW BOY IMAGE
		x = gp.screenWidth / 2 - (gp.tileSize * 2) / 2;
		y += gp.tileSize * 1;
		g2.drawImage(gp.player.down1, x, y, gp.tileSize * 2, gp.tileSize * 2, null);

		// MENU
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48));

		text = "New Game";
		x = getXforCenteredText(text);
		y += gp.tileSize * 4;
		g2.drawString(text, x, y);
		if (gp.ui.commandNum == 0) {
			g2.drawString(">", x - gp.tileSize, y);
		}

		text = "Load Game";
		x = getXforCenteredText(text);
		y += gp.tileSize;
		g2.drawString(text, x, y);
		if (gp.ui.commandNum == 1) {
			g2.drawString(">", x - gp.tileSize, y);
		}

		text = "Quit";
		x = getXforCenteredText(text);
		y += gp.tileSize;
		g2.drawString(text, x, y);
		if (gp.ui.commandNum == 2) {
			g2.drawString(">", x - gp.tileSize, y);
		}
	}

	public void drawPlayerLife() {

		int heartCount = 0;
		int i = 0;
		int x = gp.tileSize / 2;
		int y = gp.tileSize / 2;

		while (i < gp.player.life) {

			if (i < gp.player.life) {

				g2.drawImage(heart_half, x, y, null);
				i++;

				if (i < gp.player.life) {
					g2.drawImage(heart_full, x, y, null);
					i++;
				}

				heartCount++;
			}
			x += gp.tileSize;
		}

		while (heartCount < gp.player.maxLife / 2) {

			g2.drawImage(heart_blank, x, y, null);
			x += gp.tileSize;
			heartCount++;
		}

		int mannaCount = 1;
		x = gp.tileSize / 2;
		y = gp.tileSize + 30;

		while (mannaCount <= gp.player.manna) {
			g2.drawImage(manna_full, x, y, null);
			x += gp.tileSize - 15;
			mannaCount++;
		}

		while (mannaCount <= gp.player.maxManna) {
			g2.drawImage(manna_blank, x, y, null);
			x += gp.tileSize - 15;
			mannaCount++;
		}

	}

	public void drawPauseScreen() {

		String text = "PAUSED";
		g2.drawString(text, getXforCenteredText(text), gp.screenHeight / 2);

		g2.setColor(Color.BLACK);
		g2.setStroke(new BasicStroke(5));
		g2.fillRect(gp.tileSize * 4, gp.tileSize, gp.tileSize * 8, gp.tileSize * 10);
	}

	public void drawDialogueScreen() {

		int x = gp.tileSize * 2;
		int y = gp.tileSize / 2;
		int height = gp.tileSize * 4;
		int width = gp.screenWidth - gp.tileSize * 4;

		drawSubWindow(x, y, height, width);

		x += gp.tileSize / 2;
		y += gp.tileSize * 1.5;
		g2.setColor(new Color(255, 255, 255));
		g2.setFont(arial28);

		for (String line : currentDialogue.split("\n")) {
			g2.drawString(line, x, y);
			y += 40;
		}
	}

	public void drawCharacterScreen() {

		int frameX = gp.tileSize * 2;
		int frameY = gp.tileSize;
		int frameWidth = gp.tileSize * 5;
		int frameHeight = gp.tileSize * 10;

		drawSubWindow(frameX, frameY, frameHeight, frameWidth);

		g2.setColor(Color.WHITE);
		g2.setFont(colonna32);

		int textX = frameX + 20;
		int textY = frameY + gp.tileSize;
		final int lineHeight = 32;

		g2.drawString("Level", textX, textY);
		textY += lineHeight;
		g2.drawString("Life", textX, textY);
		textY += lineHeight;
		g2.drawString("Manna", textX, textY);
		textY += lineHeight;
		g2.drawString("Strength", textX, textY);
		textY += lineHeight;
		g2.drawString("Dexterity", textX, textY);
		textY += lineHeight;
		g2.drawString("Attack", textX, textY);
		textY += lineHeight;
		g2.drawString("Defense", textX, textY);
		textY += lineHeight;
		g2.drawString("Exp", textX, textY);
		textY += lineHeight;
		g2.drawString("Next Level", textX, textY);
		textY += lineHeight;
		g2.drawString("Coin", textX, textY);
		textY += lineHeight + 15;
		g2.drawString("Weapon", textX, textY);
		textY += lineHeight + 20;
		g2.drawString("Shield", textX, textY);
		textY += lineHeight;

		String text;
		int tailX = (frameX + frameWidth) - 20;
		textY = frameY + gp.tileSize;

		text = String.valueOf(gp.player.level);
		textX = getXforAlignedText(text, tailX);
		g2.drawString(text, textX, textY);
		textY += lineHeight;

		text = String.valueOf(gp.player.life) + "/" + String.valueOf(gp.player.maxLife);
		textX = getXforAlignedText(text, tailX);
		g2.drawString(text, textX, textY);
		textY += lineHeight;

		text = String.valueOf(gp.player.manna) + "/" + String.valueOf(gp.player.maxManna);
		textX = getXforAlignedText(text, tailX);
		g2.drawString(text, textX, textY);
		textY += lineHeight;

		text = String.valueOf(gp.player.strength);
		textX = getXforAlignedText(text, tailX);
		g2.drawString(text, textX, textY);
		textY += lineHeight;

		text = String.valueOf(gp.player.dexterity);
		textX = getXforAlignedText(text, tailX);
		g2.drawString(text, textX, textY);
		textY += lineHeight;

		text = String.valueOf(gp.player.attack);
		textX = getXforAlignedText(text, tailX);
		g2.drawString(text, textX, textY);
		textY += lineHeight;

		text = String.valueOf(gp.player.defense);
		textX = getXforAlignedText(text, tailX);
		g2.drawString(text, textX, textY);
		textY += lineHeight;

		text = String.valueOf(gp.player.exp);
		textX = getXforAlignedText(text, tailX);
		g2.drawString(text, textX, textY);
		textY += lineHeight;

		text = String.valueOf(gp.player.nextLevelExp);
		textX = getXforAlignedText(text, tailX);
		g2.drawString(text, textX, textY);
		textY += lineHeight;

		text = String.valueOf(gp.player.coin);
		textX = getXforAlignedText(text, tailX);
		g2.drawString(text, textX, textY);
		textY += lineHeight - 15;

		g2.drawImage(gp.player.currentWeapon.down1, tailX - gp.tileSize, textY, null);
		textY += gp.tileSize;

		g2.drawImage(gp.player.currentShield.down1, tailX - gp.tileSize, textY, null);
	}

	public void drawInventory(Entity entity, boolean cursor) {

		int frameX = 0;
		int frameY = 0;
		int frameHeight = gp.tileSize * 5;
		int frameWidth = gp.tileSize * 6;
		int slotCol = 0;
		int slotRow = 0;

		if (entity == gp.player) {
			frameX = gp.tileSize * 12;
			frameY = gp.tileSize;
			slotCol = playerSlotCol;
			slotRow = playerSlotRow;
		} else {
			frameX = gp.tileSize * 3;
			frameY = gp.tileSize;
			slotCol = npcSlotCol;
			slotRow = npcSlotRow;
		}

		drawSubWindow(frameX, frameY, frameHeight, frameWidth);

		// SLOT
		final int slotXstart = frameX + 20;
		final int slotYstart = frameY + 20;
		int slotX = slotXstart;
		int slotY = slotYstart;
		int slotSize = gp.tileSize + 3;

		// DRAW PLAYERS ITEMS
		for (int i = 0; i < entity.inventory.size(); i++) {

			if (entity.inventory.get(i) == entity.currentShield
					|| entity.inventory.get(i) == entity.currentWeapon
					|| entity.inventory.get(i) == entity.currentLight ) {
				g2.setColor(new Color(230, 233, 0));
				g2.fillRoundRect(slotX, slotY, gp.tileSize, gp.tileSize, 10, 10);
			}

			g2.drawImage(entity.inventory.get(i).down1, slotX, slotY, null);

			if (entity.inventory.get(i).amount > 1) {

				g2.setColor(Color.white);
				g2.setFont(colonna32);
				g2.drawString(String.valueOf(entity.inventory.get(i).amount),
					getXforAlignedText(String.valueOf(entity.inventory.get(i).amount), slotX+gp.tileSize), slotY+gp.tileSize );

			}

			slotX += slotSize;
			if (i == 4 || i == 9 || i == 14 || i == 19) {
				slotX = slotXstart;
				slotY += slotSize;
			}
		}

		// CURSOR

		if (cursor) {

			int cursorX = slotXstart + (slotSize * slotCol);
			int cursorY = slotYstart + (slotSize * slotRow);
			int cursorWidth = gp.tileSize;
			int cursorHeight = gp.tileSize;

			g2.setStroke(new BasicStroke(3));
			g2.setColor(Color.WHITE);
			g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);

			// DESCRIPTION WINDOW
			int dFrameY = frameY + frameHeight;
			int index = getIndexForInventory(slotCol , slotRow);

			if (index < entity.inventory.size()) {

				g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 28));

				frameHeight -= gp.tileSize;
				drawSubWindow(frameX, dFrameY, frameHeight, frameWidth);
				g2.drawString(entity.inventory.get(index).name, frameX += 20, dFrameY += 50);

				g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 28));

				for (String line : entity.inventory.get(index).description.split("\n")) {

					dFrameY += 40;
					g2.drawString(line, frameX, dFrameY);
				}
			}
		}
	}

	public void drawTransition() {

		transitionCounter ++;

		g2.setColor(new Color(0,0,0,255-transitionCounter));
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

		if (transitionCounter >= 40) {

			gp.player.worldX = gp.eHandler.tempWorldX;
			gp.player.worldY = gp.eHandler.tempWorldY;
			gp.currentMap = gp.eHandler.tempMap;
		}

		if (transitionCounter >= 60) {
			transitionCounter = 0;


			gp.gameState = gp.playState;
		}
	}

	public void drawTradeState() {

		switch (subState) {
		case 0 : drawSelectState(); break;
		case 1 : drawBuyState(); break;
		case 2 : drawSellState(); break;
		}

		gp.keyH.enterPressed = false;
	}

	public void drawSelectState () {

		drawDialogueScreen();

		int x = gp.tileSize*15;
		int y = gp.tileSize*5;

		drawSubWindow(x , y , gp.tileSize*4 , gp.tileSize*3);

		x += gp.tileSize;
		y += gp.tileSize;

		g2.setColor(Color.white);
		g2.setFont(g2.getFont().deriveFont(28f));

		g2.drawString("Buy", x, y);
		if (commandNum == 1) {
			g2.drawString(">", (int)(x-gp.tileSize*0.5), y);
			if (gp.keyH.enterPressed) {
				subState = 1;
			}
		}

		y += gp.tileSize;

		g2.drawString("Sell", x, y);
		if (commandNum == 2) {
			g2.drawString(">", (int)(x-gp.tileSize*0.5), y);
			if (gp.keyH.enterPressed) {
				subState = 2;
			}
		}

		y += gp.tileSize;

		g2.drawString("Leave", x, y);
		if (commandNum == 3) {
			g2.drawString(">", (int)(x-gp.tileSize*0.5), y);
			if (gp.keyH.enterPressed) {
				currentDialogue = "Come Again ! hehe ";
				gp.gameState = gp.singleDialogueState;
				drawDialogueScreen();
//				npc.speak();
			}
		}
	}

	public void drawBuyState () {

		// player inventory
		drawInventory(npc, true);

		// npc inventory
		drawInventory(gp.player, false);

		int x = gp.tileSize * 7;
		int y = (int)(gp.tileSize * 5.5);

		int index = getIndexForInventory(npcSlotCol,  npcSlotRow);

		if ( index < npc.inventory.size()) {

			int price = npc.inventory.get(index).price;
			drawSubWindow(x, y, gp.tileSize*1, gp.tileSize*2);
			g2.drawImage(coin,x+10,y+10,30,30,null);

			g2.setFont(g2.getFont().deriveFont(22f));
			y += 24;
			x = getXforAlignedText(price + "",x+gp.tileSize*2) - 20;

			g2.drawString(String.valueOf(price), x , y+10);
		}

		drawSubWindow(gp.tileSize*3,  gp.tileSize*10 , (int)(gp.tileSize*1.5), gp.tileSize*6);

		drawSubWindow(gp.tileSize*12,  gp.tileSize*10 , (int)(gp.tileSize*1.5), gp.tileSize*6);

		g2.setFont(g2.getFont().deriveFont(28f));

		g2.drawString("Press [ESC] to EXIT" , gp.tileSize*3+20, gp.tileSize*10+45 );

		g2.drawString("Coins : " + gp.player.coin , gp.tileSize*12+20, gp.tileSize*10+45 );

		if (gp.keyH.enterPressed) {

			if (gp.player.inventory.size() == gp.player.maxInventorySize) {

				currentDialogue = "You can't carry anymore !";
				gp.gameState = gp.singleDialogueState;
				drawDialogueScreen();
				subState = 0;

			}
			else if (npc.inventory.get(index).price > gp.player.coin) {

				currentDialogue = " You need more coins !";
				gp.gameState = gp.singleDialogueState;
				drawDialogueScreen();
				subState = 0;
			}
			else {

//				gp.player.inventory.add(npc.inventory.get(index));
				gp.player.canObtain(npc.inventory.get(index));
				gp.player.coin -= npc.inventory.get(index).price;
			}
		}
	}

	public void drawSellState () {

		// player inventory
		drawInventory(gp.player,true);

		int index = getIndexForInventory(playerSlotCol,  playerSlotRow);

		drawSubWindow(gp.tileSize*3,  gp.tileSize*10 , (int)(gp.tileSize*1.5), gp.tileSize*6);

		drawSubWindow(gp.tileSize*12,  gp.tileSize*10 , (int)(gp.tileSize*1.5), gp.tileSize*6);

		g2.setFont(g2.getFont().deriveFont(28f));

		g2.drawString("Press [ESC] to EXIT" , gp.tileSize*3+20, gp.tileSize*10+45 );

		g2.drawString("Coins : " + gp.player.coin , gp.tileSize*12+20, gp.tileSize*10+45 );

		if (gp.keyH.enterPressed) {

			if (gp.player.inventory.get(index) == gp.player.currentShield ||
					gp.player.inventory.get(index) == gp.player.currentWeapon) {

				currentDialogue = "You cannot sell equipped weapons";
				gp.gameState = gp.singleDialogueState;
				subState = 0;
			}  else {

				gp.player.coin += gp.player.inventory.get(index).price;

				if (gp.player.inventory.get(index).amount > 1) {

					gp.player.inventory.get(index).amount--;

				} else {

					if (gp.player.inventory.get(index).amount > 1) {

						gp.player.inventory.get(index).amount --;

					} else {

						gp.player.inventory.remove(index);
					}
				}
			}
		}
	}

	public int getIndexForInventory(int slotCol ,int slotRow) {

		int x = slotCol + (slotRow * 5);
		return x;
	}

	public void drawSubWindow(int x, int y, int height, int width) {

		g2.setColor(new Color(0, 0, 0, 200));
		g2.fillRoundRect(x, y, width, height, 35, 35);

		g2.setColor(new Color(255, 255, 255));
		g2.setStroke(new BasicStroke(5));
		g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);
	}

	public int getXforCenteredText(String text) {

		int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = gp.screenWidth / 2 - length / 2;
		return x;
	}

	public int getXforAlignedText(String text, int dirx) {

		return dirx - (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
	}

	public void drawDebugScreen() {

		String text = String.valueOf((gp.player.worldX) / gp.tileSize) + "/"
				+ String.valueOf((gp.player.worldY) / gp.tileSize);
		g2.drawString(text, 0, 480);
		text = "Draw : " + String.valueOf(gp.preTime - gp.curTime);
		g2.drawString(text, 0, 510);
	}

	public void drawRetryScreen() {

		g2.setColor(new Color(0,0,0,150));
		g2.setStroke(new BasicStroke(5));
		g2.fillRect(0, 0, gp.tileSize*20, gp.tileSize*12);

		g2.setFont(colonna96);

		g2.setColor(Color.BLACK);
		String text = "Game Over";
		int x = getXforCenteredText(text);
		int y = gp.tileSize*4;
		g2.drawString(text, x, y);

		g2.setColor(Color.WHITE);
		g2.drawString(text, x-5, y-5);

		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40));

		text = "Retry";
		y += gp.tileSize*3;
		x = getXforCenteredText(text);
		g2.drawString(text, x, y);
		if (commandNum == 0) {
			g2.drawString(">", x-gp.tileSize, y);
		}

		text = "Exit";
		y +=  gp.tileSize;
		x = getXforCenteredText(text);
		g2.drawString(text, x, y);
		if (commandNum == 1) {
			g2.drawString(">", x-gp.tileSize, y);
		}
	}
}
