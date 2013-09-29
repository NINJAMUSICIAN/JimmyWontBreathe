package Entity;

import TileMap.*;

import java.util.ArrayList;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends MapObject {
	
	// player stuff
	private int health;
	private int maxHealth;
	private int fire;
	private int maxFire;
	private boolean drunk = false;
	private boolean hungover;
	private TileMap tiley;
	
	private boolean dead;
	private boolean canMove = true;
	
	private boolean flinching;
	private long flinchTimer;
	private long drunkTimer;
	
	// fireball
	private boolean screaming;
	private int screamCost;
	private int screamDamage;
	private ArrayList<Scream> screams;
	
	// scratch
	private boolean bashing;
	private int bashDamage;
	private int bashRange;
	
	// gliding
	private boolean gliding;
	
	// animations
	private ArrayList<BufferedImage[]> sprites;
	private final int[] numFrames = {
		1, 6, 1, 1, 3, 1
	};
	
	// animation actions
	private static final int IDLE = 0;
	private static final int WALKING = 1;
	private static final int JUMPING = 2;
	private static final int FALLING = 3;
	private static final int SCREAMING = 4;
	private static final int BASHING = 5;
	
	
	public Player(String s, TileMap tm) {
		
		super(tm);
		this.tiley = tm;
		//width = size of animation/tiles
		width = 30;
		height = 60;
		//cwidth = makes the dragon go kinda into the ground
		cwidth = 30;
		cheight = 50;
		
		moveSpeed = 0.6;
		maxSpeed = 2.0;
		stopSpeed = 0.4;
		fallSpeed = 0.15;
		maxFallSpeed = 4.0;
		jumpStart = -6.5;
		stopJumpSpeed = 0.3;
		
		facingRight = true;
		
		health = maxHealth = 5;
		fire = maxFire = 2500;
		
		screamCost = 200;
		screamDamage = 1;
		screams = new ArrayList<Scream>();
	
		drunk = false;
		hungover = false;
		
		bashDamage = 1;
		bashRange = 64;
		
		// load sprites
		try {
			
			BufferedImage spritesheet = ImageIO.read(
				getClass().getResourceAsStream(s));
			
			sprites = new ArrayList<BufferedImage[]>();
			for(int i = 0; i < 6; i++) {
				
				BufferedImage[] bi =
					new BufferedImage[numFrames[i]];
				
				for(int j = 0; j < numFrames[i]; j++) {
					
					if(i == 0) {
						bi[j] = spritesheet.getSubimage(
								j * width,
								i * height,
								width,
								height
						);
					}
					else if(i > 1 && i < 5){
						bi[j] = spritesheet.getSubimage(
								j * width,
								i * height,
								width,
								height
						);
					} else if(i == 1){
					
						bi[j] = spritesheet.getSubimage(
								j * width * 2,
								i * height,
								width * 2,
								height
						);
						}else if(i == 5){
							
							bi[j] = spritesheet.getSubimage(
									j * width * 4,
									i * height,
									width * 4,
									height
							);
						}
					}
				sprites.add(bi);
					
				}
			}
			
		catch(Exception e) {
			e.printStackTrace();
		}
		
		animation = new Animation();
		currentAction = IDLE;
		animation.setFrames(sprites.get(IDLE));
		animation.setDelay(400); 
		
	}
	
	public int getHealth() { return health; }
	public int getMaxHealth() { return maxHealth; }
	public int getFire() { return fire; }
	public int getMaxFire() { return maxFire; }
	
	//power ups
	public void setDrunk(boolean b){
		drunk = b;
	}
	public boolean isDrunk(){return drunk;}
	public boolean isHungover() {
		return hungover;
	}
	public void setHungover(boolean hungover) {
		this.hungover = hungover;
	}

	public void setFiring() { 
		screaming = true;
	}
	public void setScratching() {
		bashing = true;
	}
	public void setGliding(boolean b) { 
		gliding = b;
	}
	
	public void setMovable(boolean b){
		canMove = b;
	}
	public boolean getMovable(){
		return canMove;
	}
	
	public boolean isDead(){return dead;}
	public void kill(){dead = true;}
	public void revive(){dead = false;}
	
	public void setDx(double x){
		dx = x;
	}
	
	public void setCurrentAction(int action){
		currentAction = action;
}
	
	public void checkDrunk(ArrayList<Alcohol> drinks){
		for(int i = 0; i < drinks.size(); i++){
			Alcohol a = drinks.get(i);
			if(intersects(a)){
				drink();
				a.done();
			
			}
		}
	}

	public void checkCoins(ArrayList<Coin> coins){
		for(int i = 0; i < coins.size(); i++){
			Coin c = coins.get(i);
			if(intersects(c)){
				c.addScore();
				c.done();
			}
		}
	}
	
	public void checkAttack(ArrayList<Enemy> enemies){
		//loop through enemies
		for(int i = 0; i < enemies.size(); i++){
			Enemy e = enemies.get(i);
			if(bashing){
				if(facingRight){
					if(
						e.getx() > x &&
						e.getx() < x + bashRange &&
						e.gety() > y - height / 2 &&
						e.gety() < y + height / 2
						
						){
						e.hit(bashDamage);
					}
				}else{
					if(e.getx() < x &&
					   e.getx() > x - bashRange &&
					   e.gety() > y - height / 2 &&
					   e.gety() < y + height / 2
					   ){
						e.hit(bashDamage);
					}
				}
			}
			for(int j = 0; j < screams.size(); j++){
				if(screams.get(j).intersects(e)){
					e.hit(screamDamage);
					screams.get(j).setHit();
					break;
				}
			}
			
			if(intersects(e)){
				hit(e.getDamage());
			}
			
		}
	}
	
	public void checkFallDead(){
		if(y > tiley.getHeight() - 70){
			dead = true;
		}
	}
	
	public void hit(int damage){
		if(flinching) return;
		health -= damage;
		if(health < 0) health = 0;
		if(health == 0) dead = true;
		flinching = true;
		flinchTimer = System.nanoTime();
		
	}
	
	public void drink(){
		
		drunk = true;
		drunkTimer = System.nanoTime();
		
		
	}
	
	private void getNextPosition() {
		
		// movement
		if(left) {
			dx -= moveSpeed;
			if(dx < -maxSpeed) {
				dx = -maxSpeed;
			}
		}
		else if(right) {
			dx += moveSpeed;
			if(dx > maxSpeed) {
				dx = maxSpeed;
			}
		}
		else {
			if(dx > 0) {
				dx -= stopSpeed;
				if(dx < 0) {
					dx = 0;
				}
			}
			else if(dx < 0) {
				dx += stopSpeed;
				if(dx > 0) {
					dx = 0;
				}
			}
		}
		
		// cannot move while attacking, except in air
		if(
		(currentAction == BASHING || currentAction == SCREAMING) &&
		!(jumping || falling)) {
			dx = 0;
		}
		
		// jumping
		if(jumping && !falling) {
			dy = jumpStart;
			falling = true;	
		}
		
		// falling
		if(falling) {
			
			if(dy > 0 && gliding) dy += fallSpeed * 0.1;
			else dy += fallSpeed;
			
			if(dy > 0) jumping = false;
			if(dy < 0 && !jumping) dy += stopJumpSpeed;
			
			if(dy > maxFallSpeed) dy = maxFallSpeed;
			
		}
		
	}
	
	public void update() {
		
		// update position
		getNextPosition();
		checkTileMapCollision();
		checkFallDead();
		setPosition(xtemp, ytemp);
		
		
		
		//check attack has stopped
		if(currentAction == BASHING){
			if(animation.hasPlayedOnce()) bashing = false;
		}
		if(currentAction == SCREAMING){
			if(animation.hasPlayedOnce()) screaming = false;
		}
		
		//scream attack
		fire += 1;
		if(fire > maxFire) fire = maxFire;
		if(screaming && currentAction != SCREAMING){
			if(fire > screamCost){
				fire -= screamCost;
				Scream sc = new Scream(tileMap, facingRight);
				sc.setPosition(x, y);
				screams.add(sc);
			}
		}
		
		//update screams
		for(int i = 0; i < screams.size(); i++){
			screams.get(i).update();
			if(screams.get(i).shouldRemove()){
				screams.remove(i);
				i--;
			}
		}
		//check done flinching
		if(flinching){
			long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
			if(elapsed > 1000){
				flinching = false;
			}
		}
		
		if(drunk){
			long elapsed = (System.nanoTime() - drunkTimer) / 1000000;
			if(elapsed > 15000){
				drunk = false;
				hungover = true;
			}
		}
		
		// set animation
		if(bashing) {
			if(currentAction != BASHING) {
				currentAction = BASHING;
				animation.setFrames(sprites.get(BASHING));
				animation.setDelay(100);
					width = 120;
			}
		}
		else if(screaming) {
			if(currentAction != SCREAMING) {
				currentAction = SCREAMING;
				animation.setFrames(sprites.get(SCREAMING));
				animation.setDelay(100);
				
				width = 30;
				
			}
		}
		else if(dy > 0) {
			 if(currentAction != FALLING) {
				currentAction = FALLING;
				animation.setFrames(sprites.get(FALLING));
				animation.setDelay(100);
				
					width = 30;
				
			}
		}
		else if(dy < 0) {
			if(currentAction != JUMPING) {
				currentAction = JUMPING;
				animation.setFrames(sprites.get(JUMPING));
				animation.setDelay(-1);
				
					width = 30;
				
			}
		}
		else if(left || right) {
			if(canMove){
			if(currentAction != WALKING) {
				currentAction = WALKING;
				animation.setFrames(sprites.get(WALKING));
				animation.setDelay(80);
			
					width = 60;	
				}
			}
		}
		else {
			if(currentAction != IDLE) {
				currentAction = IDLE;
				animation.setFrames(sprites.get(IDLE));
				animation.setDelay(400);
				
					width = 30;
					
			}
		}
		
		animation.update();
		
		// set direction
		if(currentAction != BASHING && currentAction != SCREAMING) {
			if(right) facingRight = true;
			if(left) facingRight = false;
		}
		
	}
	
	public void reset(){
		health = maxHealth = 5;
	}
	
	public void draw(Graphics2D g) {
		
		setMapPosition();
		
		for(int i = 0; i < screams.size(); i++){
			screams.get(i).draw(g);
		}
		
		// draw player
		if(flinching) {
			long elapsed =
				(System.nanoTime() - flinchTimer) / 1000000;
			if(elapsed / 100 % 2 == 0) {
				return;
			}
		}
		
		super.draw(g);
		
	}
	
}