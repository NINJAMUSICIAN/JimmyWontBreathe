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
import Entity.Enemies.Fish;
import Entity.Enemies.Flyer;
import Entity.Enemies.Ghost;
import GameState.GameState;
import GameState.GameStateManager;
import Main.GamePanel;
import TileMap.Background;
import TileMap.TileMap;

public class Level5State extends GameState {

	private Background bg;
	private TileMap tileMap;
	
	private Player player;
	
	private ArrayList<Enemy> enemies;
	private ArrayList<Coin> coins;
	
	private Tombstone finish;
	private HUD hud;
	
	public Level5State(GameStateManager gsm){
		this.gsm = gsm;
		init();
	}

	public void init() {
		
		tileMap = new TileMap(30);
		tileMap.loadTiles("/Tilesets/CaveTiles.gif");
		tileMap.loadMap("/Maps/2-2.map");
		tileMap.setPosition(0, 0);
		tileMap.setTween(1);
		bg = new Background("/Backgrounds/Forest.png", 0.1);
		
		player = new Player("/Sprites/JimmyRunning.gif", tileMap);
		player.setPosition(100, 400);
		
		finish = new Tombstone(2955, 675, GameStateManager.LEVEL6STATE, gsm, tileMap, false);
		
		populateEnemies();
		populateCoins();
		
		hud = new HUD(player);
		
	}

	public void populateEnemies(){
		enemies = new ArrayList<Enemy>();
		
		Ghost g;
		Flyer f;
		Fish is;
		
		Point[] gPoints = new Point[]{
			new Point(420, 585),
			new Point(1575, 255),
			new Point(1890, 255),
			new Point(1710, 315)
		};
		
		for(int i = 0; i < gPoints.length; i++){
			g = new Ghost(tileMap);
			g.setPosition(gPoints[i].x, gPoints[i].y);
			enemies.add(g);
		}
		
		Point[] fPoints = new Point[]{
			new Point(1215, 285)
		};
		
		for(int j = 0; j < fPoints.length; j++){
			f = new Flyer(tileMap, false);
			f.setPosition(fPoints[j].x, fPoints[j].y);
			enemies.add(f);
		}
		
		Point[] ishPoints = new Point[]{
				 new Point(180, 525),
				 new Point(300, 100), 
				 new Point(1035, 85),
				 new Point(1065, 600)
		};
		
		for(int l = 0; l < ishPoints.length; l++){
			is = new Fish("/Sprites/Enemies/fishRed.png", tileMap);
			is.setPosition(ishPoints[l].x, ishPoints[l].y);
			enemies.add(is);
		}
	}
	
	public void populateCoins(){
		coins = new ArrayList<Coin>();
		
		BronzeCoin bc;
		SilverCoin sc;
	
		Point[] bPoints = new Point[]{
				new Point(255, 615),
				new Point(615, 555),
				new Point(765, 495),
				new Point(825, 435),
				new Point(885, 375),
				new Point(945, 315),
				new Point(1215, 285),
				new Point(1245, 285),
				new Point(1485, 225),
				new Point(1635, 225),
				new Point(1785, 225),
				new Point(1935, 225),
				new Point(2205, 285)
				
		};
		
		for(int i = 0; i < bPoints.length; i++){
			bc = new BronzeCoin(tileMap);
			bc.setPosition(bPoints[i].x, bPoints[i].y);
			coins.add(bc);
		}
		
		Point[] sPoints = new Point[]{
				new Point(345, 585),
				new Point(495, 585),
				new Point(1125, 315),
				new Point(1335, 255),
				new Point(1365, 255),
				new Point(1695, 315),
				new Point(2355, 255)
				
		};
		
		for(int j = 0; j < sPoints.length; j++){
			sc = new SilverCoin(tileMap);
			sc.setPosition(sPoints[j].x, sPoints[j].y);
			coins.add(sc);
		}
		
	}
	
	public void restart(){
		for(int i = 0; i < enemies.size(); i++){
			enemies.get(i).kill();
		}
		
		Ghost g;
		Flyer f;
		Fish is;
		
		Point[] gPoints = new Point[]{
			new Point(420, 585),
			new Point(1575, 255),
			new Point(1890, 255),
			new Point(1710, 315)
		};
		
		for(int i = 0; i < gPoints.length; i++){
			g = new Ghost(tileMap);
			g.setPosition(gPoints[i].x, gPoints[i].y);
			enemies.add(g);
		}
		
		Point[] fPoints = new Point[]{
			new Point(1215, 285)
		};
		
		for(int j = 0; j < fPoints.length; j++){
			f = new Flyer(tileMap, false);
			f.setPosition(fPoints[j].x, fPoints[j].y);
			enemies.add(f);
		}
		
		Point[] ishPoints = new Point[]{
				 new Point(180, 525),
				 new Point(300, 100), 
				 new Point(1035, 85),
				 new Point(1065, 600)
		};
		
		for(int l = 0; l < ishPoints.length; l++){
			is = new Fish("/Sprites/Enemies/fishRed.png", tileMap);
			is.setPosition(ishPoints[l].x, ishPoints[l].y);
			enemies.add(is);
		}
		
	}
	
	public void update() {
		
		player.update();
		player.checkAttack(enemies);
		player.checkCoins(coins);
		
		finish.update();
		finish.checkGrab(player);
		
		bg.setPosition(tileMap.getx(), tileMap.gety());
		
		if(player.isDead()){
			player.setPosition(100, 400);
			player.revive();
			player.reset();
			restart();
		}
		
		tileMap.setPosition(
				GamePanel.WIDTH / 2 - player.getx(),
				GamePanel.HEIGHT / 2 - player.gety());
		
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
	
	public void draw(Graphics2D g) {
		bg.draw(g);
		tileMap.draw(g);
		
		player.draw(g);
		
		for(int i = 0; i < coins.size(); i++){
			coins.get(i).draw(g);
		}
		
		for(int i = 0; i < enemies.size(); i++){
			enemies.get(i).draw(g);
		}
		
		finish.draw(g);
		
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
