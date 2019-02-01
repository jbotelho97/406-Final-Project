/**
 * 
 */
package galaxy;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PImage;

/**
 * Astroid, cannot be destroyed, must be avoided. Does not fight back
 * @author jbote
 *
 */
public class Rectangle implements AppConst {

	/**
	 * Our instance of PApplet used.
	 */
	private static PApplet app_;
	
	public float x_, y_, w_, h_;
	
	public boolean alive;
	
	private PImage sprite;
	
	public Rectangle(float x, float y, PImage rock) {
		x_ = x;
		y_ = y;
		w_ = 15.f;
		h_ = 15.f;
		alive = true;
		sprite = rock;
		int adjW = (int)(w_ * worldToPix);
		sprite.resize(adjW, adjW);
	}
	
	public void draw_() {
		app_.pushMatrix();
		
		app_.translate(x_, y_);
		app_.stroke(0);
		app_.fill(0, 255, 0);
		app_.rect(-w_ / 2, -h_ / 2, w_, h_);
		
		app_.pushMatrix();
		app_.scale(pixToWorld, -pixToWorld);
		
		app_.image(sprite, -w_ * worldToPix / 2, -h_ * worldToPix / 2);
		app_.popMatrix();
		
		app_.fill(255);

		app_.popMatrix();
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
	
	protected static void setup_(PApplet app) {
		app_ = app;
	}

	public void update() {
		x_ += screenV;
		
	}
	
	//Moves rect off-screen
	public void die() {
		y_ = -100f;
		x_ = 0f;
		alive = false;
		
	}

}
