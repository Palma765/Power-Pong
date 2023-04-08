package pong;

import java.awt.Point;
/**
 * Class HumanPaddle
 */
public class HumanPaddle extends Paddle{
  
  /**
   * HumanPaddle Constructor
   */
  public HumanPaddle () {
	  super.location = new Point(1243, 280);  //initial position: middle of the right side
  }
  
  /**
   * Moves the paddle up on the y-axis (according to it's speed)
   */
  public void moveUp() {
	  if(location.getY() - 10 >= 0)
			location.translate(0,(int) - (10 * speed));
  }
  
  /**
   * Moves the paddle down on the y-axis (according to it's speed)
   */
  public void moveDown() {
	  if(location.getY() + 10 <= 535)
			location.translate(0,(int) (10 * speed));
  }

}
