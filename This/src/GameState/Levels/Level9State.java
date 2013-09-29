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

public class Level9State extends GameState{

	private TileMap tileMap;
	private Background bg;
	
	private Player player;
	
	private ArrayList<Enemy>enemies;
	private ArrayList<Coin>coins;
	
	private Tombstone finish;
	private HUD hud;
	
	public Level9State(GameStateManager gsm){
		this.gsm = gsm;
		init();
	}
	
	
	public void init() {
		tileMap = new TileMap(30);
		tileMap.loadTiles("/Tilesets/Blackest1.png");
		tileMap.loadMap("/Maps/3-3.map");
		tileMap.setPosition(0, 0);
		tileMap.setTween(1);
		
		bg = new Background("/Backgrounds/DarkForestLoop.png", 0.1);
		
		player = new Player("/Sprites/BlackestJimmy.png", tileMap);
		player.setPosition(90, 180);
		
		finish = new Tombstone(2955, 615, GameStateManager.LEVEL10STATE, gsm, tileMap, false);
		
		populateEnemies();
		populateCoins();
		
		hud = new HUD(player);
		
	}
		
	public void populateEnemies(){
		enemies = new ArrayList<Enemy>();
		
		Ghost g;
		Fish f;
		
		Point[] ghostSpots = new Point[]{ 
				new Point(345, 645),
				new Point(435, 645),
				new Point(1155, 645),
				new Point(1275, 645),
				new Point(1395, 615),
				new Point(1545, 615),
				};
		
		for(int i = 0; i < ghostSpots.length; i++){
			g = new Ghost(tileMap);
			g.setPosition(ghostSpots[i].x, ghostSpots[i].y);
			enemies.add(g);
		}
		
		Point[] fishSpots = new Point[]{ 
				new Point(1005, 675),
				new Point(1035, 585),
				};
		
		for(int i = 0; i < fishSpots.length; i++){
			f = new Fish("/Sprites/Enemies/fishBlack.png", tileMap);
			f.setPosition(fishSpots[i].x, fishSpots[i].y);
			enemies.add(f);
		}
		
	}
	
	public void populateCoins(){
		
		coins = new ArrayList<Coin>();
		
		BronzeCoin bc;
		SilverCoin sc;
		GoldCoin gc;
		
		Point[] bronzePoints = new Point[]{ 
				new Point(315, 495),
				new Point(465, 495),
				new Point(615, 195),
				new Point(645, 195),
				new Point(1005, 255),
				new Point(1035, 255),
				new Point(1095, 285),
				new Point(1095, 345),
				new Point(1095, 405),
				new Point(1095, 615),
				new Point(1335, 585),
				new Point(1245, 225),
				new Point(1275, 225),
				new Point(1395, 315),
				new Point(1455, 315),
				new Point(1605, 195),
				new Point(1665, 195),
				new Point(1635, 405),
				new Point(1665, 405),
				new Point(1875, 375),
				new Point(1815, 165),
				new Point(1965, 165),
				new Point(2055, 255),
				new Point(2175, 165),
				new Point(2265, 165),
				new Point(2115, 615),
				new Point(2145, 615),
				new Point(2265, 615),
				new Point(2295, 615),
				new Point(2475, 255),
				new Point(2625, 285),
				};
		
		for(int i = 0; i < bronzePoints.length; i++){
			bc = new BronzeCoin(tileMap);
			bc.setPosition(bronzePoints[i].x, bronzePoints[i].y);
			coins.add(bc);
		}
		
		Point[] silverPoints = new Point[]{ 
				new Point(375, 375),
				new Point(405, 375),
				new Point(825, 255),
				new Point(1215, 645),
				new Point(1425, 315),
				new Point(1455, 615),
				new Point(1605, 585),
				new Point(1635, 195),
				new Point(1935, 615),
				new Point(1965, 315),
				new Point(2205, 165),
				new Point(2235, 165),
				new Point(2445, 615),
				new Point(2715, 225),
				};
		
		for(int i = 0; i < silverPoints.length; i++){
			sc = new SilverCoin(tileMap);
			sc.setPosition(silverPoints[i].x, silverPoints[i].y);
			coins.add(sc);
		}
		
		gc = new GoldCoin(tileMap);
		gc.setPosition(2955, 165);
		coins.add(gc);
		
	}
	
	public void update() {
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