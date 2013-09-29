package Entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import GameState.Levels.Level2State;
import TileMap.TileMap;

public class SilverCoin extends Coin {
	
	private BufferedImage[] sprites;
	
	public SilverCoin(TileMap tm){
		super(tm);
		
		coinScore = 1000;
		
		try{
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/PowerUps/Coins/SilverCoin.png"));
				sprites = new BufferedImage[9];
				for(int i = 0; i < sprites.length; i++){
					sprites[i] = spritesheet.getSubimage(i * width, 0, width, height);
				}
		}catch(Exception e){
			e.printStackTrace();
		}
		animation = new Animation();
		animation.setFrames(sprites);
		animation.setDelay(100);
	}
	
	
	
	public boolean isPicked(){return picked;}
	
	public int getScore(){return coinScore;}
	
	public void addScore(){
		Level2State.score += coinScore;
	}

	
	public void setMapPosition(int x, int y){
		xmap = x;
		ymap = y;
	}
	
	public void update(){
		animation.update();
	}
	
	public void draw(Graphics2D g){
		
		setMapPosition();
		g.drawImage(
				animation.getImage(),
				(int)(x + xmap - width / 2),
				(int)(y + ymap - height / 2),
				null
			);
		
	}
	
}
