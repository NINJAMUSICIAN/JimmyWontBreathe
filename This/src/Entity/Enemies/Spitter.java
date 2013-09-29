package Entity.Enemies;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import Entity.Animation;
import Entity.Enemy;
import Entity.Player;
import TileMap.TileMap;

public class Spitter extends Enemy {

	@SuppressWarnings("unused")
	private TileMap tm;
	
	//Spitting
	@SuppressWarnings("unused")
	private int screamDamage;
	private ArrayList<Spit> screams;
	private long spitTime;
	
	//Animations
	private ArrayList<BufferedImage[]> sprites;
	private final int[] numFrames = {
			1, 3
	};
	
	private static final int IDLE = 0;
	private static final int CHARGING = 1;
	private static final int SPITTING = 2;
	
	
	public Spitter(boolean f ,String s, TileMap tm) {
		super(tm);
		this.tm = tm;
		facingRight = f;
		right = f;
		
		moveSpeed = 0;
		maxSpeed = 1.5;
		fallSpeed = 0.0;
		maxFallSpeed = 0.0;
		
		width = 30;
		height = 30;
		cwidth = 30;
		cheight = 30;
		
		health = maxHealth = 1;
		damage = 1;
		
		screamDamage = 1;
		screams = new ArrayList<Spit>();
		
		deathScore = 100;
		
		try{
			
			BufferedImage spritesheet = ImageIO.read(
					getClass().getResourceAsStream(s
							)
						);
			
			sprites = new ArrayList<BufferedImage[]>();
			
			for(int i = 0; i < 2; i++){
				
				BufferedImage[] bi = 
						new BufferedImage[numFrames[i]];
				
				for(int j = 0; j < numFrames[i]; j++){
					
						bi[j] = spritesheet.getSubimage(
								j * width,
								i * height,
								width,
								height
							);
						}
				sprites.add(bi);
					}
				}
		catch(Exception e){
		e.printStackTrace();	
		}
		animation = new Animation();
		currentAction = IDLE;
		animation.setFrames(sprites.get(IDLE));
		animation.setDelay(400);
	}

	public void charger(){
		if(currentAction == IDLE){
			spitTime = System.nanoTime();
			currentAction = CHARGING;
		}
		
	}
	
	public void checkSpitHit(Player p){
		
		for(int i = 0; i < screams.size(); i++){
			if(screams.get(i).intersects(p)){
				p.hit(1);
				screams.get(i).setHit();
			}
		}
		
	}
	
	public void update(){
		checkTileMapCollision();
		
		animation.update();
		
		if(currentAction == CHARGING){
			long elapsed = (System.nanoTime() - spitTime) / 1000000;
			if(elapsed > 5000){
				currentAction = SPITTING;
			}
		}
		
		if(currentAction == SPITTING){
			Spit sc = new Spit("/Sprites/Enemies/SpitterBall.png",facingRight, tileMap);
			sc.setPosition(x, y);
			screams.add(sc);	
			currentAction = IDLE;
		}
		
		for(int i = 0; i < screams.size(); i++){
			screams.get(i).update();
			if(screams.get(i).shouldRemove()){
				screams.remove(i);
				i--;
			}
		}
		
		charger();

	}
	
	public void draw(Graphics2D g){
		
		setMapPosition();
		
		for(int i = 0; i < screams.size(); i++){
			screams.get(i).draw(g);
		}
		super.draw(g);
		
	}
	
}
