//"/Sprites/Enemies/TheDevil.png"

package Entity.Enemies;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import Entity.Animation;
import Entity.Enemy;
import TileMap.TileMap;

public class Devil extends Enemy {

	private ArrayList<Enemy> enemies;
	private int go = 0;
	private int defeated = 0;

	private ArrayList<BufferedImage[]> sprites;
	private final int[] numFrames = { 4, 1 };
	private static final int WALKING = 0;
	private static final int SPAWNING = 1;

	private TileMap tm;

	public Devil(TileMap tm, ArrayList<Enemy> enemies) {

		super(tm);
		this.tm = tm;
		this.enemies = enemies;

		enemies = new ArrayList<Enemy>();

		moveSpeed = .2;
		maxSpeed = 1;
		fallSpeed = .2;
		maxFallSpeed = 10.0;

		width = 30;
		height = 60;
		cwidth = 30;
		cheight = 60;

		health = maxHealth = 6;
		damage = 1;

		deathScore = 100000;

		// loadSprites
		try {
			BufferedImage spritesheet = ImageIO.read(getClass()
					.getResourceAsStream("/Sprites/Enemies/TheDevil.png"));

			sprites = new ArrayList<BufferedImage[]>();
			for (int i = 0; i < 2; i++) {
				BufferedImage[] bi = new BufferedImage[numFrames[i]];
				for (int j = 0; j < numFrames[i]; j++) {
					if (i == 0) {
						bi[j] = spritesheet.getSubimage(j * width, i * height,
								width, height);
					}
					if (i == 1) {
						bi[j] = spritesheet.getSubimage(j * width * 2, i
								* height, width * 2, height);
					}
				}
				sprites.add(bi);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		animation = new Animation();
		currentAction = WALKING;
		animation.setFrames(sprites.get(WALKING));
		animation.setDelay(400);

		facingRight = true;
		right = true;
	}

	public void getNextPosition() {
		if (left) {
			dx -= moveSpeed;
			if (dx < -maxSpeed) {
				dx = -maxSpeed;
			}
		} else if (right) {
			dx += moveSpeed;
			if (dx > maxSpeed) {
				dx = maxSpeed;
			}
		}
		if (falling) {
			dy += fallSpeed;
		}
	}

	public void devilSpawn() {

		Ghost g;

		Point[] points = new Point[] { new Point(150, 35), new Point(300, 35),
				new Point(450, 35) };
		if (enemies.size() < 5) {

			for (int i = 0; i < points.length; i++) {
				g = new Ghost(tm);
				g.setPosition(points[i].x, points[i].y);
				enemies.add(g);
			}
		}

		go++;

	}

	public void addDefeated() {
		defeated++;
	}

	public void update() {
		if (currentAction == WALKING) {
			getNextPosition();
		} else {
			dx = 0;
		}
		checkTileMapCollision();
		setPosition(xtemp, ytemp);

		if (flinching) {
			long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
			if (elapsed > 400) {
				flinching = false;
			}
		}

		if (right && dx == 0) {
			right = false;
			left = true;
			facingRight = false;
		} else if (left && dx == 0) {
			right = true;
			left = false;
			facingRight = true;
		}

		if (health % 2 == 0) {
			defeated = 0;
			currentAction = WALKING;

			animation.setFrames(sprites.get(WALKING));
			animation.setDelay(50);
			width = 30;
			go = 0;
		} else {
			currentAction = SPAWNING;
			if (go == 0) {
				devilSpawn();
			}
			animation.setFrames(sprites.get(SPAWNING));
			animation.setDelay(200);
			width = 60;
			setPosition(300, 90);

		}

		if (defeated == 3) {
			health--;
		}

		animation.update();

	}

	public void draw(Graphics2D g) {
		// if(notOnScreen()) return;

		setMapPosition();
		super.draw(g);
	}
}
