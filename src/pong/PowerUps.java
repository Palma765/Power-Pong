package pong;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Color;
import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

/**
 * Class PowerUps
 */
public abstract class PowerUps {

  private Point powerUpLocation;
  protected PlayPanel panel;
  protected String imagePath;
  private Timer effectTimer = new Timer();
  private int countdown = 0;
  private BufferedImage icon;
  private int radius;
  protected Random random = new Random();

  /**
   * Constructor
   * @param location The powerup's location
   * @param playPanel The play panel the powerup will be drawed on
   */
  public PowerUps(Point location, PlayPanel playPanel) {
	  powerUpLocation = location;
	  panel = playPanel;
	  radius = 30;
  }
	
  /**
   * The powerup icon is loaded from the corresponding file path
   */
  public void readImage() {
	  try {
		  icon = ImageIO.read(new File(imagePath));
	  } 
	  catch (IOException e) {
		  e.printStackTrace();
	  }
  }
 
  /**
   * Set a 10 seconds timer that is activated after collision with a powerup
   */
  protected void setTimer() {
	  powerUpLocation = new Point(2000,2000);
	  TimerTask effectTask = new TimerTask() {
		  public void run() {
			  if(panel.active()) {
				  countdown ++;
				  if(countdown == 300) {
					  removeEffect();
					  effectTimer.cancel();
					  countdown = 0;
				  }
			  }
		  }
	  };
	  effectTimer.schedule(effectTask, new Date(), 1000/30);
  }
  
  /**
   * Cancels the effect.
   * 
   * Removes the effect and removes the timerTask.
   */
  public void cancelEffect() {
	  removeEffect();
	  effectTimer.cancel();
  }
  
	
  /**
   * Draw the powerup icon from the loaded image to a given location
   * @param g2d Graphics Object
   */
  public void draw(Graphics2D g2d) {
	  try {
		  g2d.drawImage(icon, (int)this.powerUpLocation.getX(), (int)this.powerUpLocation.getY(), radius*2, radius*2, null);
	  } 
	  catch (Exception e) {
		  System.out.println("Error: "+e);
		  g2d.setColor(Color.GREEN);
		  g2d.fillOval((int)powerUpLocation.getX(), (int)powerUpLocation.getY(), radius*2, radius*2);
	  }
  }
  
  /**
   * The effect is executed
   */
  public abstract void triggerEffect();
  
  /**
   * The effect is finished
   */
  protected abstract void removeEffect();
	
  /**
   * Get the value of powerUpLocation
   * @return the value of powerUpLocation
   */
  public Point getLocation() {
	  return powerUpLocation;
  }
  
  /**
   * Get the value of radius
   * @return the value of radius
   */
  public int getRadius() {
	  return radius;
  }
}
