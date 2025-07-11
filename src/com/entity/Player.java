package com.entity;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.main.GamePanel;
import com.main.KeyHandler;
import com.object.OBJ_Axe;
import com.object.OBJ_Key;
import com.object.OBJ_Shield_Wood;
import com.object.OBJ_Weapon_Normal;

public class Player extends Entity {

	KeyHandler keyH;
	BufferedImage image = null;
	public final int screenX;
	public final int screenY;
	public int npc_ind;
	public boolean lightUpdated = false;

	public Player(GamePanel gp, KeyHandler keyH) {

		super(gp);

		this.keyH = keyH;

		solidArea = new Rectangle();
		solidArea.x = 8;
		solidArea.y = 16;
		solidArea.height = 32;
		solidArea.width = 32;

		defaultSolidX = solidArea.x;
		defaultSolidY = solidArea.y;

		this.screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
		this.screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

		collisionOn = false;

		type = player;
		level = 1;
		strength = 1;
		dexterity = 1;
		exp = 0;
		nextLevelExp = 5;
		coin = 500;
		currentWeapon = new OBJ_Weapon_Normal(gp);
		currentShield = new OBJ_Shield_Wood(gp);
		attack = getAttack();
		defense = getDefense();
		projectTile = new FireBall(gp);

		attackArea = currentWeapon.attackArea;

		setDefaultValues();
		getPlayerImage();
		getAttackImage();
	}

	public int getAttack() {
		return attack = strength * currentWeapon.attack;
	}

	public int getDefense() {
		return defense = dexterity * currentShield.defense;
	}

	public void setDefaultValues() {

		worldX = gp.tileSize * 23;
		worldY = gp.tileSize * 21;
		speed = 4;
		defaultSpeed = speed;
		direction = "down";

		maxLife = 6;
		life = maxLife;

		maxManna = 4;
		manna = maxManna;

		currentWeapon = new OBJ_Axe(gp);
		currentShield = new OBJ_Shield_Wood(gp);

		addItems();
	}

	public void getPlayerImage() {

		down1 = setup("/images/boy_down_1", gp.tileSize, gp.tileSize);
		down2 = setup("/images/boy_down_2", gp.tileSize, gp.tileSize);
		up1 = setup("/images/boy_up_1", gp.tileSize, gp.tileSize);
		up2 = setup("/images/boy_up_2", gp.tileSize, gp.tileSize);
		right1 = setup("/images/boy_right_1", gp.tileSize, gp.tileSize);
		right2 = setup("/images/boy_right_2", gp.tileSize, gp.tileSize);
		left1 = setup("/images/boy_left_1", gp.tileSize, gp.tileSize);
		left2 = setup("/images/boy_left_2", gp.tileSize, gp.tileSize);
	}

	public void getAttackImage() {

		if (currentWeapon.name == "Normal Sword") {
			attackUp1 = setup("/playerAtack/boy_attack_up_1", gp.tileSize, gp.tileSize * 2);
			attackUp2 = setup("/playerAtack/boy_attack_up_2", gp.tileSize, gp.tileSize * 2);
			attackDown1 = setup("/playerAtack/boy_attack_down_1", gp.tileSize, gp.tileSize * 2);
			attackDown2 = setup("/playerAtack/boy_attack_down_2", gp.tileSize, gp.tileSize * 2);
			attackRight1 = setup("/playerAtack/boy_attack_right_1", gp.tileSize * 2, gp.tileSize);
			attackRight2 = setup("/playerAtack/boy_attack_right_2", gp.tileSize * 2, gp.tileSize);
			attackLeft1 = setup("/playerAtack/boy_attack_left_1", gp.tileSize * 2, gp.tileSize);
			attackLeft2 = setup("/playerAtack/boy_attack_left_2", gp.tileSize * 2, gp.tileSize);
		}
		if (currentWeapon.name == "Wood Axe") {
			attackUp1 = setup("/playerAtack/boy_axe_up_1", gp.tileSize, gp.tileSize * 2);
			attackUp2 = setup("/playerAtack/boy_axe_up_2", gp.tileSize, gp.tileSize * 2);
			attackDown1 = setup("/playerAtack/boy_axe_down_1", gp.tileSize, gp.tileSize * 2);
			attackDown2 = setup("/playerAtack/boy_axe_down_2", gp.tileSize, gp.tileSize * 2);
			attackRight1 = setup("/playerAtack/boy_axe_right_1", gp.tileSize * 2, gp.tileSize);
			attackRight2 = setup("/playerAtack/boy_axe_right_2", gp.tileSize * 2, gp.tileSize);
			attackLeft1 = setup("/playerAtack/boy_axe_left_1", gp.tileSize * 2, gp.tileSize);
			attackLeft2 = setup("/playerAtack/boy_axe_left_2", gp.tileSize * 2, gp.tileSize);
		}
	}

	public void addItems() {
		inventory.add(currentShield);
		inventory.add(currentWeapon);
		inventory.add(new OBJ_Key(gp));
		inventory.add(new OBJ_Key(gp));
		inventory.add(new OBJ_Key(gp));
	}

	@Override
	public void update() {

		if (keyH.mousePressed) {
			attacking();
		} else if (keyH.downPressed || keyH.upPressed || keyH.leftPressed || keyH.rightPressed || keyH.enterPressed) {

			if (keyH.downPressed) {
				direction = "down";
			}
			if (keyH.upPressed) {
				direction = "up";
			}
			if (keyH.leftPressed) {
				direction = "left";
			}
			if (keyH.rightPressed) {
				direction = "right";
			}

			collisionOn = false;

			// TILE COLLISION
			gp.colCheck.checkTile(this);

			// OBJECT COLLISION
			int index = gp.colCheck.checkObject(this, true);
			pickUpObject(index);

			// NPC COLLISION
			npc_ind = gp.colCheck.checkEntity(this, gp.npc[gp.currentMap]);
			interactNPC(npc_ind);

			// EVENT CHECKER (damage, heal)
			gp.eHandler.checkEvent();

			int m_ind = gp.colCheck.checkEntity(this, gp.monster[gp.currentMap]);
			interactMonster(m_ind);

			gp.colCheck.checkEntity(this, gp.iTile[gp.currentMap]);

			if (!collisionOn && !keyH.enterPressed) {
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
			}

			keyH.enterPressed = false;

			spriteCounter++;
			if (spriteCounter == 10) {
				if (spriteNum == 1) {
					spriteNum = 2;
				} else {
					spriteNum = 1;
				}
				spriteCounter = 0;
			}
		}

		if (keyH.shotPressed && !projectTile.alive && shotAvailableCounter == 30 && projectTile.haveResources()) {

			projectTile.set(worldX, worldY, direction, true, this);
			projectTile.subractResources();

			for (int i = 0; i < gp.projectList[gp.currentMap].length; i++) {
				if (gp.projectList[gp.currentMap][i] == null) {
					gp.projectList[gp.currentMap][i] = projectTile;
					break;
				}
			}

			shotAvailableCounter = 0;
		}

		if (invincible) {
			invincibleCounter++;
			if (invincibleCounter > 60) {
				invincibleCounter = 0;
				invincible = false;
			}
		}
		if (shotAvailableCounter < 30) {
			shotAvailableCounter++;
		}

		if (manna > maxManna) {
			manna = maxManna;
		}

		if (life > maxLife) {
			life = maxLife;
		}

//		if (life <= 0) {
//			gp.gameState = gp.retryState;
//		}
	}

	public void attacking() {

		spriteCounter++;
		if (spriteCounter < 5) {
			spriteNum = 1;
		}
		if (spriteCounter > 5 && spriteCounter <= 25) {
			spriteNum = 2;

			int currentWorldX = worldX;
			int currentWorldY = worldY;
			int solidAreaWidth = solidArea.width;
			int solidAreaHeight = solidArea.height;

			switch (direction) {
			case "up":
				worldY -= attackArea.height;
				break;
			case "down":
				worldY += attackArea.height;
				break;
			case "left":
				worldX -= attackArea.width;
				break;
			case "right":
				worldX += attackArea.width;
				break;
			}

			solidArea.width = attackArea.width;
			solidArea.height = attackArea.height;

			int monsterIndex = gp.colCheck.checkEntity(this, gp.monster[gp.currentMap]);
			damageMonster(monsterIndex, attack , currentWeapon.knockBackPower);

			int iTileIndex = gp.colCheck.checkEntity(this, gp.iTile[gp.currentMap]);
			damageTile(iTileIndex, attack);

			int projectTile = gp.colCheck.checkEntity(this, gp.projectList[gp.currentMap]);
			damageProjectTile(projectTile);

			worldX = currentWorldX;
			worldY = currentWorldY;

			solidArea.width = solidAreaWidth;
			solidArea.height = solidAreaHeight;
		}
		if (spriteCounter > 25) {
			spriteNum = 1;
			spriteCounter = 0;
			keyH.mousePressed = false;
		}
	}

	public void pickUpObject(int i) {

		if (i != -1) {
			if (gp.obj[gp.currentMap][i].type == pickable) {

				gp.obj[gp.currentMap][i].useItem(this);
				gp.obj[gp.currentMap][i] = null;

			} else if (gp.obj[gp.currentMap][i].type == obstacle) {

				if (gp.keyH.enterPressed) {
					gp.obj[gp.currentMap][i].interact();
				}

			}
			else {

				String text = "";

				if (canObtain(gp.obj[gp.currentMap][i])) {
					text = "Got a " + gp.obj[gp.currentMap][i].name;
					gp.obj[gp.currentMap][i] = null;
				} else {
					text = "You don't have space";
				}
				gp.ui.addMessage(text);
			}
		}
	}

	@Override
	public void selectObject() {

		int index = gp.ui.getIndexForInventory(gp.ui.playerSlotCol, gp.ui.playerSlotRow);

		if (index < inventory.size()) {

			Entity entity = inventory.get(index);

			if (entity.type == shield) {

				currentShield = entity;
				getAttackImage();
				defense = getDefense();
				gp.ui.addMessage("Shield changed");

			} else if (entity.type == weapon) {

				currentWeapon = entity;
				getAttackImage();
				attack = getAttack();
				gp.ui.addMessage("Weapon changed");

			} else if (entity.type == light) {

				if (currentLight == entity) {
					currentLight = null;
				} else {
					currentLight = entity;
				}

				lightUpdated = true;

				gp.ui.addMessage("Light changed");

			} else if (entity.type == consumable) {

				if (entity.useItem(this)) {

					if (inventory.get(index).amount > 1) {
						inventory.get(index).amount--;
					} else {

						inventory.remove(index);
					}
				}
			}
		}
	}

	public void interactNPC(int index) {

		if (keyH.enterPressed) {
			if (index != -1) {
				gp.gameState = gp.dialogueState;
				gp.npc[gp.currentMap][index].speak();
			}
		}
	}

	public void interactMonster(int i) {

		if (i != -1) {
			if (!invincible && !gp.monster[gp.currentMap][i].dying) {
				int damage = gp.monster[gp.currentMap][i].attack - defense;

				if (damage < 0) {
					damage = 0;
				}
				life -= damage;
				gp.ui.addMessage(damage + " damage conceeded");
				invincible = true;
			}
		}
	}

	public void damageMonster(int i, int attack , int knockBackPower) {

		if (i != -1) {
			if (!gp.monster[gp.currentMap][i].invincible) {

				int damage = attack - gp.monster[gp.currentMap][i].defense;

				if (damage < 0) {
					damage = 0;
				}

				if (knockBackPower > 0) {
					knockBack (gp.monster[gp.currentMap][i]);
				}

				gp.monster[gp.currentMap][i].life -= damage;
				gp.ui.addMessage(damage + " damage given");
				gp.monster[gp.currentMap][i].invincible = true;
				gp.monster[gp.currentMap][i].damageReaction();
				gp.monster[gp.currentMap][i].healthOn = true;
				if (gp.monster[gp.currentMap][i].life <= 0) {
					gp.monster[gp.currentMap][i].dying = true;
					gp.ui.addMessage("Greean SLime killed");
					exp += gp.monster[gp.currentMap][i].exp;
					gp.ui.addMessage("Exp +" + gp.monster[gp.currentMap][i].exp);
					checkLevelUp();
				}
			}
		}
	}

	public void damageTile(int i, int attack) {

		if (i != -1) {
			if (!gp.iTile[gp.currentMap][i].invincible && gp.iTile[gp.currentMap][i].isCorrectWeapon(this)
					&& gp.iTile[gp.currentMap][i].destructable) {

				generateParticle(gp.iTile[gp.currentMap][i], gp.iTile[gp.currentMap][i]);

				gp.iTile[gp.currentMap][i].life--;
				gp.iTile[gp.currentMap][i].invincible = true;

				if (gp.iTile[gp.currentMap][i].life <= 0) {
					gp.iTile[gp.currentMap][i].alive = false;
				}
			}
		}
	}

	public void damageProjectTile(int i) {
		if (i != -1) {
			gp.projectList[gp.currentMap][i].alive = false;
			generateParticle(gp.projectList[gp.currentMap][i], gp.projectList[gp.currentMap][i]);
		}
	}

	public void knockBack (Entity entity) {

		entity.direction = direction;
		entity.speed = this.currentWeapon.knockBackPower;
		entity.knockBackOn = true;
	}

	public void checkLevelUp() {

		if (exp > nextLevelExp) {
			level += 1;
			exp = exp - nextLevelExp;
			maxLife += 2;
			strength++;
			dexterity++;
			attack = getAttack();
			defense = getDefense();
			nextLevelExp *= 2;

			gp.ui.currentDialogue = "Yeah ! You are leveled Up" + "\nYou Feel Stronger";
			gp.gameState = gp.singleDialogueState;
		}
	}

	@Override
	public void draw(Graphics2D g2) {

		int tempx = screenX;
		int tempy = screenY;

		switch (direction) {
		case "up":
			if (keyH.mousePressed) {
				if (spriteNum == 1) {
					image = attackUp1;
				}
				if (spriteNum == 2) {
					image = attackUp2;
				}
				tempy -= gp.tileSize;
			}
			if (!keyH.mousePressed) {
				if (spriteNum == 1) {
					image = up1;
				}
				if (spriteNum == 2) {
					image = up2;
				}
			}
			break;
		case "down":
			if (keyH.mousePressed) {
				if (spriteNum == 1) {
					image = attackDown1;
				}
				if (spriteNum == 2) {
					image = attackDown2;
				}
			}
			if (!keyH.mousePressed) {
				if (spriteNum == 1) {
					image = down1;
				}
				if (spriteNum == 2) {
					image = down2;
				}
			}
			break;
		case "left":
			if (keyH.mousePressed) {
				if (spriteNum == 1) {
					image = attackLeft1;
				}
				if (spriteNum == 2) {
					image = attackLeft2;
				}
				tempx -= gp.tileSize;
			}
			if (!keyH.mousePressed) {
				if (spriteNum == 1) {
					image = left1;
				}
				if (spriteNum == 2) {
					image = left2;
				}
			}
			break;
		case "right":
			if (keyH.mousePressed) {
				if (spriteNum == 1) {
					image = attackRight1;
				}
				if (spriteNum == 2) {
					image = attackRight2;
				}
			}
			if (!keyH.mousePressed) {
				if (spriteNum == 1) {
					image = right1;
				}
				if (spriteNum == 2) {
					image = right2;
				}
			}
			break;
		}

		// DRAWING INVINSIBILITY
		if (this.invincible) {
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
		}

		g2.drawImage(image, tempx, tempy, null);

		// RESETTING INVINSIBILITY
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
	}
}






















