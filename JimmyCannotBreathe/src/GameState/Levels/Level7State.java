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
import Entity.Enemies.Spitter;
import GameState.GameState;
import GameState.GameStateManager;
import Main.GamePanel;
import TileMap.Background;
import TileMap.TileMap;

public class Level7State extends GameState{

	private Background bg;
	private TileMap tileMap;
	
	private Player player;
	
	private Tombstone finish;
	
	private ArrayList<Enemy> enemies;
	private ArrayList<Spitter> spitters;
	private ArrayList<Coin> coins; 
	
	private HUD hud;
	
	public Level7State(GameStateManager gsm){
		this.gsm = gsm;
		init();
	}
	
	public void init(){
		
		tileMap = new TileMap(30);
		tileMap.loadTiles("/Tilesets/Blackest1.png");
		tileMap.loadMap("/Maps/3-1.map"); 
		tileMap.setPosition(0, 0);
		tileMap.setTween(1);
		
		bg = new Background("/Backgrounds/DarkForestLoop.png", 0.1);
		
		finish = new Tombstone(2955, 675, GameStateManager.LEVEL8STATE, gsm, tileMap, false);
		
		player = new Player("/Sprites/BlackestJimmy.png", tileMap);
		player.setPosition(100, 630);
		
		populateEnemies();
		populateCoins();
		
		hud = new HUD(player);
		
	}
	
	public void populateEnemies(){
		enemies = new ArrayList<Enemy>();
		spitters = new ArrayList<Spitter>();
		
		Spitter s;
		Fish f;
		
		Point[] rightSpits = new Point[]{
				new Point(165, 555)
		};
		
		for(int i = 0; i < rightSpits.length; i++){
			s = new Spitter(true, "/Sprites/Enemies/Spitter.png", tileMap);
			s.setPosition(rightSpits[i].x, rightSpits[i].y);
			spitters.add(s);
			enemies.add(s);
		}
		
		Point[] spitPoints = new Point[]{ 
				new Point(795, 345),
				new Point(1065, 195),
				new Point(1095, 255),
				new Point(1125, 315),
				new Point(1155, 375),
				new Point(1185, 435),
				new Point(1215, 495),
				new Point(1245, 555)
				};
		
		for(int i = 0; i < spitPoints.length; i++){
			s = new Spitter(false, "/Sprites/Enemies/Spitter.png", tileMap);
			s.setPosition(spitPoints[i].x, spitPoints[i].y);
			spitters.add(s);
			enemies.add(s);
		}	
		Point[] fishPoints = new Point[]{ 
				new Point(2145, 315),
				new Point(2175, 465),
				};
		
		for(int i = 0; i < fishPoints.length; i++){
			f = new Fish("/Sprites/Enemies/fishBlack.png", tileMap);
			f.setPosition(fishPoints[i].x, fishPoints[i].y);
			enemies.add(f);
		}
		
	}
	
	public void populateCoins(){
		coins = new ArrayList<Coin>();
		
		BronzeCoin bc;
		SilverCoin sc;
		GoldCoin gc;
		
		Point[] bronzePoints = new Point[]{ 
				new Point(45, 435),
				new Point(225, 375),
				new Point(585, 495),
				new Point(735, 165),
				new Point(855, 165),
				new Point(855, 195),
				new Point(855, 225),
				new Point(855, 255),
				new Point(855, 555),
				new Point(855, 585),
				new Point(855, 615),
				new Point(855, 645),
				new Point(855, 675),
				new Point(1425, 555),
				new Point(1455, 555),
				new Point(1605, 465),
				new Point(1635, 465),
				new Point(1455, 345),
				new Point(1485, 345),
				new Point(1815, 225),
				new Point(1935, 225),
				new Point(2025, 285),
				new Point(2295, 285),
				new Point(2535, 285),
				};
		
		for(int i = 0; i < bronzePoints.length; i++){
			bc = new BronzeCoin(tileMap);
			bc.setPosition(bronzePoints[i].x, bronzePoints[i].y);
			coins.add(bc);
		}
		
		Point[] silverPoints = new Point[]{ 
				new Point(105, 315),
				new Point(165, 465),
				new Point(795, 255),
				new Point(855, 285),
				new Point(855, 315),
				new Point(855, 345),
				new Point(855, 375),
				new Point(855, 405),
				new Point(855, 465),
				new Point(855, 495),
				new Point(855, 525),
				new Point(1665, 285),
				new Point(1695, 285),
				new Point(1875, 225),
				new Point(2415, 225),
				new Point(2625, 435),
				new Point(2355, 675)
				};
		
		for(int i = 0; i < silverPoints.length; i++){
			sc = new SilverCoin(tileMap);
			sc.setPosition(silverPoints[i].x, silverPoints[i].y);
			coins.add(sc);
		}
		
		Point[] goldPoints = new Point[]{ 
				new Point(45, 195),
				new Point(165, 135),
				new Point(855, 435),
				};
		
		for(int i = 0; i < goldPoints.length; i++){
			gc = new GoldCoin(tileMap);
			gc.setPosition(goldPoints[i].x, goldPoints[i].y);
			coins.add(gc);
			
		}
		
	}
	
	public void restart(){
		
		for(int i = 0; i < enemies.size(); i++){
			enemies.get(i).kill();
		}
		populateEnemies();
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
			player.setPosition(100, 500);
			player.reset();
			restart();
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

