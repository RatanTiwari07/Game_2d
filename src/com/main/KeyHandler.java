package com.main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class KeyHandler implements KeyListener, MouseListener {

	public boolean upPressed , downPressed, rightPressed, leftPressed, mousePressed,
				   enterPressed, debugPressed, shotPressed;
	GamePanel gp;

	public KeyHandler (GamePanel gp) {
		this.gp = gp;
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {

		int code = e.getKeyCode();

		if (gp.gameState == gp.titleState) {

			titleState(code);

		} else if (gp.gameState == gp.playState) {

			playState(code);

		} else if (gp.gameState == gp.pauseState) {

			pauseState(code);

		} else if (gp.gameState == gp.dialogueState) {

			dialogueState(code);

		} else if (gp.gameState == gp.singleDialogueState) {

			singleDialogueState(code);

		} else if (gp.gameState == gp.characterState) {

			characterState(code);

		} else if (gp.gameState == gp.retryState) {

			retryState(code);

		} else if (gp.gameState == gp.tradeState) {

			tradeState(code);

		}
	}

	public void titleState (int code) {

		if (code == KeyEvent.VK_UP) {
			gp.ui.commandNum--;
			if (gp.ui.commandNum < 0) {
				gp.ui.commandNum = 2;
			}
		}
		if (code == KeyEvent.VK_DOWN) {
			gp.ui.commandNum++;
			if (gp.ui.commandNum > 2) {
				gp.ui.commandNum = 0;
			}
		}
		if (code == KeyEvent.VK_ENTER) {
			if (gp.ui.commandNum == 0) {
				gp.gameState = gp.playState;
			}
			if (gp.ui.commandNum == 1) {
				gp.gameState = gp.playState;
			}
			if (gp.ui.commandNum == 2) {
				System.exit(1);
			}
		}
	}

	public void playState (int code) {

		if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
			leftPressed = true;
		}
		if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
			downPressed = true;
		}
		if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT ) {
			rightPressed = true;
		}
		if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP ) {
			upPressed = true;
		}
		if (code == KeyEvent.VK_P || code == KeyEvent.VK_SPACE ) {
			gp.gameState = gp.pauseState;
		}
		if (code == KeyEvent.VK_ENTER) {
			enterPressed = true;
		}
		if (code == KeyEvent.VK_T) {
			if (debugPressed) {
				debugPressed = false;
			}
			if (!debugPressed) {
				debugPressed = true;
			}
		}
		if (code == KeyEvent.VK_C) {
			gp.gameState = gp.characterState;
		}
		if (code == KeyEvent.VK_E) {
			shotPressed = true;
		}
	}

	public void pauseState (int code) {

		if (code == KeyEvent.VK_P || code == KeyEvent.VK_SPACE ) {
			gp.gameState = gp.playState;
		}
	}

	public void dialogueState (int code) {

		if (code == KeyEvent.VK_ENTER) {
			gp.gameState = gp.playState;
		}
		if (code == KeyEvent.VK_F) {
			gp.npc[gp.currentMap][gp.player.npc_ind].speak();
		}

	}

	public void singleDialogueState (int code) {

		if (code == KeyEvent.VK_F || code == KeyEvent.VK_ENTER) {
			gp.gameState = gp.playState;
		}

	}

	public void characterState (int code) {

		if (code == KeyEvent.VK_C) {
			gp.gameState = gp.playState;
		}
		if (code  == KeyEvent.VK_ENTER) {
			gp.player.selectObject();
		}
		playerInventory(code);

	}

	public void retryState (int code) {

		if (code == KeyEvent.VK_UP || code == KeyEvent.VK_W) {
			gp.ui.commandNum--;
			if (gp.ui.commandNum < 0) {
				gp.ui.commandNum = 1;
			}
		}
		if (code == KeyEvent.VK_DOWN || code == KeyEvent.VK_S) {
			gp.ui.commandNum++;
			if (gp.ui.commandNum > 1) {
				gp.ui.commandNum = 0;
			}
		}
		if (code == KeyEvent.VK_ENTER) {
			if (gp.ui.commandNum == 0) {
				gp.reset();
				gp.gameState = gp.playState;
			}
			if (gp.ui.commandNum == 1) {
				System.exit(1);
			}
		}

	}

	public void tradeState (int code) {

		if (code == KeyEvent.VK_ENTER) {
			enterPressed = true;
		}

		if (code == KeyEvent.VK_W) {
			gp.ui.commandNum--;
			if (gp.ui.commandNum < 1) {
				gp.ui.commandNum = 3;
			}
		}
		if (code == KeyEvent.VK_S) {
			gp.ui.commandNum++;
			if (gp.ui.commandNum > 3) {
				gp.ui.commandNum = 1;
			}
		}

		if (gp.ui.subState == 1) {
			npcInventory(code);
		}
		if (gp.ui.subState == 2) {
			playerInventory(code);
		}

		if (code == KeyEvent.VK_ESCAPE) {
			gp.ui.subState = 0;
		}
	}

	public void playerInventory (int code) {

		if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
			if (gp.ui.playerSlotCol != 0) {
				gp.ui.playerSlotCol--;
			}
		}
		if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
			if (gp.ui.playerSlotRow != 3) {
				gp.ui.playerSlotRow++;
			}
		}
		if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT ) {
			if (gp.ui.playerSlotCol != 4) {
				gp.ui.playerSlotCol++;
			}
		}
		if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP ) {
			if (gp.ui.playerSlotRow != 0) {
				gp.ui.playerSlotRow--;
			}
		}
	}

	public void npcInventory (int code) {

		if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
			if (gp.ui.npcSlotCol != 0) {
				gp.ui.npcSlotCol--;
			}
		}
		if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
			if (gp.ui.npcSlotRow != 3) {
				gp.ui.npcSlotRow++;
			}
		}
		if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT ) {
			if (gp.ui.npcSlotCol != 4) {
				gp.ui.npcSlotCol++;
			}
		}
		if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP ) {
			if (gp.ui.npcSlotRow != 0) {
				gp.ui.npcSlotRow--;
			}
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {

		int code = e.getKeyCode();

		if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
			leftPressed = false;
		}
		if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
			downPressed = false;
		}
		if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT ) {
			rightPressed = false;
		}
		if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP ) {
			upPressed = false;
		}
		if (code == KeyEvent.VK_E) {
			shotPressed = false;
		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		mousePressed = true;
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

}
