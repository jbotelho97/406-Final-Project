/**
 * 
 */
package galaxy;

import processing.core.PApplet;
import processing.core.PImage;

/**
 * The goliath will randomly go up or down with varying speeds and durations
 * @author Jack Botelho
 *
 */
public class Goliath extends Enemy implements AppConst {

	private float vx_, vy_;
	
	private float yCalls = 300;
	
	private static PApplet app;
	
	private PImage sprite;
	
	
	/**
	 * @param x
	 * @param y
	 * @param gol 
	 */
	public Goliath(float x, float y, PImage gol) {
		super(x, y);
		super.init(20, 12, 15, 3);
		vx_ = -0.05f;
		vy_ = 0.0f;
		sprite = gol;
	}

	/* (non-Javadoc)
	 * @see galaxy.Enemy#draw_(processing.core.PApplet)
	 */
	@Override
	public void draw_(PApplet app_) {
		
		app = app_;
		
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
			yCalls = app.random(300, 1000);
			vy_ = app.random(-0.5f, 0.5f);//			System.out.println("x: " + super.getX() + " y: " + super.getY());
		}
	}

}
