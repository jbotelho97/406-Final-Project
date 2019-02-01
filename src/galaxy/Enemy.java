/**
 * 
 */
package galaxy;

import java.util.ArrayList;

import processing.core.PApplet;

/**
 * Abstract class that holds all of the enemy types
 * @author Jack Botelho
 *
 */
public abstract class Enemy implements AppConst {

	/**
	 * X and Y positions as well as width and height
	 */
	private float x_, y_, w_, h_;
	
	
	/**
	 * Damage it deals, health it has.
	 */
	private int damage_, health_;
	
	public boolean alive;
	
	
	public Enemy(float x, float y) {
		x_ = x;
		y_ = y;
		alive = true;
	}
	//Getters
	public int getDamage() {return damage_;}
	public int getHealth() {return health_;}
	public float getX() {return x_;}
	public float getY() {return y_;}
	public float getW() {return w_;}
	public float getH() {return h_;}
	
	public void setX(float x) {x_ += x;}
	public void setY(float y) {y_ += y;}
	
	public void init(float w, float h, int d, int health) {
		w_ = w;
		h_ = h;
		damage_ = d;
		health_ = health;
	}
	/**
	 * This will return true upon touching the player's ship
	 * @param p	Player instance
	 * @return	True for a collision, false otherwise
	 */
	public boolean collision(Player p) {
		//0 - xmin, 1 - xmax, 2 - ymin, 3 - ymax
		float[] bounds = p.getBounds();
		float xmin = x_ - w_ / 2;
		float xmax = x_ + w_ / 2;
		float ymin = y_ - h_ / 2;
		float ymax = y_ + h_ / 2;
		
		//Detects if the enemy is inside of the player's ship
		if((xmin >= bounds[0] && xmin <= bounds[1]) || (xmax >= bounds[0] && xmax <= bounds[1])) {
			if((bounds[3] >= ymin && bounds[3] <= ymax) || (bounds[2] >= ymin && bounds[2] <= ymax)) {
				return true;
			}
		}
		
		return false;
	}
	
	public boolean shotCollision(ArrayList<Shot> shots) {
		float xmin = x_ - w_ / 2;
		float xmax = x_ + w_ / 2;
		float ymin = y_ - h_ / 2;
		float ymax = y_ + h_ / 2;
		
		for(Shot cshot: shots) {
			float[] bounds = cshot.getBounds();
			if((xmin >= bounds[0] && xmin <= bounds[1]) || (xmax >= bounds[0] && xmax <= bounds[1])) {
				if(bounds[3] >= ymin && bounds[3] <= ymax) {
					shots.remove(cshot);
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Calculates damage taken from a hit
	 * @param dmg	Damage taken
	 */
	public void takeHit(int dmg) {
		health_ -= dmg;
	}
	
	
	/**
	 * Draws the enemy
	 */
	public abstract void draw_(PApplet app_);
	
	/**
	 * Updates movement
	 */
	public abstract void update();

	
	
	public void die() {
		y_ = -100f;
		x_ = 0f;
		alive = false;
		
	}

}
