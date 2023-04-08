package pong;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import java.awt.Color;
import java.awt.Font;

/**
 * MenuPanel is the top panel of PlayPane that contains score and timer
 */
@SuppressWarnings("serial")
public class MenuPanel extends JPanel{

  private Timer timer = new Timer();
  private int time = 0;
  private JButton pauseButton;
  private int leftPlayer = 0;
  private int rightPlayer = 0;
  private JLabel scoreLabel;
  private JLabel timeLabel;
  
  private boolean active=false;
  
  Font fontPlain = new Font("Courier", Font.PLAIN, 20);
  Font fontPlainLarge = new Font("Courier", Font.PLAIN, 60);
  
  /**
   * Constructs menuPanel and adjusts timer
   */
  public MenuPanel() {
	  
	  this.setBackground(Color.decode("#1F2933"));
	  this.setLayout(null);
	  pauseButton = new JButton("||");
	  scoreLabel = new JLabel("0 - 0");
	  timeLabel = new JLabel("00:00");
	  
	  TimerTask task = new TimerTask() {
		@Override
		public void run() {
			if(active) {
				time++;
				timeLabel.setText(String.format("%02d:%02d",(time%3600)/60,time%60));
			}
		}
	  };
	  
	  timer.schedule(task, new Date(), 1000);
	  
	  
	  pauseButton.setBounds(20, 10, 60, 60);
	  pauseButton.setFont(fontPlain);
	  pauseButton.setBackground(Color.decode("#3E4C59"));
	  pauseButton.setForeground(Color.white);
	  pauseButton.setBorder(BorderFactory.createLineBorder(Color.gray, 2));
	  
	  scoreLabel.setBounds(481, 5, 300, 70);
	  scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
	  scoreLabel.setForeground(Color.white);
	  scoreLabel.setFont(fontPlainLarge);
	  
	  timeLabel.setBounds(1050, 5, 300, 70);
	  timeLabel.setHorizontalAlignment(SwingConstants.CENTER);
	  timeLabel.setForeground(Color.white);
	  timeLabel.setFont(fontPlain);
	  
	  this.add(pauseButton);
	  this.add(scoreLabel);
	  this.add(timeLabel);
  }

  /**
   * Get the value of pauseButton
   * @return the value of pauseButton
   */
  public JButton getPauseButton () {
    return pauseButton;
  }

  /**
   * Set the value of scoreLabel
   * @param side side of scoreLabel?
   */
  public void updateScore (int side) {
    if (side==-1)
    	leftPlayer++;
    else if (side==1)
    	rightPlayer++;
    if (leftPlayer==10 || rightPlayer==10)
    	scoreLabel.setText(String.format("%02d - %02d",leftPlayer,rightPlayer));
    else
    	scoreLabel.setText(String.format("%01d - %01d",leftPlayer,rightPlayer));
  }

  
  /**
   * info if game is active
   */
  public void startStopGame() {
	  active ^= true;
	  
	  pauseButton.setEnabled(active);
	  
  }
}
