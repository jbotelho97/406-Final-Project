/**
 * 
 */
package galaxy;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PImage;

/**
 * This is the specific ship the player character controls
 * @author Jack Botelho
 *
 */
public class Player implements AppConst {

	PImage sprite;
	
	/**
	 * Our instance of PApplet used.
	 */
	private static PApplet app_;
	
	//These is the starting position of the ship once spawned
	private float startX = -40f, startY = 0;
	
	/**
	 * Position and position variables
	 */
	private float x_, y_;
	
	private float vy_;
	public int moving; 
	
	/**
	 * Size of the player's ship
	 */
	private float width_ = 10.f, height_ = 6.0f;
	
	/**
	 * This holds the health that will will go down when character is hit
	 * When it reaches 0 that is a game over
	 */
	public int health_;
	
	/**
	 * This will hold the shots that the ship fires
	 */
	private ArrayList<Shot> shots;
	
	/**
	 * When the ship first spawns it will be invincible for a few frames
	 */
	public boolean invincible;//true if invincible, player takes no damage
	public boolean isAlive;//true if alive
	public int invinceFrames;//Frames remaining in invinciblity 
	
	public Player(PImage img) {
		x_ = startX;
		y_ = startY;
		invincible = false;
		invinceFrames = 200;
		health_ = 50;
		shots = new ArrayList();
		isAlive = true;
		sprite = img;
		vy_ = 0.0f;
		moving = 0;
	}
	
	protected static void setup_(PApplet app) {
		app_ = app;
	}
	
	public void draw_() {
		app_.pushMatrix();
		
		app_.translate(x_, y_);
		app_.stroke(0);
		
		app_.fill(255,0,0);
//		app_.rect(-width_ / 2,-height_ / 2, width_, height_); //this is the box for collision
		
		//image sizing
		app_.pushMatrix();
		app_.scale(pixToWorld, -pixToWorld);
		sprite.resize(80, 48);
		app_.image(sprite, -width_ * worldToPix / 2, -height_ * worldToPix / 2);
		app_.popMatrix();
		
		
		app_.fill(255);
		
		if(shots != null) {
			for(Shot t : shots) {
				t.draw_(app_);
			}
		}

		app_.popMatrix();
	}
	
	public void fire() {
		Shot t = new Shot(width_ / 2, y_, x_);
		shots.add(t);
	}
	
	/**
	 * Makes an array of the current bounds or 'hit-box' of the ship 
	 * @return	An array of floats which are the bounds of the ship
	 */
	public float[] getBounds() {
		float[] bounds = {x_ - width_/ 2, x_ + width_ / 2, y_ - height_ / 2, y_ + height_ / 2};
		return bounds;
	}
	
	public boolean inBoundsT() {
		return(y_ + height_ <= yMax);
	}
	
	public boolean inBoundsB() {
		return(y_ - height_>= yMin);
	}
	
	//Getters
	public float getX() {return x_;}
	public float getY() {return y_;}
	public float getW() {return width_;}
	public float getH() {return height_;}
	public int getHealth() {return health_;}
	public ArrayList<Shot> sendShot() {return shots;}
	
	//Setters
	public void setX(float x) {x_ = x;}
	public void setY(float y) {y_ = y;}
	public void setHealth(int h) {health_ = h;}

	public void update_() {
		if(health_ <= 0) {
			die();
		}
		if(shots != null) {
			for(Shot t : shots) {
				t.update();
			}
		}
		y_ += vy_;
		if(moving == 1) {
			vy_ = 0.1f;
		}
		else if(moving == -1) {
			vy_ = -0.1f;
		}
		else {
			vy_ = 0.0f;
		}
	}

	//sets is alive to false, will trigger a system exit
	private void die() {
		isAlive = false;
	}
	
	

}
