package pong;

import java.awt.Color;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.BasicStroke;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.lang.Math;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import sound.SoundPlayer;

/**
 * PlayPanel is the Panel where the game is shown.
 */
@SuppressWarnings("serial")
public class PlayPanel extends JPanel{

  //
  // Fields
  //
  private HumanPaddle player = new HumanPaddle();
  private PCPaddle computer;
  private PlayPane playPane;
  private List<PowerUps> powerList = new ArrayList<>();
  private Ball firstBall;
  private Ball secondBall = null;
  private Timer timer = new Timer();

  private Color backgroundColor = Color.decode("#1F2933");
  private boolean active = false;
  private boolean firstStart = true;
  private Random random = new Random();
  private int counterPower=0;
  
  private int FIELD_WIDTH = -1;
  private int FIELD_HEIGHT = -1;
  
  private SoundPlayer soundPlayer;
  private boolean spawningPowerUps;
  
  private boolean up = false;
  private boolean down = false;
  private boolean startedGame = false;
   
  /**
   * Constructs the PlayPanel, initiates the keyBindings and sets up the game Task for the Timer.
   * 
   * @param playPane			The PlayPane in which this PlayPanel will be shown
   * @param soundPlayer			The soundPlayer for the whole game
   * @param difficulty			The difficulty value for the PCPaddle
   * @param spawningPowerUps	The boolean if PowerUps should be generated
   */
  public PlayPanel (PlayPane playPane, SoundPlayer soundPlayer, float difficulty, boolean spawningPowerUps) {
	  
	  keyBindings();
	  
	  computer = new PCPaddle(difficulty);
	  this.playPane = playPane;
	  this.soundPlayer = soundPlayer;
	  this.spawningPowerUps = spawningPowerUps;
	  this.setBackground(backgroundColor);
	  
	  firstBall = new Ball();
	  
	  TimerTask game = new TimerTask() {
		  /**
		   * Runs the update() method if the PlayPanel is active.
		   */
		  public void run() {
			  if(active && startedGame) {
				  update();
			  }
				  
		  }
	  };
	  
	  //Sets game as Task with executing every 1000/60 Ms (60fps).
	  timer.schedule(game,new Date(), 1000/60);
	
  }
  
  /**
   * Sets the KeyBindings and Actions for controlling the game.
   * 
   * As long as W or UP are pressed the player moves up.
   * As long as S or DOWN are pressed the player moves up.
   * With ENTER, SPACE or ESCAPE the game is started.
   * With ENTER or ESCAPE you can pause or resume the game.
   */
  private void keyBindings() {
	  getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0),"start game");
	  getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0),"start game");
	  getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),"start game");
	  getActionMap().put("start game", new AbstractAction() {
		@Override
		public void actionPerformed(ActionEvent e) {
			startedGame = true;
			playPane.toggleStart();
			
			getActionMap().remove("start game");
			getInputMap().remove(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0));
			getInputMap().remove(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0));
			getInputMap().remove(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0));
			
			getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0),"pause resume game");
			getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),"pause resume game");
		}
	  });
	  
	  getActionMap().put("pause resume game", new AbstractAction() {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(active)
				playPane.forwardPauseButton().doClick();
			else
				playPane.getResumeButton().doClick();
		}
	  });
	  
	  getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, false),"up_start");
	  getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, false),"up_start");
	  getActionMap().put("up_start", new AbstractAction() {
		@Override
		public void actionPerformed(ActionEvent e) {
			up = true;
		}
	  });
	  
	  getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, true),"up_stop");
	  getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, true),"up_stop");
	  getActionMap().put("up_stop", new AbstractAction() {
		@Override
		public void actionPerformed(ActionEvent e) {
			up = false;
		}
	  });
	  
	  getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, false),"down_start");
	  getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0, false),"down_start");
	  getActionMap().put("down_start", new AbstractAction() {
		@Override
		public void actionPerformed(ActionEvent e) {
			down = true;
		}
	  });
	  
	  getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, true),"down_stop");
	  getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0, true),"down_stop");
	  getActionMap().put("down_stop", new AbstractAction() {
		@Override
		public void actionPerformed(ActionEvent e) {
			down = false;
		}
	  });
  }
  
  /**
   * Starts and stops the game.
   * 
   * Negates the boolean active value of the PlayPanel and grabs the focus if active.
   * When its the first call (and therefore the PlayPanel is now visible)
   * necessary width and heights computations are made.
   */
  public void startStopGame() {
	  if (firstStart) {
		  firstStart ^=true;
		  playPane.toggleStart();
		  
		  FIELD_WIDTH=this.getWidth()-15;
		  int heightJFrame = this.getRootPane().getHeight();
		  int heightJPanel = getHeight();
		  FIELD_HEIGHT=heightJPanel-(heightJFrame-heightJPanel)/2-2*5-2*2;
		  firstBall.setBounds(FIELD_WIDTH,FIELD_HEIGHT);
		  computer.setBounds(FIELD_WIDTH,FIELD_HEIGHT);
		  player.setBounds(FIELD_WIDTH,FIELD_HEIGHT);
	  }
	  active ^= true;
	  //bitwise XOR is used here.
	  //does the same thing as:
	  //active = !active;
	  if (active)
		  grabFocus();
  }
  

  /**
   * Set the value of backgroundColor
   * @param newVar the new value of backgroundColor
   */
  public void setBackgroundColor (Color newVar) {
    backgroundColor = newVar;
  }

  /**
   * Get the value of backgroundColor
   * @return the value of backgroundColor
   */
  public Color getBackgroundColor () {
    return backgroundColor;
  }
  
  /**
   * Gets called 30 times/s and updates the whole playfield.
   * 
   * Gets called 30 times/s and checks for collisions, goals and
   * spawns PowerUps every 300 calls and updates the ball locations and updates the computer.
   * 
   * @see pong.Ball
   * @see pong.HumanPaddle
   */
  public void update() {
	  if(spawningPowerUps) {
		  if(counterPower==600) {
			  spawnPowerUp();
			  counterPower=0;
		  }
		  else {
			  counterPower++;
		  }
		  collisionBallPowerUp();
	  }
	  
	  
	collisionBallPaddle();
    goalCheck();
    
    firstBall.updateLocation(soundPlayer);
    if (secondBall!=null) {
    	secondBall.updateLocation(soundPlayer);
        computer.update(firstBall, secondBall, 300-counterPower);
    }
    else {
    	computer.update(firstBall);
    }
    
    if (up) {
    	player.moveUp();
    }
    else if (down) {
    	player.moveDown();
    }
  }
  
  /**
   * Paints all components.
   */
  protected void paintComponent(Graphics g) {
	super.paintComponent(g);
	Graphics2D g2D = (Graphics2D) g;
	
	Stroke dashed = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
	g2D.setStroke(dashed);
	g2D.setColor(Color.decode("#9AA5B1"));
	g2D.drawLine(FIELD_WIDTH/2,0,FIELD_WIDTH/2,FIELD_HEIGHT);
	
	player.draw(g2D);
	computer.draw(g2D);
	
	try {
		for(int i=0; i<powerList.size();i++)
			powerList.get(i).draw(g2D);
	} catch (ConcurrentModificationException e) {
		System.out.println("PowerUp added while iterating through powerList");
	}
	
	
	firstBall.draw(g2D);
	if (secondBall!=null)
		secondBall.draw(g2D);
	
	repaint();
  }
  
  /**
   * Checks for a collision with a ball and a paddle and updates the Direction of the ball if so.
   * 
   * Checks for a collision between firstBall&player and firstBall&computer
   * and if there is a secondBall also between secondBall&player and secondBall&computer.
   * If there was a collision the Direction of the ball is getting updated with the
   * difference of the y coordinate of the ball and the y coordinate of the paddle.
   * 
   * @see pong.Ball
   */
  private void collisionBallPaddle() {
	  int mitteBall = firstBall.getLocation().y + firstBall.getRadius();
	  int mitteHumanPaddle = player.getLocation().y + player.getLength()/2;
	  int mittePCPaddle = computer.getLocation().y + computer.getLength()/2;
	  
	  if (checkCollisionBallPaddle(firstBall, player)) {
		  firstBall.updateDirection(mitteBall - mitteHumanPaddle);
	  }
	  else if (checkCollisionBallPaddle(firstBall, computer)) {
		  firstBall.updateDirection(mitteBall - mittePCPaddle);
	  }
	  if (secondBall !=null) {
		  mitteBall = secondBall.getLocation().y + secondBall.getRadius();
		  if (checkCollisionBallPaddle(secondBall, player)) {
			  secondBall.updateDirection(mitteBall - mitteHumanPaddle);
		  }
		  else if (checkCollisionBallPaddle(secondBall, computer)) {
			  secondBall.updateDirection(mitteBall - mittePCPaddle);
		  }
	  }
  }
  
  /**
   * Checks if there was a collision between a Ball and a Paddle
   * 
   * If the distance on x and y is less than the radius of the ball + the width/2 or the Length/2
   * of the paddle, then there is a collision and the collision sound will be played.
   * 
   * @param ball 	that should be checked for a collision
   * @param paddle 	that should be checked for a collision
   * @return 		true if there was a collision, false if not
   */
  private boolean checkCollisionBallPaddle(Ball ball, Paddle paddle) {
	  final int width=20;
	  double xDiff = Math.abs(ball.getLocation().getX()+ball.getRadius()-(paddle.getLocation().getX()+width/2));
	  double yDiff = Math.abs(ball.getLocation().getY()+ball.getRadius()-(paddle.getLocation().getY()+paddle.getLength()/2));
	  int limitXDiff = ball.getRadius()+width/2;
	  int limitYDiff = ball.getRadius()+paddle.getLength()/2;
	  
	  if(xDiff<= limitXDiff && yDiff <= limitYDiff) {
		  soundPlayer.collisionPaddle();
		  return true;
	  }
	  else
		  return false;
	  
  }
  
  /**
   * Checks for a collision of the firstBall with a PowerUp and if so,
   * removes all other PowerUps and executes the PowerUp effect.
   * 
   * Goes through every PowerUp and checks if the realDistance is lower or equal the 
   * limitDistance. If so and if there was no other detection, the collision sound is played,
   * all other PowerUps are removed, the counter for the next PowerUp to spawn is reset and
   * the effect of the PowerUp is triggered.
   */
  private void collisionBallPowerUp() {
	boolean detected = false;
	double mitteXBall = firstBall.getLocation().getX()+firstBall.getRadius();
	double mitteYBall = firstBall.getLocation().getY()+firstBall.getRadius();
	for(int i=0; i<powerList.size(); i++) {
		double xDiff = mitteXBall - (powerList.get(i).getLocation().getX()+powerList.get(i).getRadius());
		double yDiff = mitteYBall - (powerList.get(i).getLocation().getY()+powerList.get(i).getRadius());
		double realDistance = Math.sqrt(Math.pow(xDiff,2)+Math.pow(yDiff,2));
		int limitDistance = firstBall.getRadius()+powerList.get(i).getRadius();
		if(!detected && realDistance <= limitDistance) {
		  soundPlayer.collisionPowerUp();
		  removePowerUps(powerList.get(i));
		  detected=true;
		  counterPower=0;
		  powerList.get(0).triggerEffect();
		}
	}
  }

  /**
   * Checks if there is goal.
   */
  private void goalCheck() {
	  goalCheckBall(firstBall);
	  if (secondBall!=null) {
		  goalCheckBall(secondBall);
	  }
  }
  
  /**
   * Checks if there is a goal by the ball and updates the scores if so and calls goal().
   * @param ball	The ball a goal should be checked for.
   */
  private void goalCheckBall(Ball ball) {
	  if (ball.getLocation().getX()+ball.getRadius()<=20) {
		  player.raiseScore();
		  firstBall.setLastWinner(1);
		  playPane.updateScore(1);
		  goal();
	  }
	  else if (ball.getLocation().getX()+ball.getRadius()>FIELD_WIDTH-20) {
		  computer.raiseScore();
		  firstBall.setLastWinner(-1);
		  playPane.updateScore(-1);
		  goal();
	  }
  }
  
  /**
   * Resets all things if there was a goal.
   * 
   * Gets called if there was a goal, resets all elements,
   * cancels the PowerUp if active and calls winCheck().
   */
  private void goal() {
	  if (powerList.size()==1 && powerList.get(0).getLocation() == new Point(2000,2000))
		  powerList.get(0).cancelEffect();
	  soundPlayer.point();
	  firstBall.reset();
	  secondBall = null;
	  computer.resetSize();
	  computer.resetSpeed();
	  player.resetSize();
	  player.resetSpeed();
	  powerList.clear();
	  counterPower=0;
	  winCheck();
  }
  
  /**
   * Checks if there is a winner and if so ends the game.
   * 
   * If one paddle got 10 points the playPane is given the winner,
   * a sound is played and the ActionMap is cleared (KeyBindings no longer work).
   */
  private void winCheck() {
	  if (player.getScore()==10) {
		  playPane.setWinnerSide(1);
		  soundPlayer.endWinning();
		  getActionMap().clear();
	  }
	  else if (computer.getScore()==10) {
		  playPane.setWinnerSide(-1);
		  soundPlayer.endLoosing();
		  getActionMap().clear();
	  }
  }
  
  /**
   * Removes all PowerUps except the given.
   * @param powerUp The PowerUp that should remain in the powerList
   */
  private void removePowerUps(PowerUps powerUp) {
	  powerList.clear();
	  powerList.add(powerUp);
  }
  
  /**
   * Removes all PowerUps
   */
  public void removeAllPowerUps() {
	  powerList.clear();
  }
  
  /**
   * Adds an random PowerUp at a random location.
   * 
   * Creates and adds a random PowerUp to the powerList.
   * The location of the PowerUp is random in the range of 50 <= x <= field_length-160 and
   * 30 <= y <= field_height-120.
   */
  private void spawnPowerUp() {
	  Point point = new Point(50+random.nextInt(FIELD_WIDTH-160),30+random.nextInt(FIELD_HEIGHT-120));
	  switch(random.nextInt(5)) {
	  case 0:
		  powerList.add(new PowerUpChangeBallDirection(point, this));
		  break;
	  case 1:
		  powerList.add(new PowerUpChangeBallSpeed(point, this));
		  break;
	  case 2:
		  powerList.add(new PowerUpDoubleBall(point, this));
		  break;
	  case 3:
		  powerList.add(new PowerUpResizeBall(point, this));
		  break;
	  case 4:
		  powerList.add(new PowerUpResizePaddle(point, this));
		  break;
	  default:
		  
		  System.out.println("Generating of PowerUp failed. Randomnumber out of range.");

	  }
  }
  
  /**
   * Creates the secondBall.
   */
  public void createSecondBall() {
	  secondBall = new Ball(firstBall);
  }
  
  /**
   * Destroys the secondBall.
   */
  public void destroySecondBall() {
	  secondBall = null;
  }
  
  /**
   * Gives out the firstBall.
   * @return the firstBall
   */
  public Ball forwardBall() {
	  return firstBall;
  }
  
  /**
   * Gives out an array of the paddles.
   * @return an Paddle array with player and computer.
   */
  public Paddle[] forwardPaddles() {
	  return new Paddle[] {player, computer};
  }
  
  /**
   * Gives out whether the PlayPanel is active or not.
   * @return true if the PlayPanel is active and false if not
   */
  public boolean active() {
	  return active;
  }  

}
