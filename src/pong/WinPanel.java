package pong;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import java.awt.Color;
import java.awt.Font;

/**
 * WinPanel is displayed if the game ends (no matter if the player wins or loses)
 */
@SuppressWarnings("serial")
public class WinPanel extends JPanel{
  private JLabel winMessage;
  private JButton mainMenuButton;
  private JButton exitButton;
  private JButton restartGameButton;

  Font fontPlain = new Font("Courier", Font.PLAIN, 50);
  Font fontPlainSmall = new Font("Courier", Font.PLAIN, 25);
  Font fontPlainSmaller = new Font("Courier", Font.PLAIN, 20);
  
  /**
   * Constructs WinPanel and adjusts the components of the panel
   */
  public WinPanel() {
	  
	  this.setBackground(Color.decode("#1F2933"));
	  this.setBounds(290, 250, 700, 400);
	  this.setLayout(null);
	  this.setBorder(BorderFactory.createLineBorder(Color.gray, 2));
	  
	  winMessage = new JLabel();
	  winMessage.setBounds(100, 20, 500, 100);
	  winMessage.setForeground(Color.white);
	  winMessage.setHorizontalAlignment(SwingConstants.CENTER);
	  winMessage.setFont(fontPlain);
	  this.add(winMessage);
	  
	  //button to restart the game
	  restartGameButton = new JButton("RESTART GAME");
	  restartGameButton.setBounds(235, 160, 230, 50);
	  restartGameButton.setBackground(Color.decode("#60b59f"));
	  restartGameButton.setForeground(Color.white);
	  restartGameButton.setFont(fontPlainSmaller);
	  this.add(restartGameButton);
	  
	  
	  //main menu button to get back to start screen
	  mainMenuButton = new JButton("MAIN MENU");
	  mainMenuButton.setBounds(75, 260, 230, 50);
	  mainMenuButton.setBackground(Color.decode("#3E4C59"));
	  mainMenuButton.setForeground(Color.white);
	  mainMenuButton.setFont(fontPlainSmaller);
	  this.add(mainMenuButton);
	  
	  //exit button to end the program
	  exitButton = new JButton("QUIT GAME");
	  exitButton.setBounds(395, 260, 230, 50);
	  exitButton.setBackground(Color.decode("#3E4C59"));
	  exitButton.setForeground(Color.white);
	  exitButton.setFont(fontPlainSmaller);
	  this.add(exitButton);
  }

  /**
  * @param message the winMessage to set
  */
  public void setWinMessage(String message) {
	winMessage.setText(message);
  }

  /**
  * @return the mainMenuButton
  */
  public JButton getMainMenuButton() {
	return mainMenuButton;
  }


  /**
  * @return the exitButton
  */
  public JButton getExitButton() {
	return exitButton;
  }


  /**
  * @return the restartGameButton
  */
  public JButton getRestartGameButton() {
	return restartGameButton;
  }
 
}
