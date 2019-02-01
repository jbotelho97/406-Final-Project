/**
 * 
 */
package galaxy;

import processing.core.PApplet;
import processing.core.PImage;

/**
 * Generates enemies and obstacles based on the current level
 * @author Jack Botelho
 *
 */
public class LevelHandler implements AppConst {

	private static PApplet app_;
	
	/**
	 * Holds the current level of difficulty the game is at.
	 */
	private int level_;
	
	public LevelHandler() {
		level_ = 1;
	}
	
	/**
	 * Getter
	 * @return	Current level
	 */
	public int getLevel() {
		return level_;
	}
	
	public Enemy[] emGen(PImage med, PImage gol) {
		int totalNum = (int)(app_.random(1.f, 10.f));
		float offset = 30f;
		Enemy[] em = new Enemy[totalNum];
		for(int i = 0; i < em.length; i++) {
			int rand = (int)(app_.random(1, 100));
			if(rand % 2 == 0) {
				em[i] = new Medusa(app_.random(20.f, 75.0f) + offset, app_.random(-26.0f, 26.0f), med);
			}
			else {
				em[i] = new Goliath(app_.random(20.f, 75.0f) + offset, app_.random(-17.f, 17.f), gol);
			}
			
			offset += 30f;
		}
		return em;
	}

	/**
	 * Changes the level
	 * @param l	The number of the new level
	 */
	public void upLevel() {
		level_++;
	}
	
	protected static void setup_(PApplet app) {
		app_ = app;
	}


	public Rectangle[] recGen(PImage rock) {
		int totalNum = (int)(app_.random(1.f, 10.f));
		float offset = 25;
		Rectangle[] rec = new Rectangle[totalNum];
		for(int i = 0; i < rec.length; i++) {
			rec[i] = new Rectangle(app_.random(0, 35.f) + offset,
					app_.random(-30.f, 30.f), rock);
			offset += 25;
		}
		return rec;
	}

}
