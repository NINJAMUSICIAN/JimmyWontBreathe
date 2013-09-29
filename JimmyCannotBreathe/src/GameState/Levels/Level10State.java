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
	import Entity.Enemies.Ghost;
	import GameState.GameState;
	import GameState.GameStateManager;
	import Main.GamePanel;
	import TileMap.Background;
	import TileMap.TileMap;

	public class Level10State extends GameState{

		private TileMap tileMap;
		private Background bg;
		
		private Player player;
		
		private ArrayList<Enemy>enemies;
		private ArrayList<Coin>coins;
		
		private Tombstone finish;
		private HUD hud;
		
		public Level10State(GameStateManager gsm){
			this.gsm = gsm;
			init();
		}
		
		
		public void init() {
			tileMap = new TileMap(30);
			tileMap.loadTiles("/Tilesets/hellTileSet.png");
			tileMap.loadMap("/Maps/4-1.map");
			tileMap.setPosition(0, 0);
			tileMap.setTween(1);
			
			bg = new Background("/Backgrounds/DarkForestLoop.png", 0.1);
			
			player = new Player("/Sprites/RedJimmy.png", tileMap);
			player.setPosition(90, 180);
			
			finish = new Tombstone(2955, 795, GameStateManager.LEVEL11STATE, gsm, tileMap, false);
			
			populateEnemies();
			populateCoins();
			
			hud = new HUD(player);
			
		}
			
		public void populateEnemies(){
			enemies = new ArrayList<Enemy>();
			
			Ghost g;
			
			Point[] ghostSpots = new Point[]{ 
					new Point(719, 640),
					new Point(879, 640),
					new Point(975, 640),
					new Point(1135, 580),
					};
			
			
			for(int i = 0; i < ghostSpots.length; i++){
				g = new Ghost(tileMap);
				g.setPosition(ghostSpots[i].x, ghostSpots[i].y);
				enemies.add(g);
			}
//			
//			
//			for(int i = 0; i < fishSpots.length; i++){
//				f = new Fish("/Sprites/Enemies/fishBlack.png", tileMap);
//				f.setPosition(fishSpots[i].x, fishSpots[i].y);
//				enemies.add(f);
//			}
			
		}
		
		public void populateCoins(){
			
			coins = new ArrayList<Coin>();
			
			BronzeCoin bc;
			SilverCoin sc;
			GoldCoin gc;
		
			Point[] bronzePoints = new Point[]{ 
					new Point(165, 735),
					new Point(225, 705),
					new Point(405, 705),
					new Point(495, 705),
					new Point(765, 615),
					new Point(1305, 495),
					new Point(1455, 495),
					new Point(1785, 495),
					new Point(1935, 495),
					new Point(1665, 765),
					new Point(2115, 375),
					new Point(1995, 255),
					new Point(2205, 465),
					new Point(2265, 465),
					new Point(2385, 525),
					};
//			
			for(int i = 0; i < bronzePoints.length; i++){
				bc = new BronzeCoin(tileMap);
				bc.setPosition(bronzePoints[i].x, bronzePoints[i].y);
				coins.add(bc);
			}

			Point[] silverPoints = new Point[]{ 
					new Point(285, 705),
					new Point(585, 615),
					new Point(975, 555),
					new Point(1155, 495),
					new Point(1575, 495),
					new Point(1605, 495),
					new Point(1785, 735),
					new Point(1905, 735),
					new Point(2235, 465),
					new Point(1845, 225),
					};
			
			for(int i = 0; i < silverPoints.length; i++){
				sc = new SilverCoin(tileMap);
				sc.setPosition(silverPoints[i].x, silverPoints[i].y);
				coins.add(sc);
			}

			Point[] goldCoins = new Point[]{ 
					new Point(1365, 795),
					new Point(1665, 165),
					};
			
			
			for(int i = 0; i < goldCoins.length; i++){
				gc = new GoldCoin(tileMap);
				gc.setPosition(goldCoins[i].x, goldCoins[i].y);
				coins.add(gc);
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

