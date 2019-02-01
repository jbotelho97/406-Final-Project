/**
 * 
 */
package galaxy;

import processing.core.PApplet;
import processing.core.PImage;

/**
 * This class is the main window that holds all the other classes together
 * @author Jack Botelho
 *
 */
public class MainWindow extends PApplet implements AppConst {
	/**
	 * Hold the current frame number
	 */
	private long frame_ = 0L;
	
	/**
	 * The instance of the player's character
	 */
	Player p1;
	
	/**
	 * This class manages the terrain generation and some collision.
	 */
	Rectangle[] rect;
	
	LevelHandler billy;
	
	PImage[] sprites;
	
	/**
	 * Holds an array of all our enemies.
	 */
	Enemy[] enemies; 
	
	public int enemyCount;
	
	public void settings() {
		size(winW, winH);
	}
	
	public void setup() {
		frameRate(400);
		
		
		sprites = new PImage[4];
		
		sprites[0] = loadImage("space.jpg");//player ship
		sprites[1] = loadImage("ast.jpg");//asteroids
		sprites[2] = loadImage("med.jpg");//medusa
		sprites[3] = loadImage("bigboi.jpg");//goliath
		
		p1 = new Player(sprites[0]);//loads the player
		
		billy = new LevelHandler();//loads the level handler
		
		LevelHandler.setup_(this);//level handler needs to be setup ahead of time as it needs to generate
		//enemies before the setupGraphicsClasses method is called.
		
		//Generation of Level One
		enemies = billy.emGen(sprites[2], sprites[3]);
		
		rect = billy.recGen(sprites[1]);
		
		enemyCount = rect.length + enemies.length;
		
		setupGraphicsClasses();
	}
	
	public void draw() {
		frame_++;
		if(p1.invincible) {//after a hit player is invulnerable for a half-second
			p1.invinceFrames--;
			if(p1.invinceFrames <= 0) {
				p1.invincible = false;
				p1.invinceFrames = 200;
			}
		}
		pushMatrix();
		
		if(frame_ % 4 == 0) {
			background(0);
			translate(worldX, worldY);
			scale(worldToPix, -worldToPix);
			
			stroke(0);
			strokeWeight(pixToWorld);
			
			
			p1.draw_();
			
			//drawing and checks for collision
			for(int i = 0; i < rect.length; i++) {
				rect[i].draw_();
				if(rect[i].collision(p1) && !p1.invincible) {
					p1.health_-= 5;
					p1.invincible = true;
				}
				if(rect[i].shotCollision(p1.sendShot())) {
					rect[i].die();
					enemyCount--;
				}
			}
			for(int i = 0; i < enemies.length; i++) {
				enemies[i].draw_(this);
				if(enemies[i].collision(p1) && !p1.invincible) {//true if collision + player can be damaged
					p1.health_-= enemies[i].getDamage();
					p1.invincible = true;
				}
				if(enemies[i].shotCollision(p1.sendShot())) {
					enemies[i].die();
					enemyCount--;
				}
			}
			
			//health bar
			if(p1.invincible) {
				fill(0,0,255);
			}
			else {
				fill(255,0 ,0);
			}
			rect(xMin, yMax - 5, 2 * p1.health_, 5);
			
		}

		//updates
		for(int i = 0; i < rect.length; i++) {
			if(rect[i].alive) {
				rect[i].update();
			}
			if(rect[i].x_ < xMin - 50f) {
				enemyCount--;
				rect[i].die();
			}
		}
		for(int i = 0; i < enemies.length; i++) {
			if(enemies[i].alive) {
				enemies[i].update();
			}
			if(enemies[i].getX() < xMin - 50f) {
				enemyCount--;
				enemies[i].die();
			}
		}
		p1.update_();
		
		//if you died
		if(!(p1.isAlive)) {
			System.out.println("You Died! You made it to Level " + billy.getLevel() +"!");
			System.exit(1);
		}
		//when all enemies are eliminated move on to next level
		if(enemyCount <= 0) {
			nextLevel();
		}
		
		popMatrix();
		
		//On screen Text
		fill(255);
		textSize(50);
		text("Level :" + billy.getLevel(), 0, 590);
		textSize(25);
		text("Health :" + p1.health_, 0, 20);
		
	}
	
	/**
	 * Creates the next level
	 */
	private void nextLevel() {
		billy.upLevel();
		enemies = billy.emGen(sprites[2], sprites[3]);
		rect = billy.recGen(sprites[1]);
		enemyCount = enemies.length + rect.length;
		p1.health_ += 5;
	}

	//Handles movement and firing
	public void keyPressed() {
		switch(key) {
		case 'w' ://move up
			if(p1.inBoundsT()) {
//				p1.setY(p1.getY() + 1f);
				p1.moving = 1;
			}
			break;
		case 's' ://move down
			if(p1.inBoundsB()) {
//				p1.setY(p1.getY() - 1f);
				p1.moving = -1;
			}
			break;
		case 'e' ://fire
			p1.fire();
			break;
		}

	}
	
	public void keyReleased() {
		p1.moving = 0;
	}
	

	/**
	 * Passes an instance of PApplet to all subclasses that use it
	 */
	private void setupGraphicsClasses() {
		Player.setup_(this);
		for(int i = 0; i < rect.length; i++) {
			rect[i].setup_(this);
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PApplet.main("galaxy.MainWindow");

	}

}
