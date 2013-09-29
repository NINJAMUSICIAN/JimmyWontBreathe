package Entity.Enemies;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import Entity.Animation;
import Entity.Enemy;
import TileMap.TileMap;

public class Flyer extends Enemy{


private BufferedImage[] sprites;	
	
	public Flyer(TileMap tm, boolean goingRight){
		super(tm);
		
		moveSpeed = .3;
		maxSpeed = .3;
		maxFallSpeed = 0.0;
		
		width = 30;
		height = 30;
		cwidth = 30;
		cheight = 30;
		
		health = maxHealth = 1;
		damage = 1;
		
		deathScore = 100;
		
		//load sprites
		try{
			
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream
					("/Sprites/Enemies/FlyingGhost.png"));
			
			sprites = new BufferedImage[4];
			for(int i = 0; i < sprites.length; i++){
				sprites[i] = spritesheet.getSubimage(i * width, 0, width, height);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		animation = new Animation();
		animation.setDelay(60);
		animation.setFrames(sprites);
		
		right = false;
		facingRight = false;
		
	}
	
	public void getNextPosition(){
		if(left){
			dx -= moveSpeed;
			if(dx < -maxSpeed){
				dx = -maxSpeed;
			}
		} else if(right){
			dx += moveSpeed;
			if(dx > maxSpeed){
				dx = maxSpeed;
			}
		}
	}
	
	public void update(){
		getNextPosition();
		checkTileMapCollision();
		setPosition(xtemp, ytemp);
		
		if(flinching){
			long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
			if(elapsed > 400){
				flinching = false;
			}
		}
		
		if(right && dx == 0){
			right = false;
			left = true;
			facingRight = false;
		} else if(left && dx == 0){
			right = true;
			left = false;
			facingRight = true;
		}
		
		animation.update();

	}

	public void draw(Graphics2D g){
		
		setMapPosition();
		super.draw(g);
		
	}
	
}
