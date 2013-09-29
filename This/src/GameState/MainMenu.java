package GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import TileMap.Background;

public class MainMenu extends GameState {

	private Background bg;
	
	private int currentChoice = 0;
	private String[] options = {
			"Play",
			"Instructions",
			"Exit"
	};
	
	private Color textColor;
	private Font textFont;
	
	public MainMenu(GameStateManager gsm){
		this.gsm = gsm;
		
		try{
			bg = new Background("/Backgrounds/MainMenu.png", 1);
			bg.setVector(0, 0);
			
			textColor = new Color(51, 51, 51);
			textFont = new Font("Arial", Font.PLAIN, 28);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void init() {
		
	}

	
	public void update() {
		
		bg.update();
		
	}

	
	public void draw(Graphics2D g) {
		
		bg.draw(g);
		
		g.setColor(textColor);
		g.setFont(textFont);
		
		for(int i = 0; i < options.length; i++){
			if(i == currentChoice){
				g.setColor(Color.WHITE);
			} else {
				g.setColor(textColor);
			}
			g.drawString(options[i], 15, 58 + i * 25);
		}
	}
	
	public void select(){
		if(currentChoice == 0){
			gsm.setState(GameStateManager.CUTSCENE1);
		}else if(currentChoice == 1){
			//gsm.setState(GameStateManager.INSTRUCTIONS);
		}else{
			System.exit(0);
		}
	}

	
	public void keyPressed(int k) {
		
		if(k == KeyEvent.VK_ENTER){
			select();
		}
		if(k == KeyEvent.VK_UP){
			currentChoice--;
			if(currentChoice == -1){
				currentChoice = options.length - 1;
			}
		}
		if(k == KeyEvent.VK_DOWN){
			currentChoice++;
			if(currentChoice == options.length){
				currentChoice = 0;
				
			}
		}
	}

	
	public void keyReleased(int k) {
		
		
	}

	
	
}
