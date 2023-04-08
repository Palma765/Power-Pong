package sound;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Plays BackgroundMusic and sounds.
 */
public class SoundPlayer {
	private float volume = 0.45f;
	private float volumeFixed = 0.45f;
	private boolean muted = false;
	private Clip music;
	
	/**
	 * Starts the background music in and infinite loop.
	 */
	public SoundPlayer() {
		//Source: https://mixkit.co/free-sound-effects/game/
		music();
	}
	
	
	/**
	 * Gives the AudioInputStream of a given file.
	 * @param file	file to which the AudioInputStream is desired
	 * @return		AudioInputStream of the file or null.
	 */
	private AudioInputStream getStream(String file) {
		Path path = FileSystems.getDefault().getPath("src","sound","SoundFiles");
		String fs = System.getProperty("file.separator");
		try {
			return AudioSystem.getAudioInputStream(new File(path+fs+file).getAbsoluteFile());
		} catch (UnsupportedAudioFileException | IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//
	//VolumeSetting
	//
	/**
	 * Sets the overall volume and applies it to the music clip.
	 * @param volume	volume between 0f and 1f that should be set.
	 */
	public void setVolume(float volume) {
		if (0f<=volume && volume <=0.9f) {
			this.volumeFixed=volume;
//			muted=false;
			if(!muted)
				this.volume=volume;
			setVolume(music);
		}
	}
	
	/**
	 * Sets the applied volume to 0 or back to the fixed volume.
	 */
	public void mute() {
		muted ^=true;
		
		if (muted)
			volume=0f;
		else
			volume=volumeFixed;
			
		setVolume(music);
	}
	
	/**
	 * Sets the overall volume of the clip if it is open.
	 * @param clip	The clip the volume should be set for.
	 */
	private void setVolume(Clip clip) {
		if(clip.isOpen()) {
			FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			float range = gainControl.getMaximum() - gainControl.getMinimum();
			float gain = (float) ((range * Math.sqrt(volume)) + gainControl.getMinimum());
			gainControl.setValue(gain);
		}
	}
	
	/**
	 * Plays the given audio file.
	 * @param filename	the name of the audio file.
	 */
	private void play(String filename) {
		AudioInputStream stream = getStream(filename);
		try {
			Clip clip;
			clip = AudioSystem.getClip();
			clip.open(stream);
			setVolume(clip);
			clip.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Plays the collisionPaddle sound.
	 */
	public void collisionPaddle() {
		play("paddle-hit.wav");
	}
	
	/**
	 * Plays the collisionPowerUp sound.
	 */
	public void collisionPowerUp() {
		play("power_up.wav");
	}
	
	/**
	 * Plays the collisionWall sound.
	 */
	public void collisionWall() {
		play("wall-hit.wav");
	}
	
	/**
	 * Plays the BackgroundMusic in an infinite loop.
	 */
	private void music() {
		AudioInputStream stream = getStream("music.wav");
		try {
			music = AudioSystem.getClip();
			music.open(stream);
			music.loop(Clip.LOOP_CONTINUOUSLY);
			setVolume(music);
			music.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Plays the loosing sound.
	 */
	public void endLoosing() {
		play("fail.wav");
	}
	
	/**
	 * Plays the winning sound.
	 */
	public void endWinning() {
		play("win.wav");
	}
	
	/**
	 * Plays the point sound.
	 */
	public void point() {
		play("goal.wav");
	}
	
}
