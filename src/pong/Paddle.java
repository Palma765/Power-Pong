package pong;

import java.awt.Point;

import java.awt.Color;
import java.awt.Graphics2D;


/**
 * Class Paddle
 */
abstract public class Paddle {

  protected Point location;  //upper left corner of the paddle
  protected float speed;
  private int score;
  private int length;
  private Color color = Color.decode("#9AA5B1");
  
  /**
   * Paddle Constructor
   */
  public Paddle () {
	  speed = 1.0f;
	  score = 0;
	  length = 80;
  }

  /**
   * Get the value of location
   * @return the value of location
   */
  public Point getLocation () {
    return location;
  }

  /**
   * Get the value of score
   * @return the value of score
   */
  public int getScore () {
    return score;
  }

  /**
   * Set the value of length
   * @param newVar the new value of length
   */
  public void setLength (int newVar) {
    length = newVar;
  }

  /**
   * Get the value of length
   * @return the value of length
   */
  public int getLength () {
    return length;
  }
  
  /** Render method
   * @param g2d Graphics2D Object
   */
  public void draw(Graphics2D g2d)
  {
	  g2d.setColor(color);
	  g2d.fillRect((int)(location.getX()), (int)(location.getY()), 20, length);
  }


  /** Resets the size of the paddle to the initial value
   */
  public void resetSize()
  {
	  length = 80;
  }

  /** Resets the speed of the paddle to the initial value
   */
  public void resetSpeed() {
	  speed = 1;
  }
  
  /**
   * changes the speed of the paddle according to its size
   */
  public void changeSpeed() {
	  float sizeDifference = (this.getLength() / 80.0f) -1.0f;
	  if(sizeDifference < 0) {
		  speed += sizeDifference * -2;
	  }
	  else {
		  speed -= sizeDifference / 2;
	  }
  }

  /** Raises the score of the paddle by one
   */
  public void raiseScore()
  {
	  score++;
  }
  
  /**
   * Sets the paddle in the middle of the field_height
   * @param field_width		width of the field (Not used, but could be)
   * @param field_height	height of the field
   */
  public void setBounds(int field_width, int field_height) {
	  location.translate(0,(field_height-length)/2-location.y);
  }


}
