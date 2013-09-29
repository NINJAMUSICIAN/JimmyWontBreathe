package Entity;

import GameState.Levels.Level2State;
import TileMap.TileMap;

public class Enemy extends MapObject{

	protected int health;
	protected int maxHealth;
	protected int deathScore;
	protected boolean dead;
	protected int damage;
	
	protected boolean flinching;
	protected long flinchTimer;

	
	public Enemy(TileMap tm){
		super(tm);
	}
	
	public boolean isDead(){return dead;}
	public int getDamage(){return damage;}
	public int getScore(){return deathScore;}
	
public void addScore(int score){	
		Level2State.score += deathScore;
	}

public void kill(){dead = true;}
	
	public void hit(int damage){
		if(dead || flinching) return;
		health -= damage;
		if(health < 0) health = 0;
		if(health == 0) dead = true;
		flinching = true;
		flinchTimer = System.nanoTime();
	}
	
	public void update(){
		
	}
	
}
