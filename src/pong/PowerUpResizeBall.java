package pong;

import java.awt.Point;
import java.nio.file.FileSystems;

/**
 * Class PowerUpResizeBall
 */
public class PowerUpResizeBall extends PowerUps{
	
  /**
   * Constructor
   * @param location The powerup's location
   * @param playPanel The play panel the powerup will be drawed on
   */
  public PowerUpResizeBall(Point location, PlayPanel playPanel) {
	  super(location, playPanel);
	  imagePath = FileSystems.getDefault().getPath("src","graphics","BallSize_Trans.png").normalize().toString();
	  readImage();
  }

  /**
   * Trigger the powerup's effect: Change ball's size
   */
  @Override
  public void triggerEffect() {
	  super.setTimer();
	  panel.forwardBall().setRadius(10 + random.nextInt(20));
  }

  /**
   * Remove the powerup's effect
   */
  @Override
  protected void removeEffect() {
	  panel.forwardBall().resetRadius();
  }
}
