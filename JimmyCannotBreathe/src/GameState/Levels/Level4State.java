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
import GameState.GameState;
import GameState.GameStateManager;
import Main.GamePanel;
import TileMap.Background;
import TileMap.TileMap;

public class Level4State extends GameState {

	private Background bg;
	private TileMap tileMap;
	
	private Player player;
	
	private ArrayList<Enemy> enemies;
	private ArrayList<Coin> coins;
	
	private Tombstone finish;
	private HUD hud;
	Fish fish;
	
	
	public Level4State(GameStateManager gsm){
		this.gsm = gsm;
		init();
	}
	
	public void init(){
		
		tileMap = new TileMap(30);
		tileMap.loadTiles("/Tilesets/CaveTiles.gif");
		tileMap.loadMap("/Maps/2-1.map");
		tileMap.setPosition(0, 0);
		tileMap.setTween(1);
		bg = new Background("/Backgrounds/Forest.png", 0.1);
		
		player = new Player("/Sprites/JimmyRunning.gif", tileMap);
		player.setPosition(100, 400);
		
		finish = new Tombstone(2955, 495, GameStateManager.LEVEL5STATE, gsm, tileMap, false);
		
		populateEnemies();
		populateCoins();
		
		hud = new HUD(player);
		
	}
	
	public void populateEnemies(){
		enemies = new ArrayList<Enemy>();
		
		Ghost g;
		Fish f;
		
		Point[] gPoints = new Point[]{
				new Point(740, 440),
				new Point(1070, 440),
				new Point(2760, 440)
		};
		
		for(int i = 0; i < gPoints.length; i++){
			g = new Ghost(tileMap);
			g.setPosition(gPoints[i].x, gPoints[i].y);
			enemies.add(g);
		}
		
		Point[] fPoints = new Point[]{
				new Point(255, 100),
				new Point(945, 100),
				new Point(1245, 200),
				new Point(1455, 100),
				new Point(2255, 300),
				new Point(2415, 200)
		};
		
		for(int j = 0; j < fPoints.length; j++){
			f = new Fish("/Sprites/Enemies/fishRed.png", tileMap);
			f.setPosition(fPoints[j].x, fPoints[j].y);
			enemies.add(f);
		}
	}
	
	public void hangoverMonsters(){
		for(int i = 0; i < 1; i++){
		Ghost g;
		
		Point[] gPoints = new Point[]{
				new Point(800, 440),
				new Point(1130, 440),
				new Point(2830, 440),
				new Point(2900, 440),
				new Point(740, 440),
				new Point(1070, 440),
				new Point(2760, 440)
		};
		for(int j = 0; j < gPoints.length; j++){
			g = new Ghost(tileMap);
			g.setPosition(gPoints[j].x, gPoints[j].y);
			enemies.add(g);
			}
		}
		player.setHungover(false);
	}
	
	public void populateCoins(){
		coins = new ArrayList<Coin>();
		
		BronzeCoin bc;
		SilverCoin sc;
		GoldCoin gc;
		
		Point[] bPoints = new Point[]{
			new Point(345, 465),
			new Point(375, 435),
			new Point(405, 405),
			new Point(435, 375),
			new Point(735, 345),
			new Point(795, 345),
			new Point(1095, 345),
			new Point(1335, 495),
			new Point(1365, 495),
			new Point(1485, 255),
			new Point(1515, 255),
			new Point(1815, 255),
			new Point(1875, 255),
			new Point(1995, 255),
			new Point(2055, 255),
			new Point(2175, 375),
			new Point(2205, 375),
			new Point(2325, 345),
			new Point(2355, 345),
			new Point(2475, 315),
			new Point(2505, 315),
			
		};
		
		for(int i = 0; i < bPoints.length; i++){
			bc = new BronzeCoin(tileMap);
			bc.setPosition(bPoints[i].x, bPoints[i].y);
			coins.add(bc);
		}
		
		Point[] sPoints = new Point[]{
				new Point(735, 495),
				new Point(795, 495),
				new Point(1065, 495),
				new Point(1125, 495),
				new Point(1365, 220),
				new Point(1335, 220),
				new Point(1305, 220),
				new Point(1695, 280),
				new Point(2265, 310),
				new Point(2415, 280)
		};
		for(int j = 0; j < sPoints.length; j++){
			sc = new SilverCoin(tileMap);
			sc.setPosition(sPoints[j].x, sPoints[j].y);
			coins.add(sc);
		}
		
		Point[] gPoints = new Point[]{
			new Point(1185, 130),
			new Point(1935, 495)
		};
		
		for(int b = 0; b < gPoints.length; b++){
			gc = new GoldCoin(tileMap);
			gc.setPosition(gPoints[b].x, gPoints[b].y);
			coins.add(gc);
		}
		
	}
	
	public void restart(){
		for(int i = 0; i < enemies.size(); i++){
			enemies.get(i).kill();
		}
		
		Ghost g;
		Fish f;
		
		Point[] gPoints = new Point[]{
				new Point(740, 440),
				new Point(1070, 440),
				new Point(2760, 440)
		};
		
		for(int i = 0; i < gPoints.length; i++){
			g = new Ghost(tileMap);
			g.setPosition(gPoints[i].x, gPoints[i].y);
			enemies.add(g);
		}
		
		Point[] fPoints = new Point[]{
				new Point(255, 100),
				new Point(945, 100),
				new Point(1245, 200),
				new Point(1455, 100),
				new Point(2255, 300),
				new Point(2415, 200)
		};
		
		for(int j = 0; j < fPoints.length; j++){
			f = new Fish("/Sprites/Enemies/fishRed.png", tileMap);
			f.setPosition(fPoints[j].x, fPoints[j].y);
			enemies.add(f);
		}	
		
	}
	
	public void update(){
		player.update();
		player.checkAttack(enemies);
		player.checkCoins(coins);
		
		finish.update();
		finish.checkGrab(player);
		
//		System.out.println(player.gety());
//		System.out.println(player.getx());
		
		bg.setPosition(tileMap.getx(), tileMap.gety());
		
		tileMap.setPosition(
				GamePanel.WIDTH / 2 - player.getx(),
				GamePanel.HEIGHT / 2 - player.gety());
		
		if(player.isDead()){
			player.setPosition(100, 400);
			player.revive();
			player.reset();
			restart();
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
		
		for(int i = 0; i < coins.size(); i++){
			Coin c = coins.get(i);
			c.update();
			if(c.shouldRemove()){
				c.addScore();
			coins.remove(i);
			i--;
		}
	}
		bg.setPosition(tileMap.getx(), tileMap.gety());
	}
	
	public void draw(Graphics2D g){
		bg.draw(g);
		
		tileMap.draw(g);
		
		player.draw(g);
		
		for(int i = 0; i < coins.size(); i++){
			coins.get(i).draw(g);
		}

		finish.draw(g);
		
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
