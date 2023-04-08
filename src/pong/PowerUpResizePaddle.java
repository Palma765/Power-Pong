package pong;

import java.awt.Point;
import java.nio.file.FileSystems;

/**
 * Class PowerUpResizePaddle
 */
public class PowerUpResizePaddle extends PowerUps{
  
  /**
   * Constructor
   * @param location The powerup's location
   * @param playPanel The play panel the powerup will be drawed on
   */
  public PowerUpResizePaddle(Point location, PlayPanel playPanel) {
	  super(location, playPanel);
	  imagePath = FileSystems.getDefault().getPath("src","graphics","PaddleSize_Trans.png").normalize().toString();
	  readImage();
  }
	
  /**
   * Trigger the powerup's effect: Change the paddle's sizes and speed
   */
  @Override
  public void triggerEffect() {
	  super.setTimer();
	  for (Paddle p: panel.forwardPaddles()) {
		  p.setLength(40 + random.nextInt(120));
		  p.changeSpeed();
	  }
  }

  /**
   * Remove the powerup's effect
   */
  @Override
  protected void removeEffect() {
	  for (Paddle p: panel.forwardPaddles()) {
		  p.resetSize();
		  p.resetSpeed();
	  }
  }
}
