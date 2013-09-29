package GameState.Levels;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import Entity.Enemy;
import Entity.HUD;
import Entity.Player;
import Entity.Tombstone;
import Entity.Enemies.Flyer;
import Entity.Enemies.Ghost;
import GameState.GameState;
import GameState.GameStateManager;
import Main.GamePanel;
import TileMap.Background;
import TileMap.TileMap;

public class Level3State extends GameState{

	private TileMap tileMap;
	private Background bg;
	
	private Player player;
	
	private ArrayList<Enemy> enemies;
	private HUD hud;
	
	private Tombstone finish;
	
	public Level3State(GameStateManager gsm){
		this.gsm = gsm;
		init();
	}

	
	public void init() {
		
		
		tileMap = new TileMap(30);
		tileMap.loadTiles("/Tilesets/DaylightTiles.gif");
		tileMap.loadMap("/Maps/1-3.map");
		tileMap.setPosition(0,0);
		tileMap.setTween(1);
		bg = new Background("/Backgrounds/VerticalSky.png", 0.0);
		
		finish = new Tombstone(50, 230, GameStateManager.CUTSCENE2, gsm, tileMap, true);
		
		player = new Player("/Sprites/JimmyRunning.gif", tileMap);
		player.setPosition(130, 2375);
		
		
		
		hud = new HUD(player);
		
		populateEnemies();
		
		hud = new HUD(player);
	}

	public void populateEnemies(){
		enemies = new ArrayList<Enemy>();
		Ghost g;
		Flyer f;
		
		Point[] walkers = new Point[]{
				new Point(390, 890),
				new Point(235, 890),
				new Point(400, 440)
		};
		for(int i = 0; i < walkers.length; i++){
			g = new Ghost(tileMap);
			g.setPosition(walkers[i].x, walkers[i].y);
			enemies.add(g);
		}
		Point[] flyers = new Point[]{
				new Point(280, 560),
				new Point(190, 560),
				new Point(235, 410),
				new Point(310, 1340),
				new Point(190, 1340)
		};
		
		
		for(int i = 0; i < flyers.length; i++){
			f = new Flyer(tileMap, false);
			f.setPosition(flyers[i].x, flyers[i].y);
			enemies.add(f);
		}
	}
	
	public void restart(){
		for(int i = 0; i < enemies.size(); i++){
			enemies.get(i).kill();
		}
		
		Ghost g;
		Flyer f;
		
		Point[] walkers = new Point[]{
				new Point(390, 890),
				new Point(235, 890),
				new Point(400, 440)
		};
		for(int i = 0; i < walkers.length; i++){
			g = new Ghost(tileMap);
			g.setPosition(walkers[i].x, walkers[i].y);
			enemies.add(g);
		}
		Point[] flyers = new Point[]{
				new Point(280, 560),
				new Point(190, 560),
				new Point(235, 410),
				new Point(310, 1340),
				new Point(190, 1340)
		};
		
		
		for(int i = 0; i < flyers.length; i++){
			f = new Flyer(tileMap, false);
			f.setPosition(flyers[i].x, flyers[i].y);
			enemies.add(f);
		}
		
	}
	
	public void update() {
		player.update();
		player.checkAttack(enemies);
		
		finish.update();
		finish.checkGrab(player);
		
		System.out.println(player.getx());
		System.out.println(player.gety());
		
		bg.setPosition(tileMap.getx(), tileMap.gety());
		
		tileMap.setPosition(
				GamePanel.WIDTH / 2 - player.getx(),
				GamePanel.HEIGHT / 2 - player.gety());
		
		
		if(player.isDead()){
			player.setPosition(130, 2375);
			player.revive();
			player.reset();
			restart();
		}
		
		
//		for(int i = 0; i < drinks.size(); i++){
//			Alcohol a = drinks.get(i);
//			a.update();
//			if(a.shouldRemove()){
//				drinks.remove(i);
//				i--;
//			}
//		}
//		
		for(int i = 0; i < enemies.size(); i++){
			Enemy e = enemies.get(i);
			e.update();
		if(player.isDrunk()){
			e.kill();
		}
			if(e.isDead()){
				enemies.remove(i);
				e.addScore(Level2State.score);
				i--;
				
			}
			
		}
		
		bg.setPosition(tileMap.getx(), tileMap.gety());
				

	}

	
	public void draw(Graphics2D g) {
		bg.draw(g);
		
		tileMap.draw(g);
		
		finish.draw(g);
		
		player.draw(g);
		
	for(int i = 0; i < enemies.size(); i++){
			
			enemies.get(i).draw(g);
		}
		
		hud.draw(g);
		
	}

	
	public void keyPressed(int k) {
		if(player.getMovable()){
		if(k == KeyEvent.VK_LEFT) player.setLeft(true);
		if(k == KeyEvent.VK_RIGHT) player.setRight(true);
		if(k == KeyEvent.VK_W) player.setJumping(true);
		if(k == KeyEvent.VK_R) player.setScratching();
		if(k == KeyEvent.VK_F) player.setFiring();
		}
	}
	
	public void keyReleased(int k) {
		if(k == KeyEvent.VK_LEFT) player.setLeft(false);
		if(k == KeyEvent.VK_RIGHT) player.setRight(false);
		if(k == KeyEvent.VK_W) player.setJumping(false);

	}

	
}
