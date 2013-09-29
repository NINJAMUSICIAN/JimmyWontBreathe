package Entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import GameState.GameStateManager;
import TileMap.TileMap;

public class Tombstone extends MapObject{

	private int goState;

	private GameStateManager gsm;
	
	private Animation animation, animation2;
	private BufferedImage[] sprites, handimations;
	private boolean remove;
	private boolean reach = false;
	private boolean draw;
	
	
	public Tombstone(int x, int y, int newState, GameStateManager gsm, TileMap tm, boolean draw) {
		super(tm);
		
		goState = newState;
		this.x = x;
		this.y = y;
		this.draw = draw;
		this.gsm = gsm;
		
		
		width = 30;
		height = 30;
		cwidth = 30;
		cheight = 30;
	
		try {
			
		BufferedImage spritesheet = ImageIO.read
				(getClass().getResourceAsStream(
						"/PowerUps/Tombstone/theStone.png"
						)
					);
		
		sprites = new BufferedImage[1];
		for(int i = 0;i < sprites.length; i++){
			sprites[i] = spritesheet.getSubimage(
					i * width,
					0,
					width,
					height
					);
		}
		BufferedImage spritesheet2 = ImageIO.read(
				getClass().getResourceAsStream(
						"/PowerUps/theHand.png"
						)
					);
		
		handimations = new BufferedImage[4];
		for(int i = 0; i < handimations.length; i++){
			handimations[i] = spritesheet2.getSubimage(i * width, 0, width, height);
			}
		
			
		}catch(Exception e){
			e.printStackTrace();
		}
		animation2 = new Animation();
		animation2.setFrames(handimations);
		animation2.setDelay(800);
		
		
		animation = new Animation();
		animation.setFrames(sprites);
		animation.setDelay(0);
		
	}
	
	public void checkGrab(Player p){
		if(intersects(p)){
			grab(p);
			p.setDx(0);
			
			
		}
	}
	
	
	
	public void grab(Player p){
		reach = true;
		p.setMovable(false);
		if(animation2.hasPlayedOnce()){
		gsm.setState(goState);
		}
	}
	
	public void update(){
		animation.update();
		animation2.update();
	}

	
	
	public boolean shouldRemove(){return remove;}
	public void done(){remove = true;}
	
	public void setMapPosition(int x, int y){
		xmap = x;
		ymap = y;
	}
	
	public void draw(Graphics2D g){
		setMapPosition();
		if(draw){
		g.drawImage(
				animation.getImage(), 
				(int)(x + xmap - width / 2),
				(int)(y + ymap - height / 2),
				null);
		}
	}
	public void drawHand(Graphics2D g){
		if(draw){
		if(reach){
		g.drawImage(
				animation2.getImage(), 
				(int)(x + xmap - width / 2),
				(int)(y + ymap - height / 2),
				null);
			}
		}
	}
	
}
