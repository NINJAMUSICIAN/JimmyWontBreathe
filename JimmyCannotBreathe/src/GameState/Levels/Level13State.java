package GameState.Levels;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import Entity.BronzeCoin;
import Entity.Coin;
import Entity.Enemy;
import Entity.HUD;
import Entity.Player;
import Entity.SilverCoin;
import Entity.Tombstone;
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
	private ArrayList<Coin> coins;
	
	private HUD hud;
		
	private Tombstone finish;
	
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
		
		player = new Player("/Sprites/RedBlueJimmy.png", tileMap);
		player.setPosition(120, 120);
		finish = new Tombstone(2955, 945, GameStateManager.MENUSTATE, gsm, tileMap, false);
//2955, 945
		
		hud = new HUD(player);
		
		populateCoins();
	}

	public void populateCoins(){
		coins = new ArrayList<Coin>();
		
		BronzeCoin bc;
		SilverCoin sc;
		
		Point[] bronzePoints = new Point[]{ 
				new Point(585, 855),
				new Point(675, 855),
				new Point(1005, 945),
				new Point(1035, 945),
				new Point(1125, 945),
				new Point(1155, 945),
				new Point(1065, 795),
				new Point(1095, 795),
				new Point(1035, 705),
				new Point(1125, 705),
				new Point(975, 615),
				new Point(855, 555),
				new Point(1035, 525),
				new Point(1125, 525),
				new Point(1275, 555),
				new Point(1335, 555),
				new Point(915, 435),
				new Point(975, 435),
				new Point(1185, 435),
				new Point(1245, 435),
				new Point(1155, 345),
				new Point(1005, 345),
				new Point(1455, 915),
				new Point(1515, 855),
				new Point(1575, 795),
				new Point(1635, 735),
				new Point(1725, 645),
				new Point(1665, 465),
				new Point(1725, 465),
				new Point(1785, 465),
				new Point(1695, 315),
				new Point(1725, 315),
				new Point(1755, 315),
				new Point(1815, 735),
				new Point(1875, 795),
				new Point(1935, 855),
				new Point(1995, 915),
				new Point(2355, 855),
				new Point(2385, 885),
				new Point(2415, 915),
				new Point(2625, 915),
				new Point(2745, 795),
				new Point(2865, 675),
				};
		
		for(int i = 0; i < bronzePoints.length;i++){
			bc = new BronzeCoin(tileMap);
			bc.setPosition(bronzePoints[i].x, bronzePoints[i].y);
			coins.add(bc);
		}
		
		Point[] silverPoints = new Point[]{ 
				new Point(1065, 945),
				new Point(1095, 945),
				new Point(1035, 795),
				new Point(1125, 795),
				new Point(1185, 615),
				new Point(825, 555),
				new Point(885, 555),
				new Point(1305, 555),
				new Point(1065, 495),
				new Point(1095, 495),
				new Point(945, 435),
				new Point(1215, 435),
				new Point(1065, 255),
				new Point(1095, 255),
				new Point(1635, 555),
				new Point(1815, 555),
				new Point(1605, 375),
				new Point(1845, 375),
				new Point(1575, 255),
				new Point(1875, 255),
				new Point(2685, 855),
				new Point(2805, 735),
				new Point(2925, 615),
				};
		
		for(int i = 0; i < silverPoints.length;i++){
			sc = new SilverCoin(tileMap);
			sc.setPosition(silverPoints[i].x, silverPoints[i].y);
			coins.add(sc);
		}
		
	}
	
	public void update() {
		player.update();
	//	player.checkAttack(enemies);
		player.checkCoins(coins);
//		
		finish.update();
		finish.checkGrab(player);
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
//		
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

