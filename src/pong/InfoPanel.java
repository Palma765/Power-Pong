package pong;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import java.awt.Font;
import java.awt.Image;
import java.awt.Color;
import java.nio.file.FileSystems;
/**
 * InfoPanel is shown after startScreen to show a Panel with contro information
 * will disappear when the game starts
 */
@SuppressWarnings("serial")
public class InfoPanel extends JPanel{

	  Font fontPlain = new Font("Courier", Font.PLAIN, 35);
	  Font fontPlainSmall = new Font("Courier", Font.PLAIN, 20);
	  
	  private JLabel controlLabel;
	  private JLabel startGameKeysLabel;
	  private JLabel pauseGameKeysLabel;
	  private JLabel movePaddleKeysLabel;
	  
	  private JButton escapeButtonStart;
	  private JButton enterButtonStart; 
	  private JButton spaceButtonStart;
	  private JLabel  orLabelStart1;
	  private JLabel orLabelStart2;
	  
	  private JButton escapeButtonPause;
	  private JButton enterButtonPause;
	  private JLabel  orLabelPause;
	  
	  private JButton upButton;
	  private JButton downButton;
	 
	  
	  ImageIcon escapeIcon = resizeIcon(new ImageIcon(FileSystems.getDefault().getPath("src","graphics","escape.png").normalize().toString()), 50, 50);
	  ImageIcon enterIcon = resizeIcon(new ImageIcon(FileSystems.getDefault().getPath("src","graphics","enter.png").normalize().toString()), 50, 50);
	  ImageIcon upIcon = resizeIcon(new ImageIcon(FileSystems.getDefault().getPath("src","graphics","arrow_up.png").normalize().toString()), 30, 30);
	  ImageIcon downIcon = resizeIcon(new ImageIcon(FileSystems.getDefault().getPath("src","graphics","arrow_down.png").normalize().toString()), 30, 30);
	  ImageIcon spaceIcon = resizeIcon(new ImageIcon(FileSystems.getDefault().getPath("src","graphics","spacebar.png").normalize().toString()), 50, 50);
	  

	  /**
	   * Constructs InfoPanel and adjusts every component
	   */
	  public InfoPanel () { 
		  this.setBackground(Color.decode("#1F2933"));
		  this.setBounds(290, 250, 700, 400);
		  this.setLayout(null);
		  this.setBorder(BorderFactory.createLineBorder(Color.gray, 2));
		  
		  controlLabel = new JLabel("Controls");
		  controlLabel.setBounds(250, 0, 200, 60);
		  controlLabel.setFont(fontPlain);
		  controlLabel.setForeground(Color.white);
		  controlLabel.setHorizontalAlignment(SwingConstants.CENTER);
		  this.add(controlLabel);
		  
		  // Buttons und Label für StartGame-Keys
		  startGameKeysLabel = new JLabel("Start Game: ");
		  startGameKeysLabel.setBounds(10, 100, 150, 50);
		  startGameKeysLabel.setFont(fontPlainSmall);
		  startGameKeysLabel.setForeground(Color.white);
		  this.add(startGameKeysLabel);
		  
		  escapeButtonStart = new JButton();
		  escapeButtonStart.setIcon(escapeIcon);
		  escapeButtonStart.setBounds(180, 100, 50, 50);
		  escapeButtonStart.setEnabled(false);
		  escapeButtonStart.setContentAreaFilled(false);
		  escapeButtonStart.setBorderPainted(false);
		  this.add(escapeButtonStart);	
		  
		  enterButtonStart = new JButton();
		  enterButtonStart.setIcon(enterIcon);
		  enterButtonStart.setBounds(280, 95, 50, 50);
		  enterButtonStart.setEnabled(false);
		  enterButtonStart.setContentAreaFilled(false);
		  enterButtonStart.setBorderPainted(false);
		  this.add(enterButtonStart);
		  
		  spaceButtonStart = new JButton();
		  spaceButtonStart.setIcon(spaceIcon);
		  spaceButtonStart.setBounds(380, 100, 50, 50);
		  spaceButtonStart.setEnabled(false);
		  spaceButtonStart.setContentAreaFilled(false);
		  spaceButtonStart.setBorderPainted(false);
		  this.add(spaceButtonStart);
		  
		  orLabelStart1 = new JLabel("or");
		  orLabelStart1.setBounds(243, 120, 20, 20);
		  orLabelStart1.setForeground(Color.white);
		  this.add(orLabelStart1);
		  
		  orLabelStart2 = new JLabel("or");
		  orLabelStart2.setBounds(343, 120, 20, 20);
		  orLabelStart2.setForeground(Color.white);
		  this.add(orLabelStart2);
		  
		  // Buttons und Label für PauseGame-Keys
		  pauseGameKeysLabel = new JLabel("Pause and resume: ");
		  pauseGameKeysLabel.setBounds(10, 200, 220, 50);
		  pauseGameKeysLabel.setFont(fontPlainSmall);
		  pauseGameKeysLabel.setForeground(Color.white);
		  this.add(pauseGameKeysLabel);
		  
		  escapeButtonPause = new JButton();
		  escapeButtonPause.setIcon(escapeIcon);
		  escapeButtonPause.setBounds(250, 200, 50, 50);
		  escapeButtonPause.setEnabled(false);
		  escapeButtonPause.setContentAreaFilled(false);
		  escapeButtonPause.setBorderPainted(false);
		  this.add(escapeButtonPause);	
		  
		  enterButtonPause = new JButton();
		  enterButtonPause.setIcon(enterIcon);
		  enterButtonPause.setBounds(350, 195, 50, 50);
		  enterButtonPause.setEnabled(false);
		  enterButtonPause.setContentAreaFilled(false);
		  enterButtonPause.setBorderPainted(false);
		  this.add(enterButtonPause);
		  
		  orLabelPause = new JLabel("or");
		  orLabelPause.setBounds(313, 215, 20, 20);
		  orLabelPause.setForeground(Color.white);
		  this.add(orLabelPause);
		  
		  // Buttons und Labels für MovePaddle-Keys
		  movePaddleKeysLabel = new JLabel("Move Paddle: ");
		  movePaddleKeysLabel.setBounds(10, 300, 160, 50);
		  movePaddleKeysLabel.setFont(fontPlainSmall);
		  movePaddleKeysLabel.setForeground(Color.white);
		  this.add(movePaddleKeysLabel);	
		 
		  upButton = new JButton();
		  upButton.setIcon(upIcon);
		  upButton.setBounds(200, 300, 30, 30);
		  upButton.setEnabled(false);
		  upButton.setContentAreaFilled(false);
		  upButton.setBorderPainted(false);
		  this.add(upButton);
		  
		  downButton = new JButton();
		  downButton.setIcon(downIcon);
		  downButton.setBounds(200, 330, 30, 30);
		  downButton.setEnabled(false);
		  downButton.setContentAreaFilled(false);
		  downButton.setBorderPainted(false);
		  this.add(downButton);
	
	  }
	  
	  /**
	   * normalizes an ImageICon
	   * 
	   * @param icon ImageIcon that shall be adjusted
	   * @param width The width that the returned ImageIcon should have
	   * @param height The height that the returned ImageIcon should have
	   * @return an ImageIcon with height and width of specified parameters
	   */
	  public ImageIcon resizeIcon(ImageIcon icon, int width, int height) {
		  Image img = icon.getImage();
		  Image newImg = img.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
		  return new ImageIcon(newImg);
	}
}


