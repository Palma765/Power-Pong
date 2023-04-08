package pong;

import java.awt.Point;

/**
 * Class PCPaddle
 */
public class PCPaddle extends Paddle {

  private float difficulty;  
  
  /**
   * PC Paddle Constructor
   * @param difficulty Selected difficulty
   */
  public PCPaddle (float difficulty) {
	  this.difficulty = difficulty;
	  super.location = new Point(0, 280);  //initial position: middle of the left side
  }

  /**
   * Computes the move of the PCPaddle
   * @param ballPoint	Location of the Ball
   */
  private void computePaddleMove(Point ballPoint) {
	  double ballDistance = (ballPoint.getY() - (this.getLocation().getY()) - this.getLength()/2) * difficulty * speed;
	  int newY = (int)(location.getY() + (int)ballDistance);
	  
	  if (newY < location.getY() - 10 * speed) {
		 if(location.getY() - 10 >= 0)
			location.translate(0,(int) - (10 * speed));
	  }
	  else if (newY > location.getY() + 10 * speed){
		 if(location.getY() + 10 <= 535)
			location.translate(0,(int) (10 * speed));
	  }
  }
  
  /**
   * Updates the position of the paddle if the ball is moving in it's direction
   * @param ball	Ball
   */
  public void update(Ball ball)
  {
	  if(ball.getXDirection()<0)
		  computePaddleMove(ball.getLocation());
  }
  
  /**
   * Computes which ball should be targeted if there exists more than one.
   * Targets the ball which is moving towards the PCPaddle or chooses the one closest to the paddle if both
   * are moving in it's direction (provided they can reach the field boundaries in their time left). 
   * @param mainBall			Main ball
   * @param secondBall			Second Ball
   * @param timeLeftSecondBall	Time left until the second ball ceases to exist
   */
  public void update(Ball mainBall, Ball secondBall, int timeLeftSecondBall) {
	  Point pMain = mainBall.getLocation();
	  float xMain = mainBall.getXDirection();
	  Point pSecond = secondBall.getLocation();
	  float xSecond = secondBall.getXDirection();
	  float sSecond = secondBall.getactiveSpeed();
	  
	  //Both balls are moving towards the PCPaddle
	  if (xMain<0 && xSecond<0) {
		  //Main ball is closest
		  if(pMain.getX()<pSecond.getX()) {
			  computePaddleMove(pMain);
		  }
		  //Second ball is closest
		  else {
			  //Second ball can't reach field boundaries in time left, so not dangerous -> target Main ball
			  if(pSecond.getX()+timeLeftSecondBall*sSecond>0)
				  computePaddleMove(pMain);
			  //Second ball is dangerous -> target
			  else
				  computePaddleMove(pSecond);
		  }
	  }
	  //Only main ball is moving towards the PCPaddle
	  else if (xMain<0) {
		  computePaddleMove(pMain);
	  }
	  //Only second ball is moving towards the PCPaddle
	  else if (xSecond<0) {
		  computePaddleMove(pSecond);
	  }
  }


}
