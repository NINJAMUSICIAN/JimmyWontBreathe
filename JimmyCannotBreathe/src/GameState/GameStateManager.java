package GameState;

import java.util.ArrayList;

import GameState.Cutscenes.Cut1;
import GameState.Cutscenes.Cut2;
import GameState.Levels.Level10State;
import GameState.Levels.Level11State;
import GameState.Levels.Level12State;
import GameState.Levels.Level13State;
import GameState.Levels.Level1State;
import GameState.Levels.Level2State;
import GameState.Levels.Level2Trap;
import GameState.Levels.Level3State;
import GameState.Levels.Level4State;
import GameState.Levels.Level5State;
import GameState.Levels.Level6State;
import GameState.Levels.Level7State;
import GameState.Levels.Level8State;
import GameState.Levels.Level9State;

public class GameStateManager {
	
	private ArrayList<GameState> gameStates;
	private int currentState;
	
	public static final int MENUSTATE = 0;
	public static final int CUTSCENE1 = 1;
	public static final int LEVEL1STATE = 2;
	public static final int LEVEL1TRAP = 3;
	public static final int LEVEL2STATE = 4;
	public static final int LEVEL3STATE = 5;
	public static final int CUTSCENE2 = 6;
	public static final int LEVEL4STATE = 7;
	public static final int LEVEL5STATE = 8;
	public static final int LEVEL6STATE = 9;
	public static final int LEVEL7STATE = 10;
	public static final int LEVEL8STATE = 11;
	public static final int LEVEL9STATE = 12;
	public static final int LEVEL10STATE = 13;
	public static final int LEVEL11STATE = 14;
	public static final int LEVEL12STATE = 15;
	public static final int LEVEL13STATE = 16;
	
	public GameStateManager(){
		gameStates = new ArrayList<GameState>();
		
		currentState = LEVEL13STATE;
		gameStates.add(new MainMenu(this));
		gameStates.add(new Cut1(this));
		gameStates.add(new Level1State(this));
		gameStates.add(new Level2Trap(this));
		gameStates.add(new Level2State(this));
		gameStates.add(new Level3State(this));
		gameStates.add(new Cut2(this));
		gameStates.add(new Level4State(this));
		gameStates.add(new Level5State(this));
		gameStates.add(new Level6State(this));
		gameStates.add(new Level7State(this));
		gameStates.add(new Level8State(this));
		gameStates.add(new Level9State(this));
		gameStates.add(new Level10State(this));
		gameStates.add(new Level11State(this));
		gameStates.add(new Level12State(this));
		gameStates.add(new Level13State(this));
		
	}
	
	public void setState(int state){
		currentState = state;
		gameStates.get(currentState).init();
	}
	public void update(){
		gameStates.get(currentState).update();
	}
	public void draw(java.awt.Graphics2D g){
		gameStates.get(currentState).draw(g);
	}
	public void keyPressed(int k){
		gameStates.get(currentState).keyPressed(k);
	}
	public void keyReleased(int k){
		gameStates.get(currentState).keyReleased(k);
	}
	
	
	
}
