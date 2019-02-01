package galaxy;

import processing.core.PApplet;

public class Shot implements AppConst {

	private float x_, rx_, y_;

	/**
	 * 
	 * @param x
	 * @param y
	 * @param relX	is x relative to the world reference frame
	 */
	public Shot(float x, float y, float relX) {
		x_ = x; 
		y_ = y;
		rx_ = relX;
	}
	
	/**
	 * Draws the shot
	 * @param app_	instance of PApplet
	 */
	public void draw_(PApplet app_) {
		app_.pushMatrix();
		
		app_.translate(x_, 0);
		
		app_.noStroke();
		
		app_.fill(255,255,0);
		app_.rect(0, 0, 2, 1);
		
		app_.popMatrix();
	}
	
	/**
	 * Moves to the left
	 */
	public void update() {
		x_ += (0.25f - screenV);
		rx_ += (0.25f - screenV);
	}
	
	
	/**
	 * Gets the bounding box
	 * @return	The bounds of the shot
	 */
	public float[] getBounds() {
		float[] bounds = {rx_, rx_ + 2, y_ - 1, y_};//we need to use rx_ as x_ changes with the ship
		return bounds;
	}
	

}
