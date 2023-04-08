package pong;

import java.awt.Point;
import java.nio.file.FileSystems;

/**
 * Class PowerUpChangeBallSpeed
 */
public class PowerUpChangeBallSpeed extends PowerUps{

  /**
   * Constructor
   * @param location The powerup's location
   * @param playPanel The play panel the powerup will be drawed on
   */
  public PowerUpChangeBallSpeed (Point location, PlayPanel playPanel) {
	  super(location, playPanel);
	  imagePath = FileSystems.getDefault().getPath("src","graphics","BallSpeed_Trans.png").normalize().toString();
	  readImage();
  }

  /**
   * Trigger the powerup's effect: Change the ball's speed
   */
  @Override
  public void triggerEffect() {
	  super.setTimer();
	  panel.forwardBall().setSpeed(panel.forwardBall().getactiveSpeed());
	  panel.forwardBall().setactiveSpeed(panel.forwardBall().getactiveSpeed()*(1.02f + Math.min(0.3f, random.nextFloat())));
  }

  /**
   * Remove the powerup's effect
   */
  @Override
  protected void removeEffect() {
	  float lastSpeed = panel.forwardBall().getSpeed();
	  panel.forwardBall().setactiveSpeed(lastSpeed);
  }
}
