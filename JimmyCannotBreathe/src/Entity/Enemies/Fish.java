package Entity.Enemies;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import Entity.Animation;
import Entity.Enemy;
import TileMap.TileMap;

public class Fish extends Enemy{

	private BufferedImage[] sprites;
	private boolean up;
	private boolean down;
	
	private TileMap tm;
	
	public Fish(String s, TileMap tm) {
		super(tm);
		
		this.tm = tm;
		
		moveSpeed = .3;
		maxSpeed = 1.5;
		fallSpeed = 1.0;
		maxFallSpeed = 0;
		
		width = 30;
		height = 30;
		cwidth = 30;
		cheight = 30;
		
		health = maxHealth = 1;
		damage = 1;
		
		deathScore = 200;
		
		try{
			
			BufferedImage spriteSheet = ImageIO.read(getClass().getResourceAsStream(s));
			sprites = new BufferedImage[1];
			for(int i = 0; i < sprites.length; i++){
				sprites[i] = spriteSheet.getSubimage(i * width, 0, width, height);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		animation = new Animation();
		animation.setFrames(sprites);
		animation.setDelay(100);
		
		up = false;
		down = true;
		
	}

	public void getNextPosition(){
		
	
	if(up){
		dy -= moveSpeed;
		if(dy < -maxSpeed){
			dy = -maxSpeed;
		}
	}else{
		dy += moveSpeed;
		if(dy > maxSpeed){
			dy = maxSpeed;
			}
		}
	}
	
	public void update(){
		getNextPosition();
		checkTileMapCollision();
		setPosition(xtemp, ytemp);
		
		if(flinching){
			long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
			if(elapsed >  400){
				flinching = false;
			}
		}
		
		if(up && dy == 0){
			up = false;
			down = true;
		} else if((down && dy == 0) || (y >= tm.getHeight() - 40)){
			up = true;
			down = false;
		}
		
		animation.update();
	}

	public void draw(Graphics2D g){
		setMapPosition();
		
		if(up){
			g.drawImage(
				animation.getImage(),
				(int)(x + xmap - width / 2),
				(int)(y + ymap - height / 2),
				null
			);
		} else { 
			g.drawImage(
				animation.getImage(),
					(int)(x + xmap - width / 2),
					(int)(y + ymap - height / 2 + height),
					width,
					-height,
					null
				);
		}
	}
}
