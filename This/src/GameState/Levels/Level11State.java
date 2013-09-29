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

	public class Level11State extends GameState{

		private TileMap tileMap;
		private Background bg;
		
		private Player player;
		
		private ArrayList<Enemy>enemies;
		private ArrayList<Coin>coins;
		
		private Tombstone finish;
		private HUD hud;
		
		public Level11State(GameStateManager gsm){
			this.gsm = gsm;
			init();
		}
		
		
		public void init() {
			tileMap = new TileMap(30);
			tileMap.loadTiles("/Tilesets/hellTileSet.png");
			tileMap.loadMap("/Maps/4-2.map");
			tileMap.setPosition(0, 0);
			tileMap.setTween(1);
			
			bg = new Background("/Backgrounds/DarkForestLoop.png", 0.1);
			
			player = new Player("/Sprites/RedJimmy.png", tileMap);
			player.setPosition(120, 2920);
			
			finish = new Tombstone(690, 2955, GameStateManager.LEVEL12STATE, gsm, tileMap, false);
			
			populateEnemies();
			populateCoins();
			
			hud = new HUD(player);
			
		}
			
		public void populateEnemies(){
			enemies = new ArrayList<Enemy>();
		
		}
		
		public void populateCoins(){
			
			coins = new ArrayList<Coin>();

			BronzeCoin bc;
			SilverCoin sc;
			
			Point[] bronzePoints = new Point[]{ 
					new Point(75, 2835),
					new Point(315, 2655),
					new Point(315, 2565),
					new Point(75, 2445),
					new Point(135, 2445),
					new Point(315, 2235),
					new Point(135, 1995),
					new Point(165, 1995),
					new Point(285, 1875),
					new Point(315, 1755),
					new Point(75, 1665),
					new Point(105, 1665),
					new Point(195, 1665),
					new Point(225, 1665),
					new Point(285, 1455),
					new Point(285, 1215),
					new Point(315, 1005),
					new Point(195, 885),
					new Point(225, 885),
					new Point(45, 765),
					new Point(135, 645),
					new Point(255, 405),
					new Point(315, 285),
					new Point(45, 225),
					};
		
			Point[] silverPoints = new Point[]{ 
					new Point(165, 2745),
					new Point(165, 2325),
					new Point(195, 2325),
					new Point(225, 2115),
					new Point(135, 1545),
					new Point(165, 1545),
					new Point(75, 1335),
					new Point(75, 1095),
					new Point(195, 525),
					};
			
			for(int i = 0; i < bronzePoints.length; i++){
				bc = new BronzeCoin(tileMap);
				bc.setPosition(bronzePoints[i].x, bronzePoints[i].y);
				coins.add(bc);
			}
			
			for(int i = 0; i < silverPoints.length; i++){
				sc = new SilverCoin(tileMap);
				sc.setPosition(silverPoints[i].x, silverPoints[i].y);
				coins.add(sc);
			}
			
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

