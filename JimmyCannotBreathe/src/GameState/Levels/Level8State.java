package GameState.Levels;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import Entity.BronzeCoin;
import Entity.Coin;
import Entity.Enemy;
import Entity.GoldCoin;
import Entity.HUD;
import Entity.Player;
import Entity.SilverCoin;
import Entity.Tombstone;
import Entity.Enemies.Fish;
import Entity.Enemies.Ghost;
import Entity.Enemies.Spitter;
import GameState.GameState;
import GameState.GameStateManager;
import Main.GamePanel;
import TileMap.Background;
import TileMap.TileMap;

public class Level8State extends GameState {

	private TileMap tileMap;
	private Background bg;
	
	private Player player;
	
	private ArrayList<Enemy> enemies;
	private ArrayList<Spitter> spitters;
	private ArrayList<Coin> coins;
	
	private Tombstone finish;
	private HUD hud;
	
	public Level8State(GameStateManager gsm){
		this.gsm = gsm;
		init();
	}

	public void init(){

		tileMap = new TileMap(30);
		tileMap.loadTiles("/Tilesets/Blackest1.png");
		tileMap.loadMap("/Maps/3-2.map");
		tileMap.setPosition(0, 0);
		tileMap.setTween(1);
		
		bg = new Background("/Backgrounds/DarkForestLoop.png", 0.1);
		
		player = new Player("/Sprites/BlackestJimmy.png", tileMap);
		player.setPosition(30, 60);
		
		finish = new Tombstone(2955, 465, GameStateManager.LEVEL9STATE, gsm, tileMap, true);
		
		populateEnemies();
		populateCoins();
		
		hud = new HUD(player);
		
	}

	public void populateEnemies(){
	enemies = new ArrayList<Enemy>();
	spitters = new ArrayList<Spitter>();
	
	Spitter s;
	Ghost g;
	Fish f;
	
	Point[] rightSpits = new Point[]{ 
			new Point(195, 195),
			new Point(165, 255),
			new Point(135, 315),
			new Point(105, 375),
			new Point(75, 435),
			new Point(45, 495),
			};
	
	for(int i = 0; i < rightSpits.length; i++){
		s = new Spitter(true, "/Sprites/Enemies/Spitter.png", tileMap);
		s.setPosition(rightSpits[i].x, rightSpits[i].y);
		spitters.add(s);
		enemies.add(s);
	}
	
	Point[] leftSpits = new Point[]{ 
			new Point(375, 195),
			new Point(405, 255),
			new Point(435, 315),
			new Point(465, 375),
			new Point(495, 435),
			new Point(525, 495),
			};
	
	for(int i = 0; i < leftSpits.length; i++){
		s = new Spitter(false, "/Sprites/Enemies/Spitter.png", tileMap);
		s.setPosition(leftSpits[i].x, leftSpits[i].y);
		spitters.add(s);
		enemies.add(s);
	}	
	
	Point[] gPoints = new Point[]{ 
			new Point(1605, 585),
			new Point(1755, 585),
			};
	
	for(int i = 0; i < gPoints.length; i++){
		g = new Ghost(tileMap);
		g.setPosition(gPoints[i].x, gPoints[i].y);
		enemies.add(g);
		}
	
	Point[] fishPoints = new Point[]{ 
			new Point(2295, 645),
			new Point(2535, 555),
			new Point(2715, 465),
			};
	
	for(int i = 0; i < fishPoints.length; i++){
		f = new Fish("/Sprites/Enemies/fishBlack.png", tileMap);
		f.setPosition(fishPoints[i].x, fishPoints[i].y);
		enemies.add(f);
	}
	
	}
	
	public void populateCoins() {
	coins = new ArrayList<Coin>();
		
	BronzeCoin bc;
	SilverCoin sc;
	GoldCoin gc;
	
	Point[] bronzePoints = new Point[]{ 
			new Point(285, 165),
			new Point(285, 195),
			new Point(285, 225),
			new Point(285, 255),
			new Point(285, 285),
			new Point(285, 405),
			new Point(285, 435),
			new Point(285, 465),
			new Point(285, 495),
			new Point(285, 525),
			new Point(705, 555),
			new Point(735, 555),
			new Point(825, 465),
			new Point(945, 165),
			new Point(1005, 165),
			new Point(1275, 585),
			new Point(1515, 525),
			new Point(1575, 465),
			new Point(1665, 345),
			new Point(1695, 345),
			new Point(1785, 465),
			new Point(1845, 525),
			new Point(2145, 585),
			new Point(2205, 585),
			new Point(2415, 555),
			};
	
	for(int i = 0; i < bronzePoints.length; i++){
		bc = new BronzeCoin(tileMap);
		bc.setPosition(bronzePoints[i].x, bronzePoints[i].y);
		coins.add(bc);
	}
	
	Point[] silverPoints = new Point[]{ 
			new Point(285, 315),
			new Point(285, 345),
			new Point(285, 375),
			new Point(735, 345),
			new Point(825, 225),
			new Point(975, 165),
			new Point(1635, 585),
			new Point(1665, 585),
			new Point(1695, 585),
			new Point(1725, 585),
			new Point(2385, 555),
			new Point(2445, 555),
			new Point(2625, 525),
			};
	
	for(int i = 0; i < silverPoints.length; i++){
		sc = new SilverCoin(tileMap);
		sc.setPosition(silverPoints[i].x, silverPoints[i].y);
		coins.add(sc);
	}
	
	Point[] goldPoints = new Point[]{ 
			new Point(345, 405),
			new Point(1275, 165),
			new Point(1305, 165),
			new Point(1335, 165),
			new Point(1395, 165),
			new Point(1425, 165),
			new Point(1455, 165),
			new Point(1365, 165),
			};
	
	for(int i = 0; i < goldPoints.length; i++){
		gc = new GoldCoin(tileMap);
		gc.setPosition(goldPoints[i].x, goldPoints[i].y);
		coins.add(gc);
		
	}
	
	}
	
	public void update(){

		player.update();
		player.checkAttack(enemies);
		player.checkCoins(coins);
		
		finish.update();
		finish.checkGrab(player);
		
		bg.setPosition(tileMap.getx(), 0);
		
		tileMap.setPosition(
				GamePanel.WIDTH / 2 - player.getx(),
				GamePanel.HEIGHT / 2 - player.gety());
		
		
		if(player.isDead()){
			player.setPosition(30, 60);
			player.reset();
			//restart();
			player.revive();
		}
		
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
		
		for(int i = 0; i < spitters.size(); i++){
			spitters.get(i).checkSpitHit(player);
		}
		
		for(int i = 0; i < coins.size(); i++){
			Coin c = coins.get(i);
			c.update();
			if(c.shouldRemove()){
				coins.remove(i);
				c.addScore();
				i--;
			}
		}
		
	}

	public void draw(Graphics2D g){
		bg.draw(g);
		tileMap.draw(g);
		player.draw(g);
		finish.draw(g);
		
		for(int i = 0; i < coins.size(); i++){
			coins.get(i).draw(g);
		}
		
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
