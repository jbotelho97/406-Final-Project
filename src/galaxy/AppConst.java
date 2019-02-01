package galaxy;
/**
 * This interface stores a bunch of application-wide constants
 * @author Jack Botelho
 *
 */
public interface AppConst {

	/**
	 * Dimensions of the window in pixels
	 */
	int winW = 800, winH = 600;
	
	//World Dimensions and Scaling
	
	/**
	 * The center of the screen is the new origin
	 */
	float worldX = winW / 2, worldY = winH / 2; 
	
	/**
	 * Width and Height of the world in world units
	 */
	float worldW = 100, worldH = 80;
	
	/**
	 * Min and Max x-values for the screen
	 */
	float xMax = worldW / 2, xMin = -xMax;
	
	/**
	 * Min and Max y-values for the screen
	 */
	float yMax = worldH / 2, yMin = -yMax;
	
	/**
	 * Conversion scale for pixels and world units.
	 */
	float pixToWorld = worldW / winW, worldToPix = 1.0f / pixToWorld;
	
	/**
	 * The amount the screen will auto scroll to the right
	 */
	float screenV = -0.05f;
	
	/**
	 * This is the graveyard: where we send actors and objects when they die
	 */
	float graveX = -10000f, graveY = -10000f;
}
