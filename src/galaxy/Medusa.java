/**
 * 
 */
package galaxy;

import processing.core.PApplet;
import processing.core.PImage;

/**
 * The Medusa ship moves fast in an up-and-down motion
 * @author Jack Botelho
 *
 */
public class Medusa extends Enemy implements AppConst {
	
	private float vx_, vy_;
	
	private int yCalls = 300;
	
	private PImage sprite;
	
	
	/**
	 * @param x	Starting x-coordinate
	 * @param y	Starting y-coordinate
	 * @param med 
	 */
	public Medusa(float x, float y, PImage med) {
		super(x, y);
		super.init(5.0f, 3.0f, 10, 2);
		vx_ = -0.1f;
		vy_ = 0.1f;
		sprite = med;
	}

	/* (non-Javadoc)
	 * @see galaxy.Enemy#draw_()
	 */
	@Override
	public void draw_(PApplet app_) {
		app_.pushMatrix();
		
		app_.translate(super.getX(), super.getY());
		app_.fill(125);
		
		float w = super.getW();
		float h = super.getH();
		
//		app_.rect(-w / 2, h / 2, w, h); //bounding box
		
		app_.pushMatrix();
		app_.scale(pixToWorld, -pixToWorld);
		int adjW = (int)(super.getW() * worldToPix);
		int adjH = (int)(super.getH() * worldToPix);
		sprite.resize(adjW, adjH);
		app_.image(sprite, -w * worldToPix / 2, -h * 1.5f * worldToPix );
		app_.popMatrix();
		
		app_.popMatrix();
	}

	/* (non-Javadoc)
	 * @see galaxy.Enemy#update()
	 */
	@Override
	public void update() {
		super.setX(vx_ + screenV);
		super.setY(vy_);
		yCalls--;
		if(yCalls <= 0 || super.getY() + super.getH() / 2 > yMax || super.getY() - super.getH() / 2 < yMin) {
			yCalls = 300;
			vy_ = -vy_;
		}
	}
	
}
