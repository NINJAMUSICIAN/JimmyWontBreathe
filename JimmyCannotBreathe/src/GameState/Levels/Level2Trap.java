package GameState.Levels;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import Entity.HUD;
import Entity.Player;
import Entity.Tombstone;
import GameState.GameState;
import GameState.GameStateManager;
import Main.GamePanel;
import TileMap.Background;
import TileMap.TileMap;

public class Level2Trap extends GameState{

	private TileMap tileMap;
	private Background bg;
	
	private Player player;
	
	private HUD hud;
	private Tombstone ladder;
	
	public Level2Trap(GameStateManager gsm){
		this.gsm = gsm;
		init();
	}
	
	public void init() {
		
		tileMap = new TileMap(30);
		tileMap.loadTiles("/Tilesets/CaveTiles.gif");
		tileMap.loadMap("/Maps/1-1-t1.map");
		tileMap.setPosition(0, 0);
		tileMap.setTween(1);
		ladder = new Tombstone(1305, 345, GameStateManager.LEVEL4STATE, gsm, tileMap, false);
		bg = new Background("/Backgrounds/Forest.png", 0.0);
		
		player = new Player("/Sprites/JimmyRunning.gif", tileMap);
		player.setPosition(100, 100);
		
		Level2State.getScore();
		
		hud = new HUD(player);
		
		
		
		System.out.println("youre here!");
		
	}
	
	public void update() {
		player.update();
		ladder.update();
		ladder.checkGrab(player);
		//player.checkAttack(enemies);
		//player.checkDrunk(drinks);
		
		tileMap.setPosition(
				GamePanel.WIDTH / 2 - player.getx(),
				GamePanel.HEIGHT / 2 - player.gety()
				);
		
		if(player.isDead()){
			player.setPosition(100, 400);
			player.revive();
			player.reset();
		}
		
		bg.setPosition(tileMap.getx(), tileMap.gety());
		
	}

	
	public void draw(Graphics2D g) {
	bg.draw(g);
		
	tileMap.draw(g);
	
	player.draw(g);
	
	hud.draw(g);
	ladder.draw(g);
	
		
	}

	
	public void keyPressed(int k) {
		if(k == KeyEvent.VK_LEFT) player.setLeft(true);
		if(k == KeyEvent.VK_RIGHT) player.setRight(true);
		if(k == KeyEvent.VK_UP) player.setUp(true);
		if(k == KeyEvent.VK_DOWN) player.setDown(true);
		if(k == KeyEvent.VK_W) player.setJumping(true);
		if(k == KeyEvent.VK_R) player.setScratching();
		if(k == KeyEvent.VK_F) player.setFiring();
		
	}

	
	public void keyReleased(int k) {
		if(k == KeyEvent.VK_LEFT) player.setLeft(false);
		if(k == KeyEvent.VK_RIGHT) player.setRight(false);
		if(k == KeyEvent.VK_UP) player.setUp(false);
		if(k == KeyEvent.VK_DOWN) player.setDown(false);
		if(k == KeyEvent.VK_W) player.setJumping(false);
		
	}

}
