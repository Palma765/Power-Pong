package pong;

import java.awt.Point;
import java.util.Random;

import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Color;

import sound.SoundPlayer;

/**
 * Class Ball
 */
public class Ball extends Component{

  private static final long serialVersionUID = 1L;
  
  private Point location = new Point();
  private int radius = 20;
  private float xDirection;
  private float yDirection;
  private int lastWinner = 0;
  private float speed;
  private Random rd = new Random();
  private float activeSpeed;
  private Color color = Color.decode("#9AA5B1");
  private int field_width = -1;
  private int field_height = -1;
  
  /**
   * Constructor
   */
  public Ball () {
	  xDirection = rd.nextBoolean() ? 1f : -1f;
	  yDirection = rd.nextBoolean() ? rd.nextFloat() : rd.nextFloat() * -1;
	  activeSpeed = 8f;
  };
  
  /**
   * Constructor for a second Ball that will travel in the opposite direction of the first Ball. 
   * @param firstBall Ball
   */
  public Ball (Ball firstBall) {
	  field_height=firstBall.getField_Height();
	  location=firstBall.getLocation();
	  activeSpeed=firstBall.getactiveSpeed();
	  xDirection=firstBall.getXDirection() * -1;
	  yDirection=firstBall.getYDirection() * -1;
  }

  /**
   * Render method
   * @param g2d Graphics Object
   */
  public void draw(Graphics2D g2d)
  {
	  g2d.setColor(color);
	  g2d.fillOval((int)location.getX(), (int)location.getY(), radius, radius);
  }
  
  /**
   * Checks for a will-hit and updates the location accordingly. 
   * @param sound SoundPlayer so the wall-hit sound can be played
   */
  public void updateLocation(SoundPlayer sound)
  {
	  if (collisionBallWall()) {
		  yDirection *= -1;
		  sound.collisionWall();
	  }
	  int newX = (int) (location.getX() + xDirection * activeSpeed);
	  int newY = (int) (location.getY() + yDirection * activeSpeed);
	  location = new Point(newX, newY);
  }

  /**
   * Resets radius to the default value of 20. 
   */
  public void resetRadius()
  {
	  radius = 20;
  }

  /**
   * Inverts the horizontal Direction
   */
  public void changeBallDirection()
  {
	  xDirection *= -1;
  }

  /**
   * Resets the ball to its initial state
   */
  public void reset()
  {
	  radius = 20;
	  location = new Point(field_width/2 - radius/2, field_height/2 - radius/2);
	  yDirection = rd.nextBoolean() ? rd.nextFloat() : rd.nextFloat() * -1;
	  activeSpeed = 8f;
	  //Assuming 1 == left player
	  if (lastWinner == 1) {
		  xDirection = -1;
	  }
	  else {
		  xDirection = 1;
	  }
  }

  /**
   * Prevents the ball form getting stuck in the paddle
   */
  public void fixLocation()
  {
	  int newX = (int) (location.getX() + xDirection * activeSpeed);
	  int newY = (int) (location.getY() + yDirection * activeSpeed);
	  location = new Point(newX, newY);
  }
  
  /**
   * Updates ball direction depending on where it hit the paddle relative to its center. 
   * Above center: negative distance
   * Below center: positive distance
   * @param paddlePos Position of Collision with paddle relative to the paddle center
   */
  public void updateDirection(int paddlePos) {
	  if (paddlePos <= 0) {
		  yDirection = Math.max(-1, yDirection += paddlePos * 0.01);
		  xDirection *= -1;
	  }
	  else if (paddlePos > 0) {
		  yDirection = Math.min(1, yDirection += paddlePos * 0.01);
		  xDirection *= -1;
	  }
	  activeSpeed = Math.min(activeSpeed *= 1.025, 30);
	  fixLocation();
	  return;
  }

  /**
   * Checks if the ball would be out of bounds on the next frame.
   * @return true if it would, false otherwise
   */
  private boolean collisionBallWall()
  {
	  if (location.getY() + yDirection * activeSpeed < 0 || location.getY() + yDirection * activeSpeed > field_height - radius) {
		  return true;
	  }
	  else {
		  return false;
	  }
  }
  
  /**
   * Get the value of location
   * @return the value of location
   */
  public Point getLocation () {
	  return location;
  }
  
  /**
   * Get the value of field_height
   * @return the value of field_height
   */
  public int getField_Height () {
	  return field_height;
  }
  
  /**
   * Set the value of radius
   * @param newVar the new value of radius
   */
  public void setRadius (int newVar) {
	  radius = newVar;
  }
  
  /**
   * Get the value of radius
   * @return the value of radius
   */
  public int getRadius () {
	  return radius/2;
  }
  
  /**
   * Get the value of xDirection
   * @return the value of xDirection
   */
  public float getXDirection () {
	  return xDirection;
  }
  
  /**
   * Get the value of yDirection
   * @return the value of yDirection
   */
  public float getYDirection () {
	  return yDirection;
  }
  
  /**
   * Set the value of lastWinner
   * @param newVar the new value of lastWinner
   */
  public void setLastWinner (int newVar) {
	  lastWinner = newVar;
  }
  
  /**
   * Get the value of activeSpeed
   * @return the value of speed
   */
  public float getactiveSpeed () {
	  return activeSpeed;
  }
  
  /**
   * Set the value of activeSpeed
   * @param newVar the new value of speed
   */
  public void setactiveSpeed (float newVar) {
	  activeSpeed = newVar;
  }
  
  /**
   * Get the value of activeSpeed
   * @return the value of speed
   */
  public float getSpeed () {
	  return speed;
  }
  
  /**
   * Set the value of speed
   * @param newVar the new value of speed
   */
  public void setSpeed (float newVar) {
	  speed = newVar;
  }
  
  /**
   * Sets bounds so the ball knows where to expect walls. 
   * @param width Field Width
   * @param height Field Height
   */
  public void setBounds(int width, int height) {
	  field_height = height;
	  field_width = width;
	  location = new Point(field_width/2 - radius/2, field_height/2 - radius/2);
  }
}
