package GameState.Levels;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import Entity.Coin;
import Entity.Enemy;
import Entity.HUD;
import Entity.Player;
import Entity.Tombstone;
import Entity.Enemies.Devil;
import GameState.GameState;
import GameState.GameStateManager;
import Main.GamePanel;
import TileMap.Background;
import TileMap.TileMap;

public class Level13State extends GameState {

	private Background bg;
	private TileMap tileMap;
	
	private Player player;
	private ArrayList<Enemy> enemies;
	
	private HUD hud;
	
	private Devil devil;
	private boolean triggered = false;
	
	private Tombstone tomby;
	
	public Level13State(GameStateManager gsm){
		this.gsm = gsm;
		init();
	}
	
	@Override
	public void init() {
		enemies = new ArrayList<Enemy>();
		tileMap = new TileMap(30);
		tileMap.loadTiles("/Tilesets/RedAndBlue.png");
		tileMap.loadMap("/Maps/5-1.map");
		tileMap.setPosition(0, 0);
		tileMap.setTween(1);
		
		bg = new Background("/Backgrounds/White.png", 0.1);
		
		player = new Player("/Sprites/RedJimmy.png", tileMap);
		player.setPosition(120, 120);
		
		hud = new HUD(player);
	}

	@Override

	public void update() {
		player.update();
	//	player.checkAttack(enemies);
//		player.checkCoins(coins);
//		
//		finish.update();
//		finish.checkGrab(player);
//		
		bg.setPosition(tileMap.getx(), 0);
		
		tileMap.setPosition(
				GamePanel.WIDTH / 2 - player.getx(),
				GamePanel.HEIGHT / 2 - player.gety());
		
		
		if(player.isDead()){
			player.setPosition(100, 500);
			player.reset();
			//restart();
			player.revive();
		}
		
//		for(int i = 0; i < enemies.size(); i++){
//			Enemy e = enemies.get(i);
//			e.update();
//			if(player.isDrunk()){
//				e.kill();
//			}
//			if(e.isDead()){
//				enemies.remove(i);
//				e.addScore(Level2State.score);
//				i--;
//			}
//		}
//		
//		for(int i = 0; i < coins.size(); i++){
//			Coin c = coins.get(i);
//			c.update();
//			if(c.shouldRemove()){
//				coins.remove(i);
//				c.addScore();
//				i--;
//			}
//		}
		
	}

	public void draw(Graphics2D g){
		bg.draw(g);
		tileMap.draw(g);
		player.draw(g);
//		finish.draw(g);
//		
//		for(int i = 0; i < coins.size(); i++){
//			coins.get(i).draw(g);
//		}
		
		for(int i = 0; i < enemies.size(); i++){
			enemies.get(i).draw(g);
		}
		hud.draw(g);
	}


	public void keyPressed(int k){
		if(k == KeyEvent.VK_RIGHT) player.setRight(true);
		if(k == KeyEvent.VK_LEFT) player.setLeft(true);
		if(k == KeyEvent.VK_W) player.setJumping(true);
		if(k == KeyEvent.VK_R) player.setScratching();
		if(k == KeyEvent.VK_F) player.setFiring();
	}
	
	public void keyReleased(int k){
		if(k == KeyEvent.VK_RIGHT) player.setRight(false);
		if(k == KeyEvent.VK_LEFT) player.setLeft(false);
		if(k == KeyEvent.VK_W) player.setJumping(false);
	}

}

