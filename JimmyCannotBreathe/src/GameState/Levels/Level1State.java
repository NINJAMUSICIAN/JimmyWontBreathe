package GameState.Levels;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import Entity.Alcohol;
import Entity.Coin;
import Entity.Enemy;
import Entity.GoldCoin;
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

public class Level1State extends GameState {

	private TileMap tileMap;
	private Background bg;
	
	private Player player;
	
	public static int score = 0;

	private ArrayList<Enemy> enemies;
	private ArrayList<Alcohol> drinks;
	private ArrayList<Coin> coins;
	
	private Tombstone tomby;
	
	private HUD hud;
	
	
	private boolean standingOn;
	@SuppressWarnings("unused")
	private boolean triggered;
	
	private boolean done;
	
	
	
	public Level1State(GameStateManager gsm){
		this.gsm = gsm;
		init();
	}
	
	@Override
	public void init() {
		done = false;
		coins = new ArrayList<Coin>();
		drinks = new ArrayList<Alcohol>();
		tileMap = new TileMap(30);
		tileMap.loadTiles("/Tilesets/grasstileset2.png");
		tileMap.loadMap("/Maps/1-1.map");
		tileMap.setPosition(0, 0);
		tileMap.setTween(1);
		bg = new Background("/Backgrounds/Forest.png", 0.1);
		
		tomby = new Tombstone(3150, 440, GameStateManager.LEVEL2STATE, gsm, tileMap, true);
		
		player = new Player("/Sprites/JimmyRunning.gif", tileMap);
		player.setPosition(200, 400);
		
		populateEnemies();
		placeCoins();
		
		hud = new HUD(player);
	}

	private void populateEnemies(){
		enemies = new ArrayList<Enemy>();
		Ghost g;
		Flyer f;
		
		

		Point[] points = new Point[]{
				new Point(860, 200),
				new Point(1035, 425),
				new Point(1525, 300),
				new Point(1680, 300),
				new Point(1800, 300)
				
		};
		
		for(int i = 0; i < points.length; i++){
			g = new Ghost(tileMap);
			g.setPosition(points[i].x, points[i].y);
			enemies.add(g);
		}
		
		Point[] flySpots = new Point[]{
				new Point(2100, 335)
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

	public void placeCoins(){
//		
//		GoldCoin gc;
//		
//		Point[] gPoints = new Point[]{
//				new Point(2745, 230)
//		};
//		
//		for(int i = 0; i < gPoints.length; i++){
//			gc = new GoldCoin(tileMap);
//			gc.setPosition(gPoints[i].x, gPoints[i].y);
//			coins.add(gc);
//		}
//		
	}

	public void restart(){
		for(int i = 0; i < enemies.size(); i++){
			enemies.get(i).kill();
		}
		
		Ghost g;
		Flyer f;
		
		

		Point[] points = new Point[]{
				new Point(860, 200),
				new Point(1035, 425),
				new Point(1525, 300),
				new Point(1680, 300),
				new Point(1800, 300)
				
		};
		
		for(int i = 0; i < points.length; i++){
			g = new Ghost(tileMap);
			g.setPosition(points[i].x, points[i].y);
			enemies.add(g);
		}
		
		Point[] flySpots = new Point[]{
				new Point(2100, 335)
		};
		
		for(int i = 0; i < flySpots.length; i++){
			f = new Flyer(tileMap, false);
			f.setPosition(flySpots[i].x, flySpots[i].y);
			enemies.add(f);
		}
		
	}
	
	public void update() {
		
		player.update();
		player.checkAttack(enemies);
		player.checkDrunk(drinks);
		if(done){
		player.checkCoins(coins);
		}
		tomby.checkGrab(player);
		tomby.update();
		
		if(player.isDead()){
			player.setPosition(100, 400);
			player.revive();
			player.reset();
			restart();
		}
		
		System.out.println(player.gety());
		System.out.println(player.getx());
		
		
		if(player.getx() >= 2820 && player.gety() == 185){
			if(!done){
				
				long elapsed = System.nanoTime() / 1000000;
				if(elapsed > 50000){
					GoldCoin gc = new GoldCoin(tileMap);
					gc.setPosition(2745, 230);
					coins.add(gc);
					done = true;
				}
			}
		}
		
		if(player.isHungover()){
			if(player.gety() == 215){
				standingOn = true;
			}
			hangoverMonsters();
		}
		
		if(standingOn){
			
				
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
		if(done){
		for(int i = 0; i < coins.size(); i++){
			Coin c = coins.get(i);
			c.update();
			if(c.shouldRemove()){
				c.addScore();
			coins.remove(i);
			i--;
		}
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
	
	
			tomby.draw(g);
		
		
		// draw player
		player.draw(g);
if(done){
		for(int i = 0; i < coins.size(); i++){
			coins.get(i).draw(g);
		}
}
		//tomby2.drawHand(g);
		
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
}
