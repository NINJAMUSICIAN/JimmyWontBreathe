package GameState.Levels;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import Entity.Alcohol;
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

public class Level2State extends GameState {

	private TileMap tileMap;
	private Background bg;
	
	private Player player;
	
	public static int score = 0;

	private ArrayList<Enemy> enemies;
	private ArrayList<Alcohol> drinks;
	
	private Tombstone tomby, tomby2;
	private Alcohol alcohol;
	private HUD hud;
	
	private boolean standingOn;
	private boolean triggered;
	
	public Level2State(GameStateManager gsm){
		this.gsm = gsm;
		init();
	}
	
	@Override
	public void init() {
		triggered = false;
		standingOn = false;
		drinks = new ArrayList<Alcohol>();
		tileMap = new TileMap(30);
		tileMap.loadTiles("/Tilesets/DaylightTiles.gif");
		tileMap.loadMap("/Maps/1-2.map");
		tileMap.setPosition(0, 0);
		tileMap.setTween(1);
		bg = new Background("/Backgrounds/Forest.png", 0.1);
		alcohol = new Alcohol(855, 225, tileMap);
		drinks.add(alcohol);
		
		tomby2 = new Tombstone(6030,  435, GameStateManager.LEVEL3STATE, gsm, tileMap, true);
		//6030, 435, GameStateManager.LEVEL2STATE, gsm, tileMap, false
		
		player = new Player("/Sprites/JimmyRunning.gif", tileMap);
		player.setPosition(300, 400);
		
		populateEnemies();
		
		hud = new HUD(player);
	}

	private void populateEnemies(){
		enemies = new ArrayList<Enemy>();
		Ghost g;
		Flyer f;
		
		Point[] points = new Point[]{
				new Point(1200, 50),
				new Point(1400, 50),
				new Point(1440, 50),
				new Point(2700, 215),
				new Point(4568, 350)
				
		};
		
		for(int i = 0; i < points.length; i++){
			g = new Ghost(tileMap);
			g.setPosition(points[i].x, points[i].y);
			enemies.add(g);
		}
		
		Point[] flySpots = new Point[]{
				new Point(950, 200),
				new Point(3100, 200),
				new Point(6000, 170),
				new Point(3300, 320),
				new Point(3650, 320),
				new Point(3960, 200)
		};
		
		for(int i = 0; i < flySpots.length; i++){
			f = new Flyer(tileMap, false);
			f.setPosition(flySpots[i].x, flySpots[i].y);
			enemies.add(f);
		}
	}
	
	public void hangoverMonsters(){
		for(int i = 0; i < 1; i++){
			Ghost g;
			Flyer f;
			Point[] points = new Point[]{
					new Point(1200, 50),
					new Point(1400, 50),
					new Point(1440, 50),
					new Point(1300, 50),
					new Point(1480, 50),
					new Point(1520, 50),
					new Point(1600, 50),
					new Point(2750, 215),
					new Point(2600, 215),
					new Point(4550, 350),
					new Point(4620, 350)
					
					
			};
			
			for(int j = 0; j < points.length; j++){
				g = new Ghost(tileMap);
				g.setPosition(points[j].x, points[j].y);
				enemies.add(g);
			}
			Point[] flySpots = new Point[]{
					new Point(950, 200),
					new Point(750, 200),
					new Point(2900, 200),
					new Point(3100, 200),
					new Point(3300, 320),
					new Point(3650, 320),
					new Point(3960, 200),
					new Point(4200, 200),
					new Point(6000, 170),
					
			};
			
			for(int q = 0; q < flySpots.length; q++){
				f = new Flyer(tileMap, false);
				f.setPosition(flySpots[q].x, flySpots[q].y);
				enemies.add(f);
			}
			
		}
		
		player.setHungover(false);
	}

	public void restart(){
		for(int i = 0; i < enemies.size(); i++){
			enemies.get(i).kill();
		}
		
		Ghost g;
		Flyer f;
		
		Point[] points = new Point[]{
				new Point(1200, 50),
				new Point(1400, 50),
				new Point(1440, 50),
				new Point(2700, 215),
				new Point(4568, 350)
				
		};
		
		for(int i = 0; i < points.length; i++){
			g = new Ghost(tileMap);
			g.setPosition(points[i].x, points[i].y);
			enemies.add(g);
		}
		
		Point[] flySpots = new Point[]{
				new Point(950, 200),
				new Point(3100, 200),
				new Point(6000, 170),
				new Point(3300, 320),
				new Point(3650, 320),
				new Point(3960, 200)
		};
		
		for(int i = 0; i < flySpots.length; i++){
			f = new Flyer(tileMap, false);
			f.setPosition(flySpots[i].x, flySpots[i].y);
			enemies.add(f);
		}
		
	}
	
	@Override
	public void update() {
		
		player.update();
		player.checkAttack(enemies);
		player.checkDrunk(drinks);
		tomby2.checkGrab(player);
		tomby2.update();
		
		if(player.isDead()){
			player.setPosition(100, 400);
			player.revive();
			player.reset();
			restart();
		}
		
		if(triggered){
			tomby.checkGrab(player);
			tomby.update();
		}
		
		if(player.isHungover()){
			if(player.gety() == 215){
				standingOn = true;
			}
			hangoverMonsters();
		}
		
		if(standingOn){
			
				tomby = new Tombstone(800, 435, GameStateManager.LEVEL1TRAP, gsm, tileMap, true);
				standingOn = false;
				triggered = true;
			
		}
		
		
		bg.setPosition(tileMap.getx(), tileMap.gety());
		
		tileMap.setPosition(
				GamePanel.WIDTH / 2 - player.getx(),
				GamePanel.HEIGHT / 2 - player.gety());
		
		
		
		
		
		for(int i = 0; i < drinks.size(); i++){
			Alcohol a = drinks.get(i);
			a.update();
			if(a.shouldRemove()){
				drinks.remove(i);
				i--;
			}
		}
		
		for(int i = 0; i < enemies.size(); i++){
			Enemy e = enemies.get(i);
			e.update();
		if(player.isDrunk()){
			e.kill();
		}
			if(e.isDead()){
				enemies.remove(i);
				e.addScore(score);
				i--;
				
			}
			
		}
		
		bg.setPosition(tileMap.getx(), tileMap.gety());
				

	}
	
	@Override
	public void draw(Graphics2D g) {
		bg.draw(g);
		
		// draw tilemap
		tileMap.draw(g);
		
		//draw tombstone
	
		if(triggered){
			tomby.draw(g);
		}
		tomby2.draw(g);
		// draw player
		player.draw(g);

		//tomby2.drawHand(g);
		
		if(triggered){
			tomby.drawHand(g);
		}
		
		for(int i = 0; i < enemies.size(); i++){
			
			enemies.get(i).draw(g);
		}
		for(int i = 0; i < drinks.size(); i++){
			drinks.get(i).draw(g);
		}
		hud.draw(g);		
		
	}

	@Override
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

	public static int getScore() {
		return score;
	}

	public static void addScore(int added){
		score += added;
	}
}
