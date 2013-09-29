package Entity;

import GameState.Levels.Level2State;
import TileMap.TileMap;

public class Coin extends MapObject{
	
	protected int coinScore;
	protected boolean picked;
	private boolean remove;
	
	public Coin(TileMap tm){
		super(tm);
		width = 30;
		height = 30;
		cwidth = 30;
		cheight = 30;
	}
	
	public boolean isPicked(){return picked;}
	protected int getScore(){return coinScore;}
	
	public void addScore(){
		Level2State.score += coinScore;
	}
	
	public boolean shouldRemove(){return remove;}
	
	public void done(){
		remove = true;
	}
	
	public void update(){
		animation.update();
	}
	
}
