package Entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import TileMap.TileMap;

public class Alcohol extends MapObject{

	
	private Animation animation;
	private BufferedImage[] sprites;
	private boolean remove;

	public Alcohol(int x, int y, TileMap tm){
		super(tm);
		
		this.x = x;
		this.y = y;	

		width = 30;
		height = 30;
		cwidth = 30;
		cheight = 30;

		try{
			BufferedImage spritesheet = ImageIO.read(
				getClass().getResourceAsStream(
					"/PowerUps/Alcohol.png"
					)
				);

			sprites = new BufferedImage[1];
			for(int i = 0; i < sprites.length; i++){
				sprites[i] = spritesheet.getSubimage(
					i * width,
					0,
					width,
					height
					);
			}

		}catch(Exception e){
			e.printStackTrace();
		}

		animation = new Animation();
		animation.setFrames(sprites);
		animation.setDelay(-1);
		
	}

	
	public void update(){
		animation.update();
	
	}

	public boolean shouldRemove(){return remove;}
		
	public void done(){
		remove = true;
	}
	
	public void setMapPosition(int x, int y){
		xmap = x;
		ymap = y;
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
