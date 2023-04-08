package pong;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.Image;
import sound.SoundPlayer;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.FileSystems;
import java.util.Hashtable;
/**
 * Panel that is shown at the start of the game
 */
@SuppressWarnings("serial")
public class StartPanel extends JPanel{

  private JLabel titel;
  private JButton startButton;
  private JSlider volumeSlider;
  private JButton muteButton;
  private JSlider difficultySlider;
  private JCheckBox checkPowerUp;
  
  Font fontPlain = new Font("Courier", Font.PLAIN, 45);
  Font fontPlainSmall = new Font("Courier", Font.PLAIN, 25);
  Font fontPlainXSmall = new Font("Courier", Font.PLAIN, 20);
  Font fontPlainXXSmall = new Font("Courier", Font.PLAIN, 15);
  
  ImageIcon volumeIcon = resizeIcon(new ImageIcon(FileSystems.getDefault().getPath("src","graphics","volume.png").normalize().toString()), 50, 50);
  ImageIcon muteIcon = resizeIcon(new ImageIcon(FileSystems.getDefault().getPath("src","graphics","mute.png").normalize().toString()), 50, 50);
  private boolean muted = false;
  
  /**
   * contructs the startPanel and adjusts every component of the panels
   * @param soundPlayer instance of SoundPlayer to control the sounds of the game
   */
  public StartPanel(SoundPlayer soundPlayer) {
	  this.setBackground(Color.decode("#1F2933"));
	  this.setBounds(0, 0, 1280, 722);
	  this.setLayout(null);
	  
	  titel = new JLabel("P O W E R  P O N G");
	  volumeSlider = new JSlider(0,90,45);
	  muteButton = new JButton();
	  difficultySlider = new JSlider(0,2,1);
	  checkPowerUp = new JCheckBox("Remove Powerups");
	  startButton = new JButton("Start Game");
	  
	  this.add(titel);
	  this.add(difficultySlider);
	  this.add(volumeSlider);
	  this.add(muteButton);
	  this.add(checkPowerUp);
	  this.add(startButton);
	  
	  titel.setForeground(Color.white);
	  titel.setFont(fontPlain);
	  titel.setBounds(390, 50, 500, 50);
	  
	  Hashtable<Integer, JLabel> diffLabels = new Hashtable<>();
	  diffLabels.put(0, new JLabel("EASY"));
	  diffLabels.put(1, new JLabel("NORMAL"));
	  diffLabels.put(2, new JLabel("HARD"));
	  diffLabels.get(0).setForeground(Color.white);
	  diffLabels.get(0).setFont(fontPlainXXSmall);
	  diffLabels.get(1).setForeground(Color.white);
	  diffLabels.get(1).setFont(fontPlainXXSmall);
	  diffLabels.get(2).setForeground(Color.white);
	  diffLabels.get(2).setFont(fontPlainXXSmall);
	  
	  difficultySlider.setBounds(100, 300, 400, 50);
	  difficultySlider.setBackground(Color.decode("#1F2933"));
	  difficultySlider.setForeground(Color.white);
	  difficultySlider.setMinorTickSpacing(1);
	  difficultySlider.setPaintTicks(true);
	  difficultySlider.setLabelTable(diffLabels);
	  difficultySlider.setPaintLabels(true);
	  
	  Hashtable<Integer, JLabel> volLabels = new Hashtable<>();
	  volLabels.put(0, new JLabel("-"));
	  volLabels.put(90, new JLabel("+"));
	  volLabels.get(0).setForeground(Color.white);
	  volLabels.get(0).setFont(fontPlainXSmall);
	  volLabels.get(90).setForeground(Color.white);
	  volLabels.get(90).setFont(fontPlainXSmall);
	  
	  volumeSlider.setBounds(780, 300, 400, 50);
	  volumeSlider.setBackground(Color.decode("#1F2933"));
	  volumeSlider.setMajorTickSpacing(9);
	  volumeSlider.setPaintTicks(true);
	  volumeSlider.setLabelTable(volLabels);
	  volumeSlider.setPaintLabels(true);
	  
	  muteButton.setBounds(955, 235, 50, 50);
	  muteButton.setIcon(volumeIcon);
	  muteButton.setBorder(null);
	  
	  checkPowerUp.setFont(fontPlainSmall);
	  checkPowerUp.setBackground(Color.decode("#1F2933"));
	  checkPowerUp.setForeground(Color.white);
	  checkPowerUp.setBounds(510, 450, 260, 30);
	  
	  startButton.setBounds(440, 550, 400, 100);
	  startButton.setBackground(Color.decode("#3E4C59"));
	  startButton.setForeground(Color.white);
	  startButton.setBorder(BorderFactory.createLineBorder(Color.gray, 2));
	  startButton.setFont(fontPlainSmall);
	  
	  volumeSlider.addChangeListener(new ChangeListener() {
		@Override
		public void stateChanged(ChangeEvent e) {
			soundPlayer.setVolume(volumeSlider.getValue()/90f);
		}
		});
	  
	  muteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				soundPlayer.mute();
				muteIconSwitch();
			}
		  });
	}
  
  /**
   * Get the value of startButton
   * @return the value of startButton
   */
  public JButton getStartButton() {
	return startButton;
  }
  
  /**
   * Get the value of difficultySlider
   * @return the value of difficultySlider
   */
  public float getDifficulty() {
	switch (difficultySlider.getValue()) {
	case 0:
		return 0.23f;
	case 1:
		return 0.3f;
	case 2:
		return 0.5f;
	default:
		return 0.3f;
	}
  }
  
  /**
   * Get the value of checkPowerUp
   * @return the value of checkPowerUp
   */
  public boolean getCheckPowerUp() {
	return !checkPowerUp.isSelected();
  }
  
  /**
   * Resizes an ImageIcon
   * @param icon ImageIcon entity
   * @param width Desired width after scaling
   * @param height Desired height after scaling
   * @return scaled Imageicon
   */
  public ImageIcon resizeIcon(ImageIcon icon, int width, int height) {
	  Image img = icon.getImage();
	  Image newImg = img.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
	  return new ImageIcon(newImg);
  }
  
  /**
   * Switches muteButton Icons
   */
  public void muteIconSwitch() {
	  if (muted) {
		  muted = false;
		  muteButton.setIcon(volumeIcon);
		  this.repaint();
	  }
	  else {
		  muted = true;
		  muteButton.setIcon(muteIcon);
		  this.repaint();
	  }
  }
  
}
