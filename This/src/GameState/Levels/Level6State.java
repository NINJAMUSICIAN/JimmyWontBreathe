package GameState.Levels;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import Entity.Coin;
import Entity.Enemy;
import Entity.GoldCoin;
import Entity.HUD;
import Entity.Player;
import Entity.Tombstone;
import GameState.GameState;
import GameState.GameStateManager;
import Main.GamePanel;
import TileMap.Background;
import TileMap.TileMap;
import Entity.Enemies.*;
import Entity.*;

public class Level6State extends GameState {

	private Background bg;
	private TileMap tileMap;
	
	private Player player;
	
	private ArrayList<Enemy> enemies;
	private ArrayList<Coin> coins;
	
	
	private Tombstone finish;
	private HUD hud;
	
	public Level6State(GameStateManager gsm){
		this.gsm = gsm;
		init();
	}
	
	public void init(){
		
		
		tileMap = new TileMap(30);
		tileMap.loadTiles("/Tilesets/CaveTiles.gif");
		tileMap.loadMap("/Maps/2-3.map");
		tileMap.setPosition(0, 0);
		tileMap.setTween(1);
		
		bg = new Background("/Backgrounds/Forest.png", 0.1);
		
		player = new Player("/Sprites/JimmyRunning.gif", tileMap);
		player.setPosition(100, 500);
		
		finish = new Tombstone(2955, 675, GameStateManager.LEVEL7STATE, gsm, tileMap, false);

		
		populateEnemies();
		populateCoins();
		
		hud = new HUD(player);
	}
	
	public void populateEnemies(){
		enemies = new ArrayList<Enemy>();
		
		Flyer f;
		Ghost g;
		Fish a;
		
		Point[] fPoints = new Point[]{
				new Point(1275, 405),
				new Point(1845, 375)
		};
		
		for(int i = 0; i < fPoints.length; i++){
			f = new Flyer(tileMap, false);
			f.setPosition(fPoints[i].x, fPoints[i].y);
			enemies.add(f);
		}
		
		Point[] gPoints = new Point[]{
				new Point(900, 465),
				new Point(300, 600)
		};
		
		for(int j = 0; j < gPoints.length; j++){
			g = new Ghost(tileMap);
			g.setPosition(gPoints[j].x, gPoints[j].y);
			enemies.add(g);
		}
		
		Point[] ishPoints = new Point[]{
				new Point(195, 600),
				new Point(525, 400)
		};
		
		for(int x = 0; x < ishPoints.length; x++){
			a = new Fish("/Sprites/Enemies/fishRed.png", tileMap);
			a.setPosition(ishPoints[x].x, ishPoints[x].y);
			enemies.add(a);
		}
		
	}
	
	public void restart(){
		Flyer f;
		Ghost g;
		Fish a;
		
		for(int i = 0; i < enemies.size(); i++){
			enemies.get(i).kill();
		}
		Point[] fPoints = new Point[]{
				new Point(1275, 405),
				new Point(1845, 375)
		};
		
		for(int i = 0; i < fPoints.length; i++){
			f = new Flyer(tileMap, false);
			f.setPosition(fPoints[i].x, fPoints[i].y);
			enemies.add(f);
		}
		
		Point[] gPoints = new Point[]{
				new Point(900, 465),
				new Point(300, 600)
		};
		
		for(int j = 0; j < gPoints.length; j++){
			g = new Ghost(tileMap);
			g.setPosition(gPoints[j].x, gPoints[j].y);
			enemies.add(g);
		}
		
		Point[] ishPoints = new Point[]{
				new Point(195, 600),
				new Point(525, 400)
		};
		
		for(int x = 0; x < ishPoints.length; x++){
			a = new Fish("/Sprites/Enemies/fishRed.png", tileMap);
			a.setPosition(ishPoints[x].x, ishPoints[x].y);
			enemies.add(a);
		}
		
	}
	
	public void populateCoins(){
		coins = new ArrayList<Coin>();
		GoldCoin gc;
		BronzeCoin bc;
		SilverCoin sc;
		
		Point[] goldSpot = new Point[]{
				new Point(1275, 675)
		};
		
		for(int i = 0; i < goldSpot.length; i++){
			gc = new GoldCoin(tileMap);
			gc.setPosition(goldSpot[i].x, goldSpot[i].y);
			coins.add(gc);
		}
		
		Point[] bPoints = new Point[]{
				new Point(885, 345),
				new Point(915, 345),
				new Point(1275, 315),
				new Point(1215, 465),
				new Point(1335, 465),
				new Point(1155, 255),
				new Point(1395, 255),
				new Point(1695, 405), 
				new Point(1755, 405),
				new Point(1935, 405),
				new Point(1995, 405),
				new Point(2145, 345),
				new Point(2295, 345),
				new Point(2445, 405),
				new Point(2475, 405),
				new Point(2595, 495),
				new Point(2625, 495)
		};
		
		for(int i = 0; i < bPoints.length; i++){
			bc = new BronzeCoin(tileMap);
			bc.setPosition(bPoints[i].x, bPoints[i].y);
			coins.add(bc);
		}
		
		Point[] sPoints = new Point[]{
				new Point(345, 675),
				new Point(375, 675),
				new Point(855, 495),
				new Point(945, 495),
				new Point(1095, 170),
				new Point(1545, 405),
				new Point(1665, 645), 
				new Point(1665, 675),
				new Point(1845, 465),
				new Point(2205, 255),
				new Point(2235, 255)
		};
		
		for(int i = 0; i < sPoints.length; i++){
			sc = new SilverCoin(tileMap);
			sc.setPosition(sPoints[i].x, sPoints[i].y);
			coins.add(sc);
			
		}
		
	}
	
	public void update(){
		
		player.update();
		player.checkAttack(enemies);
		player.checkCoins(coins);
		
		finish.update();
		finish.checkGrab(player);
		
		bg.setPosition(tileMap.getx(), tileMap.gety());
		
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
