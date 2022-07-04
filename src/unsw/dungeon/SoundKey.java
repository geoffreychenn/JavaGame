package unsw.dungeon;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class SoundKey implements Sound{

	public SoundKey() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void playSound() {
		String path = "sounds/rpg_sound_pack/key.wav";
		SoundPlayer.playSound(path);
		
	}

}
