package com.entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

import com.main.GamePanel;
import com.main.UtilityTool;

public class Entity {

	GamePanel gp;
	public BufferedImage down1, down2, up1, up2, left1, left2, right1, right2;
	public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, attackRight1, attackRight2, attackLeft1,
			attackLeft2;
	public BufferedImage image, image2, image3;

	Random random = new Random();
	public String dialogues[] = new String[5];
	public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
	public Rectangle attackArea = new Rectangle(0, 0, 0, 0);

	public ArrayList<Entity> inventory = new ArrayList<>();
	public int maxInventorySize = 20;

	public int worldX, worldY;
	public int speed;
	public int defaultSpeed;
	public String direction;
	public int defaultSolidX = worldX;
	public int defaultSolidY = worldY;

	boolean healthOn = false;
	public boolean invincible;
	public boolean collisionOn = false;
	public boolean collision = false;
	public boolean attacking = false;
	public boolean alive = true;
	public boolean dying = false;
	public boolean knockBackOn = false;
	public boolean stackable = false;

	public int dialogueIndex = 0;
	int healthOnCounter = 0;
	public int actionLockCounter;
	public int invincibleCounter;
	public int shotAvailableCounter;
	public int dyingCounter = 0;
	protected int spriteCounter = 0;
	public int knockBackCounter = 0;
	protected int spriteNum = 1;

	public String name = " ";
	public int life;
	public int maxLife;
	public int level;
	public int strength;
	public int dexterity;
	public int attack;
	public int defense;
	public int exp;
	public int nextLevelExp;
	public int coin;
	public int maxManna;
	public int manna;
	public int useCost;
	public int knockBackPower;
	public int amount = 1;
	public int lightRadius;

	public String description = "";
	public String knockBackDirection = "";
	public Entity currentWeapon;
	public Entity currentShield;
	public Entity currentLight;
	public Entity currentAttacker;
	public ProjectTile projectTile;

	public int value;

	public int price;

	public boolean pathOn = false;

	public int type;
	public final int player = 0;
	public final int npc = 1;
	public final int green_slime = 2;
	public final int weapon = 3;
	public final int shield = 4;
	public final int consumable = 5;
	public final int pickable = 6;
	public final int obstacle = 7;
	public final int light = 8;

	public Entity(GamePanel gp) {
		this.gp = gp;
	}

	public int getTopY () {
		return worldY + solidArea.y;
	}

	public int getBottomY () {
		return worldY + solidArea.y + solidArea.height ;
	}

	public int getRightX () {
		return worldX + solidArea.x + solidArea.width ;
	}

	public int getLeftX () {
		return worldX + solidArea.x ;
	}

	public int getCol () {
		return (worldX + solidArea.x)/gp.tileSize;
	}

	public int getRow () {
		return (worldY + solidArea.y)/gp.tileSize;
	}

	public int goalRow (Entity target) {
		return (target.worldY + 10) / gp.tileSize;
	}

	public int goalCol (Entity target) {
		return (target.worldX + 10) / gp.tileSize;
	}



	public boolean canObtain (Entity entity) {

		int i = inventoryHas(entity);
		boolean obtained = false;

		if (i != 999) {

			if (entity.stackable) {

				inventory.get(i).amount++;
				obtained = true;

			} else {

				if (inventory.size() < maxInventorySize) {

					inventory.add(entity);
					obtained = true;
				}
			}

		 } else {

			 if (inventory.size() < maxInventorySize) {

				 inventory.add(entity);
				 obtained = true;
			 }

		 }

		return obtained;
	}

	public void setAction() {}

	public void selectObject() {
	}

	public boolean useItem(Entity entity) {return false;}

	public void checkDrop() {}

	public void dropItem(Entity entity) {}

	public void damageReaction() {}

	public Color getParticleColour() {return null;}

	public int getParticleSize() {return 0;}

	public int getParticleSpeed() {return 0;}

	public int getParticleMaxLife() {return 0;}

	public void generateParticle(Entity generator, Entity target) {

		gp.particleList.add(new Particle(gp, target, generator.getParticleSpeed(), generator.getParticleMaxLife(),
				generator.getParticleSize(), generator.getParticleColour(), -2, -1));

		gp.particleList.add(new Particle(gp, target, generator.getParticleSpeed(), generator.getParticleMaxLife(),
				generator.getParticleSize(), generator.getParticleColour(), 2, -1));

		gp.particleList.add(new Particle(gp, target, generator.getParticleSpeed(), generator.getParticleMaxLife(),
				generator.getParticleSize(), generator.getParticleColour(), -2, 1));

		gp.particleList.add(new Particle(gp, target, generator.getParticleSpeed(), generator.getParticleMaxLife(),
				generator.getParticleSize(), generator.getParticleColour(), 2, 1));
	}

	public void speak() {

		gp.ui.currentDialogue = dialogues[dialogueIndex];

		if (dialogues[dialogueIndex + 1] == null) {
			dialogueIndex = 0;
		} else {
			dialogueIndex++;
		}
		switch (gp.player.direction) {

		case "up":
			direction = "down";
			break;
		case "down":
			direction = "up";
			break;
		case "right":
			direction = "left";
			break;
		case "left":
			direction = "right";
			break;
		}
	}

	public void checkCollision() {

		collisionOn = false;
		gp.colCheck.checkTile(this);
		gp.colCheck.checkObject(this, false);
		gp.colCheck.checkEntity(this, gp.npc[gp.currentMap]);
		gp.colCheck.checkEntity(this, gp.monster[gp.currentMap]);

		boolean contact = gp.colCheck.checkPlayer(this);

		if (this.type == green_slime && contact) {
			damagePlayer(attack);
		}
	}

	public void update() {

		if (knockBackOn) {

			checkCollision();

			if (collisionOn) {
				knockBackCounter = 0;
				knockBackOn = false;
				speed = defaultSpeed;
			} else if (!collisionOn) {
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

			knockBackCounter ++;

			if (knockBackCounter == 10) {

				knockBackCounter = 0;
				knockBackOn = false;
				speed = defaultSpeed;
			}

		} else {

			checkCollision();

			setAction();

			if (!collisionOn) {

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
		}

		spriteCounter++;
		if (spriteCounter == 20) {
			if (spriteNum == 1) {
				spriteNum = 2;
			} else {
				spriteNum = 1;
			}
			spriteCounter = 0;
		}

		if (invincible) {
			invincibleCounter++;
			if (invincibleCounter > 40) {
				invincibleCounter = 0;
				invincible = false;
			}
		}

		if (shotAvailableCounter < 30) {
			shotAvailableCounter++;
		}
	}

	public void damagePlayer(int attack) {
		if (!gp.player.invincible) {
			int damage = attack - gp.player.defense;
			if (damage < 0) {
				damage = 0;
			}
			gp.player.life -= damage;
			gp.ui.addMessage(damage + " damage conceeded");
			gp.player.invincible = true;
		}

	}

	public int inventoryHas (Entity entity) {

		int index = 999;

		for (int i = 0; i<inventory.size(); i++) {

			if (inventory.get(i).name.equals(entity.name)) {

				index = i;
				break;
			}
		}

		return index;
	}

	public void interact () {}

	public int getDetected (Entity user, Entity target [][] , String targetName) {

		int index = 999;

		int nextWorldX = user.getLeftX();
		int nextWorldY = user.getTopY();

		switch (user.direction) {
		case "up" :
			nextWorldY = user.getTopY()-1;
			break;
		case "down" :
			nextWorldY = user.getBottomY()+1;
			break;
		case "left" :
			nextWorldX = user.getLeftX()-1;
			break;
		case "right" :
			nextWorldX = user.getRightX()+1;
			break;
		}

		int col = nextWorldX / gp.tileSize;
		int row = nextWorldY / gp.tileSize;

		for (int i = 0; i<target[gp.currentMap].length; i++) {

			if (target[gp.currentMap][i] != null) {

				if (target[gp.currentMap][i].getCol() == col &&
						target[gp.currentMap][i].getRow() == row &&
						target[gp.currentMap][i].name.equals(targetName)) {
					index = i;
					break;
				}

			}
		}

		return index;
	}

	public void draw(Graphics2D g2) {

		BufferedImage image = null;

		int screenX = worldX - gp.player.worldX + gp.player.screenX;
		int screenY = worldY - gp.player.worldY + gp.player.screenY;

		if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX
				&& worldX - gp.tileSize < gp.player.worldX + gp.player.screenX
				&& worldY + gp.tileSize > gp.player.worldY - gp.player.screenY
				&& worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

			switch (direction) {
			case "up":
				if (spriteNum == 1) {
					image = up1;
				}
				if (spriteNum == 2) {
					image = up2;
				}
				break;
			case "down":
				if (spriteNum == 1) {
					image = down1;
				}
				if (spriteNum == 2) {
					image = down2;
				}
				break;
			case "left":
				if (spriteNum == 1) {
					image = left1;
				}
				if (spriteNum == 2) {
					image = left2;
				}
				break;
			case "right":
				if (spriteNum == 1) {
					image = right1;
				}
				if (spriteNum == 2) {
					image = right2;
				}
				break;
			}

			// DRAWING INVINSIBILITY
			if (invincible) {
				changeAlpha(g2, 0.4f);
			}

			if (dying) {
				dyingAnimation(g2);
			}

			if (this.type == green_slime) {
				if (healthOn) {

					healthOnCounter++;
					g2.setColor(Color.BLACK);
					g2.fillRect(screenX, screenY - 6, gp.tileSize, 10);

					if (life > 0) {
						int red = (gp.tileSize / maxLife) * life;

						g2.setColor(Color.RED);
						g2.fillRect(screenX + 2, screenY - 4, red, 6);
					}

					if (healthOnCounter > 600) {
						healthOn = false;
						healthOnCounter = 0;
					}
				}
			}

			g2.drawImage(image, screenX, screenY, null);

			// RESETTING INVINSIBILITY
			changeAlpha(g2, 1);
		}
	}

	public void dyingAnimation(Graphics2D g2) {

		dyingCounter++;

		if (dyingCounter <= 5) {
			changeAlpha(g2, 0);
		}
		if (dyingCounter > 5 && dyingCounter <= 10) {
			changeAlpha(g2, 1);
		}
		if (dyingCounter > 10 && dyingCounter <= 15) {
			changeAlpha(g2, 0);
		}
		if (dyingCounter > 15 && dyingCounter <= 20) {
			changeAlpha(g2, 1);
		}
		if (dyingCounter > 20 && dyingCounter <= 25) {
			changeAlpha(g2, 0);
		}
		if (dyingCounter > 25 && dyingCounter <= 30) {
			changeAlpha(g2, 1);
		}
		if (dyingCounter > 30 && dyingCounter <= 35) {
			changeAlpha(g2, 0);
		}
		if (dyingCounter > 35 && dyingCounter <= 40) {
			changeAlpha(g2, 1);
		}
		if (dyingCounter > 40) {
			dying = false;
			alive = false;
		}
	}

	public void changeAlpha(Graphics2D g2, float val) {
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, val));
	}

	public BufferedImage setup(String imagePath, int width, int height) {

		UtilityTool uTool = new UtilityTool();
		BufferedImage image = null;

		try {
			image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
			image = uTool.scaleImage(image, width, height);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return image;
	}

	public void searchPath(int goalCol, int goalRow, boolean follow) {

		int startCol = (worldX + solidArea.x) / gp.tileSize;
		int startRow = (worldY + solidArea.y) / gp.tileSize;

		gp.pFind.setNodes(startCol, startRow, goalCol, goalRow);

		if (gp.pFind.search()) {

			int nextX = gp.pFind.pathList.get(0).col * gp.tileSize;
			int nextY = gp.pFind.pathList.get(0).row * gp.tileSize;

			int enLeftX = worldX + solidArea.x;
			int enRightX = worldX + solidArea.x + solidArea.width;
			int enTopY = worldY + solidArea.y;
			int enBottomY = worldY + solidArea.y + solidArea.height;

			if (enLeftX > nextX) {
				direction = "left";
			} else if (enTopY < nextY && enLeftX > nextX) {
				direction = "down";
				checkCollision();
				if (collisionOn) {
					direction = "left";
				}
			} else if (enTopY >= nextY && enBottomY < nextY + gp.tileSize) {
				if (enLeftX > nextX) {
					direction = "left"; // i want to check it
				}
				if (enLeftX < nextX) {
					direction = "right";
				}
			} else if (enTopY > nextY && enLeftX > nextX) {
				direction = "up";
				checkCollision();
				if (collisionOn) {
					direction = "left";
				}
			} else if (enTopY > nextY && enLeftX < nextX) {
				direction = "up";
				checkCollision();
				if (collisionOn) {
					direction = "right";
				}
			} else if (enTopY < nextY && enLeftX < nextX) {
				direction = "down";
				checkCollision();
				if (collisionOn) {
					direction = "right";
				}
			} else if (enTopY > nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize) {
				direction = "up";
			} else if (enTopY < nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize) {
				direction = "down";
			}

			if (follow) {
				int nextCol = gp.pFind.pathList.get(0).col;
				int nextRow = gp.pFind.pathList.get(0).row;

				if (nextCol == goalCol && nextRow == goalRow) {
					pathOn = false;
				}
			}

		}
	}

}
