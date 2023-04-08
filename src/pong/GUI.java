package pong;

import javax.swing.JFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLayeredPane;

import sound.SoundPlayer;


/**
 * Class GUI - all panels are put together here
 */
@SuppressWarnings("serial")
public class GUI extends JFrame{

  //
  // Fields
  //
  private StartPanel startPanel;
  private PlayPane playPane;
  private PausePanel pausePanel;
  private InfoPanel infoPanel;
  private WinPanel winPanel;
  
  private JLayeredPane layeredPane;
  
  private SoundPlayer soundPlayer = new SoundPlayer();
  private boolean winScreenActive=false;
  
  /**
   * Constructor - generates instances of all Panels and adjusts JFrame
   */
  public GUI () {
	  
	  //Panels
	  startPanel = new StartPanel(soundPlayer);
	  pausePanel = new PausePanel(soundPlayer);
	  infoPanel = new InfoPanel();
	  winPanel = new WinPanel();
	  
	  setLayeredPane();
	  
	  //Frame
	  this.setTitle("P O W E R P O N G");
	  this.setUndecorated(false);
	  this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	  this.setSize(1280, 722);
	  this.setLocationRelativeTo(null);
      this.setResizable(false);
	  this.setLayout(null);
	  this.add(layeredPane);
	  this.setVisible(true);
	  
	  addListener();
  }
  
  /**
   * adds Listener to Buttons and calls methods to change the configuration of layeredPane
   */
  public void addListener() {
	  
	  startPanel.getStartButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				startGame();
				startInfoPanel();
			}
		});
	  
	  
	  //ActionListener Panels mit PlayPane wird hinzugef�gt beim starten des Spiels erst!!
	  //Muss so, weil PlayPane bei neustarten und so jedes mal neu erzeugt wird.
	  
	  pausePanel.getContinueButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				resumeGame();
			}
		});
	  
	  pausePanel.getExitButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				exit();
			}
		});
	  
	  winPanel.getMainMenuButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				startMainMenu();
				winScreenActive = false;
			}
		});
	  
	  winPanel.getRestartGameButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				startGame();
				winScreenActive = false;
			}
		});
	  
	  
	  
	  // Listener, um Programm zu beenden
	  winPanel.getExitButton().addActionListener(e -> System.exit(0));
	  
  }
  
  /**
   * adds listeners in case of the end of a game
   */
  public void addListenersRepeated() {
	  playPane.getWinButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				startWinPanel(true);
				winScreenActive = true;
			}
		});
	  
	  playPane.getLoseButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				startWinPanel(false);
				winScreenActive = true;
			}
		});
	  
	    playPane.getResumeButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				resumeGame();
			}
		});

	    playPane.forwardPauseButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!winScreenActive)
					startPausePanel();
			}
		});
  }
  
  /**
   * initializes layeredPane and assigns layeres to the panels within layeredPane
   */
  public void setLayeredPane () {
	  layeredPane = new JLayeredPane();
	  layeredPane.setBounds(0, 0, 1280, 722);
	  layeredPane.setOpaque(true);
	  layeredPane.add(startPanel, Integer.valueOf(4));
	  layeredPane.add(infoPanel, Integer.valueOf(3));
	  layeredPane.add(pausePanel, Integer.valueOf(1));
	  layeredPane.add(winPanel, Integer.valueOf(0));
  }


  //
  // Other methods
  //

  /**
   * Schliesst endPanel und PlayPane, falls diese offen sind, und startet das Spiel.
   */
  private void startGame()
  {
	  	try {
	  		layeredPane.remove(playPane);
	  	} catch (Exception e) {
	  		
	  	}
	    
	    playPane = new PlayPane(this, soundPlayer,startPanel.getDifficulty(),startPanel.getCheckPowerUp());
	    layeredPane.add(playPane, Integer.valueOf(2));
	    addListenersRepeated();
	  
	    layeredPane.setLayer(infoPanel, Integer.valueOf(3));
	    layeredPane.setLayer(startPanel, Integer.valueOf(0));
	    layeredPane.setLayer(playPane, Integer.valueOf(4));
		layeredPane.setLayer(pausePanel, Integer.valueOf(2));
		layeredPane.setLayer(winPanel, Integer.valueOf(1));
		
		playPane.toggleUpdate();
  }
  
  /**
   * hides InfoPanel after game start
   */
  public void hideInfoPanel() {
	    layeredPane.setLayer(infoPanel, Integer.valueOf(3));
	    layeredPane.setLayer(startPanel, Integer.valueOf(0));
	    layeredPane.setLayer(playPane, Integer.valueOf(4));
		layeredPane.setLayer(pausePanel, Integer.valueOf(2));
		layeredPane.setLayer(winPanel, Integer.valueOf(1));
  }

  /**
   * shows infoPanel after startScreen
   */
  private void startInfoPanel() {
	    layeredPane.setLayer(infoPanel, Integer.valueOf(4));
	    layeredPane.setLayer(startPanel, Integer.valueOf(2));
		layeredPane.setLayer(playPane, Integer.valueOf(3));
		layeredPane.setLayer(pausePanel, Integer.valueOf(1));
		layeredPane.setLayer(winPanel, Integer.valueOf(0));
  }
  
  /**
   * shows startPanel
   */
  private void startMainMenu() {
	    layeredPane.setLayer(infoPanel, Integer.valueOf(3));
	    layeredPane.setLayer(startPanel, Integer.valueOf(4));
		layeredPane.setLayer(playPane, Integer.valueOf(2));
		layeredPane.setLayer(pausePanel, Integer.valueOf(1));
		layeredPane.setLayer(winPanel, Integer.valueOf(0));
  }


  /**
   * shows pausePanel
   */
  private void startPausePanel()
  {
	    playPane.toggleUpdate();
	    layeredPane.setLayer(infoPanel, Integer.valueOf(3));
	    layeredPane.setLayer(startPanel, Integer.valueOf(0));
		layeredPane.setLayer(playPane, Integer.valueOf(2));
		layeredPane.setLayer(pausePanel, Integer.valueOf(4));
		layeredPane.setLayer(winPanel, Integer.valueOf(1));
  }


  /**
   * shows winPanel
   */
  private void startWinPanel(boolean win)
  {
	  playPane.toggleUpdate();
//	  	if(win)  
//	  		winPanel.setWinMessage("<html>YOU WON!<br/>SCORE: " + playPane.getScore() + "<br/>TIME: " + playPane.getTime() + "<html/>");
//	  	else
//	  		winPanel.setWinMessage("<html>YOU LOST!<br/>SCORE: " + playPane.getScore() + "<br/>TIME: " + playPane.getTime() + "<html/>");
	    
	    if(win)  
	  		winPanel.setWinMessage("<html><b>YOU WON!<b/><html/>");
	  	else
	  		winPanel.setWinMessage("<html><b>YOU LOST!<b/><html/>");
	  	
	  
	    layeredPane.setLayer(startPanel, Integer.valueOf(0));
		layeredPane.setLayer(playPane, Integer.valueOf(2));
		layeredPane.setLayer(pausePanel, Integer.valueOf(1));
		layeredPane.setLayer(infoPanel, Integer.valueOf(3));
		layeredPane.setLayer(winPanel, Integer.valueOf(4));
  }


  /**
   * resume to game, pausePanel is no longer displayed
   */
  private void resumeGame()
  {
	    layeredPane.setLayer(startPanel, Integer.valueOf(0));
		layeredPane.setLayer(playPane, Integer.valueOf(4));
		layeredPane.setLayer(pausePanel, Integer.valueOf(2));
		layeredPane.setLayer(winPanel, Integer.valueOf(1));
		layeredPane.setLayer(infoPanel, Integer.valueOf(3));
		playPane.toggleUpdate();
  }


  /**
   * Beendet das Spiel und f�hrt zum Main Menu.
   */
  private void exit()
  {
	    layeredPane.setLayer(startPanel, Integer.valueOf(4));
	    layeredPane.setLayer(infoPanel, Integer.valueOf(3));
		layeredPane.setLayer(playPane, Integer.valueOf(2));
		layeredPane.setLayer(pausePanel, Integer.valueOf(1));
		layeredPane.setLayer(winPanel, Integer.valueOf(0));
  }


}
