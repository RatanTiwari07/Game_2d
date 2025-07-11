package com.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.JPanel;

import com.entity.Entity;
import com.entity.Player;
import com.tile.InteractiveTiles;
import com.tile.TileManager;

import com.ai.PathFinder;
import com.environment.EnvironmentManager;

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Runnable {

	// SCREEN SETTINGS
	private final int originalTileSize = 16;
	private final int scale = 3;
	public final int tileSize = scale * originalTileSize;

	public final int maxScreenCol = 20;
	public final int maxScreenRow = 12;
	public final int screenWidth = maxScreenCol * tileSize;
	public final int screenHeight = maxScreenRow * tileSize;

	// WORLD SETTINGS
	public final int maxWorldCol = 50 ;
	public final int maxWorldRow = 50 ;
	public final int maxMap = 3;
	public int currentMap = 0;
	public final int worldWidth = tileSize * maxWorldCol;
	public final int worldHeight = tileSize * maxWorldRow;

	// GAMESTATE SETTINGS
	public int gameState ;
	public final int titleState = 0;
	public final int playState = 1;
	public final int pauseState = 2;
	public final int dialogueState = 3;
	public final int singleDialogueState = 4;
	public final int characterState = 5;
	public final int retryState = 6;
	public final int transitionState = 7;
	public final int tradeState = 8;

//	public int screenHeight2 = screenHeight;
//	public int screenWidth2 = screenWidth;
//	BufferedImage tempScreen;
	Graphics2D g2;

	int FPS = 60;
	double timer;
	long preTime;
	long curTime;

	Thread gameThread;
	public UI ui = new UI(this);
	public TileManager tileM = new TileManager(this);
	AssetSetter asetter = new AssetSetter(this);
	public CollisionChecker colCheck = new CollisionChecker(this);
	public KeyHandler keyH = new KeyHandler(this);
	public EventHandler eHandler = new EventHandler (this);
	public PathFinder pFind = new PathFinder (this);
	public EnvironmentManager enManager = new EnvironmentManager(this);
	public Player player = new Player (this,keyH);

	// ENTITY OBJECT AND ARRAYS
	public Entity[][] obj = new Entity [maxMap][10];
	public Entity [][] npc = new Entity [maxMap][2];
	public Entity [][] monster = new Entity [maxMap][3];
	public InteractiveTiles [][] iTile = new InteractiveTiles [maxMap][20];
	public List<Entity> particleList = new ArrayList <>();
	public Entity [][] projectList = new Entity[maxMap][20];
	List<Entity> entityList = new ArrayList <>();

	public GamePanel () {

		this.setPreferredSize(new Dimension(screenWidth,screenHeight));
		this.setBackground(Color.BLACK);
		this.setDoubleBuffered(true);
		this.setFocusable(true);
		this.addKeyListener(keyH);
		this.addMouseListener(keyH);
	}

	public void setUpGame() {
		asetter.setObject();
		asetter.setNPC();
		asetter.setMonster();
		asetter.setInteractiveTiles();
		enManager.setup();
		gameState = titleState;
	}

	public void reset () {
		player.setDefaultValues();
		setUpGame();
	}

//		tempScreen = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
//		g2 = (Graphics2D)tempScreen.getGraphics();

//		setFullScreen();
//
//	public void drawToScreen () {
//
//		Graphics g = getGraphics();
//		g.drawImage(tempScreen, 0, 0, screenWidth2, screenHeight2, null);
//	}
//
//	public void setFullScreen () {
//
//		GraphicsEnvironment gs = GraphicsEnvironment.getLocalGraphicsEnvironment();
//		GraphicsDevice gd = gs.getDefaultScreenDevice();
//
//		gd.setFullScreenWindow(Main.frame);
//
//		screenWidth2 = Main.frame.getWidth();
//		screenHeight2 = Main.frame.getHeight() ;
//	}

	public void startGameThread () {
		gameThread = new Thread (this);
		gameThread.start();
		setUpGame();
	}

	@Override
	public void run () {

		double drawInterval = 1000000000/FPS; // 16666666.66666667       0.0166666666666667

		long lastTime = System.nanoTime();
		double delta = 0.0;

		while (gameThread != null) {

			long currentTime = System.nanoTime();
			delta += (currentTime - lastTime) / drawInterval;
			timer += (currentTime-lastTime);
			lastTime = currentTime ;

			if (delta >= 1.0) {

				update();
//				drawToScreen();
				repaint();
//				draw ();
				delta--;
			}
		}
	}

	public void update () {

		if (gameState == playState) {

			player.update();

			// NPC update

			for (int i=0; i<npc[currentMap].length; i++) {
				if (npc[currentMap][i] != null) {
					npc[currentMap][i].update();
				}
			}

			// monster updation in thread

			for (int i=0; i<monster[currentMap].length; i++) {
				if (monster[currentMap][i] != null) {
					if (monster[currentMap][i].alive && !monster[currentMap][i].dying) {
						monster[currentMap][i].update();
					}
					if (!monster[currentMap][i].alive) {
						monster[currentMap][i].checkDrop();
						monster[currentMap][i] = null;
					}
				}
			}

			// projectlist (attacks) updation in thread

			for (int i=0; i<projectList[currentMap].length; i++) {
				if (projectList[currentMap][i] != null) {
					if (projectList[currentMap][i].alive) {
						projectList[currentMap][i].update();
					}
					if (!projectList[currentMap][i].alive) {
						projectList[currentMap][i] = null;
					}
				}
			}


			for (int i=0; i<particleList.size(); i++) {
				if (particleList.get(i) != null) {
					if (particleList.get(i).alive) {
						particleList.get(i).update();
					}
					if (!particleList.get(i).alive) {
						particleList.remove(i);
					}
				}
			}

			// Special tiles updation in thread

			for (int i=0; i<iTile[currentMap].length; i++) {
				if (iTile[currentMap][i] != null) {
					if (iTile[currentMap][i].alive) {
						iTile[currentMap][i].update();
					}
					if (!iTile[currentMap][i].alive) {
						iTile[currentMap][i] = iTile[currentMap][i].getDisposedForm();
					}
				}
			}

			enManager.update();

		}
		if (gameState == pauseState) {
			// nothing
		}
	}

	@Override
	public void paintComponent (Graphics g) {

		super.paintComponent(g);

		g2 = (Graphics2D) g;

		if (this.gameState == this.titleState) {
			ui.draw(g2);
		} else {
			// TILE
			tileM.draw(g2);

			for (int i=0; i<iTile[currentMap].length; i++) {
				if (iTile[currentMap][i] != null) {
					entityList.add(iTile[currentMap][i]);
				}
			}

			entityList.add(player);

			// ADDING ENTITY'S TO LIST
			for (int i=0; i<npc[currentMap].length; i++) {
				if (npc[currentMap][i] != null) {
					entityList.add(npc[currentMap][i]);
				}
			}

			for (int i=0; i<obj[currentMap].length; i++) {
				if (obj[currentMap][i] != null) {
					entityList.add(obj[currentMap][i]);
				}
			}

			for (int i=0; i<monster[currentMap].length; i++) {
				if (monster[currentMap][i] != null) {
					entityList.add(monster[currentMap][i]);
				}
			}

			for (int i=0; i<projectList[currentMap].length; i++) {
				if (projectList[currentMap][i] != null) {
					entityList.add(projectList[currentMap][i]);
				}
			}

			for (int i=0; i<particleList.size(); i++) {
				if (particleList.get(i) != null) {
					entityList.add(particleList.get(i));
				}
			}

			// SORT
			Collections.sort(entityList , new Comparator<Entity>() {

				@Override
				public int compare(Entity o1, Entity o2) {
					int result = Integer.compare(o1.worldY, o2.worldY);
					return result;
				}
			});

			for (Entity entity : entityList) {
				entity.draw(g2);
			}

			entityList.clear();

			enManager.draw(g2);
			// UI
			ui.draw(g2);

		}
	}

}














