package pong;


import javax.swing.JButton;
import javax.swing.JSplitPane;

import sound.SoundPlayer;


/**
 * PlayPane which shows the menuPanel and the PlayPanel.
 */
@SuppressWarnings("serial")
public class PlayPane extends JSplitPane{

	private MenuPanel menuPanel;
	private PlayPanel playPanel;
	private JButton win = new JButton();
	private JButton lose = new JButton();
	private JButton resume = new JButton();
	private GUI gui;
  
  /**
   * Constructor of PlayPane
   * @param gui				The GUI with all the Panels/Panes
   * @param soundPlayer		The SoundPlayer to play Sounds and adjust the volume
   * @param difficulty		The difficulty of the PCPaddle
   * @param powerUps		The boolean whether PowerUps should be generated or not
   */
  public PlayPane (GUI gui, SoundPlayer soundPlayer, float difficulty, boolean powerUps) {
	  
	  this.setBounds(0, 0, 1280, 722);
	  
	  this.gui = gui;
	  
	  menuPanel = new MenuPanel();
	  
	  playPanel = new PlayPanel(this, soundPlayer, difficulty, powerUps);
	  
	  this.setOrientation(VERTICAL_SPLIT);
	  this.setTopComponent(menuPanel);
	  this.setBottomComponent(playPanel);
	  this.setDividerSize(4);
	  this.setDividerLocation(80);
  }
  
  /**
   * Calls the Action dependent on the winner by clicking a non visible Button.
   * @param side	The winnerSide
   */
  public void setWinnerSide(int side) {
	  if (side==1)
		  win.doClick();
	  else if (side==-1)
		  lose.doClick();
  }
  
  /**
   * Returns the winButton.
   * @return	winButton
   */
  public JButton getWinButton() {
	  return win;
  }
  
  /**
   * Returns the loseButton.
   * @return	loseButton
   */
  public JButton getLoseButton() {
	  return lose;
  }
  
  /**
   * Returns the resumeButton.
   * @return resumeButton
   */
  public JButton getResumeButton() {
	  return resume;
  }


  /**
   * Updates the Score in menuPanel.
   * @param side	The winnerSide
   */
  public void updateScore(int side)
  {
	  menuPanel.updateScore(side);
  }


  /**
   * Returns the pauseButton from menuPanel.
   * @return	pauseButton from menuPanel
   */
  public JButton forwardPauseButton()
  {
	  return menuPanel.getPauseButton();
  }


  /**
   * Toggles start and stop of the game.
   */
  public void toggleUpdate()
  {
	  playPanel.startStopGame();
	  menuPanel.startStopGame();
  }
  
  /**
   * Toggles the first start to menuPanel and hides the InfoPanel in GUI.
   */
  public void toggleStart() {
	  menuPanel.startStopGame();
	  gui.hideInfoPanel();
  }
}
