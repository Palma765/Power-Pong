package pong;

import java.awt.Point;
import java.nio.file.FileSystems;

/**
 * Class PowerUpDoubleBall
 */
public class PowerUpDoubleBall extends PowerUps{
	
  /**
   * Constructor
   * @param location The powerup's location
   * @param playPanel The play panel the powerup will be drawed on
   */	
  public PowerUpDoubleBall(Point location, PlayPanel playPanel) {
	  super(location, playPanel);
	  imagePath = FileSystems.getDefault().getPath("src","graphics","DoubleBall_Trans.png").normalize().toString();
	  readImage();
  }
	
  /**
   * Trigger the powerup's effect: Create a second ball
   */
  @Override
  public void triggerEffect() {
	  super.setTimer();
	  panel.createSecondBall();
  }

  /**
   * Remove the powerup's effect
   */
  @Override
  protected void removeEffect() {
	  panel.destroySecondBall();
  }
}
