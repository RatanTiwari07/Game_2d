package com.main;

import com.entity.Monster;
import com.entity.NpcMerchant;
import com.entity.NpcOldMan;
import com.object.OBJ_Axe;
import com.object.OBJ_Door;
import com.object.OBJ_Lantern;
import com.object.OBJ_Manna;
import com.object.OBJ_Red_Potion;
import com.object.OBJ_Tent;
import com.tile.Interactive_tree;

public class AssetSetter {

	GamePanel gp;
	int i = 0;
	int map = 0;

	public AssetSetter (GamePanel gp) {
		this.gp = gp;
	}

	public void setObject () {
		i=0;
		map=0;
		gp.obj[map][i] = new OBJ_Red_Potion(gp);
		gp.obj[map][i].worldX = 29 * gp.tileSize;
		gp.obj[map][i].worldY = 20 * gp.tileSize;

		i++;
		gp.obj[map][i] = new OBJ_Red_Potion(gp);
		gp.obj[map][i].worldX = 30 * gp.tileSize;
		gp.obj[map][i].worldY = 20 * gp.tileSize;

		i++;
		gp.obj[map][i] = new OBJ_Lantern(gp);
		gp.obj[map][i].worldX = 31 * gp.tileSize;
		gp.obj[map][i].worldY = 20 * gp.tileSize;

		i++;
		gp.obj[map][i] = new OBJ_Tent(gp);
		gp.obj[map][i].worldX = 38 * gp.tileSize;
		gp.obj[map][i].worldY = 9 * gp.tileSize;

		i++;
		gp.obj[map][i] = new OBJ_Axe(gp);
		gp.obj[map][i].worldX = 32 * gp.tileSize;
		gp.obj[map][i].worldY = 21 * gp.tileSize;

		i++;
		gp.obj[map][i] = new OBJ_Manna(gp);
		gp.obj[map][i].worldX = 34 * gp.tileSize;
		gp.obj[map][i].worldY = 21 * gp.tileSize;

		i++;
		gp.obj[map][i] = new OBJ_Door(gp);
		gp.obj[map][i].worldX = 14 * gp.tileSize;
		gp.obj[map][i].worldY = 21 * gp.tileSize;

	}
	public void setNPC () {
		i = 0;
		map=0;
		gp.npc[map][i] = new NpcOldMan(gp);
		gp.npc[map][i].worldX = 21 * gp.tileSize;
		gp.npc[map][i].worldY = 21 * gp.tileSize;

		map = 1;
		i = 0;
		gp.npc[map][i] = new NpcMerchant(gp);
		gp.npc[map][i].worldX = 12 * gp.tileSize ;
		gp.npc[map][i].worldY = 7 * gp.tileSize ;
	}

	public void setMonster () {
		i = 0;
		map=0;
		gp.monster[map][i] = new Monster(gp);
		gp.monster[map][i].worldX = 21 * gp.tileSize;
		gp.monster[map][i].worldY = 36 * gp.tileSize;

		i++;
		gp.monster[map][i] = new Monster(gp);
		gp.monster[map][i].worldX = 24 * gp.tileSize;
		gp.monster[map][i].worldY = 37 * gp.tileSize;
	}

	public void setInteractiveTiles () {

		i=0;
		map=0;
		gp.iTile[map][i] = new Interactive_tree(gp,12,27);
		i++;
		gp.iTile[map][i] = new Interactive_tree(gp,12,28);
		i++;
		gp.iTile[map][i] = new Interactive_tree(gp,12,29);
		i++;
		gp.iTile[map][i] = new Interactive_tree(gp,12,30);
		i++;
		gp.iTile[map][i] = new Interactive_tree(gp,12,31);
		i++;
		gp.iTile[map][i] = new Interactive_tree(gp,12,32);
		i++;
		gp.iTile[map][i] = new Interactive_tree(gp,12,33);
		i++;
		gp.iTile[map][i] = new Interactive_tree(gp,40,18);
		i++;
		gp.iTile[map][i] = new Interactive_tree(gp,40,17);
		i++;
		gp.iTile[map][i] = new Interactive_tree(gp,40,16);
		i++;
		gp.iTile[map][i] = new Interactive_tree(gp,40,15);
		i++;
		gp.iTile[map][i] = new Interactive_tree(gp,40,14);
		i++;
		gp.iTile[map][i] = new Interactive_tree(gp,40,13);
		i++;
		gp.iTile[map][i] = new Interactive_tree(gp,41,13);
		i++;
		gp.iTile[map][i] = new Interactive_tree(gp,41,12);
		i++;
		gp.iTile[map][i] = new Interactive_tree(gp,41,11);
		i++;
		gp.iTile[map][i] = new Interactive_tree(gp,41,10);
		i++;
		gp.iTile[map][i] = new Interactive_tree(gp,40,10);
	}
}
