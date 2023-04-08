package pong;

import java.awt.Point;
import java.nio.file.FileSystems;

/**
 * Class PowerUpChangeBallDirection
 */
public class PowerUpChangeBallDirection extends PowerUps{
	
  /**
   * Constructor
   * @param location The powerup's location
   * @param playPanel The play panel the powerup will be drawed on
   */
  public PowerUpChangeBallDirection (Point location, PlayPanel playPanel) {
	  super(location, playPanel);
	  imagePath = FileSystems.getDefault().getPath("src","graphics","DirChange_Trans.png").normalize().toString();
	  readImage();
  }

  /**
   * Trigger the powerup's effect: Change the ball's direction
   */
  @Override
  public void triggerEffect() {
	  super.setTimer();
	  panel.forwardBall().changeBallDirection();
  }
  
  /**
   * Remove the powerup's effect
   */
  @Override
  protected void removeEffect() {}
}
