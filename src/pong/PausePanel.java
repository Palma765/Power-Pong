package pong;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import sound.SoundPlayer;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.FileSystems;
import java.util.Hashtable;
/**
 * PausePanel is shown if the player pauses the game
 */
@SuppressWarnings("serial")
public class PausePanel extends JPanel{

  private JLabel message;
  private JButton continueButton;
  private JButton exitButton;
  private JSlider volumeSlider;
  private JButton muteButton;
  
  
  Font fontPlain = new Font("Courier", Font.PLAIN, 50);
  Font fontPlainSmall = new Font("Courier", Font.PLAIN, 20);
  
  ImageIcon volumeIcon = resizeIcon(new ImageIcon(FileSystems.getDefault().getPath("src","graphics","volume.png").normalize().toString()), 50, 50);
  ImageIcon muteIcon = resizeIcon(new ImageIcon(FileSystems.getDefault().getPath("src","graphics","mute.png").normalize().toString()), 50, 50);
  private boolean muted = false;
 
  /**
   * Constructs pausePanel and adjusts every component of this panel
   * @param soundPlayer controls ingame-sounds
   */
  public PausePanel (SoundPlayer soundPlayer) { 
	  this.setBackground(Color.decode("#1F2933"));
	  this.setBounds(290, 250, 700, 400);
	  this.setLayout(null);
	  this.setBorder(BorderFactory.createLineBorder(Color.gray, 2));
	  
	  message = new JLabel("PAUSE");
	  message.setBounds(200, 20, 300, 80);
	  message.setForeground(Color.white);
	  message.setHorizontalAlignment(SwingConstants.CENTER);
	  message.setFont(fontPlain);
	  
	  continueButton = new JButton("CONTINUE");
	  continueButton.setBounds(75, 120, 250, 50);
	  continueButton.setBackground(Color.decode("#3E4C59"));
	  continueButton.setForeground(Color.white);
	  continueButton.setFont(fontPlainSmall);
	  
	  
	  exitButton = new JButton("MAIN MENU");
	  exitButton.setBounds(375, 120, 250, 50);
	  exitButton.setBackground(Color.decode("#3E4C59"));
	  exitButton.setForeground(Color.white); 
	  exitButton.setFont(fontPlainSmall);
	  
	  Hashtable<Integer, JLabel> volLabels = new Hashtable<>();
	  volLabels.put(0, new JLabel("-"));
	  volLabels.put(90, new JLabel("+"));
	  volLabels.get(0).setForeground(Color.white);
	  volLabels.get(0).setFont(fontPlainSmall);
	  volLabels.get(90).setForeground(Color.white);
	  volLabels.get(90).setFont(fontPlainSmall);
	  
	  volumeSlider = new JSlider(0,90,45);
	  volumeSlider.setBounds(150, 240, 400, 50);
	  volumeSlider.setBackground(Color.decode("#1F2933"));
	  volumeSlider.setMajorTickSpacing(9);
	  volumeSlider.setPaintTicks(true);
	  volumeSlider.setLabelTable(volLabels);
	  volumeSlider.setPaintLabels(true);
	  
	  muteButton = new JButton();
	  muteButton.setBounds(325, 300, 50, 50);
	  muteButton.setIcon(volumeIcon);
	  muteButton.setBorder(null);
	  
	  this.add(continueButton);
	  this.add(exitButton);
	  this.add(message);
	  this.add(volumeSlider);
	  this.add(muteButton);
	  
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
   * Get the value of continueButton
   * @return the value of continueButton
   */
  public JButton getContinueButton () {
    return continueButton;
  }

  /**
   * Get the value of exitButton
   * @return the value of exitButton
   */
  public JButton getExitButton () {
    return exitButton;
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
