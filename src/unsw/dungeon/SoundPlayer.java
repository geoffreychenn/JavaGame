package unsw.dungeon;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundPlayer {

	public static void playSound(String path) {
		try {
			AudioInputStream sound = AudioSystem.getAudioInputStream(new File(path).getAbsoluteFile());
			try {
				Clip clip = AudioSystem.getClip();
				clip.open(sound);
				clip.start();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
